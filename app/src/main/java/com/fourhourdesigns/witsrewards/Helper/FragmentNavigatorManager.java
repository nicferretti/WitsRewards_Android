package com.fourhourdesigns.witsrewards.Helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fourhourdesigns.witsrewards.BuildConfig;
import com.fourhourdesigns.witsrewards.Fragment.FragmentContent;
import com.fourhourdesigns.witsrewards.Interface.NavigationManager;
import com.fourhourdesigns.witsrewards.Activities.HomeActivity;
import com.fourhourdesigns.witsrewards.R;

public class FragmentNavigatorManager implements NavigationManager {
    private static FragmentNavigatorManager mInstance;
    private FragmentManager mFragmentManager;
    private HomeActivity homeActivity;

    public static FragmentNavigatorManager getmInstance(HomeActivity homeActivity){

        if (mInstance==null)
            mInstance=new FragmentNavigatorManager();
        mInstance.configure(homeActivity);
        return mInstance;
    }

    private void configure(HomeActivity homeActivity){

        homeActivity = homeActivity;
        mFragmentManager= homeActivity.getSupportFragmentManager();
    }


    @Override
    public void showFragment(String title){

        showFragment(FragmentContent.newInstance(title),false);


    }

    private void showFragment(Fragment fragmentcontent,boolean b){
        FragmentManager fm= mFragmentManager;
        FragmentTransaction ft= fm.beginTransaction().replace(R.id.container,fragmentcontent);
        ft.addToBackStack(null);

        if (b|| BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();


    }

}
