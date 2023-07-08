package cna.self.qimozuoye.data;

import android.content.Context;

import cna.self.qimozuoye.data.UserDataBase.DBase;

/**
 * 数据库持有者，全静态变量，开启程序时在MainActivity获取新的数据库
 * 在Fragment中使用DataBaseHolder.getDb()获取数据库对象
 * */
public class DataBaseHolder {
    private static DBase db;
    // 连接数据库，申请数据库对象并持有
    public static void RegDB(Context context){
        db = DBase.getDataBase(context);
    }
    public static DBase getDb() {
        return db;
    }
}
