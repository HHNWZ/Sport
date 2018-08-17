package kelvin.tablayout;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Exercise_main extends AppCompatActivity implements
        kelvin_push_up_invitation.OnFragmentInteractionListener,
        FriendsFragment2.OnFragmentInteractionListener,
        kelvin_aerobic_exercise_invitation.OnFragmentInteractionListener,
        kelvin_running_invitation.OnFragmentInteractionListener,
        kelvin_sit_up_invitation.OnFragmentInteractionListener,
        kelvin_walking_invitation.OnFragmentInteractionListener
{

    private FirebaseAuth mAuth;
    private Toolbar eToolbar;

    private ViewPager eViewPager;
    private ExercisePagerAdapter eSectionsPagerAdapter;

    private DatabaseReference mUserRef;

    private TabLayout eTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_main);
        mAuth = FirebaseAuth.getInstance();
        mUserRef= FirebaseDatabase.getInstance().getReference();

        eToolbar = (Toolbar) findViewById(R.id.exercise_main_page_toolbar);
        eToolbar.setTitle("運動記錄");
        eToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        eToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Exercise_main.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        //Tabs
        eViewPager = (ViewPager) findViewById(R.id.exercise_main_tabPager);
        eSectionsPagerAdapter = new ExercisePagerAdapter(getSupportFragmentManager());

        eViewPager.setAdapter(eSectionsPagerAdapter);

        eTabLayout = (TabLayout) findViewById(R.id.exercise_main_tabs);
        eTabLayout.setupWithViewPager(eViewPager);
    }

    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }
}
