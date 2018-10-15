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

public class CrunchesHourHistory extends AppCompatActivity {

    private static Toolbar crunches_hour_history_toolbar;
    public static ActionBar actionBar;
    public static RecyclerView crunches_hour_history_recycle_view;
    public static DatabaseReference crunches_hour_exercise_history_database;
    private static FirebaseAuth mAuth;
    public static String day_crunches_key;
    public static String hour_crunches_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches_hour_history);
        crunches_hour_history_toolbar=(Toolbar)findViewById(R.id.crunches_hour_history_toolbar);
        setSupportActionBar(crunches_hour_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("仰臥起坐歷史記錄");
        actionBar.setSubtitle("以時間顯示");
        actionBar.setLogo(R.drawable.crunchestoolbar);
        day_crunches_key=getIntent().getStringExtra("keyDay");
        Log.i("key值",day_crunches_key);
        mAuth = FirebaseAuth.getInstance();
        crunches_hour_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("crunches").child(day_crunches_key);
        crunches_hour_exercise_history_database.keepSynced(true);
        crunches_hour_history_recycle_view=(RecyclerView)findViewById(R.id.crunches_hour_history_recycle_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(CrunchesHourHistory.this);
        crunches_hour_history_recycle_view.setHasFixedSize(true);
        crunches_hour_history_recycle_view.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        day_crunches_key=getIntent().getStringExtra("keyDay");
        Log.i("key值",day_crunches_key);
        mAuth = FirebaseAuth.getInstance();
        crunches_hour_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("crunches").child(day_crunches_key);
        crunches_hour_exercise_history_database.keepSynced(true);
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(CrunchesHourHistory.this,CrunchesDayHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public  void  onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ExerciseData3,HourCrunchesHistoryViewHolder>firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<ExerciseData3, HourCrunchesHistoryViewHolder>(
                ExerciseData3.class,
                R.layout.exercise_history_layout,
                HourCrunchesHistoryViewHolder.class,
                crunches_hour_exercise_history_database
        ) {
            @Override
            protected void populateViewHolder(HourCrunchesHistoryViewHolder viewHolder, ExerciseData3 model, int position) {
                crunches_hour_exercise_history_database=getRef(position);
                hour_crunches_key=crunches_hour_exercise_history_database.getKey();
                viewHolder.setHourKey(hour_crunches_key);

                final String keyHour =getRef(position).getKey();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_key = new Intent();
                        intent_key.setClass(CrunchesHourHistory.this  , CrunchesHistory.class);
                        intent_key.putExtra("keyHour",keyHour);
                        intent_key.putExtra("keyDay",day_crunches_key);

                        startActivity(intent_key);
                    }
                });
            }
        };
        crunches_hour_history_recycle_view.setAdapter(firebaseRecyclerAdapter);

    }

    public static class HourCrunchesHistoryViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public HourCrunchesHistoryViewHolder(View itemView){
            super((itemView));

            mView=itemView;
        }

        public void setHourKey(String walking){
            TextView day_exercise_history_text_view=(TextView)mView.findViewById(R.id.day_exercise_history_text_view);
            day_exercise_history_text_view.setText(walking);
        }
    }
}
