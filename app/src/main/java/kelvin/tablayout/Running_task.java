package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.a888888888.sport.R;

public class Running_task extends AppCompatActivity {
    private Toolbar running_task;
    public String running_data;
    public TextView data_running_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_task);
        running_task=(Toolbar)findViewById(R.id.Running_task_app_bar);
        running_task.setTitle("跑步每週任務");
        running_task.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        running_task.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Running_task.this,kelvin_tab_layout.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        running_data=getIntent().getStringExtra("exercise_data");
        data_running_task=(TextView)findViewById(R.id.data_running_task);
        data_running_task.setText(running_data);
    }
}
