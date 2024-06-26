package com.example.skyvoyage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class UserMySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "userSQLite.db";
    private static final String TABLE_NAME_USER = "user";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME_USER +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";

    public UserMySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Code to upgrade database version and migrate data
    }

    public long insertUserData(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        return db.insert(TABLE_NAME_USER, null, values);
    }

    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME_USER,
                new String[]{"id"},
                "username = ? AND password = ?",
                new String[]{username, password},
                null,
                null,
                null
        );

        boolean isValid = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return isValid;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_USER,
                new String[]{"id"},
                "username = ?",
                new String[]{username},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public int updateUserData(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        return db.update(TABLE_NAME_USER, values, "username = ?", new String[]{user.getUsername()});
    }

    public int deleteUserByUsername(String username) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_USER, "username = ?", new String[]{username});
    }

    public List<User> queryAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<User> userList = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME_USER,
                new String[]{"username", "password"},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));

                User user = new User(username, password);
                userList.add(user);
            }
            cursor.close();
        }
        return userList;
    }

    public List<User> queryUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<User> userList = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME_USER,
                new String[]{"username", "password"},
                "username = ?",
                new String[]{username},
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String userUsername = cursor.getString(cursor.getColumnIndex("username"));
                String userPassword = cursor.getString(cursor.getColumnIndex("password"));

                User user = new User(userUsername, userPassword);
                userList.add(user);
            }
            cursor.close();
        }
        return userList;
    }


}
