package com.trainingandroidpart1.physicianregistration.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trainingandroidpart1.physicianregistration.R;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;

import java.util.List;

/**
 * Created by Admin on 6/30/2016.
 */
public class VerifyPhysicianCustomAdapter extends BaseAdapter{
    private List<VerificationDocType> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public VerifyPhysicianCustomAdapter(Context aContext,  List<VerificationDocType> listData) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_list_item_verify_physician, null);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.title);
            holder.titleTextView.setTextSize(15);

            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.description);
            holder.descriptionTextView.setTextColor(R.color.colorDarkGrey);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VerificationDocType verificationDocTypes = this.listData.get(position);

        holder.titleTextView.setText(verificationDocTypes.getTitle());
        holder.descriptionTextView.setText(verificationDocTypes.getBriefDescription());

        return convertView;
    }


    static class ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
    }

}
