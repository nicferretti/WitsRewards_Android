package com.fourhourdesigns.witsrewards.Activities;

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
import com.fourhourdesigns.witsrewards.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private EditText loginStudentEmail;
    private EditText loginPassword;
    private FirebaseAuth mAuth;
    private Button loginButton;
    private Button registerButton;
    private TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if(mAuth.getCurrentUser() != null){
            //finish();
           // startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        //}

        mAuth = FirebaseAuth.getInstance();
        loginStudentEmail = findViewById(R.id.loginStudentNumber);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        progressDialog = new ProgressDialog(this);
        error = findViewById(R.id.error);

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
        String studentNumber = loginStudentEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        String email = studentNumber + "@students.wits.ac.za";

        if (studentNumber.equals("")) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
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
                    FirebaseUser user = mAuth.getCurrentUser();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
                if(!task.isSuccessful()){
                    error.setText("Invalid login name or password");
                }
            }
        });
    }
}


