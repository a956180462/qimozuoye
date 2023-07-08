package cna.self.qimozuoye;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cna.self.qimozuoye.data.BillAdapter;
import cna.self.qimozuoye.data.DataBaseHolder;
import cna.self.qimozuoye.data.UserDataBase.ComeIn;
import cna.self.qimozuoye.data.UserDataBase.DBase;
import cna.self.qimozuoye.data.model.LoggedInUser;
import cna.self.qimozuoye.databinding.FragmentComeinStatisticsBinding;
/**
 * 提前说明，ComeinStatisticsFragment和 ExpensesStatisticsFragment是几乎一样的
 * */
public class ComeinStatisticsFragment extends Fragment {

    // 活动数据List<ComeIn>
    private static final MutableLiveData<List<ComeIn>> data = new MutableLiveData<>();
    // 获取ComeIn类型的adapter
    private BillAdapter<ComeIn> adapter;
    private FragmentComeinStatisticsBinding binding;
    DBase db = DataBaseHolder.getDb();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentComeinStatisticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = binding.listComein;

        data.setValue(DataTransfer.listComeIn);

        adapter = new BillAdapter<>(data.getValue(), getContext());
        listView.setAdapter(adapter);

        // 数据变动时事务
        data.observe(getViewLifecycleOwner(), list -> {
            DataTransfer.listComeIn = data.getValue();
            adapter.ChangeList(data.getValue());
            adapter.notifyDataSetChanged();
        });
        // 获取数据，后台线程向活动数据加入数据时应使用postValue();
        DBase.databaseWriteExecutor.execute(() -> {
            List<ComeIn> dataList = db.userDAO().getComeIns(LoggedInUser.getAccount());
            data.postValue(dataList);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}