package com.trainingandroidpart1.physicianregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.trainingandroidpart1.physicianregistration.Adapter.VerifyPhysicianCustomAdapter;
import com.trainingandroidpart1.physicianregistration.Request.CountingFileRequestBody;
import com.trainingandroidpart1.physicianregistration.Response.AvatarResponse.AvatarResponse;
import com.trainingandroidpart1.physicianregistration.Response.GetOpenDoctorDetailForProvider.DocumentList;
import com.trainingandroidpart1.physicianregistration.Response.GetOpenDoctorDetailForProvider.MainResponse;
import com.trainingandroidpart1.physicianregistration.Response.GetProfile.GetProfileResponse;
import com.trainingandroidpart1.physicianregistration.Response.StandardResponse;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.Main;
import com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician.VerificationDocType;
import com.trainingandroidpart1.physicianregistration.Service.ServiceManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class PhysicalVertificationActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    private String retrieveToken = null;
    private String retrieveID = null;
    private ListView listview;
    private TextView textView;
    private ProgressDialog progressDialog;


    private CircleProgressBar circleProgressBar;
    private TextView description_textview;
    private ImageButton pending_status;
    private ImageButton imageButton_arrow;
    private ImageView logo_of_pending;
    private TextView hello_texview;
    private TextView name_texview;
    private TextView other_texview;

    private String image_path;
    private String image_path_url_amazon;
    private String image_path_url_amazon2;
    private String image_path_url_amazon3;
    private String image_path_url_amazon_full_part;
    private String image_path_for_avatar;
    private String image_path_for_goverment_par1;
    private String image_path_for_goverment_par2;
    private String image_path_for_cardOrSheet;
    private String finalImagePathForGov;
    private String retrieve_guid;

    private boolean hasUpdoadSelfieImageOrNot = false;
    private boolean hasUpdoadGovermentImageOrNot = false;
    private boolean hasUpdoadCardOrSheetImageOrNot = false;
    private boolean hasTakeTwoSideOrNot = false;
    private View view;
    private Menu menu;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_vertification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        new SendRequests().execute();


    }

    public void initView() {

        listview = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.namePhysician);

        view = getLayoutInflater().inflate(R.layout.custom_list_item_verify_physician, null);

        sharedPreferences = getSharedPreferences("MyPrefer", Context.MODE_PRIVATE);
        retrieveToken = sharedPreferences.getString(getString(R.string.storePreToken), "");
        retrieveID = sharedPreferences.getString(getString(R.string.storePreID), "");

        TextView intro_string_vp1 = (TextView) findViewById(R.id.intro_string_vp1);
        TextView intro_string_vp2 = (TextView) findViewById(R.id.intro_string_vp2);
        TextView namePhysician = (TextView) findViewById(R.id.namePhysician);

        Typeface font_medium = Typeface.createFromAsset(getAssets(), "Ubuntu-Regular.ttf");
        intro_string_vp1.setTypeface(font_medium);
        intro_string_vp2.setTypeface(font_medium);
        namePhysician.setTypeface(font_medium);


    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.apply();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 69:
                    image_path = data.getExtras().getString(getString(R.string.image_path_string));
                    image_path_for_avatar = image_path;

                    retrieve_guid = data.getExtras().getString("SendGUID");
                    new UploadPhoto(0, image_path_for_avatar, retrieve_guid).execute();
                    break;
                case 70:

                    image_path = data.getExtras().getString(getString(R.string.image_path_string));
                    image_path_for_goverment_par1 = image_path;
                    showAlertToShootAgain();
                    //new UploadPhoto(1, image_path_for_goverment_par1).execute();
                    break;
                case 71:

                    image_path = data.getExtras().getString(getString(R.string.image_path_string));
                    retrieve_guid = data.getExtras().getString("SendGUID");
                    image_path_for_cardOrSheet = image_path;
                    new UploadPhoto(2, image_path_for_cardOrSheet, retrieve_guid).execute();
                    break;
                case 702:
                    retrieve_guid = data.getExtras().getString("SendGUID");
                    image_path = data.getExtras().getString(getString(R.string.image_path_string));
                    image_path_for_goverment_par2 = image_path;
                    hasTakeTwoSideOrNot = true;
                    //finalImagePathForGov = finalImagePath();
                    new UploadPhoto(1, finalImagePath(), retrieve_guid).execute();
                    break;
            }

        }
    }


    public Bitmap convertToBitMap(String imagePath) {

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        return bitmap;
    }

    public String finalImagePath() {
        Bitmap bitmap = combineImages(convertToBitMap(image_path_for_goverment_par1), convertToBitMap(image_path_for_goverment_par2));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return createImagePath(byteArray);
    }

    public String createImagePath(byte[] bytes) {
        String temp_path = null;
        File pictureFile = getOutputMediaFile();
        if (pictureFile != null) {
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                temp_path = pictureFile.getPath();
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
            }
        }
        return temp_path;
    }

    public Bitmap combineImages(Bitmap c, Bitmap s) {
        Bitmap cs = null;

        int width, height = 0;

        if (c.getWidth() > s.getWidth()) {
            width = c.getWidth();

        } else {
            width = s.getWidth();
            //height = c.getHeight() + s.getHeight();
        }
        height = c.getHeight() + s.getHeight();
        cs = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, 0f, c.getHeight(), null);


        return cs;
    }

    private void updateView(int index) {
        view = listview.getChildAt(index -
                listview.getFirstVisiblePosition());

        circleProgressBar = (CircleProgressBar) view.findViewById(R.id.upload_progress);
        description_textview = (TextView) view.findViewById(R.id.description);
        pending_status = (ImageButton) view.findViewById(R.id.pending_status);
        imageButton_arrow = (ImageButton) view.findViewById(R.id.imageButton_arrow);

        logo_of_pending = (ImageView) findViewById(R.id.logo_pending);
        hello_texview = (TextView) findViewById(R.id.intro_string_vp1);
        name_texview = (TextView) findViewById(R.id.namePhysician);
        other_texview = (TextView) findViewById(R.id.intro_string_vp2);
        if (view == null) {
            return;
        }

    }

    private void reUpdateView(final int index) {

        listview.post(new Runnable() {
            public void run() {
                int firstVisibleElement = listview.getFirstVisiblePosition();
                int lastVisibleElement = listview.getLastVisiblePosition();
                if (index >= firstVisibleElement && index <= lastVisibleElement) {
                    view = listview.getChildAt(index - firstVisibleElement);
                }
                Log.d("MyTag", view.toString());
                if (view == null) {
                    return;
                }

                circleProgressBar = (CircleProgressBar) view.findViewById(R.id.upload_progress);
                description_textview = (TextView) view.findViewById(R.id.description);
                pending_status = (ImageButton) view.findViewById(R.id.pending_status);
                imageButton_arrow = (ImageButton) view.findViewById(R.id.imageButton_arrow);

                logo_of_pending = (ImageView) findViewById(R.id.logo_pending);
                hello_texview = (TextView) findViewById(R.id.intro_string_vp1);
                name_texview = (TextView) findViewById(R.id.namePhysician);
                other_texview = (TextView) findViewById(R.id.intro_string_vp2);


                Typeface font_medium = Typeface.createFromAsset(getAssets(), "Ubuntu-Regular.ttf");

                description_textview.setText("Đạng đợi xác nhận...");
                circleProgressBar.setVisibility(View.GONE);
                imageButton_arrow.setVisibility(View.VISIBLE);
                pending_status.setVisibility(View.VISIBLE);
                description_textview.setTextColor(Color.parseColor("#FFEBEE"));
                pending_status.setBackground(getDrawable(R.drawable.ic_verification_pending));

                logo_of_pending.setBackground(getDrawable(R.drawable.ic_verification_pending_illustration));
                hello_texview.setText("Đang chờ xác nhận.");
                name_texview.setText("");
                other_texview.setText("Chúng tôi đang xem xét hồ sơ của bạn.Vui lòng xem trạng thái ở phía dưới.");
                hello_texview.setTypeface(font_medium);
                other_texview.setTypeface(font_medium);
            }
        });


    }

    public void showAlertToShootAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vui lòng chụp tiếp mặt sau tấm thẻ.")
                .setTitle("Jio Doctor")
                .setCancelable(false)
                .setPositiveButton("Chụp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PhysicalVertificationActivity.this, CameraGovermentActivity.class);
                        startActivityForResult(intent, 702);
                        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.verify_physician_main, menu);

        return true;
    }

    public void showOverflowMenu(boolean showMenu) {
        if (menu == null)
            return;
        menu.setGroupVisible(R.id.verfiy_group, showMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_continue) {

            if (hasUpdoadSelfieImageOrNot || hasUpdoadGovermentImageOrNot || hasUpdoadCardOrSheetImageOrNot) {
                next_to_done_verfication();
            } else {

                showAlert();
            }

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
                        Intent intent = new Intent(PhysicalVertificationActivity.this, AvatarPhysicalActivity.class);
                        startActivity(intent);
                        finish();
                        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showLoading() {

        progressDialog = new ProgressDialog(PhysicalVertificationActivity.this);
        progressDialog.setMessage("Đang xử lý ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideLoading() {

        progressDialog.dismiss();
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

    public void next_to_done_verfication() {
        Intent intent = new Intent(PhysicalVertificationActivity.this, DoneVerificationActivity.class);
        intent.putExtra("ImagePathForAvatar", image_path_for_avatar);
//        intent.putExtra("ImagePathByAmazonForSelfie",image_path_url_amazon);
        intent.putExtra("HasUploadAvatarOrNot", hasUpdoadSelfieImageOrNot);
        startActivity(intent);
        finish();
        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void next_to_selfie_camera(String id) {
        Intent intent = new Intent(PhysicalVertificationActivity.this, CameraSelfieActivity.class);
        intent.putExtra("IdOfCamera", id);
        startActivityForResult(intent, 69);

        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void next_to_taken_picture(String image_path_url_amazon) {
        Intent intent = new Intent(PhysicalVertificationActivity.this, TempHoldPictureActivity.class);
        intent.putExtra("ImagePathByAmazon", image_path_url_amazon);
        startActivity(intent);

        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

    }

    public void next_to_taken_picture_for_selfie(String image_path_url_amazon) {
        Intent intent = new Intent(PhysicalVertificationActivity.this, TempHoldPictureActivity.class);
        intent.putExtra("ImagePathByAmazonForSelfie", image_path_url_amazon);
        intent.putExtra("NeedRotateURL", true);
        startActivity(intent);

        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

    }

    public void next_to_govermentID_camera(String id) {
        Intent intent = new Intent(PhysicalVertificationActivity.this, CameraGovermentActivity.class);
        intent.putExtra("IdOfCamera", id);
        startActivityForResult(intent, 70);

        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void next_to_choose_card_camera(String id) {
        Intent intent = new Intent(PhysicalVertificationActivity.this, CameraChooseOptionCardActivity.class);
        intent.putExtra("IdOfCamera", id);
        startActivityForResult(intent, 71);
        PhysicalVertificationActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public class SendRequests extends AsyncTask<Void, Void, Void> {
        Main main;
        MainResponse responseForGetOpenDoctorDetail;
        GetProfileResponse getProfileResponse;

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

            Call<GetProfileResponse> callProfile = ServiceManager.instance().getProfile(retrieveToken, Long.parseLong(retrieveID));
            Call<Main> callDocument = ServiceManager.instance().verify(180, "vi", "VN", retrieveToken, Long.parseLong(retrieveID));
            Call<MainResponse> callGetOpenDoctorDetail = ServiceManager.instance().getOpenDoctorDetailForProvider(retrieveToken, Long.parseLong(retrieveID));
            try {
                getProfileResponse = callProfile.execute().body();
                main = callDocument.execute().body();

                responseForGetOpenDoctorDetail = callGetOpenDoctorDetail.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void v) {
             /* for profile response */
            if (getProfileResponse == null) {
                hideLoading();
                showAlertError("Sever out");

            } else {
                if (getProfileResponse.getSuccess()) {
                    hideLoading();
                    textView.setText(getProfileResponse.toString());
                } else {
                    hideLoading();
                    Toast.makeText(getApplicationContext(), getProfileResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            /* for document response */
            if (main == null) {
                hideLoading();
                showAlertError("Sever out");
            } else {
                if (main.getSuccess()) {
                    hideLoading();
                    final List<VerificationDocType> verificationDocTypes = main.getVerificationDocTypes();

                    listview.setAdapter(new VerifyPhysicianCustomAdapter(PhysicalVertificationActivity.this, verificationDocTypes));

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int qd = verificationDocTypes.get(position).getDocTypeId();
                            if (qd == 1) {
                                if (hasUpdoadSelfieImageOrNot) {
                                    next_to_taken_picture_for_selfie(image_path_url_amazon);
                                } else {
                                    next_to_selfie_camera("1");
                                }

                            } else if (qd == 2) {
                                if (hasUpdoadGovermentImageOrNot) {
                                    next_to_taken_picture(image_path_url_amazon2);
                                } else {
                                    next_to_govermentID_camera("2");
                                }

                            } else {

                                if (hasUpdoadCardOrSheetImageOrNot) {
                                    next_to_taken_picture(image_path_url_amazon3);
                                } else {
                                    next_to_choose_card_camera("3");
                                }


                            }
                        }
                    });
                } else {
                    hideLoading();
                    Toast.makeText(getApplicationContext(), main.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            /* for get open doctor detail */
            if (responseForGetOpenDoctorDetail == null) {
                hideLoading();
                showAlertError("Sever out");

            } else {
                if (responseForGetOpenDoctorDetail.getSuccess()) {
                    hideLoading();
                    List<DocumentList> documentList = responseForGetOpenDoctorDetail.getData().getDocumentList();
                    for (int i = 0; i < documentList.size(); i++) {

                        switch (documentList.get(i).getDocTypeId()) {
                            case 1:
                                hasUpdoadSelfieImageOrNot = true;
                                reUpdateView(0);
                                image_path_url_amazon = documentList.get(i).getImageURL();
                                break;
                            case 2:
                                hasUpdoadGovermentImageOrNot = true;
                                reUpdateView(1);
                                image_path_url_amazon2 = documentList.get(i).getImageURL();
                                break;
                            case 3:
                                hasUpdoadCardOrSheetImageOrNot = true;
                                reUpdateView(2);
                                image_path_url_amazon3 = documentList.get(i).getImageURL();
                                break;
                        }

                    }


                } else {
                    hideLoading();
                    Toast.makeText(getApplicationContext(), responseForGetOpenDoctorDetail.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }


    }

    public class UploadPhoto extends AsyncTask<Void, Integer, Void> {
        AvatarResponse avatarResponse;
        StandardResponse standardResponse;
        GetProfileResponse getProfileResponse;
        MainResponse mainResponse;
        File file;
        int id_to_update;
        long totalSize;
        String real_image_path;
        String guid;

        public UploadPhoto(int id, String real_image_path, String guid) {
            id_to_update = id;
            this.real_image_path = real_image_path;
            this.guid = guid;
        }

        @Override
        protected void onPreExecute() {
            updateView(id_to_update);
            imageButton_arrow.setVisibility(View.GONE);
            circleProgressBar.setVisibility(View.VISIBLE);
            description_textview.setText("Đang tải lên");
            showOverflowMenu(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            file = new File(real_image_path);
            totalSize = file.length();

            String fileName = "file\"; filename=\"" + file.getName();


            RequestBody requestBody1 = new CountingFileRequestBody(file, "multipart/form-data", new CountingFileRequestBody.ProgressListener() {
                @Override
                public void transferred(long num) {
                    int progressValue = (int) ((num / (float) totalSize) * 100);
                    //Log.i("uploadedProgressPercent", " progress is : " + progressValue + " %");
                    publishProgress(progressValue);
                }
            });
            RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), retrieveID);
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), retrieveToken);

            Map<String, RequestBody> requestBodyMap = new HashMap<>();
            requestBodyMap.put("token", token);
            requestBodyMap.put("userID", id);
            requestBodyMap.put(fileName, requestBody1);


            Call<StandardResponse> callSaveOpenDoctor = ServiceManager.instance().saveOpenDoctorDocument(guid, id_to_update + 1, retrieveToken, Long.parseLong(retrieveID));
            Call<AvatarResponse> call = ServiceManager.instance().uploadAvatar(requestBodyMap);
            //Call<GetProfileResponse> callProfile = ServiceManager.instance().getProfile(retrieveToken, Long.parseLong(retrieveID));
            Call<MainResponse> callGetOpenDoctorDetail = ServiceManager.instance().getOpenDoctorDetailForProvider(retrieveToken, Long.parseLong(retrieveID));



            try {
                standardResponse = callSaveOpenDoctor.execute().body();
                avatarResponse = call.execute().body();
                //getProfileResponse = callProfile.execute().body();
                mainResponse = callGetOpenDoctorDetail.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //Log.d("SADSAD", String.valueOf(totalSize));
            int circle_value = values[0];
            circleProgressBar.setProgress(circle_value);

        }

        @Override
        protected void onPostExecute(Void aVoid) {

                try {
                    if (standardResponse.getSuccess()) {

                    } else {

                        Toast.makeText(getApplicationContext(), standardResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    showAlertError(e.getMessage());
                }



                try {
                    if (avatarResponse.getSuccess()) {
                        showOverflowMenu(true);

                        //image_path_url_amazon =avatarResponse.getMessage();
                        if (id_to_update == 0) {
                            hasUpdoadSelfieImageOrNot = true;
                            image_path_url_amazon = avatarResponse.getMessage();
                        } else if (id_to_update == 1) {
                            hasUpdoadGovermentImageOrNot = true;
                            if (hasTakeTwoSideOrNot) {
                                image_path_url_amazon2 = avatarResponse.getMessage();
                                Log.d("FullPart", image_path_url_amazon2);
                            }

                        } else if (id_to_update == 2) {
                            hasUpdoadCardOrSheetImageOrNot = true;
                            image_path_url_amazon3 = avatarResponse.getMessage();


                        }

                        //hasUpdoadSelfieImageOrNot =true;
                        Typeface font_medium = Typeface.createFromAsset(getAssets(), "Ubuntu-Regular.ttf");

                        description_textview.setText("Đạng đợi xác nhận...");
                        circleProgressBar.setVisibility(View.GONE);
                        imageButton_arrow.setVisibility(View.VISIBLE);
                        pending_status.setVisibility(View.VISIBLE);
                        description_textview.setTextColor(Color.parseColor("#FFEBEE"));
                        pending_status.setBackground(getDrawable(R.drawable.ic_verification_pending));
                        logo_of_pending.setBackground(getDrawable(R.drawable.ic_verification_pending_illustration));
                        hello_texview.setText("Đang chờ xác nhận.");
                        name_texview.setText("");
                        other_texview.setText("Chúng tôi đang xem xét hồ sơ của bạn.Vui lòng xem trạng thái ở phía dưới.");
                        hello_texview.setTypeface(font_medium);
                        other_texview.setTypeface(font_medium);


                    } else {
                        showAlertError(avatarResponse.getMessage());
                        circleProgressBar.setVisibility(View.GONE);
                        imageButton_arrow.setVisibility(View.VISIBLE);
                        description_textview.setText("Có lỗi. Vui lòng thử lại");
                        description_textview.setTextColor(Color.parseColor("#D32F2F"));
                        pending_status.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    showAlertError(e.getMessage());
                }

            try {
                if(mainResponse.getSuccess()){
                    List<DocumentList> documentList = mainResponse.getData().getDocumentList();
                    for(int i = 0 ; i < documentList.size();i++){
                        switch (documentList.get(i).getDocTypeId()) {
                            case 1:
                                documentList.get(i).setImageURL(image_path_url_amazon);
                                break;
                            case 2:
                                documentList.get(i).setImageURL(image_path_url_amazon2);
                                break;
                            case 3:
                                documentList.get(i).setImageURL(image_path_url_amazon3);
                                break;
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), mainResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                showAlertError(e.getMessage());
            }




        }
    }

}
