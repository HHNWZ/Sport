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

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SquatsMonitor extends AppCompatActivity {
    private Toolbar squats_monitor_toolbar;
    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    private final int MENU_ITEM_PERMISSION_SETTING = 1;
    public static final String APP_TAG = "Sport";
    private static SquatsMonitor mInstance = null;
    private SquatsReporter sReporter;
    private static DatabaseReference mDatabase;
    private static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squats_monitor);
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        squats_monitor_toolbar=(Toolbar)findViewById(R.id.squats_monitor_toolbar);
        squats_monitor_toolbar.setTitle("深蹲監控");
        setSupportActionBar(squats_monitor_toolbar);

        squats_monitor_toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        squats_monitor_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SquatsMonitor.this,Exercise_main.class);
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
                pmsManager.requestPermissions(mKeySet, SquatsMonitor.this).setResultListener(mPermissionListener);
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
            sReporter =new SquatsReporter(mStore);

            try {
                // 檢查是否獲取了此應用程序所需的權限
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

                if (resultMap.containsValue(Boolean.FALSE)) {
                    //如果未獲取，則請求讀取步數的權限
                    pmsManager.requestPermissions(mKeySet, SquatsMonitor.this).setResultListener(mPermissionListener);
                } else {
                    //獲取當前步數並顯示它

                    sReporter.start();
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

                        sReporter.start();
                    }
                }
            };

    public void drawSquats(long squats_duration,int squats_mean_heart_rate,long squats_start_time,long squats_end_time, int squats_calorie,int squats_max_heart_rate,String squats_UUID,int squats_count){
        if(squats_count!=0){
            TextView duration_data_of_squats_monitor= (TextView) findViewById(R.id.duration_data_of_squats_monitor);
            TextView calorie_data_of_squats_monitor= (TextView) findViewById(R.id.calorie_data_of_squats_monitor);
            TextView meanHeartRate_data_of_squats_monitor= (TextView) findViewById(R.id.meanHeartRate_data_of_squats_monitor);
            TextView max_heart_rate_data_of_squats_monitor= (TextView) findViewById(R.id.max_heart_rate_data_of_squats_monitor);
            TextView squats_start_time_data= (TextView) findViewById(R.id.squats_start_time_data);
            TextView squats_end_time_data= (TextView) findViewById(R.id.squats_end_time_data);
            TextView count_squats_data=(TextView)findViewById(R.id.count_squats_data);

            duration_data_of_squats_monitor.setText(""+Time.get_duration_time(squats_duration));
            calorie_data_of_squats_monitor.setText(""+squats_calorie+"大卡");
            meanHeartRate_data_of_squats_monitor.setText(""+squats_mean_heart_rate+"次/分");
            max_heart_rate_data_of_squats_monitor.setText(""+squats_max_heart_rate+"次/分");
            squats_start_time_data.setText(""+Time.get_start_time(squats_start_time));
            squats_end_time_data.setText(""+Time.get_end_time(squats_end_time));
            count_squats_data.setText(""+squats_count+"次");

            writeNewExerciseData.setNewExerciseData3("squats",
                    Time.get_start_time(squats_start_time),
                    Time.get_end_time(squats_end_time),
                    Time.get_duration_time(squats_duration),
                    squats_mean_heart_rate,
                    squats_calorie,
                    squats_max_heart_rate,
                    squats_count,
                    Time.getToDate(squats_start_time),
                    Time.getTime(squats_start_time)
            );


            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String big_count=dataSnapshot.child("exercise_count").child("squats").child("big_count").getValue().toString();
                    String small_count=dataSnapshot.child("exercise_count").child("squats").child("small_count").getValue().toString();
                    String today_count=dataSnapshot.child("exercise_count").child("squats").child("today_count").getValue().toString();
                    String all_count=dataSnapshot.child("exercise_count").child("squats").child("all_count").getValue().toString();
                    String week_record=dataSnapshot.child("exercise_count").child("squats").child("week_record").getValue().toString();
                    String DataIdcheck=dataSnapshot.child("exercise_count").child("squats").child("DataIdcheck").getValue().toString();

                    int bigCount=Integer.parseInt(big_count);
                    int smallCount=Integer.parseInt(small_count);
                    int today_count1=Integer.parseInt(today_count);
                    int all_count1=Integer.parseInt(all_count);
                    int week_record1=Integer.parseInt(week_record);
                    if(squats_count>bigCount){
                        mDatabase.child("exercise_count").child("squats").child("big_count").setValue(squats_count);

                        if(smallCount==0){
                            mDatabase.child("exercise_count").child("squats").child("small_count").setValue(bigCount);

                        }else if(smallCount!=0&&bigCount<smallCount){
                            mDatabase.child("exercise_count").child("squats").child("small_count").setValue(bigCount);

                        }
                    }else if(squats_count<bigCount){

                        if(smallCount==0){
                            mDatabase.child("exercise_count").child("squats").child("small_count").setValue(squats_count);

                        }else if(smallCount!=0&&squats_count<smallCount){
                            mDatabase.child("exercise_count").child("squats").child("small_count").setValue(squats_count);

                        }
                    }

                    if(DataIdcheck.equals(squats_UUID)){

                    }else {
                        today_count1=today_count1+squats_count;
                        all_count1=all_count1+squats_count;
                        week_record1=week_record1+squats_count;

                        mDatabase.child("exercise_count").child("squats").child("today_count").setValue(today_count1);
                        mDatabase.child("exercise_count").child("squats").child("all_count").setValue(all_count1);
                        mDatabase.child("exercise_count").child("squats").child("DataIdcheck").setValue(squats_UUID);
                        mDatabase.child("exercise_count").child("squats").child("week_record").setValue(week_record1);
                        mDatabase.child("exercise_count").child("squats").child("count").setValue(squats_count);
                        mDatabase.child("squats_all_count").setValue(all_count1);
                        mDatabase.child("squats_all_count_sort").setValue(-all_count1);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }
    public static SquatsMonitor getInstance() {
        return mInstance;
    }

    private void showPermissionAlarmDialog() {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(SquatsMonitor.this);
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
