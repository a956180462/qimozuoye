package cna.self.qimozuoye.data;

import cna.self.qimozuoye.data.UserDataBase.DBase;
import cna.self.qimozuoye.data.UserDataBase.User;
import cna.self.qimozuoye.data.model.LoggedInUser;

/**
 * 登录页面附属类
 * 用于查验账户
 * 返回：Result类型，成功则为.Success，失败则为.Error
 */
public class LoginDataSource {
    DBase db = DataBaseHolder.getDb();
    User user;
    public Result<LoggedInUser> login(String username, String password) {
        user = db.userDAO().getUser(username,password);
        if (user == null){
            return new Result.Error(new Exception("账户或密码错误"));
        }else {
            LoggedInUser fakeUser = new LoggedInUser(user.getAccount());
            return new Result.Success<>(fakeUser);
        }

    }
}