package com.example.witsrewards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

public class AboutActivity extends AppCompatActivity {
    Button IUnderstand;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        expandableListView = (ExpandableListView)findViewById(R.id.exp_listview);

        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(AboutActivity.this);

        expandableListView.setAdapter(adapter);

        IUnderstand = (Button)findViewById(R.id.IUnderstand);
        IUnderstand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
