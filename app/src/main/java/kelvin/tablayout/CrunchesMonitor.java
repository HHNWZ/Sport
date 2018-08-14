package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.example.a888888888.sport.R;

public class CrunchesMonitor extends AppCompatActivity {
    private Toolbar crunches_monitor_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches_monitor);

        crunches_monitor_toolbar=(Toolbar)findViewById(R.id.crunches_monitor_toolbar);
        crunches_monitor_toolbar.setTitle("仰臥起坐監控");
        setSupportActionBar(crunches_monitor_toolbar);

        crunches_monitor_toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        crunches_monitor_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CrunchesMonitor.this,kelvin_tab_layout.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
