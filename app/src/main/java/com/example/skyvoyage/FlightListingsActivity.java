package com.example.skyvoyage;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FlightListingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlightAdapter flightAdapter;
    private List<Flight> flightList;
    private FlightMySQLiteOpenHelper flightMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_listings);

        flightMySQLiteOpenHelper = new FlightMySQLiteOpenHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        flightList = new ArrayList<>();
        flightAdapter = new FlightAdapter(this, flightList);
        recyclerView.setAdapter(flightAdapter);

        // Populate the flight list from the database
        populateFlightListFromDb();
    }

    private void populateFlightListFromDb() {
        flightList.clear();
        flightList.addAll(flightMySQLiteOpenHelper.queryAllFromDb());
        flightAdapter.notifyDataSetChanged();
    }
}
