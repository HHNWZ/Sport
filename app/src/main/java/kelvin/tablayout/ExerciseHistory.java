package kelvin.tablayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;

import java.util.Timer;
import java.util.TimerTask;

public class ExerciseHistory extends AppCompatActivity {
    private Toolbar day_exercise_history_toolbar;
    private static int i=0;
    private static int k=0;
    public static ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_history);
        day_exercise_history_toolbar=(Toolbar)findViewById(R.id.day_exercise_history_toolbar);

        setSupportActionBar(day_exercise_history_toolbar);
        actionBar =getSupportActionBar();
        actionBar.setTitle("步行歷史記錄");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("以日期顯示");
        actionBar.setLogo(R.drawable.walkingtoolbar);
        //actionBar.setDisplayShowTitleEnabled(false);
        Log.i("i值",""+i);
        mHanlder.postDelayed(task,1);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ExerciseHistory.this,Exercise_main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor( android.R.color.holo_blue_light)));
                    break;
                case 2:
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor( android.R.color.holo_red_light)));
                    break;
                case 3:
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor( android.R.color.holo_orange_light)));
                    break;
                case 4:
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor( android.R.color.holo_green_light)));
                    break;
                case 5:
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if(i>5){
                i=0;
            }
            i=i+1;
            mHanlder.sendEmptyMessage(i);

            mHanlder.postDelayed(this, 1 * 100);//延迟5秒,再次执行task本身,实现了循环的效果

        }
    };
}
