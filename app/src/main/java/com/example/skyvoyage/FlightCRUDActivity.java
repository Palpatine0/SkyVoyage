package com.example.skyvoyage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class FlightCRUDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_crud);
    }

    public void insertData(View view) {
        Intent intent = new Intent(this, FlightInsertActivity.class);
        startActivity(intent);
    }

    public void updateData(View view) {
        Intent intent = new Intent(this, FlightUpdateActivity.class);
        startActivity(intent);
    }

    public void queryData(View view) {
        Intent intent = new Intent(this, FlightQueryActivity.class);
        startActivity(intent);
    }

    public void deleteData(View view) {
        Intent intent = new Intent(this, FlightDeleteActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
