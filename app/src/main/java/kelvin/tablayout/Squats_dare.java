package kelvin.tablayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Squats_dare extends AppCompatActivity {
    private Toolbar squats_dare_app_bar;
    public static ActionBar actionBar;
    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    private final int MENU_ITEM_PERMISSION_SETTING = 1;
    public static final String APP_TAG = "Sport";
    private static Squats_dare mInstance = null;
    private SquatsDareReporter cReporter;

    private DatabaseReference dareDatabase;
    private DatabaseReference friendDatabase;
    private DatabaseReference myDatabase;
    private DatabaseReference confirm_database;
    private DatabaseReference friend_point_database;
    private DatabaseReference clear_dareDatabase;


    private static FirebaseAuth mAuth;
    private static TextView exercise_week_data,user_single_name,squats_finish_time_data,squats_finish_count_data;
    private static TextView friend_single_name,friend_finish_time_data,friend_squats_finish_count_data;
    private static TextView text_VS,text_winner;
    private static TextView friend_finish_time,friend_squats_finish_count;
    private static Button confirm_dare;
    private CircleImageView mDisplayImage,friend_single_image;
    private static String myName,myImage,myFinishTime,myCount,friend_point;
    private static String friendName,friendImage,friendFinishTime,friendCount;
    public Data squats_dare_data=new Data();
    private static long myFinishTimeLong;
    private static int myCountInt;
    private static long FriendFinishTimeLong;
    private static int FriendCountInt;
    private static int Int_exercise_week_dat;
    private static int Int_friend_point;
    private String myID;
    private ImageView my_win_icon,friend_win_icon,win_arrow;
    private String from_page="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("我在Exercise_main的","OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squats_dare);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        Bundle bundle=getIntent().getExtras();
        from_page=bundle.getString("from_page");
        Log.i("OnCreate的From_page",from_page);
        squats_dare_app_bar = (Toolbar) findViewById(R.id.squats_dare_app_bar);
        setSupportActionBar(squats_dare_app_bar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("深蹲挑戰");
        GlobalVariable User = (GlobalVariable)getApplicationContext();
        Log.i("MainActivity的值",""+User.getTask());
        actionBar.setDisplayHomeAsUpEnabled(true);
        squats_dare_app_bar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();
        myID = mAuth.getCurrentUser().getUid();
        myDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        friend_point_database= FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        dareDatabase= FirebaseDatabase.getInstance().getReference();
        confirm_database= FirebaseDatabase.getInstance().getReference();
        friendDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        clear_dareDatabase= FirebaseDatabase.getInstance().getReference().child("Users");


        exercise_week_data=(TextView)findViewById(R.id.exercise_week_data);
        user_single_name=(TextView)findViewById(R.id.user_single_name);
        squats_finish_time_data=(TextView)findViewById(R.id.squats_finish_time_data);
        squats_finish_count_data=(TextView)findViewById(R.id.squats_finish_count_data);

        friend_single_name=(TextView)findViewById(R.id.friend_single_name);
        friend_finish_time_data=(TextView)findViewById(R.id.friend_finish_time_data);
        friend_squats_finish_count_data=(TextView)findViewById(R.id.friend_squats_finish_count_data);

        text_VS=(TextView)findViewById(R.id.text_VS);
        text_winner=(TextView)findViewById(R.id.text_winner);

        friend_finish_time=(TextView)findViewById(R.id.friend_finish_time);
        friend_squats_finish_count=(TextView)findViewById(R.id.friend_squats_finish_count);

        confirm_dare=(Button)findViewById(R.id.confirm_dare);
        my_win_icon=findViewById(R.id.my_win_icon);
        friend_win_icon=findViewById(R.id.friend_win_icon);
        win_arrow=findViewById(R.id.win_arrow);
        mDisplayImage = (CircleImageView) findViewById(R.id.user_single_image);
        friend_single_image = (CircleImageView) findViewById(R.id.friend_single_image);
        exercise_week_data.setText("20");
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myName=dataSnapshot.child("name").getValue().toString();
                myImage=dataSnapshot.child("thumb_image").getValue().toString();
                myFinishTime=dataSnapshot.child("squats_dare").child("time").getValue().toString();
                myCount=dataSnapshot.child("squats_dare").child("count").getValue().toString();
                friend_point=dataSnapshot.child("friend_point").getValue().toString();
                Int_friend_point=Integer.parseInt(friend_point);
                squats_dare_data.setSquats_dare_friend_point(Int_friend_point);

                myFinishTimeLong=Long.parseLong(myFinishTime);
                myCountInt=Integer.parseInt(myCount);
                user_single_name.setText(myName);
                squats_finish_count_data.setText(myCountInt+"次");
                squats_finish_time_data.setText(Time.changeYogaTime(myFinishTimeLong));
                squats_dare_data.setSquats_dare_myFinishTime(myFinishTimeLong);
                squats_dare_data.setSquats_dare_myCount(myCountInt);

                if(myImage.equals("default")){
                    Picasso.get().load(R.drawable.default_avatar).into(mDisplayImage);

                }else{
                    Picasso.get().load(myImage).into(mDisplayImage);
                }

                dareDatabase.child("Squats_Dare").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("12345",""+myID);
                        if(dataSnapshot.hasChild(myID)){
                            squats_dare_app_bar.setOnMenuItemClickListener(null);
                            final String list_user_id =dataSnapshot.child(myID).child("id").getValue().toString();
                            Log.i("朋友id1234",""+list_user_id);
                            text_VS.setVisibility(View.VISIBLE);
                            friend_single_image.setVisibility(View.VISIBLE);
                            friend_single_name.setVisibility(View.VISIBLE);
                            friend_finish_time.setVisibility(View.VISIBLE);
                            friend_finish_time_data.setVisibility(View.VISIBLE);
                            friend_squats_finish_count.setVisibility(View.VISIBLE);
                            friend_squats_finish_count_data.setVisibility(View.VISIBLE);

                            friendDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(text_VS.getVisibility()==View.VISIBLE){
                                        friendName=dataSnapshot.child("name").getValue().toString();
                                        friendImage=dataSnapshot.child("thumb_image").getValue().toString();
                                        friendFinishTime=dataSnapshot.child("squats_dare").child("time").getValue().toString();
                                        friendCount=dataSnapshot.child("squats_dare").child("count").getValue().toString();
                                        FriendFinishTimeLong=Long.parseLong(friendFinishTime);
                                        FriendCountInt=Integer.parseInt(friendCount);



                                        friend_single_name.setText(friendName);
                                        friend_finish_time_data.setText(Time.changeYogaTime(FriendFinishTimeLong));
                                        friend_squats_finish_count_data.setText(FriendCountInt+"次");

                                        if(friendImage.equals("default")){
                                            Picasso.get().load(R.drawable.default_avatar).into(friend_single_image);
                                        }else {
                                            Picasso.get().load(friendImage).into(friend_single_image);
                                        }
                                        Log.i("1",""+ squats_dare_data.getSquats_dare_myFinishTime());
                                        Log.i("12",""+ squats_dare_data.getSquats_dare_myCount());
                                        Log.i("123",""+ FriendFinishTimeLong);
                                        Log.i("1234",""+ FriendCountInt);
                                        Int_exercise_week_dat=Integer.parseInt(exercise_week_data.getText().toString());
                                        if(squats_dare_data.getSquats_dare_myCount()==Int_exercise_week_dat&&FriendCountInt==Int_exercise_week_dat&&squats_dare_data.getSquats_dare_myCount()!=0&&FriendCountInt!=0&&text_VS.getVisibility()==View.VISIBLE){

                                            if(squats_dare_data.getSquats_dare_myFinishTime()>FriendFinishTimeLong&&squats_dare_data.getSquats_dare_myFinishTime()!=0&&FriendFinishTimeLong!=0){
                                                mDisplayImage.setBorderWidth(0);
                                                text_winner.setVisibility(View.VISIBLE);
                                                confirm_dare.setVisibility(View.VISIBLE);
                                                my_win_icon.setVisibility(View.INVISIBLE);
                                                friend_win_icon.setVisibility(View.VISIBLE);
                                                friend_win_icon.animate().rotation(friend_win_icon.getRotation()+720).setDuration(5000).start();
                                                win_arrow.setVisibility(View.VISIBLE);
                                                win_arrow.setImageResource(R.drawable.right_arrow);
                                                //friend_single_image.setBorderWidth(20);
                                                //friend_single_image.setBorderColor(Color.parseColor("#ff0000"));
                                                //text_winner.setText("勝利方是朋友");
                                            }else if(squats_dare_data.getSquats_dare_myFinishTime()<FriendFinishTimeLong&&squats_dare_data.getSquats_dare_myFinishTime()!=0&&FriendFinishTimeLong!=0){
                                                friend_single_image.setBorderWidth(0);
                                                text_winner.setVisibility(View.VISIBLE);
                                                confirm_dare.setVisibility(View.VISIBLE);
                                                Log.i("我在這裡M","2");
                                                friend_win_icon.setVisibility(View.INVISIBLE);
                                                my_win_icon.setVisibility(View.VISIBLE);
                                                my_win_icon.animate().rotation(my_win_icon.getRotation()+720).setDuration(5000).start();
                                                win_arrow.setVisibility(View.VISIBLE);
                                                win_arrow.setImageResource(R.drawable.left_arrow);
                                                //mDisplayImage.setBorderWidth(20);
                                                //mDisplayImage.setBorderColor(Color.parseColor("#ff0000"));
                                                //text_winner.setText("勝利方是你");
                                                Log.i("你之前的friend_pint",""+squats_dare_data.getSquats_dare_friend_point());
                                            }


                                            confirm_dare.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    confirm_database.child("Squats_Dare").child(myID).child("id").setValue(null);
                                                    confirm_dare.setVisibility(View.INVISIBLE);
                                                    friend_single_image.setVisibility(View.INVISIBLE);
                                                    friend_single_name.setVisibility(View.INVISIBLE);
                                                    friend_finish_time.setVisibility(View.INVISIBLE);
                                                    friend_finish_time_data.setVisibility(View.INVISIBLE);
                                                    friend_squats_finish_count.setVisibility(View.INVISIBLE);
                                                    friend_squats_finish_count_data.setVisibility(View.INVISIBLE);
                                                    text_VS.setVisibility(View.INVISIBLE);
                                                    text_winner.setVisibility(View.INVISIBLE);
                                                    win_arrow.setVisibility(View.INVISIBLE);
                                                    myDatabase.child("squats_dare").child("time").setValue(0);
                                                    myDatabase.child("squats_dare").child("count").setValue(0);
                                                    if(squats_dare_data.getSquats_dare_myFinishTime()>FriendFinishTimeLong){
                                                        Log.i("勝利方是:","朋友");
                                                        Toast.makeText(Squats_dare.this,"朋友獲得10點friendpoint", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(Squats_dare.this, Exercise_main.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                        friend_win_icon.setVisibility(View.INVISIBLE);
                                                        my_win_icon.setVisibility(View.INVISIBLE);
                                                        startActivity(intent);

                                                    }else if(squats_dare_data.getSquats_dare_myFinishTime()<FriendFinishTimeLong){
                                                        Log.i("你之前的friend_pint",""+squats_dare_data.getSquats_dare_friend_point());
                                                        friend_point_database.child("friend_point").setValue(squats_dare_data.getSquats_dare_friend_point()+10);
                                                        Log.i("勝利方是:","你");
                                                        Toast.makeText(Squats_dare.this,"你獲得10點friendpoint", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(Squats_dare.this, Exercise_main.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                        friend_win_icon.setVisibility(View.INVISIBLE);
                                                        my_win_icon.setVisibility(View.INVISIBLE);
                                                        startActivity(intent);
                                                    }



                                                }
                                            });
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
        if(text_winner.getText().equals("勝利方是你")){
            Log.i("我在這裡M","3");
            mDisplayImage.setBorderWidth(5);
            mDisplayImage.setBorderColor(Color.parseColor("#73c4d9"));
        }
        if(text_winner.getText().equals("勝利方是朋友")){
            Log.i("我在這裡M","4");
            friend_single_image.setBorderWidth(5);
            friend_single_image.setBorderColor(Color.parseColor("#73c4d9"));
        }







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
                Intent intent = new Intent(Squats_dare.this, Exercise_main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            if(from_page.equals("SimpleActivity")){
                Intent intent = new Intent(Squats_dare.this, SimpleMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(from_page.equals("ExerciseActivity")){
            Intent intent = new Intent(Squats_dare.this, Exercise_main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if(from_page.equals("SimpleActivity")){
            Intent intent = new Intent(Squats_dare.this, SimpleMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    private final Toolbar.OnMenuItemClickListener onMenuItemClickListener;

    {
        onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.task_friend:
                        if(text_VS.getVisibility()==View.INVISIBLE) {
                            Intent intent = new Intent(Squats_dare.this, SquatsDareFriend.class);
                            startActivity(intent);
                            Log.i("點擊", "成功");
                        }
                        break;
                    case R.id.gear_fit:
                        connect_squats();
                        break;
                }
                return true;
            }
        };
    }

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
            cReporter = new SquatsDareReporter(mStore);

            try {
                // 檢查是否獲取了此應用程序所需的權限
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

                if (resultMap.containsValue(Boolean.FALSE)) {
                    //如果未獲取，則請求讀取步數的權限
                    pmsManager.requestPermissions(mKeySet, Squats_dare.this).setResultListener(mPermissionListener);
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

    public void drawSquatsDare(long squats_duration,int squats_count) {
        if (squats_count != 0) {
            myDatabase.child("squats_dare").child("count").setValue(squats_count);
            myDatabase.child("squats_dare").child("time").setValue(squats_duration);
        }

    }

    public static  Squats_dare getInstance(){
        return mInstance;
    }

    private void showPermissionAlarmDialog() {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(Squats_dare.this);
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

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("我在Squats_dare的","onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("我在Squats_dare的","onResume");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("我在Squats_dare的","onRestart");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("我在Squats_dare的","onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("我在Squats_dare的","onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("我在Squats_dare的","onDestroy");
    }
}
