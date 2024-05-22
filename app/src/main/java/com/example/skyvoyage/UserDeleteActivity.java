package com.example.skyvoyage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UserDeleteActivity extends AppCompatActivity {
    private EditText etUsername;
    private UserMySQLiteOpenHelper userMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete);
        etUsername = findViewById(R.id.et_username);
        userMySQLiteOpenHelper = new UserMySQLiteOpenHelper(this);
    }

    public void deleteUser(View view) {
        String username = etUsername.getText().toString().trim();
        if (username.isEmpty()) {
            ToastUtil.toastShort(this, "Please enter a valid username!");
            return;
        }

        int rowsAffected = userMySQLiteOpenHelper.deleteUserByUsername(username);
        if (rowsAffected > 0) {
            ToastUtil.toastShort(this, "User deleted successfully!");
        } else {
            ToastUtil.toastShort(this, "Failed to delete user!");
        }
    }
}
