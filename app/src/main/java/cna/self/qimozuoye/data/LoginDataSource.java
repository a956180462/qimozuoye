package cna.self.qimozuoye.data;

import cna.self.qimozuoye.data.UserDataBase.DBase;
import cna.self.qimozuoye.data.UserDataBase.User;
import cna.self.qimozuoye.data.model.LoggedInUser;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
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