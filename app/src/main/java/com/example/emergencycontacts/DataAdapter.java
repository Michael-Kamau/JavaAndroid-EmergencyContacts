package com.example.emergencycontacts;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;

public class DataAdapter extends Adapter<DataAdapter.DataViewHolder> {
    private ArrayList<DataModel> dataLists;
    String number;
    Context mContext;

    public static class DataViewHolder extends ViewHolder {
        public LinearLayout linearLayout;
        public RatingBar ratingBar;
        public TextView tvComment;
        public TextView tvName;
        public CardView cvData;

        public DataViewHolder(View itemView) {
            super(itemView);

            this.cvData=(CardView)itemView.findViewById(R.id.rating_page_cardVidew);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
            this.tvComment = (TextView) itemView.findViewById(R.id.tvComment);
            this.ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.mainLayout);
        }
    }

    public DataAdapter(ArrayList<DataModel> contactsList) {
        this.dataLists = contactsList;
    }

    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.mContext=parent.getContext();
        return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false));
    }

    public void onBindViewHolder(DataViewHolder holder, int position) {
        final DataViewHolder Holder = holder;
        DataModel currentItem = (DataModel) this.dataLists.get(position);

        holder.cvData.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.tvName.setText(currentItem.getName());
        holder.tvComment.setText(currentItem.getComment());
        holder.ratingBar.setRating(Float.parseFloat(currentItem.getRating()));
        holder.linearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DataAdapter.this.number = Holder.tvComment.getText().toString();
            }
        });
    }

    public int getItemCount() {
        return this.dataLists.size();
    }
}
