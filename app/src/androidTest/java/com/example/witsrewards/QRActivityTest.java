package com.example.witsrewards;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class QRActivityTest {

    @Rule
    public ActivityTestRule<QRActivity> mActivityTestRule = new ActivityTestRule<QRActivity>(QRActivity.class);
    private QRActivity mActivity = null;


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {
    }


}