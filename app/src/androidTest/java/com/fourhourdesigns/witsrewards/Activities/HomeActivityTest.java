package com.fourhourdesigns.witsrewards.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.fourhourdesigns.witsrewards.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class HomeActivityTest {


    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    private HomeActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void onCreate() {

        TextView username_tv = mActivity.username_tv;
        TextView details_tv = mActivity.details_tv;
        TextView points_tv = mActivity.points_tv;
        TextView level_tv = mActivity.level_tv;
        assertNotNull(username_tv);
        assertNotNull(details_tv);
        assertNotNull(points_tv);
        assertNotNull(level_tv);
    }

    @Test
    @UiThreadTest
    public void makeNavLayout() {
        mActivity.makeNavLayout();
        Toolbar toolbar = mActivity.findViewById(R.id.toolbar);
        ActionBar actionBar = mActivity.getSupportActionBar();
        DrawerLayout drawer = mActivity.findViewById(R.id.drawer_layout);
        NavigationView navigationView = mActivity.findViewById(R.id.nav_view);
        assertNotNull(toolbar);
        assertNotNull(actionBar);
        assertNotNull(drawer);
        assertNotNull(navigationView);
    }

    @Test
    public void onNavigationFirstItemItemSelected() {
        NavigationView navigationView = mActivity.findViewById(R.id.nav_view);
        MenuItem menueItem=navigationView.getMenu().getItem(0);
        assertTrue(mActivity.onNavigationItemSelected(menueItem));
    }

    @Test
    public void getUserInformation() {
        assertTrue(mActivity.getUserInformation("tVdaEPFAlnUpOxEeJ8er1NC6ntz2"));
    }
    @Test
    @UiThreadTest
    public void setPoints() {
        TextView username_tv = mActivity.username_tv;
        TextView details_tv = mActivity.details_tv;
        TextView points_tv = mActivity.points_tv;
        TextView level_tv = mActivity.level_tv;
        mActivity.setPoints("test user","1st",30,"bronze");
        System.out.println("POINTS    " + points_tv.getText().toString());
        System.out.println("LEVEL    " + level_tv.getText().toString());
        assertTrue(username_tv.getText().toString().equals("test user"));
        assertTrue(details_tv.getText().toString().equals("1st"));
        assertTrue(points_tv.getText().toString().equals("30"));
        assertTrue(level_tv.getText().toString().equals("BRONZE"));
    }
    @Test
    @UiThreadTest
    public void setRecyclerView() {
        assertTrue(mActivity.setRecyclerView());
    }

    @Test
    public void getEvents() {
        assertFalse(mActivity.getEvents());
    }
}