package com.trainingandroidpart1.physicianregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
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
import com.trainingandroidpart1.physicianregistration.Response.GetOpenDocumentUploadURL.GetOpenDocumentUploadURLResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;
import com.trainingandroidpart1.physicianregistration.Service.ServiceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageHolderActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    public CircleProgressBar circleProgressBar;

    private String retrieveToken = null;
    private String retrieveID = null;
    private String retrieve_guid;
    private String image_path_cropped;
    private String image_path;
    private String image_path_original;

    private ImageView imv_background;

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
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");



        Intent intent = getIntent();
        image_path = intent.getStringExtra("ADA");
        //image_path_original = intent.getStringExtra("OriginalImg");
        checkIfFromGovermentActivity();





        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        if (getIntent().getBooleanExtra("NeedRotate", false)) {
            Picasso.with(getApplicationContext()).load("file://"+ image_path).resize(width,height).rotate(180).centerInside().into(_imv);
        }else{
            Picasso.with(getApplicationContext()).load("file://"+ image_path).resize(width,height).centerInside().into(_imv);
        }


    }

    public void checkIfFromGovermentActivity() {
        if (getIntent().getBooleanExtra("NeedBackground", false)) {
            imv_background.setVisibility(View.VISIBLE);
            //image_path = image_path_original;

        }
    }

    public void retake_camera(View view) {
//        int id = getIntent().getIntExtra("IDCamera",1);
//        if(id == 1){
//            Intent intent = new Intent(ImageHolderActivity.this,CameraSelfieActivity.class);
//            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//
//
//        }else if ( id == 2){
//            Intent intent = new Intent(ImageHolderActivity.this,CameraGovermentActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }else if (id == 3){
//            Intent intent = new Intent(ImageHolderActivity.this,CameraCardActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }else{
//            Intent intent = new Intent(ImageHolderActivity.this,CameraSheetActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }

        finish();
    }

    public void use_photo(View view) {
        new sendRequestGetOpenUploadURL().execute();

    }

    class sendRequestGetOpenUploadURL extends AsyncTask<Void,Void,Void>{
        GetOpenDocumentUploadURLResponse uploadURLResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showLoading();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Call<GetOpenDocumentUploadURLResponse> call = ServiceManager.instance().getOpenDocumentUploadURL(retrieveToken,Long.parseLong(retrieveID));

            try {
                uploadURLResponse = call.execute().body();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {


            /* for get open doctor detail */
            if( uploadURLResponse == null ){
                Toast.makeText(getApplicationContext(),"Sever out",Toast.LENGTH_LONG).show();
                //showAlertError("Sever out");

            }else{
                if(uploadURLResponse.isSuccess()){
                    //hideLoading();
                    retrieve_guid = uploadURLResponse.getGuid();
                    Intent intent = new Intent();
                    intent.putExtra(getString(R.string.image_path_string),image_path);
                    intent.putExtra(getString(R.string.image_path_cropped_string),image_path_cropped);
                    intent.putExtra("SendGUID",retrieve_guid);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    hideLoading();
                    Toast.makeText(getApplicationContext(),uploadURLResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
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

        progressDialog = new ProgressDialog(ImageHolderActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
    }

}
