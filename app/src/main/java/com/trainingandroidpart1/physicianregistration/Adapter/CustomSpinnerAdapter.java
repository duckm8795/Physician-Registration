package com.trainingandroidpart1.physicianregistration.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.trainingandroidpart1.physicianregistration.DegreeListActivity;
import com.trainingandroidpart1.physicianregistration.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kieum on 7/13/2016.
 */
public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private List<String> asr = new ArrayList<>();

    public CustomSpinnerAdapter(Context context,List<String> asr) {
        this.asr=asr;
        activity = context;
    }



    public int getCount()
    {
        return asr.size();
    }

    public Object getItem(int i)
    {
        return asr.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(),"Ubuntu-Regular.ttf");
        TextView txt = new TextView(activity);
        txt.setPadding(40, 50, 40, 50);

        txt.setTextSize(20);
        txt.setTypeface(typeface);
        txt.setGravity(Gravity.CENTER);

        txt.setText(asr.get(position));
        txt.setTextColor(Color.parseColor("#4DB6AC"));
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(),"Ubuntu-Regular.ttf");

        TextView txt = new TextView(activity);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(40, 40, 40, 40);
        txt.setTextSize(20);
        txt.setTypeface(typeface);


        txt.setText(asr.get(i));
        txt.setTextColor(Color.parseColor("#4DB6AC"));
        return  txt;
    }

}