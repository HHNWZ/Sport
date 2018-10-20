package kelvin.tablayout;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.util.Timer;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

public class Exercise_main extends AppCompatActivity

{

    private Toolbar exercise_main_toolbar;
    private ActionBar exercise_main_action_bar;
    private ExercisePagerAdapter mSectionsPagerAdapter;
    private TabLayout exercise_main_tabs;
    private ViewPager exercise_main_tabPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_main);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())//1234
                .init();
        exercise_main_toolbar=(Toolbar)findViewById(R.id.exercise_main_toolbar);
        setSupportActionBar(exercise_main_toolbar);
        exercise_main_action_bar=getSupportActionBar();
        exercise_main_action_bar.setTitle("運動排行榜");
        exercise_main_action_bar.setDisplayHomeAsUpEnabled(true);

        exercise_main_tabPager=(ViewPager)findViewById(R.id.exercise_main_tabPager);
        mSectionsPagerAdapter=new ExercisePagerAdapter(getSupportFragmentManager());

        exercise_main_tabPager.setAdapter(mSectionsPagerAdapter);
        exercise_main_tabPager.setPageTransformer(true,new CubeOutTransformer());

        exercise_main_tabs=(TabLayout)findViewById(R.id.exercise_main_tabs);
        exercise_main_tabs.setupWithViewPager(exercise_main_tabPager);







    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Exercise_main.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }
}
