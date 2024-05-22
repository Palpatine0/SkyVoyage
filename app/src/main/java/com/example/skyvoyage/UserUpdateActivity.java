package com.example.skyvoyage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UserUpdateActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private UserMySQLiteOpenHelper userMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        userMySQLiteOpenHelper = new UserMySQLiteOpenHelper(this);
    }

    public void updateUser(View view) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            ToastUtil.toastShort(this, "Please fill in all fields!");
            return;
        }

        User user = new User(username, password);
        // Assuming `updateUserData` is implemented in the SQLiteOpenHelper to update user data
        int rowsAffected = userMySQLiteOpenHelper.updateUserData(user);
        if (rowsAffected > 0) {
            ToastUtil.toastShort(this, "User updated successfully!");
        } else {
            ToastUtil.toastShort(this, "Failed to update user!");
        }
    }
}
