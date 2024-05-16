package com.example.skyvoyage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mySQLite.db";
    private static final String TABLE_NAME_USER = "user";
    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_USER +
            " (id integer primary key autoincrement, username text,password text)";

    public MySQLiteOpenHelper(Context context) {
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

    public long insertData(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        return db.insert(TABLE_NAME_USER, null, values);
    }

    public int deleteFromDbByName(String name) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_USER, "name = ?", new String[]{name});
    }

    /*public int updateData(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("number", student.getNumber());
        values.put("gender", student.getGender());
        values.put("score", student.getScore());
        return db.update(TABLE_NAME_USER, values, "name = ?", new String[]{student.getName()});
    }*/

    /*public List<Student> queryFromDbByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Student> studentList = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME_USER, // Corrected
                new String[]{"name", "number", "gender", "score"},
                "name LIKE ?",
                new String[]{name},
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name1 = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));
                String score = cursor.getString(cursor.getColumnIndex("score"));

                Student student = new Student();
                student.setName(name1);
                student.setNumber(number);
                student.setGender(gender);
                student.setScore(score);
                studentList.add(student);
            }
            cursor.close();
        }
        return studentList;
    }*/

    /*public List<Student> queryAllFromDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Student> studentList = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME_USER, // Corrected
                new String[]{"name", "number", "gender", "score"},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name1 = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));
                String score = cursor.getString(cursor.getColumnIndex("score"));

                Student student = new Student();
                student.setName(name1);
                student.setNumber(number);
                student.setGender(gender);
                student.setScore(score);
                studentList.add(student);
            }
            cursor.close();
        }
        return studentList;
    }*/



}
