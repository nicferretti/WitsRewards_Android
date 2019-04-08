package com.fourhourdesigns.witsrewards;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    String[] Headings = {"What is WitsRewards?", "How do I get rewards?", "What rewards are available?"};
    String[][] Text = {{"WitsRewards is an incentives program which has the aim to create a more engaging and healthy university ecosystem.\n\nBy being a more active member within university life, students can" +
            " earn great rewards for their participation which not only benefits the student but also the university and local businesses"}
            , {"By completing tasks in these 3 areas you will earn points: \n\n" + "1. Academia\n\n" + "2. University life\n\n" + "3. Local business \n\n" +
            "And with these points you will be able to redeem them for rewards. \n\n" + "As you earn points you will also be able increase your level. As you increase your level, the better the" +
            " rewards become. The levels work \n on Bronze, Silver, Gold and \n Diamond tiers."}, {"The rewards scale depending on your tier level: \n\n" +
            "1. Discounts at various food and other establishments\n\n" + "2. Free tickets to various events, sports games and attractions on campus\n\n" + "3. Greater favorability with the university" +
            " which can be added to academic transcripts"}};

    Context context;

    public ExpandableListViewAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getGroupCount() {
        return Headings.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Text[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Headings[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Text[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        TextView txtView = new TextView(context);
        txtView.setText(Headings[groupPosition]);
        txtView.setPadding(100, 0, 0, 0);
        txtView.setTextSize(30);
        txtView.setTextColor(Color.BLACK);
        return txtView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {


        final TextView txtView = new TextView(context);
        txtView.setText(Text[groupPosition][childPosition]);
        txtView.setPadding(100, 0, 0, 0);
        txtView.setTextSize(20);
        txtView.setTextColor(Color.WHITE);


        return txtView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
