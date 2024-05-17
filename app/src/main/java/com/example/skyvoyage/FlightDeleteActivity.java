package com.example.skyvoyage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FlightDeleteActivity extends AppCompatActivity {
    private EditText etId;
    private FlightMySQLiteOpenHelper flightMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_delete);
        initView();
        flightMySQLiteOpenHelper = new FlightMySQLiteOpenHelper(this);
    }

    private void initView() {
        etId = findViewById(R.id.et_id);
    }

    public void deleteData(View view) {
        int id = Integer.parseInt(etId.getText().toString().trim());
        if (id <= 0) {
            ToastUtil.toastShort(this, "Please enter a valid ID!");
            return;
        }

        int rowsAffected = flightMySQLiteOpenHelper.deleteFromDbById(id);
        if (rowsAffected > 0) {
            ToastUtil.toastShort(this, "Deleted successfully!");
        } else {
            ToastUtil.toastShort(this, "Failed to delete!");
        }
    }
}
