package com.trainingandroidpart1.physicianregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ngocbeo1121.iospasscode.IOSPasscodeView;
import com.ngocbeo1121.iospasscode.IOSPasscodeViewCallback;

public class PasscodeActivity extends AppCompatActivity {
    private IOSPasscodeView passcodeView;
    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        initView();
        callBackFromIOSPasscode();

    }


    public void callBackFromIOSPasscode(){
        passcodeView.setCallback(new IOSPasscodeViewCallback() {
            @Override
            public boolean onCompleted(final IOSPasscodeView passcodeView) {

                int input_length = passcodeView.getPasscode().length();
                if (input_length == 4) {
                    sharedPreferences.edit().putString(getString(R.string.store_passcode_string), passcodeView.getPasscode()).apply();

                    nextToRePassCodeActivity();
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

    public void initView() {

        sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);

        passcodeView = (IOSPasscodeView) findViewById(R.id.passcodeView);
    }
    public void nextToRePassCodeActivity(){
        Intent intent = new Intent(getApplicationContext(), RePasscodeActivity.class);
        startActivity(intent);
    }

}
