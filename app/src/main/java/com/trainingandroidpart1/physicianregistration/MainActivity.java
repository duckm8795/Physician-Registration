package com.trainingandroidpart1.physicianregistration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Response.CreateProviderAccount.CreateProviderAccountResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
<<<<<<< HEAD
=======
    Context context;
    Button button;
>>>>>>> origin/master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

    }
    public void sign_up(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }


}
