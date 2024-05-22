package com.example.skyvoyage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UserInsertActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private UserMySQLiteOpenHelper userMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_insert);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        userMySQLiteOpenHelper = new UserMySQLiteOpenHelper(this);
    }

    public void insertUser(View view) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            ToastUtil.toastShort(this, "Please fill in all fields!");
            return;
        }

        User user = new User(username, password);
        long rowId = userMySQLiteOpenHelper.insertUserData(user);
        if (rowId != -1) {
            ToastUtil.toastShort(this, "User inserted successfully!");
        } else {
            ToastUtil.toastShort(this, "Failed to insert user!");
        }
    }
}
