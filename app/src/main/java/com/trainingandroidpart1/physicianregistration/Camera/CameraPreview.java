package com.trainingandroidpart1.physicianregistration.Camera;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.trainingandroidpart1.physicianregistration.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by kieuduc on 17/07/2016.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "TAG";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Bitmap scaled;
    private boolean isFrontCamera;
    private Camera.Size mPreviewSize;
    private List<Camera.Size> mSupportedPreviewSizes;
    private int drawale_resource;



    public CameraPreview(Context context, Camera camera, boolean isFrontCamera,int drawable_id) {
        super(context);
        this.isFrontCamera = isFrontCamera;
        mCamera = camera;
        this.drawale_resource =drawable_id;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setKeepScreenOn(true);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);



        // Try to set camera picture size
        try {
            setCameraRotate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public CameraPreview(Context context, Camera camera, int drawable_id) {
        super(context);
        this.drawale_resource =drawable_id;
        mCamera = camera;

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setKeepScreenOn(true);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);



        // Try to set camera picture size
        try {
            setCameraRotate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {



        scaled = set_background(drawale_resource);
        try {
            setWillNotDraw(false);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void onDraw(Canvas canvas) {
        Paint myPaint = new Paint();
//
        myPaint.setStrokeWidth(5);
        myPaint.setColor(Color.CYAN);
        //canvas.drawRect(75, 510, 1365, (canvas.getHeight()-400), myPaint);
        float deviceHeight = getScreenHeight();
        float deviceWidth = getScreenWidth();
        canvas.drawBitmap(scaled, 0,  0, null ); // draw the background
        //canvas.drawRect(0, deviceWidth/3,  deviceWidth, (float) (deviceHeight/1.75),myPaint);
    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    public Bitmap set_background (int drawable_id){
        Bitmap background = BitmapFactory.decodeResource(getResources(), drawable_id);
        float scale_as_height = (float) background.getHeight() / (float)getHeight();
        float scale_as_width = (float) background.getWidth() / (float)getWidth();
        int newWidth = Math.round(background.getWidth() / scale_as_width);
        int newHeight = Math.round(background.getHeight()/scale_as_height);
        return Bitmap.createScaledBitmap(background, newWidth, newHeight, true);
    }
    private void setCameraRotate() throws Exception {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setRotation(isFrontCamera ? 270 : 90);
        try {
            if (!isFrontCamera) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCamera.setDisplayOrientation(90);

        // Preview size
        if (mPreviewSize != null) {
            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
        }

        mCamera.setParameters(parameters);
    }
    public void updateCameraRotate(int rotate) throws Exception{
        Camera.Parameters parameters = mCamera.getParameters();
        if (isFrontCamera) {
            parameters.setRotation((270 - rotate) % 360);
        } else {
            parameters.setRotation((90 + rotate) % 360);
        }
        mCamera.setParameters(parameters);
    }

}
