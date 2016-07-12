package com.trainingandroidpart1.physicianregistration.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trainingandroidpart1.physicianregistration.Entity.SpecialistEntity;
import com.trainingandroidpart1.physicianregistration.R;

import me.yokeyword.indexablelistview.IndexableAdapter;

/**
 * Created by kieuduc on 10/07/2016.
 */
public class SpecialistAdapter  extends IndexableAdapter<SpecialistEntity>{
    private Context mContext;

    public SpecialistAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected TextView onCreateTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_tv_title_specialist, parent, false);
        return (TextView) view.findViewById(R.id.tv_title);
    }

    @Override
    protected IndexableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_specialist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(IndexableAdapter.ViewHolder holder, SpecialistEntity cityEntity) {
        MyViewHolder cityViewHolder = (MyViewHolder) holder;
        cityViewHolder.tvCity.setText(cityEntity.getName());

    }


    class MyViewHolder extends IndexableAdapter.ViewHolder {
        TextView tvCity;

        public MyViewHolder(View view) {
            super(view);
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"Ubuntu-Regular.ttf");
            tvCity = (TextView) view.findViewById(R.id.tv_name);

            tvCity.setTypeface(typeface);
        }
    }
}
