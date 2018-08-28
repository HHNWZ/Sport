package kelvin.tablayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

public class Running_task extends AppCompatActivity {

    private Toolbar running_task_toolbar;
    public static ActionBar actionBar;
    private static FirebaseAuth mAuth;
    private DatabaseReference taskDatabase;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference myUsersDatabase;
    private CircleImageView mDisplayImage;
    private TextView myName;
    private TextView myStatus;
    public static TextView exercise_week_data;
    public static TextView susses_text_view;
    public static String myname,mystatu,friend_point;
    private RecyclerView running_task_recycler_view;
    private View mMainView;
    public static double myRunning,userRunning,all_task,same_task;
    public static double k;
    public static int j=0;
    public static int i;
    public int int_friend_point;
    public Data running_data=new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_task);

        running_task_toolbar=(Toolbar)findViewById(R.id.running_task_toolbar);
        setSupportActionBar(running_task_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("跑步每週共同任務");
        actionBar.setSubtitle("點擊右邊的圖標和朋友一起完成");
        running_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();
        taskDatabase= FirebaseDatabase.getInstance().getReference().child("Task_running").child(mAuth.getCurrentUser().getUid());//共同任務資料庫
        myUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mUsersDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mDisplayImage = (CircleImageView) findViewById(R.id.user_single_image);

        myName = (TextView) findViewById(R.id.user_single_name);
        myStatus = (TextView) findViewById(R.id.user_single_status);
        exercise_week_data=(TextView)findViewById(R.id.exercise_week_data);
        susses_text_view=(TextView)findViewById(R.id.susses_text_view);
        exercise_week_data.setText(Time.getRunning_data(System.currentTimeMillis()));
        running_task_recycler_view=(RecyclerView)findViewById(R.id.running_task_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(Running_task.this);
        running_task_recycler_view.setHasFixedSize(true);
        running_task_recycler_view.setLayoutManager(layoutManager);
        myUsersDatabase.keepSynced(true);
        //running_data.setRunning_task_status("還沒完成");
        Timer timer=new Timer();

        TimerTask mTimerTask =new TimerTask(){
            @Override
            public void run(){
                myUsersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        myname = dataSnapshot.child("name").getValue().toString();
                        final String image = dataSnapshot.child("thumb_image").getValue().toString();
                        friend_point=dataSnapshot.child("friend_point").getValue().toString();
                        mystatu=dataSnapshot.child("exercise_count").child("running").child("today_record").getValue().toString();
                        String running_task_status=dataSnapshot.child("running_task_status").getValue().toString();
                        int_friend_point=Integer.parseInt(friend_point);
                        myRunning=Double.parseDouble(mystatu);

                        all_task=running_data.getFriend_running_task_data()+myRunning;
                        same_task=Double.parseDouble(exercise_week_data.getText().toString());

                        if(all_task>=same_task&&running_data.getFriend_running_task_data()!=0&&myRunning!=0&&same_task!=0){
                            susses_text_view.setText("你獲得10點friendpoint");
                            actionBar.setSubtitle("你和朋友完成任務");

                            if(running_task_status.equals("還沒完成")){

                                myUsersDatabase.child("friend_point").setValue(int_friend_point+10);
                                myUsersDatabase.child("running_task_status").setValue("完成");
                            }

                        }else {
                            susses_text_view.setText("當前完成"+all_task+"公里");
                            myUsersDatabase.child("running_task_status").setValue("還沒完成");
                        }
                        myName.setText(myname);
                        myStatus.setText("今日跑步距離:"+mystatu+"公里");
                        if(!image.equals("default")){
                            Picasso.with(Running_task.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                                    .placeholder(R.drawable.default_avatar).into(mDisplayImage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {

                                    Picasso.with(Running_task.this).load(image).placeholder(R.drawable.default_avatar).into(mDisplayImage);

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        timer.schedule(mTimerTask,1000,5000);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Running_task.this,Exercise_main.class);
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
                    Intent intent = new Intent(Running_task.this,FriendActivity.class);
                    intent.putExtra("Task_req","Task_req_running");
                    intent.putExtra("Task","Task_running");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
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
        Log.i("k1值",""+k);
        Log.i("j1值",""+j);
        FirebaseRecyclerAdapter<Friends,RunningTaskViewHolder> runningTaskViewHolderFirebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Friends, RunningTaskViewHolder>(
                Friends.class,
                R.layout.task_single_layout,
                RunningTaskViewHolder.class,
                taskDatabase

        ) {
            public int getItenCount(){
                int itemCount =super.getItemCount();

                return itemCount;
            }

            @Override
            protected void populateViewHolder(RunningTaskViewHolder viewHolder, Friends model, int position) {
                //k=0;
                final String list_user_id = getRef(position).getKey();
                Log.i("k2值",""+k);
                Log.i("j2值",""+j);

                        mUsersDatabase.child(list_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final String userName = dataSnapshot.child("name").getValue().toString();
                                String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                                String userStatus=dataSnapshot.child("exercise_count").child("running").child("today_record").getValue().toString();
                                userRunning=Double.parseDouble(userStatus);
                                running_data.setFriend_running_task_data(userRunning);
                                Log.i("最終get&set","已經set");
                                Log.i("k3值",""+k);
                                Log.i("j3值",""+j);
                                if(j<=getItenCount()){
                                    Log.i("k4值",""+k);
                                    k=k+userRunning;
                                    Log.i("k5值",""+k);
                                    myUsersDatabase.child("exercise_count").child("running").child("task_record").setValue(k);
                                    Log.i("j4值",""+j);
                                    j=j+1;
                                    Log.i("j5值",""+j);
                                }

                                Log.i("朋友跑步距離",""+k);
                                viewHolder.setName(userName);
                                viewHolder.setSatus("跑步今天記錄:"+userStatus+"公里");
                                viewHolder.setUserImage(userThumb,getApplication());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }





        };
        Log.i("k6值",""+k);
        Log.i("j6值",""+j);
        j=0;
        k=0;
        running_task_recycler_view.setAdapter(runningTaskViewHolderFirebaseRecyclerAdapter);
        j=0;
        k=0;
        Log.i("k7值",""+k);
        Log.i("j7值",""+j);
    }


    public static class RunningTaskViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public RunningTaskViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setSatus(String status){

            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(status);

        }

        public void setName(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }
    }








}
