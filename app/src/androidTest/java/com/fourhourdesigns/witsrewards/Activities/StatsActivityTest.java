package com.fourhourdesigns.witsrewards.Activities;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.fourhourdesigns.witsrewards.R;
import com.github.mikephil.charting.charts.PieChart;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class StatsActivityTest {

    @Rule
    public ActivityTestRule<StatsActivity> mActivityTestRule = new ActivityTestRule<StatsActivity>(StatsActivity.class,true,true);
    private  StatsActivity mActivity = null;
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void onCreate() {
        PieChart StatsPieChart = mActivity.findViewById(R.id.StatsPC);
        RelativeLayout StatsLayout = mActivity.findViewById(R.id.StatsLayout);
        assertNotNull(StatsLayout);
        assertNotNull(StatsPieChart);
    }

    @Test
    public void testAddData(){

    }


    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}

