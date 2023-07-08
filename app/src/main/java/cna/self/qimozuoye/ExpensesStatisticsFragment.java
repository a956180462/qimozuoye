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
import cna.self.qimozuoye.data.UserDataBase.DBase;
import cna.self.qimozuoye.data.UserDataBase.Expenses;
import cna.self.qimozuoye.data.model.LoggedInUser;
import cna.self.qimozuoye.databinding.FragmentExpensesStatisticsBinding;

/**
 * 提前说明，和 ComeinStatisticsFragment是几乎一样的
 * */
public class ExpensesStatisticsFragment extends Fragment {

    private static final MutableLiveData<List<Expenses>> data = new MutableLiveData<>();
    private BillAdapter<Expenses> adapter;
    private FragmentExpensesStatisticsBinding binding;
    DBase db = DataBaseHolder.getDb();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentExpensesStatisticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = binding.listExpenses;
        data.setValue(DataTransfer.listExpenses);
        adapter = new BillAdapter<>(data.getValue(), getContext());
        listView.setAdapter(adapter);

        data.observe(getViewLifecycleOwner(), list -> {
            DataTransfer.listExpenses = data.getValue();
            adapter.ChangeList(data.getValue());
            adapter.notifyDataSetChanged();
        });
        DBase.databaseWriteExecutor.execute(() -> {
            List<Expenses> dataList = db.userDAO().getExpenses(LoggedInUser.getAccount());
            data.postValue(dataList);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}