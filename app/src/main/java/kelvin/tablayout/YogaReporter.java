package kelvin.tablayout;

import android.database.Cursor;
import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.Calendar;

public class YogaReporter {
    private final HealthDataStore mStore;

    public YogaReporter(HealthDataStore store) {
        mStore = store;
    }

    public void start() {
        //註冊觀察員以監聽步數的變化並獲得今天的步數
        HealthDataObserver.addObserver(mStore, HealthConstants.Exercise.HEALTH_DATA_TYPE, mObserver);
        readLastYoga();
    }
    // 根據需求閱讀今天的步數
    private void readLastYoga(){
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
                        HealthConstants.Exercise.MEAN_HEART_RATE,
                        HealthConstants.Exercise.START_TIME,
                        HealthConstants.Exercise.END_TIME,
                        HealthConstants.Exercise.CALORIE,
                        //HealthConstants.Exercise.INCLINE_DISTANCE,
                        //HealthConstants.Exercise.DECLINE_DISTANCE,
                        HealthConstants.Exercise.MAX_HEART_RATE,
                        //HealthConstants.Exercise.MAX_ALTITUDE,
                        //HealthConstants.Exercise.MIN_ALTITUDE,
                        //HealthConstants.Exercise.MEAN_SPEED,
                        //HealthConstants.Exercise.MAX_SPEED,
                        HealthConstants.Common.UUID,
                        //HealthConstants.Exercise.LOCATION_DATA



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
                //double walking_distance = 0;
                long yoga_duration = 0;
                long yoga_start_time = 0;
                long yoga_end_time = 0;
                int yoga_mean_heart_rate = 0;
                int yoga_calorie = 0;
                //double walking_incline_distance = 0;
                //double walking_decline_distance = 0;
                int yoga_max_heart_rate = 0;
                //int walking_max_altitude = 0;
                //int walking_min_altitude = 0;
                //double walking_mean_speed = 0;
                //double walking_max_speed = 0;
                String yoga_UUID = "";
                Cursor c = null;
                //byte[] walking_location_data=null;


                try {


                    c = result.getResultCursor();
                    if (c != null) {
                        while (c.moveToNext()) {
                            //walking_distance = c.getDouble(c.getColumnIndex(HealthConstants.Exercise.DISTANCE));
                            yoga_duration = c.getLong(c.getColumnIndex(HealthConstants.Exercise.DURATION));
                            yoga_mean_heart_rate = c.getInt(c.getColumnIndex(HealthConstants.Exercise.MEAN_HEART_RATE));
                            yoga_start_time = c.getLong(c.getColumnIndex(HealthConstants.Exercise.START_TIME));
                            yoga_end_time = c.getLong(c.getColumnIndex(HealthConstants.Exercise.END_TIME));
                            yoga_calorie = c.getInt(c.getColumnIndex(HealthConstants.Exercise.CALORIE));
                            //walking_incline_distance = c.getDouble(c.getColumnIndex(HealthConstants.Exercise.INCLINE_DISTANCE));
                            //walking_decline_distance = c.getDouble(c.getColumnIndex(HealthConstants.Exercise.DECLINE_DISTANCE));
                            yoga_max_heart_rate = c.getInt(c.getColumnIndex(HealthConstants.Exercise.MAX_HEART_RATE));
                            //walking_max_altitude = c.getInt(c.getColumnIndex(HealthConstants.Exercise.MAX_ALTITUDE));
                            //walking_min_altitude = c.getInt(c.getColumnIndex(HealthConstants.Exercise.MIN_ALTITUDE));
                            //walking_mean_speed = c.getDouble(c.getColumnIndex(HealthConstants.Exercise.MEAN_SPEED));
                            //walking_max_speed = c.getDouble(c.getColumnIndex(HealthConstants.Exercise.MAX_SPEED));
                            yoga_UUID = c.getString(c.getColumnIndex(HealthConstants.Common.UUID));
                            //walking_location_data = c.getBlob(c.getColumnIndex(HealthConstants.Exercise.LOCATION_DATA));
                            //Log.e("追踪1",""+walking_location_data.length);


                        }
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }

                YogaMonitor.getInstance().drawYoga(yoga_duration, yoga_mean_heart_rate, yoga_start_time, yoga_end_time, yoga_calorie, yoga_max_heart_rate,yoga_UUID);
            }
        };
    }

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        // 收到更改事件時更新步驟計數
        @Override
        public void onChange(String dataTypeName) {
            Log.d(Walking_monitor.APP_TAG, "Observer接收數據更改事件");
            readLastYoga();
        }
    };
}
