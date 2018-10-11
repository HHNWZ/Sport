package kelvin.tablayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


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

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RunningMonitor extends AppCompatActivity {
    private Toolbar running_monitor_toolbar;
    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    private final int MENU_ITEM_PERMISSION_SETTING = 1;
    public static final String APP_TAG = "Sport";
    private static RunningMonitor mInstance = null;
    private RunningReporter rReporter;
    private static DatabaseReference mDatabase;
    private static FirebaseAuth mAuth;
    private   double todayDistance;
    private String today_distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_running_monitor);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        running_monitor_toolbar=(Toolbar)findViewById(R.id.running_monitor_toolBar);
        running_monitor_toolbar.setTitle("跑步監控");
        setSupportActionBar(running_monitor_toolbar);
        running_monitor_toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        running_monitor_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningMonitor.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


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
        // 請求連接到運行狀況數據存儲
        mStore.connectService();




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
                pmsManager.requestPermissions(mKeySet, RunningMonitor.this).setResultListener(mPermissionListener);
            } catch (Exception e) {
                Log.e(APP_TAG, e.getClass().getName() + " - " + e.getMessage());
                Log.e(APP_TAG, "權限設置失敗。");
            }
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private final HealthDataStore.ConnectionListener mConnectionListener = new HealthDataStore.ConnectionListener() {

        @Override
        public void onConnected() {
            Log.d(APP_TAG, "健康數據服務已連接。");
            HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
            //mReporter = new StepCountReporter(mStore);
            rReporter = new RunningReporter(mStore);
            try {
                // 檢查是否獲取了此應用程序所需的權限
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

                if (resultMap.containsValue(Boolean.FALSE)) {
                    //如果未獲取，則請求讀取步數的權限
                    pmsManager.requestPermissions(mKeySet, RunningMonitor.this).setResultListener(mPermissionListener);
                } else {
                    //獲取當前步數並顯示它

                    rReporter.start();
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

                        rReporter.start();
                    }
                }
            };
    public void drawRunning(double running_distance, long running_duration,int running_mean_heart_rate,long running_start_time,long running_end_time, int running_calorie,double running_incline_distance,double running_decline_distance,int running_max_heart_rate,int running_max_altitude,
                            int running_min_altitude,double running_mean_speed,double running_max_speed,String running_UUID){
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        if(running_distance!=0) {
            TextView distance_data_of_running_monitor = (TextView) findViewById(R.id.distance_data_of_running_monitor);
            TextView duration_data_of_running_monitor = (TextView) findViewById(R.id.duration_data_of_running_monitor);
            TextView meanHeartRate_data_of_running_monitor = (TextView) findViewById(R.id.meanHeartRate_data_of_running_monitor);
            TextView start_time_data_of_running_monitor = (TextView) findViewById(R.id.start_time_data_of_running_monitor);
            TextView end_time_data_of_running_monitor = (TextView) findViewById(R.id.end_time_data_of_running_monitor);
            TextView calorie_data_of_running_monitor = (TextView) findViewById(R.id.calorie_data_of_running_monitor);
            TextView incline_distance_data_of_running_monitor = (TextView) findViewById(R.id.incline_distance_data_of_running_monitor);
            TextView decline_distance_data_of_running_monitor = (TextView) findViewById(R.id.decline_distance_data_of_running_monitor);
            TextView max_heart_rate_data_of_running_monitor = (TextView) findViewById(R.id.max_heart_rate_data_of_running_monitor);
            TextView max_altitude_data_of_running_monitor = (TextView) findViewById(R.id.max_altitude_data_of_running_monitor);
            TextView min_altitude_data_of_running_monitor = (TextView) findViewById(R.id.min_altitude_data_of_running_monitor);
            TextView mean_speed_data_of_running_monitor = (TextView) findViewById(R.id.mean_speed_data_of_running_monitor);
            TextView max_speed_data_of_running_monitor = (TextView) findViewById(R.id.max_speed_data_of_running_monitor);



            distance_data_of_running_monitor.setText("" + UnitConversion.get_kilometer(running_distance)+"公里");
            meanHeartRate_data_of_running_monitor.setText("" + running_mean_heart_rate+"次/分");
            duration_data_of_running_monitor.setText(Time.get_duration_time(running_duration));
            start_time_data_of_running_monitor.setText(Time.get_start_time(running_start_time));
            end_time_data_of_running_monitor.setText(Time.get_end_time(running_end_time));
            calorie_data_of_running_monitor.setText("" + running_calorie+"大卡");
            incline_distance_data_of_running_monitor.setText("" + UnitConversion.get_kilometer(running_incline_distance)+"公里");
            decline_distance_data_of_running_monitor.setText("" + UnitConversion.get_kilometer(running_decline_distance)+"公里");
            max_heart_rate_data_of_running_monitor.setText("" + running_max_heart_rate+"次/分");
            max_altitude_data_of_running_monitor.setText("" + running_max_altitude+"米");
            min_altitude_data_of_running_monitor.setText("" + running_min_altitude+"米");
            mean_speed_data_of_running_monitor.setText("" + UnitConversion.get_kilometer_per_hour(running_mean_speed)+"公里/小時");
            max_speed_data_of_running_monitor.setText("" + UnitConversion.get_kilometer_per_hour(running_max_speed)+"公里小時");

            writeNewExerciseData.setNewExerciseData("running",
                    Time.get_start_time(running_start_time),
                    Time.get_end_time(running_end_time),
                    UnitConversion.get_kilometer(running_distance),
                    Time.get_duration_time(running_duration),
                    running_mean_heart_rate,
                    running_calorie,
                    UnitConversion.get_kilometer(running_incline_distance),
                    UnitConversion.get_kilometer(running_decline_distance),
                    running_max_heart_rate,
                    running_max_altitude,
                    running_min_altitude,
                    UnitConversion.get_kilometer_per_hour(running_mean_speed),
                    UnitConversion.get_kilometer_per_hour(running_max_speed),
                    Time.getToDate(running_start_time),
                    Time.getTime(running_start_time)
            );

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String long_distance=dataSnapshot.child("exercise_count").child("running").child("long_distance").getValue().toString();
                    String short_distance=dataSnapshot.child("exercise_count").child("running").child("short_distance").getValue().toString();
                    String DataIdcheck=dataSnapshot.child("exercise_count").child("running").child("DataIdcheck").getValue().toString();
                    String today_record=dataSnapshot.child("exercise_count").child("running").child("today_record").getValue().toString();
                    String all_record=dataSnapshot.child("exercise_count").child("running").child("all_record").getValue().toString();
                    String week_record=dataSnapshot.child("exercise_count").child("running").child("week_record").getValue().toString();
                    String week_calorie=dataSnapshot.child("exercise_count").child("running").child("week_calorie").getValue().toString();
                    int week_calorie1=Integer.parseInt(week_calorie);

                    double longDistance=Double.parseDouble(long_distance);
                    double shortDistance=Double.parseDouble(short_distance);
                    double today_record1=Double.parseDouble(today_record);
                    double all_record1=Double.parseDouble(all_record);
                    double week_record1=Double.parseDouble(week_record);
                    if(UnitConversion.get_kilometer(running_distance)>longDistance){
                        mDatabase.child("exercise_count").child("running").child("long_distance").setValue(UnitConversion.get_kilometer(running_distance));
                        Log.i("追踪1","新的距離大於最長距離");
                        if(shortDistance==0){
                            mDatabase.child("exercise_count").child("running").child("short_distance").setValue(longDistance);
                            Log.i("追踪2longDistance",""+longDistance);
                            Log.i("追踪3shortDistance",""+shortDistance);
                        }else if(shortDistance!=0&&longDistance<shortDistance){
                            mDatabase.child("exercise_count").child("running").child("short_distance").setValue(longDistance);
                            Log.i("追踪4longDistance",""+longDistance);
                            Log.i("追踪5shortDistance",""+shortDistance);
                        }
                    }else if(UnitConversion.get_kilometer(running_distance)<longDistance){
                        Log.i("追踪6","新的距離小於最短距離");
                        if(shortDistance==0){
                            mDatabase.child("exercise_count").child("running").child("short_distance").setValue(UnitConversion.get_kilometer(running_distance));
                            Log.i("追踪7longDistance",""+longDistance);
                            Log.i("追踪8shortDistance",""+shortDistance);
                        }else if(shortDistance!=0&&UnitConversion.get_kilometer(running_distance)<shortDistance){
                            mDatabase.child("exercise_count").child("running").child("short_distance").setValue(UnitConversion.get_kilometer(running_distance));
                            Log.i("追踪9longDistance",""+longDistance);
                            Log.i("追踪10shortDistance",""+shortDistance);
                        }
                    }

                    if(DataIdcheck.equals(running_UUID)){

                    }else {

                        today_record1=today_record1+UnitConversion.get_kilometer(running_distance);
                        all_record1=all_record1+UnitConversion.get_kilometer(running_distance);
                        week_record1=week_record1+UnitConversion.get_kilometer(running_distance);
                        week_calorie1=week_calorie1+running_calorie;
                        DecimalFormat df = new DecimalFormat("0.00");
                        mDatabase.child("exercise_count").child("running").child("today_record").setValue(df.format(today_record1));
                        mDatabase.child("exercise_count").child("running").child("all_record").setValue(df.format(all_record1));
                        mDatabase.child("exercise_count").child("running").child("week_record").setValue(df.format(week_record1));
                        mDatabase.child("exercise_count").child("running").child("DataIdcheck").setValue(running_UUID);
                        mDatabase.child("exercise_count").child("running").child("distance").setValue(UnitConversion.get_kilometer(running_distance));
                        mDatabase.child("exercise_count").child("running").child("week_calorie").setValue( week_calorie1);
                        mDatabase.child("running_all_count").setValue(all_record1);
                        mDatabase.child("running_all_count_sort").setValue(-all_record1);



                    }









                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
    public static RunningMonitor getInstance() {
        return mInstance;
    }
    private void showPermissionAlarmDialog() {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(RunningMonitor.this);
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
