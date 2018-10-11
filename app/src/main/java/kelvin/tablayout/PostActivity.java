package kelvin.tablayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a888888888.sport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class PostActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ProgressDialog loadingBar;

    private ImageButton SelectPostImage;
    private Button UpdatePostButton;
    private EditText PostDescription;

    private static final int Gallery_Pick = 1;
    private Uri ImageUri;
    private String Description;

    private StorageReference PostsImagesRefrence;
    private DatabaseReference UsersRef, PostsRef;
    private FirebaseAuth mAuth;

    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Log.i("為什麼我會在這裡",""+1);
        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        PostsImagesRefrence = FirebaseStorage.getInstance().getReference();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");


        SelectPostImage = (ImageButton) findViewById(R.id.select_post_image);
        UpdatePostButton = (Button) findViewById(R.id.update_post_button);
        PostDescription =(EditText) findViewById(R.id.click_post_description);
        loadingBar = new ProgressDialog(this);


        mToolbar = (Toolbar) findViewById(R.id.activity_post_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("發帖文");


        SelectPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });


        UpdatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.i("為什麼我會在這裡",""+2);
                ValidatePostInfo();
            }
        });
    }



    private void ValidatePostInfo()
    {
        Log.i("為什麼我會在這裡",""+3);
        Description = PostDescription.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "請上傳圖片", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "請對圖片進行描述", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.i("為什麼我會在這裡",""+4);
            loadingBar.setTitle("新增帖文");
            loadingBar.setMessage("請等待,我們正在上傳你得帖文");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            Log.i("為什麼我會在這裡",""+5);
            StoringImageToFirebaseStorage();
        }
    }



    private void StoringImageToFirebaseStorage()
    {
        Log.i("為什麼我會在這裡",""+6);
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        Log.i("為什麼我會在這裡",""+7);

        StorageReference filePath = PostsImagesRefrence.child("Post Images").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");

        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    Log.i("為什麼我會在這裡",""+8);
                    downloadUrl = task.getResult().getDownloadUrl().toString();
                    Toast.makeText(PostActivity.this, "帖文圖片正處理成功", Toast.LENGTH_SHORT).show();
                    Log.i("為什麼我會在這裡",""+9);
                    SavingPostInformationToDatabase();

                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(PostActivity.this, "帖文圖片處理失敗" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    private void SavingPostInformationToDatabase()
    {
        Log.i("為什麼我會在這裡",""+10);
        UsersRef.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("為什麼我會在這裡",""+11);
                if(dataSnapshot.exists())
                {
                    Log.i("為什麼我會在這裡",""+12);
                    String userFullName = dataSnapshot.child("name").getValue().toString();
                    String userProfileImage = dataSnapshot.child("thumb_image").getValue().toString();

                    HashMap postsMap = new HashMap();
                    postsMap.put("uid", current_user_id);
                    postsMap.put("date", saveCurrentDate);
                    postsMap.put("time", saveCurrentTime);
                    postsMap.put("description", Description);
                    postsMap.put("postimage", downloadUrl);
                    postsMap.put("profileimage", userProfileImage);
                    postsMap.put("fullname", userFullName);
                    Log.i("為什麼我會在這裡",""+13);
                    PostsRef.child(current_user_id + postRandomName).updateChildren(postsMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    Log.i("為什麼我會在這裡",""+14);
                                    if(task.isSuccessful())
                                    {
                                        //SendUserToMainActivity();
                                        Log.i("為什麼我會在這裡",""+15);
                                        Toast.makeText(PostActivity.this, "帖文上傳成功", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(PostActivity.this, "帖文上傳失敗", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            SelectPostImage.setImageURI(ImageUri);
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            SendUserToMainActivity();
        }

        return super.onOptionsItemSelected(item);
    }



    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(PostActivity.this, PhotoBlog.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(mainIntent);

    }
}
