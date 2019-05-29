package com.fourhourdesigns.witsrewards.Activities;

import android.support.annotation.NonNull;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.fourhourdesigns.witsrewards.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.Map;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);
    private RegisterActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testOnCreate() throws Exception {

        Button buttonRegister = (Button) mActivity.findViewById(R.id.registerButton2);
        EditText editTextStudentNumber = (EditText) mActivity.findViewById(R.id.EnterEmail);
        EditText editTextPassword = (EditText) mActivity.findViewById(R.id.EnterPassword);
        EditText editTextVerifyPassword = (EditText) mActivity.findViewById(R.id.VerifyPassword);
        EditText editTextName = (EditText) mActivity.findViewById(R.id.EnterName);
        EditText editTextSurname = (EditText) mActivity.findViewById(R.id.EnterSurname);
        Spinner yearsDropdown = mActivity.findViewById(R.id.YOS);

        assertNotNull(buttonRegister);
        assertNotNull(editTextStudentNumber);
        assertNotNull(editTextPassword);
        assertNotNull(editTextVerifyPassword);
        assertNotNull(editTextName);
        assertNotNull(editTextSurname);
        assertNotNull(yearsDropdown);

    }

    @Test
    public void testSaveUserInfo() {

        Map userinfo = mActivity.saveUserInformation("000", "Test", "User", "1st", "0000000");
        System.out.println(userinfo);
        assertEquals("000", userinfo.get("id"));
        assertEquals("Test", userinfo.get("name"));
        assertNotNull("User", userinfo.get("surname"));
        assertNotNull("1st", userinfo.get("yos"));
        assertNotNull("0000000", userinfo.get("studentNumber"));
        assertEquals(0, userinfo.get("academiaPoints"));
        assertEquals(0, userinfo.get("universityPoints"));
        assertEquals(0, userinfo.get("businessPoints"));
        assertEquals("bronze", userinfo.get("level"));
    }

    @Test
    public void testOnClick(){
        onView(withId(R.id.registerButton2))            // withId(R.id.my_view) is a ViewMatcher
                .perform(scrollTo(),click())               // click() is a ViewAction
                .check(matches(isDisplayed()));
    }

    @Test
    @UiThreadTest
    public void testRegisterUserEmailEmpty() {
        boolean b = mActivity.registerUser("", "test123", "test123");
        assertFalse(b);
    }

    @Test
    @UiThreadTest
    public void testRegisterUserPasswordEmpty() {
        final boolean b = mActivity.registerUser("test@email.com", "", "test123");
        assertFalse(b);
    }

    @Test
    @UiThreadTest
    public void testRegisterUserPasswordsDontMatch(){
        final boolean b = mActivity.registerUser("0000000","test123","t");
        assertFalse(b);
    }

    @Test
    @UiThreadTest
    public void testRegisterUserValid(){
        final boolean b = mActivity.registerUser("0000000","test123","test123");
        assertTrue(b);
    }

    @Test
    @UiThreadTest
    public void testRegisterUserLogin(){
        assertNotNull(mActivity.userRegistrationLogin("0000000","test123"));
    }

    @Test
    @UiThreadTest
    public void testCreateUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            user.delete();
        }
        String b = mActivity.createUser("0000000","test123");
        assertEquals("0000000@students.wits.ac.za", b);{

        }
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("0000000@students.wits.ac.za", "test123").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                }
            }
        });
    }
}