package kelvin.tablayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.a888888888.sport.BackHandlerHelper;
import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;

public class kelvin_tab_layout extends AppCompatActivity
        implements kelvin_running_invitation.OnFragmentInteractionListener,kelvin_walking_invitation.OnFragmentInteractionListener,
        kelvin_aerobic_exercise_invitation.OnFragmentInteractionListener,
        kelvin_push_up_invitation.OnFragmentInteractionListener,
        kelvin_sit_up_invitation.OnFragmentInteractionListener,
        kelvin_running_tag_friend.OnFragmentInteractionListener,
        FriendsFragment2.OnFragmentInteractionListener

{


    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;//kkkkkkkkkkkkkkk
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelvin_tab_layout);
        toolbar = (Toolbar)findViewById(R.id.exercise_data_Toolbar);
        toolbar.setTitle("運動資料");
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(kelvin_tab_layout.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new KelvinRunningFragment(),"跑步");
        viewPagerAdapter.addFragments(new KelvinWalkingFragment(),"步行");
        viewPagerAdapter.addFragments(new KelvinAerobicExerciseFragment(),"有氧運動");
        viewPagerAdapter.addFragments(new KelvinSitUpsFragment(),"仰臥起坐");
        viewPagerAdapter.addFragments(new KelvinPushUpFragment(),"伏地挺身 ");//跟新


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            Intent intentHome= new Intent(kelvin_tab_layout.this,MainActivity.class);//kk
            /*Toast.makeText(kelvin_tab_layout.this, "按返回鍵會用到這裡", Toast.LENGTH_SHORT).show();*/
            startActivity(intentHome);
            this.finish();
        }
        else if (!BackHandlerHelper.handleBackPress(this)) {
            /*Toast.makeText(kelvin_tab_layout.this, "按返回鍵會用到這裡3", Toast.LENGTH_SHORT).show();不用刪除，因為fragment的返回鍵會無反應*/
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }



}
