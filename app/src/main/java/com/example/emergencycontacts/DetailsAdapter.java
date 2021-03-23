                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        package com.example.emergencycontacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {
    private ArrayList<ContactsModel> contactsLists;
    private OnItemClickListener mListener;
    Context mContext;
    String number;
    ArrayAdapter<String> spinnerAdapter;

    public static class DetailsViewHolder extends RecyclerView.ViewHolder {
        public Button btCall;
        public Button btMaps;
        public Button btRating;
        public CardView cardView;
        public Spinner contactSpinner;
        public TextView etContacts;

        /* renamed from: id */
        public TextView f45id;
        public TextView lat;
        public LinearLayout linearLayout;
        public TextView lon;
        public RatingBar ratingBar;
        public TextView tvCity;
        public TextView tvDetails;
        public TextView tvDistance;
        public TextView tvReviews;
        public TextView tvService;
        public TextView tvTown;
        public  CardView cvDetails;

        public DetailsViewHolder(View itemView) {
            super(itemView);

            this.cvDetails=(CardView)itemView.findViewById(R.id.details_cardVidew);
            this.tvCity = (TextView) itemView.findViewById(R.id.tvName);
            this.tvTown = (TextView) itemView.findViewById(R.id.tvTown);
            this.tvDetails = (TextView) itemView.findViewById(R.id.tvDetails);
            this.tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            this.etContacts = (TextView) itemView.findViewById(R.id.etContact);
            this.contactSpinner = (Spinner) itemView.findViewById(R.id.spFields);
            this.lat = (TextView) itemView.findViewById(R.id.tvLatitude);
            this.lon = (TextView) itemView.findViewById(R.id.tvLongitude);
            this.f45id = (TextView) itemView.findViewById(R.id.tvId);
            this.tvService = (TextView) itemView.findViewById(R.id.tvService);
            this.tvReviews = (TextView) itemView.findViewById(R.id.tvReviews2);
            this.ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            this.btCall = (Button) itemView.findViewById(R.id.buttonCall);
            this.btMaps = (Button) itemView.findViewById(R.id.buttonMaps);
            this.btRating = (Button) itemView.findViewById(R.id.buttonRatings);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.mainLayout);
        }
    }

    public interface OnItemClickInterface {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public DetailsAdapter(ArrayList<ContactsModel> contactsList) {
        this.contactsLists = contactsList;
    }

    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext=parent.getContext();
        return new DetailsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    }

    public void onBindViewHolder(final DetailsViewHolder holder, int position) {
        final DetailsViewHolder Holder = holder;
        ContactsModel currentItem = (ContactsModel) this.contactsLists.get(position);

        holder.cvDetails.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
       // holder.tvCity.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        //holder.tvReviews.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));

        holder.tvCity.setText(currentItem.getName());
        holder.tvTown.setText(currentItem.getTown());
        holder.tvDetails.setText(currentItem.getDetails());
        holder.tvDistance.setText(currentItem.getDist() + " Km");
        holder.etContacts.setText(currentItem.getContact());
        holder.lat.setText(currentItem.getLat());
        holder.lon.setText(currentItem.getLon());
        holder.f45id.setText(currentItem.getId());
        holder.tvService.setText(currentItem.getService());
        holder.tvReviews.setText(currentItem.getHits() + "");
        holder.ratingBar.setRating(currentItem.getRating());
        this.spinnerAdapter = new ArrayAdapter<>(holder.f45id.getContext(), R.layout.spinner_layout, R.id.Tv_spinnner_data, currentItem.getSpinnerList());
        holder.contactSpinner.setAdapter(this.spinnerAdapter);
        holder.btCall.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DetailsAdapter.this.number = Holder.etContacts.getText().toString();
                Log.d("**********", "onClick: call" + DetailsAdapter.this.number);
                Holder.etContacts.getContext().startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + DetailsAdapter.this.number)));
            }
        });
        holder.btMaps.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String lat = Holder.lat.getText().toString();
                String lon = Holder.lon.getText().toString();
                String details = Holder.tvDetails.getText().toString();
                Log.d("**********", "onClick: call" + DetailsAdapter.this.number);
                Intent mapIntent = new Intent("android.intent.action.VIEW");
                mapIntent.setData(Uri.parse("geo:<" + lat + ">,<" + lon + ">?q=<" + lat + ">,<" + lon + ">(" + details + ")"));
                Holder.etContacts.getContext().startActivity(mapIntent);
            }
        });
        holder.btRating.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent ratingIntent = new Intent(Holder.btRating.getContext(), rating_page.class);
                Log.d("forLoop^^^^^", "doInBackground: " + Holder.tvService.getText().toString());
                ratingIntent.putExtra("id", Holder.f45id.getText().toString());
                ratingIntent.putExtra("service_value", Holder.tvService.getText().toString());
                Holder.btRating.getContext().startActivity(ratingIntent);
            }
        });
        holder.contactSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                holder.etContacts.setText(parent.getItemAtPosition(position).toString());
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public int getItemCount() {
        return this.contactsLists.size();
    }
}
