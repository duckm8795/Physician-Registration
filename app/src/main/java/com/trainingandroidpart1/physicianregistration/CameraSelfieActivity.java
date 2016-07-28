package com.trainingandroidpart1.physicianregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Camera.CameraPreview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraSelfieActivity extends AppCompatActivity {


    private int cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private String pathImg;
    private Camera mCamera;
    private CameraPreview mPreview;
    private LayoutInflater controlInflater = null;
    private Context context;
    private ImageView captureButton;
    private FrameLayout preview_layout;
    private ProgressDialog progressDialog;
    private ImageButton ic_switch_camera;
    private ImageButton ic_flash;
    private boolean hasFlash = false;
    private boolean isLighOn = false;
    private boolean isFrontCamera;
    private int cameraRotate = 0;
    private boolean needRotate = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout__camera_general);

        initView();


        if (preview_layout != null) {
            preview_layout.addView(mPreview);
        }

        if (captureButton != null) {
            captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mPreview.updateCameraRotate(cameraRotate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mCamera.takePicture(null, null, pictureCallback);
                }
            });
        }


    }

    public void initView() {
        hasFlash = this.getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);





        preview_layout = (FrameLayout) findViewById(R.id.center_view);

        ic_switch_camera = (ImageButton) findViewById(R.id.ic_switch_camera);
        ic_flash = (ImageButton) findViewById(R.id.ic_flash);
        ic_flash.setVisibility(View.GONE);
        ic_switch_camera.setVisibility(View.GONE);

        captureButton = (ImageView) findViewById(R.id.capture_image);

        mCamera = getCameraFront(cameraId);
        mPreview = new CameraPreview(CameraSelfieActivity.this, mCamera, R.drawable.selfie_icon);

        create_flow_text();

    }

    public static Camera getCameraFront(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }



    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }



    public void create_flow_text() {
        // Create an flow text of Camera
        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.custom_text_onto_camera, null);

        ViewGroup.LayoutParams layoutParamsControl
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addContentView(viewControl, layoutParamsControl);
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private void addCameraPreview() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mPreview.setLayoutParams(layoutParams);
        preview_layout.addView(mPreview);
    }

    private boolean isFrontCamera() {
        return cameraId != Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    public void switch_flash(View view) {
        if (mCamera != null && hasFlash) {
            Camera.Parameters p = mCamera.getParameters();
            if (isLighOn) {
                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(p);
                mCamera.startPreview();
                isLighOn = false;

            } else {
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(p);
                mCamera.startPreview();
                isLighOn = true;
            }
        } else{
            Toast.makeText(getApplicationContext(), "Sorry, Camera trước của bạn không hỗ trợ đèn flash!", Toast.LENGTH_LONG).show();
        }
    }

    public void switch_camera(View view) {
        view.setEnabled(false);

        // Release current camera
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        if (preview_layout != null) {
            preview_layout.removeAllViewsInLayout();
            preview_layout.removeView(mPreview);
        }

        // Create new camera
        if (!isFrontCamera()) {
            cameraId = findFrontFacingCamera();
        } else {
            cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        mCamera = getCameraFront(cameraId);
        if (mCamera != null) {
            mPreview = new CameraPreview(getApplicationContext(), mCamera, isFrontCamera(), R.drawable.selfie_icon);
            addCameraPreview();
        }
        view.setEnabled(true);
    }

    public void showLoading() {
        progressDialog = new ProgressDialog(CameraSelfieActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void finish_camera(View view) {
        finish();
    }



    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            new TakePhoto(data).execute();

        }
    };
    class TakePhoto extends AsyncTask<Void, Void, Void> {
        byte[] data;


        public TakePhoto(byte[] dt) {
            data = dt;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            File pictureFile = getOutputMediaFile();
            if (pictureFile != null) {
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    pathImg = pictureFile.getPath();
                    fos.write(data);
                    fos.close();
                } catch (IOException e) {
                }
            }

            return null;

        }

        protected void onPostExecute(Void v) {
            hideLoading();

            Intent i = new Intent(CameraSelfieActivity.this, ImageHolderActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            i.putExtra("IDCamera",1);
            i.putExtra("ADA", pathImg);
            i.putExtra("NeedRotate",needRotate);
            startActivity(i);
            CameraSelfieActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }


    }


}
