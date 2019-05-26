package com.example.witsrewards;

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
                registerOnClick();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOnClick();
            }
        });
    }

    public boolean loginOnClick(){
        String studentNumber = loginStudentEmail.getText().toString();
        String password = loginPassword.getText().toString();
        userLogin(studentNumber,password);
        return true;
    }

    public Intent registerOnClick(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        return intent;
    }

    public String getPassword() {
        String password = loginPassword.getText().toString().trim();
        if (password.equals("")) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        return password;
    }

    public String getStudentNumber() {
        String studentNumber = loginStudentEmail.getText().toString().trim();

        if (studentNumber.equals("")) {
            //email is empty
            Toast.makeText(this, "Please enter student number", Toast.LENGTH_SHORT).show();
        }
        return studentNumber;
    }

    public Intent userLogin(String studentNumber,String password) {
        String email = studentNumber + "@students.wits.ac.za";
        final Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

        if (studentNumber.isEmpty() || password.isEmpty()) {
            return null;
        }

        progressDialog.setMessage("Logging in user");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    startActivity(intent);
                }
                if (!task.isSuccessful()) {
                    error.setText("Invalid login name or password");
                }
            }
        });
        return intent;
    }
}


