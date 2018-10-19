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

public class SquatsHistory extends AppCompatActivity {

    private Toolbar squats_history_toolbar;
    public static ActionBar actionBar;
    public  static DatabaseReference squats_history_database;
    private static FirebaseAuth mAuth;
    public static String day_squats_key;
    public static String hour_squats_key;
    private TextView count_squats_history_text_view;
    private TextView duration_of_squats_history_text_view;
    private TextView calorie_squats_history_text_view;
    private TextView mean_HeartRat_squats_history_text_view;
    private TextView max_heart_rate_squats_history_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squats_history);

        squats_history_toolbar=(Toolbar)findViewById(R.id.squats_history_toolbar);
        setSupportActionBar(squats_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("深蹲歷史記錄");
        actionBar.setSubtitle("詳細資料");
        actionBar.setLogo(R.drawable.squatstoolbar);
        day_squats_key=getIntent().getStringExtra("keyDay");
        hour_squats_key=getIntent().getStringExtra("keyHour");
        Log.i("keyHour值",hour_squats_key);
        Log.i("keyDay值",day_squats_key);
        count_squats_history_text_view=(TextView)findViewById(R.id.count_squats_history_text_view);
        duration_of_squats_history_text_view=(TextView)findViewById(R.id.duration_of_squats_history_text_view);
        calorie_squats_history_text_view=(TextView)findViewById(R.id.calorie_squats_history_text_view);
        mean_HeartRat_squats_history_text_view=(TextView)findViewById(R.id.mean_HeartRat_squats_history_text_view);
        max_heart_rate_squats_history_text_view=(TextView)findViewById(R.id.max_heart_rate_squats_history_text_view);
        mAuth = FirebaseAuth.getInstance();
        squats_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("squats").child(day_squats_key).child(hour_squats_key);

        squats_history_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ExerciseData3 newSquatsData=dataSnapshot.getValue(ExerciseData3.class);
                Log.i("次數1234",""+newSquatsData.count2);
                count_squats_history_text_view.setText("次數:"+newSquatsData.count2+"次");
                duration_of_squats_history_text_view.setText("運輸時間:"+newSquatsData.duration);
                calorie_squats_history_text_view.setText("消耗的卡路里:"+newSquatsData.calorie+"大卡");
                mean_HeartRat_squats_history_text_view.setText("平均心跳:"+newSquatsData.mean_heart_rate+"次分");
                max_heart_rate_squats_history_text_view.setText("最高心跳:"+newSquatsData.max_heart_rate+"次/分");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SquatsHistory.this,SquatsHourHistory.class);
            intent.putExtra("keyDay",day_squats_key);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
