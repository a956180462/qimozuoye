package cna.self.qimozuoye.data.UserDataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = "Account")
public class User {

    public User(){}


    @NonNull
    @ColumnInfo(name = "Account")
    private String Account;

    @NonNull
    @ColumnInfo(name = "Password")
    private String Password;


//--------getter------------------------


    @NonNull
    public String getAccount() {
        return Account;
    }

    @NonNull
    public String getPassword() {
        return Password;
    }
//---------setter---------------
    public void setAccount(@NonNull String account) {
        Account = account;
    }

    public void setPassword(@NonNull String password) {
        Password = password;
    }


}
