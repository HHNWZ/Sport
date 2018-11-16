package com.example.a888888888.sport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

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

import kelvin.tablayout.ChatActivity;
import kelvin.tablayout.ControlDie;
import kelvin.tablayout.CrunchesDareFriendProfile;
import kelvin.tablayout.CrunchesMonitor;
import kelvin.tablayout.Exercise_main;
import kelvin.tablayout.GlobalVariable;
import kelvin.tablayout.LoginActivity;
import kelvin.tablayout.MainActivityFireBase;
import kelvin.tablayout.PhotoBlog;
import kelvin.tablayout.ProfileActivity;
import kelvin.tablayout.RegisterActivity;
import kelvin.tablayout.RunningDareFriendProfile;
import kelvin.tablayout.RunningMonitor;
import kelvin.tablayout.SettingsActivity;
import kelvin.tablayout.SquatsDareFriendProfile;
import kelvin.tablayout.SquatsMonitor;
import kelvin.tablayout.Squats_dare;
import kelvin.tablayout.TaskProfile;
import kelvin.tablayout.Time;
import kelvin.tablayout.TimerTaskTest;
import kelvin.tablayout.WalkingDareFriendProfile;
import kelvin.tablayout.Walking_monitor;
import kelvin.tablayout.YogaDareFriendProfile;
import kelvin.tablayout.YogaMonitor;




