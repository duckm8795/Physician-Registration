package com.trainingandroidpart1.physicianregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Adapter.SpecialistAdapter;
import com.trainingandroidpart1.physicianregistration.Entity.SpecialistEntity;
import com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse.MainSpecialList;
import com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse.SpecialtyList;
import com.trainingandroidpart1.physicianregistration.Response.StandardResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablelistview.IndexEntity;
import me.yokeyword.indexablelistview.IndexableStickyListView;
import retrofit2.Call;

public class SpecialistActivity extends AppCompatActivity {
    SpecialistEntity specialityEntity;
    List<SpecialtyList> specialtyList;
    String result_main;
    private IndexableStickyListView mIndexableStickyListView;
    private SearchView mSearchView;
    private SharedPreferences sharedPreferences = null;
    private String retrieveToken = null;
    private String retrieveID = null;
    private String specialtyIDs = null;
    private SpecialistAdapter mAdapter;
    private List<SpecialistEntity> specialityName = new ArrayList<>();
    private ProgressDialog progressDialog;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    String main_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);

        initView();
        new SendRequests().execute();

    }


    public void initView() {
        setTitle("Bằng cấp");

        mIndexableStickyListView = (IndexableStickyListView) findViewById(R.id.indexListView);
        mSearchView = (SearchView) findViewById(R.id.searchview);

        mAdapter = new SpecialistAdapter(SpecialistActivity.this);
        mIndexableStickyListView.setAdapter(mAdapter);


        sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");
    }




    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(R.drawable.ic_error_outline_black_24dp).setTitle("Jio Doctor")
                .setMessage("Bạn có chắc muốn thoát ứng dụng?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Không", null).show();
    }

    class SendRequests extends AsyncTask<Void, Void, Void> {
        MainSpecialList mainSpecialList;

        public SendRequests() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.currentThread();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

            Call<MainSpecialList> call = serviceAPI.getSpecialtyList("vi", "VN", retrieveToken, Long.parseLong(retrieveID));
            try {
                mainSpecialList = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void v) {
            if (mainSpecialList != null) {
                if (mainSpecialList.getSuccess()) {
                    hideLoading();
                    List<Boolean> check_selected = new ArrayList<>();
                    specialtyList = mainSpecialList.getSpecialtyList();
                    for (int i = 0; i < specialtyList.size(); i++) {
                        check_selected.add(false);
                    }
                    for (int i = 0; i < specialtyList.size(); i++) {
                        specialityEntity = new SpecialistEntity(specialtyList.get(i).getDescription(),
                                specialtyList.get(i).getSpecialtyID(), check_selected.get(i));
                        specialityName.add(specialityEntity);

                    }

                    mIndexableStickyListView.bindDatas(specialityName);
                    mIndexableStickyListView.setOnItemContentClickListener(new IndexableStickyListView.OnItemContentClickListener() {
                        @Override
                        public void onItemClick(View v, IndexEntity indexEntity) {
                            specialityEntity = (SpecialistEntity) indexEntity;
                            //Toast.makeText(getApplicationContext(),String.valueOf(specialityEntity.getId()),Toast.LENGTH_LONG).show();
                            CheckBox box = (CheckBox) v.findViewById(R.id.check_speciality_togg);

                            specialityEntity.setSelected(true);
                            if (box.isChecked()) {
                                box.setChecked(false);
                                specialityEntity.setSelected(false);
                            } else {
                                box.setChecked(true);
                            }
                        }
                    });
                    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            // 委托处理搜索
                            mIndexableStickyListView.searchTextChange(newText);
                            return true;
                        }
                    });
                } else {
                    hideLoading();
                    Toast.makeText(getApplicationContext(), mainSpecialList.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                hideLoading();
                showAlertError("Sever out");
            }


        }


    }
    public void next_to_language_activity(View view) {
        view.startAnimation(buttonClick);
        StringBuffer responseText = new StringBuffer();


        for (int i = 0; i < specialityName.size(); i++) {

            if (specialityName.get(i).isSelected()) {
                responseText.append("," + specialtyList.get(i).getSpecialtyID());
            }
        }
        main_string = String.valueOf(responseText);
        if (!main_string.equals("")) {
            main_string = responseText.substring(1, responseText.length());
            new SendRequests2(main_string).execute();
        }else{
            alert();
        }




    }

    class SendRequests2 extends AsyncTask<Void, Void, Void> {
        StandardResponse standardResponse;
        String result ;
        public SendRequests2(String main_string) {
            result = main_string;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();



            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                Thread.currentThread();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

            Call<StandardResponse> call = serviceAPI.updateSpecialityList(result, retrieveToken, Long.parseLong(retrieveID));
            try {
                standardResponse = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Void v) {
            if (standardResponse != null) {
                if (standardResponse.getSuccess()) {
                    hideLoading();
    //                if (result.equals("")){
    //                    alert();
    //                }else{
    //                    startActivity(new Intent(SpecialistActivity.this,LanguageListActivity.class));
    //                }
                    Intent intent = new Intent(SpecialistActivity.this, LanguageListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    SpecialistActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else{
                    hideLoading();
                    Toast.makeText(getApplicationContext(),standardResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            } else {
                hideLoading();
                showAlertError("Sever out");
            }


        }


    }



    public void showLoading() {

        progressDialog = new ProgressDialog(SpecialistActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
    }
    public void showAlertError(String error) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(error)
                .setTitle("Jio Doctor")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Vui lòng chọn ít nhất một chuyên môn.")
                .setTitle("Jio Doctor")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


