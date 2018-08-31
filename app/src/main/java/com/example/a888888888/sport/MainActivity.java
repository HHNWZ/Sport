package com.example.a888888888.sport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.hanks.htextview.rainbow.RainbowTextView;
import com.hedan.piechart_library.PieChartBean;
import com.hedan.piechart_library.PieChart_View;

import com.nelson.circlelayout.CircleLayout;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import at.markushi.ui.CircleButton;
import kelvin.tablayout.ChatActivity;
import kelvin.tablayout.CrunchesMonitor;
import kelvin.tablayout.Crunches_task;
import kelvin.tablayout.Exercise_main;
import kelvin.tablayout.LoginActivity;
import kelvin.tablayout.MainActivityFireBase;
import kelvin.tablayout.ProfileActivity;
import kelvin.tablayout.RegisterActivity;
import kelvin.tablayout.RunningMonitor;
import kelvin.tablayout.Running_task;
import kelvin.tablayout.SettingsActivity;
import kelvin.tablayout.Sit_up_task;
import kelvin.tablayout.SquatsMonitor;
import kelvin.tablayout.Squats_task;
import kelvin.tablayout.Time;
import kelvin.tablayout.TimerTaskTest;
import kelvin.tablayout.Walking_monitor;
import kelvin.tablayout.Walking_task;
import kelvin.tablayout.YogaMonitor;
import kelvin.tablayout.Yoga_task;
import necowneco.tablayout.habaActivity;
import qwer.BlankFragment;
import qwer.BlankFragment2;
import qwer.BlankFragment3;
import qwer.BlankFragmentDay;
import qwer.BlankFragmentc1;
import qwer.BlankFragmentc2;
import qwer.BlankFragmentc3;
import qwer.BlankFragmentc4;
import qwer.Dietcontrol;
import qwer.ShowDiary;
import qwer.addDiary;
import qwer.foodAndKLL;
import qwer.theDate;



