package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.a888888888.sport.R;

public class Push_up_task extends AppCompatActivity {
    private Toolbar push_up_task;
    public String push_up_data;
    public TextView data_push_up_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up_task);
        push_up_task=(Toolbar)findViewById(R.id.Push_up_task_app_bar);
        push_up_task.setTitle("伏地挺身每週任務");
        push_up_task.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        push_up_task.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Push_up_task.this,kelvin_tab_layout.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        push_up_data=getIntent().getStringExtra("exercise_data");
        data_push_up_task=(TextView)findViewById(R.id.data_push_up_task);
        data_push_up_task.setText(push_up_data);
    }
}
