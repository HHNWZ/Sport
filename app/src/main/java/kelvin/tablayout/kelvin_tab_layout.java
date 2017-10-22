package kelvin.tablayout;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.a888888888.sport.R;

public class kelvin_tab_layout extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
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
}
