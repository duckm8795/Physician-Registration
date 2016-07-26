package com.trainingandroidpart1.physicianregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class TempHoldPictureActivity extends AppCompatActivity {
    private ImageView tempImage;
    private ImageButton exit;
    private String imageURL;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_hold_picture);
        initView();
        loadImage();
    }

    public void initView() {
        tempImage = (ImageView) findViewById(R.id.imageTempHolder);
        exit = (ImageButton) findViewById(R.id.exit_ic);
        imageURL = getImageFromURL();
    }

    public String getImageFromURL() {
        Intent intent = getIntent();
        return intent.getStringExtra("ImagePathByAmazon");
    }

    public void loadImage() {

        showLoading();
        Picasso.with(TempHoldPictureActivity.this).load(imageURL).rotate(180).into(tempImage, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                hideLoading();
            }

            @Override
            public void onError() {
                hideLoading();
            }
        });
    }

    public void showLoading() {

        progressDialog = new ProgressDialog(TempHoldPictureActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
    }

    public void exit(View view) {
        finish();
    }

    //    public void setImageURL(){
//        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
//        int width = displayMetrics.widthPixels;
//        int height = displayMetrics.heightPixels;
//
////        if (getIntent().getBooleanExtra("NeedRotate", false)) {
////            Picasso.with(getApplicationContext()).load("file://"+ image_path).resize(width,height).rotate(180).centerInside().into(_imv);
////        }else{
////            Picasso.with(getApplicationContext()).load("file://"+ image_path).resize(width,height).centerInside().into(_imv);
////        }
//        Picasso.with(getApplicationContext()).load(getImageFromURL()).into(tempImage);
//    }
}
