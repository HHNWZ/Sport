package kelvin.tablayout;

import android.database.Cursor;
import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.Calendar;

public class CrunchesDareReporter {
    private final HealthDataStore mStore;

    public CrunchesDareReporter(HealthDataStore store){
        mStore=store;
    }

    public void start(){
        HealthDataObserver.addObserver(mStore,HealthConstants.Exercise.HEALTH_DATA_TYPE,mObserver);
        readDareCrunches();
    }

    private void readDareCrunches(){
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);

        // 設置從今天開始時間到當前時間的時間範圍
        long startTime = getStartTimeOfToday();
        long endTime = System.currentTimeMillis();
        HealthDataResolver.Filter filter = HealthDataResolver.Filter.and(HealthDataResolver.Filter.greaterThanEquals(HealthConstants.Exercise.START_TIME, startTime),
                HealthDataResolver.Filter.lessThanEquals(HealthConstants.Exercise.START_TIME, endTime), HealthDataResolver.Filter.eq(HealthConstants.Exercise.EXERCISE_TYPE,10023));

        HealthDataResolver.ReadRequest request = new HealthDataResolver.ReadRequest.Builder()
                .setDataType(HealthConstants.Exercise.HEALTH_DATA_TYPE)
                .setProperties(new String[]{HealthConstants.Exercise.DISTANCE,
                        HealthConstants.Exercise.DURATION,
                        HealthConstants.Exercise.COUNT,
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
                int crunches_count=0;
                Cursor c = null;
                try {
                    c = result.getResultCursor();
                    if (c != null) {
                        while (c.moveToNext()) {
                            crunches_duration = c.getLong(c.getColumnIndex(HealthConstants.Exercise.DURATION));
                            crunches_count=c.getInt(c.getColumnIndex(HealthConstants.Exercise.COUNT));
                        }
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
                Crunches_dare.getInstance().drawCrunchesDare(crunches_duration,crunches_count);
            }
        };
    }

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        // 收到更改事件時更新步驟計數
        @Override
        public void onChange(String dataTypeName) {
            Log.d(Walking_monitor.APP_TAG, "Observer接收數據更改事件");
            readDareCrunches();
        }
    };


}
