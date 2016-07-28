package com.trainingandroidpart1.physicianregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class TempHoldPictureActivity extends AppCompatActivity {
    private ImageView tempImage;
    private ImageButton exit;
    private String imageURL;
    private String imageURLForSelfie;
    private ProgressDialog progressDialog;
    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_hold_picture);
        initView();

        if(getIntent().getBooleanExtra("NeedRotateURL",false)){
            loadImageForSelfie();
        }else{
            loadImage();
        }

    }

    public void initView() {
        tempImage = (ImageView) findViewById(R.id.imageTempHolder);

        exit = (ImageButton) findViewById(R.id.exit_ic);
        imageURL = getImageFromURL();
        imageURLForSelfie =getImageFromURLForSelfie();
    }

    public String getImageFromURL() {
        Intent intent = getIntent();
        return intent.getStringExtra("ImagePathByAmazon");
    }

    public String getImageFromURLForSelfie() {
        Intent intent = getIntent();
        return intent.getStringExtra("ImagePathByAmazonForSelfie");
    }
    public void loadImage() {

        showLoading();
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        //tempImage.setScaleType(ImageView.ScaleType.CENTER_CROP);



        Picasso.with(TempHoldPictureActivity.this).load(imageURL).into(tempImage, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                hideLoading();
                if(mAttacher!=null){
                    mAttacher.update();
                }else{
                    mAttacher = new PhotoViewAttacher(tempImage);
                }
            }

            @Override
            public void onError() {
                hideLoading();
            }
        });
    }
    public void loadImageForSelfie() {

        showLoading();

        Picasso.with(TempHoldPictureActivity.this).load(imageURLForSelfie).rotate(180).into(tempImage, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                hideLoading();
                if(mAttacher!=null){
                    mAttacher.update();
                }else{
                    mAttacher = new PhotoViewAttacher(tempImage);
                }
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

}
