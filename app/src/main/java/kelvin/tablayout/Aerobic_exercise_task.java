package kelvin.tablayout;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Aerobic_exercise_task extends AppCompatActivity {

    //private Toolbar aerobic_exercise_task;
    private Toolbar  aerobic_exercise_task;
    public  String aerobic_exercise_data;
    public TextView data_aerobic_exercise_task;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aerobic_exercise_task);
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        aerobic_exercise_task=(Toolbar)findViewById(R.id.Aerobic_exercise_task_app_bar);
        aerobic_exercise_task.setTitle("有氧運動每週任務");
        aerobic_exercise_task.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        aerobic_exercise_task.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aerobic_exercise_task.this,kelvin_tab_layout.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        data_aerobic_exercise_task=(TextView)findViewById(R.id.data_aerobic_exercise_task);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String exercise_data =dataSnapshot.child("exercise_data").getValue().toString();
                data_aerobic_exercise_task.setText(exercise_data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
