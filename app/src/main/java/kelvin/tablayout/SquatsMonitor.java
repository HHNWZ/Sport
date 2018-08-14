package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.a888888888.sport.R;

public class SquatsMonitor extends AppCompatActivity {
    private Toolbar squats_monitor_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squats_monitor);

        squats_monitor_toolbar=(Toolbar)findViewById(R.id.squats_monitor_toolbar);
        squats_monitor_toolbar.setTitle("深蹲監控");
        setSupportActionBar(squats_monitor_toolbar);

        squats_monitor_toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        squats_monitor_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SquatsMonitor.this,kelvin_tab_layout.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
