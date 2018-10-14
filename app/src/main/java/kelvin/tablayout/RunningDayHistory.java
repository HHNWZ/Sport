package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.a888888888.sport.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RunningDayHistory extends AppCompatActivity {
    private Toolbar running_day_exercise_history_toolbar;
    public static ActionBar actionBar;
    public static RecyclerView running_day_exercise_history_recycler_view;
    public static DatabaseReference running_day_exercise_history_database;
    private static FirebaseAuth mAuth;
    public static String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_day_history);
        running_day_exercise_history_toolbar=(Toolbar)findViewById(R.id.running_day_exercise_history_toolbar);
        setSupportActionBar(running_day_exercise_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("跑步歷史記錄");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("以日期顯示");
        actionBar.setLogo(R.drawable.runningtoolbar);
        mAuth = FirebaseAuth.getInstance();
        running_day_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("running");
        running_day_exercise_history_recycler_view=(RecyclerView)findViewById(R.id. running_day_exercise_history_recycler_view);
        running_day_exercise_history_database.keepSynced(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(RunningDayHistory.this);
        running_day_exercise_history_recycler_view.setHasFixedSize(true);
        running_day_exercise_history_recycler_view.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(RunningDayHistory.this,Exercise_main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ExerciseData,RunningDayExerciseHistoryViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ExerciseData, RunningDayExerciseHistoryViewHolder>(
                ExerciseData.class,
                R.layout.exercise_history_layout,
                RunningDayExerciseHistoryViewHolder.class,
                running_day_exercise_history_database

        ) {
            @Override
            protected void populateViewHolder(RunningDayExerciseHistoryViewHolder viewHolder, ExerciseData model, int position) {
                running_day_exercise_history_database=getRef(position);
                key=running_day_exercise_history_database.getKey();
                viewHolder.setDayKey(key);

                final String keyDay = getRef(position).getKey();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_key = new Intent();
                        intent_key.setClass(RunningDayHistory.this  , RunningHourHistory.class);
                        intent_key.putExtra("keyDay",keyDay);
                        startActivity(intent_key);
                    }
                });
            }
        };
        running_day_exercise_history_recycler_view.setAdapter(firebaseRecyclerAdapter);

    }

    public static class RunningDayExerciseHistoryViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public RunningDayExerciseHistoryViewHolder(View itemView){
            super(itemView);

            mView=itemView;
        }



        public void setDayKey(String walking){
            TextView day_exercise_history_text_view=(TextView)mView.findViewById(R.id.day_exercise_history_text_view);
            day_exercise_history_text_view.setText(walking);
        }
    }
}
