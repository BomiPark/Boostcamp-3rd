package project.android.mini_project3.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by qkrqh on 2017-07-19.
 */

public class ItemLab {

    private static ItemLab itemLab;
    private Context context;
    private SQLiteDatabase database;

    private ArrayList<Item> items;

    public static ItemLab get(Context context){
        itemLab = new ItemLab(context);
        return itemLab;
    }

    private ItemLab(Context context){

        this.context = context.getApplicationContext();
    }

    public int addItem(Item item){

        database = new DBHelper(context).getWritableDatabase();

        ContentValues values = getContentValues(item);
        long id = database.insert(DBHelper.TABLE_NAME, null, values);

        database.close();

        return (int)id;
    }

    public void updateItem(Item item){

        database = new DBHelper(context).getWritableDatabase();

        ContentValues values = getContentValues(item);
        String whereClause = "_id=?";
        String[] whereArgs = new String[] {item.get_id()+""};

        database.update(DBHelper.TABLE_NAME, values, whereClause, whereArgs);

        database.close();
    }

    public ArrayList<Item> getItems(){

        database = new DBHelper(context).getWritableDatabase();

        items = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from " + DBHelper.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.set_id(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setPhone(cursor.getString(2));
            item.setAddress(cursor.getString(3));
            item.setExplain(cursor.getString(4));
            item.setLatitude(cursor.getDouble(5));
            item.setLongitude(cursor.getDouble(6));
            items.add(item);
        }

        cursor.close();
        database.close();

        return items;
    }

    public Item getItem(int _id){

        database = new DBHelper(context).getWritableDatabase();

        Item item = new Item();

        Cursor cursor = database.rawQuery("select * from " + DBHelper.TABLE_NAME + " where _id = " + _id, null);
        while (cursor.moveToNext()) {
            item.set_id(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setPhone(cursor.getString(2));
            item.setAddress(cursor.getString(3));
            item.setExplain(cursor.getString(4));
            item.setLatitude(cursor.getDouble(5));
            item.setLongitude(cursor.getDouble(6));
        }

        cursor.close();
        database.close();

        return item;
    }

    private static ContentValues getContentValues(Item item){

        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("phone", item.getPhone());
        values.put("address", item.getAddress());
        values.put("explain", item.getExplain());
        values.put("latitude", item.getLatitude());
        values.put("longitude", item.getLongitude());

        return values;
    }
}
