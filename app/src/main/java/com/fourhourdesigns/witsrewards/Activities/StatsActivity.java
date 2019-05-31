package com.fourhourdesigns.witsrewards.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Toast;
import com.fourhourdesigns.witsrewards.App;
import com.fourhourdesigns.witsrewards.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private PieChart StatsPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //Retrieving user data
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        System.out.println(mAuth.getUid() + " AWE MY SON ID");
        if (mAuth.getUid() != null) {
            System.out.println(mAuth.getUid() + " UIIIDDDDD");
            getPoints(mAuth.getUid());
        }
        //Setting up piechart
        StatsPieChart = findViewById(R.id.StatsPC);
        StatsPieChart.getDescription().setText("");
        StatsPieChart.setRotationEnabled(true);
        StatsPieChart.setCenterTextSize(15);
        StatsPieChart.setExtraOffsets(0, 0, 0, 0);
        StatsPieChart.setHoleRadius(55f);
        StatsPieChart.setHoleColor(Color.TRANSPARENT);
        StatsPieChart.setTransparentCircleAlpha(115);
        StatsPieChart.setTransparentCircleRadius(60);
        StatsPieChart.setTransparentCircleColor(Color.WHITE);
        StatsPieChart.animateY(1250, Easing.EaseInOutCubic);
    }

    public boolean getPoints(String UID) {
        mFirestore.collection("users").document(UID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot == null || documentSnapshot.getDouble("academiaPoints")==null){
                    return;
                }
                System.out.println("DATA OVER HERE:   "+ documentSnapshot.getData());
                Double APoints = documentSnapshot.getDouble("academiaPoints");
                Double UPoints = documentSnapshot.getDouble("universityPoints");
                Double BPoints = documentSnapshot.getDouble("businessPoints");
                String level = documentSnapshot.getString("level");
                Double sum = APoints + UPoints + BPoints;

                //Storing the retrieved data to pass onto piechart creation
                ArrayList<Double> temp = new ArrayList<>();
                temp.add(APoints);
                temp.add(UPoints);
                temp.add(BPoints);
                temp.add(100 - APoints - UPoints - BPoints);

                ArrayList<Integer> Points = new ArrayList<>();

                for (Double d : temp) {
                    Points.add(d.intValue());
                }
                String[] labels = {"Academia Points: " + APoints.intValue() + "/30", "University Points: " + UPoints.intValue() + "/30", "Business Points: " + BPoints.intValue() + "/40",
                        "Points until upgrade: " + (100-sum.intValue()) + "/100"};

                addData(Points, labels, level);
            }
        });
        return true;
    }

    public boolean addData(ArrayList<Integer> ydata, String[] xdata, String level) {

        //Creating center text
        String centerText = "WitsRewards points" + "\n" + "Level: ";
        SpannableString sp = new SpannableString(centerText);
        sp.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 25, 0);

        //Customizing so the returned level changes color according to the level of the user
        String colorText = StringUtils.capitalize(level);
        SpannableString sb = new SpannableString(colorText);

        if (level.equals("bronze")) {
            int BronzeColorValue = Color.parseColor("#cd7f32");
            sb.setSpan(new ForegroundColorSpan(BronzeColorValue), 0, 6, 0);
        }

        if (level.equals("silver")) {
            int SilverColorValue = Color.parseColor("#C0C0C0");
            sb.setSpan(new ForegroundColorSpan(SilverColorValue), 0, 6, 0);
        }

        if (level.equals("gold")) {
            int GoldColorValue = Color.parseColor("#FFDF00");
            sb.setSpan(new ForegroundColorSpan(GoldColorValue), 0, 4, 0);
        }

        StatsPieChart.setCenterText(TextUtils.concat(sp, "", sb));

        //Adding data into a arraylist of PieEntries
        ArrayList<PieEntry> yEntries = new ArrayList<>();

        for (int i = 0; i < ydata.size(); i++) {
            yEntries.add(new PieEntry(ydata.get(i), i));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntries, "User points");
        pieDataSet.setSliceSpace(6);
        pieDataSet.setValueTextSize(12);

        //Colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#007FFF"));
        colors.add(Color.parseColor("#0892D0"));
        colors.add(Color.parseColor("#0F52BA"));
        colors.add(Color.parseColor("#D4AF37"));
        pieDataSet.setColors(colors);

        //Adding legend
        Legend legend = StatsPieChart.getLegend();
        legend.setEnabled(true);
        legend.setYOffset(60);
        legend.setXOffset(0);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.WHITE);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setWordWrapEnabled(false);

        List<LegendEntry> entries = new ArrayList<>();
        for (int i = 0; i < xdata.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors.get(i);
            entry.label = xdata[i];
            entries.add(entry);
        }

        legend.setCustom(entries);
        legend.setDrawInside(false);

        //Create pie data object
        PieData pieData = new PieData(pieDataSet);
        StatsPieChart.setData(pieData);
        StatsPieChart.invalidate();
        return true;
    }
}
