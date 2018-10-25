package kelvin.tablayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private String mChatUser;
    private String exercise_type;
    private String exercise_data_count;
    private String exercise_data;
    private String exercise_unit;
    private String start_year_of_invitation;
    private String start_month_of_invitation;
    private String start_day_of_invitation;
    private String start_hour_of_invitation;
    private String start_minute_of_invitation;
    private String end_year_of_invitation;
    private String end_month_of_invitation;
    private String end_day_of_invitation;
    private String end_hour_of_invitation;
    private String end_minute_of_invitation;
    private static String online;
    private static String button_online;
    private String mCurrentUserId;
    private String Uid;
    public static String my_name;
    public static String image;
    public static String my_image;

    private Toolbar mChatToolbar;

    private DatabaseReference mRootRef;
    private DatabaseReference mUserRef;
    private DatabaseReference myUsersDatabase;

    private FirebaseAuth mAuth;

    private TextView mTitleView;
    private TextView mLastSeenView;

    private CircleImageView mProfileImage;

    private ImageButton mChatAddBtn;
    private ImageButton mChatSendBtn;

    private EditText mChatMessageView;


    private RecyclerView mMessagesList;
    private SwipeRefreshLayout mRefreshLayout;

    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mAdapter;

    private static final int TOTAL_ITEMS_TO_LOAD = 10;
    private int mCurrentPage = 1;

    private static final int GALLERY_PICK = 1;

    // Storage Firebase
    private StorageReference mImageStorage;

    //New Solution
    private int itemPos = 0;

    private String mLastKey = "";
    private String mPrevKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();

        mChatToolbar = (Toolbar) findViewById(R.id.chat_app_bar);
        setSupportActionBar(mChatToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mUserRef= FirebaseDatabase.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();//自己的id
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());//讀取自己的資料
        mChatUser = getIntent().getStringExtra("user_id");//對方的id

        exercise_type=getIntent().getStringExtra("exercise_type");
        exercise_data_count=getIntent().getStringExtra("exercise_data_count");
        exercise_data=getIntent().getStringExtra("exercise_data");
        exercise_unit=getIntent().getStringExtra("exercise_unit");
        start_year_of_invitation=getIntent().getStringExtra("start_year_of_invitation");
        start_month_of_invitation=getIntent().getStringExtra("start_month_of_invitation");
        start_day_of_invitation=getIntent().getStringExtra("start_day_of_invitation");
        start_hour_of_invitation=getIntent().getStringExtra("start_hour_of_invitation");
        start_minute_of_invitation=getIntent().getStringExtra("start_minute_of_invitation");
        end_year_of_invitation=getIntent().getStringExtra("end_year_of_invitation");
        end_month_of_invitation=getIntent().getStringExtra("end_month_of_invitation");
        end_day_of_invitation=getIntent().getStringExtra("end_day_of_invitation");
        end_hour_of_invitation=getIntent().getStringExtra("end_hour_of_invitation");
        end_minute_of_invitation=getIntent().getStringExtra("end_minute_of_invitation");

        Log.i("錯誤1","錯誤1");
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//查找xml文件並套用
        View action_bar_view = inflater.inflate(R.layout.chat_custom_bar, null);

        actionBar.setCustomView(action_bar_view);

        // ---- Custom Action bar Items ----

        mTitleView = (TextView) findViewById(R.id.custom_bar_title);
        mLastSeenView = (TextView) findViewById(R.id.custom_bar_seen);
        mProfileImage = (CircleImageView) findViewById(R.id.custom_bar_image);

        mChatAddBtn = (ImageButton) findViewById(R.id.chat_add_btn);
        mChatSendBtn = (ImageButton) findViewById(R.id.chat_send_btn);
        mChatMessageView = (EditText) findViewById(R.id.chat_message_view);

        mAdapter = new MessageAdapter(messagesList);

        mMessagesList = (RecyclerView) findViewById(R.id.messages_list);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_swipe_layout);
        mLinearLayout = new LinearLayoutManager(ChatActivity.this);

        Log.i("錯誤2","錯誤2");
        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);

        mMessagesList.setAdapter(mAdapter);

        //------- IMAGE STORAGE ---------
        mImageStorage = FirebaseStorage.getInstance().getReference();

        mRootRef.child("Chat").child(mCurrentUserId).child(mChatUser).child("seen").setValue(true);//初始對方的信息
        mUserRef.child("Users");
        myUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");//使用者資料庫

        loadMessages();//載入資料
        if(exercise_data!=null){
            mChatMessageView.setText(exercise_type+exercise_data+exercise_unit+"\n"+start_year_of_invitation+"年"+start_month_of_invitation+"月"+start_day_of_invitation+"號 "+start_hour_of_invitation+":"+start_minute_of_invitation+"\n"+end_year_of_invitation+"年"+end_month_of_invitation+"月"+end_day_of_invitation+"號 "+end_hour_of_invitation+":"+end_minute_of_invitation);
        }else {
            mChatMessageView.setText("");
        }
        myUsersDatabase.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                my_name=dataSnapshot.child("name").getValue().toString();
                my_image=dataSnapshot.child("thumb_image").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRootRef.child("Users").child(mChatUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                online = dataSnapshot.child("online").getValue().toString();
                image = dataSnapshot.child("image").getValue().toString();
                String userName= dataSnapshot.child("name").getValue().toString();
                Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(mProfileImage);
                mTitleView.setText(userName);
                if(online.equals("true")) {

                    mLastSeenView.setText("在線");
                } else {

                    GetTimeAgo getTimeAgo = new GetTimeAgo();

                    long lastTime = Long.parseLong(online);

                    String lastSeenTime =getTimeAgo.getTimeAgo(lastTime, getApplicationContext());
                    mLastSeenView.setText(lastSeenTime);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mRootRef.child("Chat").child(mCurrentUserId).addValueEventListener(new ValueEventListener() {//用自己的id在chat找
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChild(mChatUser)){//如果有對方的id存在

                    Map chatAddMap = new HashMap();
                    chatAddMap.put("seen", false);
                    chatAddMap.put("timestamp", ServerValue.TIMESTAMP);

                    Map chatUserMap = new HashMap();
                    chatUserMap.put("Chat/" + mCurrentUserId + "/" + mChatUser, chatAddMap);//判斷對方有沒有看到
                    chatUserMap.put("Chat/" + mChatUser + "/" + mCurrentUserId, chatAddMap);

                    mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError != null){

                                Log.d("CHAT_LOG", databaseError.getMessage().toString());

                            }

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uid=mChatUser;
                button_online=online;
                //myUsersDatabase.child(mAuth.getCurrentUser().getUid()).child("last_message_time").child(Uid).setValue(System.currentTimeMillis());
                if(button_online.equals("true")){

                }else {
                    //Toast.makeText(ChatActivity.this, Uid, Toast.LENGTH_SHORT).show();
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
                                    Log.i("image",image.toString());
                                    String strJsonBody = "{"
                                            + "\"app_id\": \"04904fc0-8d20-4c22-be79-77da6073d641\","
                                            + "\"large_icon\": \""+my_image.toString()+"\","
                                            + "\"small_icon\": \""+R.drawable.testicon +"\","
                                            + "\"filters\": [{\"field\": \"tag\", \"key\": \"Uid\", \"relation\": \"=\", \"value\": \""+Uid+"\"}],"
                                            + "\"android_background_layout\": " +
                                            "{\"headings_color\": \"9C27B0\"," +
                                            "\"contents_color\": \"00695C\"},"
                                            + "\"data\": {\"activityToBeOpened\":\"ChatActivity\",\"user_id\": \""+mAuth.getCurrentUser().getUid()+"\"},"
                                            + "\"contents\": {\"en\": \"You have new message\",\"zh-Hant\": \""+my_name.toString()+"發信息給你"+"\"}"
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

                sendMessage();//發送信息

            }
        });



        mChatAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

            }
        });



        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mCurrentPage++;

                itemPos = 0;

                loadMoreMessages();


            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        mUserRef.child("online").setValue("true");



    }


    @Override
    protected void onStop() {
        super.onStop();

        mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            Uri imageUri = data.getData();

            final String current_user_ref = "messages/" + mCurrentUserId + "/" + mChatUser;//自己的id先在到對方id
            final String chat_user_ref = "messages/" + mChatUser + "/" + mCurrentUserId;//對方的id在到自己的id

            DatabaseReference user_message_push = mRootRef.child("messages")
                    .child(mCurrentUserId).child(mChatUser).push();//用自己的id在去差子節點別人的id

            final String push_id = user_message_push.getKey();//唯一值


            StorageReference filepath = mImageStorage.child("message_images").child( push_id + ".jpg");//雲端存資料庫

            filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){

                        String download_url = task.getResult().getDownloadUrl().toString();

                        Map mymessageMap = new HashMap();
                        mymessageMap.put("message", download_url);
                        mymessageMap.put("seen", false);
                        mymessageMap.put("type", "image");
                        mymessageMap.put("time", ServerValue.TIMESTAMP);
                        mymessageMap.put("from", mCurrentUserId);
                        mymessageMap.put("sendType","send");

                        Map messageMap = new HashMap();
                        messageMap.put("message", download_url);
                        messageMap.put("seen", false);
                        messageMap.put("type", "image");
                        messageMap.put("time", ServerValue.TIMESTAMP);
                        messageMap.put("from", mCurrentUserId);
                        messageMap.put("sendType","received");

                        Map messageUserMap = new HashMap();
                        messageUserMap.put(current_user_ref + "/" + push_id, mymessageMap);
                        messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

                        mChatMessageView.setText("");

                        mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                if(databaseError != null){

                                    Log.d("CHAT_LOG", databaseError.getMessage().toString());

                                }

                            }
                        });


                    }

                }
            });

        }

    }

    private void loadMoreMessages() {

        DatabaseReference messageRef = mRootRef.child("messages").child(mCurrentUserId).child(mChatUser);//自己的id在到別人的id

        Query messageQuery = messageRef.orderByKey().endAt(mLastKey).limitToLast(10);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Messages message = dataSnapshot.getValue(Messages.class);
                String messageKey = dataSnapshot.getKey();

                if(!mPrevKey.equals(messageKey)){

                    messagesList.add(itemPos++, message);

                } else {

                    mPrevKey = mLastKey;
                }


                if(itemPos == 1) {

                    mLastKey = messageKey;

                }


                Log.d("TOTALKEYS", "Last Key : " + mLastKey + " | Prev Key : " + mPrevKey + " | Message Key : " + messageKey);

                mAdapter.notifyDataSetChanged();

                mRefreshLayout.setRefreshing(false);

                mLinearLayout.scrollToPositionWithOffset(10, 0);//運動recyclerview

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadMessages() {

        DatabaseReference messageRef = mRootRef.child("messages").child(mCurrentUserId).child(mChatUser);//自己的id先在到對方的id

        Query messageQuery = messageRef.limitToLast(mCurrentPage * TOTAL_ITEMS_TO_LOAD);


        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Messages message = dataSnapshot.getValue(Messages.class);

                itemPos++;

                if(itemPos == 1){

                    String messageKey = dataSnapshot.getKey();

                    mLastKey = messageKey;
                    mPrevKey = messageKey;

                }

                messagesList.add(message);
                mAdapter.notifyDataSetChanged();

                mMessagesList.scrollToPosition(messagesList.size() - 1);

                mRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void sendMessage() {


        String message = mChatMessageView.getText().toString();

        if(!TextUtils.isEmpty(message)){

            String current_user_ref = "messages/" + mCurrentUserId + "/" + mChatUser;//自己的id先在到別人的id
            String chat_user_ref = "messages/" + mChatUser + "/" + mCurrentUserId;//別人id在到自己的id

            DatabaseReference user_message_push = mRootRef.child("messages")
                    .child(mCurrentUserId).child(mChatUser).push();

            String push_id = user_message_push.getKey();

            Map mymessageMap = new HashMap();
            mymessageMap.put("message", message);
            mymessageMap.put("seen", false);
            mymessageMap.put("type", "text");
            mymessageMap.put("time", ServerValue.TIMESTAMP);
            mymessageMap.put("from", mCurrentUserId);
            mymessageMap.put("sendType","sent");

            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", mCurrentUserId);
            messageMap.put("sendType","received");

            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref + "/" + push_id, mymessageMap);
            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

            mChatMessageView.setText("");

            mRootRef.child("Chat").child(mCurrentUserId).child(mChatUser).child("seen").setValue(true);
            mRootRef.child("Chat").child(mCurrentUserId).child(mChatUser).child("timestamp").setValue(ServerValue.TIMESTAMP);

            mRootRef.child("Chat").child(mChatUser).child(mCurrentUserId).child("seen").setValue(false);
            mRootRef.child("Chat").child(mChatUser).child(mCurrentUserId).child("timestamp").setValue(ServerValue.TIMESTAMP);

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if(databaseError != null){

                        Log.d("CHAT_LOG", databaseError.getMessage().toString());

                    }

                }
            });

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ChatActivity.this,MainActivityFireBase.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            finish();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}