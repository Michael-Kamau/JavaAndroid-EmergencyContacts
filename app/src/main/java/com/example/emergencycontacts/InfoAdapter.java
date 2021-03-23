package com.example.emergencycontacts;

import android.content.Context;
//import android.support.p000v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class InfoAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<InfoModel> models;

    public InfoAdapter(List<InfoModel> models2, Context context2) {
        this.models = models2;
        this.context = context2;
    }

    public int getCount() {
        return this.models.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        this.layoutInflater = LayoutInflater.from(this.context);
        View view = this.layoutInflater.inflate(R.layout.info_item, container, false);
        TextView title = (TextView) view.findViewById(R.id.infoTitle);
        TextView description = (TextView) view.findViewById(R.id.infoContent);
        ((ImageView) view.findViewById(R.id.infoImage)).setImageResource(((InfoModel) this.models.get(position)).getImage());
        title.setText(((InfoModel) this.models.get(position)).getTitle());
        description.setText(((InfoModel) this.models.get(position)).getDescription());
        container.addView(view, 0);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
