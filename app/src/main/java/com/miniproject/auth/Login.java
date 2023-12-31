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

import java.util.Objects;

public class Login extends AppCompatActivity {
    Button login_btn;
    TextView txt_create_account;
    EditText lemail,lpassword;
    FirebaseAuth mAuth;
    ProgressBar lprogresbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn=findViewById(R.id.btn);
        txt_create_account=findViewById(R.id.txt_create_account);
        lemail=findViewById(R.id.email);
        lpassword=findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        lprogresbar=findViewById(R.id.lprogressbar);

        //user is already create so throw direct mainActivity
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=lemail.getText().toString().trim();
                String password=lpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    lemail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    lpassword.setError("password is Required");
                    return;
                }
                if(password.length()<6){
                    lpassword.setError("character More Then 6 Required");
                    return;
                }
                lprogresbar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "SucessFully Create User", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Error !!!"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            lprogresbar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });

        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });




    }
}