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
    public static TextView distance_data_of_walking_monitor,duration_data_of_walking_monitor,meanHeartRate_data_of_walking_monitor,start_time_data_of_walking_monitor,
            end_time_data_of_walking_monitor,calorie_data_of_walking_monitor,incline_distance_data_of_walking_monitor,decline_distance_data_of_walking_monitor,max_heart_rate_data_of_walking_monitor,
            max_altitude_data_of_walking_monitor,min_altitude_data_of_walking_monitor,mean_speed_data_of_walking_monitor,max_speed_data_of_walking_monitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_history);
        walking_history_toolbar=(Toolbar)findViewById(R.id.walking_history_toolbar);
        setSupportActionBar(walking_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("詳細資料");
        actionBar.setLogo(R.drawable.walkingtoolbar);
        day_walking_key=getIntent().getStringExtra("keyDay");
        hour_walking_key=getIntent().getStringExtra("keyHour");
        Log.i("keyHour值",hour_walking_key);
        Log.i("keyDay值",day_walking_key);
        mAuth = FirebaseAuth.getInstance();
        walking_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("walking").child(day_walking_key).child(hour_walking_key);
        distance_data_of_walking_monitor = (TextView) findViewById(R.id.distance_data_of_walking_monitor);
        duration_data_of_walking_monitor = (TextView) findViewById(R.id.duration_data_of_walking_monitor);
        meanHeartRate_data_of_walking_monitor = (TextView) findViewById(R.id.meanHeartRate_data_of_walking_monitor);
        start_time_data_of_walking_monitor = (TextView) findViewById(R.id.start_time_data_of_walking_monitor);
        end_time_data_of_walking_monitor = (TextView) findViewById(R.id.end_time_data_of_walking_monitor);
        calorie_data_of_walking_monitor = (TextView) findViewById(R.id.calorie_data_of_walking_monitor);
        incline_distance_data_of_walking_monitor = (TextView) findViewById(R.id.incline_distance_data_of_walking_monitor);
        decline_distance_data_of_walking_monitor = (TextView) findViewById(R.id.decline_distance_data_of_walking_monitor);
        max_heart_rate_data_of_walking_monitor = (TextView) findViewById(R.id.max_heart_rate_data_of_walking_monitor);
        max_altitude_data_of_walking_monitor = (TextView) findViewById(R.id.max_altitude_data_of_walking_monitor);
        min_altitude_data_of_walking_monitor = (TextView) findViewById(R.id.min_altitude_data_of_walking_monitor);
        mean_speed_data_of_walking_monitor = (TextView) findViewById(R.id.mean_speed_data_of_walking_monitor);
        max_speed_data_of_walking_monitor = (TextView) findViewById(R.id.max_speed_data_of_walking_monitor);

        walking_history_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ExerciseData newWalkingData =dataSnapshot.getValue(ExerciseData.class);
                distance_data_of_walking_monitor.setText(""+newWalkingData.distance+"公里");
                duration_data_of_walking_monitor.setText(newWalkingData.duration);
                meanHeartRate_data_of_walking_monitor.setText(""+newWalkingData.mean_heart_rate+"次/分");
                start_time_data_of_walking_monitor.setText(newWalkingData.start_time);
                end_time_data_of_walking_monitor.setText(newWalkingData.end_time);
                calorie_data_of_walking_monitor.setText(""+newWalkingData.calorie+"大卡");
                incline_distance_data_of_walking_monitor.setText(""+newWalkingData.incline_distance+"公里");
                decline_distance_data_of_walking_monitor.setText(""+newWalkingData.decline_distance+"公里");
                max_heart_rate_data_of_walking_monitor.setText(""+newWalkingData.max_heart_rate+"次/分");
                max_altitude_data_of_walking_monitor.setText(""+newWalkingData.max_altitude+"米");
                min_altitude_data_of_walking_monitor.setText(""+newWalkingData.min_altitude+"米");
                mean_speed_data_of_walking_monitor.setText(""+newWalkingData.mean_speed+"公里/小時");
                max_speed_data_of_walking_monitor.setText(""+newWalkingData.max_speed+"公里/小時");
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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
