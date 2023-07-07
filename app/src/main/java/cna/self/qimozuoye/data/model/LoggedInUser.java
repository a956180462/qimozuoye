package cna.self.qimozuoye.data.model;

import java.io.Serializable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private static String account = null;

    public LoggedInUser(String account) {
        LoggedInUser.account = account;
    }


    public static String getAccount() {
        return account;
    }

    public static void SignOut(){
        account = null;
    }
}