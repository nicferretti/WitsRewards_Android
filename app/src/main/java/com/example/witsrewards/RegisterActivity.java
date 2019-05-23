package com.example.witsrewards;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRegister;
    private EditText editTextStudentNumber;
    private EditText editTextPassword;
    private EditText editTextVerifyPassword;
    private ProgressDialog progressDialogRegister;
    private ProgressDialog progressDialogLogin;
    private EditText editTextName;
    private EditText editTextSurname;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore dbRef;
    private Spinner yearsDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonRegister = (Button) findViewById(R.id.registerButton2);
        firebaseAuth = FirebaseAuth.getInstance();
        editTextStudentNumber = (EditText) findViewById(R.id.EnterEmail);
        editTextPassword = (EditText) findViewById(R.id.EnterPassword);
        editTextVerifyPassword = (EditText) findViewById(R.id.VerifyPassword);
        editTextName = (EditText) findViewById(R.id.EnterName);
        editTextSurname = (EditText) findViewById(R.id.EnterSurname);
        progressDialogRegister = new ProgressDialog(this);
        progressDialogLogin = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        dbRef = FirebaseFirestore.getInstance();
        yearsDropdown = findViewById(R.id.YOS);

        String[] years = new String[]{"1st", "2nd", "3rd", "Postgraduate"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        yearsDropdown.setAdapter(adapter1);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }


    public void saveUserInformation() {

        //Setting up initial user values
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String user_ID = firebaseAuth.getUid();
        String YoS = yearsDropdown.getSelectedItem().toString();
        String studentNumber = editTextStudentNumber.getText().toString().trim();
        int AcademiaPoints = 0;
        int UniversityPoints = 0;
        int BusinessPoints = 0;
        String level = "bronze";

        //Passing values to hashmap which be taken by Firebase
        Map intialUserInformation = new HashMap();
        intialUserInformation.put("id", user_ID);
        intialUserInformation.put("name", name);
        intialUserInformation.put("surname", surname);
        intialUserInformation.put("yos", YoS);
        intialUserInformation.put("academiaPoints", AcademiaPoints);
        intialUserInformation.put("universityPoints", UniversityPoints);
        intialUserInformation.put("businessPoints", BusinessPoints);
        intialUserInformation.put("level", level);
        intialUserInformation.put("studentNumber", studentNumber);

        //Saves information in the "users" collection and into the respective user document
        DocumentReference dbUsers = dbRef.collection("users").document(firebaseAuth.getUid());
        dbUsers.set(intialUserInformation);

    }

    public void registerUser() {

        //Getting email and password data
        String email = editTextStudentNumber.getText().toString().trim() + "@students.wits.ac.za";
        String password = editTextPassword.getText().toString().trim();
        String verifyPassword = editTextVerifyPassword.getText().toString().trim();

        if (email.equals("")) {
            //email is empty
            Toast.makeText(this, "Please enter student number", Toast.LENGTH_SHORT).show();
            return;
        }


        if (password.equals("")) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(verifyPassword)) {
            //password is empty
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialogRegister.setMessage("Registering user...");
        progressDialogRegister.show();

        //Create user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialogRegister.cancel();
                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                    //Now that user has been registered, sign them in
                    userRegistrationLogin();
                } else {
                    System.out.println(task.getResult());
                    ;
                    progressDialogRegister.cancel();
                    Toast.makeText(RegisterActivity.this, "Could not register, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void userRegistrationLogin() {

        //Signs in user
        String email = editTextStudentNumber.getText().toString().trim() + "@students.wits.ac.za";
        String password = editTextPassword.getText().toString().trim();


        progressDialogLogin.setMessage("Logging in user");
        progressDialogLogin.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialogLogin.dismiss();
                if (task.isSuccessful()) {
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
