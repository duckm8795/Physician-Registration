package com.trainingandroidpart1.physicianregistration.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Entity.SpecialistEntity;
import com.trainingandroidpart1.physicianregistration.R;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.LanguageList;
import com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse.SpecialtyList;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablelistview.IndexableAdapter;

/**
 * Created by kieuduc on 10/07/2016.
 */
public class SpecialistAdapter  extends IndexableAdapter<SpecialistEntity>{
    private Context mContext;
    private List<SpecialistEntity> specialityName = new ArrayList<>();
    private SpecialtyList specialtyList;
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
    protected void onBindViewHolder(IndexableAdapter.ViewHolder holder,final  SpecialistEntity cityEntity) {

        MyViewHolder myViewHolderholder = (MyViewHolder) holder;
        myViewHolderholder.specialtyText.setText(cityEntity.getName());

        myViewHolderholder.checkSpecialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
//                TextView tv = (TextView) v;
                cityEntity.setSelected(cb.isChecked());
                //Toast.makeText(mContext,String.valueOf(cityEntity.getId()),Toast.LENGTH_SHORT).show();
            }
        });


        myViewHolderholder.checkSpecialty.setChecked(cityEntity.isSelected());
    }



    class MyViewHolder extends IndexableAdapter.ViewHolder {
        TextView specialtyText;
        CheckBox checkSpecialty;
        public MyViewHolder(final View view) {
            super(view);
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"Ubuntu-Regular.ttf");
            specialtyText = (TextView) view.findViewById(R.id.tv_name);
            checkSpecialty = (CheckBox) view.findViewById(R.id.check_speciality_togg);
            specialtyText.setTypeface(typeface);
            specialtyText.setTextColor(R.color.color_language_text);

        }
    }
}
