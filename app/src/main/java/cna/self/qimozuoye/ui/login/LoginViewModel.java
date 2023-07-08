package cna.self.qimozuoye.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import cna.self.qimozuoye.data.LoginRepository;
import cna.self.qimozuoye.data.Result;
import cna.self.qimozuoye.data.model.LoggedInUser;
import cna.self.qimozuoye.R;

/**
 * 文本框输入有效性查验、登录请求发起
 *
 * */

public class LoginViewModel extends ViewModel {
    // 注意MutableLiveData，这是活动数据，当loginFormState发生改变时
    // 我们可以用loginFormState.observe();来触发事件
    // 很像一个中断
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final LoginRepository loginRepository;



    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

//    发起登录请求，获取Result，
    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            loginResult.setValue(new LoginResult(new LoggedInUserView(LoggedInUser.getAccount())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }


//      输入有效性结果设置
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // 用户名有效性判断
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            //邮箱类用户名识别
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // 密码有效性判断
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}