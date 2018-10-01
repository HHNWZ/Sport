package kelvin.tablayout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhotoBlog extends AppCompatActivity {
    private Toolbar photo_blog_app_bar;
    public static ActionBar actionBar;
    private FirebaseAuth mAuth;
    public static DatabaseReference myName2;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView postList;
    private Toolbar mToolbar;
    private CircleImageView NavProfileImage;
    private TextView NavProfileName;
    private ImageButton AddNewPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_blog);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();

        /*photo_blog_app_bar = (Toolbar) findViewById(R.id.photo_blog_app_bar);
        setSupportActionBar(photo_blog_app_bar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("運動資訊分享");
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        mAuth = FirebaseAuth.getInstance();
        Log.i("我的id",""+mAuth.getCurrentUser().getUid());
        myName2 = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mToolbar=(Toolbar)findViewById(R.id.activity_photo_blog_toolbar) ;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("運動社交平台");
        AddNewPostButton=(ImageButton)findViewById(R.id.add_new_post_button);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(PhotoBlog.this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        View navView=navigationView.inflateHeaderView(R.layout.navigation_header_photo_blog);
        NavProfileImage=(CircleImageView)navView.findViewById(R.id.nav_profile_image);
        NavProfileName=(TextView)navView.findViewById(R.id.nav_user_full_name);
        myName2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    if(dataSnapshot.hasChild("name")){
                        String myName=dataSnapshot.child("name").getValue().toString();
                        NavProfileName.setText(myName);
                    }
                    if(dataSnapshot.hasChild("thumb_image")){
                        String myImage=dataSnapshot.child("thumb_image").getValue().toString();
                        Picasso.with(PhotoBlog.this).load(myImage).placeholder(R.drawable.profile_icon2).into(NavProfileImage);
                    }
                    else {
                        Toast.makeText(PhotoBlog.this,"資料讀取錯誤",Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });

        AddNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToPostActivity();
            }
        });


    }

    private void SendUserToPostActivity() {
        Intent addNewPostIntent = new Intent(PhotoBlog.this,PostActivity.class);
        startActivity(addNewPostIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.nav_blog_profile:
                Toast.makeText(this,"個人主頁",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_back_main_activity:
                Intent intent = new Intent();
                intent.setClass(PhotoBlog.this, MainActivity.class);
                startActivity(intent);
                break;
            case  R.id.nav_blog_post:
                SendUserToPostActivity();
                break;
            case  R.id.nav_blog_view_friend_message:
                Toast.makeText(this,"瀏覽朋友運動帖文",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_friend:
                Toast.makeText(this,"朋友",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_find_friend:
                Toast.makeText(this,"尋找朋友",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_messages:
                Toast.makeText(this,"信息",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_settings:
                Toast.makeText(this,"設置",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(PhotoBlog.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.photo_blog_menu,menu);
        return true;
    }*/
}
