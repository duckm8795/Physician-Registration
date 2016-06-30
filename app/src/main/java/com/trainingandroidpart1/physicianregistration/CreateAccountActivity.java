package com.trainingandroidpart1.physicianregistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    ToggleButton showHiddenPassword;
    RadioButton maleGender;

    RadioButton femaleGender;
    long userID;
    String accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();


    }
    public String genderCheck(){
        if( maleGender.isChecked()){
            return  "M";
        }
        else if (femaleGender.isChecked()){
            return  "F";
        }else {
            return "M";
        }
    }
    public boolean agreeTerm(){
        if ( agreeTerm.isChecked()){
            return true;
        }else{
            return false;
        }
    }
    public void create_new_provider_account(View view){
        if ( agreeTerm()){
            ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
            Call<CreateProviderAccountResponse> call =
                    serviceAPI.createProviderAccount("M", email.getText().toString(),firstName.getText().toString(),
                            "vi",surName.getText().toString(),password.getText().toString(),"VN",genderCheck(),"Asia/Saigon");
            call.enqueue(new Callback<CreateProviderAccountResponse>() {
                @Override
                public void onResponse(Call<CreateProviderAccountResponse> call, Response<CreateProviderAccountResponse> response) {
                    if ( response.body().getMessage().equals("")){
                        Toast.makeText(CreateAccountActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        userID = response.body().getUserID();
                        accessToken = response.body().getAccessToken();
                        Intent intent = new Intent(CreateAccountActivity.this,PasscodeActivity.class);
                        intent.putExtra(PasscodeActivity.USER_ID,userID);
                        intent.putExtra(PasscodeActivity.ACCESS_TOKEN,accessToken);
                        startActivity(intent);
                    }else{
                        Toast.makeText(CreateAccountActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<CreateProviderAccountResponse> call, Throwable t) {

                }
            });
        }else{
            showAlertTerm();
        }

    }
    public void initView(){
        firstName = (TextView) findViewById(R.id.firstName_textView);
        surName = (TextView) findViewById(R.id.surName_textView);
        email = (TextView) findViewById(R.id.email_textView);
        password = (TextView) findViewById(R.id.password_textView);
        agreeTerm = (CheckBox) findViewById(R.id.agreeTerm_checkbox);
        showHiddenPassword = (ToggleButton) findViewById(R.id.showHiddenPassword_toggle_button);
        maleGender = (RadioButton) findViewById(R.id.male_radio_button);
        femaleGender = (RadioButton) findViewById(R.id.female_radio_button);

        showHiddenPassword.setText(null);
        showHiddenPassword.setTextOn(null);
        showHiddenPassword.setTextOff(null);
//        if( password)
//        if (showHiddenPassword.isChecked()){
//            password.setInputType(InputType.TYPE_CLASS_TEXT);
//        }else {
//            password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        }
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
    public void showAlertTerm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng chấp nhận các điều khoản sử dụng và chính sách bảo mật trước khi tạo tài khoản.")
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
