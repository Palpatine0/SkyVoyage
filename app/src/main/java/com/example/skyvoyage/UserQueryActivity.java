package com.example.skyvoyage;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class UserQueryActivity extends AppCompatActivity {
    private EditText etUsername;
    private TextView tvResult;
    private UserMySQLiteOpenHelper userMySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_query);
        etUsername = findViewById(R.id.et_username);
        tvResult = findViewById(R.id.tv_result);
        userMySQLiteOpenHelper = new UserMySQLiteOpenHelper(this);

        // Enable scrolling for the TextView
        tvResult.setMovementMethod(new ScrollingMovementMethod());
    }

    public void queryUser(View view) {
        String username = etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            List<User> users = userMySQLiteOpenHelper.queryAllUsers();
            showData(users);
            return;
        }
        List<User> users = userMySQLiteOpenHelper.queryUserByUsername(username);
        showData(users);
    }

    private void showData(List<User> users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append("<b>Username:</b> ");
            stringBuilder.append(user.getUsername());
            stringBuilder.append("<br><b>Password:</b> ");
            stringBuilder.append(user.getPassword());
            stringBuilder.append("<br><br>");
        }
        tvResult.setText(Html.fromHtml(stringBuilder.toString()));
    }
}