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
        mAuth = FirebaseAuth.getInstance();
        walking_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("walking").child(day_walking_key).child(hour_walking_key);


        walking_history_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ExerciseData newWalkingData =dataSnapshot.getValue(ExerciseData.class);

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
