package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Sit_up_task extends AppCompatActivity {
    private Toolbar sit_up_task;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView exerciseWeekTitle,exerciseWeekUnit,erxerciseWeekData;
    private String exerciseWeekTitleString,exerciseWeekUnitString,ToolbarTitleString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit_up_task);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        ToolbarTitleString=getIntent().getStringExtra("ToolbarTitle");
        exerciseWeekTitleString=getIntent().getStringExtra("exerciseWeekTitle");
        exerciseWeekUnitString=getIntent().getStringExtra("exerciseWeekUnit");
        sit_up_task=(Toolbar)findViewById(R.id.Sit_up_task_app_bar);
        sit_up_task.setTitle(ToolbarTitleString);
        sit_up_task.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        sit_up_task.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sit_up_task.this,Exercise_main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        erxerciseWeekData=(TextView)findViewById(R.id.exercise_week_data);
        exerciseWeekTitle=(TextView)findViewById(R.id.exercise_week_title);
        exerciseWeekUnit=(TextView)findViewById(R.id.exercise_week_unit);
        exerciseWeekTitle.setText(exerciseWeekTitleString);
        exerciseWeekUnit.setText(exerciseWeekUnitString);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String exercise_data =dataSnapshot.child("exercise_data").getValue().toString();
                erxerciseWeekData.setText(exercise_data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
