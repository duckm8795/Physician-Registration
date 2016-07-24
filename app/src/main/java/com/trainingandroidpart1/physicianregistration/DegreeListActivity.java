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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Adapter.CustomSpinnerAdapter;
import com.trainingandroidpart1.physicianregistration.Response.GetDegreeList.DegreeList;
import com.trainingandroidpart1.physicianregistration.Response.GetDegreeList.MedDegreeList;
import com.trainingandroidpart1.physicianregistration.Response.StandardResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

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

            ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
            Call<StandardResponse> call = serviceAPI.saveProviderProfileList(retrieveToken, Long.parseLong(retrieveID), "Degree", degreeID);
            try {
                standardResponse = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            if (standardResponse.getSuccess()) {
                hideLoading();
                Intent i = new Intent(DegreeListActivity.this, SpecialistActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                DegreeListActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                hideLoading();
                Toast.makeText(getApplicationContext(), standardResponse.getMessage(), Toast.LENGTH_LONG).show();
            }


        }


    }

    public void next_to_spcialist_activity(View view) {
        new SendRequestsToSpecialty().execute();
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
            ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
            Call<DegreeList> call = serviceAPI.getDegreeList("180");
            try {
                degreeList = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void v) {
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


        }


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
