package com.example.witsrewards;

import android.app.ProgressDialog;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class,true,true);
    private  LoginActivity mActivity = null;
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void onCreate() {
        EditText loginStudentEmail = mActivity.findViewById(R.id.loginStudentNumber);
        EditText loginPassword = mActivity.findViewById(R.id.loginPassword);
        Button loginButton = mActivity.findViewById(R.id.loginButton);
        Button registerButton = mActivity.findViewById(R.id.registerButton);
        TextView error = mActivity.findViewById(R.id.error);
        assertNotNull(loginStudentEmail);
        assertNotNull(loginPassword);
        assertNotNull(loginButton);
        assertNotNull(registerButton);
        assertNotNull(error);
    }

    @Test
    public void getStudentNumber(){

    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}

