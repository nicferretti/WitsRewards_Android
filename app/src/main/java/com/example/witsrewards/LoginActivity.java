package com.example.witsrewards;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText loginStudentNum;
    EditText loginPassword;
    Button loginButton;
    Button registerButton;
    static Boolean isValid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginStudentNum = findViewById(R.id.loginStudentNum);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentNum = loginStudentNum.getText().toString();
                String password = loginPassword.getText().toString();
                isValidStudentNum(studentNum,password);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void isValidStudentNum(String studentNum, final String password) {

        final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        db.collection(getString(R.string.students))
                .whereEqualTo("studentNumber", studentNum)
                .get(Source.SERVER)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println(task.getResult().getDocuments());;
                            if (!task.getResult().isEmpty()) {
                                for(DocumentSnapshot doc : task.getResult()){
                                    if (doc.get("password").equals(password)){
                                        alertDialog.setMessage("You have logged in");
                                        alertDialog.show();
                                    }
                                    else {
                                        alertDialog.setMessage("Incorrect password");
                                        alertDialog.show();
                                    }
                                }
                            }
                            else {
                                alertDialog.setMessage("Invalid student number");
                                alertDialog.show();
                            }
                        }
                    }
                });

    }


}
