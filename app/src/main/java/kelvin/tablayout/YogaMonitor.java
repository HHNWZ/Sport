package kelvin.tablayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class YogaMonitor extends AppCompatActivity {
    private Toolbar yoga_monitor_toolbar;
    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    private final int MENU_ITEM_PERMISSION_SETTING = 1;
    public static final String APP_TAG = "Sport";
    private static YogaMonitor mInstance = null;
    private YogaReporter yReporter;
    private static DatabaseReference mDatabase;
    private static FirebaseAuth mAuth;
    private ActionBar yoga_monitor_action_bar;
    //public static String time;
    private String from_page="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_monitor);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        Bundle bundle=getIntent().getExtras();
        from_page=bundle.getString("from_page");
        Log.i("OnCreate的From_page",from_page);
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        yoga_monitor_toolbar=(Toolbar)findViewById(R.id.yoga_monitor_toolBar);
        setSupportActionBar(yoga_monitor_toolbar);
        yoga_monitor_action_bar=getSupportActionBar();
        yoga_monitor_action_bar.setTitle("瑜伽監控");
        yoga_monitor_action_bar.setDisplayHomeAsUpEnabled(true);




        mInstance = this;
        mKeySet = new HashSet<>();
        mKeySet.add(new HealthPermissionManager.PermissionKey(HealthConstants.Exercise.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ));
        HealthDataService healthDataService = new HealthDataService();
        try {
            healthDataService.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 創建一個HealthDataStore實例並設置其偵聽器
        mStore = new HealthDataStore(this, mConnectionListener);
        //Toast.makeText(Walking_monitor.this, "請求連接到運行狀況數據存儲", Toast.LENGTH_SHORT).show();
        // 請求連接到運行狀況數據存儲


        mStore.connectService();
        //Toast.makeText(Walking_monitor.this, ""+walking_location.length, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //給菜單充氣;這會將項目添加到操作欄（如果存在）。
        getMenuInflater().inflate(R.menu.step_count_menu, menu);
        menu.add(1, MENU_ITEM_PERMISSION_SETTING, 0, "連接到 S Health");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 處理操作欄項目點擊此處。操作欄會
        // 自動處理Home / Up按鈕上的點擊，這麼久
        //在AndroidManifest.xml中指定父活動時。
        int id = item.getItemId();
        if(id == (MENU_ITEM_PERMISSION_SETTING)) {
            HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
            try {
                // 顯示允許用戶更改選項的用戶權限UI
                pmsManager.requestPermissions(mKeySet, YogaMonitor.this).setResultListener(mPermissionListener);
            } catch (Exception e) {
                Log.e(APP_TAG, e.getClass().getName() + " - " + e.getMessage());
                Log.e(APP_TAG, "權限設置失敗。");
            }
        }
        if (item.getItemId() == android.R.id.home) {
            if(from_page.equals("ExercisePlanning")){
                Intent intent = new Intent(YogaMonitor.this, MonitoringTool.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return super.onOptionsItemSelected(item);
            }else if(from_page.equals("MainActivity")){
                Intent intent = new Intent(YogaMonitor.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return super.onOptionsItemSelected(item);
            }
        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed(){
        if(from_page.equals("ExercisePlanning")){
            Intent intent = new Intent(YogaMonitor.this, MonitoringTool.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }else if(from_page.equals("MainActivity")){
            Intent intent = new Intent(YogaMonitor.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }
    private final HealthDataStore.ConnectionListener mConnectionListener = new HealthDataStore.ConnectionListener() {

        @Override
        public void onConnected() {
            Log.d(APP_TAG, "健康數據服務已連接。");
            HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
            //mReporter = new StepCountReporter(mStore);
            yReporter = new YogaReporter(mStore);
            try {
                // 檢查是否獲取了此應用程序所需的權限
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

                if (resultMap.containsValue(Boolean.FALSE)) {
                    //如果未獲取，則請求讀取步數的權限
                    pmsManager.requestPermissions(mKeySet, YogaMonitor.this).setResultListener(mPermissionListener);
                } else {
                    //獲取當前步數並顯示它

                    yReporter.start();
                }
            } catch (Exception e) {
                Log.e(APP_TAG, e.getClass().getName() + " - " + e.getMessage());
                Log.e(APP_TAG, "權限設置失敗。");
            }
        }

        @Override
        public void onConnectionFailed(HealthConnectionErrorResult error) {
            Log.d(APP_TAG, "健康數據服務不可用。");
            showConnectionFailureDialog(error);
        }

        @Override
        public void onDisconnected() {
            Log.d(APP_TAG, "Health data service is disconnected.");
        }
    };

    private final HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> mPermissionListener =
            new HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult>() {

                @Override
                public void onResult(HealthPermissionManager.PermissionResult result) {
                    Log.d(APP_TAG, "健康數據服務已斷開連接。");
                    Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = result.getResultMap();

                    if (resultMap.containsValue(Boolean.FALSE)) {

                        showPermissionAlarmDialog();
                    } else {
                        //獲取當前步數並顯示它

                        yReporter.start();
                    }
                }
            };

    public void drawYoga(long yoga_duration,int yoga_mean_heart_rate,long yoga_start_time,long yoga_end_time, int yoga_calorie,int yoga_max_heart_rate,String yoga_UUID){
        if(yoga_duration!=0){
            PhilText duration_of_yoga_of_minute=(PhilText)findViewById(R.id.duration_of_yoga_of_minute);
            PhilText duration_of_yoga_of_second=(PhilText)findViewById(R.id.duration_of_yoga_of_second);
            PhilText yoga_start_year=(PhilText)findViewById(R.id.yoga_start_year);
            PhilText yoga_start_month=(PhilText)findViewById(R.id.yoga_start_month);
            PhilText yoga_start_day=(PhilText)findViewById(R.id.yoga_start_day);
            TextView yoga_start_week_text_view=(TextView)findViewById(R.id.yoga_start_week_text_view);
            PhilText yoga_start_hour=(PhilText) findViewById(R.id.yoga_start_hour);
            PhilText yoga_start_minute=(PhilText)findViewById(R.id.yoga_start_minute);
            PhilText yoga_start_second=(PhilText)findViewById(R.id.yoga_start_second);
            PhilText yoga_finish_year=(PhilText)findViewById(R.id.yoga_finish_year);
            PhilText yoga_finish_month=(PhilText)findViewById(R.id.yoga_finish_month);
            PhilText yoga_finish_day=(PhilText)findViewById(R.id.yoga_finish_day);
            TextView yoga_finish_week_text_view=(TextView)findViewById(R.id.yoga_finish_week_text_view);
            PhilText yoga_finish_hour=(PhilText) findViewById(R.id.yoga_finish_hour);
            PhilText yoga_finish_minute=(PhilText)findViewById(R.id.yoga_finish_minute);
            PhilText yoga_finish_second=(PhilText)findViewById(R.id.yoga_finish_second);
            PhilText calorie_yoga_data=(PhilText) findViewById(R.id.calorie_yoga_data);
            PhilText mean_HeartRat_yoga_data=(PhilText) findViewById(R.id.mean_HeartRat_yoga_data);
            PhilText max_heart_rate_yoga_data=(PhilText) findViewById(R.id.max_heart_rate_yoga_data);

            duration_of_yoga_of_minute.setText(Time.get_duration_minute(yoga_duration));
            duration_of_yoga_of_second.setText(Time.get_duration_second(yoga_duration));
            yoga_start_year.setText(Time.get_start_time_year(yoga_start_time));
            yoga_start_month.setText(Time.get_start_time_month(yoga_start_time));
            yoga_start_day.setText(Time.get_start_time_day(yoga_start_time));
            yoga_start_week_text_view.setText("週"+Time.get_start_week(yoga_start_time));
            yoga_start_hour.setText(Time.get_start_time_hour(yoga_start_time));
            yoga_start_minute.setText(Time.get_start_time_minute(yoga_start_time));
            yoga_start_second.setText(Time.get_start_time_second(yoga_start_time));
            yoga_finish_year.setText(Time.get_finish_time_year(yoga_end_time));
            yoga_finish_month.setText(Time.get_finish_time_month(yoga_end_time));
            yoga_finish_day.setText(Time.get_finish_time_day(yoga_end_time));
            yoga_finish_week_text_view.setText(Time.get_finish_week(yoga_end_time));
            yoga_finish_hour.setText(Time.get_finish_time_hour(yoga_end_time));
            yoga_finish_minute.setText(Time.get_finish_time_minute(yoga_end_time));
            yoga_finish_second.setText(Time.get_finish_time_second(yoga_end_time));
            calorie_yoga_data.setText(""+yoga_calorie);
            mean_HeartRat_yoga_data.setText(""+yoga_mean_heart_rate);
            max_heart_rate_yoga_data.setText(""+yoga_max_heart_rate);

            writeNewExerciseData.setNewExerciseData2("yoga",
                    Time.get_start_time(yoga_start_time),
                    Time.get_end_time(yoga_end_time),
                    Time.get_duration_time(yoga_duration),
                    yoga_mean_heart_rate,
                    yoga_calorie,
                    yoga_max_heart_rate,
                    Time.getToDate(yoga_start_time),
                    Time.getTime(yoga_start_time)
                    );


            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String long_time=dataSnapshot.child("exercise_count").child("yoga").child("long_time").getValue().toString();
                    String short_time=dataSnapshot.child("exercise_count").child("yoga").child("short_time").getValue().toString();
                    String today_time=dataSnapshot.child("exercise_count").child("yoga").child("today_time").getValue().toString();
                    String all_time=dataSnapshot.child("exercise_count").child("yoga").child("all_time").getValue().toString();
                    String week_record=dataSnapshot.child("exercise_count").child("yoga").child("week_record").getValue().toString();
                    String DataIdcheck=dataSnapshot.child("exercise_count").child("yoga").child("DataIdcheck").getValue().toString();
                    String week_calorie=dataSnapshot.child("exercise_count").child("yoga").child("week_calorie").getValue().toString();
                    long longTime=Long.parseLong(long_time);
                    long shortTime=Long.parseLong(short_time);
                    long todayTime=Long.parseLong(today_time);
                    long allTime=Long.parseLong(all_time);
                    long weekRecord=Long.parseLong(week_record);
                    int week_calorie1=Integer.parseInt(week_calorie);
                    if(yoga_duration>longTime){
                        mDatabase.child("exercise_count").child("yoga").child("long_time").setValue(yoga_duration);
                        Log.i("追踪1","新的距離大於最長距離");
                        if(shortTime==0){
                            mDatabase.child("exercise_count").child("yoga").child("short_time").setValue(longTime);
                            Log.i("追踪2longDistance",""+longTime);
                            Log.i("追踪3shortDistance",""+shortTime);
                        }else if(shortTime!=0&&longTime<shortTime){
                            mDatabase.child("exercise_count").child("yoga").child("short_time").setValue(longTime);
                            Log.i("追踪4longDistance",""+longTime);
                            Log.i("追踪5shortDistance",""+shortTime);
                        }
                    }else if(yoga_duration<longTime){
                        Log.i("追踪6","新的距離小於最短距離");
                        if(shortTime==0){
                            mDatabase.child("exercise_count").child("yoga").child("short_time").setValue(yoga_duration);
                            Log.i("追踪7longDistance",""+longTime);
                            Log.i("追踪8shortDistance",""+shortTime);
                        }else if(shortTime!=0&&yoga_duration<shortTime){
                            mDatabase.child("exercise_count").child("yoga").child("short_time").setValue(yoga_duration);
                            Log.i("追踪9longDistance",""+longTime);
                            Log.i("追踪10shortDistance",""+shortTime);
                        }
                    }

                    if(DataIdcheck.equals(yoga_UUID)){

                    }else {
                        todayTime=todayTime+yoga_duration;
                        allTime=allTime+yoga_duration;
                        weekRecord=weekRecord+yoga_duration;
                        week_calorie1=week_calorie1+yoga_calorie;

                        mDatabase.child("exercise_count").child("yoga").child("today_time").setValue(todayTime);
                        mDatabase.child("exercise_count").child("yoga").child("all_time").setValue(allTime);
                        mDatabase.child("exercise_count").child("yoga").child("DataIdcheck").setValue(yoga_UUID);
                        mDatabase.child("exercise_count").child("yoga").child("week_record").setValue(weekRecord);
                        mDatabase.child("exercise_count").child("yoga").child("time").setValue(yoga_duration);
                        mDatabase.child("exercise_count").child("yoga").child("week_calorie").setValue(week_calorie1);
                        mDatabase.child("yoga_all_count").setValue(allTime);
                        mDatabase.child("yoga_all_count_sort").setValue(-allTime);

                    }





                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            if(from_page.equals("ExercisePlanning")){
                Log.i("我在這裡","1");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("exercise_plan").hasChild("yoga")&&from_page.equals("ExercisePlanning")&&dataSnapshot.child("exercise_plan").hasChild("yoga_check_time")){
                            Log.i("我在這裡","2");
                            String yoga_now_plan=dataSnapshot.child("exercise_plan").child("yoga").getValue().toString();
                            String yoga_date_check=dataSnapshot.child("exercise_plan").child("yoga_check_time").getValue().toString();
                            int yoga_now_plan_int=Integer.parseInt(yoga_now_plan);
                            if(!yoga_date_check.equals(Time.get_start_time(yoga_start_time))){
                                Log.i("我在這裡","3");
                                mDatabase.child("exercise_plan").child("yoga_check_time").setValue(Time.get_start_time(yoga_start_time));
                                if(yoga_mean_heart_rate<160){
                                    Log.i("我在這裡","4");
                                    int yoga_before_plan_int=yoga_now_plan_int+20;
                                    mDatabase.child("exercise_plan").child("yoga").setValue(yoga_before_plan_int);
                                    Toast.makeText(YogaMonitor.this,"你的心跳低於正常所以把你的運動方案的瑜伽量提高",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }


        }
    }



    public static YogaMonitor getInstance() {
        return mInstance;
    }
    private void showPermissionAlarmDialog() {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(YogaMonitor.this);
        alert.setTitle("注意");
        alert.setMessage("應獲取所有權限");
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    private void showConnectionFailureDialog(HealthConnectionErrorResult error) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        mConnError = error;
        String message = "無法與S Health連接";

        if (mConnError.hasResolution()) {
            switch(error.getErrorCode()) {
                case HealthConnectionErrorResult.PLATFORM_NOT_INSTALLED:
                    message = "請安裝S Health";
                    break;
                case HealthConnectionErrorResult.OLD_VERSION_PLATFORM:
                    message = "請升級S Healthh";
                    break;
                case HealthConnectionErrorResult.PLATFORM_DISABLED:
                    message = "請啟用S Health";
                    break;
                case HealthConnectionErrorResult.USER_AGREEMENT_NEEDED:
                    message = "請同意S Health政策";
                    break;
                default:
                    message = "請提供S Health";
                    break;
            }
        }

        alert.setMessage(message);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (mConnError.hasResolution()) {
                    mConnError.resolve(mInstance);
                }
            }
        });

        if (error.hasResolution()) {
            alert.setNegativeButton("Cancel", null);
        }

        alert.show();
    }


}
