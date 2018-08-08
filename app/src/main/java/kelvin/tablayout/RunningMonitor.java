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


import com.example.a888888888.sport.R;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_monitor);
        running_monitor_toolbar=(Toolbar)findViewById(R.id.running_monitor_toolBar);
        running_monitor_toolbar.setTitle("跑步監控");
        setSupportActionBar(running_monitor_toolbar);

        running_monitor_toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        running_monitor_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningMonitor.this,kelvin_tab_layout.class);
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
    public void drawRunning(double running_distance_meter, long running_duration,int running_mean_heart_rate,long running_start_time,long running_end_time, double running_calorie,double running_incline_distance_meter,double running_decline_distance_menter){

        TextView distance_data_of_running_monitor = (TextView)findViewById(R.id.distance_data_of_running_monitor);
        TextView duration_data_of_running_monitor = (TextView)findViewById(R.id.duration_data_of_running_monitor);
        TextView meanHeartRate_data_of_running_monitor =(TextView )findViewById(R.id.meanHeartRate_data_of_running_monitor);
        TextView start_time_data_of_running_monitor=(TextView)findViewById(R.id.start_time_data_of_running_monitor);
        TextView end_time_data_of_running_monitor=(TextView)findViewById(R.id.end_time_data_of_running_monitor);
        TextView calorie_data_of_running_monitor=(TextView)findViewById(R.id.calorie_data_of_running_monitor);
        TextView incline_distance_data_of_running_monitor=(TextView)findViewById(R.id.incline_distance_data_of_running_monitor);
        TextView decline_distance_data_of_running_monitor=(TextView)findViewById(R.id.decline_distance_data_of_running_monitor);

        Calendar running_duration_time = Calendar.getInstance();
        running_duration_time.setTimeInMillis(running_duration);
        int running_duration_hour = running_duration_time.get(Calendar.HOUR);//12小时制
        int running_duration_minute = running_duration_time.get(Calendar.MINUTE);
        int running_duration_second = running_duration_time.get(Calendar.SECOND);

        Calendar running_start_calendar=Calendar.getInstance();
        running_start_calendar.setTimeInMillis(running_start_time);
        int running_start_year = running_start_calendar.get(Calendar.YEAR);
        int running_start_month= running_start_calendar.get(Calendar.MONTH)+1;
        int running_start_day =running_start_calendar.get(Calendar.DAY_OF_MONTH);
        int running_start_week=running_start_calendar.get(Calendar.DAY_OF_WEEK);
        int running_start_hour = running_start_calendar.get(Calendar.HOUR_OF_DAY);
        int running_start_minute=running_start_calendar.get(Calendar.MINUTE);
        int running_start_second=running_start_calendar.get(Calendar.SECOND);

        Calendar running_end_calendar=Calendar.getInstance();
        running_end_calendar.setTimeInMillis(running_end_time);
        int running_end_year = running_end_calendar.get(Calendar.YEAR);
        int running_end_month= running_end_calendar.get(Calendar.MONTH)+1;
        int running_end_day =running_end_calendar.get(Calendar.DAY_OF_MONTH);
        int running_end_week=running_end_calendar.get(Calendar.DAY_OF_WEEK);
        int running_end_hour = running_end_calendar.get(Calendar.HOUR_OF_DAY);
        int running_end_minute=running_end_calendar.get(Calendar.MINUTE);
        int running_end_second=running_end_calendar.get(Calendar.SECOND);



        double running_distance_km;
        double running_incline_distance_km;
        double running_decline_distance_km;
        running_decline_distance_km=((running_decline_distance_menter/100)/10d);
        running_distance_km = ((running_distance_meter/100d)/10d);
        running_incline_distance_km=((running_incline_distance_meter/100d)/10d);
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");

        distance_data_of_running_monitor.setText(""+df.format(running_distance_km));
        meanHeartRate_data_of_running_monitor.setText(""+running_mean_heart_rate);
        if(running_duration<3600000){
            duration_data_of_running_monitor.setText(running_duration_minute+"分鐘 "+running_duration_second+"秒");
        }
        if(running_duration>=3600000){
            duration_data_of_running_monitor.setText(running_duration_hour+"時 "+running_duration_minute+"分鐘 "+running_duration_second+"秒");
        }

        if(running_start_week==1){
            start_time_data_of_running_monitor.setText(running_start_year+"年"+running_start_month+"月"+running_start_day+"日 週日"+" "+running_start_hour+"時 "+running_start_minute+"分鐘 "+running_start_second+"秒 ");
            end_time_data_of_running_monitor.setText(running_end_year+"年"+running_end_month+"月"+running_end_day+"日 週日"+" "+running_end_hour+"時 "+running_end_minute+"分鐘 "+running_end_second+"秒 ");
        }
        if(running_start_week==2){
            start_time_data_of_running_monitor.setText(running_start_year+"年"+running_start_month+"月"+running_start_day+"日 週一"+" "+running_start_hour+"時 "+running_start_minute+"分鐘 "+running_start_second+"秒 ");
            end_time_data_of_running_monitor.setText(running_end_year+"年"+running_end_month+"月"+running_end_day+"日 週一"+" "+running_end_hour+"時 "+running_end_minute+"分鐘 "+running_end_second+"秒 ");
        }
        if(running_start_week==3){
            start_time_data_of_running_monitor.setText(running_start_year+"年"+running_start_month+"月"+running_start_day+"日 週二"+" "+running_start_hour+"時 "+running_start_minute+"分鐘 "+running_start_second+"秒 ");
            end_time_data_of_running_monitor.setText(running_end_year+"年"+running_end_month+"月"+running_end_day+"日 週二"+" "+running_end_hour+"時 "+running_end_minute+"分鐘 "+running_end_second+"秒 ");
        }
        if(running_start_week==4){
            start_time_data_of_running_monitor.setText(running_start_year+"年"+running_start_month+"月"+running_start_day+"日 週三"+" "+running_start_hour+"時 "+running_start_minute+"分鐘 "+running_start_second+"秒 ");
            end_time_data_of_running_monitor.setText(running_end_year+"年"+running_end_month+"月"+running_end_day+"日 週三"+" "+running_end_hour+"時 "+running_end_minute+"分鐘 "+running_end_second+"秒 ");
        }
        if(running_start_week==5){
            start_time_data_of_running_monitor.setText(running_start_year+"年"+running_start_month+"月"+running_start_day+"日 週四"+" "+running_start_hour+"時 "+running_start_minute+"分鐘 "+running_start_second+"秒 ");
            end_time_data_of_running_monitor.setText(running_end_year+"年"+running_end_month+"月"+running_end_day+"日 週四"+" "+running_end_hour+"時 "+running_end_minute+"分鐘 "+running_end_second+"秒 ");
        }
        if(running_start_week==6){
            start_time_data_of_running_monitor.setText(running_start_year+"年"+running_start_month+"月"+running_start_day+"日 週五"+" "+running_start_hour+"時 "+running_start_minute+"分鐘 "+running_start_second+"秒 ");
            end_time_data_of_running_monitor.setText(running_end_year+"年"+running_end_month+"月"+running_end_day+"日 週五"+" "+running_end_hour+"時 "+running_end_minute+"分鐘 "+running_end_second+"秒 ");
        }
        if(running_start_week==7){
            start_time_data_of_running_monitor.setText(running_start_year+"年"+running_start_month+"月"+running_start_day+"日 週六"+" "+running_start_hour+"時 "+running_start_minute+"分鐘 "+running_start_second+"秒 ");
            end_time_data_of_running_monitor.setText(running_end_year+"年"+running_end_month+"月"+running_end_day+"日 週六"+" "+running_end_hour+"時 "+running_end_minute+"分鐘 "+running_end_second+"秒 ");
        }
        calorie_data_of_running_monitor.setText(""+df.format(running_calorie));
        incline_distance_data_of_running_monitor.setText(""+df.format(running_incline_distance_km));
        decline_distance_data_of_running_monitor.setText(""+df.format(running_decline_distance_km));



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
