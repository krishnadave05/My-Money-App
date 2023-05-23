package com.example.my_money_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePage extends AppCompatActivity {

    Button signout;
    Button realwork;
    Button dashboard;

    TextView greeting;

    FirebaseFirestore db;

    String strProvider;

    FirebaseAuth mAuth;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        signout = (Button)findViewById(R.id.signout);
        realwork = (Button) findViewById(R.id.realwork);
        dashboard = (Button) findViewById(R.id.dashboard);
        greeting = (TextView)findViewById(R.id.greetingTextView);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mAuth.getAccessToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult getTokenResult) {
                strProvider = getTokenResult.getSignInProvider();
                if(strProvider.equals("password")){
                    DocumentReference docRef = db.collection("login").document(FirebaseAuth.getInstance().getUid());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
//                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                greeting.setText("hi "+document.get("firstName"));
                            } else {
                                Log.d("sccs msg ", "No such document");
                            }
                        } else {
                            Log.d("error msg ", "get failed with ", task.getException());
                        }
                    }
                });
                    Toast.makeText(HomePage.this, "email password "+FirebaseAuth.getInstance().getUid(), Toast.LENGTH_SHORT).show();
                }else{
                    greeting.setText("Hello "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                }
            }
        });

//        for (UserInfo user: FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
//            if (user.getProviderId().equals("password")) {
//                DocumentReference docRef = db.collection("login").document(FirebaseAuth.getInstance().getUid());
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
////                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                                greeting.setText("hi "+document.get("firstName"));
//                            } else {
//                                Log.d("sccs msg ", "No such document");
//                            }
//                        } else {
//                            Log.d("error msg ", "get failed with ", task.getException());
//                        }
//                    }
//                });
//            }else{
//                greeting.setText("Hello "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
//            }
//        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this,LoginActivity.class));
            }
        });

        realwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,Input.class));
            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, DashboardActivity.class));
            }
        });
    }
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser()==null){
//            startActivity(new Intent(HomePage.this,LoginActivity.class));
//        }
//    }
}