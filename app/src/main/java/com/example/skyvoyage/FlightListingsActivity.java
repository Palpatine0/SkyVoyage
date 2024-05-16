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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_listings);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        flightList = new ArrayList<>();
        flightAdapter = new FlightAdapter(flightList);
        recyclerView.setAdapter(flightAdapter);

        // Populate the flight list
        populateFlightList();
    }

    private void populateFlightList() {
        flightList.add(new Flight("2:57pm - 7:07pm", "3h 10m (Nonstop)", "Dallas (DFW) - Baltimore (BWI)", "$160", "Frontier Airlines",R.drawable.frontier_airline));
        flightList.add(new Flight("7:30am - 11:34am", "3h 4m (Nonstop)", "Dallas (DFW) - Washington (IAD)", "$208", "American Airlines",R.drawable.american_airlines));
        flightList.add(new Flight("2:57pm - 7:07pm", "3h 10m (Nonstop)", "Dallas (DFW) - Baltimore (BWI)", "$160", "Frontier Airlines",R.drawable.frontier_airline));
        flightList.add(new Flight("7:30am - 11:34am", "3h 4m (Nonstop)", "Dallas (DFW) - Washington (IAD)", "$208", "American Airlines",R.drawable.american_airlines));
        flightList.add(new Flight("7:30am - 11:34am", "3h 4m (Nonstop)", "Dallas (DFW) - Washington (IAD)", "$208", "American Airlines",R.drawable.american_airlines));
        flightList.add(new Flight("7:30am - 11:34am", "3h 4m (Nonstop)", "Dallas (DFW) - Washington (IAD)", "$208", "American Airlines",R.drawable.american_airlines));
        // Add more flights as needed
        flightAdapter.notifyDataSetChanged();
    }
}
