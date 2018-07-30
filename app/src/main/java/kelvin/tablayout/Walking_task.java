package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.a888888888.sport.R;

public class Walking_task extends AppCompatActivity {
    private Toolbar walking_task;
    public String walking_data;
    public TextView data_walking_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_task);
        walking_task=(Toolbar)findViewById(R.id.Walking_task_app_bar);
        walking_task.setTitle("步行每週任務");
        walking_task.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        walking_task.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Walking_task.this,kelvin_tab_layout.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        walking_data=getIntent().getStringExtra("exercise_data");
        data_walking_task=(TextView)findViewById(R.id.data_walking_task);
        data_walking_task.setText(walking_data);
    }
}
