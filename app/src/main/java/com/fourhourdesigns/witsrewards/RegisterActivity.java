package com.fourhourdesigns.witsrewards;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextVerifyPassword;
    private ProgressDialog progressDialogRegister;
    private ProgressDialog progressDialogLogin;
    private EditText editTextName;
    private EditText editTextSurname;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    private String YoS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonRegister= (Button)findViewById(R.id.registerButton2);
        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText)findViewById(R.id.EnterEmail);
        editTextPassword = (EditText)findViewById(R.id.EnterPassword);
        editTextVerifyPassword = (EditText)findViewById(R.id.VerifyPassword);
        editTextName = (EditText)findViewById(R.id.EnterName);
        editTextSurname = (EditText)findViewById(R.id.EnterSurname);
        progressDialogRegister = new ProgressDialog(this);
        progressDialogLogin = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);



        dbRef = FirebaseDatabase.getInstance().getReference("User information");


        Spinner yearsDropdown = findViewById(R.id.YOS);
        String[] years = new String[]{"1st", "2nd", "3rd","Postgraduate"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        yearsDropdown.setAdapter(adapter1);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            YoS = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveUserInformation(){
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, surname, YoS);
        FirebaseUser user = firebaseAuth.getCurrentUser();



        dbRef.child(user.getUid()).setValue(userInformation).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Successfully saved user information", Toast.LENGTH_LONG).show();
                }
                if(!task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String verifyPassword = editTextVerifyPassword.getText().toString().trim();

        if(email.equals("")){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!email.contains("@students.wits.ac.za")){
            Toast.makeText(this,"Please enter a Wits student email address",Toast.LENGTH_SHORT).show();
            return;
        }


        if(password.equals("")){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(verifyPassword)){
            //password is empty
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialogRegister.setMessage("Registering user...");
        progressDialogRegister.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialogRegister.cancel();
                    Toast.makeText(RegisterActivity.this,  "Registered successfully", Toast.LENGTH_SHORT).show();
                    userRegistrationLogin();
                }
                else{
                    progressDialogRegister.cancel();
                    Toast.makeText(RegisterActivity.this,  "Could not register, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });}

    public void userRegistrationLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        progressDialogLogin.setMessage("Logging in user");
        progressDialogLogin.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialogLogin.dismiss();
                if(task.isSuccessful()){
                    // finish();
                    progressDialogLogin.cancel();
                    Toast.makeText(getApplicationContext(), "User logged in successfully", Toast.LENGTH_SHORT).show();
                    saveUserInformation();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification();
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
