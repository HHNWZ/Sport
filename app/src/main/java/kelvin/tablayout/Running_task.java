package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Running_task extends AppCompatActivity {
    private Toolbar running_task;
    public String running_data;
    public TextView data_running_task;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_task);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        running_task=(Toolbar)findViewById(R.id.Running_task_app_bar);
        running_task.setTitle("跑步每週任務");
        running_task.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        running_task.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Running_task.this,Exercise_main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        data_running_task=(TextView)findViewById(R.id.data_running_task);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String exercise_data =dataSnapshot.child("exercise_data").getValue().toString();
                data_running_task.setText(exercise_data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
