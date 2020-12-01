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
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Adapter_Position extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_NORMAL = 0;
    private final int VIEW_TYPE_AD = 1;

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
            mHolder.article_LBL_title.setText(temp.getCityName());
            mHolder.article_LBL_subTitle.setText(temp.getStartHour());


            Glide
                    .with(context)
                    .load(temp.getMainImage())
                    .centerCrop()
                    .into(mHolder.article_IMG_back);

    }

    private Position getItem(int position) {
        return positions.get(position);
    }



    static class ViewHolder_Normal extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView article_IMG_back;
        public TextView article_LBL_title;
        public TextView article_LBL_subTitle;

        public ViewHolder_Normal(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            article_IMG_back = itemView.findViewById(R.id.article_IMG_back);
            article_LBL_title = itemView.findViewById(R.id.article_LBL_title);
            article_LBL_subTitle = itemView.findViewById(R.id.article_LBL_subTitle);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }

    static class ViewHolder_Ad extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView article_IMG_back;
        public TextView article_LBL_title;
        public TextView article_LBL_subTitle;
        public MaterialButton article_BTN_click;

        public ViewHolder_Ad(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            article_IMG_back = itemView.findViewById(R.id.article_IMG_back);
            article_LBL_title = itemView.findViewById(R.id.article_LBL_title);
            article_LBL_subTitle = itemView.findViewById(R.id.article_LBL_subTitle);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }
}
