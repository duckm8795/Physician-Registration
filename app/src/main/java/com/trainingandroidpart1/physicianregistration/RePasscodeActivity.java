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
import android.widget.Toast;

import com.ngocbeo1121.iospasscode.IOSPasscodeView;
import com.ngocbeo1121.iospasscode.IOSPasscodeViewCallback;
import com.trainingandroidpart1.physicianregistration.Response.SetSecurityPin.SetSecurityPinResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RePasscodeActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences = null;
    private String retrieveToken = null;
    private String retrieveID = null;
    private String retrievePasscode = null;
    private IOSPasscodeView passcodeView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_passcode);

        initView();
        callbackFromIOSPasscode();


    }

    public void callbackFromIOSPasscode() {
        passcodeView.setCallback(new IOSPasscodeViewCallback() {
            @Override
            public boolean onCompleted(IOSPasscodeView passcodeView) {

                if (passcodeView.getPasscode().equals(retrievePasscode)) {
                    new SendRequests().execute();
                    return true;
                } else {
                    Toast.makeText(RePasscodeActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return false;
                }


            }

            @Override
            public void onDigit(IOSPasscodeView passcodeView, int digit, boolean appended) {

            }

            @Override
            public void onDelete(IOSPasscodeView passcodeView) {

            }

            @Override
            public void onCancel(IOSPasscodeView passcodeView) {

            }
        });
    }

    public void showLoading() {

        progressDialog = new ProgressDialog(RePasscodeActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
    }




    public void initView() {
        passcodeView = (IOSPasscodeView) findViewById(R.id.re_passcodeView);

        /* retrieve id and token from Share Pre */
        sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");
        retrievePasscode = sharedPreferences.getString(getString(R.string.store_passcode_string), "");


    }

    public void back_to_parent_activity(View view) {
        Intent intent = new Intent(RePasscodeActivity.this, PasscodeActivity.class);

        startActivity(intent);

    }



    class SendRequests extends AsyncTask<Void, Void, Void> {
        SetSecurityPinResponse setSecurityPinResponse;
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
            ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
            Call<SetSecurityPinResponse> call = serviceAPI.setSecurityPin(Long.parseLong(retrieveID), retrieveToken, retrievePasscode);
            try {
                setSecurityPinResponse =  call.execute().body();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void v) {
            if( setSecurityPinResponse != null){
                if(setSecurityPinResponse.getSuccess()){
                    hideLoading();
                    nextToVerficationActivity();
                }else{
                    hideLoading();
                    Toast.makeText(getApplicationContext(),setSecurityPinResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            }else{
                hideLoading();
                showAlertError("Sever out");
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
    public void nextToVerficationActivity() {
        Intent intent = new Intent(RePasscodeActivity.this, PhysicalVertificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        RePasscodeActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

}
