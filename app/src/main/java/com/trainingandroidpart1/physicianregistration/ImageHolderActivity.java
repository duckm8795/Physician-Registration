package com.trainingandroidpart1.physicianregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.squareup.picasso.Picasso;
import com.trainingandroidpart1.physicianregistration.Request.CountingFileRequestBody;
import com.trainingandroidpart1.physicianregistration.Response.AvatarResponse.AvatarResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageHolderActivity extends AppCompatActivity {

    public static final String TAG = ImageHolderActivity.class.getSimpleName();
    public CircleProgressBar circleProgressBar;
    ImageView imv_background;
    File file;
    String image_path;
    Bitmap bitmap;
    long totalSize;
    private SharedPreferences sharedPreferences = null;
    private String retrieveToken = null;
    private String retrieveID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_holder);
        initView();


    }

    public void initView() {
        ImageView _imv = (ImageView) findViewById(R.id.imageHolder22);
        imv_background = (ImageView) findViewById(R.id.imageHolder_contain_background);
        circleProgressBar = (CircleProgressBar) findViewById(R.id.upload_progress);

        /* retrieve id and token from Share Pre */
        sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");

        circleProgressBar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        image_path = intent.getStringExtra("ADA");
        checkIfFromGovermentActivity();

//        File pictureFile = new File(image_path);
//        bitmap =Bitmap.createBitmap( 5,
//                5, Bitmap.Config.ARGB_8888);
//        try {
//            FileOutputStream fos = new FileOutputStream(pictureFile);
//            fos.flush();
//            fos.close();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//        } catch (IOException e) {
//        }
//
//        if (_imv != null) {
//            _imv.setImageBitmap(bitmap);
//        }
//
//        Drawable d = new BitmapDrawable(getResources(), bitmap);
//        if (_imv != null) {
//            _imv.setBackground(d);
//        }

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        Picasso.with(getApplicationContext()).load("file://"+ image_path).resize(width,height).centerCrop().into(_imv);


    }

    public void checkIfFromGovermentActivity() {
        if (getIntent().getBooleanExtra("NeedBackground", false)) {
            imv_background.setVisibility(View.VISIBLE);
        }
    }

    public void retake_camera(View view) {
        finish();
    }

    public void use_photo(View view) {
        new UploadPhoto().execute();


    }

    public class UploadPhoto extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            circleProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            file = new File(image_path);
            totalSize = file.length();

            String fileName = "file\"; filename=\"" + file.getName();


            RequestBody requestBody1 = new CountingFileRequestBody(file, "multipart/form-data", new CountingFileRequestBody.ProgressListener() {
                @Override
                public void transferred(long num) {
                    int progressValue = (int) ((num / (float) totalSize) * 100);
                    Log.i("uploadedProgressPercent", " progress is : " + progressValue + " %");
                    publishProgress(progressValue);
                }
            });
            RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), retrieveID);
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), retrieveToken);

            Map<String, RequestBody> requestBodyMap = new HashMap<>();
            requestBodyMap.put("token", token);
            requestBodyMap.put("userID", id);
            requestBodyMap.put(fileName, requestBody1);

            ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

            Call<AvatarResponse> call = serviceAPI.uploadAvatar(requestBodyMap);
            call.enqueue(new Callback<AvatarResponse>() {
                @Override
                public void onResponse(Call<AvatarResponse> call, Response<AvatarResponse> response) {

                    if (response.body().getSuccess()) {
                        Log.d(TAG, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AvatarResponse> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ImageHolderActivity.this, PhysicalVertificationActivity.class);
                    startActivity(intent);
                }
            });
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("SADSAD", String.valueOf(totalSize));
            int circle_value = values[0];
            circleProgressBar.setProgress(circle_value);
            if (circle_value == 100) {
                ;
                //data.putExtra("Hello","SADSADSADSAD");

//                Intent intent = new Intent(ImageHolderActivity.this, PhysicalVertificationActivity.class);
//
//                startActivity(intent);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {


        }
    }

}
