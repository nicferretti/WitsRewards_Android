package com.fourhourdesigns.witsrewards;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.fourhourdesigns.witsrewards.Adapter.CustomExpandableListAdapter;
import com.fourhourdesigns.witsrewards.Helper.FragmentNavigatorManager;
import com.fourhourdesigns.witsrewards.Interface.NavigationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String>IstTitle;
    private Map<String,List<String>> IstChild;
    private NavigationManager navagationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle=getTitle().toString();
        expandableListView=(ExpandableListView)findViewById(R.id.navList);
        navagationManager= FragmentNavigatorManager.getmInstance(this);

        initItems();
        View listHeaderView=getLayoutInflater().inflate(R.layout.nav_header,null,false);
        expandableListView.addHeaderView(listHeaderView);

        genData();

        addDrawersItems();
        setUpDrawer();

        if (savedInstanceState==null)
            selectFirstItemAsDefault();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Wits Rewards");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {
        if (navagationManager!=null){

            String firstItem= IstTitle.get(0);
            navagationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }
    }

    private void setUpDrawer() {
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,(R.string.open),(R.string.close)){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Wits Rewards");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void addDrawersItems() {
        adapter= new CustomExpandableListAdapter(this,IstTitle,IstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(IstTitle.get(groupPosition).toString()); //Set Title for toolbar when open
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("WitsRewards");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Change Fragment When Clicked
                String selecteditem = ((List)(IstChild.get(IstTitle.get(groupPosition)))).get(childPosition).toString();
                getSupportActionBar().setTitle(selecteditem);

                if (items[0].equals(IstTitle.get(groupPosition)))
                    navagationManager.showFragment(selecteditem);
                else
                    throw new IllegalArgumentException("Not Supported Fragment");
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;


            }
        });
    }

    private void genData() {
        List<String> title = Arrays.asList("Wits","Logout","About Wits Rewards","Rewards");
        List<String> childItem=Arrays.asList("Restaurants","Map","Timetable","Tasks");
        //List<String> childItem1=Arrays.asList("Sign in as a different user","Log Out");
        IstChild=new TreeMap<>();
        IstChild.put(title.get(0),childItem);
        IstChild.put(title.get(1),childItem);
        IstChild.put(title.get(2),childItem);
        IstChild.put(title.get(3),childItem);

        IstTitle= new ArrayList<>(IstChild.keySet());



    }


    private void initItems() {
        items= new String[]{"Logout","About Wits Rewards","Rewards","Wits"};
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
