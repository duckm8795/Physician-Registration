package com.trainingandroidpart1.physicianregistration.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    protected void onBindViewHolder(IndexableAdapter.ViewHolder holder, final SpecialistEntity cityEntity) {
        final MyViewHolder cityViewHolder = (MyViewHolder) holder;
        cityViewHolder.tvCity.setText(cityEntity.getName());

        cityViewHolder.tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cityViewHolder.checkSpecialty.isChecked()){
                    cityViewHolder.checkSpecialty.setChecked(true);
                }else{
                    cityViewHolder.checkSpecialty.setChecked(false);
                }
                //Toast.makeText(mContext,String.valueOf(cityEntity.getId()),Toast.LENGTH_LONG).show();
            }
        });

    }


    class MyViewHolder extends IndexableAdapter.ViewHolder {
        TextView tvCity;
        CheckBox checkSpecialty;
        public MyViewHolder(final View view) {
            super(view);
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"Ubuntu-Regular.ttf");
            tvCity = (TextView) view.findViewById(R.id.tv_name);
            checkSpecialty = (CheckBox) view.findViewById(R.id.check_speciality_togg);
            tvCity.setTypeface(typeface);
            tvCity.setTextColor(R.color.color_language_text);

//            tvCity.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(view.getContext(),"T",Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
