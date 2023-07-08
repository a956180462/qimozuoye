package cna.self.qimozuoye;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cna.self.qimozuoye.data.DataBaseHolder;
import cna.self.qimozuoye.data.UserDataBase.UserDAO;
import cna.self.qimozuoye.data.model.LoggedInUser;
import cna.self.qimozuoye.databinding.FragmentSelectBinding;

/**
 * 朴实无华的统计页面（一开始想做查询页面来着，但是懒）
 * */
public class SelectFragment extends Fragment {

    private FragmentSelectBinding binding;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("DefaultLocale")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        UserDAO dao = DataBaseHolder.getDb().userDAO();
        // 初始化，你也不想让两个null相加然后报错吧。。。
        Float comeIn = 0f;
        Float expenses = 0f;
        // 查询
        Float TComeIn   = dao.TotalComeInMoney(LoggedInUser.getAccount());
        Float TExpenses = dao.TotalExpensesMoney(LoggedInUser.getAccount());
        if(TComeIn != null){
            comeIn = TComeIn;
        }
        if (TExpenses != null){
            expenses = TExpenses;
        }
        // 显示
        binding.ComeInNum.setText(String.format("%.2f",comeIn));
        binding.ExpensesNum.setText(String.format("%.2f",expenses));
        binding.TotalNum.setText(String.format("%.2f",comeIn + expenses));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}