package com.example.skyvoyage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_register, btn_submit;
    EditText et_username, et_password, et_validation_code;
    TextView tv_validation_code;
    UserMySQLiteOpenHelper userMySQLiteOpenHelper;
    String generatedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register = findViewById(R.id.btn_register);
        btn_submit = findViewById(R.id.btn_submit);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_validation_code = findViewById(R.id.et_validation_code);
        tv_validation_code = findViewById(R.id.tv_validation_code);

        btn_register.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        tv_validation_code.setOnClickListener(this);

        userMySQLiteOpenHelper = new UserMySQLiteOpenHelper(this);
        generateValidationCode();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_register) {
            Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intentRegister);
        } else if (id == R.id.btn_submit) {
            if (validateInputs() && validateCode()) {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                boolean isValid = userMySQLiteOpenHelper.validateUser(username, password);
                if (isValid) {
                    Intent intentFlightListings = new Intent(LoginActivity.this, FlightListingsActivity.class);
                    startActivity(intentFlightListings);
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (id == R.id.tv_validation_code) {
            generateValidationCode();
        }
    }

    private boolean validateInputs() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (username.isEmpty()) {
            et_username.setError("Please enter a username");
            et_username.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            et_password.setError("Please enter a password");
            et_password.requestFocus();
            return false;
        }

        if (password.length() < 8) {
            et_password.setError("Password must be at least 8 characters long");
            et_password.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validateCode() {
        String code = et_validation_code.getText().toString().trim();
        if (code.isEmpty()) {
            et_validation_code.setError("Please enter the validation code");
            et_validation_code.requestFocus();
            return false;
        }

        if (!code.equals(generatedCode)) {
            et_validation_code.setError("Invalid validation code");
            et_validation_code.requestFocus();
            generateValidationCode();
            return false;
        }

        return true;
    }

    private void generateValidationCode() {
        Random random = new Random();
        generatedCode = String.format("%04d", random.nextInt(10000));
        tv_validation_code.setText(generatedCode);
    }
}
