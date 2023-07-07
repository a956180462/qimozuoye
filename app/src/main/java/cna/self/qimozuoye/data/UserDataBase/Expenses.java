package cna.self.qimozuoye.data.UserDataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Expenses {

    @PrimaryKey(autoGenerate = true)
    int ID;

    @NonNull
    @ColumnInfo(name = "Account")
    String Account;

    @NonNull
    @ColumnInfo(name = "Date")
    Date mDate;

    @NonNull
    @ColumnInfo(name = "Money")
    Float Money;

    @ColumnInfo(name = "Remark")
    String Remark;

//----------getter---------------

    @NonNull
    public String getAccount() {
        return Account;
    }

    @NonNull
    public Date getDate() {
        return mDate;
    }

    @NonNull
    public Float getMoney() {
        return Money;
    }

    public String getRemark() {
        return Remark;
    }

//------------setter-------------


    public void setAccount(@NonNull String account) {
        Account = account;
    }

    public void setMoney(@NonNull Float money) {
        Money = money;
    }

    public void setDate(@NonNull Date date) {
        mDate = date;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
