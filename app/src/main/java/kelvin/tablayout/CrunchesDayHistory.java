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

public class CrunchesDayHistory extends AppCompatActivity {
    private Toolbar crunches_day_exercise_history_toolbar;
    public static ActionBar actionBar;
    public static RecyclerView crunches_day_exercise_history_recycler_view;
    public static DatabaseReference crunches_day_exercise_history_database;
    private static FirebaseAuth mAuth;
    public static String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches_day_history);
        crunches_day_exercise_history_toolbar=(Toolbar)findViewById(R.id.crunches_day_exercise_history_toolbar);
        setSupportActionBar(crunches_day_exercise_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("仰臥起坐歷史記錄");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("以日期顯示");
        actionBar.setLogo(R.drawable.crunchestoolbar);
        mAuth = FirebaseAuth.getInstance();
        crunches_day_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("crunches");
        crunches_day_exercise_history_recycler_view=(RecyclerView)findViewById(R.id.crunches_day_exercise_history_recycler_view);
        crunches_day_exercise_history_database.keepSynced(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(CrunchesDayHistory.this);
        crunches_day_exercise_history_recycler_view.setHasFixedSize(true);
        crunches_day_exercise_history_recycler_view.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(CrunchesDayHistory.this,Exercise_main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ExerciseData3,CrunchesDayExerciseHistoryViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ExerciseData3, CrunchesDayExerciseHistoryViewHolder>(
                ExerciseData3.class,
                R.layout.exercise_history_layout,
                CrunchesDayExerciseHistoryViewHolder.class,
                crunches_day_exercise_history_database
        ) {
            @Override
            protected void populateViewHolder(CrunchesDayExerciseHistoryViewHolder viewHolder, ExerciseData3 model, int position) {
                crunches_day_exercise_history_database=getRef(position);
                key=crunches_day_exercise_history_database.getKey();
                viewHolder.setDayKey(key);

                final String keyDay = getRef(position).getKey();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_key = new Intent();
                        intent_key.setClass(CrunchesDayHistory.this  , CrunchesHourHistory.class);
                        intent_key.putExtra("keyDay",keyDay);
                        startActivity(intent_key);
                    }
                });
            }
        };
        crunches_day_exercise_history_recycler_view.setAdapter(firebaseRecyclerAdapter);

    }

    public static class CrunchesDayExerciseHistoryViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public CrunchesDayExerciseHistoryViewHolder(View itemView){
            super(itemView);

            mView=itemView;
        }



        public void setDayKey(String walking){
            TextView day_exercise_history_text_view=(TextView)mView.findViewById(R.id.day_exercise_history_text_view);
            day_exercise_history_text_view.setText(walking);
        }
    }
}
