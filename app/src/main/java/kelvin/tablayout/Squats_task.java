package kelvin.tablayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.github.mikephil.charting.animation.Easing;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class Squats_task extends AppCompatActivity {
    private Toolbar squats_task_toolbar;
    public static ActionBar actionBar;
    private static FirebaseAuth mAuth;
    public static int count = 0;
    private DatabaseReference squats_task_Database;
    private DatabaseReference squats_task_friendDatabase;
    private DatabaseReference squats_task_myDatabase;

    private DatabaseReference squats_task_friend_point_database;


    private TextView squats_task_data;
    private TextView squats_susses_text_view;
    private TextView squats_susses_text_view_data;

    private CircleImageView my_squats_task_image;
    private TextView my_squats_task_name;
    private TextView my_squats_task_finish_count_data;

    private CircleImageView friend_squats_task_image;
    private TextView friend_squats_task_name;
    private TextView friend_squats_task_finish_count;
    private TextView friend_squats_task_finish_count_data;

    private TextView squats_task_text_and;
    private TextView squats_task_friend_point;
    private TextView squats_task_friend_point1;
    private TextView squats_task_friend_point2;
    private Button confirm_squats_task_button;

    private static String squats_task_my_name;
    private static String squats_task_my_image;
    private static String squats_task_my_count;
    private static String squats_task_my_friend_point;

    private static String squats_task_friend_name;
    private static String squats_task_friend_image;
    private static String squats_task_friend_count;

    private static int squats_task_my_count_int;
    private static int squats_task_my_friend_point_int;
    private static int squats_task_friend_count_int;
    private static int squats_progress;
    private static int squats_task_data_int;
    private String myID;

    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    private final int MENU_ITEM_PERMISSION_SETTING = 1;
    public static final String APP_TAG = "Sport";
    private static Squats_task mInstance = null;
    private SquatsTaskReporter cReporter;


    private Data squats_data = new Data();
    public CircularSeekBar squats_task_seek_bar;
    private String from_page="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squats_task);
        Bundle bundle=getIntent().getExtras();
        from_page=bundle.getString("from_page");
        Log.i("OnCreate的From_page",from_page);
        GlobalVariable task = (GlobalVariable) getApplicationContext();

        task.setTask("Squats_Task");
        task.setTask_reg("Task_req_squats");
        squats_task_toolbar = (Toolbar) findViewById(R.id.squats_task_toolbar);
        setSupportActionBar(squats_task_toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("深蹲每週共同任務");

        squats_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();
        myID = mAuth.getCurrentUser().getUid();

        squats_task_myDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        squats_task_friend_point_database = FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        squats_task_Database = FirebaseDatabase.getInstance().getReference();
        //squats_today_count_database=FirebaseDatabase.getInstance().getReference().child("Users");

        squats_task_friendDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        squats_task_seek_bar = (CircularSeekBar) findViewById(R.id.squats_task_seek_bar);
        squats_task_data = (TextView) findViewById(R.id.squats_task_data);
        squats_susses_text_view = (TextView) findViewById(R.id.squats_susses_text_view);
        squats_susses_text_view_data = (TextView) findViewById(R.id.squats_susses_text_view_data);
        my_squats_task_image = (CircleImageView) findViewById(R.id.my_squats_task_image);
        my_squats_task_name = (TextView) findViewById(R.id.my_squats_task_name);
        my_squats_task_finish_count_data = (TextView) findViewById(R.id.my_squats_task_finish_count_data);

        friend_squats_task_image = (CircleImageView) findViewById(R.id.friend_squats_task_image);
        friend_squats_task_name = (TextView) findViewById(R.id.friend_squats_task_name);
        friend_squats_task_finish_count = (TextView) findViewById(R.id.friend_squats_task_finish_count);
        friend_squats_task_finish_count_data = (TextView) findViewById(R.id.friend_squats_task_finish_count_data);

        squats_task_text_and = (TextView) findViewById(R.id.squats_task_text_and);
        squats_task_friend_point = (TextView) findViewById(R.id.squats_task_friend_point);
        squats_task_friend_point1 = (TextView) findViewById(R.id.squats_task_friend_point1);
        squats_task_friend_point2 = (TextView) findViewById(R.id.squats_task_friend_point2);
        confirm_squats_task_button = (Button) findViewById(R.id.confirm_squats_task_button);



        squats_task_data.setText("100");
        squats_task_seek_bar.setMax(Float.parseFloat(squats_task_data.getText().toString()));
        squats_susses_text_view.setText("目前沒有朋友");

        squats_task_myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                squats_task_my_name = dataSnapshot.child("name").getValue().toString();
                squats_task_my_image = dataSnapshot.child("thumb_image").getValue().toString();
                squats_task_my_count = dataSnapshot.child("exercise_count").child("squats").child("today_count").getValue().toString();
                squats_task_my_friend_point = dataSnapshot.child("friend_point").getValue().toString();

                squats_task_my_friend_point_int = Integer.parseInt(squats_task_my_friend_point);

                squats_data.setMy_task_friend_point(squats_task_my_friend_point_int);

                squats_task_my_count_int = Integer.parseInt(squats_task_my_count);
                GlobalVariable squats_today_count = (GlobalVariable) getApplicationContext();
                squats_today_count.setSquats_today_count(squats_task_my_count_int);
                task.setMy_squats_count(squats_task_my_count_int);
                squats_data.setMy_task_int_exercise_data(squats_task_my_count_int);

                my_squats_task_name.setText(squats_task_my_name);
                my_squats_task_finish_count_data.setText(squats_task_my_count + "次");

                if (squats_task_my_image.equals("default")) {
                    Picasso.get().load(R.drawable.default_avatar).into(my_squats_task_image);
                } else {
                    Picasso.get().load(squats_task_my_image).into(my_squats_task_image);
                }

                squats_task_Database.child("Squats_Task").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("12345", "" + myID);
                        if (dataSnapshot.hasChild(myID)) {
                            //mHanlder.postDelayed(task2,2000);
                            //squats_task_toolbar.setOnMenuItemClickListener(null);
                            final String list_user_id = dataSnapshot.child(myID).child("id").getValue().toString();
                            task.setChat_id_send(list_user_id);
                            squats_task_text_and.setVisibility(View.VISIBLE);
                            friend_squats_task_name.setVisibility(View.VISIBLE);
                            friend_squats_task_image.setVisibility(View.VISIBLE);
                            friend_squats_task_finish_count.setVisibility(View.VISIBLE);
                            friend_squats_task_finish_count_data.setVisibility(View.VISIBLE);

                            squats_task_friendDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (squats_task_text_and.getVisibility() == View.VISIBLE) {
                                        squats_task_friend_name = dataSnapshot.child("name").getValue().toString();
                                        squats_task_friend_image = dataSnapshot.child("thumb_image").getValue().toString();
                                        squats_task_friend_count = dataSnapshot.child("exercise_count").child("squats").child("today_count").getValue().toString();
                                        squats_task_friend_count_int = Integer.parseInt(squats_task_friend_count);
                                        task.setFriend_squats_count(squats_task_friend_count_int);
                                        friend_squats_task_name.setText(squats_task_friend_name);
                                        friend_squats_task_finish_count_data.setText(squats_task_friend_count + "次");


                                        if (squats_task_friend_image.equals("default")) {
                                            Picasso.get().load(R.drawable.default_avatar).into(friend_squats_task_image);
                                        } else {
                                            Picasso.get().load(squats_task_friend_image).into(friend_squats_task_image);
                                        }

                                        squats_progress = squats_task_friend_count_int + squats_data.getMy_task_int_exercise_data();
                                        Log.i("進度條的進度", "" + squats_progress);

                                        squats_task_data_int = Integer.parseInt(squats_task_data.getText().toString());
                                        Log.i("仰臥起坐共同任務運動量", "" + squats_task_data_int);
                                        if (squats_progress >= squats_task_data_int) {
                                            squats_task_seek_bar.setProgress((float) squats_task_data_int);
                                            squats_susses_text_view.setText("你們已經完成");
                                            squats_task_friend_point.setVisibility(View.VISIBLE);
                                            squats_task_friend_point1.setVisibility(View.VISIBLE);
                                            squats_task_friend_point2.setVisibility(View.VISIBLE);
                                            confirm_squats_task_button.setVisibility(View.VISIBLE);
                                            squats_susses_text_view_data.setVisibility(View.GONE);
                                            confirm_squats_task_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    squats_task_text_and.setVisibility(View.INVISIBLE);
                                                    friend_squats_task_name.setVisibility(View.INVISIBLE);
                                                    friend_squats_task_image.setVisibility(View.INVISIBLE);
                                                    friend_squats_task_finish_count.setVisibility(View.INVISIBLE);
                                                    friend_squats_task_finish_count_data.setVisibility(View.INVISIBLE);
                                                    squats_task_friend_point.setVisibility(View.INVISIBLE);
                                                    squats_task_friend_point1.setVisibility(View.INVISIBLE);
                                                    squats_task_friend_point2.setVisibility(View.INVISIBLE);
                                                    squats_task_Database.child("Squats_Task").child(myID).child("id").removeValue();
                                                    squats_task_friend_point_database.child("friend_point").setValue(squats_data.getMy_task_friend_point() + 10);

                                                    squats_susses_text_view.setText("目前沒有朋友");
                                                    squats_susses_text_view_data.setVisibility(View.GONE);
                                                    squats_task_seek_bar.setProgress((0));

                                                    squats_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
                                                    confirm_squats_task_button.setVisibility(View.INVISIBLE);
                                                    Intent intent = new Intent(Squats_task.this, Exercise_main.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else if (squats_progress < squats_task_data_int) {
                                            squats_susses_text_view.setText("目前完成");
                                            squats_susses_text_view_data.setVisibility(View.VISIBLE);
                                            squats_susses_text_view_data.setText(squats_progress + "次");
                                            squats_task_seek_bar.setProgress((float) squats_progress);
                                        }
                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if(from_page.equals("ExerciseActivity")){
                Intent intent = new Intent(Squats_task.this, Exercise_main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            if(from_page.equals("SimpleActivity")){
                Intent intent = new Intent(Squats_task.this, SimpleMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if(from_page.equals("ExerciseActivity")){
            Intent intent = new Intent(Squats_task.this, Exercise_main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if(from_page.equals("SimpleActivity")){
            Intent intent = new Intent(Squats_task.this, SimpleMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.task_friend:
                    Intent intent = new Intent(Squats_task.this, SquatsTaskFriend.class);
                    //intent.putExtra("Task_req","Task_req_squats");
                    //intent.putExtra("Task","Squats_Task");
                    //squats_task_myDatabase.child("exercise_count").child("squats").child("today_count").setValue(57);
                    startActivity(intent);
                    Log.i("點擊", "成功");
                    break;
                case R.id.gear_fit:
                    connect_squats();
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

    private final HealthDataStore.ConnectionListener mConnectionListener = new HealthDataStore.ConnectionListener() {

        @Override
        public void onConnected() {
            Log.d(APP_TAG, "健康數據服務已連接。");
            HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
            //mReporter = new StepCountReporter(mStore);
            cReporter = new SquatsTaskReporter(mStore);

            try {
                // 檢查是否獲取了此應用程序所需的權限
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

                if (resultMap.containsValue(Boolean.FALSE)) {
                    //如果未獲取，則請求讀取步數的權限
                    pmsManager.requestPermissions(mKeySet, Squats_task.this).setResultListener(mPermissionListener);
                } else {
                    //獲取當前步數並顯示它

                    cReporter.start();
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

                        cReporter.start();
                    }
                }
            };
    public void drawSquatsTask(int squats_count) {
        if (squats_count != 0) {
            Log.i("深蹲當下記錄",""+squats_count);
            GlobalVariable squats_today_count = (GlobalVariable) getApplicationContext();
            Log.i("深蹲今天記錄",""+squats_today_count.getSquats_today_count());
            int now_today_count=squats_count+squats_today_count.getSquats_today_count();
            squats_task_myDatabase.child("exercise_count").child("squats").child("today_count").setValue(now_today_count);

        }

    }
    public static  Squats_task getInstance(){
        return mInstance;
    }
    private void showPermissionAlarmDialog() {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(Squats_task.this);
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

    public void connect_squats(){
        mStore = new HealthDataStore(this, mConnectionListener);
        mStore.connectService();
    }
}
