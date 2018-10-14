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

public class SquatsHourHistory extends AppCompatActivity {

    private static Toolbar squats_hour_history_toolbar;
    public static ActionBar actionBar;
    public static RecyclerView squats_hour_history_recycle_view;
    public static DatabaseReference squats_hour_exercise_history_database;
    private static FirebaseAuth mAuth;
    public static String day_squats_key;
    public static String hour_squats_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squats_hour_history);
        squats_hour_history_toolbar=(Toolbar)findViewById(R.id.squats_hour_history_toolbar);
        setSupportActionBar(squats_hour_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("深蹲歷史記錄");
        actionBar.setSubtitle("以時間顯示");
        actionBar.setLogo(R.drawable.squatstoolbar);
        day_squats_key=getIntent().getStringExtra("keyDay");
        Log.i("key值",day_squats_key);
        mAuth = FirebaseAuth.getInstance();
        squats_hour_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("squats").child(day_squats_key);
        squats_hour_exercise_history_database.keepSynced(true);
        squats_hour_history_recycle_view=(RecyclerView)findViewById(R.id.squats_hour_history_recycle_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(SquatsHourHistory.this);
        squats_hour_history_recycle_view.setHasFixedSize(true);
        squats_hour_history_recycle_view.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SquatsHourHistory.this,SquatsDayHistory.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public  void  onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ExerciseData3,HourSquatsHistoryViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ExerciseData3, HourSquatsHistoryViewHolder>(
                ExerciseData3.class,
                R.layout.exercise_history_layout,
                HourSquatsHistoryViewHolder.class,
                squats_hour_exercise_history_database
        ) {
            @Override
            protected void populateViewHolder(HourSquatsHistoryViewHolder viewHolder, ExerciseData3 model, int position) {
                squats_hour_exercise_history_database=getRef(position);
                hour_squats_key=squats_hour_exercise_history_database.getKey();
                viewHolder.setHourKey(hour_squats_key);

                final String keyHour =getRef(position).getKey();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_key = new Intent();
                        intent_key.setClass(SquatsHourHistory.this  , SquatsHistory.class);
                        intent_key.putExtra("keyHour",keyHour);
                        intent_key.putExtra("keyDay",day_squats_key);

                        startActivity(intent_key);
                    }
                });
            }
        };
        squats_hour_history_recycle_view.setAdapter(firebaseRecyclerAdapter);
    }

    public static class HourSquatsHistoryViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public HourSquatsHistoryViewHolder(View itemView){
            super((itemView));

            mView=itemView;
        }

        public void setHourKey(String walking){
            TextView day_exercise_history_text_view=(TextView)mView.findViewById(R.id.day_exercise_history_text_view);
            day_exercise_history_text_view.setText(walking);
        }
    }
}
