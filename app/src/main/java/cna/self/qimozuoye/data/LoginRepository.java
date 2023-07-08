package cna.self.qimozuoye.data;

import cna.self.qimozuoye.data.model.LoggedInUser;

/**
 * 在login查验数据库之前封装了一次
 * 是查验数据库的代理类
 * 可以在后期做拓展用
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private final LoginDataSource dataSource;

    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 获取LoginRepository对象，保障了全局只获得了一个LoginRepository对象
    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public Result<LoggedInUser> login(String username, String password) {
        //
        return dataSource.login(username, password);
    }
}