package kelvin.tablayout;

import android.database.Cursor;
import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.Calendar;

public class YogaDareReporter {
    private final HealthDataStore mStore;

    public YogaDareReporter(HealthDataStore store){
        mStore=store;
    }

    public void start(){
        HealthDataObserver.addObserver(mStore,HealthConstants.Exercise.HEALTH_DATA_TYPE,mObserver);
        readDareYoga();
    }

    private void readDareYoga(){
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);

        // 設置從今天開始時間到當前時間的時間範圍
        long startTime = getStartTimeOfToday();
        long endTime = System.currentTimeMillis();
        HealthDataResolver.Filter filter = HealthDataResolver.Filter.and(HealthDataResolver.Filter.greaterThanEquals(HealthConstants.Exercise.START_TIME, startTime),
                HealthDataResolver.Filter.lessThanEquals(HealthConstants.Exercise.START_TIME, endTime), HealthDataResolver.Filter.eq(HealthConstants.Exercise.EXERCISE_TYPE,9002));

        HealthDataResolver.ReadRequest request = new HealthDataResolver.ReadRequest.Builder()
                .setDataType(HealthConstants.Exercise.HEALTH_DATA_TYPE)
                .setProperties(new String[]{HealthConstants.Exercise.DISTANCE,
                        HealthConstants.Exercise.DURATION,
                        HealthConstants.Exercise.CALORIE,
                })
                .setFilter(filter)
                .build();

        try {
            resolver.read(request).setResultListener(mListener);
        } catch (Exception e) {
            Log.e(Walking_monitor.APP_TAG, e.getClass().getName() + " - " + e.getMessage());
            Log.e(Walking_monitor.APP_TAG, "步驟計數失敗。");
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

    private final HealthResultHolder.ResultListener<HealthDataResolver.ReadResult> mListener;
    {
        mListener = new HealthResultHolder.ResultListener<HealthDataResolver.ReadResult>() {
            @Override
            public void onResult(HealthDataResolver.ReadResult result) {
                long crunches_duration = 0;
                int crunches_calorie=0;
                Cursor c = null;
                try {
                    c = result.getResultCursor();
                    if (c != null) {
                        while (c.moveToNext()) {
                            crunches_duration = c.getLong(c.getColumnIndex(HealthConstants.Exercise.DURATION));
                            crunches_calorie=c.getInt(c.getColumnIndex(HealthConstants.Exercise.CALORIE));
                        }
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
                Yoga_dare.getInstance().drawYogaDare(crunches_duration,crunches_calorie);
            }
        };
    }

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        // 收到更改事件時更新步驟計數
        @Override
        public void onChange(String dataTypeName) {
            Log.d(Walking_monitor.APP_TAG, "Observer接收數據更改事件");
            readDareYoga();
        }
    };


}
