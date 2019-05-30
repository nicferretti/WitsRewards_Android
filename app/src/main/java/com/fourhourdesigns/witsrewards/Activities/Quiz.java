package com.fourhourdesigns.witsrewards.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.fourhourdesigns.witsrewards.QuestionsLibrary;
import com.fourhourdesigns.witsrewards.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import javax.annotation.Nullable;
import java.util.HashMap;


public class Quiz extends AppCompatActivity {

    private QuestionsLibrary mQuestionLibrary = new QuestionsLibrary();

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    public FirebaseFirestore db;


    private String mAnswer;
    private double mScore = 0;
    private int mQuestionNumber = 0;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mScoreView = (TextView) findViewById(R.id.score);
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);


        updateQuestion();

        mAuth = FirebaseAuth.getInstance();


        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mButtonChoice1.getText() == mAnswer) {
                    mScore = mScore + 10;
                    updateScore(mScore);
                    updateQuestion();

                    Toast.makeText(Quiz.this, "Correct", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Quiz.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mButtonChoice2.getText() == mAnswer) {
                    mScore = mScore + 10;
                    updateScore(mScore);
                    updateQuestion();

                    Toast.makeText(Quiz.this, "Correct", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Quiz.this, "Wrong", Toast.LENGTH_LONG).show();
                    updateQuestion();
                }
            }
        });


        mButtonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //My logic for Button goes in here

                if (mButtonChoice3.getText() == mAnswer) {
                    mScore = mScore + 10;
                    updateScore(mScore);
                    updateQuestion();

                    Toast.makeText(Quiz.this, "Correct", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Quiz.this, "Wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });


    }


    private void updateQuestion() {
        if (mQuestionNumber < mQuestionLibrary.getLength()) {
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
            mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
            mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));

            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);

            mQuestionNumber++;
        } else {
            updateToFirestore();

        }
    }


    private void updateScore(double point) {
        mScoreView.setText("" + mScore);
    }


    public void updateToFirestore() {
        db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("users").document(mAuth.getUid());

        Bundle b = getIntent().getExtras();
        double result = b.getDouble("UPOINTS");

        Toast.makeText(Quiz.this, ""+result, Toast.LENGTH_LONG).show();


        ref.update("universityPoints", (mScore+result)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Quiz.this, ":)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Quiz.this, HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(Quiz.this, "Thanks for playing the weekly quiz, come back next week to win more points", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Quiz.this, ":(", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}