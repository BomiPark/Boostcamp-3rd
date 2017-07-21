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

        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '스타벅스 판교점', '031-8018-4436', '경기도 성남시 분당구 분당구 삼평동 680' , '오전10:00~ 오후11:00', " + 37.4015984 +", "  + 127.1087813 +" );");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '공차 판교점', '031-628-6050', '경기도 성남시 분당구 분당구 삼평동 682' , '오전9:00~ 오후10:00', " + 37.4019670 +", "  + 127.1068350 +" );");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '스타벅스 수원성대점', '031-292-7571', '경기도 수원시 장안구 장안구 율전동 285-6' , '오전7:00~ 오후11:00', " + 37.2989280 +", "  + 126.9715830 +" );");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '투썸플레이스 강남삼성타운점', '02-522-2389', '서울특별시 서초구 서초동 1320' , '오전10:00~ 오후8:00', " + 37.4971890 +", "  + 127.0261890 +" );");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '매드포갈릭 판교점', '031-703-8920', '경기도 성남시 분당구 대왕판교로 606번길 10 라스트리트 1동 219호' , '오전11:00~ 오후10:00', " + 37.3942620 +", "  + 127.1082890 +" );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
