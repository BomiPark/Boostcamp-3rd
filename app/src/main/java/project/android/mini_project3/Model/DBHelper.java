package project.android.mini_project3.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qkrqh on 2017-07-17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db";
    public static final String TABLE_NAME = "table_item";

    public DBHelper(Context context){
        super(context, DB_NAME, null, 1); //데이터베이스 파일 생성
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "create table " + TABLE_NAME + " ( _id integer primary key autoincrement, name text, phone text, address text, explain text, latitude double, longitude double);";
        sqLiteDatabase.execSQL(createTable);

        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '스타벅스 판교점', '031-8018-4436', '경기도 성남시 분당구 분당구 삼평동 680' , '넓당', " + 37.4015984 +", "  + 127.1087813 +" );");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '공차 판교점', '031-628-6050', '경기도 성남시 분당구 분당구 삼평동 682' , '좁당', " + 37.4019670 +", "  + 127.1068350 +" );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
