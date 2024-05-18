package com.example.skyvoyage;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FlightInsertActivity extends AppCompatActivity {
    private EditText etId, etDepartureTime, etArrivalTime, etDuration, etRoute, etPrice, etAirlineName, etCount;
    private Spinner spAirlineLogo;
    private FlightMySQLiteOpenHelper flightMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_insert);
        initView();
        flightMySQLiteOpenHelper = new FlightMySQLiteOpenHelper(this);

        spAirlineLogo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLogo = spAirlineLogo.getSelectedItem().toString();
                etAirlineName.setText(selectedLogo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                etAirlineName.setText("");
            }
        });
    }

    private void initView() {
        etId = findViewById(R.id.et_id);
        etDepartureTime = findViewById(R.id.et_departure_time);
        etArrivalTime = findViewById(R.id.et_arrival_time);
        etDuration = findViewById(R.id.et_duration);
        etRoute = findViewById(R.id.et_route);
        etPrice = findViewById(R.id.et_price);
        etAirlineName = findViewById(R.id.et_airline_name);
        spAirlineLogo = findViewById(R.id.sp_airline_logo);
        etCount = findViewById(R.id.et_count);

        Button btnSelectDepartureTime = findViewById(R.id.btn_select_departure_time);
        Button btnSelectArrivalTime = findViewById(R.id.btn_select_arrival_time);

        btnSelectDepartureTime.setOnClickListener(v -> showTimePickerDialog(etDepartureTime));
        btnSelectArrivalTime.setOnClickListener(v -> showTimePickerDialog(etArrivalTime));
    }

    private void showTimePickerDialog(EditText timeField) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minuteOfHour) -> {
            String time = String.format("%02d:%02d", hourOfDay, minuteOfHour);
            timeField.setText(time);
            calculateDuration();
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void calculateDuration() {
        String departureTime = etDepartureTime.getText().toString().trim();
        String arrivalTime = etArrivalTime.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        if (!departureTime.isEmpty() && !arrivalTime.isEmpty()) {
            try {
                Date departureDate = sdf.parse(departureTime);
                Date arrivalDate = sdf.parse(arrivalTime);

                long durationMillis = arrivalDate.getTime() - departureDate.getTime();
                if (durationMillis < 0) {
                    durationMillis += 24 * 60 * 60 * 1000; // add 24 hours if the time crosses midnight
                }

                long hours = durationMillis / (60 * 60 * 1000);
                long minutes = (durationMillis / (60 * 1000)) % 60;

                String duration = String.format("%dh %02dm", hours, minutes);
                etDuration.setText(duration);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertData(View view) {
        String idStr = etId.getText().toString().trim();
        String time = etDepartureTime.getText().toString().trim() + " - " + etArrivalTime.getText().toString().trim();
        String duration = etDuration.getText().toString().trim();
        String route = etRoute.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String airlineName = etAirlineName.getText().toString().trim();
        int count = Integer.parseInt(etCount.getText().toString().trim());

        if (idStr.isEmpty() || time.isEmpty() || duration.isEmpty() || route.isEmpty() || price.isEmpty() || airlineName.isEmpty()) {
            ToastUtil.toastShort(this, "Please fill in all fields!");
            return;
        }

        int id = Integer.parseInt(idStr);
        int airlineLogo;
        String selectedLogo = spAirlineLogo.getSelectedItem().toString();
        switch (selectedLogo) {
            case "Frontier Airlines":
                airlineLogo = ImageConstants.FRONTIER_AIRLINES_LOGO;
                break;
            case "American Airlines":
                airlineLogo = ImageConstants.AMERICAN_AIRLINES_LOGO;
                break;
            case "United Airlines":
                airlineLogo = ImageConstants.UNITED_AIRLINES_LOGO;
                break;
            case "Delta Airlines":
                airlineLogo = ImageConstants.DELTA_AIRLINES_LOGO;
                break;
            default:
                airlineLogo = 0; // Default logo or handle error
                break;
        }

        Flight flight = new Flight(id, time, duration, route, price, airlineName, airlineLogo, count);

        // Insert data into the database
        long rowId = flightMySQLiteOpenHelper.insertData(flight);
        if (rowId != -1) {
            ToastUtil.toastShort(this, "Done insert!");
        } else {
            ToastUtil.toastShort(this, "Failed insert!");
        }
    }
}
