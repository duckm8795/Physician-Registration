package com.trainingandroidpart1.physicianregistration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.trainingandroidpart1.physicianregistration.Response.CreateProviderAccount.CreateProviderAccountResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {
    TextView firstName;         // ten
    TextView surName;           // ho
    TextView email;
    TextView password;
    CheckBox agreeTerm;
    RadioButton showHiddenPassword;
    RadioButton maleGender;
    RadioButton femaleGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();


    }

    public void create_new_provider_account(View view){
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
        Call<CreateProviderAccountResponse> call =
                serviceAPI.createProviderAccount("M", email.getText().toString(),firstName.getText().toString(),
                        " vi",surName.getText().toString(),password.getText().toString(),"VN","M","Asia/Saigon");
        call.enqueue(new Callback<CreateProviderAccountResponse>() {
            @Override
            public void onResponse(Call<CreateProviderAccountResponse> call, Response<CreateProviderAccountResponse> response) {
                Toast.makeText(CreateAccountActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CreateProviderAccountResponse> call, Throwable t) {

            }
        });
    }
    public void initView(){
        firstName = (TextView) findViewById(R.id.firstName_textView);
        surName = (TextView) findViewById(R.id.surName_textView);
        email = (TextView) findViewById(R.id.email_textView);
        password = (TextView) findViewById(R.id.password_textView);
        agreeTerm = (CheckBox) findViewById(R.id.agreeTerm_checkbox);
        showHiddenPassword = (RadioButton) findViewById(R.id.showHiddenPassword_toggle_button);
        maleGender = (RadioButton) findViewById(R.id.male_toggle_button);
        femaleGender = (RadioButton) findViewById(R.id.female_toggle_button);
    }

    public void showAlertGender() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Vui lòng chọn giới tính.")
                .setTitle("Jio Doctor")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showAlertName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng nhập tên.")
                .setTitle("Jio Doctor")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showAlertSurname() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng nhập họ.")
                .setTitle("Jio Doctor")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showAlertEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng nhập email.")
                .setTitle("Jio Doctor")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showAlertPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng nhập password.")
                .setTitle("Jio Doctor")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
