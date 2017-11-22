package kelvin.tablayout;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;

public class kelvin_tab_layout extends AppCompatActivity
        implements kelvin_running_invitation.OnFragmentInteractionListener,kelvin_walking_invitation.OnFragmentInteractionListener,
        kelvin_aerobic_exercise_invitation.OnFragmentInteractionListener,
        kelvin_push_up_invitation.OnFragmentInteractionListener,
        kelvin_sit_up_invitation.OnFragmentInteractionListener{

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;//kkkkkkkkkkkkkkk
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelvin_tab_layout);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new KelvinRunningFragment(),"跑步");
        viewPagerAdapter.addFragments(new KelvinWalkingFragment(),"步行");
        viewPagerAdapter.addFragments(new KelvinAerobicExerciseFragment(),"有氧運動");
        viewPagerAdapter.addFragments(new KelvinSitUpsFragment(),"仰臥起坐");
        viewPagerAdapter.addFragments(new KelvinPushUpFragment(),"伏地挺身 ");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private int fc = 0; //fragment次數暫存
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int count = getFragmentManager().getBackStackEntryCount();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && fc !=0 && count!=0) {
// Do something.
            Intent intentHome= new Intent(kelvin_tab_layout.this,MainActivity.class);
            startActivity(intentHome);

            this.finish();
            return true;
        }
        if (count == 0) {
            super.onBackPressed();
            fc=1;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }//返回鍵的設定

    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }
}
