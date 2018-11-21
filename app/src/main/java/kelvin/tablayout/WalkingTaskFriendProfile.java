package kelvin.tablayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WalkingTaskFriendProfile extends AppCompatActivity {
    private Toolbar walking_task_friend_profile_toolbar;
    public static ActionBar actionBar;
    private FirebaseAuth mAuth;
    private ImageView mProfileImage;
    public static TextView mProfileName;
    private Button mProfileSendReqBtn,delete_send_req_btn;
    private ProgressDialog mProgressDialog;
    private DatabaseReference ReqWalkingTaskDatabase;
    private DatabaseReference WalkingTaskDatabase;
    private DatabaseReference myUsersDatabase;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mRootRef;
    public static String my_image;
    private static FirebaseUser mCurrent_user;
    private String task_state;
    public String Fid;
    public static String my_name;
    public static String Walking_Task_Req="Walking_Task_Req";
    public static String Walking_Task="Walking_Task";
    public String Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_task_friend_profile);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        walking_task_friend_profile_toolbar=(Toolbar)findViewById(R.id.walking_task_friend_profile_toolbar);
        setSupportActionBar(walking_task_friend_profile_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("朋友資訊");
        actionBar.setDisplayHomeAsUpEnabled(true);
        final String friend_id=getIntent().getStringExtra("user_id");

        Log.i("朋友id",friend_id);



        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(friend_id);//朋友資料庫資料庫
        myUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");//使用者資料庫
        ReqWalkingTaskDatabase=FirebaseDatabase.getInstance().getReference().child(Walking_Task_Req);//不行邀請邀請資料庫
        WalkingTaskDatabase=FirebaseDatabase.getInstance().getReference().child(Walking_Task);//不行邀請資料庫
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();//得到自己的會員id
        mProfileImage = (ImageView) findViewById(R.id.walking_task_friend_profile_image);
        mProfileName = (TextView) findViewById(R.id.walking_task_friend_profile_displayName);
        mProfileSendReqBtn = (Button) findViewById(R.id.profile_send_req_btn);//發送朋友請求按鈕
        delete_send_req_btn=(Button)findViewById(R.id.delete_send_req_btn);

        task_state="還沒有發送";

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("加載用戶數據");
        mProgressDialog.setMessage("我們加載用戶數據時請稍候.");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String friendID=friend_id.toString();
                Log.i("朋友ID",friendID);
                String display_name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                mProfileName.setText(display_name);
                Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(mProfileImage);
                mProgressDialog.dismiss();
                myUsersDatabase.child(mCurrent_user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        my_name=dataSnapshot.child("name").getValue().toString();
                        my_image=dataSnapshot.child("thumb_image").getValue().toString();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //--------------------用自己的UID去不行邀請邀請資料庫查詢--------//
                ReqWalkingTaskDatabase.child(mCurrent_user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(friend_id)){
                            String req_type=dataSnapshot.child(friend_id).child("request_type").getValue().toString();

                            if(req_type.equals("接收")){
                                task_state="接收請求";
                                mProfileSendReqBtn.setText("接受不行共同任務");
                                delete_send_req_btn.setVisibility(View.VISIBLE);
                            }else if(req_type.equals("發送")){
                                task_state="發送請求";
                                mProfileSendReqBtn.setText("取消不行共同任務");
                            }

                        }else {

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
        WalkingTaskDatabase.child(mCurrent_user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("id")){
                    task_state="不行共同任務執行中";
                    mProfileSendReqBtn.setText("已接受不行共同任務");
                    Intent intent = new Intent(WalkingTaskFriendProfile.this,Walking_task.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mProfileSendReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfileSendReqBtn.setEnabled(false);
                Fid=friend_id;

                //---如果沒有請求就發送---//
                if(task_state.equals("還沒有發送")){
                    Map requestMap =new HashMap();
                    requestMap.put(""+Walking_Task_Req+"/" + mCurrent_user.getUid() + "/" + friend_id + "/request_type", "發送");
                    requestMap.put(""+Walking_Task_Req+"/" + friend_id + "/" + mCurrent_user.getUid() + "/request_type", "接收");

                    mRootRef.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError != null){

                                Toast.makeText(WalkingTaskFriendProfile.this, "發送請求時出錯", Toast.LENGTH_SHORT).show();
                            }else{
                                task_state = "發送請求";
                                mProfileSendReqBtn.setText("取消不行共同任務");
                            }

                            mProfileSendReqBtn.setEnabled(true);
                        }
                    });
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            int SDK_INT = android.os.Build.VERSION.SDK_INT;
                            if (SDK_INT > 8) {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                        .permitAll().build();
                                StrictMode.setThreadPolicy(policy);


                                try {
                                    String jsonResponse;

                                    URL url = new URL("https://onesignal.com/api/v1/notifications");
                                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                    con.setUseCaches(false);
                                    con.setDoOutput(true);
                                    con.setDoInput(true);

                                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                    con.setRequestProperty("Authorization", "Basic MDliZjEwOTItODYyOC00M2JhLWFjZjktNWFlNDIxNjY2OTdl");
                                    con.setRequestMethod("POST");


                                    String strJsonBody = "{"
                                            + "\"app_id\": \"04904fc0-8d20-4c22-be79-77da6073d641\","
                                            + "\"large_icon\": \""+my_image.toString()+"\","

                                            + "\"filters\": [{\"field\": \"tag\", \"key\": \"Uid\", \"relation\": \"=\", \"value\": \""+Fid+"\"}],"
                                            + "\"data\": {\"activityToBeOpened\":\"WalkingTaskFriendProfile\",\"user_id\": \""+mAuth.getCurrentUser().getUid()+"\"},"
                                            + "\"contents\": {\"en\": \"Friend req\",\"zh-Hant\": \""+my_name.toString()+"發送不行共同任務給你"+"\"}"
                                            + "}";

                                    System.out.println("strJsonBody:\n" + strJsonBody);

                                    byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                                    con.setFixedLengthStreamingMode(sendBytes.length);

                                    OutputStream outputStream = con.getOutputStream();
                                    outputStream.write(sendBytes);

                                    int httpResponse = con.getResponseCode();
                                    System.out.println("httpResponse: " + httpResponse);

                                    if (httpResponse >= HttpURLConnection.HTTP_OK
                                            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                        scanner.close();
                                    } else {
                                        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                        scanner.close();
                                    }
                                    System.out.println("jsonResponse:\n" + jsonResponse);

                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                            }
                        }
                    });
                }

                //----取消發送不行邀請請求----//
                if(task_state.equals("發送請求")){
                    ReqWalkingTaskDatabase.child(mCurrent_user.getUid()).child(friend_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ReqWalkingTaskDatabase.child(friend_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProfileSendReqBtn.setEnabled(true);
                                    task_state="還沒有發送";
                                    mProfileSendReqBtn.setText("發送不行共同任務");
                                }
                            });
                        }
                    });
                    /*AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            int SDK_INT = android.os.Build.VERSION.SDK_INT;
                            if (SDK_INT > 8) {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                        .permitAll().build();
                                StrictMode.setThreadPolicy(policy);


                                try {
                                    String jsonResponse;

                                    URL url = new URL("https://onesignal.com/api/v1/notifications");
                                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                    con.setUseCaches(false);
                                    con.setDoOutput(true);
                                    con.setDoInput(true);

                                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                    con.setRequestProperty("Authorization", "Basic MDliZjEwOTItODYyOC00M2JhLWFjZjktNWFlNDIxNjY2OTdl");
                                    con.setRequestMethod("POST");


                                    String strJsonBody = "{"
                                            + "\"app_id\": \"04904fc0-8d20-4c22-be79-77da6073d641\","
                                            + "\"large_icon\": \""+my_image.toString()+"\","

                                            + "\"filters\": [{\"field\": \"tag\", \"key\": \"Uid\", \"relation\": \"=\", \"value\": \""+Fid+"\"}],"
                                            + "\"data\": {\"activityToBeOpened\":\"WalkingTaskFriendProfile\",\"user_id\": \""+mAuth.getCurrentUser().getUid()+"\"},"
                                            + "\"contents\": {\"en\": \"Friend req\",\"zh-Hant\": \""+my_name.toString()+"拒絕你的不行邀請"+"\"}"
                                            + "}";

                                    System.out.println("strJsonBody:\n" + strJsonBody);

                                    byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                                    con.setFixedLengthStreamingMode(sendBytes.length);

                                    OutputStream outputStream = con.getOutputStream();
                                    outputStream.write(sendBytes);

                                    int httpResponse = con.getResponseCode();
                                    System.out.println("httpResponse: " + httpResponse);

                                    if (httpResponse >= HttpURLConnection.HTTP_OK
                                            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                        scanner.close();
                                    } else {
                                        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                        scanner.close();
                                    }
                                    System.out.println("jsonResponse:\n" + jsonResponse);

                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                            }
                        }
                    });*/
                }
                //-----接收不行邀請請求---//
                if(task_state.equals("接收請求")){
                    delete_send_req_btn.setVisibility(View.INVISIBLE);
                    final String currentDate = Time.getToDate(System.currentTimeMillis());

                    Map friendsMap = new HashMap();

                    friendsMap.put(""+Walking_Task+"/" + mCurrent_user.getUid() + "/id", friend_id);
                    friendsMap.put(""+Walking_Task+"/" + friend_id + "/id", mCurrent_user.getUid());

                    friendsMap.put(""+Walking_Task_Req+"/" + mCurrent_user.getUid() + "/" +friend_id, null);
                    friendsMap.put(""+Walking_Task_Req+"/" + friend_id + "/" + mCurrent_user.getUid(), null);

                    mRootRef.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError == null){

                                mProfileSendReqBtn.setEnabled(true);
                                task_state = "不行共同任務執行中";
                                mProfileSendReqBtn.setText("已接受不行共同任務");
                                Intent intent = new Intent(WalkingTaskFriendProfile.this,Walking_task.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                finish();
                                startActivity(intent);
                            } else {

                                String error = databaseError.getMessage();
                                Toast.makeText(WalkingTaskFriendProfile.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /*AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            int SDK_INT = android.os.Build.VERSION.SDK_INT;
                            if (SDK_INT > 8) {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                        .permitAll().build();
                                StrictMode.setThreadPolicy(policy);


                                try {
                                    String jsonResponse;

                                    URL url = new URL("https://onesignal.com/api/v1/notifications");
                                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                    con.setUseCaches(false);
                                    con.setDoOutput(true);
                                    con.setDoInput(true);

                                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                    con.setRequestProperty("Authorization", "Basic MDliZjEwOTItODYyOC00M2JhLWFjZjktNWFlNDIxNjY2OTdl");
                                    con.setRequestMethod("POST");


                                    String strJsonBody = "{"
                                            + "\"app_id\": \"04904fc0-8d20-4c22-be79-77da6073d641\","
                                            + "\"large_icon\": \""+my_image.toString()+"\","

                                            + "\"filters\": [{\"field\": \"tag\", \"key\": \"Uid\", \"relation\": \"=\", \"value\": \""+Fid+"\"}],"
                                            + "\"android_sound\": \"messagechat\","
                                            + "\"data\": {\"activityToBeOpened\":\"WalkingTaskFriendProfile\",\"user_id\": \""+mAuth.getCurrentUser().getUid()+"\"},"
                                            + "\"contents\": {\"en\": \"Friend req\",\"zh-Hant\": \""+my_name.toString()+"接受你的不行邀請給你"+"\"}"
                                            + "}";

                                    System.out.println("strJsonBody:\n" + strJsonBody);

                                    byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                                    con.setFixedLengthStreamingMode(sendBytes.length);

                                    OutputStream outputStream = con.getOutputStream();
                                    outputStream.write(sendBytes);

                                    int httpResponse = con.getResponseCode();
                                    System.out.println("httpResponse: " + httpResponse);

                                    if (httpResponse >= HttpURLConnection.HTTP_OK
                                            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                        scanner.close();
                                    } else {
                                        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                        scanner.close();
                                    }
                                    System.out.println("jsonResponse:\n" + jsonResponse);

                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                            }
                        }
                    });*/

                }

                //----取消不行邀請---///
                if(task_state.equals("不行共同任務執行中")){
                    Map unfriendMap = new HashMap();
                    unfriendMap.put(""+Walking_Task+"/" + mCurrent_user.getUid() + "/" + friend_id, null);
                    unfriendMap.put(""+Walking_Task+"/" + friend_id + "/" + mCurrent_user.getUid(), null);

                    mRootRef.updateChildren(unfriendMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                            if(databaseError == null){

                                task_state = "還沒有發送";
                                mProfileSendReqBtn.setText("發送不行共同任務請求");
                            }else {

                                String error = databaseError.getMessage();
                                Toast.makeText(WalkingTaskFriendProfile.this, error, Toast.LENGTH_SHORT).show();
                            }
                            mProfileSendReqBtn.setEnabled(true);
                        }
                    });
                    /*AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            int SDK_INT = android.os.Build.VERSION.SDK_INT;
                            if (SDK_INT > 8) {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                        .permitAll().build();
                                StrictMode.setThreadPolicy(policy);


                                try {
                                    String jsonResponse;

                                    URL url = new URL("https://onesignal.com/api/v1/notifications");
                                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                    con.setUseCaches(false);
                                    con.setDoOutput(true);
                                    con.setDoInput(true);

                                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                    con.setRequestProperty("Authorization", "Basic MDliZjEwOTItODYyOC00M2JhLWFjZjktNWFlNDIxNjY2OTdl");
                                    con.setRequestMethod("POST");


                                    String strJsonBody = "{"
                                            + "\"app_id\": \"04904fc0-8d20-4c22-be79-77da6073d641\","
                                            + "\"large_icon\": \""+my_image.toString()+"\","

                                            + "\"filters\": [{\"field\": \"tag\", \"key\": \"Uid\", \"relation\": \"=\", \"value\": \""+Fid+"\"}],"
                                            + "\"data\": {\"activityToBeOpened\":\"WalkingTaskFriendProfile\",\"user_id\": \""+mAuth.getCurrentUser().getUid()+"\"},"
                                            + "\"contents\": {\"en\": \"Friend req\",\"zh-Hant\": \""+my_name.toString()+"取消和你的不行邀請"+"\"}"
                                            + "}";

                                    System.out.println("strJsonBody:\n" + strJsonBody);

                                    byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                                    con.setFixedLengthStreamingMode(sendBytes.length);

                                    OutputStream outputStream = con.getOutputStream();
                                    outputStream.write(sendBytes);

                                    int httpResponse = con.getResponseCode();
                                    System.out.println("httpResponse: " + httpResponse);

                                    if (httpResponse >= HttpURLConnection.HTTP_OK
                                            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                        scanner.close();
                                    } else {
                                        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                        scanner.close();
                                    }
                                    System.out.println("jsonResponse:\n" + jsonResponse);

                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                            }
                        }
                    });*/
                }
            }
        });

        delete_send_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fid=friend_id;
                mProfileSendReqBtn.setEnabled(false);

                Map friendsMap = new HashMap();
                friendsMap.put(""+Walking_Task_Req+"/" + mCurrent_user.getUid() + "/" + friend_id, null);
                friendsMap.put(""+Walking_Task_Req+"/" + friend_id + "/" + mCurrent_user.getUid(), null);

                mRootRef.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError==null){
                            mProfileSendReqBtn.setEnabled(true);
                            task_state = "還沒有發送";
                            mProfileSendReqBtn.setText("發送不行共同任務請求");
                            delete_send_req_btn.setVisibility(View.INVISIBLE);
                        }else {
                            String error = databaseError.getMessage();
                            Toast.makeText(WalkingTaskFriendProfile.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                /*AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);


                            try {
                                String jsonResponse;

                                URL url = new URL("https://onesignal.com/api/v1/notifications");
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setUseCaches(false);
                                con.setDoOutput(true);
                                con.setDoInput(true);

                                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                con.setRequestProperty("Authorization", "Basic MDliZjEwOTItODYyOC00M2JhLWFjZjktNWFlNDIxNjY2OTdl");
                                con.setRequestMethod("POST");


                                String strJsonBody = "{"
                                        + "\"app_id\": \"04904fc0-8d20-4c22-be79-77da6073d641\","
                                        + "\"large_icon\": \""+my_image.toString()+"\","
                                        + "\"small_icon\": \""+R.drawable.testicon +"\","
                                        + "\"filters\": [{\"field\": \"tag\", \"key\": \"Uid\", \"relation\": \"=\", \"value\": \""+Fid+"\"}],"
                                        + "\"android_sound\": \"messagechat\","
                                        + "\"data\": {\"activityToBeOpened\":\"WalkingTaskFriendProfile\",\"user_id\": \""+mAuth.getCurrentUser().getUid()+"\"},"
                                        + "\"contents\": {\"en\": \"Friend req\",\"zh-Hant\": \""+my_name.toString()+"拒絕你的不行邀請邀請"+"\"}"
                                        + "}";

                                System.out.println("strJsonBody:\n" + strJsonBody);

                                byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                                con.setFixedLengthStreamingMode(sendBytes.length);

                                OutputStream outputStream = con.getOutputStream();
                                outputStream.write(sendBytes);

                                int httpResponse = con.getResponseCode();
                                System.out.println("httpResponse: " + httpResponse);

                                if (httpResponse >= HttpURLConnection.HTTP_OK
                                        && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                    Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                    scanner.close();
                                } else {
                                    Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                    scanner.close();
                                }
                                System.out.println("jsonResponse:\n" + jsonResponse);

                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    }
                });*/

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(WalkingTaskFriendProfile.this,WalkingTaskFriend.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            finish();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(WalkingTaskFriendProfile.this,WalkingTaskFriend.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intent);
    }
}
