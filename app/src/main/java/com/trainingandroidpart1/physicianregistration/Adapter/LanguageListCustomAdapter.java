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
public class LanguageListCustomAdapter extends BaseAdapter{
    private List<LanguageList> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private String languageReceieve = "";
    private String temp_string = "";
    private SharedPreferences sharedPreferences = null;
    private ArrayList<Boolean> checks=new ArrayList<Boolean>();
    private LanguageList languageList;
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

    public View getView(final int position, View convertView, ViewGroup parent) {
//        Context context = null;
        final ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_item_language_list, null);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.language_name);
            holder.toggleCheckLanguage= (CheckBox) convertView.findViewById(R.id.btn_check_language);
            holder.titleTextView.setTextSize(15);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final LanguageList languageList = this.listData.get(position);
        holder.toggleCheckLanguage.setTag(languageList);


        holder.titleTextView.setText(languageList.getName());
        //holder.toggleCheckLanguage.setTag(languageList.getLanguageCode());
        Typeface font_medium = Typeface.createFromAsset(context.getAssets(), "Ubuntu-Regular.ttf");

        holder.titleTextView.setTypeface(font_medium);

        holder.titleTextView.setTextColor(R.color.color_language_text);

        holder.titleTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.toggleCheckLanguage.isChecked()){
                    holder.toggleCheckLanguage.setChecked(false);
                }else{
                    holder.toggleCheckLanguage.setChecked(true);
                }
            }
        });

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
////                if(languageReceieve.equals("")){
////                    languageReceieve = "";
////                }else{
////                    languageReceieve =languageReceieve.substring(0,languageReceieve.length() - 1 );
////                }
//
//                //saveData(result);
//
//                Toast.makeText(context,"AAAA"+languageReceieve,Toast.LENGTH_SHORT).show();
//                temp_string = languageReceieve;
//
//            }
//
//        });
//
//        saveData(temp_string);
//        holder.toggleCheckLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                String result1 = "";
//                if (b  ){
//                    if (holder.titleTextView.hasOnClickListeners()){
//                        languageReceieve += "";
//                    }else{
//                        languageReceieve += languageList.getLanguageCode()+",";
//                    }
//
//                }else{
//                    if ( languageReceieve.equals("")){
//                        languageReceieve = languageList.getLanguageCode()+",";
//                        //Toast.makeText(context,languageReceieve.substring(0,languageReceieve.length()-1),Toast.LENGTH_SHORT).show();
//                    }else{
//                        if ( languageReceieve.equals("")){
//                            languageReceieve = languageList.getLanguageCode()+",";
//                            Toast.makeText(context,languageReceieve,Toast.LENGTH_SHORT).show();
//                        }else{
//                            String temp = "";
//                            temp = languageReceieve.substring(0,languageReceieve.length() - 1 );
//                            if ( temp.equals(languageList.getLanguageCode())){
//                                languageReceieve = "";
//                                Toast.makeText(context,languageReceieve,Toast.LENGTH_SHORT).show();
//                            }else{
//                                String[] split_result = languageReceieve.split(languageList.getLanguageCode()+",");
//                                int weqe = split_result.length;
//                                if ( split_result.length == 1){
//                                    String te =split_result[0];
//                                    languageReceieve = te;
//
//                                }else{
//                                    String fff1 = split_result[0];
//                                    String fff = split_result[1];
//                                    languageReceieve =fff1 +fff;
//                                }
//
//                            }
//
//                            Toast.makeText(context,languageReceieve,Toast.LENGTH_SHORT).show();
//                        }
//
//                        //Toast.makeText(context,languageReceieve.substring(0,languageReceieve.length()-1),Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });

        return convertView;
    }

    public void saveData(String result){
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(context.getString(R.string.sharePre_Language),result).apply();
    }
    public String getdata(){
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.sharePre_Language),"");
    }
    public void filter(String charText) {

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
        //new LanguageListCustomAdapter(context,filteredMovies).notifyDataSetChanged();
    }



    static class ViewHolder {
        TextView titleTextView;
        CheckBox toggleCheckLanguage;
    }
}
