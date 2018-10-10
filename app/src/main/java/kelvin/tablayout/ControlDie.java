package kelvin.tablayout;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.a888888888.sport.Ifnotuserdata;
import com.example.a888888888.sport.Login;
import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.Over;
import com.example.a888888888.sport.Push;
import com.example.a888888888.sport.R;
import com.example.a888888888.sport.Register;
import com.example.a888888888.sport.Run;
import com.example.a888888888.sport.Sit;
import com.example.a888888888.sport.Userdata;
import com.example.a888888888.sport.Walk;
import com.onesignal.OneSignal;

import qwer.BlankFragment;
import qwer.BlankFragment2;
import qwer.BlankFragment3;
import qwer.BlankFragmentc1;
import qwer.BlankFragmentc2;
import qwer.BlankFragmentc3;
import qwer.BlankFragmentc4;
import qwer.ShowDiary;
import qwer.addDiary;
import qwer.foodAndKLL;

public class ControlDie extends AppCompatActivity implements Over.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener, BlankFragment3.OnFragmentInteractionListener
        ,Run.OnFragmentInteractionListener,Walk.OnFragmentInteractionListener,Sit.OnFragmentInteractionListener,Push.OnFragmentInteractionListener,Login.OnFragmentInteractionListener,
        ShowDiary.OnFragmentInteractionListener,addDiary.OnFragmentInteractionListener,BlankFragmentc1.OnFragmentInteractionListener , BlankFragmentc2.OnFragmentInteractionListener , BlankFragmentc3.OnFragmentInteractionListener , BlankFragmentc4.OnFragmentInteractionListener
        ,Userdata.OnFragmentInteractionListener, foodAndKLL.OnFragmentInteractionListener,Ifnotuserdata.OnFragmentInteractionListener,Register.OnFragmentInteractionListener {

    private Toolbar activity_control_die_toolbar;
    private ActionBar activity_control_die_action_bar;
    private Button angry_btn_die,angry_btn1_die,angry_btn2_die,angry_btn3_die;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_die);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        activity_control_die_toolbar=(Toolbar)findViewById(R.id.activity_control_die_toolbar);
        setSupportActionBar(activity_control_die_toolbar);
        activity_control_die_action_bar=getSupportActionBar();
        activity_control_die_action_bar.setTitle("飲食控制");
        activity_control_die_action_bar.setDisplayHomeAsUpEnabled(true);
        angry_btn_die=(Button)findViewById(R.id.angry_btn_die);
        angry_btn1_die=(Button)findViewById(R.id.angry_bt1_die);
        angry_btn2_die=(Button)findViewById(R.id.angry_bt2_die);
        angry_btn3_die=(Button)findViewById(R.id.angry_btn3_die);
        angry_btn_die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment blankFragment=new BlankFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_control_die,
                        blankFragment,
                        blankFragment.getTag()
                ).commit();
            }
        });
        angry_btn1_die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragmentc1 blankFragment=new BlankFragmentc1();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_control_die,
                        blankFragment,
                        blankFragment.getTag()
                ).commit();
            }
        });
        angry_btn2_die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment2 blankFragment=new BlankFragment2();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_control_die,
                        blankFragment,
                        blankFragment.getTag()
                ).commit();
            }
        });
        angry_btn3_die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment3 blankFragment=new BlankFragment3();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_control_die,
                        blankFragment,
                        blankFragment.getTag()
                ).commit();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(ControlDie.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }
}
