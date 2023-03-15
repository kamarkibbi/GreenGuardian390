package com.example.greenguardian390.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.os.Bundle;

import com.example.greenguardian390.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    protected EditText mUsername,mPassword;
    Button HitLogin;
    private TextView forgotPassword;
    //private FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //mAuth = FirebaseAuth.getInstance();

        mUsername=findViewById(R.id.UserName);
        mPassword=findViewById(R.id.PassWord);

        HitLogin=findViewById(R.id.Login1);
        HitLogin.setOnClickListener(this);

        progressBar=findViewById(R.id.progressBar);
        forgotPassword=findViewById(R.id.ForgotPassword);
        forgotPassword.setOnClickListener(this);


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
        String email=mUsername.getText().toString().trim();
        String password=mPassword.getText().toString().trim();

        if (email.isEmpty()){
            mUsername.setError("Email is mandatory");
            mUsername.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mUsername.setError("Please enter a valid email id");
            mUsername.requestFocus();
            return;
        }
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

