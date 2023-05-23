package com.example.my_money_app;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText email, password;
    TextView forgot_password;

    TextView gSigninBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gSigninBtn = (TextView)findViewById(R.id.gsigninBtn);
        loginBtn = findViewById(R.id.login_button);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
        forgot_password = findViewById(R.id.forgot_password);
        loginBtn.setOnClickListener(v -> {
            if (validateData()) {

                //new method created
                login(email.getText().toString(),password.getText().toString());

//                Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
//                i.putExtra("message", "Login Screen");
//                startActivity(i);
            }
        });
        forgot_password.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
            i.putExtra("message", "Forgot password Screen");
            startActivity(i);
        });

        gSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, signup_page.class));
            }
        });
    }

    public void login(String mail,String pwd){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(mail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,HomePage.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Login failed "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void SendDetailsToDb(String email, String pw) {
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String, Object> user = new HashMap<>();
//        user.put("email",email );
//        user.put("password", pw);
//
//        db.collection("login").document(email)
//                .set(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("ABT", "DocumentSnapshot successfully written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//                        Log.w("ABT", "Error writing document", e);
//                    }
//                });
//
//    }

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

    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,HomePage.class));
        }
    }
}