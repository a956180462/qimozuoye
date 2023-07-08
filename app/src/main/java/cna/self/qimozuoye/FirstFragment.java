package cna.self.qimozuoye;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import cna.self.qimozuoye.data.model.LoggedInUser;
import cna.self.qimozuoye.databinding.FragmentFirstBinding;
/**
 * 导航跳转页面
 * */
public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst1.setOnClickListener(view1 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_firstFragment_to_accountFragment));
        binding.buttonFirst2.setOnClickListener(view12 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_firstFragment_to_comeinRegisterFragment2));
        binding.buttonFirst3.setOnClickListener(view13 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_firstFragment_to_expensesRegisterFragment));
        binding.buttonFirst4.setOnClickListener(view14 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_firstFragment_to_comeinStatisticsFragment));
        binding.buttonFirst5.setOnClickListener(view15 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_firstFragment_to_expensesStatisticsFragment));
        binding.buttonFirst6.setOnClickListener(view16 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_firstFragment_to_selectFragment));
        binding.buttonFirst7.setOnClickListener(view17 -> {
            LoggedInUser.SignOut();
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_firstFragment_to_loginFragment);
        });

        // 关闭顶栏的返回键
        Toolbar toolBar = requireActivity().findViewById(R.id.toolbar);
        toolBar.setNavigationIcon(null);

        // 提示在FirstFragment页，返回键监听有变化
        DataTransfer.isOnFirstFragment = true;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 提示已经不再FirstFragment页
        DataTransfer.isOnFirstFragment = false;
        binding = null;
    }

}