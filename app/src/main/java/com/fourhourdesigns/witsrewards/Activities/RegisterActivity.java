package com.fourhourdesigns.witsrewards.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.fourhourdesigns.witsrewards.R;
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

import java.util.ArrayList;
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
                String studentNumber = editTextStudentNumber.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String verifyPassword = editTextVerifyPassword.getText().toString().trim();
                registerUser(studentNumber,password,verifyPassword);
            }
        });
    }


    public Map saveUserInformation(String uID,String name, String surname, String YoS, String studentNumber) {

        //Setting up initial user values
        int AcademiaPoints = 0;
        int UniversityPoints = 0;
        int BusinessPoints = 0;
        String level = "bronze";

        //Passing values to hashmap which be taken by Firebase
        Map intialUserInformation = new HashMap();
        intialUserInformation.put("id", uID);
        intialUserInformation.put("name", name);
        intialUserInformation.put("surname", surname);
        intialUserInformation.put("yos", YoS);
        intialUserInformation.put("academiaPoints", AcademiaPoints);
        intialUserInformation.put("universityPoints", UniversityPoints);
        intialUserInformation.put("businessPoints", BusinessPoints);
        intialUserInformation.put("level", level);
        intialUserInformation.put("studentNumber", studentNumber);

        //Saves information in the "users" collection and into the respective user document
        DocumentReference dbUsers = dbRef.collection("users").document(uID);
        dbUsers.set(intialUserInformation);

        return intialUserInformation;

    }

    public boolean registerUser(final String studentNumber, final String password, String vpassword) {

        //Getting email and password data

        if (studentNumber.equals("")) {
            //email is empty
            Toast.makeText(this, "Please enter student number", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (password.equals("")) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(vpassword)) {
            //password is empty
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        progressDialogRegister.setMessage("Registering user...");
        progressDialogRegister.show();
        createUser(studentNumber,password);
        //Create user
        return true;
    }

    public String createUser(final String studentNumber,final String password){

        final ArrayList<Boolean> arrayList = new ArrayList<>();
        String email = studentNumber + "@students.wits.ac.za";
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialogRegister.cancel();
                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                    //Now that user has been registered, sign them in
                    String name = editTextName.getText().toString().trim();
                    String surname = editTextSurname.getText().toString().trim();
                    String user_ID = firebaseAuth.getUid();
                    String YoS = yearsDropdown.getSelectedItem().toString();
                    saveUserInformation(user_ID,name,surname,YoS,studentNumber);
                    userRegistrationLogin(studentNumber,password);
                } else {
                    System.out.println(task.getResult());
                    progressDialogRegister.cancel();
                    Toast.makeText(RegisterActivity.this, "Could not register, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return email;

    }

    public Intent userRegistrationLogin(String studentNumber,String password) {

        //Signs in user
        String email = studentNumber + "@students.wits.ac.za";
        progressDialogLogin.setMessage("Logging in user");
        progressDialogLogin.show();
        final Intent intent = new Intent(getApplicationContext(), AboutActivity.class);

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialogLogin.dismiss();
                if (task.isSuccessful()) {
                    // finish();
                    progressDialogLogin.cancel();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification();
                    startActivity(intent);
                }
            }
        });
        return intent;
    }


    @Override
    public void onClick(View v) {

    }
}
