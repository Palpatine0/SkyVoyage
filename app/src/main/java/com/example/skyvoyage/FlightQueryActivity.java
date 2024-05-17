package com.example.skyvoyage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class FlightQueryActivity extends AppCompatActivity {
    private EditText etId;
    private TextView tvResult;
    private FlightMySQLiteOpenHelper flightMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_query);
        etId = findViewById(R.id.et_id);
        tvResult = findViewById(R.id.tv_result);
        flightMySQLiteOpenHelper = new FlightMySQLiteOpenHelper(this);
    }

    public void queryData(View view) {
        String idStr = etId.getText().toString().trim();
        if (TextUtils.isEmpty(idStr)) {
            List<Flight> flights = flightMySQLiteOpenHelper.queryAllFromDb();
            showData(flights);
            return;
        }
        int id = Integer.parseInt(idStr);
        List<Flight> flights = flightMySQLiteOpenHelper.queryFromDbById(id);
        showData(flights);
    }

    private void showData(List<Flight> flights) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Flight flight : flights) {
            stringBuilder.append("ID: ");
            stringBuilder.append(flight.getId());
            stringBuilder.append(", Time: ");
            stringBuilder.append(flight.getTime());
            stringBuilder.append(", Duration: ");
            stringBuilder.append(flight.getDuration());
            stringBuilder.append(", Route: ");
            stringBuilder.append(flight.getRoute());
            stringBuilder.append(", Price: ");
            stringBuilder.append(flight.getPrice());
            stringBuilder.append(", Airline: ");
            stringBuilder.append(flight.getAirlineName());
            stringBuilder.append(", Logo: ");
            stringBuilder.append(flight.getAirlineLogo());
            stringBuilder.append(", Count: ");
            stringBuilder.append(flight.getCount());
            stringBuilder.append("\n");
        }
        tvResult.setText(stringBuilder.toString());
    }
}
