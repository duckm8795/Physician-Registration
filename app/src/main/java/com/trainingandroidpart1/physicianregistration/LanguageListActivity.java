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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.trainingandroidpart1.physicianregistration.Adapter.LanguageListCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Adapter.VerifyPhysicianCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Entity.SpecialistEntity;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.LanguageList;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.MainLanguageListResponse;
import com.trainingandroidpart1.physicianregistration.Response.StandardResponse;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.Main;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguageListActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences= null;
    private String retrieveToken = null;
    private String retrieveID = null;
    private ListView listview;
    private SearchView mSearchView;
    private String language_selected = null;        // data receieved when a item's clicked.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_list);

        initView();
        process();


    }
    public void initView(){


        listview = (ListView) findViewById(R.id.languageListView);
        mSearchView = (SearchView) findViewById(R.id.searchview);

        /* retrieve id and token from Share Pre */
        sharedPreferences= getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken),"");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID),"");
    }
    public void process(){
        /* start processing to sever */
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

        Call<MainLanguageListResponse> call = serviceAPI.getLanguageList("vi","VN",retrieveToken,Long.parseLong(retrieveID));
        call.enqueue(new Callback<MainLanguageListResponse>() {
            @Override
            public void onResponse(Call<MainLanguageListResponse> call, Response<MainLanguageListResponse> response) {
                if ( response.body().getSuccess()){

                    final List<LanguageList> languageLists1 = response.body().getLanguageList();

                    final LanguageListCustomAdapter languageListCustomAdapter = new LanguageListCustomAdapter(LanguageListActivity.this,languageLists1);
                    listview.setAdapter(languageListCustomAdapter);

//                    listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            Toast.makeText(LanguageListActivity.this,"Clicked",Toast.LENGTH_LONG).show();
//                            String temp = languageLists1.get(i).getLanguageCode();
//                            language_selected = temp +",";
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(LanguageListActivity.this,"Clicked",Toast.LENGTH_LONG).show();
//                            String temp = languageLists1.get(i).getLanguageCode();
//                            language_selected = temp +",";
                        }
                    });
                    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            List<LanguageList> result = languageListCustomAdapter.filter(newText);
                            //int aaa = languageListCustomAdapter.getCount();

                            languageListCustomAdapter.notifyDataSetChanged();

                            return true;
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<MainLanguageListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void next_to_main_profile_activity(View view){
        Intent intent = new Intent ( LanguageListActivity.this,MainProfileActivity.class);
        startActivity(intent);
          /* start processing to sever */
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
        Call<StandardResponse> call = serviceAPI.saveLanguage(language_selected, retrieveToken,Long.parseLong(retrieveID));
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
