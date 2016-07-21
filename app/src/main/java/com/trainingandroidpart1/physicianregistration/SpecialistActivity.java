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
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Adapter.LanguageListCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Adapter.SpecialistAdapter;
import com.trainingandroidpart1.physicianregistration.Entity.SpecialistEntity;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.LanguageList;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.MainLanguageListResponse;
import com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse.MainSpecialList;
import com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse.SpecialtyList;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablelistview.IndexEntity;
import me.yokeyword.indexablelistview.IndexableStickyListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialistActivity extends AppCompatActivity {
    private IndexableStickyListView mIndexableStickyListView;
    private SearchView mSearchView;
    private SharedPreferences sharedPreferences = null;
    private String retrieveToken = null;
    private String retrieveID = null;
    private String specialtyIDs = null;
    private SpecialistAdapter mAdapter;
    private List<SpecialistEntity> specialityName = new ArrayList<>();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);

        initView();
        new SendRequests().execute();

    }

    public void process() {
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

        Call<MainSpecialList> call = serviceAPI.getSpecialtyList("vi", "VN", retrieveToken, Long.parseLong(retrieveID));
        call.enqueue(new Callback<MainSpecialList>() {
            @Override
            public void onResponse(Call<MainSpecialList> call, Response<MainSpecialList> response) {
                if (response.body().getSuccess()) {

                    List<SpecialtyList> specialtyList = response.body().getSpecialtyList();

                    for (int i = 0; i < specialtyList.size(); i++) {
                        SpecialistEntity specialityEntity = new SpecialistEntity(specialtyList.get(i).getDescription(),
                                specialtyList.get(i).getSpecialtyID());
                        specialityName.add(specialityEntity);

                    }

                    mIndexableStickyListView.bindDatas(specialityName);
                    mIndexableStickyListView.setOnItemContentClickListener(new IndexableStickyListView.OnItemContentClickListener() {
                        @Override
                        public void onItemClick(View v, IndexEntity indexEntity) {
                            CheckBox a = (CheckBox) v;

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


                }


            }

            @Override
            public void onFailure(Call<MainSpecialList> call, Throwable t) {

            }
        });

    }

    public void initView() {
        setTitle("Bằng cấp");

        mIndexableStickyListView = (IndexableStickyListView) findViewById(R.id.indexListView);
        mSearchView = (SearchView) findViewById(R.id.searchview);

        mAdapter = new SpecialistAdapter(getApplicationContext());
        mIndexableStickyListView.setAdapter(mAdapter);


        sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");
    }

    public void next_to_language_activity(View view) {

        Intent intent = new Intent(SpecialistActivity.this, LanguageListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        SpecialistActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//         /* start processing to sever */
//        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
//        Call<StandardResponse> call = serviceAPI.updateSpecialityList(specialtyIDs,retrieveToken, Long.parseLong(retrieveID));
//        call.enqueue(new Callback<StandardResponse>() {
//            @Override
//            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
//                if (response.body().getSuccess()) {
//                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<StandardResponse> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }
    class SendRequests extends AsyncTask<Void, Void, Void> {

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
         process();
            return null;
        }

        protected void onPostExecute(Void v) {
            hideLoading();

        }


    }
    public void showLoading() {

        progressDialog = new ProgressDialog(SpecialistActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
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
}
