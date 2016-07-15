package com.trainingandroidpart1.physicianregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.trainingandroidpart1.physicianregistration.Adapter.CustomSpinnerAdapter;
import com.trainingandroidpart1.physicianregistration.Adapter.LanguageListCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Response.GetDegreeList.DegreeList;
import com.trainingandroidpart1.physicianregistration.Response.GetDegreeList.MedDegreeList;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.LanguageList;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.MainLanguageListResponse;
import com.trainingandroidpart1.physicianregistration.Response.StandardResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        process();



    }
    public void initView(){
        materialSpinner = (Spinner) findViewById(R.id.my_spinner);
        sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");
    }

    public void process(){
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
        Call<DegreeList> call = serviceAPI.getDegreeList("180");
        call.enqueue(new Callback<DegreeList>() {
            @Override
            public void onResponse(Call<DegreeList> call, Response<DegreeList> response) {
                if (response.body().getSuccess()) {
                    List<MedDegreeList> medDegreeListList = response.body().getMedDegreeList();
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
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DegreeList> call, Throwable t) {

            }
        });
    }
    public void next_to_spcialist_activity(View view) {
        Intent intent = new Intent(DegreeListActivity.this, SpecialistActivity.class);
        startActivity(intent);
         /* start processing to sever */
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
        Call<StandardResponse> call = serviceAPI.saveProviderProfileList(retrieveToken, Long.parseLong(retrieveID), "Degree", degreeID);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.body().getSuccess()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
