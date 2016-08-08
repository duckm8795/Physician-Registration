package com.trainingandroidpart1.physicianregistration;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Adapter.VerifyPhysicianCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Response.GetOpenDoctorDetailForProvider.MainResponse;
import com.trainingandroidpart1.physicianregistration.Response.GetOpenDocumentUploadURL.GetOpenDocumentUploadURLResponse;
import com.trainingandroidpart1.physicianregistration.Response.GetProfile.GetProfileResponse;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.Main;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;
import com.trainingandroidpart1.physicianregistration.Service.ServiceManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class SplashScreenActivity extends AppCompatActivity {

    /** Duration of wait **/
    //private final int SPLASH_DISPLAY_LENGTH = 4000;
    private ProgressDialog progressDialog;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        Class<?> activityClass;

        try {
            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
            String result =  prefs.getString("lastActivity", MainActivity.class.getName());
            activityClass = Class.forName(
                    prefs.getString("lastActivity", MainActivity.class.getName()));
                    Log.i("LastActivity",result);
        } catch(ClassNotFoundException ex) {
            activityClass = MainActivity.class;
        }

        startActivity(new Intent(this, activityClass));
        SplashScreenActivity.this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();

        //setContentView(R.layout.activity_splash_screen);

//        /* New Handler to start the Menu-Activity
//         * and close this Splash-Screen after some seconds.*/
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(SplashScreenActivity.this,MainActivity.class);
//                SplashScreenActivity.this.startActivity(mainIntent);
//                SplashScreenActivity.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
    }
//    class SendRequests extends AsyncTask<Void, Void, Void> {
//        Main main;
//        MainResponse responseForGetOpenDoctorDetail;
//        GetProfileResponse getProfileResponse;
//        public SendRequests() {
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            showLoading();
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            try {
//                Thread.currentThread();
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            Call<GetProfileResponse> callProfile = ServiceManager.instance().getProfile(retrieveToken, Long.parseLong(retrieveID));
//            Call<Main> callDocument = ServiceManager.instance().verify(180, "vi", "VN", retrieveToken, Long.parseLong(retrieveID));
//            Call<MainResponse> callGetOpenDoctorDetail  = ServiceManager.instance().getOpenDoctorDetailForProvider(retrieveToken,Long.parseLong(retrieveID));
//            try {
//                getProfileResponse = callProfile.execute().body();
//                main = callDocument.execute().body();
//                //responseForGetOpenDoctorDetail= callGetOpenDoctorDetail.execute().body();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        protected void onPostExecute(Void v) {
//             /* for profile response */
//            if( getProfileResponse == null ){
//                hideLoading();
//                showAlertError("Sever out");
//
//            }else{
//                if(getProfileResponse.getSuccess()){
//                    hideLoading();
//                    textView.setText(getProfileResponse.toString());
//                }else{
//                    hideLoading();
//                    Toast.makeText(getApplicationContext(),getProfileResponse.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            }
//
//            /* for document response */
//            if(main == null){
//                hideLoading();
//                showAlertError("Sever out");
//            }else{
//                if(main.getSuccess()){
//                    hideLoading();
//                    final List<VerificationDocType> verificationDocTypes = main.getVerificationDocTypes();
//
//                    listview.setAdapter(new VerifyPhysicianCustomAdapter(PhysicalVertificationActivity.this, verificationDocTypes));
//                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            int qd = verificationDocTypes.get(position).getDocTypeId();
//                            if (qd == 1) {
//                                if (hasUpdoadSelfieImageverificationDocTypesOrNot) {
//                                    next_to_taken_picture_for_selfie(image_path_url_amazon);
//                                } else {
//                                    next_to_selfie_camera("1");
//                                }
//
//                            } else if (qd == 2) {
//                                if (hasUpdoadGovermentImageOrNot) {
//                                    next_to_taken_picture(image_path_url_amazon2);
//                                } else {
//                                    next_to_govermentID_camera("2");
//                                }
//
//                            } else {
//
//                                if (hasUpdoadCardOrSheetImageOrNot) {
//                                    next_to_taken_picture(image_path_url_amazon3);
//                                } else {
//                                    next_to_choose_card_camera("3");
//                                }
//
//
//                            }
//                        }
//                    });
//                }else{
//                    hideLoading();
//                    Toast.makeText(getApplicationContext(),main.getMessage(),Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            /* for get open doctor detail */
//            if( responseForGetOpenDoctorDetail == null ){
//                hideLoading();
//                showAlertError("Sever out");
//
//            }else{
//                if(responseForGetOpenDoctorDetail.getSuccess()){
//                    hideLoading();
//
//                }else{
//                    hideLoading();
//                    Toast.makeText(getApplicationContext(),responseForGetOpenDoctorDetail.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            }
//
//        }
//
//
//    }
//    public void showLoading() {
//
//        progressDialog = new ProgressDialog(PhysicalVertificationActivity.this);
//        progressDialog.setMessage("Đang xử lý ...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//    }
//
//    public void hideLoading() {
//
//        progressDialog.dismiss();
//    }
//    public void showAlertError(String error) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(error)
//                .setTitle("Jio Doctor")
//                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
}
