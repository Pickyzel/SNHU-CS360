package com.example.cs360project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.Vector;

public class ItemDatabase extends SQLiteOpenHelper implements Serializable {

    private static final String DATABASE_NAME = "items.db";
    private static final int VERSION = 1;

    public ItemDatabase(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class ItemTable implements Serializable {

        private static final String TABLE = "items";
        private static final String name = "name";
        private static final String onHand = "onHand";
        private static final String price = "price";
        private static final String location = "location";
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL("create table " + ItemTable.TABLE + " (" +
                ItemTable.name + " text, " +
                ItemTable.onHand + " text, " +
                ItemTable.price + " text, " +
                ItemTable.location + " text)");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + ItemTable.TABLE);
        onCreate(db);
    }

    //Method called to populate the item list from the stored database
    public Vector<Item> populateList(){
        String itemName, itemPrice, itemLocation;
        String itemOnHand;
        Item currentItem;
        Vector<Item> itemList = new Vector<Item>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + ItemTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do{
                itemName = cursor.getString(0);
                itemOnHand = cursor.getString(1);
                itemPrice = cursor.getString(2);
                itemLocation = cursor.getString(3);
                currentItem = new Item(itemName, itemOnHand, itemPrice, itemLocation);
                itemList.add(currentItem);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    public void AddItem(String name, String onHand, String price, String location){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemTable.name, name);
        values.put(ItemTable.onHand, onHand);
        values.put(ItemTable.price, price);
        values.put(ItemTable.location, location);
        db.insert(ItemTable.TABLE, null, values);
    }

    public void DeleteItem(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ItemTable.TABLE, ItemTable.name + " = ?", new String[]{name});
    }

    //updates all but the name field
    public void UpdateItem(String oldName, String newName, String onHand, String price, String location){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemTable.name, newName);
        values.put(ItemTable.onHand, onHand);
        values.put(ItemTable.price, price);
        values.put(ItemTable.location, location);
        db.update(ItemTable.TABLE, values, ItemTable.name +
                " = ?", new String[]{oldName});
    }

    public String LowInventoryItems(){
        SQLiteDatabase db = getReadableDatabase();
        String currentName, allItems="";
        String sql = "select " + ItemTable.name + " from " + ItemTable.TABLE + " where " + ItemTable.onHand + "< 5";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do{
                allItems += cursor.getString(0);
            }while(cursor.moveToNext());
        }
        return allItems;
    }
}
