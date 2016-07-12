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
import android.widget.SearchView;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Adapter.SpecialistAdapter;
import com.trainingandroidpart1.physicianregistration.Adapter.VerifyPhysicianCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Entity.SpecialistEntity;
import com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse.MainSpecialList;
import com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse.SpecialtyList;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.Main;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.indexablelistview.IndexEntity;
import me.yokeyword.indexablelistview.IndexableStickyListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialistActivity extends AppCompatActivity {
    private IndexableStickyListView mIndexableStickyListView;
    private SearchView mSearchView;
    SharedPreferences sharedPreferences= null;
    String retrieveToken = null;
    String retrieveID = null;

    private SpecialistAdapter mAdapter;
    private List<SpecialistEntity> mCities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);

        setTitle("Bằng cấp");

        mIndexableStickyListView = (IndexableStickyListView) findViewById(R.id.indexListView);
        mSearchView = (SearchView) findViewById(R.id.searchview);

        mAdapter = new SpecialistAdapter(getApplicationContext());
        mIndexableStickyListView.setAdapter(mAdapter);


        sharedPreferences= getSharedPreferences("MyPrefer", Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken),"");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID),"");
        Toast.makeText(getApplicationContext(),retrieveID,Toast.LENGTH_LONG).show();
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

        Call<MainSpecialList> call = serviceAPI.getSpecialtyList("vi","VN",retrieveToken,Long.parseLong(retrieveID));
        call.enqueue(new Callback<MainSpecialList>() {
            @Override
            public void onResponse(Call<MainSpecialList> call, Response<MainSpecialList> response) {
                if ( response.body().getSuccess()){

                    List<SpecialtyList> specialtyList = response.body().getSpecialtyList();
                    for ( int i = 0 ; i < specialtyList.size();i++){
                        SpecialistEntity cityEntity = new SpecialistEntity();

                        cityEntity.setName(specialtyList.get(i).getDescription());

                        mCities.add(cityEntity);

                    }
                    mIndexableStickyListView.bindDatas(mCities);
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
                    mIndexableStickyListView.setOnItemContentClickListener(new IndexableStickyListView.OnItemContentClickListener() {
                        @Override
                        public void onItemClick(View v, IndexEntity indexEntity) {
                            SpecialistEntity cityEntity = (SpecialistEntity) indexEntity;
                            Toast.makeText(SpecialistActivity.this, cityEntity.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    mIndexableStickyListView.setOnItemTitleClickListener(new IndexableStickyListView.OnItemTitleClickListener() {
                        @Override
                        public void onItemClick(View v, String title) {
                            Toast.makeText(SpecialistActivity.this, title, Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }

            @Override
            public void onFailure(Call<MainSpecialList> call, Throwable t) {

            }
        });

    //    int ssss = mCities.size();


        // 绑定数据
     //   mIndexableStickyListView.bindDatas(mCities);

        // 搜索

    }



    protected void initView() {
        setTitle("Bằng cấp");

        mIndexableStickyListView = (IndexableStickyListView) findViewById(R.id.indexListView);
        mSearchView = (SearchView) findViewById(R.id.searchview);

        mAdapter = new SpecialistAdapter(getApplicationContext());
        mIndexableStickyListView.setAdapter(mAdapter);


        sharedPreferences= getSharedPreferences("MyPrefer", Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken),"");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID),"");
        Toast.makeText(getApplicationContext(),retrieveID,Toast.LENGTH_LONG).show();
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

        Call<MainSpecialList> call = serviceAPI.getSpecialtyList("vi","VN",retrieveToken,Long.parseLong(retrieveID));
        call.enqueue(new Callback<MainSpecialList>() {
            @Override
            public void onResponse(Call<MainSpecialList> call, Response<MainSpecialList> response) {
                if ( response.body().getSuccess()){

                    List<SpecialtyList> specialtyList = response.body().getSpecialtyList();
                    for ( int i = 0 ; i < specialtyList.size();i++){
                        SpecialistEntity cityEntity = new SpecialistEntity();

                        cityEntity.setName(specialtyList.get(i).getDescription());

                        mCities.add(cityEntity);
                    }


                }


            }

            @Override
            public void onFailure(Call<MainSpecialList> call, Throwable t) {

            }
        });




        // 绑定数据
        mIndexableStickyListView.bindDatas(mCities);

        // 搜索
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
        mIndexableStickyListView.setOnItemContentClickListener(new IndexableStickyListView.OnItemContentClickListener() {
            @Override
            public void onItemClick(View v, IndexEntity indexEntity) {
                SpecialistEntity cityEntity = (SpecialistEntity) indexEntity;
                Toast.makeText(SpecialistActivity.this, cityEntity.getName(), Toast.LENGTH_SHORT).show();
            }
        });


        mIndexableStickyListView.setOnItemTitleClickListener(new IndexableStickyListView.OnItemTitleClickListener() {
            @Override
            public void onItemClick(View v, String title) {
                Toast.makeText(SpecialistActivity.this, title, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void initCities() {
        sharedPreferences= getSharedPreferences("MyPrefer", Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken),"");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID),"");
        Toast.makeText(getApplicationContext(),retrieveID,Toast.LENGTH_LONG).show();
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

        Call<MainSpecialList> call = serviceAPI.getSpecialtyList("vi","VN",retrieveToken,Long.parseLong(retrieveID));
        call.enqueue(new Callback<MainSpecialList>() {
            @Override
            public void onResponse(Call<MainSpecialList> call, Response<MainSpecialList> response) {
                if ( response.body().getSuccess()){

                    List<SpecialtyList> specialtyList = response.body().getSpecialtyList();
                    for ( int i = 0 ; i < specialtyList.size();i++){
                        SpecialistEntity cityEntity = new SpecialistEntity();

                        cityEntity.setName(specialtyList.get(i).getDescription());

                        mCities.add(cityEntity);
                    }


                }


            }

            @Override
            public void onFailure(Call<MainSpecialList> call, Throwable t) {

            }
        });

    }
//    private void initCities2() {
//        List<String> cityStrings = Arrays.asList(getResources().getStringArray(R.array.city_array));
//        for (String item : cityStrings) {
//            Entity cityEntity = new Entity();
//            cityEntity.setName(item);
//            mCities.add(cityEntity);
//        }
//    }
    public void next_to_language_activity(View view){
        Intent intent  = new Intent( SpecialistActivity.this,LanguageListActivity.class);
        startActivity(intent);
    }
}
