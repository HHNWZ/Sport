package kelvin.tablayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

public class SimpleMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private ActionBarDrawerToggle toggle;
    public static Menu menu;
    public static MenuItem menu_email_login,menu_email_register,menu_chat_room,menu_setting_account,menu_Logout,simple_main_activity,complex_main_activity;
    public  NavigationView navigationView;
    public  View hView;
    private DrawerLayout drawer;
    private TextView username;
    private ImageView userImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("不再邊緣運動");


         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        hView=navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
        username=(TextView)hView.findViewById(R.id.text_user_name);
        userImage=(ImageView)hView.findViewById(R.id.user_image);

        menu = navigationView.getMenu();
        menu_email_login = menu.findItem(R.id.email_login);
        //blog=menu.findItem(R.id.blog);
        menu_email_register = menu.findItem(R.id.email_register);
        menu_chat_room = menu.findItem(R.id.chat_room);
        menu_setting_account = menu.findItem(R.id.setting_account);
        menu_Logout = menu.findItem(R.id.Logout);
        simple_main_activity=menu.findItem(R.id.simple_main_activity);
        complex_main_activity=menu.findItem(R.id.complex_main_activity);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            OneSignal.sendTag("Uid",mAuth.getCurrentUser().getUid());
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            mUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()){
                        String user_name = dataSnapshot.child("name").getValue().toString();
                        String user_image = dataSnapshot.child("thumb_image").getValue().toString();
                        username.setText(user_name);
                        Picasso.get().load(user_image).placeholder(R.drawable.default_avatar).into(userImage);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            menu_email_login.setVisible(false);
            menu_email_register.setVisible(false);

            menu_chat_room.setVisible(true);
            menu_setting_account.setVisible(true);
            menu_Logout.setVisible(true);
            simple_main_activity.setVisible(false);
            complex_main_activity.setVisible(true);

        }else {
            menu_email_login.setVisible(true);
            menu_email_register.setVisible(true);
            menu_chat_room.setVisible(false);
            menu_setting_account.setVisible(false);
            menu_Logout.setVisible(false);
            simple_main_activity.setVisible(false);
            complex_main_activity.setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){//當按下左上三條線或顯示工具列
            invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.email_login) {
            Intent i = new Intent(SimpleMainActivity.this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }

        else if (id == R.id.email_register)
        {

            Intent i = new Intent(SimpleMainActivity.this,RegisterActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        else if(id==R.id.chat_room){
            Intent i = new Intent(SimpleMainActivity.this,MainActivityFireBase.class);
            startActivity(i);
        }
        else if(id==R.id.setting_account){
            Intent i = new Intent(SimpleMainActivity.this,SettingsActivity.class);
            startActivity(i);
        }
        else if(id==R.id.complex_main_activity){
            Intent i = new Intent(SimpleMainActivity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        else if(id==R.id.Logout){

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
            OneSignal.deleteTag("Uid");


            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(SimpleMainActivity.this,MainActivity.class);
            finish();
            startActivity(i);

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
