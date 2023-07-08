package cna.self.qimozuoye.data.UserDataBase;

import androidx.room.TypeConverter;

import java.util.Date;
/**
 * 转换器，用做数据库中日期的存储
 * */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