public class  MainActivity extends AppCompatActivity
        implements Over.OnFragmentInteractionListener,
         NavigationView.OnNavigationItemSelectedListener,OnChartValueSelectedListener {
    public final ArrayList<String> food_list=new ArrayList<String>();//常見食物清單
    public final ArrayList<Integer> food_KLL=new ArrayList<Integer>();//食物對應卡路里
    public final ArrayList<CalendarDay> DL=new ArrayList<>();//日記.日期
    //public final ArrayList<String> diarys=new ArrayList<>();//日記.內容
    //public final ArrayList<ArrayList<Integer>> BK_list=new ArrayList<ArrayList<Integer>>();//日記.早餐
    //public final ArrayList<ArrayList<Integer>> LH_list=new ArrayList<ArrayList<Integer>>();//日記.午餐
    //public final ArrayList<ArrayList<Integer>> DN_list=new ArrayList<ArrayList<Integer>>();//日記.晚餐

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

    public static TextView main_title;
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
    private PieChart mChart;
    private Legend l;
    private ArrayList<PieEntry> pieEntryList;
    private ArrayList<Integer> colors;
    private PieEntry running,walking,yoga,squats,crunches;
    private PieDataSet pieDataSet;
    private PieData pieData;
    private String exercise_main;






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


        };
        requestQueue.add(jsonObjectRequest);
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
        Log.i("我在MainActivity的","onCreate");
        GlobalVariable User = (GlobalVariable)getApplicationContext();
        User.setTask("MainActivity");
        Log.i("Exercise_mainOC的值",""+User.getTask_reg());
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
        //blog=menu.findItem(R.id.blog);
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
        //pieView = (PieChart_View) findViewById(R.id.pie_view);
        mChart=findViewById(R.id.chart1);
        main_title=(TextView)findViewById(R.id.main_title);
        //mHanlder2.postDelayed(task3,5000);
        button_of_walking_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Walking_monitor.class);
                startActivity(intent);

            }
        });
        button_of_running_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RunningMonitor.class);

                startActivity(intent);

            }
        });

        button_of_yoga_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, YogaMonitor.class);
                startActivity(intent);

            }
        });

        button_of_squats_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SquatsMonitor.class);
                startActivity(intent);

            }
        });

        button_of_crunches_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CrunchesMonitor.class);
                startActivity(intent);

            }
        });
        mHanlder.postDelayed(task2,5000);



        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            Log.i("我在這裡1234","2");

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
            hal.setVisibility(View.VISIBLE);
            over.setVisibility(View.VISIBLE);
            button_of_walking_monitoring.setVisibility(View.VISIBLE);
            button_of_running_monitoring.setVisibility(View.VISIBLE);
            button_of_yoga_monitoring.setVisibility(View.VISIBLE);
            button_of_squats_monitoring.setVisibility(View.VISIBLE);
            button_of_crunches_monitoring.setVisibility(View.VISIBLE);
            //pieView.setVisibility(View.VISIBLE);
            mChart.setVisibility(View.VISIBLE);
            textView6.setVisibility(View.VISIBLE);
            textView7.setVisibility(View.VISIBLE);
            textView8.setVisibility(View.VISIBLE);
            textView10.setVisibility(View.VISIBLE);
            textView9.setVisibility(View.VISIBLE);


            // 设置 pieChart 图表基本属性
            mChart.setUsePercentValues(true);            //使用百分比显示
            mChart.getDescription().setEnabled(false);    //设置pieChart图表的描述
            mChart.setBackgroundColor(android.graphics.Color.rgb(50,61,77));      //设置pieChart图表背景色
            mChart.setDragDecelerationFrictionCoef(0.95f);//设置pieChart图表转动阻力摩擦系数[0,1]
            mChart.setRotationAngle(0);                   //设置pieChart图表起始角度
            mChart.setRotationEnabled(true);              //设置pieChart图表是否可以手动旋转
            mChart.setHighlightPerTapEnabled(true);       //设置piecahrt图表点击Item高亮是否可用
            mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);// 设置pieChart图表展示动画效果

            // 设置 pieChart 图表Item文本属性
            mChart.setDrawEntryLabels(true);              //设置pieChart是否只显示饼图上百分比不显示文字（true：下面属性才有效果）
            mChart.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
            //mChart.setEntryLabelTypeface(mItalic);
            mChart.setEntryLabelTextSize(10f);            //设置pieChart图表文本字体大小

            mChart.setDrawHoleEnabled(true);              //是否显示PieChart内部圆环(true:下面属性才有意义)
            mChart.setHoleRadius(31f);                    //设置PieChart内部圆的半径(这里设置28.0f)
            mChart.setTransparentCircleRadius(31f);       //设置PieChart内部透明圆的半径(这里设置31.0f)
            mChart.setTransparentCircleColor(Color.BLACK);//设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
            mChart.setTransparentCircleAlpha(0);         //设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明
            mChart.setHoleColor(Color.rgb(50,61,77));             //设置PieChart内部圆的颜色
            mChart.setDrawCenterText(true);               //是否绘制PieChart内部中心文本（true：下面属性才有意义）

            mChart.setCenterText("每週\n總消耗\n卡路里");                 //设置PieChart内部圆文字的内容
            mChart.setCenterTextSize(10f);                //设置PieChart内部圆文字的大小
            mChart.setCenterTextColor(Color.WHITE);         //设置PieChart内部圆文字的颜色

            l = mChart.getLegend();
            l.setEnabled(false);                    //是否启用图列（true：下面属性才有意义）

            mChart.setOnChartValueSelectedListener(this);




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

                    //ArrayList<PieChartBean> lists = new ArrayList<>();
                    DecimalFormat df = new DecimalFormat("#");


                    if(Week.equals("一")){
                        if(DateCheck.equals(nowDate)){

                            pieEntryList = new ArrayList<PieEntry>();
                            colors = new ArrayList<Integer>();
                            colors.add(Color.parseColor("#38b048"));
                            colors.add(Color.parseColor("#189428"));
                            colors.add(Color.parseColor("#349bb3"));
                            colors.add(Color.parseColor("#2671ab"));
                            colors.add(Color.parseColor("#2c618a"));

                            //饼图实体 PieEntry
                            if(running_week_calorie_float!=0){
                                running = new PieEntry(running_week_calorie_float, "跑步:"+df.format(running_week_calorie_float)+"大卡");
                            }else {
                                running = new PieEntry(running_week_calorie_float, "");
                            }
                            if(walking_week_calorie_float!=0){
                                walking = new PieEntry(walking_week_calorie_float, "步行:"+df.format(walking_week_calorie_float)+"大卡");
                            }else {
                                walking = new PieEntry(walking_week_calorie_float, "");
                            }
                            if(yoga_week_calorie_float!=0){
                                yoga = new PieEntry(yoga_week_calorie_float, "瑜伽:"+df.format(yoga_week_calorie_float)+"大卡");
                            }else {
                                yoga = new PieEntry(yoga_week_calorie_float, "");
                            }
                            if(squats_week_calorie_float!=0){
                                squats = new PieEntry(squats_week_calorie_float, "深蹲:"+df.format(squats_week_calorie_float)+"大卡");
                            }else {
                                squats = new PieEntry(squats_week_calorie_float, "");
                            }
                            if(crunches_week_calorie_float!=0){
                                crunches = new PieEntry(crunches_week_calorie_float, "仰臥起坐:"+df.format(crunches_week_calorie_float)+"大卡");
                            }else {
                                crunches = new PieEntry(crunches_week_calorie_float, "");
                            }
                            pieEntryList.add(running);
                            pieEntryList.add(walking);
                            pieEntryList.add(yoga);
                            pieEntryList.add(squats);
                            pieEntryList.add(crunches);
                            //饼状图数据集 PieDataSet
                            pieDataSet = new PieDataSet(pieEntryList, "每週總消耗卡路里");
                            pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
                            pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
                            pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
                            //最终数据 PieData
                            pieData = new PieData(pieDataSet);
                            pieData.setDrawValues(false);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
                            pieData.setValueTextColor(Color.BLUE);  //设置所有DataSet内数据实体（百分比）的文本颜色
                            pieData.setValueTextSize(20f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
                            //pieData.setValueTypeface(mTfLight);     //设置所有DataSet内数据实体（百分比）的文本字体样式
                            pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
                            mChart.setData(pieData);
                            mChart.highlightValues(null);
                            mChart.invalidate();                    //将图表重绘以显示设置的属性和数据


                            textView6.setText(""+running_week_record_float+"公里");
                            textView7.setText(""+walking_week_record_float+"公里");
                            textView8.setText(""+Time.changeYogaTime(yoga_week_record_long));
                            textView10.setText(""+squats_week_record+"次");
                            textView9.setText(""+crunches_week_record+"次");
                        }else{
                            /*lists.add(new PieChartBean(Color.parseColor("#38b048"), 0, ""));//rundata
                            lists.add(new PieChartBean(Color.parseColor("#189428"), 0, ""));//walkdata
                            lists.add(new PieChartBean(Color.parseColor("#349bb3"), 0, ""));//airdata
                            lists.add(new PieChartBean(Color.parseColor("#2671ab"), 0, ""));//pushdata
                            lists.add(new PieChartBean(Color.parseColor("#2c618a"), 0, ""));//sitdata
                            pieView.setData(lists);*/

                            pieEntryList = new ArrayList<PieEntry>();
                            colors = new ArrayList<Integer>();
                            colors.add(Color.parseColor("#38b048"));
                            colors.add(Color.parseColor("#189428"));
                            colors.add(Color.parseColor("#349bb3"));
                            colors.add(Color.parseColor("#2671ab"));
                            colors.add(Color.parseColor("#2c618a"));

                            //饼图实体 PieEntry
                            running = new PieEntry(0, "跑步:0大卡");
                            walking = new PieEntry(0, "步行:0大卡");
                            yoga = new PieEntry(0, "瑜伽:0大卡");
                            squats = new PieEntry(0, "深蹲:0大卡");
                            crunches = new PieEntry(0, "仰臥起坐:0大卡");
                            pieEntryList.add(running);
                            pieEntryList.add(walking);
                            pieEntryList.add(yoga);
                            pieEntryList.add(squats);
                            pieEntryList.add(crunches);
                            //饼状图数据集 PieDataSet
                            pieDataSet = new PieDataSet(pieEntryList, "每週總消耗卡路里");
                            pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
                            pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
                            pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
                            //最终数据 PieData
                            pieData = new PieData(pieDataSet);
                            pieData.setDrawValues(false);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
                            pieData.setValueTextColor(Color.BLUE);  //设置所有DataSet内数据实体（百分比）的文本颜色
                            pieData.setValueTextSize(20f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
                            //pieData.setValueTypeface(mTfLight);     //设置所有DataSet内数据实体（百分比）的文本字体样式
                            pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
                            mChart.setData(pieData);
                            mChart.highlightValues(null);
                            mChart.invalidate();                    //将图表重绘以显示设置的属性和数据



                            //pieChart 选择监听


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
                        pieEntryList = new ArrayList<PieEntry>();
                        colors = new ArrayList<Integer>();
                        colors.add(Color.parseColor("#38b048"));
                        colors.add(Color.parseColor("#189428"));
                        colors.add(Color.parseColor("#349bb3"));
                        colors.add(Color.parseColor("#2671ab"));
                        colors.add(Color.parseColor("#2c618a"));

                        //饼图实体 PieEntry
                        if(running_week_calorie_float!=0){
                            running = new PieEntry(running_week_calorie_float, "跑步:"+df.format(running_week_calorie_float)+"大卡");
                        }else {
                            running = new PieEntry(running_week_calorie_float, "");
                        }
                        if(walking_week_calorie_float!=0){
                            walking = new PieEntry(walking_week_calorie_float, "步行:"+df.format(walking_week_calorie_float)+"大卡");
                        }else {
                            walking = new PieEntry(walking_week_calorie_float, "");
                        }
                        if(yoga_week_calorie_float!=0){
                            yoga = new PieEntry(yoga_week_calorie_float, "瑜伽:"+df.format(yoga_week_calorie_float)+"大卡");
                        }else {
                            yoga = new PieEntry(yoga_week_calorie_float, "");
                        }
                        if(squats_week_calorie_float!=0){
                            squats = new PieEntry(squats_week_calorie_float, "深蹲:"+df.format(squats_week_calorie_float)+"大卡");
                        }else {
                            squats = new PieEntry(squats_week_calorie_float, "");
                        }
                        if(crunches_week_calorie_float!=0){
                            crunches = new PieEntry(crunches_week_calorie_float, "仰臥起坐:"+df.format(crunches_week_calorie_float)+"大卡");
                        }else {
                            crunches = new PieEntry(crunches_week_calorie_float, "");
                        }




                        pieEntryList.add(running);
                        pieEntryList.add(walking);
                        pieEntryList.add(yoga);
                        pieEntryList.add(squats);
                        pieEntryList.add(crunches);
                        //饼状图数据集 PieDataSet
                        pieDataSet = new PieDataSet(pieEntryList, "每週總消耗卡路里");
                        pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
                        pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
                        pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
                        //最终数据 PieData
                        pieData = new PieData(pieDataSet);
                        pieData.setDrawValues(false);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
                        pieData.setValueTextColor(Color.BLUE);  //设置所有DataSet内数据实体（百分比）的文本颜色
                        pieData.setValueTextSize(20f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
                        //pieData.setValueTypeface(mTfLight);     //设置所有DataSet内数据实体（百分比）的文本字体样式
                        pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
                        mChart.setData(pieData);
                        mChart.highlightValues(null);
                        mChart.invalidate();                    //将图表重绘以显示设置的属性和数据
                        textView6.setText(""+running_week_record_float+"公里");
                        textView7.setText(""+walking_week_record_float+"公里");
                        textView8.setText(""+Time.changeYogaTime(yoga_week_record_long));
                        textView10.setText(""+squats_week_record+"次");
                        textView9.setText(""+crunches_week_record+"次");
                        del.setVisibility(View.VISIBLE);
                    }

                    Picasso.get().load(user_image).placeholder(R.drawable.default_avatar).into(userImage);
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
            hal.setVisibility(View.INVISIBLE);
            button_of_walking_monitoring.setVisibility(View.INVISIBLE);
            button_of_running_monitoring.setVisibility(View.INVISIBLE);
            button_of_yoga_monitoring.setVisibility(View.INVISIBLE);
            button_of_squats_monitoring.setVisibility(View.INVISIBLE);
            button_of_crunches_monitoring.setVisibility(View.INVISIBLE);
           // pieView.setVisibility(View.INVISIBLE);
            mChart.setVisibility(View.INVISIBLE);
            textView6.setVisibility(View.INVISIBLE);
            textView7.setVisibility(View.INVISIBLE);
            textView8.setVisibility(View.INVISIBLE);
            textView10.setVisibility(View.INVISIBLE);
            textView9.setVisibility(View.INVISIBLE);
            del.setVisibility(View.INVISIBLE);
            over.setVisibility(View.INVISIBLE);
            main_title.setText("点击左上角");
            //mHanlder2.postDelayed(task3,5000);




        }


        navigationView.setNavigationItemSelectedListener(this);//清單觸發監聽事件





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

        kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Exercise_main.class);
                startActivity(intent);
            }
        });
        hal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PhotoBlog.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,ControlDie.class);
                startActivity(i);
            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mAuth.getCurrentUser() == null){
                        mToolboar.setNavigationIcon(R.drawable.menubuttonwhite);
                        mToolboar.setNavigationIcon(R.drawable.menubutton_object_color);

                    }else{
                        circleLayout.setChangeCorner(1.0);
                        mChart.spin(500, mChart.getRotationAngle(), mChart.getRotationAngle() + 72, Easing.EasingOption.EaseInCubic);


                    }
                    break;
                case 2:
                    if(mAuth.getCurrentUser()==null){
                        mToolboar.setNavigationIcon(R.drawable.menubutton_object_color);
                        mToolboar.setNavigationIcon(R.drawable.menubuttonwhite);
                    }else {

                        mChart.spin(500, mChart.getRotationAngle(), mChart.getRotationAngle() + 72, Easing.EasingOption.EaseInCubic);
                    }
                    break;
                case 3:
                    if(mAuth.getCurrentUser()==null){
                        mToolboar.setNavigationIcon(R.drawable.menubuttonwhite);
                        mToolboar.setNavigationIcon(R.drawable.menubutton_object_color);
                    }{
                        mChart.spin(500, mChart.getRotationAngle(), mChart.getRotationAngle() + 72, Easing.EasingOption.EaseInCubic);
                    }


                    break;
                case 4:
                    if(mAuth.getCurrentUser()==null){
                        mToolboar.setNavigationIcon(R.drawable.menubutton_object_color);
                        mToolboar.setNavigationIcon(R.drawable.menubuttonwhite);
                    }{

                        mChart.spin(500, mChart.getRotationAngle(), mChart.getRotationAngle() + 72, Easing.EasingOption.EaseInCubic);
                    }
                    break;
                case 5:
                    if(mAuth.getCurrentUser()==null){
                        mToolboar.setNavigationIcon(R.drawable.menubuttonwhite);
                        mToolboar.setNavigationIcon(R.drawable.menubutton_object_color);
                    }{

                        mChart.spin(500, mChart.getRotationAngle(), mChart.getRotationAngle() + 72, Easing.EasingOption.EaseInCubic);
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

            count=count+1;
            if(count>5){
                count=0;
            }
            mHanlder.sendEmptyMessage(count);

            mHanlder.postDelayed(this, 1 * 500);//延迟5秒,再次执行task本身,实现了循环的效果

        }
    };







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
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("我在MainActivity的","onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("我在MainActivity的","onResume");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        GlobalVariable User = (GlobalVariable)getApplicationContext();
        Log.i("我在MainActivity的","onRestart");
        Log.i("Exercise_mainOR的值",""+User.getTask_reg());
        User.setWord("haha");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("我在MainActivity的","onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("我在MainActivity的","onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("我在MainActivity的","onDestroy");
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
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }

        else if (id == R.id.email_register)
        {

            Intent i = new Intent(MainActivity.this,RegisterActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        else if(id==R.id.chat_room){
            Intent i = new Intent(MainActivity.this,MainActivityFireBase.class);
            startActivity(i);
        }
        else if(id==R.id.setting_account){
            Intent i = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(i);
        }
        else if(id==R.id.Logout){

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
            OneSignal.deleteTag("Uid");


            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(MainActivity.this,MainActivity.class);
            finish();
            startActivity(i);

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




    public static class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {


        public String user_id_send,task_req,task;
        public JSONObject data,user_id,task_req_data,task_data;

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            data = result.notification.payload.additionalData;
            String activityToBeOpened;

            Log.i("Data1", String.valueOf(data));

            if (data != null) {
                activityToBeOpened = data.optString("activityToBeOpened", null);
                user_id = result.notification.payload.additionalData;
                task_req_data=result.notification.payload.additionalData;
                task_data=result.notification.payload.additionalData;
                user_id_send=user_id.optString("user_id");
                task_req=task_req_data.optString("Task_req");
                task=task_data.optString("Task");




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
                else if(activityToBeOpened!=null && activityToBeOpened.equals("TaskProfile")) {
                    Intent intent = new Intent(getContext(), TaskProfile.class);
                    intent.putExtra("user_id", user_id_send);
                    intent.putExtra("Task_req", task_req);
                    intent.putExtra("Task", task);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("CrunchesDareFriendProfile")) {
                    Intent intent = new Intent(getContext(), CrunchesDareFriendProfile.class);
                    intent.putExtra("user_id", user_id_send);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("RunningDareFriendProfile")) {
                    Intent intent = new Intent(getContext(), RunningDareFriendProfile.class);
                    intent.putExtra("user_id", user_id_send);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("SquatsDareFriendProfile")) {
                    Intent intent = new Intent(getContext(), SquatsDareFriendProfile.class);
                    intent.putExtra("user_id", user_id_send);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("WalkingDareFriendProfile")) {
                    Intent intent = new Intent(getContext(), WalkingDareFriendProfile.class);
                    intent.putExtra("user_id", user_id_send);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
                else if(activityToBeOpened!=null && activityToBeOpened.equals("YogaDareFriendProfile")) {
                    Intent intent = new Intent(getContext(), YogaDareFriendProfile.class);
                    intent.putExtra("user_id", user_id_send);
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }



}
