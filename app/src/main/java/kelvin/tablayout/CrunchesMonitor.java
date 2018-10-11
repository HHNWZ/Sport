package kelvin.tablayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CrunchesMonitor extends AppCompatActivity {
    private Toolbar crunches_monitor_toolbar;
    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    private final int MENU_ITEM_PERMISSION_SETTING = 1;
    public static final String APP_TAG = "Sport";
    private static CrunchesMonitor mInstance = null;
    private CrunchesReporter cReporter;
    private static DatabaseReference mDatabase;
    private static FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches_monitor);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        crunches_monitor_toolbar=(Toolbar)findViewById(R.id.crunches_monitor_toolbar);
        crunches_monitor_toolbar.setTitle("仰臥起坐監控");
        setSupportActionBar(crunches_monitor_toolbar);

        crunches_monitor_toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        crunches_monitor_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CrunchesMonitor.this,MainActivity.class);
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
                pmsManager.requestPermissions(mKeySet, CrunchesMonitor.this).setResultListener(mPermissionListener);
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
             cReporter =new CrunchesReporter(mStore);

            try {
                // 檢查是否獲取了此應用程序所需的權限
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

                if (resultMap.containsValue(Boolean.FALSE)) {
                    //如果未獲取，則請求讀取步數的權限
                    pmsManager.requestPermissions(mKeySet, CrunchesMonitor.this).setResultListener(mPermissionListener);
                } else {
                    //獲取當前步數並顯示它

                    cReporter.start();
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

                        cReporter.start();
                    }
                }
            };

    public void drawCrunches(long crunches_duration,int crunches_mean_heart_rate,long crunches_start_time,long crunches_end_time, int crunches_calorie,int crunches_max_heart_rate,String crunches_UUID,int crunches_count){
        if(crunches_count!=0){
            TextView duration_data_of_crunches_monitor= (TextView) findViewById(R.id.duration_data_of_crunches_monitor);
            TextView calorie_data_of_crunches_monitor= (TextView) findViewById(R.id.calorie_data_of_crunches_monitor);
            TextView meanHeartRate_data_of_crunches_monitor= (TextView) findViewById(R.id.meanHeartRate_data_of_crunches_monitor);
            TextView max_heart_rate_data_of_crunches_monitor= (TextView) findViewById(R.id.max_heart_rate_data_of_crunches_monitor);
            TextView crunches_start_time_data= (TextView) findViewById(R.id.crunches_start_time_data);
            TextView crunches_end_time_data= (TextView) findViewById(R.id.crunches_end_time_data);
            TextView count_crunches_data=(TextView)findViewById(R.id.count_crunches_data);

            duration_data_of_crunches_monitor.setText(""+Time.get_duration_time(crunches_duration));
            calorie_data_of_crunches_monitor.setText(""+crunches_calorie);
            meanHeartRate_data_of_crunches_monitor.setText(""+crunches_mean_heart_rate);
            max_heart_rate_data_of_crunches_monitor.setText(""+crunches_max_heart_rate);
            crunches_start_time_data.setText(""+Time.get_start_time(crunches_start_time));
            crunches_end_time_data.setText(""+Time.get_end_time(crunches_end_time));
            count_crunches_data.setText(""+crunches_count);

            writeNewExerciseData.setNewExerciseData3("crunches",
                    Time.get_start_time(crunches_start_time),
                    Time.get_end_time(crunches_end_time),
                    Time.get_duration_time(crunches_duration),
                    crunches_mean_heart_rate,
                    crunches_calorie,
                    crunches_max_heart_rate,
                    crunches_count,
                    Time.getToDate(crunches_start_time),
                    Time.getTime(crunches_start_time)
            );


            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String big_count=dataSnapshot.child("exercise_count").child("crunches").child("big_count").getValue().toString();
                    String small_count=dataSnapshot.child("exercise_count").child("crunches").child("small_count").getValue().toString();
                    String today_count=dataSnapshot.child("exercise_count").child("crunches").child("today_count").getValue().toString();
                    String all_count=dataSnapshot.child("exercise_count").child("crunches").child("all_count").getValue().toString();
                    String week_record=dataSnapshot.child("exercise_count").child("crunches").child("week_record").getValue().toString();
                    String DataIdcheck=dataSnapshot.child("exercise_count").child("crunches").child("DataIdcheck").getValue().toString();
                    String week_calorie=dataSnapshot.child("exercise_count").child("crunches").child("week_calorie").getValue().toString();
                    int bigCount=Integer.parseInt(big_count);
                    int smallCount=Integer.parseInt(small_count);
                    int today_count1=Integer.parseInt(today_count);
                    int all_count1=Integer.parseInt(all_count);
                    int week_record1=Integer.parseInt(week_record);
                    int week_calorie1=Integer.parseInt(week_calorie);
                    if(crunches_count>bigCount){
                        mDatabase.child("exercise_count").child("crunches").child("big_count").setValue(crunches_count);
                        Log.i("追踪1","新的距離大於最長距離");
                        if(smallCount==0){
                            mDatabase.child("exercise_count").child("crunches").child("small_count").setValue(bigCount);
                            Log.i("追踪2longDistance",""+bigCount);
                            Log.i("追踪3shortDistance",""+smallCount);
                        }else if(smallCount!=0&&bigCount<smallCount){
                            mDatabase.child("exercise_count").child("crunches").child("small_count").setValue(bigCount);
                            Log.i("追踪4longDistance",""+bigCount);
                            Log.i("追踪5shortDistance",""+smallCount);
                        }
                    }else if(crunches_count<bigCount){
                        Log.i("追踪6","新的距離小於最短距離");
                        if(smallCount==0){
                            mDatabase.child("exercise_count").child("crunches").child("small_count").setValue(crunches_count);
                            Log.i("追踪7longDistance",""+bigCount);
                            Log.i("追踪8shortDistance",""+smallCount);
                        }else if(smallCount!=0&&crunches_count<smallCount){
                            mDatabase.child("exercise_count").child("crunches").child("small_count").setValue(crunches_count);
                            Log.i("追踪9longDistance",""+bigCount);
                            Log.i("追踪10shortDistance",""+smallCount);
                        }
                    }
                    if(DataIdcheck.equals(crunches_UUID)){

                    }else {
                        today_count1=today_count1+crunches_count;
                        all_count1=all_count1+crunches_count;
                        week_record1=week_record1+crunches_count;
                        week_calorie1=week_calorie1+crunches_calorie;

                        mDatabase.child("exercise_count").child("crunches").child("today_count").setValue(today_count1);
                        mDatabase.child("exercise_count").child("crunches").child("all_count").setValue(all_count1);
                        mDatabase.child("exercise_count").child("crunches").child("DataIdcheck").setValue(crunches_UUID);
                        mDatabase.child("exercise_count").child("crunches").child("week_record").setValue(week_record1);
                        mDatabase.child("exercise_count").child("crunches").child("count").setValue(crunches_count);
                        mDatabase.child("exercise_count").child("crunches").child("week_calorie").setValue(week_calorie1);
                        mDatabase.child("crunches_all_count").setValue(all_count1);
                        mDatabase.child("crunches_all_count_sort").setValue(-all_count1);

                    }







                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }
    public static CrunchesMonitor getInstance() {
        return mInstance;
    }

    private void showPermissionAlarmDialog() {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(CrunchesMonitor.this);
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
