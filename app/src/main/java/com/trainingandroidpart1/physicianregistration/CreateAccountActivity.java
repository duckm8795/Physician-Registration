package com.trainingandroidpart1.physicianregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.trainingandroidpart1.physicianregistration.Response.CreateProviderAccount.CreateProviderAccountResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;
import com.trainingandroidpart1.physicianregistration.Service.ServiceManager;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;

public class CreateAccountActivity extends AppCompatActivity {
    private TextView firstName;         // ten
    private TextView surName;           // ho
    private TextView email;
    private TextView password;
    private CheckBox agreeTerm;
    private ToggleButton showHiddenPassword;
    private RadioButton maleGender;
    private ProgressDialog progressDialog;
    private RadioButton femaleGender;
    private long userID;
    private String accessToken;
    private SharedPreferences sharedPreferences = null;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        ToggleHiddenShowPassword();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void create_new_provider_account(View view) {
        view.startAnimation(buttonClick);
        if (agreeTerm()) {
            if (isMissingInput()) {

            } else {

                if (isValidEmail(email.getText().toString())) {
                    if (isValidPassword(password.getText().toString())) {
                        new SendRequests().execute();
                    } else {
                        showInvalidPassword();
                    }

                } else {
                    showInvalidEmail();
                }

            }

        } else {

            showAlertTerm();
        }

    }

    public void ToggleHiddenShowPassword() {

        showHiddenPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (showHiddenPassword.isChecked()) {
                    password.setTransformationMethod(null);
                } else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }


    public boolean isMissingInput() {
        if (firstName.getText().toString().equals("")) {
            showAlertName();
        } else {
            if (surName.getText().toString().equals("")) {
                showAlertSurname();
            } else {
                if (!maleGender.isChecked() && !femaleGender.isChecked()) {
                    showAlertGender();
                } else {
                    if (email.getText().toString().equals("")) {
                        showAlertEmail();
                    } else {
                        if (password.getText().toString().equals("")) {
                            showAlertPassword();
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;

    }

    public boolean isValidPassword(String data) {

        if (data.length() < 8) {
            return false;
        } else {
            char c;
            int count = 0;
            for (int i = 0; i < data.length(); i++) {
                c = data.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                } else if (Character.isDigit(c)) {
                    count++;
                }
            }
            if (count < 1) {
                return false;
            }
        }
        return true;
    }


    public boolean isValidEmail(String data) {
        return data != null && Patterns.EMAIL_ADDRESS.matcher(data).matches();
    }

    public void initView() {
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


        TextView create_account_string = (TextView) findViewById(R.id.create_account_string);
        TextView requirement_password_string = (TextView) findViewById(R.id.requirement_password_string);
        TextView term_string = (TextView) findViewById(R.id.term_string);
        Button create_account_btn = (Button) findViewById(R.id.create_account_btn);

        // set font
        Typeface font_medium = Typeface.createFromAsset(getAssets(), "Ubuntu-Regular.ttf");
        firstName.setTypeface(font_medium);
        surName.setTypeface(font_medium);
        email.setTypeface(font_medium);
        password.setTypeface(font_medium);
        create_account_string.setTypeface(font_medium);
        requirement_password_string.setTypeface(font_medium);
        term_string.setTypeface(font_medium);
        create_account_btn.setTypeface(font_medium);

    }


    class SendRequests extends AsyncTask<Void, Void, Void> {
        CreateProviderAccountResponse createProviderAccountResponse;
        String email1 = email.getText().toString();
        String firstName1 = firstName.getText().toString();
        String surName1 = surName.getText().toString();
        String password1 = password.getText().toString();
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
            //ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);

            Call<CreateProviderAccountResponse> call =
                    ServiceManager.instance().createProviderAccount("M", email1,firstName1 ,
                            "vi",surName1 ,password1 , "VN", genderCheck(), "Asia/Saigon");
            try {

                    createProviderAccountResponse= call.execute().body();



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void v) {
//                if( createProviderAccountResponse == null ){
//                    hideLoading();
//                    showAlertError("Sever out");
//                }else{
                    try {
                        if(createProviderAccountResponse.getSuccess()){

                            userID = createProviderAccountResponse.getUserID();
                            accessToken = createProviderAccountResponse.getAccessToken();
                            Log.d("Token",accessToken);
                            Log.d("ID", String.valueOf(userID));
                            sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString(getString(R.string.storePreToken), accessToken).apply();
                            sharedPreferences.edit().putString(getString(R.string.storePreID), String.valueOf(userID)).apply();

                            hideLoading();
                            Intent i = new Intent(CreateAccountActivity.this, PasscodeActivity.class);
                            startActivity(i);
                            finish();
                            CreateAccountActivity.this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        }else {
                            hideLoading();
                            showAlertCreateAccountResponse(createProviderAccountResponse.getMessage());
                        }
                    } catch (Exception e) {
                       // e.printStackTrace();
                        hideLoading();
                        showAlertError(e.toString());
                    }
//                }








        }


    }
    public void showLoading() {
        progressDialog = new ProgressDialog(CreateAccountActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void hideLoading() {

        progressDialog.dismiss();
    }

    public String genderCheck() {
        if (maleGender.isChecked()) {
            return "M";
        } else if (femaleGender.isChecked()) {
            return "F";
        } else {
            return "M";
        }
    }

    public boolean agreeTerm() {
        if (agreeTerm.isChecked()) {
            return true;
        } else {
            return false;
        }
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

    public void showInvalidPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Mật khẩu mới phải dài ít nhất 8 ký tự và chứa ít nhất 1 chữ số.")
                .setTitle("Jio Doctor")
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showInvalidEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng nhập địa chỉ email chính xác.")
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

    public void showAlertCreateAccountResponse(String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(data)
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
