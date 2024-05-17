package com.example.skyvoyage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FlightMySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "flightSQLite.db";
    private static final String TABLE_NAME_FLIGHT = "flight";
    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_FLIGHT +
            " (id integer primary key autoincrement, time text, duration text, route text, price text, airlineName text, airlineLogo integer, count integer)";

    public FlightMySQLiteOpenHelper(Context context) {
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

    public long insertData(Flight flight) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", flight.getTime());
        values.put("duration", flight.getDuration());
        values.put("route", flight.getRoute());
        values.put("price", flight.getPrice());
        values.put("airlineName", flight.getAirlineName());
        values.put("airlineLogo", flight.getAirlineLogo());
        values.put("count", flight.getCount());
        return db.insert(TABLE_NAME_FLIGHT, null, values);
    }

    public int deleteFromDbById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_FLIGHT, "id = ?", new String[]{String.valueOf(id)});
    }

    public int updateData(Flight flight) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", flight.getTime());
        values.put("duration", flight.getDuration());
        values.put("route", flight.getRoute());
        values.put("price", flight.getPrice());
        values.put("airlineName", flight.getAirlineName());
        values.put("airlineLogo", flight.getAirlineLogo());
        values.put("count", flight.getCount());
        return db.update(TABLE_NAME_FLIGHT, values, "id = ?", new String[]{String.valueOf(flight.getId())});
    }

    public List<Flight> queryFromDbById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Flight> flightList = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME_FLIGHT,
                new String[]{"id", "time", "duration", "route", "price", "airlineName", "airlineLogo", "count"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int flightId = cursor.getInt(cursor.getColumnIndex("id"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String duration = cursor.getString(cursor.getColumnIndex("duration"));
                String route = cursor.getString(cursor.getColumnIndex("route"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String airlineName = cursor.getString(cursor.getColumnIndex("airlineName"));
                int airlineLogo = cursor.getInt(cursor.getColumnIndex("airlineLogo"));
                int count = cursor.getInt(cursor.getColumnIndex("count"));

                Flight flight = new Flight(flightId, time, duration, route, price, airlineName, airlineLogo, count);
                flightList.add(flight);
            }
            cursor.close();
        }
        return flightList;
    }

    public List<Flight> queryAllFromDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Flight> flightList = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME_FLIGHT,
                new String[]{"id", "time", "duration", "route", "price", "airlineName", "airlineLogo", "count"},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int flightId = cursor.getInt(cursor.getColumnIndex("id"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String duration = cursor.getString(cursor.getColumnIndex("duration"));
                String route = cursor.getString(cursor.getColumnIndex("route"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String airlineName = cursor.getString(cursor.getColumnIndex("airlineName"));
                int airlineLogo = cursor.getInt(cursor.getColumnIndex("airlineLogo"));
                int count = cursor.getInt(cursor.getColumnIndex("count"));

                Flight flight = new Flight(flightId, time, duration, route, price, airlineName, airlineLogo, count);
                flightList.add(flight);
            }
            cursor.close();
        }
        return flightList;
    }
}
