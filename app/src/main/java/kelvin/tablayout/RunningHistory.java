package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RunningHistory extends AppCompatActivity {

    private Toolbar running_history_toolbar;
    public static ActionBar actionBar;
    public  static DatabaseReference running_history_database;
    private static FirebaseAuth mAuth;
    public static String day_running_key;
    public static String hour_running_key;
    private TextView distance_running_history_text_view;
    private TextView duration_of_running_history_text_view;
    private TextView calorie_running_history_text_view;
    private TextView mean_HeartRat_running_history_text_view;
    private TextView max_heart_rate_running_history_text_view;
    private TextView incline_distance_of_running_history_text_view;
    private TextView decline_distance_of_running_history_text_view;
    private TextView max_altitude_of_running_history_text_view;
    private TextView min_altitude_of_running_history_text_view;
    private TextView mean_speed_of_running_history_text_view;
    private TextView max_speed_of_running_history_text_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_history);

        running_history_toolbar=(Toolbar)findViewById(R.id.running_history_toolbar);
        setSupportActionBar(running_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("跑步歷史記錄");
        actionBar.setSubtitle("詳細資料");
        actionBar.setLogo(R.drawable.runningtoolbar);
        day_running_key=getIntent().getStringExtra("keyDay");
        hour_running_key=getIntent().getStringExtra("keyHour");
        Log.i("keyHour值",hour_running_key);
        Log.i("keyDay值",day_running_key);
        distance_running_history_text_view=(TextView)findViewById(R.id.distance_running_history_text_view);
        duration_of_running_history_text_view=(TextView)findViewById(R.id.duration_of_running_history_text_view);
        calorie_running_history_text_view=(TextView)findViewById(R.id.calorie_running_history_text_view);
        mean_HeartRat_running_history_text_view=(TextView)findViewById(R.id.mean_HeartRat_running_history_text_view);
        max_heart_rate_running_history_text_view=(TextView)findViewById(R.id.max_heart_rate_running_history_text_view);
        incline_distance_of_running_history_text_view=(TextView)findViewById(R.id.incline_distance_of_running_history_text_view);
        decline_distance_of_running_history_text_view=(TextView)findViewById(R.id.decline_distance_of_running_history_text_view);
        max_altitude_of_running_history_text_view=(TextView)findViewById(R.id.max_altitude_of_running_history_text_view);
        min_altitude_of_running_history_text_view=(TextView)findViewById(R.id.min_altitude_of_running_history_text_view);
        mean_speed_of_running_history_text_view=(TextView)findViewById(R.id.mean_speed_of_running_history_text_view);
        max_speed_of_running_history_text_view=(TextView)findViewById(R.id.max_speed_of_running_history_text_view);
        mAuth = FirebaseAuth.getInstance();
        running_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("running").child(day_running_key).child(hour_running_key);

        running_history_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ExerciseData newRunningData =dataSnapshot.getValue(ExerciseData.class);
                Log.i("距離1234",""+newRunningData.distance);
                distance_running_history_text_view.setText("距離:"+newRunningData.distance+"公里");
                duration_of_running_history_text_view.setText("運動時間:"+newRunningData.duration);
                calorie_running_history_text_view.setText("消耗的卡路里:"+newRunningData.calorie+"大卡");
                mean_HeartRat_running_history_text_view.setText("平均心跳:"+newRunningData.mean_heart_rate+"次/分");
                max_heart_rate_running_history_text_view.setText("最高心跳:"+newRunningData.max_heart_rate+"次/分");
                incline_distance_of_running_history_text_view.setText("總上坡距離:"+newRunningData.incline_distance+"公里");
                decline_distance_of_running_history_text_view.setText("總下坡距離:"+newRunningData.decline_distance+"公里");
                max_altitude_of_running_history_text_view.setText("最高坡度:"+newRunningData.max_altitude+"米");
                min_altitude_of_running_history_text_view.setText("最低坡度:"+newRunningData.min_altitude+"米");
                mean_speed_of_running_history_text_view.setText("平均速度:"+newRunningData.mean_speed+"公里/小時");
                max_speed_of_running_history_text_view.setText("最高數度"+newRunningData.max_speed+"公里/小時");





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(RunningHistory.this,RunningHourHistory.class);
            intent.putExtra("keyDay",day_running_key);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
