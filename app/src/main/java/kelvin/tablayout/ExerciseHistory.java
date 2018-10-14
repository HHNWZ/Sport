package kelvin.tablayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class ExerciseHistory extends AppCompatActivity {
    private Toolbar day_exercise_history_toolbar;
    private static int i=0;
    private static int k=0;
    public static ActionBar actionBar;
    public static RecyclerView walking_day_exercise_history;
    public static DatabaseReference walking_day_exercise_history_database,newDabase;
    private static FirebaseAuth mAuth;
    public static String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_history);
        day_exercise_history_toolbar=(Toolbar)findViewById(R.id.day_exercise_history_toolbar);

        setSupportActionBar(day_exercise_history_toolbar);
        actionBar =getSupportActionBar();
        actionBar.setTitle("步行歷史記錄");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("以日期顯示");
        actionBar.setLogo(R.drawable.walkingtoolbar);
        //actionBar.setDisplayShowTitleEnabled(false);
        Log.i("i值",""+i);
        mAuth = FirebaseAuth.getInstance();
        walking_day_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("walking");
        //newDabase=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("walking");
        walking_day_exercise_history=(RecyclerView)findViewById(R.id.walking_day_exercise_history);
        walking_day_exercise_history_database.keepSynced(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(ExerciseHistory.this);
        walking_day_exercise_history.setHasFixedSize(true);
        walking_day_exercise_history.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ExerciseHistory.this,Exercise_main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<ExerciseData,WalkingDayExerciseHistoryViewHolder> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<ExerciseData, WalkingDayExerciseHistoryViewHolder>(
                ExerciseData.class,
                R.layout.exercise_history_layout,
                WalkingDayExerciseHistoryViewHolder.class,
                walking_day_exercise_history_database.orderByKey()
        ) {
            @Override
            protected void populateViewHolder(WalkingDayExerciseHistoryViewHolder viewHolder, ExerciseData model, int position) {
                walking_day_exercise_history_database=getRef(position);
                key=walking_day_exercise_history_database.getKey();
                viewHolder.setDayKey(key);

                final String keyDay = getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_key = new Intent();
                        intent_key.setClass(ExerciseHistory.this  , Walking_hour_history.class);
                        intent_key.putExtra("keyDay",keyDay);
                        startActivity(intent_key);
                    }
                });


            }
        };

        walking_day_exercise_history.setAdapter(firebaseRecyclerAdapter);
    }

    public static class WalkingDayExerciseHistoryViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public WalkingDayExerciseHistoryViewHolder(View itemView){
            super(itemView);

            mView=itemView;
        }



        public void setDayKey(String walking){
            TextView day_exercise_history_text_view=(TextView)mView.findViewById(R.id.day_exercise_history_text_view);
            day_exercise_history_text_view.setText(walking);
        }
    }
}
