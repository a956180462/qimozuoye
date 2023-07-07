package cna.self.qimozuoye.data.UserDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertUser(User user);

    @Insert
    void insertComeIn(ComeIn comeIn);

    @Insert
    void insertExpense(Expenses expenses);

    @Delete
    void delComeIn(ComeIn comeIn);

    @Delete
    void delExpense(Expenses expenses);

    @Query("SELECT * FROM ComeIn WHERE Account = :account ORDER BY Date ASC")
    List<ComeIn> getComeIns(String account);

    @Query("SELECT * FROM Expenses WHERE Account = :account ORDER BY Date ASC")
    List<Expenses> getExpenses(String account);

    @Query("SELECT * FROM User WHERE Account = :account and Password = :pass")
    User getUser(String account, String pass);

    @Query("SELECT SUM(Money) FROM ComeIn WHERE Account = :account")
    Float TotalComeInMoney(String account);

    @Query("SELECT SUM(Money) FROM Expenses WHERE Account = :account")
    Float TotalExpensesMoney(String account);
}
