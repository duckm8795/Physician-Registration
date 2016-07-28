package com.trainingandroidpart1.physicianregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CameraChooseOptionCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_choose_option_card);
    }

    public void pick_card(View view) {
        next_to_card();
    }

    public void pick_sheet(View view) {
        next_to_sheet();
    }

    public void next_to_card() {
        Intent intent = new Intent(CameraChooseOptionCardActivity.this, CameraCardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();
        CameraChooseOptionCardActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void next_to_sheet() {
        Intent intent = new Intent(CameraChooseOptionCardActivity.this, CameraSheetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();
        CameraChooseOptionCardActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
