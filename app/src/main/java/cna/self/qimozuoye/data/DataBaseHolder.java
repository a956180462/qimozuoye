package cna.self.qimozuoye.data;

import android.content.Context;

import cna.self.qimozuoye.data.UserDataBase.DBase;

public class DataBaseHolder {
    private static DBase db;
    public static void RegDB(Context context){
        db = DBase.getDataBase(context);
    }
    public static DBase getDb() {
        return db;
    }
}
