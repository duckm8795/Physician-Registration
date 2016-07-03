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

public class PasscodeActivity extends AppCompatActivity {
    IOSPasscodeView passcodeView;
    public static final String USER_ID = "USER_ID";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    long userID;
    String accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        userID = intent.getLongExtra(USER_ID,userID);
        accessToken = intent.getStringExtra(ACCESS_TOKEN);

        passcodeView = (IOSPasscodeView) findViewById(R.id.passcodeView);
        passcodeView.setCallback(new IOSPasscodeViewCallback() {
            @Override
            public boolean onCompleted(final IOSPasscodeView passcodeView) {

                int input_length = passcodeView.getPasscode().length();
                Toast.makeText(PasscodeActivity.this, passcodeView.getPasscode(),Toast.LENGTH_LONG).show();
                if (input_length == 4 ){
                    Intent intent = new Intent(getApplicationContext(),RePasscodeActivity.class);
                    intent.putExtra(RePasscodeActivity.PASS_CODE,passcodeView.getPasscode());
                    intent.putExtra(RePasscodeActivity.RE_USER_ID,userID);
                    intent.putExtra(RePasscodeActivity.RE_ACCESS_TOKEN,accessToken);
                    startActivity(intent);
                }
                return true;

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

}
