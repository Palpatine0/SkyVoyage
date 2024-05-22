package com.example.skyvoyage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class UserManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);
    }

    public void insertUserData(View view) {
        Intent intent = new Intent(this, UserInsertActivity.class);
        startActivity(intent);
    }

    public void updateUserData(View view) {
        Intent intent = new Intent(this, UserUpdateActivity.class);
        startActivity(intent);
    }

    public void queryUserData(View view) {
        Intent intent = new Intent(this, UserQueryActivity.class);
        startActivity(intent);
    }

    public void deleteUserData(View view) {
        Intent intent = new Intent(this, UserDeleteActivity.class);
        startActivity(intent);
    }
}
