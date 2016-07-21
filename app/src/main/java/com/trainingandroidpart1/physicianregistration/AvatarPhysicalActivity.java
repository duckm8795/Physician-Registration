package com.trainingandroidpart1.physicianregistration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.trainingandroidpart1.physicianregistration.Response.AvatarResponse.AvatarResponse;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvatarPhysicalActivity extends AppCompatActivity {
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private CircleImageView circleImageView;
    private SharedPreferences sharedPreferences = null;
    private String retrieveToken = null;
    private String retrieveID = null;
    private File file;
    private Map<String, RequestBody> requestBodyMap = new HashMap<>();
    private Map<String, RequestBody> requestBodyMap_ChooseGallery = new HashMap<>();
    private FileOutputStream fo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_physical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    public void initView() {

        TextView textView = (TextView) findViewById(R.id.textView_intro_avatar_string);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        Typeface font_medium = Typeface.createFromAsset(getAssets(), "Ubuntu-Regular.ttf");
        textView.setTypeface(font_medium);

        sharedPreferences = getSharedPreferences(getString(R.string.sharePre_string), Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");

    }


    public void choose_avatar(View view) {
        final CharSequence[] items = {"Chụp ảnh mới", "Chọn ảnh"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AvatarPhysicalActivity.this);
        builder.setTitle("Jio Doctor");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result=Utility.checkPermission(MainActivity.this);

                if (items[item].equals("Chụp ảnh mới")) {
                    cameraIntent();

                } else if (items[item].equals("Chọn ảnh")) {
                    circleImageView.setImageResource(R.drawable.download);
                    Crop.pickImage(AvatarPhysicalActivity.this);
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
                beginCrop(data.getData());
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);

        file = new File(getApplicationContext().getCacheDir(), "my_avatar_");

        try {
            file.createNewFile();
            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        circleImageView.setImageBitmap(thumbnail);

        /* processing for sever */
        generateParams_chooseGallery();
        process_chooseGallery();
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            if (Crop.getOutput(result).equals(Uri.EMPTY)) {
                circleImageView.setImageResource(R.drawable.download);
                //circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.download));
                Toast.makeText(AvatarPhysicalActivity.this, "qqqqqq", Toast.LENGTH_LONG).show();
            }
            circleImageView.setImageURI(Crop.getOutput(result));
            file = new File(Crop.getOutput(result).getPath());
            generateParams();
            process();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void generateParams() {
        String fileName = "file\"; filename=\"" + file.getName();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), retrieveID);
        RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), retrieveToken);

        requestBodyMap.put("token", token);
        requestBodyMap.put("userID", id);
        requestBodyMap.put(fileName, requestBody);
    }

    public void generateParams_chooseGallery() {
        String fileName = "file\"; filename=\"" + file.getName();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), retrieveID);
        RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), retrieveToken);

        requestBodyMap_ChooseGallery.put("token", token);
        requestBodyMap_ChooseGallery.put("userID", id);
        requestBodyMap_ChooseGallery.put(fileName, requestBody);
    }

    public void process() {
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
        Call<AvatarResponse> call = serviceAPI.uploadAvatar(requestBodyMap);
        call.enqueue(new Callback<AvatarResponse>() {
            @Override
            public void onResponse(Call<AvatarResponse> call, Response<AvatarResponse> response) {
                if (response.body().getSuccess()) {
                    Toast.makeText(AvatarPhysicalActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AvatarResponse> call, Throwable t) {
                Toast.makeText(AvatarPhysicalActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void process_chooseGallery() {
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
        Call<AvatarResponse> call = serviceAPI.uploadAvatar(requestBodyMap_ChooseGallery);
        call.enqueue(new Callback<AvatarResponse>() {
            @Override
            public void onResponse(Call<AvatarResponse> call, Response<AvatarResponse> response) {
                if (response.body().getSuccess()) {
                    Log.d("AAA", response.body().getMessage());
                    //Toast.makeText(AvatarPhysicalActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AvatarResponse> call, Throwable t) {
                Toast.makeText(AvatarPhysicalActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void next_to_degree_activity(View view) {
        Intent intent = new Intent(AvatarPhysicalActivity.this, DegreeListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        AvatarPhysicalActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        new android.support.v7.app.AlertDialog.Builder(this).setIcon(R.drawable.ic_error_outline_black_24dp).setTitle("Jio Doctor")
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
