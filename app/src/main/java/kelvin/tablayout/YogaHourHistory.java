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

public class YogaHourHistory extends AppCompatActivity {

    private static Toolbar yoga_hour_history_toolbar;
    public static ActionBar actionBar;
    public static RecyclerView yoga_hour_history_recycle_view;
    public static DatabaseReference yoga_hour_exercise_history_database;
    private static FirebaseAuth mAuth;
    public static String day_yoga_key;
    public static String hour_yoga_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_hour_history);
        yoga_hour_history_toolbar=(Toolbar)findViewById(R.id.yoga_hour_history_toolbar);
        setSupportActionBar(yoga_hour_history_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("瑜伽歷史記錄");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("以時間顯示");
        actionBar.setLogo(R.drawable.yogatoolbar);
        GlobalVariable yoga_history=(GlobalVariable)getApplicationContext();
        day_yoga_key=yoga_history.getKeyDay();
        Log.i("key值",day_yoga_key);
        mAuth = FirebaseAuth.getInstance();
        yoga_hour_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("yoga").child(day_yoga_key);
        yoga_hour_exercise_history_database.keepSynced(true);
        yoga_hour_history_recycle_view=(RecyclerView)findViewById(R.id.yoga_hour_history_recycle_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(YogaHourHistory.this);
        yoga_hour_history_recycle_view.setHasFixedSize(true);
        yoga_hour_history_recycle_view.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(YogaHourHistory.this,YogaDayHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        Intent intent = new Intent(YogaHourHistory.this,YogaDayHistory.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public  void  onStart() {
        super.onStart();
        GlobalVariable yoga_history=(GlobalVariable)getApplicationContext();
        day_yoga_key=yoga_history.getKeyDay();
        Log.i("key值",day_yoga_key);
        mAuth = FirebaseAuth.getInstance();
        yoga_hour_exercise_history_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise").child("yoga").child(day_yoga_key);
        yoga_hour_exercise_history_database.keepSynced(true);

        FirebaseRecyclerAdapter<ExerciseData,HourYogaHistoryViewHolder> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<ExerciseData, HourYogaHistoryViewHolder>(
                ExerciseData.class,
                R.layout.exercise_history_layout,
                HourYogaHistoryViewHolder.class,
                yoga_hour_exercise_history_database
        ) {
            @Override
            protected void populateViewHolder(HourYogaHistoryViewHolder viewHolder, ExerciseData model, int position) {
                yoga_hour_exercise_history_database=getRef(position);
                hour_yoga_key=yoga_hour_exercise_history_database.getKey();
                viewHolder.setHourKey(hour_yoga_key);

                final String keyHour =getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GlobalVariable yoga_history=(GlobalVariable)getApplicationContext();
                        yoga_history.setKeyHour(keyHour);
                        Intent intent_key = new Intent();
                        intent_key.setClass(YogaHourHistory.this  , YogaHistory.class);
                        startActivity(intent_key);
                    }
                });

            }
        };

        yoga_hour_history_recycle_view.setAdapter(firebaseRecyclerAdapter);

    }

    public static class HourYogaHistoryViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public HourYogaHistoryViewHolder(View itemView){
            super((itemView));

            mView=itemView;
        }

        public void setHourKey(String yoga){
            TextView day_exercise_history_text_view=(TextView)mView.findViewById(R.id.day_exercise_history_text_view);
            day_exercise_history_text_view.setText(yoga);
        }
    }
}
