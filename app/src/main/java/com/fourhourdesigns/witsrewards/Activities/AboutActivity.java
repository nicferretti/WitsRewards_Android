package com.fourhourdesigns.witsrewards.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import com.fourhourdesigns.witsrewards.ExpandableListViewAdapter;
import com.fourhourdesigns.witsrewards.R;

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
                Intent intent = new Intent(AboutActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
