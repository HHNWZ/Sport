package kelvin.tablayout;

import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConstants;//此類包含Samsung Health Android SDK的數據類型的接口，例如計數步驟或練習。
import com.samsung.android.sdk.healthdata.HealthData;//此類為指定的健康數據類型提供健康數據對象，例如，血壓或體重。
import com.samsung.android.sdk.healthdata.HealthDataObserver;//此類定義健康數據更改的運行狀況數據觀察器。
import com.samsung.android.sdk.healthdata.HealthDataResolver;//此類能夠使用指定的過濾器和一些聚合函數訪問健康數據以進行插入，讀取，更新和刪除。
import com.samsung.android.sdk.healthdata.HealthDataStore;//此類提供與運行狀況數據存儲的連接。
import com.samsung.android.sdk.healthdata.HealthResultHolder;//此接口表示調用方法的結果。

import java.util.Calendar;
import java.util.TimeZone;

public class StepCountReporter {
    private final HealthDataStore mStore;

    private StepCountObserver mStepCountObserver;
    private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000L;

    public StepCountReporter(HealthDataStore store) {
        mStore = store;
    }

    public void start(StepCountObserver listener) {
        mStepCountObserver = listener;
        // 註冊觀察員以監聽步數的變化並獲得今天的步數
        HealthDataObserver.addObserver(mStore, HealthConstants.StepCount.HEALTH_DATA_TYPE, mObserver);//	addObserver(HealthDataStore store, String dataTypeName, HealthDataObserver observer),添加一個想要跟踪給定健康數據類型的數據更改的觀察者。
        readTodayStepCount();
    }

    // Read the today's step count on demand
    private void readTodayStepCount() {
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);//創建HealthDataResolver實例。


        //設置從今天開始時間到當前時間的時間範圍
        long startTime = getStartTimeOfToday();
        long endTime = startTime + ONE_DAY_IN_MILLIS;

        HealthDataResolver.ReadRequest request = new HealthDataResolver.ReadRequest.Builder()//此接口能夠請求讀取特定運行狀況數據類型的運行狀況數據。
                .setDataType(HealthConstants.StepCount.HEALTH_DATA_TYPE)//為要讀取的請求設置運行狀況數據類型。
                .setProperties(new String[] {HealthConstants.StepCount.COUNT})//為要讀取的運行狀況數據結果設置屬性數組。
                .setLocalTimeRange(HealthConstants.StepCount.START_TIME, HealthConstants.StepCount.TIME_OFFSET,
                        startTime, endTime)// 設置本地時間範圍，以便在保存的運行狀況數據中存在具有不同TIME_OFFSET的數據時，不會錯過查詢結果中的數據。
                .build();

        try {
            resolver.read(request).setResultListener(mListener);
        } catch (Exception e) {
            Log.e(Walking_monitor.APP_TAG, "步驟計數失敗", e);
        }
    }

    private long getStartTimeOfToday() {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTimeInMillis();
    }

    private final HealthResultHolder.ResultListener<HealthDataResolver.ReadResult> mListener = result -> {//此接口是一個回調函數，用於異步接收運行狀況數據請求的結果。
        int count = 0;
        String distance;
        int count_distance=0;


        try {
            for (HealthData data : result) {
                count += data.getInt(HealthConstants.StepCount.COUNT);//獲取與給定屬性關聯的整數值。
                //count_distance+=data.getFloat(HealthConstants.StepCount.DISTANCE);
                //distance=data.getString(HealthConstants.StepCount.DISTANCE);
                //count_distance+=Float.parseFloat(distance);

            }
        } finally {
            result.close();
        }

        if (mStepCountObserver != null) {
            mStepCountObserver.onChanged(count);

        }
    };

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {


        //收到更改事件時更新步驟計數
        @Override
        public void onChange(String dataTypeName) {
            Log.d(Walking_monitor.APP_TAG, "Observer接收數據更改事件");
            readTodayStepCount();
        }
    };

    public interface StepCountObserver {
        void onChanged(int count);
    }
}
