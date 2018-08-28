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

public class CrunchesHistory extends AppCompatActivity {

    private Toolbar crunches_history_toolbar;
    public static ActionBar actionBar;
    public  static DatabaseReference crunches_history_database;
    private static FirebaseAuth mAuth;
    public static String day_crunches_key;
    public static String hour_crunches_key;
    public static ExerciseData3 newCrunchesData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches_history);

        crunches_history_toolbar=(Toolbar)findViewById(R.id.crunches_history_toolbar);
        setSupportActionBar(crunches_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("跑步歷史記錄");
        actionBar.setSubtitle("詳細資料");
        actionBar.setLogo(R.drawable.crunchestoolbar);
        day_crunches_key=getIntent().getStringExtra("keyDay");
        hour_crunches_key=getIntent().getStringExtra("keyHour");
        Log.i("keyHour值",hour_crunches_key);
        Log.i("keyDay值",day_crunches_key);
        mAuth = FirebaseAuth.getInstance();
        crunches_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("crunches").child(day_crunches_key).child(hour_crunches_key);

        crunches_history_database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newCrunchesData=dataSnapshot.getValue(ExerciseData3.class);
                Log.i("次數1234",""+newCrunchesData.getCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        crunches_history_database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(CrunchesHistory.this,CrunchesHourHistory.class);
            intent.putExtra("keyDay",day_crunches_key);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
