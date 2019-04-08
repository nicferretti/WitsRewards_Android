package com.fourhourdesigns.witsrewards;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity  {

    private ProgressDialog progressDialog;
    private Button login;
    private EditText loginStudentEmail;
    private EditText loginPassword;
    private TextView signUp;
    private FirebaseAuth mAuth;
    private Button loginButton;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if(mAuth.getCurrentUser() != null){
            //finish();
           // startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        //}

        mAuth = FirebaseAuth.getInstance();
        loginStudentEmail = findViewById(R.id.loginStudentEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        progressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        String email = loginStudentEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();


        if (email.equals("")) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.contains("@students.wits.ac.za")) {
            Toast.makeText(this, "Please enter your Wits student email address", Toast.LENGTH_SHORT).show();
            return;
        }


        if (password.equals("")) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging in user");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                   // finish();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }
        });
    }
}


