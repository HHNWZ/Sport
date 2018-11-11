package kelvin.tablayout;

import android.content.Intent;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;


import com.example.a888888888.sport.MainActivity;

import com.example.a888888888.sport.R;

import com.onesignal.OneSignal;




public class ControlDie extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_die);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        Toolbar activity_control_die_toolbar = findViewById(R.id.activity_control_die_toolbar);
        setSupportActionBar(activity_control_die_toolbar);
        ActionBar activity_control_die_action_bar = getSupportActionBar();
        if (activity_control_die_action_bar != null) {
            activity_control_die_action_bar.setTitle("飲食控制");
            activity_control_die_action_bar.setDisplayHomeAsUpEnabled(true);
        }

        Button go_to_diary_calendar_button =  findViewById(R.id.go_to_diary_calendar_button);
        Button common_food_calories_button = findViewById(R.id.common_food_calories_button);
        Button ingestion_advice_button=findViewById(R.id.ingestion_advice_button);
        Button monitoring_tool_button=findViewById(R.id.monitoring_tool_button);
        go_to_diary_calendar_button.setOnClickListener(v -> {
            Intent intent = new Intent(ControlDie.this,DiaryCalendar.class);
            startActivity(intent);
        });
        common_food_calories_button.setOnClickListener(v -> {
            Intent intent = new Intent(ControlDie.this,CommonFoodCalories.class);
            startActivity(intent);
        });
        ingestion_advice_button.setOnClickListener(v -> {
            Intent intent = new Intent(ControlDie.this,IngestionAdvice.class);
            startActivity(intent);
        });
        monitoring_tool_button.setOnClickListener(v -> {
            Intent intent = new Intent(ControlDie.this,MonitoringTool.class);
            startActivity(intent);
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ControlDie.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }



}
