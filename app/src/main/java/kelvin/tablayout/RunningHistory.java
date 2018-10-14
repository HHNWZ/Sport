package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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
        mAuth = FirebaseAuth.getInstance();
        running_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("running").child(day_running_key).child(hour_running_key);

        running_history_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ExerciseData newRunninggData =dataSnapshot.getValue(ExerciseData.class);
                Log.i("距離1234",""+newRunninggData.distance);

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
