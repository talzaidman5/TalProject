package com.example.project.data;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.R;

import java.util.ArrayList;
import java.util.Date;

public class Adapter_Position extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    private final int VIEW_TYPE_NORMAL = 0;

    private Context context;
    private ArrayList<Position> positions;

    public Adapter_Position(Context context, ArrayList<Position> positions) {
        this.context = context;
        this.positions = positions;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return positions == null ? 0 : positions.size();
    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_position, parent, false);
            return new ViewHolder_Normal(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_position, parent, false);
        ViewHolder_Normal myViewHolderNormal = new ViewHolder_Normal(view);
        return myViewHolderNormal;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        Position temp = getItem(pos);


            ViewHolder_Normal mHolder = (ViewHolder_Normal) holder;
            mHolder.position_LBL_title.setText( temp.getLocation());
            mHolder.position_LBL_subTitle.setText(temp.getStartHour()+"-"+temp.getEndHour());
            mHolder.position_TXT_city.setText(getDateStr(temp.getDate()));


        Glide
                    .with(context)
                    .load(temp.getMainImage())
                    .centerCrop()
                    .into(mHolder.position_IMG_back);

    }

    private Position getItem(int position) {
        return positions.get(position);
    }

private String getDateStr(Date date){
        return date.getDate()+"/"+date.getMonth()+"/"+date.getYear();
}

    static class ViewHolder_Normal extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView position_LBL_title;
        public TextView position_LBL_subTitle;
        public TextView position_TXT_city;
        private ImageView position_IMG_back;

        public ViewHolder_Normal(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            position_LBL_title = itemView.findViewById(R.id.position_LBL_title);
            position_LBL_subTitle = itemView.findViewById(R.id.position_LBL_subTitle);
            position_TXT_city = itemView.findViewById(R.id.position_LBL_city);
            position_IMG_back = itemView.findViewById(R.id.position_IMG_back);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }

}
