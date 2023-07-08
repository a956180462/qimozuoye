package cna.self.qimozuoye.data.model;

import java.io.Serializable;

/**
 * 已经登录的用户信息
 * 包含 String Account
 */
public class LoggedInUser {

    /**account信息，静态变量，防止对象回收时数据丢失*/
    private static String account = null;

    public LoggedInUser(String account) {
        LoggedInUser.account = account;
    }

    /**获取账户信息，静态方法方便调用*/
    public static String getAccount() {
        return account;
    }

    /**推出登录*/
    public static void SignOut(){
        account = null;
    }
}