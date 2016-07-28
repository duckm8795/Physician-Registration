package com.trainingandroidpart1.physicianregistration;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    private TextView text ;
    private TextView text1 ;
    private TextView text2 ;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
         text = (TextView) findViewById(R.id.string_logo);
         text1 = (TextView) findViewById(R.id.textView1);
         text2 = (TextView) findViewById(R.id.textView2);
         text3 = (TextView) findViewById(R.id.textView3);
         text4 = (TextView) findViewById(R.id.textView4);
         text5 = (TextView) findViewById(R.id.textView5);
         text6 = (TextView) findViewById(R.id.textView6);


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
        text3.setTextColor(Color.parseColor("#E0F7FA"));
        text4.setTextColor(Color.parseColor("#E0F7FA"));
        Intent intent = new Intent(this, CreateAccountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        MainActivity.this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(R.drawable.ic_error_outline_black_24dp).setTitle("Jio Doctor")
                .setMessage("Bạn có chắc muốn thoát ứng dụng?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Không", null).show();
    }

}
