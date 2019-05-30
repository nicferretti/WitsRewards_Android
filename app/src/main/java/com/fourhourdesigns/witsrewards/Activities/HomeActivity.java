package com.fourhourdesigns.witsrewards.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.fourhourdesigns.witsrewards.Adapter.EventAdapter;
import com.fourhourdesigns.witsrewards.Interface.NavigationManager;
import com.fourhourdesigns.witsrewards.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public FirebaseFirestore db;
    public HashMap<String, Object> userInfo;
    public FirebaseAuth mAuth;
    public FirebaseUser user;
    public TextView nameTextView;
    public TextView rankTextView;
    public View listHeaderView;
    public ExpandableListView expandableListView;
    public ExpandableListAdapter adapter;
    public List<String> IstTitle;
    public Map<String, List<String>> IstChild;
    public NavigationManager navigationManager;
    public ImageView avatarImage;
    public TextView username_tv;
    public TextView details_tv;
    public TextView points_tv;
    public TextView level_tv;
    public RecyclerView recyclerView;
    public ArrayList<HashMap<String, Object>> eventList = new ArrayList<>();
    public EventAdapter eventAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username_tv = (TextView) findViewById(R.id.tv_name);
        details_tv = (TextView) findViewById(R.id.tv_details);
        points_tv = (TextView) findViewById(R.id.tv_points);
        level_tv = (TextView) findViewById(R.id.tv_level);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        if (user != null) {
            db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot doc = task.getResult();
                    userInfo = (HashMap<String, Object>) doc.getData();
                    getEvents();

                }
            });
        }
        makeNavLayout();
        getUserInformation(mAuth.getUid());
        setRecyclerView();
    }

    public boolean setRecyclerView() {
        recyclerView = findViewById(R.id.eventRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);
        return true;
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
        } else if (id == R.id.qr) {
            Intent intent = new Intent(HomeActivity.this, QRActivity.class);
            startActivity(intent);
        } else if (id == R.id.map) {
            Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.wquiz) {
            Intent intent = new Intent(HomeActivity.this, Quiz.class);
            startActivity(intent);
        }
        //startAnimationFromBackgroundThread();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public boolean getUserInformation(String UID) {
        if(UID == null){
            return false;
        }

        db.collection("users").document(UID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot == null){
                    return;
                }
                Double APoints = documentSnapshot.getDouble("academiaPoints");
                Double UPoints = documentSnapshot.getDouble("universityPoints");
                Double BPoints = documentSnapshot.getDouble("businessPoints");
                int points = (APoints.intValue() + UPoints.intValue() + BPoints.intValue());
                String level = documentSnapshot.getString("level");
                String details = documentSnapshot.getString("yos");

                String fullname = (documentSnapshot.getString("name") + " " + documentSnapshot.getString("surname"));
                String userDetails = details + " Year Student";
                setPoints(fullname,userDetails,points,level);

            }
        });
        return true;
    }

    public boolean setPoints(String fullname, String userDetails, int points, String level){
        username_tv.setText(fullname);
        details_tv.setText(userDetails);
        points_tv.setText(Integer.toString(points));
        level_tv.setText(level.toUpperCase());

        if (level.equals("bronze")) {
            level_tv.setTextColor(Color.parseColor("#cd7f32"));
        }

        if (level.equals("silver")) {
            level_tv.setTextColor(Color.parseColor("#C0C0C0"));
        }

        if (level.equals("gold")) {
            level_tv.setTextColor(Color.parseColor("#FFDF00"));
        }
        return true;
    }

    public void getEvents() {
        if(user == null){
            return;
        }
        db.collection("events")
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                eventList.add((HashMap<String, Object>) doc.getData());
                                eventAdapter.notifyDataSetChanged();
                            }
                        }
                        else{
                            System.out.println("PENIS");
                            System.out.println(task.getException());
                        }
                    }
                });
    }
}
