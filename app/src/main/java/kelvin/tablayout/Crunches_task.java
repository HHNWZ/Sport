package kelvin.tablayout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a888888888.sport.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class Crunches_task extends AppCompatActivity {

    private Toolbar crunches_task_toolbar;
    public static ActionBar actionBar;
    private static FirebaseAuth mAuth;

    private DatabaseReference crunches_task_Database;
    private DatabaseReference crunches_task_friendDatabase;
    private DatabaseReference crunches_task_myDatabase;
    private DatabaseReference crunches_task_confirm_database;
    private DatabaseReference crunches_task_friend_point_database;

    private TextView crunches_task_data;
    private TextView crunches_susses_text_view;

    private CircleImageView my_crunches_task_image;
    private TextView my_crunches_task_name;
    private TextView  my_crunches_task_finish_count_data;

    private CircleImageView friend_crunches_task_image;
    private TextView friend_crunches_task_name;
    private TextView friend_crunches_task_finish_count;
    private TextView friend_crunches_task_finish_count_data;

    private TextView crunches_task_text_and;
    private TextView crunches_task_friend_point;
    private Button confirm_crunches_task_button;


    private Data crunches_data=new Data();
    public CircularSeekBar crunches_seek_bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches_task);
        crunches_task_toolbar=(Toolbar)findViewById(R.id.crunches_task_toolbar);
        setSupportActionBar(crunches_task_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("仰臥起坐每週共同任務");
        actionBar.setSubtitle("點擊右邊的圖標和朋友一起完成");
        crunches_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();

        crunches_seek_bar=(CircularSeekBar)findViewById(R.id.crunches_seek_bar);
        crunches_task_data=(TextView)findViewById(R.id.crunches_task_data);
        crunches_susses_text_view=(TextView)findViewById(R.id.crunches_susses_text_view);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Crunches_task.this,Exercise_main.class);
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
                    Intent intent = new Intent(Crunches_task.this,FriendActivity.class);
                    intent.putExtra("Task_req","Task_req_crunches");
                    intent.putExtra("Task","Task_crunches");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Log.i("點擊","成功");
                    break;
            }

            return true;
        }
    };



    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
