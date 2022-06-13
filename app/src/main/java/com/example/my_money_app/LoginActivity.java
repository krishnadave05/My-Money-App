package com.example.my_money_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText email, password;
    TextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.login_button);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        forgot_password = findViewById(R.id.forgot_password);
        loginBtn.setOnClickListener(v -> {
            if (validateData()) {
                Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                i.putExtra("message", "Login Screen");
                startActivity(i);
            }
        });
        forgot_password.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
            i.putExtra("message", "Forgot password Screen");
            startActivity(i);
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean validateData() {
        boolean flag = true;
        if (!isEmail(email)) {
            email.setError("Enter valid email address!");
            flag = false;
        }
        if (isEmpty(password)) {
            password.setError("Password can't be empty!");
            flag = false;
        }
        return flag;
    }
}