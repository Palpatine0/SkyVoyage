package com.example.skyvoyage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class FlightUpdateActivity extends AppCompatActivity {
    private EditText etId, etTime, etDuration, etRoute, etPrice, etAirlineName, etCount;
    private Spinner spAirlineLogo;
    private FlightMySQLiteOpenHelper flightMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_update);
        initView();
        flightMySQLiteOpenHelper = new FlightMySQLiteOpenHelper(this);
    }

    private void initView() {
        etId = findViewById(R.id.et_id);
        etTime = findViewById(R.id.et_time);
        etDuration = findViewById(R.id.et_duration);
        etRoute = findViewById(R.id.et_route);
        etPrice = findViewById(R.id.et_price);
        etAirlineName = findViewById(R.id.et_airline_name);
        spAirlineLogo = findViewById(R.id.sp_airline_logo);
        etCount = findViewById(R.id.et_count);
    }

    public void updateData(View view) {
        int id = Integer.parseInt(etId.getText().toString().trim());
        String time = etTime.getText().toString().trim();
        String duration = etDuration.getText().toString().trim();
        String route = etRoute.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String airlineName = etAirlineName.getText().toString().trim();
        int count = Integer.parseInt(etCount.getText().toString().trim());

        if (time.isEmpty() || duration.isEmpty() || route.isEmpty() || price.isEmpty() || airlineName.isEmpty()) {
            ToastUtil.toastShort(this, "Please fill in all fields!");
            return;
        }

        int airlineLogo;
        String selectedLogo = spAirlineLogo.getSelectedItem().toString();
        switch (selectedLogo) {
            case "Frontier Airlines":
                airlineLogo = ImageConstants.FRONTIER_AIRLINES_LOGO;
                break;
            case "American Airlines":
                airlineLogo = ImageConstants.AMERICAN_AIRLINES_LOGO;
                break;
            default:
                airlineLogo = 0; // Default logo or handle error
                break;
        }

        Flight flight = new Flight(id, time, duration, route, price, airlineName, airlineLogo, count);

        // Update data in the database
        int rowsAffected = flightMySQLiteOpenHelper.updateData(flight);
        if (rowsAffected > 0) {
            ToastUtil.toastShort(this, "Done update!");
        } else {
            ToastUtil.toastShort(this, "Failed update!");
        }
    }
}
