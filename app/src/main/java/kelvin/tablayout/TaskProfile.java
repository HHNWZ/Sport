package kelvin.tablayout;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.a888888888.sport.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskProfile extends AppCompatActivity {
    private Toolbar task_profile_app_bar;
    public static ActionBar actionBar;
    private FirebaseAuth mAuth;
    private ImageView mProfileImage;
    public static TextView mProfileName;
    private Button mProfileSendReqBtn,delete_send_req_btn;
    private ProgressDialog mProgressDialog;
    private DatabaseReference taskReqDatabase;
    private DatabaseReference taskDatabase;
    private DatabaseReference myUsersDatabase;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mRootRef;
    public static String my_image;
    private static FirebaseUser mCurrent_user;
    private String task_state;
    public String Fid;
    public static String my_name;
    public static String Task_req;
    public static String Task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_profile);
        task_profile_app_bar=(Toolbar)findViewById(R.id.task_profile_app_bar);
        setSupportActionBar(task_profile_app_bar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("朋友資訊");
        actionBar.setDisplayHomeAsUpEnabled(true);
        final String friend_id=getIntent().getStringExtra("user_id");
        Task_req=getIntent().getStringExtra("Task_req");
        Task=getIntent().getStringExtra("Task");
        Log.i("朋友id",friend_id);
        Log.i("Task_req",Task_req);
        Log.i("Task1234",Task);


        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(friend_id);//使用者資料庫
        myUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");//使用者資料庫
        taskReqDatabase=FirebaseDatabase.getInstance().getReference().child(Task_req);//共同任務邀請資料庫
        taskDatabase=FirebaseDatabase.getInstance().getReference().child(Task);//共同任務資料庫
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();//得到自己的會員id
        mProfileImage = (ImageView) findViewById(R.id.task_profile_image);
        mProfileName = (TextView) findViewById(R.id.task_profile_displayName);
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
                Picasso.with(TaskProfile.this).load(image).placeholder(R.drawable.default_avatar).into(mProfileImage);

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
                //--------------------用自己的UID去共同任務邀請資料庫查詢--------//
                taskReqDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(friend_id)){
                            String req_type=dataSnapshot.child(friend_id).child("request_type").getValue().toString();

                            if(req_type.equals("接收")){
                                task_state="接收請求";
                                mProfileSendReqBtn.setText("接受共同任務請求");
                                delete_send_req_btn.setVisibility(View.VISIBLE);
                            }else if(req_type.equals("發送")){
                                task_state="發送請求";
                                mProfileSendReqBtn.setText("取消共同任務請求");
                            }
                            mProgressDialog.dismiss();
                        }else {
                            taskDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(friend_id)){
                                        task_state="共同任務執行中";
                                        mProfileSendReqBtn.setText("刪除共同任務");
                                    }

                                    mProgressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                    mProgressDialog.dismiss();
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
        mProfileSendReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfileSendReqBtn.setEnabled(false);
                Fid=friend_id;

                //---如果沒有請求就發送---//
                if(task_state.equals("還沒有發送")){
                    Map requestMap =new HashMap();
                    requestMap.put(""+Task_req+"/" + mCurrent_user.getUid() + "/" + friend_id + "/request_type", "發送");
                    requestMap.put(""+Task_req+"/" + friend_id + "/" + mCurrent_user.getUid() + "/request_type", "接收");

                    mRootRef.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError != null){

                                Toast.makeText(TaskProfile.this, "發送請求時出錯", Toast.LENGTH_SHORT).show();
                            }else{
                                task_state = "發送請求";
                                mProfileSendReqBtn.setText("取消共同任務請求");
                            }

                            mProfileSendReqBtn.setEnabled(true);
                        }
                    });
                }

                //----取消發送共同任務請求----//
                if(task_state.equals("發送請求")){
                    taskReqDatabase.child(mCurrent_user.getUid()).child(friend_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            taskReqDatabase.child(friend_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProfileSendReqBtn.setEnabled(true);
                                    task_state="還沒有發送";
                                    mProfileSendReqBtn.setText("發送共同任務請求");
                                }
                            });
                        }
                    });
                }
                //-----接收共同任務請求---//
                if(task_state.equals("接收請求")){
                    delete_send_req_btn.setVisibility(View.INVISIBLE);
                    final String currentDate = Time.getToDate(System.currentTimeMillis());

                    Map friendsMap = new HashMap();
                    friendsMap.put(""+Task+"/" + mCurrent_user.getUid() + "/" + friend_id + "/date", currentDate);///
                    friendsMap.put(""+Task+"/" + friend_id + "/"  + mCurrent_user.getUid() + "/date", currentDate);

                    friendsMap.put(""+Task_req+"/" + mCurrent_user.getUid() + "/" +friend_id, null);
                    friendsMap.put(""+Task_req+"/" + friend_id + "/" + mCurrent_user.getUid(), null);

                    mRootRef.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError == null){

                                mProfileSendReqBtn.setEnabled(true);
                                task_state = "共同任務執行中";
                                mProfileSendReqBtn.setText("刪除共同任務");
                            } else {

                                String error = databaseError.getMessage();
                                Toast.makeText(TaskProfile.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                //----取消共同任務---///
                if(task_state.equals("共同任務執行中")){
                    Map unfriendMap = new HashMap();
                    unfriendMap.put(""+Task+"/" + mCurrent_user.getUid() + "/" + friend_id, null);
                    unfriendMap.put(""+Task+"/" + friend_id + "/" + mCurrent_user.getUid(), null);

                    mRootRef.updateChildren(unfriendMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                            if(databaseError == null){

                                task_state = "還沒有發送";
                                mProfileSendReqBtn.setText("發送共同任務請求");
                            }else {

                                String error = databaseError.getMessage();
                                Toast.makeText(TaskProfile.this, error, Toast.LENGTH_SHORT).show();
                            }
                            mProfileSendReqBtn.setEnabled(true);
                        }
                    });
                }
            }
        });

        delete_send_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fid=friend_id;
                mProfileSendReqBtn.setEnabled(false);

                Map friendsMap = new HashMap();
                friendsMap.put(""+Task_req+"/" + mCurrent_user.getUid() + "/" + friend_id, null);
                friendsMap.put(""+Task_req+"/" + friend_id + "/" + mCurrent_user.getUid(), null);

                mRootRef.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError==null){
                            mProfileSendReqBtn.setEnabled(true);
                            task_state = "還沒有發送";
                            mProfileSendReqBtn.setText("發送共同任務請求");
                            delete_send_req_btn.setVisibility(View.INVISIBLE);
                        }else {
                            String error = databaseError.getMessage();
                            Toast.makeText(TaskProfile.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(TaskProfile.this,FriendActivity.class);
            intent.putExtra("Task_req",Task_req);
            intent.putExtra("Task",Task);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
