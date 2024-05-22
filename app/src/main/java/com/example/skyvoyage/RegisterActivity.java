package com.example.skyvoyage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_submit, btn_login;
    EditText et_username, et_password, et_confirm_password;
    UserMySQLiteOpenHelper userMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_submit = findViewById(R.id.btn_submit);
        btn_login = findViewById(R.id.btn_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);

        btn_submit.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        userMySQLiteOpenHelper = new UserMySQLiteOpenHelper(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_submit) {
            if (validateInputs()) {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (userMySQLiteOpenHelper.isUsernameExists(username)) {
                    et_username.setError("Username already exists");
                    et_username.requestFocus();
                } else {
                    User newUser = new User(username, password);
                    long result = userMySQLiteOpenHelper.insertUserData(newUser);

                    if (result != -1) {
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intentLogin);
                    } else {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else if (id == R.id.btn_login) {
            Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intentLogin);
        }
    }

    private boolean validateInputs() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirmPassword = et_confirm_password.getText().toString().trim();

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

        if (confirmPassword.isEmpty()) {
            et_confirm_password.setError("Please confirm your password");
            et_confirm_password.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            et_confirm_password.setError("Passwords do not match");
            et_confirm_password.requestFocus();
            return false;
        }

        return true;
    }
}
