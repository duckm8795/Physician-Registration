package com.trainingandroidpart1.physicianregistration.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
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
public class LanguageListCustomAdapter extends BaseAdapter   {
    public List<LanguageList> listData;
    private List<LanguageList> filteredData;
    private LayoutInflater layoutInflater;
    private Context context;
    public List<Boolean> check_selected = new ArrayList<>() ;
    private LanguageList languageList ;


    public LanguageListCustomAdapter(Context aContext,  List<LanguageList> listData) {
        this.context = aContext;
        this.listData = listData;
        this.filteredData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        for ( int i = 0 ; i < listData.size(); i++){
            check_selected.add(false);
        }
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

    public View getView(final int position, View convertView, ViewGroup parent) {
//        Context context = null;
        final ViewHolder holder;
        Typeface font_medium = Typeface.createFromAsset(context.getAssets(), "Ubuntu-Regular.ttf");
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.custom_item_language_list, null);
            holder = new ViewHolder();

            holder.titleTextView = (TextView) convertView.findViewById(R.id.language_name);
            holder.toggleCheckLanguage= (CheckBox) convertView.findViewById(R.id.btn_check_language);
            holder.titleTextView.setTextSize(15);
            holder.titleTextView.setTypeface(font_medium);
            holder.titleTextView.setTextColor(R.color.color_language_text);

            convertView.setTag(holder);

            holder.toggleCheckLanguage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v;
                    CheckBox cb = (CheckBox) v;
                    languageList = (LanguageList) tv.getTag();
                    languageList.setSelected(cb.isChecked());
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final LanguageList languageList = this.listData.get(position);
        holder.titleTextView.setText(languageList.getName());
        holder.toggleCheckLanguage.setChecked(languageList.isSelected());
        holder.toggleCheckLanguage.setTag(languageList);



//        holder.titleTextView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Toast.makeText(context,languageList.getLanguageCode(),Toast.LENGTH_LONG).show();
//                /* this part of code is received languagecode from input like : "rus,eng" to send request to sever*/
//                if (languageReceieve.contains(languageList.getLanguageCode())){
//                    holder.toggleCheckLanguage.setChecked(false);
//                    if ( languageReceieve.equals("")){
//                        languageReceieve = languageList.getLanguageCode()+",";
//                        Toast.makeText(context,languageReceieve,Toast.LENGTH_SHORT).show();
//                    }else{
//                        String temp = "";
//                        temp = languageReceieve.substring(0,languageReceieve.length() - 1 );
//                        if ( temp.equals(languageList.getLanguageCode())){
//                            languageReceieve = "";
//                            Toast.makeText(context,languageReceieve,Toast.LENGTH_SHORT).show();
//                        }else{
//                            String[] split_result = languageReceieve.split(languageList.getLanguageCode()+",");
//                            int weqe = split_result.length;
//                            if ( split_result.length == 1){
//                                String te =split_result[0];
//                                languageReceieve = te;
//
//                            }else{
//                                String fff1 = split_result[0];
//                                String fff = split_result[1];
//                                languageReceieve =fff1 +fff;
//                            }
//
//                        }
//
//                        Toast.makeText(context,languageReceieve,Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    holder.toggleCheckLanguage.setChecked(true);
//                    languageReceieve += languageList.getLanguageCode()+",";
//                    Toast.makeText(context,languageReceieve.substring(0,languageReceieve.length()-1),Toast.LENGTH_SHORT).show();
//                }
//
//
//                temp_string = languageReceieve;
//                notifyDataSetChanged();
//
//            }
//
//        });
//        saveData(temp_string);


        return convertView;
    }


    public void filter(String charText) {
        listData = filteredData;
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
            listData = filteredMovies;

        }


    }



    static class ViewHolder {
        TextView titleTextView;
        CheckBox toggleCheckLanguage;
    }
}
