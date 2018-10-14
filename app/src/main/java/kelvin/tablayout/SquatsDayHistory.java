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

public class SquatsDayHistory extends AppCompatActivity {

    private Toolbar squats_day_exercise_history_toolbar;
    public static ActionBar actionBar;
    public static RecyclerView squats_day_exercise_history_recycler_view;
    public static DatabaseReference squats_day_exercise_history_database;
    private static FirebaseAuth mAuth;
    public static String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squats_day_history);
        squats_day_exercise_history_toolbar=(Toolbar)findViewById(R.id.squats_day_exercise_history_toolbar);
        setSupportActionBar(squats_day_exercise_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("深蹲歷史記錄");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("以日期顯示");
        actionBar.setLogo(R.drawable.squatstoolbar);
        mAuth = FirebaseAuth.getInstance();
        squats_day_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("squats");
        squats_day_exercise_history_recycler_view=(RecyclerView)findViewById(R.id. squats_day_exercise_history_recycler_view);
        squats_day_exercise_history_database.keepSynced(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(SquatsDayHistory.this);
        squats_day_exercise_history_recycler_view.setHasFixedSize(true);
        squats_day_exercise_history_recycler_view.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SquatsDayHistory.this,Exercise_main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ExerciseData3,SquatsDayExerciseHistoryViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ExerciseData3, SquatsDayExerciseHistoryViewHolder>(
                ExerciseData3.class,
                R.layout.exercise_history_layout,
                SquatsDayExerciseHistoryViewHolder.class,
                squats_day_exercise_history_database
        ) {
            @Override
            protected void populateViewHolder(SquatsDayExerciseHistoryViewHolder viewHolder, ExerciseData3 model, int position) {
                squats_day_exercise_history_database=getRef(position);
                key=squats_day_exercise_history_database.getKey();
                viewHolder.setDayKey(key);

                final String keyDay = getRef(position).getKey();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_key = new Intent();
                        intent_key.setClass(SquatsDayHistory.this  , SquatsHourHistory.class);
                        intent_key.putExtra("keyDay",keyDay);
                        startActivity(intent_key);
                    }
                });
            }
        };
        squats_day_exercise_history_recycler_view.setAdapter(firebaseRecyclerAdapter);

    }

    public static class SquatsDayExerciseHistoryViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public SquatsDayExerciseHistoryViewHolder(View itemView){
            super(itemView);

            mView=itemView;
        }



        public void setDayKey(String walking){
            TextView day_exercise_history_text_view=(TextView)mView.findViewById(R.id.day_exercise_history_text_view);
            day_exercise_history_text_view.setText(walking);
        }
    }
}
