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

public class Walking_hour_history extends AppCompatActivity{

        private Toolbar hour_walking_history_toolbar;
        private static int i=0;
        private static int k=0;
        public static ActionBar actionBar;
        public static RecyclerView hour_walking_history_recycle_view;
        public static DatabaseReference hour_walking_exercise_history_database;
        private static FirebaseAuth mAuth;
        public static String day_walking_key;
        public static String hour_walking_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_hour_history);
        hour_walking_history_toolbar=(Toolbar)findViewById(R.id.hour_walking_history_toolbar);
        setSupportActionBar(hour_walking_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("以時間顯示");
        actionBar.setTitle("步行歷史記錄");
        actionBar.setLogo(R.drawable.walkingtoolbar);
        GlobalVariable walking_history=(GlobalVariable)getApplicationContext();
        day_walking_key=walking_history.getKeyDay();
        Log.i("key值",day_walking_key);
        mAuth = FirebaseAuth.getInstance();
        hour_walking_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("walking").child(day_walking_key);
        hour_walking_exercise_history_database.keepSynced(true);
        hour_walking_history_recycle_view=(RecyclerView)findViewById(R.id.hour_walking_history_recycle_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(Walking_hour_history.this);
        hour_walking_history_recycle_view.setHasFixedSize(true);
        hour_walking_history_recycle_view.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Walking_hour_history.this,ExerciseHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public  void  onStart(){
        super.onStart();
        GlobalVariable walking_history=(GlobalVariable)getApplicationContext();
        day_walking_key=walking_history.getKeyDay();
        Log.i("key值",day_walking_key);
        mAuth = FirebaseAuth.getInstance();
        hour_walking_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("walking").child(day_walking_key);
        hour_walking_exercise_history_database.keepSynced(true);
        FirebaseRecyclerAdapter<ExerciseData,HourWalkingHistoryViewHolder>firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<ExerciseData, HourWalkingHistoryViewHolder>(
                ExerciseData.class,
                R.layout.exercise_history_layout,
                HourWalkingHistoryViewHolder.class,
                hour_walking_exercise_history_database
        ) {
            @Override
            protected void populateViewHolder(HourWalkingHistoryViewHolder viewHolder, ExerciseData model, int position) {
                hour_walking_exercise_history_database=getRef(position);
                hour_walking_key=hour_walking_exercise_history_database.getKey();
                viewHolder.setHourKey(hour_walking_key);

                final String keyHour =getRef(position).getKey();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GlobalVariable walking_history=(GlobalVariable)getApplicationContext();
                        walking_history.setKeyHour(keyHour);
                        Intent intent_key = new Intent();
                        intent_key.setClass(Walking_hour_history.this  , WalkingHistory.class);
                        startActivity(intent_key);
                    }
                });
            }
        };
        hour_walking_history_recycle_view.setAdapter(firebaseRecyclerAdapter);
    }

    public static class HourWalkingHistoryViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public HourWalkingHistoryViewHolder(View itemView){
            super((itemView));

            mView=itemView;
        }

        public void setHourKey(String walking){
            TextView day_exercise_history_text_view=(TextView)mView.findViewById(R.id.day_exercise_history_text_view);
            day_exercise_history_text_view.setText(walking);
        }
    }
}
