package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.a888888888.sport.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RunningHourHistory extends AppCompatActivity {

    private static Toolbar running_hour_history_toolbar;
    public static ActionBar actionBar;
    public static RecyclerView running_hour_history_recycle_view;
    public static DatabaseReference running_hour_exercise_history_database;
    private static FirebaseAuth mAuth;
    public static String day_running_key;
    public static String hour_running_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_hour_history);
        running_hour_history_toolbar=(Toolbar)findViewById(R.id.running_hour_history_toolbar);
        setSupportActionBar(running_hour_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("跑步歷史記錄");
        actionBar.setSubtitle("以時間顯示");
        actionBar.setLogo(R.drawable.runningtoolbar);
        day_running_key=getIntent().getStringExtra("keyDay");
        Log.i("key值",day_running_key);
        mAuth = FirebaseAuth.getInstance();
        running_hour_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("running").child(day_running_key);
        running_hour_exercise_history_database.keepSynced(true);
        running_hour_history_recycle_view=(RecyclerView)findViewById(R.id.running_hour_history_recycle_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(RunningHourHistory.this);
        running_hour_history_recycle_view.setHasFixedSize(true);
        running_hour_history_recycle_view.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(RunningHourHistory.this,RunningDayHistory.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public  void  onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ExerciseData,HourRunningHistoryViewHolder>firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<ExerciseData, HourRunningHistoryViewHolder>(
                ExerciseData.class,
                R.layout.exercise_history_layout,
                HourRunningHistoryViewHolder.class,
                running_hour_exercise_history_database
        ) {
            @Override
            protected void populateViewHolder(HourRunningHistoryViewHolder viewHolder, ExerciseData model, int position) {
                running_hour_exercise_history_database=getRef(position);
                hour_running_key=running_hour_exercise_history_database.getKey();
                viewHolder.setHourKey(hour_running_key);

                final String keyHour =getRef(position).getKey();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_key = new Intent();
                        intent_key.setClass(RunningHourHistory.this  , RunningHistory.class);
                        intent_key.putExtra("keyHour",keyHour);
                        intent_key.putExtra("keyDay",day_running_key);

                        startActivity(intent_key);
                    }
                });

            }
        };

        running_hour_history_recycle_view.setAdapter(firebaseRecyclerAdapter);

    }

    public static class HourRunningHistoryViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public HourRunningHistoryViewHolder(View itemView){
            super((itemView));

            mView=itemView;
        }

        public void setHourKey(String walking){
            TextView day_exercise_history_text_view=(TextView)mView.findViewById(R.id.day_exercise_history_text_view);
            day_exercise_history_text_view.setText(walking);
        }
    }
}
