package com.fourhourdesigns.witsrewards.Activities;

import android.media.Image;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import com.fourhourdesigns.witsrewards.R;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest


public class AboutActivityTest {

    @Rule
    public ActivityTestRule<AboutActivity> mActivityTestRule = new ActivityTestRule<AboutActivity>(AboutActivity.class,true,true);
    private  AboutActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void onCreate() {
        ImageView imageViewAbout = mActivity.findViewById(R.id.imageViewAbout);
        Button IUnderstand = mActivity.findViewById(R.id.IUnderstand);
        ExpandableListView expandableListView = mActivity.findViewById(R.id.exp_listview);
        assertNotNull(imageViewAbout);
        assertNotNull(IUnderstand);
        assertNotNull(expandableListView);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}