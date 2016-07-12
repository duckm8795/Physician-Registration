package com.trainingandroidpart1.physicianregistration.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trainingandroidpart1.physicianregistration.R;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.LanguageList;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;

import java.util.List;

/**
 * Created by kieum on 7/12/2016.
 */
public class LanguageListCustomAdapter extends BaseAdapter {
    private List<LanguageList> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public LanguageListCustomAdapter(Context aContext,  List<LanguageList> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
//        Context context = null;
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_item_language_list, null);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.language_name);
            holder.titleTextView.setTextSize(15);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LanguageList languageList = this.listData.get(position);

        holder.titleTextView.setText(languageList.getName());


//        Typeface font_medium = Typeface.createFromAsset(, "Ubuntu-Medium.ttf");
//        Typeface font_light = Typeface.createFromAsset(context.getAssets(), "Ubuntu-Light.ttf");
//        holder.titleTextView.setTypeface(font_medium);
//        holder.descriptionTextView.setTypeface(font_light);

        return convertView;
    }


    static class ViewHolder {
        TextView titleTextView;

    }
}
