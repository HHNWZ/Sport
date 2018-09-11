package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.onesignal.OneSignal;

public class Crunches_dare extends AppCompatActivity {
    private Toolbar crunches_dare_app_bar;
    public static ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches_dare);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();

        crunches_dare_app_bar=(Toolbar)findViewById(R.id.crunches_dare_app_bar);
        setSupportActionBar(crunches_dare_app_bar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("仰臥起坐挑戰");
        actionBar.setDisplayHomeAsUpEnabled(true);
        crunches_dare_app_bar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Crunches_dare.this,Exercise_main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.task_friend:
                    /*Intent intent = new Intent(Crunches_task.this,FriendActivity.class);
                    intent.putExtra("Task_req","Task_req_crunches");
                    intent.putExtra("Task","Task_crunches");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                    Log.i("點擊","成功");
                    break;
                case R.id.gear_fit:

                    break;
            }

            return true;
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.dare_menu, menu);
        return true;
    }

}
