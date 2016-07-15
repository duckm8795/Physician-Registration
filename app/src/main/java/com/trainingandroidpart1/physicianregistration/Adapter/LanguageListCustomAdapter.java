package com.trainingandroidpart1.physicianregistration.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.trainingandroidpart1.physicianregistration.R;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.LanguageList;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kieum on 7/12/2016.
 */
public class LanguageListCustomAdapter extends BaseAdapter  {
    private List<LanguageList> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private Boolean flag;
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_item_language_list, null);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.language_name);
            //holder.toggleCheckLanguage= (ToggleButton) convertView.findViewById(R.id.btn_check_language);
            holder.titleTextView.setTextSize(15);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LanguageList languageList = this.listData.get(position);

        holder.titleTextView.setText(languageList.getName());


        Typeface font_medium = Typeface.createFromAsset(context.getAssets(), "Ubuntu-Regular.ttf");

        holder.titleTextView.setTypeface(font_medium);
        holder.titleTextView.setTextColor(R.color.color_language_text);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"ALASALS",Toast.LENGTH_LONG).show();
            }
        });



        return convertView;
    }
    public List<LanguageList> filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        List<LanguageList> filteredMovies = new ArrayList<>();

        if (charText.length() == 0) {

            filteredMovies.addAll(listData);
        }
        else
        {
            for (LanguageList ll : listData){
                if (ll.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    filteredMovies.add(ll);

                }
            }
            //listData = filteredMovies;

        }
        return filteredMovies;
    }

    static class ViewHolder {
        TextView titleTextView;
        ToggleButton toggleCheckLanguage;
    }
}
