package com.example.greenguardian390.Views;

/*import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenguardian390.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner;
    private EditText fullName,editEmail,editPassword;
    private Button HitRegister;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        HitRegister=findViewById(R.id.Register_button);
        fullName=findViewById(R.id.FullName);
        editEmail=findViewById(R.id.Email1);
        editPassword=findViewById(R.id.Password1);
        progressBar=findViewById(R.id.progressBar);

        HitRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Register_button:
                HitRegister();
                break;
        }
    }

    private void HitRegister() {
        String Name=fullName.getText().toString().trim();
        String Email=editEmail.getText().toString().trim();
        String Password=editPassword.getText().toString().trim();

        if(Name.isEmpty()){
            fullName.setError("Full name is Required!");
            fullName.requestFocus();
            return;
        }
        if(Email.isEmpty()){
            editEmail.setError("Email is Required!");
            editEmail.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            editPassword.setError("Email is Required!");
            editPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editEmail.setError("Please provide a valid email");
            editEmail.requestFocus();
            return;
        }
        if(Password.length()< 5){
            editPassword.setError("The password requirement is above 5 character");
            editPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(Name,Email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,"User has been register successfully",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.VISIBLE);
                                                startActivity(new Intent(RegisterActivity.this,IntroActivity.class));
                                            }
                                            else {
                                                Toast.makeText(RegisterActivity.this,"Registration Failed! Please try again.",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);

                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this," Something went wrong  ",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                        }
                    }
                });
    }
}*/