package com.assignment.project3_tiffanymcdonnell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "InventoryDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_USERS = "users";


    private static final String NAME = "id";
    private static final String ITEM_NAME = "product_id";
    private static final String ITEM_DESC = "product_de" +
            "sc";
    private static final String COUNT = "count";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PHONE = "phone";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + NAME + " INTEGER PRIMARY KEY,"
                + ITEM_NAME + " TEXT,"
                + ITEM_DESC + " TEXT,"
                + COUNT + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USERNAME + " TEXT PRIMARY KEY,"
                + PASSWORD + " TEXT,"
                + PHONE + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }



    // CRUD operations (create, read, update, delete)
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, item.getItemName());
        values.put(ITEM_DESC, item.getItemDesc());
        values.put(COUNT, item.getCount());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public Item getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS, new String[] {NAME,
                        ITEM_NAME, ITEM_DESC, COUNT}, NAME + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item product = new Item(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3));
        cursor.close();

        return product;
    }



    public int updateProduct(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, item.getItemName());
        values.put(ITEM_DESC, item.getItemDesc());
        values.put(COUNT, item.getCount());

        // Updating item by product_id
        return db.update(TABLE_PRODUCTS, values, ITEM_NAME + " = ?",
                new String[]{String.valueOf(item.getItemName())});
    }
    public boolean ItemExists(String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS,
                new String[]{ITEM_NAME},
                ITEM_NAME + "=?",
                new String[]{itemName}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public List<Item> getList() {
        List<Item> itemList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int itemIndex = cursor.getColumnIndex(ITEM_NAME);
                int itemDesIndex = cursor.getColumnIndex(ITEM_DESC);
                int countIndex = cursor.getColumnIndex(COUNT);

                if (itemIndex != -1 && itemDesIndex != -1 && countIndex != -1) {
                    String itemName = cursor.getString(itemIndex);
                    String itemDes = cursor.getString(itemDesIndex);
                    int count = cursor.getInt(countIndex);

                    Item item = new Item(itemName, itemDes, count);
                    itemList.add(item);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();


        return itemList;
    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, ITEM_NAME + " = ?", new String[] { item.getItemName() });
        db.close();
    }

    public void addUser(String username, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(PASSWORD, password);
        values.put(PHONE, phone);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }



    public boolean checkPhoneExists(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{PHONE},
                PHONE + "=?", new String[]{phone}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }


    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {USERNAME, PASSWORD},
                USERNAME + "=?", new String[] { username }, null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex(PASSWORD));
            cursor.close();
            return password.equals(storedPassword);
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }
    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{USERNAME},
                USERNAME + "=?", new String[]{username}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }
}