package kelvin.tablayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {


    private EditText UserName,FullName,StatuName;
    private Button SaveInformationbutton;
    private CircleImageView ProfileImage;
    private Toolbar setup_Toolbar;
    public static ActionBar setup_actionBar;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private StorageReference UserProfileImageRef;

    String currentUserID;
    private static final int GALLERY_PICK = 1;
    private StorageReference mImageStorage;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mImageStorage = FirebaseStorage.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserID=mCurrentUser.getUid();
        UserName=(EditText)findViewById(R.id.setup_username);
        FullName=(EditText)findViewById(R.id.setup_full_name);
        StatuName=(EditText)findViewById(R.id.setup_status);
        SaveInformationbutton=(Button)findViewById(R.id.setup_information_button);
        ProfileImage=(CircleImageView) findViewById(R.id.setup_profile_image);
        setup_Toolbar=(Toolbar)findViewById(R.id.setup_Toolbar);
        setSupportActionBar(setup_Toolbar);
        setup_actionBar=getSupportActionBar();
        setup_actionBar.setTitle("會員資料修改");
        setup_actionBar.setDisplayHomeAsUpEnabled(true);


        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SetupActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
