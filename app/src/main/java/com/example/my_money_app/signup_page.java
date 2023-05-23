package com.example.my_money_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup_page extends AppCompatActivity {

    EditText email ;
    EditText passwrd;
    Button signUp;
    TextView signin;

    ImageView gSignin;

    Spinner spinnerDropdown;

    EditText firstName;

    EditText lastName;

    EditText editTextDate;

    TextView confirmPass;

   private GoogleSignInClient client;

    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        email = (EditText)findViewById(R.id.emailSignup);
        passwrd = (EditText)findViewById(R.id.passwordSignUp);
        signUp = (Button)findViewById(R.id.btnSignup);
        signin = (TextView)findViewById(R.id.txtSignup);
        gSignin = (ImageView)findViewById(R.id.gSignInTxt);
        spinnerDropdown = (Spinner)findViewById(R.id.spinner);
        confirmPass = findViewById(R.id.confirmPass);


        editTextDate = findViewById(R.id.whichdate);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerDropdown.setAdapter(adapter);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });

        signin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup_page.this, LoginActivity.class));
            }
        });

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this,options);

        gSignin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Handle the selected date
                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                editTextDate.setText(selectedDate);
            }
        }, 2001, 1, 1);

        datePickerDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1234) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                Log.v("message-check","inside if");
                try {
                    Log.v("message","inside try");
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.v("message","inside listener");
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signup_page.this, "signin success", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        startActivity(new Intent(signup_page.this,HomePage.class));
                                        // User is signed in
                                    } else {
                                        Toast.makeText(signup_page.this, "gsignin failed "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        // Authentication failed
                                    }
                                }
                            });
                } catch (ApiException e) {
                    Toast.makeText(this, "google signin failed", Toast.LENGTH_SHORT).show();
                }
            }
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent i = new Intent(signup_page.this,HomePage.class);
            startActivity(i);
        }
    }

    private void registerNewUser() {

        if(email.getText().toString().isEmpty()){
            Toast.makeText(this, "email cannot be empty", Toast.LENGTH_SHORT).show();
        }
        if(passwrd.getText().toString().isEmpty()){
            Toast.makeText(this, "password field is required", Toast.LENGTH_SHORT).show();
        }

        if(lastName.getText().toString().isEmpty()){
            Toast.makeText(this, "first name is required", Toast.LENGTH_SHORT).show();
        }

        if(firstName.getText().toString().isEmpty()){
            Toast.makeText(this, "first name is required", Toast.LENGTH_SHORT).show();
        }
        if(confirmPass.getText().toString().equals(passwrd.getText().toString())==false){
            Toast.makeText(this, "passwords do not match", Toast.LENGTH_SHORT).show();
        }

        if(lastName.getText().toString().isEmpty()==false && firstName.getText().toString().isEmpty()==false &&email.getText().toString().isEmpty()==false && passwrd.getText().toString().isEmpty()==false && confirmPass.getText().toString().equals(passwrd.getText().toString())){
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),passwrd.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendDataToDb(firstName.getText().toString(),lastName.getText().toString(),spinnerDropdown.getSelectedItem().toString(),editTextDate.getText().toString());
                                mAuth.signOut();
                                Toast.makeText(getApplicationContext(), "signup success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(signup_page.this, LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"signup failed "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void sendDataToDb(String firstName, String lastName, String gender,String date) {
        String uid = mAuth.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> details = new HashMap<>();
        details.put("firstName", firstName);
        details.put("lastName", lastName);
        details.put("gender", gender);
        details.put("dob",date);

        db.collection("login").document(uid)
                .set(details)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("db add", "DocumentSnapshot successfully written!");
                        Toast.makeText(signup_page.this, "doc added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("db add", "Error writing document", e);
                        Toast.makeText(signup_page.this, "Error writing doc", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}