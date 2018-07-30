package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a888888888.sport.R;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Sit_up_task extends AppCompatActivity {
    private Toolbar sit_up_task;
    public String sit_up_data;
    public TextView data_sit_up_task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit_up_task);
        sit_up_task=(Toolbar)findViewById(R.id.Sit_up_task_app_bar);
        sit_up_task.setTitle("仰臥起坐每週任務");
        sit_up_task.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        sit_up_task.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sit_up_task.this,kelvin_tab_layout.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        sit_up_data=getIntent().getStringExtra("exercise_data");
        data_sit_up_task=(TextView)findViewById(R.id.data_sit_up_task);
        data_sit_up_task.setText(sit_up_data);

    }
}
