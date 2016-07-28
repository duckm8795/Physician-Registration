package com.trainingandroidpart1.physicianregistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DoneVerificationActivity extends AppCompatActivity {
    private String image_path;
    private boolean hasUploadAvatarOrNot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_verification);

        //Intent intent = getIntent();

    }

    public void next_to_set_avatar(View view) {
        image_path = getIntent().getStringExtra("ImagePathForAvatar");
        Intent intent = new Intent(DoneVerificationActivity.this,AvatarPhysicalActivity.class);

        if(getIntent().getBooleanExtra("HasUploadAvatarOrNot",false)){
            intent.putExtra("ImagePathForSetAvatar",image_path);
            intent.putExtra("NeedSetAvatar",true);

        }
        startActivity(intent);
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
