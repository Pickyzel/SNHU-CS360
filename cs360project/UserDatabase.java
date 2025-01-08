package com.example.cs360project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int VERSION = 1;

    public UserDatabase(Context context) {super(context, DATABASE_NAME, null, VERSION);}

    private static final class UsersTable{
        private static final String TABLE = "users";
        private static final String usernames = "usernames";
        private static final String passwords = "passwords";
        private static final String sms = "sms";

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + UsersTable.TABLE + " (" +
                UsersTable.usernames + " text, " +
                UsersTable.passwords + " text," +
                UsersTable.sms + " text)");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + UserDatabase.UsersTable.TABLE);
        onCreate(db);
    }

    public void addUser(String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsersTable.usernames, username);
        values.put(UsersTable.passwords, password);
        values.put(UsersTable.sms, "N");
        db.insert(UsersTable.TABLE, null, values);
    }

    public int checkUser(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + UsersTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String checkUsername = cursor.getString(0);
                String checkPassword = cursor.getString(1);
                String checkSMS = cursor.getString(2);
                if (checkUsername.equals(username) && checkPassword.equals(password)){
                    cursor.close();
                    if (checkSMS.equals("N")){
                        return 3;
                    }
                    else if (checkSMS.equals("T")){
                        return 1;
                    }
                    else if (checkSMS.equals("F")){
                        return 2;
                    }

                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }

    public void AddNotificationPreference(boolean permission, String currentUserName){
        String preference;
        if(permission){
            preference = "T";
        }
        else {
            preference = "F";
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsersTable.sms, preference);
        db.update(UsersTable.TABLE, values, UsersTable.usernames + " = ?",
                new String[]{currentUserName});
    }
}
