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

public class YogaHistory extends AppCompatActivity {

    private Toolbar yoga_history_toolbar;
    public static ActionBar actionBar;
    public  static DatabaseReference yoga_history_database;
    private static FirebaseAuth mAuth;
    public static String day_yoga_key;
    public static String hour_yoga_key;
    private TextView duration_of_yoga_history_text_view;
    private TextView calorie_yoga_history_text_view;
    private TextView mean_HeartRat_yoga_history_text_view;
    private TextView max_heart_rate_yoga_history_text_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_history);

        yoga_history_toolbar=(Toolbar)findViewById(R.id.yoga_history_toolbar);
        setSupportActionBar(yoga_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("瑜伽歷史記錄");
        actionBar.setSubtitle("詳細資料");
        actionBar.setLogo(R.drawable.yogatoolbar);
        day_yoga_key=getIntent().getStringExtra("keyDay");
        hour_yoga_key=getIntent().getStringExtra("keyHour");
        Log.i("keyHour值",hour_yoga_key);
        Log.i("keyDay值",day_yoga_key);
        duration_of_yoga_history_text_view=(TextView)findViewById(R.id.duration_of_yoga_history_text_view);
        calorie_yoga_history_text_view=(TextView)findViewById(R.id.calorie_yoga_history_text_view);
        mean_HeartRat_yoga_history_text_view=(TextView)findViewById(R.id.mean_HeartRat_yoga_history_text_view);
        max_heart_rate_yoga_history_text_view=(TextView)findViewById(R.id.max_heart_rate_yoga_history_text_view);
        mAuth = FirebaseAuth.getInstance();
        yoga_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("yoga").child(day_yoga_key).child(hour_yoga_key);

        yoga_history_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ExerciseData2 newYogaData =dataSnapshot.getValue(ExerciseData2.class);
                Log.i("持續時間1234",""+newYogaData.duration);
                duration_of_yoga_history_text_view.setText("運動時間:"+newYogaData.duration);
                calorie_yoga_history_text_view.setText("消耗的卡路里:"+newYogaData.calorie+"大卡");
                mean_HeartRat_yoga_history_text_view.setText("平均心跳:"+newYogaData.mean_heart_rate+"次/分");
                max_heart_rate_yoga_history_text_view.setText("最高心跳:"+newYogaData.max_heart_rate+"次/分");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(YogaHistory.this,YogaHourHistory.class);
            intent.putExtra("keyDay",day_yoga_key);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
