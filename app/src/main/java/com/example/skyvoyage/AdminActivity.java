package com.example.skyvoyage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void btnFlightManage(View view) {
        Intent intent = new Intent(this, FlightManageActivity.class);
        startActivity(intent);
    }

    public void btnUserManage(View view) {
        Intent intent = new Intent(this, UserManageActivity.class);
        startActivity(intent);
    }

    public void btnLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void btnListing(View view) {
        Intent intent = new Intent(this, FlightListingsActivity.class);
        startActivity(intent);
    }
}
