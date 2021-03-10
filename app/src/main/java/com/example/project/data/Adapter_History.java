package com.example.project.data;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;
import java.util.Date;

public class Adapter_History extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    private final int VIEW_TYPE_NORMAL = 0;
    private Context context;
    private ArrayList<BloodDonation> bloodDonations;

    public Adapter_History(Context context, ArrayList<BloodDonation> bloodDonations) {
        this.context = context;
        this.bloodDonations = bloodDonations;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return bloodDonations == null ? 0 : bloodDonations.size();
    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history, parent, false);
            return new ViewHolder_Normal(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history, parent, false);
        ViewHolder_Normal myViewHolderNormal = new ViewHolder_Normal(view);
        return myViewHolderNormal;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        BloodDonation temp = getItem(pos);


            ViewHolder_Normal mHolder = (ViewHolder_Normal) holder;
            mHolder.history_TXT_Date.setText( temp.getDate()+"");
            mHolder.history_TXT_Place.setText(temp.getCity());



    }

    private BloodDonation getItem(int position) {
        return bloodDonations.get(position);
    }

private String getDateStr(Date date){
        return date.getDay()+"/"+date.getMonth()+"/"+date.getYear();
}

    static class ViewHolder_Normal extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView history_TXT_Place;
        public TextView history_TXT_Date;


        public ViewHolder_Normal(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            history_TXT_Place = itemView.findViewById(R.id.history_TXT_Place);
            history_TXT_Date = itemView.findViewById(R.id.history_TXT_Date);

        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }

}
