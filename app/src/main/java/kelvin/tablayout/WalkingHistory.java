package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;;

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WalkingHistory extends AppCompatActivity {

    private Toolbar walking_history_toolbar;
    public static ActionBar actionBar;
    public  static DatabaseReference walking_history_database;
    private static FirebaseAuth mAuth;
    public static String day_walking_key;
    public static String hour_walking_key;
    private TextView distance_walking_history_text_view;
    private TextView duration_of_walking_history_text_view;
    private TextView calorie_walking_history_text_view;
    private TextView mean_HeartRat_walking_history_text_view;
    private TextView max_heart_rate_walking_history_text_view;
    private TextView incline_distance_of_walking_history_text_view;
    private TextView decline_distance_of_walking_history_text_view;
    private TextView max_altitude_of_walking_history_text_view;
    private TextView min_altitude_of_walking_history_text_view;
    private TextView mean_speed_of_walking_history_text_view;
    private TextView max_speed_of_walking_history_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_history);
        walking_history_toolbar=(Toolbar)findViewById(R.id.walking_history_toolbar);
        setSupportActionBar(walking_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("步行歷史記錄");
        actionBar.setSubtitle("詳細資料");
        actionBar.setLogo(R.drawable.walkingtoolbar);
        day_walking_key=getIntent().getStringExtra("keyDay");
        hour_walking_key=getIntent().getStringExtra("keyHour");
        Log.i("keyHour值",hour_walking_key);
        Log.i("keyDay值",day_walking_key);
        distance_walking_history_text_view=(TextView)findViewById(R.id.distance_walking_history_text_view);
        duration_of_walking_history_text_view=(TextView)findViewById(R.id.duration_of_walking_history_text_view);
        calorie_walking_history_text_view=(TextView)findViewById(R.id.calorie_walking_history_text_view);
        mean_HeartRat_walking_history_text_view=(TextView)findViewById(R.id.mean_HeartRat_walking_history_text_view);
        max_heart_rate_walking_history_text_view=(TextView)findViewById(R.id.max_heart_rate_walking_history_text_view);
        incline_distance_of_walking_history_text_view=(TextView)findViewById(R.id.incline_distance_of_walking_history_text_view);
        decline_distance_of_walking_history_text_view=(TextView)findViewById(R.id.decline_distance_of_walking_history_text_view);
        max_altitude_of_walking_history_text_view=(TextView)findViewById(R.id.max_altitude_of_walking_history_text_view);
        min_altitude_of_walking_history_text_view=(TextView)findViewById(R.id.min_altitude_of_walking_history_text_view);
        mean_speed_of_walking_history_text_view=(TextView)findViewById(R.id.mean_speed_of_walking_history_text_view);
        max_speed_of_walking_history_text_view=(TextView)findViewById(R.id.max_speed_of_walking_history_text_view);
        mAuth = FirebaseAuth.getInstance();
        walking_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("walking").child(day_walking_key).child(hour_walking_key);


        walking_history_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ExerciseData newWalkingData =dataSnapshot.getValue(ExerciseData.class);
                Log.i("距離1234",""+newWalkingData.distance);
                distance_walking_history_text_view.setText("距離:"+newWalkingData.distance+"公里");
                duration_of_walking_history_text_view.setText("運動時間:"+newWalkingData.duration);
                calorie_walking_history_text_view.setText("消耗的卡路里:"+newWalkingData.calorie+"大卡");
                mean_HeartRat_walking_history_text_view.setText("平均心跳:"+newWalkingData.mean_heart_rate+"次/分");
                max_heart_rate_walking_history_text_view.setText("最高心跳:"+newWalkingData.max_heart_rate+"次/分");
                incline_distance_of_walking_history_text_view.setText("總上坡距離:"+newWalkingData.incline_distance+"公里");
                decline_distance_of_walking_history_text_view.setText("總下坡距離:"+newWalkingData.decline_distance+"公里");
                max_altitude_of_walking_history_text_view.setText("最高坡度:"+newWalkingData.max_altitude+"米");
                min_altitude_of_walking_history_text_view.setText("最低坡度:"+newWalkingData.min_altitude+"米");
                mean_speed_of_walking_history_text_view.setText("平均速度:"+newWalkingData.mean_speed+"公里/小時");
                max_speed_of_walking_history_text_view.setText("最高數度"+newWalkingData.max_speed+"公里/小時");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(WalkingHistory.this,Walking_hour_history.class);
            intent.putExtra("keyDay",day_walking_key);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
