package com.fourhourdesigns.witsrewards.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fourhourdesigns.witsrewards.R;
import com.google.firebase.Timestamp;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    ArrayList<HashMap<String,Object>> mDataset;
    Context mContext;
    public EventAdapter(Context context, ArrayList<HashMap<String, Object>> myDataset) {
        this.mDataset = myDataset;
        this.mContext = context;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_item, viewGroup, false);
        EventAdapter.ViewHolder vh = new EventAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setItem(mDataset.get(viewHolder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventText;

        public ViewHolder(View v) {
            super(v);
            eventText = v.findViewById(R.id.eventText);
        }

        @SuppressLint("SetTextI18n")
        public void setItem(HashMap<String, Object> item) {

            Timestamp timestamp = (Timestamp) item.get("date");
            SimpleDateFormat sfd = new SimpleDateFormat("dd MMMM kk:mm");
            String date = sfd.format(timestamp.toDate());
            this.eventText.setText("Description: " +
                    item.get("description")
                    +"\n"
                    + "Date: "
                    + date
                    + "\n"
                    + "Venue: "
                    + item.get("venue"));
        }
    }

}
