package cna.self.qimozuoye;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cna.self.qimozuoye.data.model.LoggedInUser;
import cna.self.qimozuoye.databinding.FragmentAccountBinding;

/**
 * 就一张照片，一个文本框
 * */

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.textAccAcc.setText(LoggedInUser.getAccount());
        binding.imageView2.setImageResource(R.drawable.head_photo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}