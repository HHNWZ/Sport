package kelvin.tablayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static com.example.a888888888.sport.MainActivity.getContext;


public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;


    //Android Layout

    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;
    private EditText setting_username_text_view,settingFullName,settingCountryName;
    private Button settingSaveInformationbutton;
    private TextInputLayout setting_username_text_input_layout;
    private Button mStatusBtn;
    private Button mImageBtn;
    private Button gotomainpage;
    private Toolbar user_setting_Toolbar;

    private ProgressDialog loadingBar;


    private static final int GALLERY_PICK = 1;

    // Storage Firebase
    private StorageReference mImageStorage;

    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        user_setting_Toolbar=(Toolbar)findViewById(R.id.user_setting_Toolbar);
        user_setting_Toolbar.setTitle("修改會員資料");
        user_setting_Toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        user_setting_Toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mDisplayImage = (CircleImageView) findViewById(R.id.settings_image);
        //mName = (TextView) findViewById(R.id.settings_name);
        //mStatus = (TextView) findViewById(R.id.settings_status);
        setting_username_text_view=(EditText)findViewById(R.id.setting_username);
        settingFullName=(EditText)findViewById(R.id.setting_full_name);
        settingCountryName=(EditText)findViewById(R.id.setting_country_name);
        settingSaveInformationbutton=(Button)findViewById(R.id.setting_information_button);
        loadingBar = new ProgressDialog(this);
        //mStatusBtn = (Button) findViewById(R.id.settings_status_btn);
        //mImageBtn = (Button) findViewById(R.id.settings_image_btn);



        mImageStorage = FirebaseStorage.getInstance().getReference();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String current_uid = mCurrentUser.getUid();



        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    if(dataSnapshot.hasChild("image")){
                        final String image = dataSnapshot.child("image").getValue().toString();
                        if(!image.equals("default")) {
                            Picasso.with(SettingsActivity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                                    .placeholder(R.drawable.default_avatar).into(mDisplayImage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    Picasso.with(SettingsActivity.this).load(image).placeholder(R.drawable.default_avatar).into(mDisplayImage);
                                }
                            });
                        }
                    }
                    if(dataSnapshot.hasChild("name")){
                        String name = dataSnapshot.child("name").getValue().toString();
                        settingFullName.setText(name);
                    }
                    if(dataSnapshot.hasChild("country")){
                        String country = dataSnapshot.child("country").getValue().toString();
                        settingCountryName.setText(country);
                    }
                    if(dataSnapshot.hasChild("username")){
                        String username= dataSnapshot.child("username").getValue().toString();
                        setting_username_text_view.setText(username);


                    }
                    else {
                        Toast.makeText(SettingsActivity.this,"有資料缺少",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDisplayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });

        settingSaveInformationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAccountSetupInformation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .setMinCropWindowSize(500, 500)
                    .start(this);
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mProgressDialog = new ProgressDialog(SettingsActivity.this);
                mProgressDialog.setTitle("上傳圖片...");
                mProgressDialog.setMessage("我們正在處理上傳圖片請等待.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();


                Uri resultUri = result.getUri();

                File thumb_filePath = new File(resultUri.getPath());

                String current_user_id = mCurrentUser.getUid();

                Bitmap thumb_bitmap = new Compressor(this)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .setQuality(75)
                        .compressToBitmap(thumb_filePath);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();


                StorageReference filepath = mImageStorage.child("profile_images").child(current_user_id + ".jpg");
                final StorageReference thumb_filepath = mImageStorage.child("profile_images").child("thumbs").child(current_user_id + ".jpg");


                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){

                            final String download_url = task.getResult().getDownloadUrl().toString();

                            UploadTask uploadTask = thumb_filepath.putBytes(thumb_byte);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                    String thumb_downloadUrl = thumb_task.getResult().getDownloadUrl().toString();

                                    if(thumb_task.isSuccessful()){

                                        Map update_hashMap = new HashMap();
                                        update_hashMap.put("image", download_url);
                                        update_hashMap.put("thumb_image", thumb_downloadUrl);

                                        mUserDatabase.updateChildren(update_hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful()){

                                                    mProgressDialog.dismiss();
                                                    Toast.makeText(SettingsActivity.this, "圖片上傳成功", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(SettingsActivity.this, "圖片處理失敗.", Toast.LENGTH_LONG).show();
                                        mProgressDialog.dismiss();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(SettingsActivity.this, "圖片上傳成功.", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }

    private void SaveAccountSetupInformation()
    {
        String username = setting_username_text_view.getText().toString();
        String fullname = settingFullName.getText().toString();
        String country = settingCountryName.getText().toString();

        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "請輸入用戶名", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(fullname))
        {
            Toast.makeText(this, "請輸入全名", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(country))
        {
            Toast.makeText(this, "請輸入國籍", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("正在儲存資料");
            loadingBar.setMessage("請等待，正在更改資料");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            HashMap userMap = new HashMap();
            userMap.put("name", fullname);
            userMap.put("username", username);
            userMap.put("country", country);
            userMap.put("status", "默認動態");
            userMap.put("gender", "none");
            userMap.put("dob", "none");
            userMap.put("relationshipstatus", "none");
            mUserDatabase.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if(task.isSuccessful())
                    {

                        Toast.makeText(SettingsActivity.this, "資料成功更改", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }
                    else
                    {
                        String message =  task.getException().getMessage();
                        Toast.makeText(SettingsActivity.this, "錯誤: " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }





}
