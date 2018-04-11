package com.example.a888888888.sport;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import kelvin.tablayout.kelvin_tab_layout;
import necowneco.tablayout.habaActivity;
import qwer.BlankFragment;
import qwer.BlankFragment2;
import qwer.BlankFragment3;
import qwer.BlankFragmentc1;
import qwer.BlankFragmentc2;
import qwer.BlankFragmentc3;
import qwer.BlankFragmentc4;
import qwer.Dietcontrol;
import qwer.ShowDiary;
import qwer.addDiary;

public class  MainActivity extends AppCompatActivity
        implements Over.OnFragmentInteractionListener,Sport.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener, BlankFragment3.OnFragmentInteractionListener
        ,Run.OnFragmentInteractionListener,Walk.OnFragmentInteractionListener,Air.OnFragmentInteractionListener,Sit.OnFragmentInteractionListener,Push.OnFragmentInteractionListener,Login.OnFragmentInteractionListener,
        ShowDiary.OnFragmentInteractionListener,addDiary.OnFragmentInteractionListener,BlankFragmentc1.OnFragmentInteractionListener , BlankFragmentc2.OnFragmentInteractionListener , BlankFragmentc3.OnFragmentInteractionListener , BlankFragmentc4.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener
        ,Userdata.OnFragmentInteractionListener{
    public final ArrayList<CalendarDay> DL=new ArrayList<>();//日記.日期
    public final ArrayList<String> diarys=new ArrayList<>();//日記.內容
    public final CalendarDay Today = CalendarDay.today();//取得今天日期
    public CalendarDay seleDAY=Today;//選擇預設為今天
    private String showUri = "http://172.30.4.40:1335/test123.php";//連至資料庫
    private TextView rundata;
    private TextView walkdata;
    private TextView airdata;
    private TextView pushdata;
    private TextView sitdata;
    private MyDBHelper dbHelper; //內建資料庫
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolboar;
    com.android.volley.RequestQueue requestQueue;

    public MainActivity() {
    }

    private void getData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,showUri, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            JSONArray data = response.getJSONArray("data");
                            //這邊要和上面json的名稱一樣
                            //下邊是把全部資料都印出來
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jasondata = data.getJSONObject(i);
                                /*ContentValues values = new ContentValues();
                                values.put("run", jasondata.getString("run"));*/
                                rundata.setText(jasondata.getString("run"));
                                walkdata.setText(jasondata.getString("walk"));
                                airdata.setText(jasondata.getString("air"));
                                sitdata.setText(jasondata.getString("sit"));
                                pushdata.setText(jasondata.getString("push"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void addOneDiary(CalendarDay date,String diary){//寫入單筆日記資料
        DL.add(date);
        diarys.add(diary);
    }
    private CalendarDay getOneDate(int myYEAR,int myMONTH,int myDAY){//將字串資料轉換為日期型態
        Calendar c = Calendar.getInstance();//先定義為今日
        //c.getCalendar().set(myYEAR, myMONTH, myDAY);
        int YearDif=myYEAR-seleDAY.getYear();c.add(Calendar.YEAR,YearDif);//傳值與今日的年之差
        int MonthDif=myMONTH-seleDAY.getMonth();c.add(Calendar.MONTH,MonthDif);//傳值與今日的月之差
        int DayDif=myDAY-seleDAY.getDay();c.add(Calendar.DATE,DayDif);//傳值與今日的日之差
        //將變數c調整至傳直指定之日期
        return CalendarDay.from(c);//將變數c轉變為要求型態CalendarDay
    }
    public void readMyDiaryDATA(){//從檔案中讀取日記，若無則陣列歸零
        SharedPreferences spref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = spref.edit();
        int DATAsize=spref.getAll().size();
        //Toast.makeText(this, DATAsize, Toast.LENGTH_SHORT).show();
        if(spref.getBoolean("NonDiary",true)) {//驗證檔案內存在日記資料
            Toast.makeText(this, "目前尚無日記"+DATAsize, Toast.LENGTH_SHORT).show();
        }else{
            DL.clear();
            diarys.clear();
            for(int i=0;i<(DATAsize-1)/4;i++){
                addOneDiary(getOneDate(spref.getInt("DL_Y_"+Integer.toString(i),0),
                                        spref.getInt("DL_M_"+Integer.toString(i),0),
                                        spref.getInt("DL_D_"+Integer.toString(i),0)
                        ),spref.getString("diarys_"+Integer.toString(i),null)
                );
            }
            //Toast.makeText(this, "資料量："+DATAsize, Toast.LENGTH_SHORT).show();
        }
    }
    public void writAllDiaryDATA(){//將當前日記陣列存入檔案
        //Toast.makeText(this, "日期數量："+DL.size()+"，日記數量："+diarys.size(), Toast.LENGTH_SHORT).show();

        SharedPreferences spref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = spref.edit();
        editor.clear().commit();
        if(DL.size()>0){//存入時陣列中有資料
            editor.putBoolean("NonDiary",false);//標示有資料
            for(int i=0;i<DL.size();i++){//逐一寫入日寄資料
                editor.putInt("DL_Y_"+Integer.toString(i),DL.get(i).getYear())
                        .putInt("DL_M_"+Integer.toString(i),DL.get(i).getMonth())
                        .putInt("DL_D_"+Integer.toString(i),DL.get(i).getDay())
                        .putString("diarys_"+Integer.toString(i),diarys.get(i));
            }
        }else{
            editor.putBoolean("NonDiary",true);
        }
        editor.commit();

        //editor.clear().commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolboar=(Toolbar)findViewById(R.id.nav_action); //替換toolbar會爆
        setSupportActionBar(mToolboar);//Toolbar取代原本的ActionBar
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);//必須用字串資源檔
        mDrawerLayout.addDrawerListener(mToggle);//監聽選單按鈕是否被觸擊
        mToggle.syncState();//隱藏顯示箭頭返回
        //讓 ActionBar 中的返回箭號置換成 Drawer 的三條線圖示。並且把這個觸發器指定給 layDrawer 。
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);//清單觸發監聽事件

        Button kel = (Button)findViewById(R.id.button); //連至書輝的按鈕
        Button hal = (Button)findViewById(R.id.button1); //連至弘盛的按鈕
        Button del = (Button)findViewById(R.id.button2); //連至琨城的按鈕
        Button over = (Button)findViewById(R.id.button3); //連至直播的按鈕
        Button sport = (Button)findViewById(R.id.button4); //連至運動的按鈕
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        rundata =(TextView)findViewById(R.id.textView6);
        walkdata =(TextView)findViewById(R.id.textView7);
        airdata =(TextView)findViewById(R.id.textView8);
        pushdata =(TextView)findViewById(R.id.textView10);
        sitdata =(TextView)findViewById(R.id.textView9);
        getData();
        //writAllDiaryDATA();
        readMyDiaryDATA();//讀取使用者內存資料
        kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,kelvin_tab_layout.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        hal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,habaActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dietcontrol del = new Dietcontrol();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_main,
                        del,
                        del.getTag()
                ).commit();

            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Over overs=Over.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_main,
                        overs,
                        overs.getTag()
                ).commit();
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sport sport=Sport.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_main,
                        sport,
                        sport.getTag()
                ).commit();
            }
        });
    }

    public void myDayChanged(CalendarDay mydate) {//選擇日期
        seleDAY=mydate;//紀錄上一次選擇日期
    }
    public void toAddDiary(String mydiary){//跳至撰寫日記
        addDiary adddiary=addDiary.newInstance(mydiary,null);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().addToBackStack(null).replace(
                R.id.content_main,
                adddiary,
                adddiary.getTag()
        ).commit();
    }
    public void addNewDiary(String mydiary){//寫入日記至陣列
        addOneDiary(seleDAY,mydiary);
        writAllDiaryDATA();//新增時將日記存取至內存檔案中
        ShowMyDiary();
    }
    public void ShowMyDiary(){//展示日記
        ShowDiary showdiary=ShowDiary.newInstance(seleDAY.toString(),diarys.get(DL.indexOf(seleDAY)));
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().addToBackStack(null).replace(
                R.id.content_main,
                showdiary,
                showdiary.getTag()
        ).commit();
    }
    public void deleOneDiary() {
        int deletTAG=DL.indexOf(seleDAY);
        Toast.makeText(this, ""+deletTAG, Toast.LENGTH_SHORT).show();
        diarys.remove(deletTAG);//先刪除日記內容
        DL.remove(deletTAG);//再刪除作為索引的日期
        writAllDiaryDATA();//刪除後將內存檔案重寫
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){//當按下左上三條線或顯示工具列
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Login) {
            Login login=Login.newInstance("param1","param2");
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().addToBackStack(null).replace(
                    R.id.content_main,
                    login,
                    login.getTag()
            ).commit();
        }
        else if (id == R.id.navItemAbout)
        {
            Userdata userdata=Userdata.newInstance("param1","param2");
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().addToBackStack(null).replace(
                    R.id.content_main,
                    userdata,
                    userdata.getTag()
            ).commit();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
