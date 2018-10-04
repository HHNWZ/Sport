package kelvin.tablayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ClickPostActivity extends AppCompatActivity {

    private ImageView PostImage;
    private TextView PostDescription;
    private Button DeletePostButton,EditPostButton;
    private DatabaseReference ClickPostRef;
    private String PostKey , currentUserID,databaseUserID,description,image;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);

        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();

        PostImage=(ImageView)findViewById(R.id.click_post_image);
        PostDescription=(TextView)findViewById(R.id.click_post_description);
        DeletePostButton=(Button)findViewById(R.id.delete_post_button);
        EditPostButton=(Button)findViewById(R.id.edit_post_button);

        DeletePostButton.setVisibility(View.INVISIBLE);
        EditPostButton.setVisibility(View.INVISIBLE);

        PostKey=getIntent().getExtras().get("PostKey").toString();
        ClickPostRef=FirebaseDatabase.getInstance().getReference().child("Posts").child(PostKey);

        ClickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    description =dataSnapshot.child("description").getValue().toString();
                    image =dataSnapshot.child("postimage").getValue().toString();
                    databaseUserID=dataSnapshot.child("uid").getValue().toString();

                    PostDescription.setText(description);
                    Picasso.with(ClickPostActivity.this).load(image).into(PostImage);

                    if(currentUserID.equals(databaseUserID))
                    {
                        DeletePostButton.setVisibility(View.VISIBLE);
                        EditPostButton.setVisibility(View.VISIBLE);
                    }

                    EditPostButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            EditPostButton(description);
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
                DeleteCurrentPost();
            }
        });
    }

    private void EditPostButton(String description) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ClickPostActivity.this);
        builder.setTitle("修改帖文:");

        final EditText inputField = new EditText(ClickPostActivity.this);
        inputField.setText(description);
        builder.setView(inputField);

        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClickPostRef.child("description").setValue(inputField.getText().toString());
                Toast.makeText(ClickPostActivity.this,"帖文更新成功",Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        Dialog dialog=builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.rgb(115,196,217)));
    }

    private void DeleteCurrentPost() {

        ClickPostRef.removeValue();
        SendUserToMainActivity();
        Toast.makeText(this,"帖文已經刪除.",Toast.LENGTH_SHORT).show();
    }

    private void SendUserToMainActivity() {

        Intent mainIntent = new Intent(ClickPostActivity.this,PhotoBlog.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
