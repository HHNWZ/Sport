package kelvin.tablayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.example.a888888888.sport.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;

public class MonitoringTool extends AppCompatActivity implements MenustrualCycle.OnFragmentInteractionListener{
    int days=7;
    int times=30;
    public final CalendarDay Today = CalendarDay.today();//取得今天日期
    public CalendarDay seleDAY=Today;//選擇預設為今天
    public final ArrayList<CalendarDay> DL=new ArrayList<>();//日記.日期
    public int dateID;//
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
    public void restart(){
        MenustrualCycle myF=MenustrualCycle.newInstance(days,times);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.myfrag,
                myF,
                myF.getTag()
        ).commit();
    }
    public void myDayChanged(CalendarDay mydate) {//選擇日期
        seleDAY=mydate;//紀錄選擇日期
    }
    private String showTrueDate(CalendarDay cDay){
        return cDay.getYear()+"/"+(cDay.getMonth()+1)+"/"+cDay.getDay();
    }
    public void readMyDATAWord(){//從檔案中讀取，若無則重設陣列
        SharedPreferences spref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = spref.edit();
        /*int DATAsize=spref.getAll().size();
        if(spref.contains("EW_10")){
        myWordList_ew.clear();
        myWordList_cw.clear();
        for(int i=0;i<DATAsize/3;i++){
        addNewWord(spref.getString("EW_"+Integer.toString(i),"NO！！"),
        spref.getString("CW_"+Integer.toString(i),"沒字！！")
        );
        }
        }else{
        resetNewWord();
        }*/
    }
    public void writeNewWordtoDATA(){//將當前陣列存入檔案
        SharedPreferences spref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = spref.edit();
        editor.clear();
        /*for(int i=0;i<myWordList_ew.size();i++){
        editor.putString("EW_"+Integer.toString(i),myWordList_ew.get(i))
        .putString("CW_"+Integer.toString(i),myWordList_cw.get(i))
        .commit();
        }*/
    }


    public void onFragmentInteraction(String Tag, String number) {

    }
}
