package kelvin.tablayout;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.example.a888888888.sport.R;

public class MonitoringTool extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_tool);

        Toolbar monitoring_tool_toolbar=findViewById(R.id.monitoring_tool_toolbar);
        setSupportActionBar(monitoring_tool_toolbar);
        ActionBar monitoring_tool_actionbar=getSupportActionBar();
        if(monitoring_tool_actionbar!=null){
            monitoring_tool_actionbar.setTitle("監測工具");
            monitoring_tool_actionbar.setDisplayHomeAsUpEnabled(true);
        }
        TabLayout monitoring_tool_tabs=findViewById(R.id.monitoring_tool_tabs);
        ViewPager monitoring_tool_tab_pager=findViewById(R.id.monitoring_tool_tab_pager);
        MonitoringToolPagerAdapter monitoringToolPagerAdapter=new MonitoringToolPagerAdapter(getSupportFragmentManager());

        monitoring_tool_tab_pager.setAdapter(monitoringToolPagerAdapter);
        monitoring_tool_tabs.setupWithViewPager(monitoring_tool_tab_pager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MonitoringTool.this,ControlDie.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MonitoringTool.this,ControlDie.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
