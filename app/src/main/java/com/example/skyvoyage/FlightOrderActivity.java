package com.example.skyvoyage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class FlightOrderActivity extends AppCompatActivity {

    private TextView tvFlightTime, tvFlightDuration, tvFlightRoute, tvTotalPrice, tvInsuranceCost, tvFlightSummary;
    private EditText etPassengerName, etPassengerID, etContactNumber;
    private CheckBox cbTravelInsurance;
    private Button btnProceedToCheckout, btnSelectInsurance;
    private FlightMySQLiteOpenHelper flightMySQLiteOpenHelper;
    private Flight flight;
    private double totalPrice;
    private double insuranceCost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_order);

        initView();

        flightMySQLiteOpenHelper = new FlightMySQLiteOpenHelper(this);
        int flightId = getIntent().getIntExtra("FLIGHT_ID", -1);

        List<Flight> flights = flightMySQLiteOpenHelper.queryFromDbById(flightId);
        if (flights != null && !flights.isEmpty()) {
            flight = flights.get(0);
            populateFlightDetails();
        } else {
            Toast.makeText(this, "Flight not found", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if flight not found
        }
    }

    private void initView() {
        tvFlightSummary = findViewById(R.id.tvFlightSummary);
        tvFlightTime = findViewById(R.id.tvFlightTime);
        tvFlightDuration = findViewById(R.id.tvFlightDuration);
        tvFlightRoute = findViewById(R.id.tvFlightRoute);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvInsuranceCost = findViewById(R.id.tvInsuranceCost);
        etPassengerName = findViewById(R.id.etPassengerName);
        etPassengerID = findViewById(R.id.etPassengerID);
        etContactNumber = findViewById(R.id.etContactNumber);
        cbTravelInsurance = findViewById(R.id.cbTravelInsurance);
        btnProceedToCheckout = findViewById(R.id.btnProceedToCheckout);
        btnSelectInsurance = findViewById(R.id.btnSelectInsurance);

        btnProceedToCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(FlightOrderActivity.this, FlightPaymentActivity.class);
            intent.putExtra("FLIGHT_ID", flight.getId());  // Pass flight ID
            startActivity(intent);
        });

    }

    private void populateFlightDetails() {
        if (flight != null) {
            tvFlightTime.setText(flight.getTime());
            tvFlightDuration.setText(flight.getDuration());
            tvFlightRoute.setText(flight.getRoute());
            totalPrice = Double.parseDouble(flight.getPrice().replace("$", ""));
            tvTotalPrice.setText("$" + totalPrice);
        }

        cbTravelInsurance.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showInsuranceDialog();
            } else {
                totalPrice -= insuranceCost;
                insuranceCost = 0;
                updateTotalPrice();
            }
        });
    }

    private void showInsuranceDialog() {
        final String[] insuranceTypes = {"Basic - $20", "Premium - $50"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Travel Insurance")
                .setItems(insuranceTypes, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            insuranceCost = 20;
                            break;
                        case 1:
                            insuranceCost = 50;
                            break;
                    }
                    totalPrice += insuranceCost;
                    tvInsuranceCost.setText("Insurance Cost: $" + insuranceCost);
                    updateTotalPrice();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    cbTravelInsurance.setChecked(false);
                    dialog.dismiss();
                });
        builder.create().show();
    }

    private void updateTotalPrice() {
        tvTotalPrice.setText("$" + totalPrice);
    }
}
