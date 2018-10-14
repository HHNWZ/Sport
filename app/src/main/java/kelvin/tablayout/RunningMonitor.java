package kelvin.tablayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
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
    private ActionBar running_monitor_action_bar;
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
        setSupportActionBar(running_monitor_toolbar);
        running_monitor_action_bar=getSupportActionBar();
        running_monitor_action_bar.setTitle("跑步監控");
        running_monitor_action_bar.setDisplayHomeAsUpEnabled(true);


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
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(RunningMonitor.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
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

            PhilText distance_running_data=(PhilText) findViewById(R.id.distance_running_data);

            PhilText duration_of_running_of_minute=(PhilText)findViewById(R.id.duration_of_running_of_minute);
            PhilText duration_of_running_of_second=(PhilText)findViewById(R.id.duration_of_running_of_second);
            PhilText running_start_year=(PhilText)findViewById(R.id.running_start_year);
            PhilText running_start_month=(PhilText)findViewById(R.id.running_start_month);
            PhilText running_start_day=(PhilText)findViewById(R.id.running_start_day);
            TextView running_start_week_text_view=(TextView)findViewById(R.id.running_start_week_text_view);
            PhilText running_start_hour=(PhilText) findViewById(R.id.running_start_hour);
            PhilText running_start_minute=(PhilText)findViewById(R.id.running_start_minute);
            PhilText running_start_second=(PhilText)findViewById(R.id.running_start_second);
            PhilText running_finish_year=(PhilText)findViewById(R.id.running_finish_year);
            PhilText running_finish_month=(PhilText)findViewById(R.id.running_finish_month);
            PhilText running_finish_day=(PhilText)findViewById(R.id.running_finish_day);
            TextView running_finish_week_text_view=(TextView)findViewById(R.id.running_finish_week_text_view);
            PhilText running_finish_hour=(PhilText) findViewById(R.id.running_finish_hour);
            PhilText running_finish_minute=(PhilText)findViewById(R.id.running_finish_minute);
            PhilText running_finish_second=(PhilText)findViewById(R.id.running_finish_second);
            PhilText calorie_running_data=(PhilText) findViewById(R.id.calorie_running_data);
            PhilText mean_HeartRat_running_data=(PhilText) findViewById(R.id.mean_HeartRat_running_data);
            PhilText max_heart_rate_running_data=(PhilText) findViewById(R.id.max_heart_rate_running_data);
            PhilText incline_distance_data_of_running_monitor=(PhilText)findViewById(R.id.incline_distance_data_of_running_monitor);
            PhilText decline_distance_data_of_running_monitor=(PhilText)findViewById(R.id.decline_distance_data_of_running_monitor);
            PhilText max_altitude_data_of_running_monitor=(PhilText)findViewById(R.id.max_altitude_data_of_running_monitor);
            PhilText min_altitude_data_of_running_monitor=(PhilText)findViewById(R.id.min_altitude_data_of_running_monitor);
            PhilText mean_speed_data_of_running_monitor=(PhilText)findViewById(R.id.mean_speed_data_of_running_monitor);
            PhilText max_speed_data_of_running_monitor=(PhilText)findViewById(R.id.max_speed_data_of_running_monitor);


            distance_running_data.setText("" + UnitConversion.get_kilometer(running_distance));

            duration_of_running_of_minute.setText(Time.get_duration_minute(running_duration));
            duration_of_running_of_second.setText(Time.get_duration_second(running_duration));
            running_start_year.setText(Time.get_start_time_year(running_start_time));
            running_start_month.setText(Time.get_start_time_month(running_start_time));
            running_start_day.setText(Time.get_start_time_day(running_start_time));
            running_start_week_text_view.setText("週"+Time.get_start_week(running_start_time));
            running_start_hour.setText(Time.get_start_time_hour(running_start_time));
            running_start_minute.setText(Time.get_start_time_minute(running_start_time));
            running_start_second.setText(Time.get_start_time_second(running_start_time));
            running_finish_year.setText(Time.get_finish_time_year(running_end_time));
            running_finish_month.setText(Time.get_finish_time_month(running_end_time));
            running_finish_day.setText(Time.get_finish_time_day(running_end_time));
            running_finish_week_text_view.setText(Time.get_finish_week(running_end_time));
            running_finish_hour.setText(Time.get_finish_time_hour(running_end_time));
            running_finish_minute.setText(Time.get_finish_time_minute(running_end_time));
            running_finish_second.setText(Time.get_finish_time_second(running_end_time));
            calorie_running_data.setText(""+running_calorie);
            mean_HeartRat_running_data.setText(""+running_mean_heart_rate);
            max_heart_rate_running_data.setText(""+running_max_heart_rate);
            incline_distance_data_of_running_monitor.setText(""+UnitConversion.get_kilometer(running_incline_distance));
            decline_distance_data_of_running_monitor.setText(""+UnitConversion.get_kilometer(running_decline_distance));
            max_altitude_data_of_running_monitor.setText(""+running_max_altitude);
            min_altitude_data_of_running_monitor.setText(""+running_min_altitude);
            mean_speed_data_of_running_monitor.setText(""+UnitConversion.get_kilometer_per_hour(running_mean_speed));
            max_speed_data_of_running_monitor.setText(""+UnitConversion.get_kilometer_per_hour(running_max_speed));


            

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
