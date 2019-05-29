package com.fourhourdesigns.witsrewards.Activities;

import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.fourhourdesigns.witsrewards.R;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class, true, true);
    private LoginActivity mActivity = null;

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
        assertTrue(loginButton.hasOnClickListeners());
        assertTrue(registerButton.hasOnClickListeners());
        assertNotNull(loginStudentEmail);
        assertNotNull(loginPassword);
        assertNotNull(loginButton);
        assertNotNull(registerButton);
        assertNotNull(error);
        assertTrue(mActivity.loginOnClick());
        assertNotNull(mActivity.registerOnClick());

    }

    @Test
    public void testGetPasswordValid(){
        onView(withId(R.id.loginPassword))            // withId(R.id.my_view) is a ViewMatcher
                .perform(typeText("password"))               // click() is a ViewAction
                .check(matches(isDisplayed()));
        String expectedPassword = mActivity.getPassword();
        EditText loginPassword = mActivity.findViewById(R.id.loginPassword);
        assertEquals(loginPassword.getText().toString(),expectedPassword);
    }

    @Test
    @UiThreadTest
    public void testGetPasswordInvalid(){
        EditText loginPassword = mActivity.findViewById(R.id.loginPassword);
        assertEquals(loginPassword.getText().toString(),"");
    }

    @Test
    public void testGetStudentNumberValid(){
        onView(withId(R.id.loginStudentNumber))            // withId(R.id.my_view) is a ViewMatcher
                .perform(typeText("0000000"))               // click() is a ViewAction
                .check(matches(isDisplayed()));
        EditText loginNumber = mActivity.findViewById(R.id.loginStudentNumber);
        assertEquals(mActivity.getStudentNumber(),loginNumber.getText().toString());
    }
    @Test
    @UiThreadTest
    public void testGetStudentNumberInvalid(){
        EditText loginNumber = mActivity.findViewById(R.id.loginStudentNumber);
        assertEquals(mActivity.getStudentNumber(),loginNumber.getText().toString());
    }

    @Test
    @UiThreadTest
    public void testUserLoginStudentNumberEmpty(){
        assertNull(mActivity.userLogin("","password"));
    }

    @Test
    @UiThreadTest
    public void testUserLoginPasswordEmpty(){
        assertNull(mActivity.userLogin("0000000",""));
    }

    @Test
    @UiThreadTest
    public void testUserLoginValid(){
        assertNotNull(mActivity.userLogin("0000000","password"));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}