public class  MainActivity extends AppCompatActivity
        implements Over.OnFragmentInteractionListener,Sport.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener, BlankFragment3.OnFragmentInteractionListener
        ,Run.OnFragmentInteractionListener,Walk.OnFragmentInteractionListener,Air.OnFragmentInteractionListener,Sit.OnFragmentInteractionListener,Push.OnFragmentInteractionListener,Login.OnFragmentInteractionListener,
        ShowDiary.OnFragmentInteractionListener,addDiary.OnFragmentInteractionListener,BlankFragmentc1.OnFragmentInteractionListener , BlankFragmentc2.OnFragmentInteractionListener , BlankFragmentc3.OnFragmentInteractionListener , BlankFragmentc4.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener
        ,Userdata.OnFragmentInteractionListener, foodAndKLL.OnFragmentInteractionListener,Ifnotuserdata.OnFragmentInteractionListener,Register.OnFragmentInteractionListener,SwipeRefreshLayout.OnRefreshListener {
    public final ArrayList<String> food_list=new ArrayList<String>();//常見食物清單
    public final ArrayList<Integer> food_KLL=new ArrayList<Integer>();//食物對應卡路里
    public final ArrayList<CalendarDay> DL=new ArrayList<>();//日記.日期
    //public final ArrayList<String> diarys=new ArrayList<>();//日記.內容
    //public final ArrayList<ArrayList<Integer>> BK_list=new ArrayList<ArrayList<Integer>>();//日記.早餐
    //public final ArrayList<ArrayList<Integer>> LH_list=new ArrayList<ArrayList<Integer>>();//日記.午餐
    //public final ArrayList<ArrayList<Integer>> DN_list=new ArrayList<ArrayList<Integer>>();//日記.晚餐
    public ArrayList<theDate> diarys=new ArrayList<theDate>();//日清單(試作)
    public int dateID;//
    public final CalendarDay Today = CalendarDay.today();//取得今天日期
    public CalendarDay seleDAY=Today;//選擇預設為今天
    private String showUri = "http://172.30.4.170:1335/getusersport.php";//連至資料庫
    public static TextView rundata;
    public static TextView walkdata;
    public static TextView airdata;
    public static TextView pushdata;
    public static TextView sitdata;
    private TextView username;
    private TextView username1;
    private ImageView userImage;
    private MyDBHelper dbHelper; //內建資料庫
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolboar;
    private PieChart_View pieView;
    private Float frun=null,fwalk=null,fair=null,fpush=null,fsit=null;
    private static DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private String mCurrent_state,onesignal_email,device_token;
    String channelId = "love";
    String channelName = "我的最愛";
    private static Context context;
    public Date dt2=null;
    public Button kel,hal,del,over,sport;
    private static String crunches_week_record;
    public static int count=0;
    public static int count2=0;
    public static TextView textView6;
    public static TextView textView7;
    public static TextView textView8;
    public static TextView textView10;
    public static TextView textView9;
    public static CircleLayout circleLayout;
    public static NavigationView navigationView;
    public static Menu menu;
    public static MenuItem menu_email_login,menu_email_register,menu_chat_room,menu_setting_account,menu_Logout;
    public static View hView;

    public static Button button_of_walking_monitoring,button_of_running_monitoring,button_of_yoga_monitoring,button_of_squats_monitoring,button_of_crunches_monitoring;

    public static RainbowTextView main_title;
    public static ActionBar actionBar;
    public static DatabaseReference Task_walking;
    public static DatabaseReference Task_req_walking;
    public static DatabaseReference Task_running;
    public static DatabaseReference Task_req_running;
    public static DatabaseReference Task_yoga;
    public static DatabaseReference Task_req_yoga;
    public static DatabaseReference Task_squats;
    public static DatabaseReference Task_req_squats;
    public static DatabaseReference Task_crunches;
    public static DatabaseReference Task_req_crunches;





    //private DatabaseReference mUsersDatabase;


    SwipeRefreshLayout mSwipeLayout;

    com.android.volley.RequestQueue requestQueue;

    public MainActivity() {
    }

    private void getData() {

        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST,showUri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response.toString());
                            JSONArray data = jsonObject.getJSONArray("data");
                            JSONObject jasondata;
                            //這邊要和上面json的名稱一樣
                            //下邊是把全部資料都印出來
                            for (int i = 0; i < data.length(); i++) {
                                jasondata = data.getJSONObject(i);
                                rundata.setText(jasondata.getString("run"));
                                walkdata.setText(jasondata.getString("walk"));
                                airdata.setText(jasondata.getString("air"));
                                pushdata.setText(jasondata.getString("push"));
                                sitdata.setText(jasondata.getString("sit"));//dd
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "登入失敗，請重新輸入", Toast.LENGTH_SHORT).show();//傳資料回來在這裡
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());
                    }
                }

                )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login",Login.user);
                return params;
            }

        };
        requestQueue.add(jsonObjectRequest);
    }




    public void addOneDiary(CalendarDay date,String diary){//寫入單筆日記資料
        DL.add(date);
        diarys.add(new theDate(diary));
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
        int num=spref.getInt("DiaryNum",0);
        //Toast.makeText(this, DATAsize, Toast.LENGTH_SHORT).show();
        if(num==0) {//驗證檔案內存在日記資料
            //Toast.makeText(this, "目前尚無日記", Toast.LENGTH_SHORT).show();

        }else{
            DL.clear();
            diarys.clear();
            for(int i=0;i<num;i++){
                addOneDiary(getOneDate(spref.getInt("DL_Y_"+Integer.toString(i),0),
                                        spref.getInt("DL_M_"+Integer.toString(i),0),
                                        spref.getInt("DL_D_"+Integer.toString(i),0)),//新增日記.新增日期
                        spref.getString("diarys_"+Integer.toString(i),null)//新增日記.新增日記
                );
                for(int j=0;j<3;j++){
                    for(int k=0;k<food_list.size();k++){
                        diarys.get(i).setEated(j,k,spref.getInt(
                                "eated_"+i+"_"+j+"_"+k,0));
                    }
                }

            }
            //Toast.makeText(this, "資料量："+DATAsize, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRefresh() {

        // 模仿更新 ( 2秒
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {

                // 結束更新動畫
                mSwipeLayout.setRefreshing(false);
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
                Toast.makeText(MainActivity.this, "Refresh Success", Toast.LENGTH_SHORT).show();

            }}, 2000);
    }
    public void writAllDiaryDATA(){//將當前日記陣列存入檔案
        //Toast.makeText(this, "日期數量："+DL.size()+"，日記數量："+diarys.size(), Toast.LENGTH_SHORT).show();
        SharedPreferences spref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = spref.edit();
        editor.clear().commit();
        if(DL.size()>0){//存入時陣列中有資料
            editor.putInt("DiaryNum",DL.size());//標示有資料
            for(int i=0;i<DL.size();i++){//逐一寫入日寄資料
                editor.putInt("DL_Y_"+Integer.toString(i),DL.get(i).getYear())
                        .putInt("DL_M_"+Integer.toString(i),DL.get(i).getMonth())
                        .putInt("DL_D_"+Integer.toString(i),DL.get(i).getDay())
                        .putString("diarys_"+Integer.toString(i),diarys.get(i).getDiary());
                for(int j=0;j<3;j++){
                    for(int k=0;k<food_list.size();k++){
                        editor.putInt("eated_"+i+"_"+j+"_"+k,
                                diarys.get(i).getEated(j,k));
                    }
                }

            }
        }else{
            editor.putInt("DiaryNum",0);
        }
        editor.commit();

        //editor.clear().commit();
    }



    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .init();
        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();

        Calendar cal=Calendar.getInstance();
        int y=cal.get(Calendar.YEAR);
        int m=cal.get(Calendar.MONTH)+1;
        int d=cal.get(Calendar.DAY_OF_MONTH);
        String clear=null;
        String date=null;
        Date firstTime=null;
        clear=y+"/"+m+"/"+d+" 23:59:00";
        //Toast.makeText(MainActivity.this,clear,Toast.LENGTH_LONG).show();
        SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        try {
            firstTime = dateFormatter.parse(clear);
            //Toast.makeText(MainActivity.this,""+dateFormatter.format(firstTime),Toast.LENGTH_LONG).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }



        setContentView(R.layout.activity_main);
        mToolboar=(Toolbar)findViewById(R.id.nav_action); //替換toolbar會爆
        setSupportActionBar(mToolboar);//Toolbar取代原本的ActionBar
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);//必須用字串資源檔
        mDrawerLayout.addDrawerListener(mToggle);//監聽選單按鈕是否被觸擊
        mToggle.syncState();//隱藏顯示箭頭返回
        //讓 ActionBar 中的返回箭號置換成 Drawer 的三條線圖示。並且把這個觸發器指定給 layDrawer 。
        actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //mToolboar.setNavigationIcon(R.drawable.menubuttonred);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        hView =navigationView.getHeaderView(0);
        username=(TextView)hView.findViewById(R.id.text_user_name);
        userImage=(ImageView)hView.findViewById(R.id.user_image);
        circleLayout = (CircleLayout) findViewById(R.id.circle);
        //circleLayout.setChangeCorner();
        circleLayout.setCanScroll(true);
        //username.setText("123456");
        menu = navigationView.getMenu();
        menu_email_login = menu.findItem(R.id.email_login);
        menu_email_register = menu.findItem(R.id.email_register);
        menu_chat_room = menu.findItem(R.id.chat_room);
         menu_setting_account = menu.findItem(R.id.setting_account);
         menu_Logout = menu.findItem(R.id.Logout);
        final String user_id = getIntent().getStringExtra("user_id");

         kel = (Button)findViewById(R.id.button); //連至書輝的按鈕
        hal = (Button)findViewById(R.id.button1); //連至弘盛的按鈕
         del = (Button)findViewById(R.id.button2); //連至琨城的按鈕
         over = (Button)findViewById(R.id.button3); //連至直播的按鈕
         //sport = (Button)findViewById(R.id.button4); //連至運動的按鈕

        button_of_walking_monitoring=(Button)findViewById(R.id.button_of_walking_monitoring);
        button_of_running_monitoring=(Button)findViewById(R.id.button_of_running_monitoring);
        button_of_yoga_monitoring=(Button)findViewById(R.id.button_of_yoga_monitoring);
        button_of_squats_monitoring=(Button)findViewById(R.id.button_of_squats_monitoring);
        button_of_crunches_monitoring=(Button)findViewById(R.id.button_of_crunches_monitoring);
        textView6=(TextView)findViewById(R.id.textView6);
        textView7=(TextView)findViewById(R.id.textView7);
        textView8=(TextView)findViewById(R.id.textView8);
        textView10=(TextView)findViewById(R.id.textView10);
        textView9=(TextView)findViewById(R.id.textView9);
        pieView = (PieChart_View) findViewById(R.id.pie_view);
        main_title=(RainbowTextView)findViewById(R.id.main_title);
        //mHanlder2.postDelayed(task3,5000);
        button_of_walking_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Walking_monitor.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        button_of_running_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RunningMonitor.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        button_of_yoga_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, YogaMonitor.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        button_of_squats_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SquatsMonitor.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        button_of_crunches_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CrunchesMonitor.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        mHanlder.postDelayed(task2,5000);



        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {


            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            Task_walking=FirebaseDatabase.getInstance().getReference().child("Task_walking").child(mAuth.getCurrentUser().getUid());
            Task_req_walking=FirebaseDatabase.getInstance().getReference().child("Task_req_walking").child(mAuth.getCurrentUser().getUid());
            Task_running=FirebaseDatabase.getInstance().getReference().child("Task_running").child(mAuth.getCurrentUser().getUid());
            Task_req_running=FirebaseDatabase.getInstance().getReference().child("Task_req_running").child(mAuth.getCurrentUser().getUid());
            Task_yoga=FirebaseDatabase.getInstance().getReference().child("Task_yoga").child(mAuth.getCurrentUser().getUid());
            Task_req_yoga=FirebaseDatabase.getInstance().getReference().child("Task_req_yoga").child(mAuth.getCurrentUser().getUid());
            Task_squats=FirebaseDatabase.getInstance().getReference().child("Task_squats").child(mAuth.getCurrentUser().getUid());
            Task_req_squats=FirebaseDatabase.getInstance().getReference().child("Task_req_squats").child(mAuth.getCurrentUser().getUid());
            Task_crunches=FirebaseDatabase.getInstance().getReference().child("Task_crunches").child(mAuth.getCurrentUser().getUid());
            Task_req_crunches=FirebaseDatabase.getInstance().getReference().child("Task_req_crunches").child(mAuth.getCurrentUser().getUid());

            OneSignal.sendTag("Uid",mAuth.getCurrentUser().getUid());
            Timer timer = new Timer();
            String to_date= kelvin.tablayout.Week.getWeek(System.currentTimeMillis());

            if(to_date.equals("一")){
                Task_running.removeValue();
                Task_req_running.removeValue();
                mUserRef.child("running_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("running").child("task_record").setValue(0);
                Task_yoga.removeValue();
                Task_req_yoga.removeValue();
                mUserRef.child("yoga_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("yoga").child("task_record").setValue(0);
                Task_squats.removeValue();
                Task_req_squats.removeValue();
                mUserRef.child("squats_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("squats").child("task_record").setValue(0);
                Task_crunches.removeValue();
                Task_req_crunches.removeValue();
                mUserRef.child("crunches_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("crunches").child("task_record").setValue(0);
            }

            if(to_date.equals("二")){
                Task_walking.removeValue();
                Task_req_walking.removeValue();
                mUserRef.child("walking_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("walking").child("task_record").setValue(0);
                Task_yoga.removeValue();
                Task_req_yoga.removeValue();
                mUserRef.child("yoga_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("yoga").child("task_record").setValue(0);
                Task_squats.removeValue();
                Task_req_squats.removeValue();
                mUserRef.child("squats_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("squats").child("task_record").setValue(0);
                Task_crunches.removeValue();
                Task_req_crunches.removeValue();
                mUserRef.child("crunches_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("crunches").child("task_record").setValue(0);
            }
            if(to_date.equals("三")){
                Task_running.removeValue();
                Task_req_running.removeValue();
                mUserRef.child("running_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("running").child("task_record").setValue(0);
                Task_walking.removeValue();
                Task_req_walking.removeValue();
                mUserRef.child("walking_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("walking").child("task_record").setValue(0);
                Task_squats.removeValue();
                Task_req_squats.removeValue();
                mUserRef.child("squats_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("squats").child("task_record").setValue(0);
                Task_crunches.removeValue();
                Task_req_crunches.removeValue();
                mUserRef.child("crunches_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("crunches").child("task_record").setValue(0);
            }
            if(to_date.equals("四")){
                Task_running.removeValue();
                Task_req_running.removeValue();
                mUserRef.child("running_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("running").child("task_record").setValue(0);
                Task_walking.removeValue();
                Task_req_walking.removeValue();
                mUserRef.child("walking_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("walking").child("task_record").setValue(0);
                Task_yoga.removeValue();
                Task_req_yoga.removeValue();
                mUserRef.child("yoga_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("yoga").child("task_record").setValue(0);
                Task_crunches.removeValue();
                Task_req_crunches.removeValue();
                mUserRef.child("crunches_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("crunches").child("task_record").setValue(0);

            }
            if(to_date.equals("五")){
                Task_running.removeValue();
                Task_req_running.removeValue();
                mUserRef.child("running_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("running").child("task_record").setValue(0);
                Task_walking.removeValue();
                Task_req_walking.removeValue();
                mUserRef.child("walking_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("walking").child("task_record").setValue(0);
                Task_yoga.removeValue();
                Task_req_yoga.removeValue();
                mUserRef.child("yoga_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("yoga").child("task_record").setValue(0);
                Task_squats.removeValue();
                Task_req_squats.removeValue();
                mUserRef.child("squats_task_status").setValue("還沒完成");
                mUserRef.child("exercise_count").child("squats").child("task_record").setValue(0);

            }

            Log.i("數據crunches_week_record1",""+crunches_week_record);
            timer.schedule(new TimerTaskTest(), firstTime);
            main_title.setText("運動監控");
            menu_email_login.setVisible(false);
            menu_email_register.setVisible(false);
            menu_chat_room.setVisible(true);
            menu_setting_account.setVisible(true);
            menu_Logout.setVisible(true);
            kel.setVisibility(View.VISIBLE);
            button_of_walking_monitoring.setVisibility(View.VISIBLE);
            button_of_running_monitoring.setVisibility(View.VISIBLE);
            button_of_yoga_monitoring.setVisibility(View.VISIBLE);
            button_of_squats_monitoring.setVisibility(View.VISIBLE);
            button_of_crunches_monitoring.setVisibility(View.VISIBLE);
            pieView.setVisibility(View.VISIBLE);
            textView6.setVisibility(View.VISIBLE);
            textView7.setVisibility(View.VISIBLE);
            textView8.setVisibility(View.VISIBLE);
            textView10.setVisibility(View.VISIBLE);
            textView9.setVisibility(View.VISIBLE);


            mUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String user_name = dataSnapshot.child("name").getValue().toString();
                    String user_image = dataSnapshot.child("thumb_image").getValue().toString();

                    username.setText(user_name);
                    crunches_week_record=dataSnapshot.child("exercise_count").child("crunches").child("week_record").getValue().toString();
                    String running_week_record=dataSnapshot.child("exercise_count").child("running").child("week_record").getValue().toString();
                    String squats_week_record=dataSnapshot.child("exercise_count").child("squats").child("week_record").getValue().toString();
                    String walking_week_record=dataSnapshot.child("exercise_count").child("walking").child("week_record").getValue().toString();
                    String yoga_week_record=dataSnapshot.child("exercise_count").child("yoga").child("week_record").getValue().toString();
                    String crunches_week_calorie=dataSnapshot.child("exercise_count").child("crunches").child("week_calorie").getValue().toString();
                    String running_week_calorie=dataSnapshot.child("exercise_count").child("running").child("week_calorie").getValue().toString();
                    String squats_week_calorie=dataSnapshot.child("exercise_count").child("squats").child("week_calorie").getValue().toString();
                    String walking_week_calorie=dataSnapshot.child("exercise_count").child("walking").child("week_calorie").getValue().toString();
                    String yoga_week_calorie=dataSnapshot.child("exercise_count").child("yoga").child("week_calorie").getValue().toString();
                    String DateCheck=dataSnapshot.child("DateCheck").getValue().toString();
                    String Week= kelvin.tablayout.Week.getWeek(System.currentTimeMillis());
                    String nowDate=Time.getToDate(System.currentTimeMillis());
                    Log.i("現在是1",DateCheck);
                    Log.i("現在是",nowDate);
                    float crunches_week_record_float=Float.parseFloat(crunches_week_record);
                    float running_week_record_float=Float.parseFloat(running_week_record);
                    float squats_week_record_float=Float.parseFloat(squats_week_record);
                    float walking_week_record_float=Float.parseFloat(walking_week_record);
                    long yoga_week_record_long=Long.parseLong(yoga_week_record);
                    float crunches_week_calorie_float=Float.parseFloat(crunches_week_calorie);
                    float running_week_calorie_float=Float.parseFloat(running_week_calorie);
                    float squats_week_calorie_float=Float.parseFloat(squats_week_calorie);
                    float walking_week_calorie_float=Float.parseFloat(walking_week_calorie);
                    float yoga_week_calorie_float=Float.parseFloat(yoga_week_calorie);

                    ArrayList<PieChartBean> lists = new ArrayList<>();
                    DecimalFormat df = new DecimalFormat("0.00");


                    if(Week.equals("一")){
                        if(DateCheck.equals(nowDate)){
                            lists.add(new PieChartBean(android.graphics.Color.parseColor("#38b048"), running_week_calorie_float, ""));//rundata
                            lists.add(new PieChartBean(android.graphics.Color.parseColor("#189428"), walking_week_calorie_float, ""));//walkdata
                            lists.add(new PieChartBean(android.graphics.Color.parseColor("#349bb3"), yoga_week_calorie_float, ""));//airdata
                            lists.add(new PieChartBean(android.graphics.Color.parseColor("#2671ab"), squats_week_calorie_float, ""));//pushdata
                            lists.add(new PieChartBean(android.graphics.Color.parseColor("#2c618a"), crunches_week_calorie_float, ""));//sitdata
                            pieView.setData(lists);
                            textView6.setText(""+running_week_record_float+"公里");
                            textView7.setText(""+walking_week_record_float+"公里");
                            textView8.setText(""+Time.changeYogaTime(yoga_week_record_long));
                            textView10.setText(""+squats_week_record+"次");
                            textView9.setText(""+crunches_week_record+"次");
                        }else{
                            lists.add(new PieChartBean(Color.parseColor("#38b048"), 0, ""));//rundata
                            lists.add(new PieChartBean(Color.parseColor("#189428"), 0, ""));//walkdata
                            lists.add(new PieChartBean(Color.parseColor("#349bb3"), 0, ""));//airdata
                            lists.add(new PieChartBean(Color.parseColor("#2671ab"), 0, ""));//pushdata
                            lists.add(new PieChartBean(Color.parseColor("#2c618a"), 0, ""));//sitdata
                            pieView.setData(lists);
                            textView6.setText("0公里");
                            textView7.setText("0公里");
                            textView8.setText("0秒");
                            textView10.setText("0次");
                            textView9.setText("0次");
                            mUserRef.child("DateCheck").setValue(nowDate);
                            mUserRef.child("exercise_count").child("crunches").child("week_record").setValue(0);
                            mUserRef.child("exercise_count").child("running").child("week_record").setValue(0);
                            mUserRef.child("exercise_count").child("squats").child("week_record").setValue(0);
                            mUserRef.child("exercise_count").child("walking").child("week_record").setValue(0);
                            mUserRef.child("exercise_count").child("yoga").child("week_record").setValue(0);
                            mUserRef.child("exercise_count").child("crunches").child("week_calorie").setValue(0);
                            mUserRef.child("exercise_count").child("running").child("week_calorie").setValue(0);
                            mUserRef.child("exercise_count").child("squats").child("week_calorie").setValue(0);
                            mUserRef.child("exercise_count").child("walking").child("week_calorie").setValue(0);
                            mUserRef.child("exercise_count").child("yoga").child("week_calorie").setValue(0);

                        }

                    }else {
                        lists.add(new PieChartBean(android.graphics.Color.parseColor("#38b048"), running_week_calorie_float, ""));//rundata
                        lists.add(new PieChartBean(android.graphics.Color.parseColor("#189428"), walking_week_calorie_float, ""));//walkdata
                        lists.add(new PieChartBean(android.graphics.Color.parseColor("#349bb3"), yoga_week_calorie_float, ""));//airdata
                        lists.add(new PieChartBean(android.graphics.Color.parseColor("#2671ab"), squats_week_calorie_float, ""));//pushdata
                        lists.add(new PieChartBean(android.graphics.Color.parseColor("#2c618a"), crunches_week_calorie_float, ""));//sitdata
                        pieView.setData(lists);
                        textView6.setText(""+running_week_record_float+"公里");
                        textView7.setText(""+walking_week_record_float+"公里");
                        textView8.setText(""+Time.changeYogaTime(yoga_week_record_long));
                        textView10.setText(""+squats_week_record+"次");
                        textView9.setText(""+crunches_week_record+"次");
                    }

                    Picasso.with(MainActivity.this).load(user_image).placeholder(R.drawable.default_avatar).into(userImage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Log.i("數據crunches_week_record2",""+crunches_week_record);



        }else {
            menu_email_login.setVisible(true);
            menu_email_register.setVisible(true);
            menu_chat_room.setVisible(false);
            menu_setting_account.setVisible(false);
            menu_Logout.setVisible(false);
            kel.setVisibility(View.INVISIBLE);
            button_of_walking_monitoring.setVisibility(View.INVISIBLE);
            button_of_running_monitoring.setVisibility(View.INVISIBLE);
            button_of_yoga_monitoring.setVisibility(View.INVISIBLE);
            button_of_squats_monitoring.setVisibility(View.INVISIBLE);
            button_of_crunches_monitoring.setVisibility(View.INVISIBLE);
            pieView.setVisibility(View.INVISIBLE);
            textView6.setVisibility(View.INVISIBLE);
            textView7.setVisibility(View.INVISIBLE);
            textView8.setVisibility(View.INVISIBLE);
            textView10.setVisibility(View.INVISIBLE);
            textView9.setVisibility(View.INVISIBLE);
            main_title.setText("点击左上角");
            //mHanlder2.postDelayed(task3,5000);




        }


        navigationView.setNavigationItemSelectedListener(this);//清單觸發監聽事件
            //下拉更新
            mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
            mSwipeLayout.setOnRefreshListener(this);
            mSwipeLayout.setColorSchemeColors(Color.RED);



        Log.i("數據crunches_week_record3",""+crunches_week_record);


        //食物列表
        food_list.add("米飯");
        food_list.add("香蕉");
        food_list.add("牛奶");
        food_list.add("牛肉");
        food_list.add("甜甜圈");
        food_list.add("魚肉");
        food_list.add("蔬菜");
        food_list.add("雞肉");
        food_list.add("雞蛋");
        //食物熱量列表
        food_KLL.add(225);
        food_KLL.add(120);
        food_KLL.add(150);
        food_KLL.add(140);
        food_KLL.add(270);
        food_KLL.add(206);
        food_KLL.add(65);
        food_KLL.add(239);
        food_KLL.add(106);

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
                if(true) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Exercise_main.class);

                    startActivity(intent);
                    MainActivity.this.finish();
                }
                else
                {
                    Ifnotuserdata ifnotuserdata = new Ifnotuserdata();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().addToBackStack(null).replace(
                            R.id.content_main,
                            ifnotuserdata,
                            ifnotuserdata.getTag()
                    ).commit();
                }
            }
        });
        hal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true) {//IF條件：Login.user != null
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, habaActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                else
                {
                    Ifnotuserdata ifnotuserdata = new Ifnotuserdata();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().addToBackStack(null).replace(
                            R.id.content_main,
                            ifnotuserdata,
                            ifnotuserdata.getTag()
                    ).commit();
                }
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
                if(true) {
                    Over overs = Over.newInstance("param1", "param2");
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().addToBackStack(null).replace(
                            R.id.content_main,
                            overs,
                            overs.getTag()
                    ).commit();
                }
                else
                {
                    Ifnotuserdata ifnotuserdata = new Ifnotuserdata();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().addToBackStack(null).replace(
                            R.id.content_main,
                            ifnotuserdata,
                            ifnotuserdata.getTag()
                    ).commit();
                }
            }
        });

    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mAuth.getCurrentUser() == null){
                        mToolboar.setNavigationIcon(R.drawable.menubuttonred);
                        mToolboar.setNavigationIcon(R.drawable.menubuttonyellow);
                    }else{
                        textView6.setTextColor(Color.BLACK);
                        textView7.setTextColor(Color.WHITE);
                        textView8.setTextColor(Color.WHITE);
                        textView10.setTextColor(Color.WHITE);
                        textView9.setTextColor(Color.WHITE);
                        button_of_walking_monitoring.setBackgroundResource(R.drawable.walkingred);
                        button_of_running_monitoring.setBackgroundResource(R.drawable.runningbackground);
                        button_of_yoga_monitoring.setBackgroundResource(R.drawable.yogabackground);
                        button_of_squats_monitoring.setBackgroundResource(R.drawable.suagatsbackground);
                        button_of_crunches_monitoring.setBackgroundResource(R.drawable.crunchesbackground);

                    }
                    break;
                case 2:
                    if(mAuth.getCurrentUser()==null){
                        mToolboar.setNavigationIcon(R.drawable.menubuttonyellow);
                        mToolboar.setNavigationIcon(R.drawable.menubuttonred);
                    }else {
                        textView6.setTextColor(Color.WHITE);
                        textView7.setTextColor(Color.BLACK);
                        textView8.setTextColor(Color.WHITE);
                        textView10.setTextColor(Color.WHITE);
                        textView9.setTextColor(Color.WHITE);
                        button_of_walking_monitoring.setBackgroundResource(R.drawable.walkingbackground);
                        button_of_running_monitoring.setBackgroundResource(R.drawable.runningred);
                        button_of_yoga_monitoring.setBackgroundResource(R.drawable.yogabackground);
                        button_of_squats_monitoring.setBackgroundResource(R.drawable.suagatsbackground);
                        button_of_crunches_monitoring.setBackgroundResource(R.drawable.crunchesbackground);
                    }
                    break;
                case 3:
                    if(mAuth.getCurrentUser()==null){
                        mToolboar.setNavigationIcon(R.drawable.menubuttonred);
                        mToolboar.setNavigationIcon(R.drawable.menubuttonyellow);
                    }{
                        textView6.setTextColor(Color.WHITE);
                        textView7.setTextColor(Color.WHITE);
                        textView8.setTextColor(Color.BLACK);
                        textView10.setTextColor(Color.WHITE);
                        textView9.setTextColor(Color.WHITE);
                        button_of_walking_monitoring.setBackgroundResource(R.drawable.walkingbackground);
                        button_of_running_monitoring.setBackgroundResource(R.drawable.runningbackground);
                        button_of_yoga_monitoring.setBackgroundResource(R.drawable.yogared);
                        button_of_squats_monitoring.setBackgroundResource(R.drawable.suagatsbackground);
                        button_of_crunches_monitoring.setBackgroundResource(R.drawable.crunchesbackground);
                    }


                    break;
                case 4:
                    if(mAuth.getCurrentUser()==null){
                        mToolboar.setNavigationIcon(R.drawable.menubuttonyellow);
                        mToolboar.setNavigationIcon(R.drawable.menubuttonred);
                    }{
                        textView6.setTextColor(Color.WHITE);
                        textView7.setTextColor(Color.WHITE);
                        textView8.setTextColor(Color.WHITE);
                        textView10.setTextColor(Color.BLACK);
                        textView9.setTextColor(Color.WHITE);
                        button_of_walking_monitoring.setBackgroundResource(R.drawable.walkingbackground);
                        button_of_running_monitoring.setBackgroundResource(R.drawable.runningbackground);
                        button_of_yoga_monitoring.setBackgroundResource(R.drawable.yogabackground);
                        button_of_squats_monitoring.setBackgroundResource(R.drawable.squatsred);
                        button_of_crunches_monitoring.setBackgroundResource(R.drawable.crunchesbackground);
                    }
                    break;
                case 5:
                    if(mAuth.getCurrentUser()==null){
                        mToolboar.setNavigationIcon(R.drawable.menubuttonred);
                        mToolboar.setNavigationIcon(R.drawable.menubuttonyellow);
                    }{
                        textView6.setTextColor(Color.WHITE);
                        textView7.setTextColor(Color.WHITE);
                        textView8.setTextColor(Color.WHITE);
                        textView10.setTextColor(Color.WHITE);
                        textView9.setTextColor(Color.BLACK);
                        button_of_walking_monitoring.setBackgroundResource(R.drawable.walkingbackground);
                        button_of_running_monitoring.setBackgroundResource(R.drawable.runningbackground);
                        button_of_yoga_monitoring.setBackgroundResource(R.drawable.yogabackground);
                        button_of_squats_monitoring.setBackgroundResource(R.drawable.suagatsbackground);
                        button_of_crunches_monitoring.setBackgroundResource(R.drawable.crunchesred);
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Runnable task2 = new Runnable() {
        @Override
        public void run() {
            if(count>5){
                count=0;
            }
            count=count+1;
            mHanlder.sendEmptyMessage(count);

            mHanlder.postDelayed(this, 1 * 500);//延迟5秒,再次执行task本身,实现了循环的效果

        }
    };



    public theDate getTodayEaetdInfo() {
        if(DL.contains(Today)){
        return diarys.get(DL.indexOf(Today));
        }else{
            return null;
        }
    }
    public void toSportPlan() {
        BlankFragmentc4 sportplan=BlankFragmentc4.newInstance(null,null);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().addToBackStack(null).replace(
                R.id.content_main,
                sportplan,
                sportplan.getTag()
        ).commit();
    }
    public void myDayChanged(CalendarDay mydate) {//選擇日期
        seleDAY=mydate;//紀錄上一次選擇日期
        dateID=DL.indexOf(seleDAY);
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
        diarys.get(DL.indexOf(seleDAY)).setDiary(mydiary);

        //writAllDiaryDATA();//新增時將日記存取至內存檔案中
        ShowMyDiary();
    }
    public void ShowMyDiary(){//展示日記
        String theDiary=null;
        int theKLL=0;
        if(!DL.contains(seleDAY)){
            addOneDiary(seleDAY,null);
        }
        theDiary=diarys.get(DL.indexOf(seleDAY)).Diary;
        theKLL=diarys.get(DL.indexOf(seleDAY)).todayKLL();

        ShowDiary showdiary=ShowDiary.newInstance(
                showTrueDate(seleDAY),
                theDiary,
                theKLL);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().addToBackStack(null).replace(
                R.id.content_main,
                showdiary,
                showdiary.getTag()
        ).commit();
    }
    public void deletOneDay(int delet_Type){//0.刪除整天；1.只刪除日記；2/3/4.只刪除早/中/晚餐
        int deletTAG=DL.indexOf(seleDAY);
        if(delet_Type==0){//整天
            diarys.remove(deletTAG);//先刪除日記內容
            DL.remove(deletTAG);//再刪除作為索引的日期
        }else {//0時完全刪除一筆資料
            switch (delet_Type) {//除此之外要先判定當天是否還有其他資料才能刪除日期
                case 1://日記
                    diarys.get(deletTAG).removeDiary();
                    break;
                case 2://早餐
                    diarys.get(deletTAG).removeKLL(0);
                    break;
                case 3://午餐
                    diarys.get(deletTAG).removeKLL(1);
                    break;
                case 4://晚餐
                    diarys.get(deletTAG).removeKLL(2);
                    break;
                case 5://額外檢查點
                    break;
            }
            if (diarys.get(deletTAG).todayKLL() == 0 && diarys.get(deletTAG).DiaryisNull()) {
                diarys.remove(deletTAG);//先刪除日記內容
                DL.remove(deletTAG);//再刪除作為索引的日期
            }
        }
        writAllDiaryDATA();//刪除後將內存檔案重寫
    }


    private String showTrueDate(CalendarDay cDay){
        return cDay.getYear()+"/"+(cDay.getMonth()+1)+"/"+cDay.getDay();
    }
    public void toFoodList(int foodType,int foodID){
        foodAndKLL FaK=foodAndKLL.newInstance(
                foodType,//1.餐時段
                foodID,//2.食物種類
                getDiarys_Food_Num(foodType),//3.食物數量
                dateID);//4.日期ID
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().addToBackStack(null).replace(
                R.id.content_main,
                FaK,
                FaK.getTag()
        ).commit();
    }
    public ArrayList<Integer> getDiarys_Food_Num(int foodType){
        ArrayList<Integer> food_num_list=new ArrayList<Integer>();
        for(int i=0;i<food_list.size();i++){
            food_num_list.add(diarys.get(dateID).getfoodnum(foodType,i));
        }
        return food_num_list;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){//當按下左上三條線或顯示工具列
            invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }
    public  void toBFD(String daynum,String daynum1){//按鈕傳值測試
        BlankFragmentDay blankfragmentday=BlankFragmentDay.newInstance(daynum,daynum1);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().addToBackStack(null).replace(
                R.id.content_main,
                blankfragmentday,
                blankfragmentday.getTag()
        ).commit();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.email_login) {
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
        }

        else if (id == R.id.email_register)
        {

            Intent i = new Intent(MainActivity.this,RegisterActivity.class);
            //finish();
            startActivity(i);
        }
        else if(id==R.id.chat_room){
            Intent i = new Intent(MainActivity.this,MainActivityFireBase.class);
            //finish();
            startActivity(i);
        }
        else if(id==R.id.setting_account){
            Intent i = new Intent(MainActivity.this,SettingsActivity.class);
            //finish();
            startActivity(i);
        }
        else if(id==R.id.Logout){

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
            OneSignal.deleteTag("Uid");
            //OneSignal.deleteTag("User_ID");

            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(MainActivity.this,MainActivity.class);
            //finish();
            startActivity(i);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public static class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {


        public String user_id_send,exercise_data_from;
        public JSONObject data,user_id,exercise_data;

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            data = result.notification.payload.additionalData;
            String activityToBeOpened;

            Log.i("Data1", String.valueOf(data));

            if (data != null) {
                activityToBeOpened = data.optString("activityToBeOpened", null);
                user_id = result.notification.payload.additionalData;
                exercise_data=result.notification.payload.additionalData;
               exercise_data_from=exercise_data.optString("exercise_data");
                user_id_send=user_id.optString("user_id");
                mUserRef.child("exercise_data").setValue(exercise_data_from);


                if(activityToBeOpened != null && activityToBeOpened.equals("ProfileActivity")){
                    Log.i("OneSignalExample", "customkey set with value: " + activityToBeOpened);
                    Log.i("我在這裡","Chat_send_id："+user_id_send);
                    Intent intent = new Intent(getContext(), ProfileActivity.class);
                    intent.putExtra("user_id",user_id_send);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("ChatActivity")){
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("user_id",user_id_send);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("Yoga_task")){
                    Intent intent = new Intent(getContext(), Yoga_task.class);
                    intent.putExtra("ToolbarTitle","瑜伽每週任務");
                    intent.putExtra("exerciseWeekTitle","做瑜伽");
                    intent.putExtra("exerciseWeekUnit","分鐘");
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("Squats_task")){
                    Intent intent = new Intent(getContext(), Squats_task.class);
                    intent.putExtra("ToolbarTitle","深蹲每週任務");
                    intent.putExtra("exerciseWeekTitle","做深蹲");
                    intent.putExtra("exerciseWeekUnit","次");
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("Running_task")){
                    Intent intent = new Intent(getContext(), Running_task.class);
                    intent.putExtra("ToolbarTitle","跑步每週任務");
                    intent.putExtra("exerciseWeekTitle","去跑步");
                    intent.putExtra("exerciseWeekUnit","公里");
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("Crunches_task")){
                    Intent intent = new Intent(getContext(), Crunches_task.class);
                    intent.putExtra("ToolbarTitle","仰臥起坐每週任務");
                    intent.putExtra("exerciseWeekTitle","做仰臥起坐");
                    intent.putExtra("exerciseWeekUnit","次");
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("Walking_task")){
                    Intent intent = new Intent(getContext(),Walking_task.class);
                    intent.putExtra("ToolbarTitle","步行每週任務");
                    intent.putExtra("exerciseWeekTitle","去步行");
                    intent.putExtra("exerciseWeekUnit","公里");
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken){
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
                Log.i("apple", "open");

            }

        }
    }



}
