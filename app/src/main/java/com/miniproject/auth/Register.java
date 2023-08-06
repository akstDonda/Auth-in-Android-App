package com.miniproject.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword;
    Button rbtn;
    TextView login_btn;
    ProgressBar reg_progressbar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName=findViewById(R.id.fullname);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);


        rbtn=findViewById(R.id.btn);
        login_btn=findViewById(R.id.loginaccounttext);
        reg_progressbar=findViewById(R.id.rprogressbar);

        mAuth = FirebaseAuth.getInstance();


        //user is already create so throw direct mainActivity
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }




        //registration button validation
        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("password is Required");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("character More Then 6 Required");
                    return;
                }
                reg_progressbar.setVisibility(View.VISIBLE);


                //registration in firebase
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "SucessFully Create User", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "Error !!!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        });



    }
}