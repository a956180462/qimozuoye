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
        Float comeIn = 0f;
        Float expenses = 0f;
        Float TComeIn   = dao.TotalComeInMoney(LoggedInUser.getAccount());
        Float TExpenses = dao.TotalExpensesMoney(LoggedInUser.getAccount());
        if(TComeIn != null){
            comeIn = TComeIn;
        }
        if (TExpenses != null){
            expenses = TExpenses;
        }
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