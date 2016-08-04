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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Adapter.CustomSpinnerAdapter;
import com.trainingandroidpart1.physicianregistration.Response.GetDegreeList.DegreeList;
import com.trainingandroidpart1.physicianregistration.Response.GetDegreeList.MedDegreeList;
import com.trainingandroidpart1.physicianregistration.Response.StandardResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;
import com.trainingandroidpart1.physicianregistration.Service.ServiceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DegreeListActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences = null;
    private String retrieveToken = null;
    private String retrieveID = null;
    private String degreeID = null;
    private Spinner materialSpinner;
    private ProgressDialog progressDialog;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        new SendRequests().execute();


    }

    public void initView() {
        materialSpinner = (Spinner) findViewById(R.id.my_spinner);
        sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");
    }



    //
    class SendRequestsToSpecialty extends AsyncTask<Void, Void, Void> {
        StandardResponse standardResponse;

        public SendRequestsToSpecialty() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
         /* start processing to sever */
            try {
                Thread.currentThread();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            Call<StandardResponse> call = ServiceManager.instance().saveProviderProfileList(retrieveToken, Long.parseLong(retrieveID), "Degree", degreeID);
            try {
                standardResponse = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            if(standardResponse != null){
                if (standardResponse.getSuccess()) {
                    hideLoading();
                    Intent i = new Intent(DegreeListActivity.this, SpecialistActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                    DegreeListActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    hideLoading();
                    Toast.makeText(getApplicationContext(), standardResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }else{
                hideLoading();
                showAlertError("Sever out");
            }



        }


    }

    public void next_to_spcialist_activity(View view) {
        view.startAnimation(buttonClick);
        new SendRequestsToSpecialty().execute();
    }
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
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
        DegreeList degreeList;

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

            Call<DegreeList> call = ServiceManager.instance().getDegreeList("180");
            try {
                degreeList = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            if(degreeList != null){
                if (degreeList.getSuccess()) {
                    hideLoading();

                    List<MedDegreeList> medDegreeListList = degreeList.getMedDegreeList();
                    List<String> medDegreeName = new ArrayList<>();
                    final List<Integer> medDegreeID = new ArrayList<>();
                    for (int i = 0; i < medDegreeListList.size(); i++) {
                        medDegreeName.add(medDegreeListList.get(i).getName());
                        medDegreeID.add(medDegreeListList.get(i).getMedDegreeID());
                    }

                    CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(DegreeListActivity.this, medDegreeName);

                    materialSpinner.setAdapter(customSpinnerAdapter);
                    materialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            degreeID = medDegreeID.get(i).toString();
                            //Toast.makeText(getApplicationContext(), degreeID, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            degreeID = "13";
                        }
                    });

                } else {
                    hideLoading();
                    Toast.makeText(getApplicationContext(), degreeList.getMessage(), Toast.LENGTH_LONG).show();
                }
            }else{
                hideLoading();
                showAlertError("Sever out");
            }



        }


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
    public void showLoading() {

        progressDialog = new ProgressDialog(DegreeListActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
    }

}
