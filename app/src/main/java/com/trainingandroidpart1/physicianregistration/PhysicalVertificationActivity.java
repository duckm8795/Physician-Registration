package com.trainingandroidpart1.physicianregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.trainingandroidpart1.physicianregistration.Adapter.VerifyPhysicianCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Response.GetProfile.GetProfileResponse;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.Main;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysicalVertificationActivity extends AppCompatActivity {

    private static final int REQUEST = 1;
    private SharedPreferences sharedPreferences;
    private String retrieveToken = null;
    private String retrieveID = null;
    private ListView listview;
    private TextView textView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_vertification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        new SendRequests().execute();


    }

    public void process() {

        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

        /* get documents */
        Call<Main> call = serviceAPI.verify(180, "vi", "VN", retrieveToken, Long.parseLong(retrieveID));
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                if (response.body().getSuccess()) {

                    final List<VerificationDocType> verificationDocTypes = response.body().getVerificationDocTypes();

                    listview.setAdapter(new VerifyPhysicianCustomAdapter(PhysicalVertificationActivity.this, verificationDocTypes));
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int qd = verificationDocTypes.get(position).getDocTypeId();
                            if (qd == 1) {
                                next_to_selfie_camera();
                            } else if (qd == 2) {
                                next_to_govermentID_camera();
                            } else {
                                next_to_choose_card_camera();
                            }
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {

            }
        });

        /* get full name */
        Call<GetProfileResponse> call1 = serviceAPI.getProfile(retrieveToken, Long.parseLong(retrieveID));
        call1.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.body().getSuccess()) {
                    textView.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {

            }
        });
    }

    public void next_to_selfie_camera() {
        Intent intent = new Intent(PhysicalVertificationActivity.this, CameraSelfieActivity.class);
        startActivity(intent);
        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void next_to_govermentID_camera() {
        Intent intent = new Intent(PhysicalVertificationActivity.this, CameraGovermentActivity.class);

        startActivityForResult(intent, REQUEST);
        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void next_to_choose_card_camera() {
        Intent intent = new Intent(PhysicalVertificationActivity.this, CameraChooseOptionCardActivity.class);
        startActivity(intent);
        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            // Make sure the request was successful
            Log.d("QKDQWOEIQWOE", "ghghggh");
//                Toast.makeText(getApplicationContext(), "ÁDASASASA",
//                        Toast.LENGTH_SHORT).show();

        }
    }

    public void initView() {
        listview = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.namePhysician);

        sharedPreferences = getSharedPreferences("MyPrefer", Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");

        TextView intro_string_vp1 = (TextView) findViewById(R.id.intro_string_vp1);
        TextView intro_string_vp2 = (TextView) findViewById(R.id.intro_string_vp2);
        TextView namePhysician = (TextView) findViewById(R.id.namePhysician);

        Typeface font_medium = Typeface.createFromAsset(getAssets(), "Ubuntu-Regular.ttf");
        intro_string_vp1.setTypeface(font_medium);
        intro_string_vp2.setTypeface(font_medium);
        namePhysician.setTypeface(font_medium);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.verify_physician_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_continue) {
            showAlert();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn vẫn chưa tải lên bất cứ loại giấy tờ hay bằng cấp nào.Bạn muốn tải chúng lên ngay bây giờ chứ?")
                .setTitle("Jio Doctor")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Để sau ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PhysicalVertificationActivity.this, AvatarPhysicalActivity.class);
                        startActivity(intent);
                        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showLoading() {

        progressDialog = new ProgressDialog(PhysicalVertificationActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
    }

    //    public class UploadPhoto extends AsyncTask<Void,Integer,Void> {
//
//        @Override
//        protected void onPreExecute() {
//            circleProgressBar = (CircleProgressBar) findViewById(R.id.upload_progress);
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            file = new File(image_path);
//            final long totalSize = file.length();
//            String fileName = "file\"; filename=\"" + file.getName();
//
////        RequestBody requestBody =  RequestBody.create(new CountingFileRequestBody(file, "multipart/form-data", new CountingFileRequestBody.ProgressListener() {
////            @Override
////            public void transferred(long num) {
////                int progressValue = (int) ((num / (float) totalSize) * 100);
////                Log.i("uploadedProgressPercent", " progress is : " + progressValue + " %");
////
////            }
////        }));
//
//            RequestBody requestBody1 = new CountingFileRequestBody(file, "multipart/form-data", new CountingFileRequestBody.ProgressListener() {
//                @Override
//                public void transferred(long num) {
//                    int progressValue = (int) ((num / (float) totalSize) * 100);
//                    Log.i("uploadedProgressPercent", " progress is : " + progressValue + " %");
//                    publishProgress(progressValue);
//                }
//            });
//            RequestBody id =  RequestBody.create(MediaType.parse("multipart/form-data"), "6587");
//            RequestBody token =  RequestBody.create(MediaType.parse("multipart/form-data"), "92fdf844-ee91-4765-a1a7-75a8dff14563");
//
//            Map<String, RequestBody> requestBodyMap = new HashMap<>();
//            requestBodyMap.put("token",token);
//            requestBodyMap.put("userID", id);
//            requestBodyMap.put(fileName, requestBody1);
//
//            ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
//
//            Call<AvatarResponse> call = serviceAPI.uploadAvatar(requestBodyMap);
//            call.enqueue(new Callback<AvatarResponse>() {
//                @Override
//                public void onResponse(Call<AvatarResponse> call, Response<AvatarResponse> response) {
//
//                    if ( response.body().getSuccess()){
//                        Log.d(TAG,response.body().getMessage());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<AvatarResponse> call, Throwable t) {
//                    Log.d(TAG,t.getMessage());
//
//                }
//            });
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            int circle_value = values[0];
//            circleProgressBar.setProgress(circle_value);
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            Log.d(TAG,"Sucesss upload");
//        }
//    }
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
}
