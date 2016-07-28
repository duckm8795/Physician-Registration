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



        Intent intent = getIntent();
        image_path = intent.getStringExtra("ADA");

        checkIfFromGovermentActivity();
        Bitmap bitmap = BitmapFactory.decodeFile(image_path);
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

        if (getIntent().getBooleanExtra("NeedRotate", false)) {
            Picasso.with(getApplicationContext()).load("file://"+ image_path).resize(width,height).rotate(180).centerInside().into(_imv);
        }else{
            Picasso.with(getApplicationContext()).load("file://"+ image_path).resize(width,height).centerInside().into(_imv);
        }


//        _imv.setImageBitmap(scaleDownBitmapImage(bitmap,width,height));
//        _imv.setRotation(270);
    }

    public void checkIfFromGovermentActivity() {
        if (getIntent().getBooleanExtra("NeedBackground", false)) {
            imv_background.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.image_path_string),image_path);
        setResult(RESULT_OK, intent);
        finish();


    }



}
