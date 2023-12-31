package cna.self.qimozuoye.data.UserDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ComeIn.class,User.class,Expenses.class},
        version = 1,
        exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DBase extends RoomDatabase {


    public abstract UserDAO userDAO();
    private static volatile DBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    // 创建了线程池，可以用databaseWriteExecutor.execute(()->{});创建新线程
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DBase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DBase.class, "DBase")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
