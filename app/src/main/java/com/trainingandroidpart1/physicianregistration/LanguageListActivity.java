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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Adapter.LanguageListCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.LanguageList;
import com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse.MainLanguageListResponse;
import com.trainingandroidpart1.physicianregistration.Response.StandardResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguageListActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences = null;
    private String retrieveToken = null;
    private String retrieveID = null;
    private ListView listview;
    private SearchView mSearchView;
    private CheckBox checkBox;
    private String retrieve_language_selected = "";        // data receieved when a item's clicked.
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_list);

        initView();
        new SendRequests().execute();


    }

    public void initView() {


        listview = (ListView) findViewById(R.id.languageListView);

        mSearchView = (SearchView) findViewById(R.id.searchview);
        checkBox = (CheckBox) findViewById(R.id.btn_check_language);
        /* retrieve id and token from Share Pre */
        sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");
        retrieve_language_selected = sharedPreferences.getString(getString(R.string.sharePre_Language), "");
    }

    public void process() {
        /* start processing to sever */
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

        Call<MainLanguageListResponse> call = serviceAPI.getLanguageList("vi", "VN", retrieveToken, Long.parseLong(retrieveID));
        call.enqueue(new Callback<MainLanguageListResponse>() {
            @Override
            public void onResponse(Call<MainLanguageListResponse> call, Response<MainLanguageListResponse> response) {
                if (response.body().getSuccess()) {

                    final List<LanguageList> languageLists1 = response.body().getLanguageList();

                    final LanguageListCustomAdapter languageListCustomAdapter = new LanguageListCustomAdapter(LanguageListActivity.this, languageLists1);

                    listview.setAdapter(languageListCustomAdapter);


                    /* implement search view*/
                    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            //listview.setFilterText(newText);
                            return true;
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<MainLanguageListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void showLoading() {

        progressDialog = new ProgressDialog(LanguageListActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
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
         /* start processing to sever */
            ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

            Call<MainLanguageListResponse> call = serviceAPI.getLanguageList("vi", "VN", retrieveToken, Long.parseLong(retrieveID));
            call.enqueue(new Callback<MainLanguageListResponse>() {
                @Override
                public void onResponse(Call<MainLanguageListResponse> call, Response<MainLanguageListResponse> response) {
                    if (response.body().getSuccess()) {

                        final List<LanguageList> languageLists1 = response.body().getLanguageList();

                        final LanguageListCustomAdapter languageListCustomAdapter = new LanguageListCustomAdapter(LanguageListActivity.this, languageLists1);

                        listview.setAdapter(languageListCustomAdapter);


                    /* implement search view*/
                        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {

                                //listview.setFilterText(newText);
                                return true;
                            }
                        });
                    }


                }

                @Override
                public void onFailure(Call<MainLanguageListResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            return null;
        }

        protected void onPostExecute(Void v) {
            hideLoading();

        }


    }
    public void next_to_main_profile_activity(View view) {
//        if (retrieve_language_selected.equals("")){
//            alert();
//        }else{
        Intent intent = new Intent(LanguageListActivity.this, MainProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        LanguageListActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
          /* start processing to sever */

        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
        Call<StandardResponse> call = serviceAPI.saveLanguage(retrieve_language_selected, retrieveToken, Long.parseLong(retrieveID));
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.body().getSuccess()) {
                    Toast.makeText(getApplicationContext(), retrieve_language_selected, Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
//        }


    }

    public void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Vui lòng chọn ít nhất một ngôn ngữ.")
                .setTitle("Jio Doctor")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
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
