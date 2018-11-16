package kelvin.tablayout;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import com.example.a888888888.sport.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ClickPostActivity extends AppCompatActivity {

    private ImageView PostImage;
    private EditText PostDescription;
    private Button DeletePostButton,EditPostButton;
    private DatabaseReference ClickPostRef;
    private String PostKey , currentUserID,databaseUserID,description,image,file_name;

    private Toolbar click_post_toolbar;
    private ActionBar click_post_action_bar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);
        GlobalVariable User = (GlobalVariable)getApplicationContext();
        click_post_toolbar=(Toolbar)findViewById(R.id.click_post_toolbar);
        setSupportActionBar(click_post_toolbar);
        click_post_action_bar=getSupportActionBar();
        click_post_action_bar.setTitle("修改帖文");
        click_post_action_bar.setDisplayHomeAsUpEnabled(true);

        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();

        PostImage=(ImageView)findViewById(R.id.click_post_image);
        PostDescription=(EditText)findViewById(R.id.click_post_description);
        DeletePostButton=(Button)findViewById(R.id.delete_post_button);
        EditPostButton=(Button)findViewById(R.id.edit_post_button);


        DeletePostButton.setVisibility(View.INVISIBLE);
        EditPostButton.setVisibility(View.INVISIBLE);
        PostDescription.setVisibility(View.INVISIBLE);

        PostKey=getIntent().getExtras().get("PostKey").toString();
        ClickPostRef=FirebaseDatabase.getInstance().getReference().child("Posts").child(PostKey);


        ClickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {
                    description =dataSnapshot.child("description").getValue().toString();
                    image =dataSnapshot.child("postimage").getValue().toString();
                    databaseUserID=dataSnapshot.child("uid").getValue().toString();
                    file_name=dataSnapshot.child("file_name").getValue().toString();
                    Log.i("文件名稱",file_name);
                    PostDescription.setText(description);
                    User.setFile_name(file_name);
                    Picasso.get().load(image).into(PostImage);


                    if(currentUserID.equals(databaseUserID))
                    {
                        DeletePostButton.setVisibility(View.VISIBLE);
                        EditPostButton.setVisibility(View.VISIBLE);
                        PostDescription.setVisibility(View.VISIBLE);
                    }

                    EditPostButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ClickPostRef.child("description").setValue(PostDescription.getText().toString());
                            Toast.makeText(ClickPostActivity.this,"帖文更新成功",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        
        DeletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delete_file_name=User.getFile_name();
                Log.i("被刪除文件的名稱",""+delete_file_name);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference desertRef = storageRef.child("Post Images").child(delete_file_name);
                ClickPostRef.removeValue();
                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("圖片成功刪除","haha");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                    }
                });

                SendUserToMainActivity();
                Toast.makeText(ClickPostActivity.this,"帖文已經刪除.",Toast.LENGTH_SHORT).show();

            }
        });
    }



    private void DeleteCurrentPost() {


    }

    private void SendUserToMainActivity() {

        Intent mainIntent = new Intent(ClickPostActivity.this,PhotoBlog.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(mainIntent);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ClickPostActivity.this, PhotoBlog.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent( ClickPostActivity.this,PhotoBlog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intent);
    }
}
