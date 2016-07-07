package com.trainingandroidpart1.physicianregistration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Response.CreateProviderAccount.CreateProviderAccountResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.string_logo);
        TextView text1 = (TextView) findViewById(R.id.textView1);
        TextView text2 = (TextView) findViewById(R.id.textView2);
        TextView text3 = (TextView) findViewById(R.id.textView3);
        TextView text4 = (TextView) findViewById(R.id.textView4);
        TextView text5 = (TextView) findViewById(R.id.textView5);
        TextView text6 = (TextView) findViewById(R.id.textView6);


        Typeface font = Typeface.createFromAsset(getAssets(), "Ubuntu-Light.ttf");
        Typeface font_medium = Typeface.createFromAsset(getAssets(), "Ubuntu-Regular.ttf");
            text.setTypeface(font);
            text1.setTypeface(font_medium);
            text2.setTypeface(font_medium);
            text3.setTypeface(font_medium);
            text4.setTypeface(font_medium);
            text5.setTypeface(font_medium);
            text6.setTypeface(font_medium);



    }
    public void sign_up(View view) {

        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }


}
