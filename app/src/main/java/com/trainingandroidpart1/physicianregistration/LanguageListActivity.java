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
import android.widget.ListView;
import android.widget.SearchView;

import com.trainingandroidpart1.physicianregistration.Adapter.LanguageListCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Adapter.VerifyPhysicianCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Entity.SpecialistEntity;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.LanguageList;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.MainLanguageListResponse;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.Main;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguageListActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences= null;
    String retrieveToken = null;
    String retrieveID = null;
    ListView listview;
    SearchView mSearchView;
     //List<LanguageList> languageLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_list);
        listview = (ListView) findViewById(R.id.languageListView);
        mSearchView = (SearchView) findViewById(R.id.searchview);
        sharedPreferences= getSharedPreferences("MyPrefer", Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken),"");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID),"");

        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

        Call<MainLanguageListResponse> call = serviceAPI.getLanguageList("vi","VN",retrieveToken,Long.parseLong(retrieveID));
        call.enqueue(new Callback<MainLanguageListResponse>() {
            @Override
            public void onResponse(Call<MainLanguageListResponse> call, Response<MainLanguageListResponse> response) {
                if ( response.body().getSuccess()){

                    List<LanguageList> languageLists1 = response.body().getLanguageList();

                    listview.setAdapter(new LanguageListCustomAdapter(LanguageListActivity.this, languageLists1));

                }


            }

            @Override
            public void onFailure(Call<MainLanguageListResponse> call, Throwable t) {

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
                //listview.searchTextChange(newText);
                return true;
            }
        });
    }
    public void next_to_main_profile_activity(View view){
        Intent intent = new Intent ( LanguageListActivity.this,MainProfileActivity.class);
        startActivity(intent);
    }

}
