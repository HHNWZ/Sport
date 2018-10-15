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

public class ControlDie extends AppCompatActivity {

    private Toolbar activity_control_die_toolbar;
    private ActionBar activity_control_die_action_bar;
    private Button go_to_diary_calendar_button,angry_btn1_die,angry_btn2_die,angry_btn3_die;

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
        go_to_diary_calendar_button=(Button)findViewById(R.id.go_to_diary_calendar_button);
        go_to_diary_calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlDie.this,DiaryCalendar.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(ControlDie.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
