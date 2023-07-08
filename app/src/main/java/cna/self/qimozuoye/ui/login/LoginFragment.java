package cna.self.qimozuoye.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import cna.self.qimozuoye.DataTransfer;
import cna.self.qimozuoye.MainHandler;
import cna.self.qimozuoye.R;
import cna.self.qimozuoye.data.DataBaseHolder;
import cna.self.qimozuoye.data.UserDataBase.DBase;
import cna.self.qimozuoye.data.UserDataBase.User;
import cna.self.qimozuoye.databinding.FragmentLoginBinding;

/**
 * 登录页
 * */
public class LoginFragment extends Fragment implements MainHandler {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private boolean State = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 获取一个非空的LoginViewModel
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        // 全局提醒：在Login页面，返回事件发生改变（MainActivity,onBackPressed()）
        DataTransfer.isOnLoginFragment = true;

        // 获取控件信息
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button regButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;

        // 禁止登录注册空间使能（文本信息未查验）
        loginButton.setEnabled(false);
        regButton.setEnabled(false);

        // loginViewModel.getLoginFormState()返回了一个MutableLiveData
        // 详情请看LoginViewModel的注释！
        // 输入合法性查询
        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            // 查看输入是否合法，若是，则登录注册使能
            loginButton.setEnabled(loginFormState.isDataValid());
            regButton.setEnabled(loginFormState.isDataValid());
            State = loginFormState.isDataValid();

            // 若否，设置错误信息
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        // loginViewModel.getLoginResult()返回了一个MutableLiveData
        // 详情请看LoginViewModel的注释！
        // 登录结果查询
        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);

            // 失败，显示失败信息
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            // 成功，跳转到登录成功事务处理
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
        });

        // EditText 可以添加一个TextWatcher，顾名思义
        // 这是用来监控文本框中文本变化的
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            // 文本发生改变后，进行输入合法性检查
            // 当合法性状态改变时(合法->非法或非法->合法)
            // loginViewModel中的loginResult发生变化
            // 触发上面的loginViewModel.getLoginFormState().observe()
            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        // EditText加入文本监视器
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        // 密码框对回车键的监听
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if(State){
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
            }
            return false;
        });

        // 登录键监听
        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });

        // 注册键键监听
        regButton.setOnClickListener(v -> {
            User user = new User();
            user.setAccount(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            Context context = requireContext().getApplicationContext();
            // 写入操作，后台线程中进行
            DBase.databaseWriteExecutor.execute(() -> {
                long affRows = DataBaseHolder.
                                getDb().
                                userDAO().
                                insertUser(user);
                Log.e("REG", "onViewCreated: " + affRows);
                // 使用handler处理结果
                Message msg = handler.obtainMessage(REG_RESULT, (int) affRows,0,context);
                handler.sendMessage(msg);
            });
        });
    }

    // 登录成功事务处理
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        if (getContext() != null
                && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_SHORT).show();
            // 页面跳转
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_loginFragment_to_firstFragment);
        }


    }

    // 登录失败事务处理
    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_SHORT).show();
        }
    }

    // 登录页面结束，isOnLoginFragment应该为否
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataTransfer.isOnLoginFragment = false;
        binding = null;
    }
}