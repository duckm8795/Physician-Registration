package com.trainingandroidpart1.physicianregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ngocbeo1121.iospasscode.IOSPasscodeView;
import com.ngocbeo1121.iospasscode.IOSPasscodeViewCallback;
import com.trainingandroidpart1.physicianregistration.Response.SetSecurityPin.SetSecurityPinResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RePasscodeActivity extends AppCompatActivity {
    public static final String PASS_CODE = "PASSCODE";
    public static final String RE_USER_ID = "RE_USER_ID";
    public static final String RE_ACCESS_TOKEN = "RE_ACCESS_TOKEN";
    long userID;
    String accessToken;
    String  passcode_text;
    IOSPasscodeView passcodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_passcode);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        userID = intent.getLongExtra(RE_USER_ID,userID);
        accessToken = intent.getStringExtra(RE_ACCESS_TOKEN);
        passcode_text = intent.getStringExtra(PASS_CODE);

        passcodeView = (IOSPasscodeView) findViewById(R.id.re_passcodeView);
        passcodeView.setCallback(new IOSPasscodeViewCallback() {
            @Override
            public boolean onCompleted(IOSPasscodeView passcodeView) {

                if ( passcodeView.getPasscode().equals(passcode_text)){
                    Toast.makeText(RePasscodeActivity.this,"Sucusssee",Toast.LENGTH_LONG).show();
                    ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
                    Call<SetSecurityPinResponse> call = serviceAPI.setSecurityPin(userID,accessToken,passcodeView.getPasscode());
                    call.enqueue(new Callback<SetSecurityPinResponse>() {
                        @Override
                        public void onResponse(Call<SetSecurityPinResponse> call, Response<SetSecurityPinResponse> response) {
                            if( response.body().getSuccess()){
                                Intent intent = new Intent(RePasscodeActivity.this,PhysicalVertificationActivity.class);
                                intent.putExtra(PhysicalVertificationActivity.VERIFY_USER_ID,userID);
                                intent.putExtra(PhysicalVertificationActivity.VERIFY_ACCESS_TOKEN,accessToken);
                                startActivity(intent);

                            }

                        }

                        @Override
                        public void onFailure(Call<SetSecurityPinResponse> call, Throwable t) {

                        }
                    });
                    return  true;
                }else{
                    Toast.makeText(RePasscodeActivity.this,"Failed",Toast.LENGTH_LONG).show();
                    return  false;
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
    public void back_to_parent_activity (View view){
        Intent intent = new Intent(RePasscodeActivity.this,PasscodeActivity.class);

        startActivity(intent);

    }


}
