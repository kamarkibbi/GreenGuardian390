package com.example.greenguardian390.Views;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenguardian390.Models.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.os.Bundle;

import com.example.greenguardian390.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    protected EditText mUsername,mPassword;
    Button HitLogin;
    private TextView forgotPassword;

    TextView test;

    DatabaseReference mDatabase;
    ProgressBar progressBar;

    FirebaseFirestore db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mUsername=findViewById(R.id.UserName);
        mPassword=findViewById(R.id.PassWord);

        HitLogin=findViewById(R.id.Login1);
        HitLogin.setOnClickListener(this);

        progressBar=findViewById(R.id.progressBar);
        forgotPassword=findViewById(R.id.ForgotPassword);
        forgotPassword.setOnClickListener(this);

        test=findViewById(R.id.testData);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Login1:
                userLogin();
                break;
        }
        /*
        switch (view.getId()){
            case R.id.ForgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }*/
    }

    private void userLogin() {
        String usernameInputted=mUsername.getText().toString().trim();
        String password=mPassword.getText().toString().trim();

        if (usernameInputted.isEmpty()){
            mUsername.setError("username is mandatory");
            mUsername.requestFocus();
            return;
        }
        /*if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mUsername.setError("Please enter a valid email id");
            mUsername.requestFocus();
            return;
        }*/
        if(password.isEmpty()){
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;
        }
        if(password.length()< 5){
            mPassword.setError("The minimum requirement is 5 character");
            mPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

       //mDatabase.getKey().toLowerCase().contains("username");

        mDatabase= FirebaseDatabase.getInstance().getReference("userProfile/"+usernameInputted);

        //FOR SOME REASON STILL GOES TO MAIN PAGE IF ACCOUNT DOESNT EXIST???? FIXXX
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                System.out.println("on data change called");

                UserProfile userProfile= snapshot.getValue(UserProfile.class);

                Intent intent=new Intent(LoginActivity.this,MainPage.class);


                intent.putExtra("currentProfile", userProfile);

                startActivity(intent);


                System.out.println(userProfile.getUsername());
                /*for(DataSnapshot d :snapshot.getChildren())
                {
                    if ((d.getKey().toLowerCase().contains("username")))
                    {
                        if((d.getValue().equals(usernameInputted)))
                        {
                            test.setText(d.getValue()+"");
                            Intent intent=new Intent(LoginActivity.this,MainPage.class);
                            //intent.putExtra("currentProfile",usernameInputted);
                            startActivity(new Intent(LoginActivity.this,MainPage.class));
                        }
                }*/


                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this,"Please check username spelling or create account in register page",Toast.LENGTH_LONG).show();
            }

        });







        /*DocumentReference docRef = db.collection("userProfile").document(usernameInputted);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                System.out.println("On complete called");
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/



        //Tanzila code, used authentication database, ours is real-time database
        /*
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }
                else {
                    Toast.makeText(LoginActivity.this,"Failed to login!Either Username or password is incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });*/

    }
}

