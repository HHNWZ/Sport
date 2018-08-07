package kelvin.tablayout;

import android.database.Cursor;
import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.Calendar;

public class RunningReporter {
    private final HealthDataStore mStore;

    public RunningReporter(HealthDataStore store){
        mStore =store;
    }

    public void start() {
        //註冊觀察員以監聽步數的變化並獲得今天的步數
        HealthDataObserver.addObserver(mStore, HealthConstants.Exercise.HEALTH_DATA_TYPE, mObserver);
        readLastWalk();
    }

    // 根據需求閱讀今天的步數
    private void readLastWalk() {
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);

        // 設置從今天開始時間到當前時間的時間範圍
        long startTime = getStartTimeOfToday();
        long endTime = System.currentTimeMillis();
        HealthDataResolver.Filter filter = HealthDataResolver.Filter.and(HealthDataResolver.Filter.greaterThanEquals(HealthConstants.Exercise.START_TIME, startTime),
                HealthDataResolver.Filter.lessThanEquals(HealthConstants.Exercise.START_TIME, endTime), HealthDataResolver.Filter.eq(HealthConstants.Exercise.EXERCISE_TYPE,1002));

        HealthDataResolver.ReadRequest request = new HealthDataResolver.ReadRequest.Builder()
                .setDataType(HealthConstants.Exercise.HEALTH_DATA_TYPE)
                .setProperties(new String[] {HealthConstants.Exercise.DISTANCE,HealthConstants.Exercise.DURATION,HealthConstants.Exercise.MEAN_HEART_RATE})
                .setFilter(filter)
                .build();

        try {
            resolver.read(request).setResultListener(mListener);
        } catch (Exception e) {
            Log.e(RunningMonitor.APP_TAG, e.getClass().getName() + " - " + e.getMessage());
            Log.e(RunningMonitor.APP_TAG, "步驟計數失敗。");
        }
    }

    private long getStartTimeOfToday() {
        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTimeInMillis();
    }

    private final HealthResultHolder.ResultListener<HealthDataResolver.ReadResult> mListener = new HealthResultHolder.ResultListener<HealthDataResolver.ReadResult>() {
        @Override
        public void onResult(HealthDataResolver.ReadResult result) {
            int count = 0;
            long time=0 ;
            String meanHeartRate2="";
            Cursor c = null;

            try {
                c = result.getResultCursor();
                if (c != null) {
                    while (c.moveToNext()) {
                        count = c.getInt(c.getColumnIndex(HealthConstants.Exercise.DISTANCE));
                        time = c.getLong(c.getColumnIndex(HealthConstants.Exercise.DURATION));
                        meanHeartRate2=c.getString(c.getColumnIndex(HealthConstants.Exercise.MEAN_HEART_RATE));
                    }
                }
            } finally {
                if (c != null) {
                    c.close();
                }
            }
            RunningMonitor.getInstance().drawRunning(String.valueOf(count),time,meanHeartRate2);
        }
    };

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        // 收到更改事件時更新步驟計數
        @Override
        public void onChange(String dataTypeName) {
            Log.d(RunningMonitor.APP_TAG, "Observer接收數據更改事件");
            readLastWalk();
        }
    };
}
