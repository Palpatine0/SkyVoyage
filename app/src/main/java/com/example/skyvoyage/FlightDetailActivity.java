package com.example.skyvoyage;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FlightDetailActivity extends AppCompatActivity {

    private TextView tvFlightTime, tvFlightDuration, tvFlightRoute, tvFlightPrice, tvAirlineName, tvCO2;
    private ImageView ivAirlineLogo;
    private FlightMySQLiteOpenHelper flightMySQLiteOpenHelper;
    private int flightId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);

        // Initialize views
        tvFlightTime = findViewById(R.id.tvFlightTime);
        tvFlightDuration = findViewById(R.id.tvFlightDuration);
        tvFlightRoute = findViewById(R.id.tvFlightRoute);
        tvFlightPrice = findViewById(R.id.tvPriceStandard);
        tvAirlineName = findViewById(R.id.tvAirlineName);
        ivAirlineLogo = findViewById(R.id.ivAirlineLogo);
        tvCO2 = findViewById(R.id.tvCO2);

        flightMySQLiteOpenHelper = new FlightMySQLiteOpenHelper(this);

        // Get flight ID from Intent
        flightId = getIntent().getIntExtra("FLIGHT_ID", -1);

        // Load flight details from the database
        loadFlightDetails(flightId);
    }

    private void loadFlightDetails(int id) {
        Flight flight = flightMySQLiteOpenHelper.queryFromDbById(id).get(0);

        if (flight != null) {
            tvFlightTime.setText(flight.getTime());
            tvFlightDuration.setText(flight.getDuration());
            tvFlightRoute.setText(flight.getRoute());
            tvFlightPrice.setText(flight.getPrice());
            tvAirlineName.setText(flight.getAirlineName());
            ivAirlineLogo.setImageResource(flight.getAirlineLogo());
            // Set other details as necessary
        } else {
            // Handle the case when the flight is not found
            tvFlightTime.setText("Flight not found");
        }
    }
}
