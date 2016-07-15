package com.trainingandroidpart1.physicianregistration;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trainingandroidpart1.physicianregistration.Adapter.VerifyPhysicianCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Response.GetProfile.GetProfileResponse;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.Main;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysicalVertificationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SharedPreferences sharedPreferences;
    private String retrieveToken = null;
    private String retrieveID = null;
    private ListView listview;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_vertification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        process();



    }
    public void process(){

        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

        /* get documents */
        Call<Main> call = serviceAPI.verify(180,"vi","VN",retrieveToken,Long.parseLong(retrieveID));
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                if ( response.body().getSuccess()){

                    List<VerificationDocType> verificationDocTypes = response.body().getVerificationDocTypes();

                    listview.setAdapter(new VerifyPhysicianCustomAdapter(PhysicalVertificationActivity.this, verificationDocTypes));

                }


            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {

            }
        });

        /* get full name */
        Call<GetProfileResponse> call1 = serviceAPI.getProfile(retrieveToken,Long.parseLong(retrieveID));
        call1.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.body().getSuccess()){
                    textView.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {

            }
        });
    }
    public void initView(){
        listview = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.namePhysician);

        sharedPreferences= getSharedPreferences("MyPrefer", Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken),"");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID),"");

        TextView intro_string_vp1 = (TextView) findViewById(R.id.intro_string_vp1);
        TextView intro_string_vp2 = (TextView) findViewById(R.id.intro_string_vp2);
        TextView namePhysician = (TextView) findViewById(R.id.namePhysician);

        Typeface font_medium = Typeface.createFromAsset(getAssets(), "Ubuntu-Regular.ttf");
        intro_string_vp1.setTypeface(font_medium);
        intro_string_vp2.setTypeface(font_medium);
        namePhysician.setTypeface(font_medium);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.verify_physician_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id ==  R.id.action_continue){
            showAlert();
        }
        return super.onOptionsItemSelected(item);
    }
    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn vẫn chưa tải lên bất cứ loại giấy tờ hay bằng cấp nào.Bạn muốn tải chúng lên ngay bây giờ chứ?")
                .setTitle("Jio Doctor")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Để sau ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PhysicalVertificationActivity.this,AvatarPhysicalActivity.class);
                        startActivity(intent);
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),"SSSS",Toast.LENGTH_SHORT).show();
    }
}
