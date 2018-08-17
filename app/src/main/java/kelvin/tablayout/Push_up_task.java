package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Push_up_task extends AppCompatActivity {
    public Toolbar push_up_task;
    public String push_up_data,exercisedata,data_task,user_id;
    public TextView data_push_up_task;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up_task);
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        data_push_up_task=(TextView)findViewById(R.id.data_push_up_task);
        push_up_task=(Toolbar)findViewById(R.id.Push_up_task_app_bar);
        push_up_task.setTitle("深蹲每週任務");
        push_up_task.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        push_up_task.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Push_up_task.this,Exercise_main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String exercise_data =dataSnapshot.child("exercise_data").getValue().toString();
                data_push_up_task.setText(exercise_data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }


}
