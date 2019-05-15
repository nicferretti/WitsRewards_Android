package com.fourhourdesigns.witsrewards.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.fourhourdesigns.witsrewards.Interface.NavigationManager;
import com.fourhourdesigns.witsrewards.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;
    private FirebaseFirestore db;
    private String name;
    private HashMap<String, Object> userInfo;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView nameTextView;
    private TextView rankTextView;
    private View listHeaderView;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> IstTitle;
    private Map<String, List<String>> IstChild;
    private NavigationManager navigationManager;
    private ImageView avatarImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                userInfo = (HashMap<String, Object>) doc.getData();
            }
        });
        makeNavLayout();
    }



//    private void populateHeader(){
//        listHeaderView = getLayoutInflater().inflate(R.layout.nav_header, null, false);
//        avatarImage = listHeaderView.findViewById(R.id.avatar);
//        rankTextView = listHeaderView.findViewById(R.id.balance);
//        nameTextView = listHeaderView.findViewById(R.id.name);
//        nameTextView.setText(userInfo.get("name") + " " + userInfo.get("surname"));
//        name = (String)userInfo.get("name");
//        StringUtils utils = new StringUtils();
//        String level = utils.capitalize((String)userInfo.get("level"));
//        rankTextView.setText("Level: " + level);
//        final TextDrawable drawableFront = TextDrawable.builder()
//                .beginConfig()
//                .fontSize((int)((30 * Resources.getSystem().getDisplayMetrics().density)))
//                .endConfig()
//                .buildRound(Character.toString(name.charAt(0)), ColorGenerator.MATERIAL.getRandomColor());
//    }

    public void makeNavLayout() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView header = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        Picasso.get().load(R.drawable.skyline).into(header);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.about) {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.stats) {
            Intent intent = new Intent(HomeActivity.this, StatsActivity.class);
            startActivity(intent);
        }else if (id == R.id.qr) {
            Intent intent = new Intent(HomeActivity.this, QRActivity.class);
            startActivity(intent);
        }
        //startAnimationFromBackgroundThread();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
