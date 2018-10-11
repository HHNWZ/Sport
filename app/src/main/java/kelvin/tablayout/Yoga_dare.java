package kelvin.tablayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
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

public class Yoga_dare extends AppCompatActivity {
    private Toolbar yoga_dare_app_bar;
    public static ActionBar actionBar;
    private HealthDataStore mStore;
    private HealthConnectionErrorResult mConnError;
    private Set<HealthPermissionManager.PermissionKey> mKeySet;
    private final int MENU_ITEM_PERMISSION_SETTING = 1;
    public static final String APP_TAG = "Sport";
    private static Yoga_dare mInstance = null;
    private YogaDareReporter cReporter;

    private DatabaseReference dareDatabase;
    private DatabaseReference friendDatabase;
    private DatabaseReference myDatabase;
    private DatabaseReference confirm_database;
    private DatabaseReference friend_point_database;
    private DatabaseReference clear_dareDatabase;


    private static FirebaseAuth mAuth;
    private static TextView exercise_week_data,user_single_name,yoga_finish_time_data,yoga_finish_calorie_data;
    private static TextView friend_single_name,friend_finish_time_data,friend_yoga_finish_calorie_data;
    private static TextView text_VS,text_winner;
    private static TextView friend_finish_time,friend_yoga_finish_calorie;
    private static Button confirm_dare;
    private CircleImageView mDisplayImage,friend_single_image;
    private static String myName,myImage,myFinishTime,myCalorie,friend_point;
    private static String friendName,friendImage,friendFinishTime,friendCalorie;
    public Data yoga_dare_data=new Data();
    private static long myFinishTimeLong;
    private static int myCalorieInt;
    private static long FriendFinishTimeLong;
    private static int FriendCalorieInt;
    private static long Int_exercise_week_dat;
    private static int Int_friend_point;
    private static long Long_exercise_week_dat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_dare);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();

        yoga_dare_app_bar = (Toolbar) findViewById(R.id.yoga_dare_app_bar);
        setSupportActionBar(yoga_dare_app_bar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("瑜伽挑戰");
        actionBar.setDisplayHomeAsUpEnabled(true);
        yoga_dare_app_bar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        friend_point_database= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        dareDatabase= FirebaseDatabase.getInstance().getReference();
        confirm_database= FirebaseDatabase.getInstance().getReference();
        friendDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        clear_dareDatabase= FirebaseDatabase.getInstance().getReference().child("Users");


        exercise_week_data=(TextView)findViewById(R.id.exercise_week_data);
        user_single_name=(TextView)findViewById(R.id.user_single_name);
        yoga_finish_time_data=(TextView)findViewById(R.id.yoga_finish_time_data);
        yoga_finish_calorie_data=(TextView)findViewById(R.id.yoga_finish_calorie_data);

        friend_single_name=(TextView)findViewById(R.id.friend_single_name);
        friend_finish_time_data=(TextView)findViewById(R.id.friend_finish_time_data);
        friend_yoga_finish_calorie_data=(TextView)findViewById(R.id.friend_yoga_finish_calorie_data);

        text_VS=(TextView)findViewById(R.id.text_VS);
        text_winner=(TextView)findViewById(R.id.text_winner);

        friend_finish_time=(TextView)findViewById(R.id.friend_finish_time);
        friend_yoga_finish_calorie=(TextView)findViewById(R.id.friend_yoga_finish_calorie);

        confirm_dare=(Button)findViewById(R.id.confirm_dare);

        mDisplayImage = (CircleImageView) findViewById(R.id.user_single_image);
        friend_single_image = (CircleImageView) findViewById(R.id.friend_single_image);
        exercise_week_data.setText("3");
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myName=dataSnapshot.child("name").getValue().toString();
                myImage=dataSnapshot.child("thumb_image").getValue().toString();
                myFinishTime=dataSnapshot.child("yoga_dare").child("time").getValue().toString();
                myCalorie=dataSnapshot.child("yoga_dare").child("calorie").getValue().toString();
                friend_point=dataSnapshot.child("friend_point").getValue().toString();
                Int_friend_point=Integer.parseInt(friend_point);
                yoga_dare_data.setYoga_dare_friend_point(Int_friend_point);

                myFinishTimeLong=Long.parseLong(myFinishTime);
                myCalorieInt=Integer.parseInt(myCalorie);
                user_single_name.setText(myName);
                yoga_finish_calorie_data.setText(myCalorieInt+"卡路里");
                yoga_finish_time_data.setText(Time.changeYogaTime(myFinishTimeLong));
                yoga_dare_data.setYoga_dare_myFinishTime(myFinishTimeLong);
                yoga_dare_data.setYoga_dare_myCalorie(myCalorieInt);

                if(myImage.equals("default")){
                    Picasso.get().load(R.drawable.default_avatar).into(mDisplayImage);

                }else{
                    Picasso.get().load(myImage).into(mDisplayImage);
                }

                dareDatabase.child("Yoga_Dare").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("12345",""+mAuth.getCurrentUser().getUid());
                        if(dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){
                            yoga_dare_app_bar.setOnMenuItemClickListener(null);
                            final String list_user_id =dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("id").getValue().toString();
                            Log.i("朋友id1234",""+list_user_id);
                            text_VS.setVisibility(View.VISIBLE);
                            friend_single_image.setVisibility(View.VISIBLE);
                            friend_single_name.setVisibility(View.VISIBLE);
                            friend_finish_time.setVisibility(View.VISIBLE);
                            friend_finish_time_data.setVisibility(View.VISIBLE);
                            friend_yoga_finish_calorie.setVisibility(View.VISIBLE);
                            friend_yoga_finish_calorie_data.setVisibility(View.VISIBLE);

                            friendDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (text_VS.getVisibility() == View.VISIBLE) {
                                        friendName = dataSnapshot.child("name").getValue().toString();
                                        friendImage = dataSnapshot.child("thumb_image").getValue().toString();
                                        friendFinishTime = dataSnapshot.child("yoga_dare").child("time").getValue().toString();
                                        friendCalorie = dataSnapshot.child("yoga_dare").child("calorie").getValue().toString();
                                        FriendFinishTimeLong = Long.parseLong(friendFinishTime);
                                        FriendCalorieInt = Integer.parseInt(friendCalorie);


                                        friend_single_name.setText(friendName);
                                        friend_finish_time_data.setText(Time.changeYogaTime(FriendFinishTimeLong));
                                        friend_yoga_finish_calorie_data.setText(FriendCalorieInt + "卡路里");

                                        if (friendImage.equals("default")) {
                                            Picasso.get().load(R.drawable.default_avatar).into(friend_single_image);
                                        } else {
                                            Picasso.get().load(friendImage).into(friend_single_image);
                                        }
                                        Log.i("1", "" + yoga_dare_data.getYoga_dare_myFinishTime());
                                        Log.i("12", "" + yoga_dare_data.getYoga_dare_myCalorie());
                                        Log.i("123", "" + FriendFinishTimeLong);
                                        Log.i("1234", "" + FriendCalorieInt);
                                        Int_exercise_week_dat = Long.parseLong(exercise_week_data.getText().toString()) * 60 * 1000;

                                        if (yoga_dare_data.getYoga_dare_myFinishTime() == Int_exercise_week_dat && FriendFinishTimeLong == Int_exercise_week_dat && yoga_dare_data.getYoga_dare_myCalorie() != 0 && FriendCalorieInt != 0 && text_VS.getVisibility() == View.VISIBLE) {
                                            text_winner.setVisibility(View.VISIBLE);
                                            if (yoga_dare_data.getYoga_dare_myCalorie() < FriendCalorieInt) {
                                                text_winner.setText("勝利方是朋友");
                                            } else if (yoga_dare_data.getYoga_dare_myCalorie() > FriendCalorieInt) {
                                                text_winner.setText("勝利方是你");
                                                Log.i("你之前的friend_pint", "" + yoga_dare_data.getYoga_dare_friend_point());
                                            }
                                            confirm_dare.setVisibility(View.VISIBLE);
                                            confirm_dare.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    confirm_database.child("Yoga_Dare").child(mAuth.getCurrentUser().getUid()).child("id").setValue(null);
                                                    confirm_dare.setVisibility(View.INVISIBLE);
                                                    friend_single_image.setVisibility(View.INVISIBLE);
                                                    friend_single_name.setVisibility(View.INVISIBLE);
                                                    friend_finish_time.setVisibility(View.INVISIBLE);
                                                    friend_finish_time_data.setVisibility(View.INVISIBLE);
                                                    friend_yoga_finish_calorie.setVisibility(View.INVISIBLE);
                                                    friend_yoga_finish_calorie_data.setVisibility(View.INVISIBLE);
                                                    text_VS.setVisibility(View.INVISIBLE);
                                                    text_winner.setVisibility(View.INVISIBLE);
                                                    if (yoga_dare_data.getYoga_dare_myCalorie() < FriendCalorieInt) {
                                                        Log.i("勝利方是:", "朋友");
                                                        Toast.makeText(Yoga_dare.this, "朋友獲得10點friendpoint", Toast.LENGTH_SHORT).show();
                                                    } else if (yoga_dare_data.getYoga_dare_myCalorie() > FriendCalorieInt) {
                                                        Log.i("你之前的friend_pint", "" + yoga_dare_data.getYoga_dare_friend_point());
                                                        friend_point_database.child("friend_point").setValue(yoga_dare_data.getYoga_dare_friend_point() + 10);
                                                        Log.i("勝利方是:", "你");
                                                        Toast.makeText(Yoga_dare.this, "你獲得10點friendpoint", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(Yoga_dare.this, Exercise_main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private final Toolbar.OnMenuItemClickListener onMenuItemClickListener;

    {
        onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.task_friend:
                        if(text_VS.getVisibility()==View.INVISIBLE) {
                            Intent intent = new Intent(Yoga_dare.this, YogaDareFriend.class);
                            startActivity(intent);
                            Log.i("點擊", "成功");
                        }
                        break;
                    case R.id.gear_fit:
                        connect_yoga();
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
            //mReporter = new StepCalorieReporter(mStore);
            cReporter = new YogaDareReporter(mStore);

            try {
                // 檢查是否獲取了此應用程序所需的權限
                Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(mKeySet);

                if (resultMap.containsValue(Boolean.FALSE)) {
                    //如果未獲取，則請求讀取步數的權限
                    pmsManager.requestPermissions(mKeySet, Yoga_dare.this).setResultListener(mPermissionListener);
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

    public void drawYogaDare(long yoga_duration,int yoga_calorie) {
        if (yoga_calorie != 0) {
            myDatabase.child("yoga_dare").child("calorie").setValue(yoga_calorie);
            myDatabase.child("yoga_dare").child("time").setValue(yoga_duration);
        }

    }

    public static  Yoga_dare getInstance(){
        return mInstance;
    }

    private void showPermissionAlarmDialog() {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(Yoga_dare.this);
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

    public void connect_yoga(){
        mStore = new HealthDataStore(this, mConnectionListener);
        mStore.connectService();
    }
    
}
