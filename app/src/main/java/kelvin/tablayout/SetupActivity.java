package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText UserName,FullName,StatuName;
    private Button SaveInformationbutton;
    private CircleImageView ProfileImage;
    private Toolbar setup_Toolbar;
    public static ActionBar setup_actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

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
