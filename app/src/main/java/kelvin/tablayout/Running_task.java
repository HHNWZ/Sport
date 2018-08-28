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
    public static String myname,mystatu,my_all_task,friend_point;
    private RecyclerView running_task_recycler_view;
    private View mMainView;
    public static double myRunning,userRunning,double_my_all_task,all_task,same_task,double_friend_point;
    public static double k;
    public static int j=0;
    public static String task_statu;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_task);

        task_statu="還沒完成任務";
        running_task_toolbar=(Toolbar)findViewById(R.id.running_task_toolbar);
        setSupportActionBar(running_task_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("跑步每週共同任務");
        actionBar.setSubtitle("點擊右邊的圖標和朋友一起完成");
        running_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();
        Log.i("我的id",""+mAuth.getCurrentUser().getUid());
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
        myUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myname = dataSnapshot.child("name").getValue().toString();
                final String image = dataSnapshot.child("thumb_image").getValue().toString();
                friend_point=dataSnapshot.child("friend_point").getValue().toString();
                mystatu=dataSnapshot.child("exercise_count").child("running").child("today_record").getValue().toString();
                my_all_task=dataSnapshot.child("exercise_count").child("running").child("task_record").getValue().toString();
                String running_task_status=dataSnapshot.child("running_task_status").getValue().toString();
                double_my_all_task=Double.parseDouble(my_all_task);
                double_friend_point=Double.parseDouble(friend_point);

                myRunning=Double.parseDouble(mystatu);
                all_task=double_my_all_task+myRunning;
                same_task=Double.parseDouble(exercise_week_data.getText().toString());
                Log.i("all_task1234",""+double_friend_point);
                if(all_task>=same_task&&double_my_all_task!=0&&myRunning!=0&&same_task!=0){
                    susses_text_view.setText("你獲得10點friendpoint");
                    actionBar.setSubtitle("你和朋友完成任務");

                    if(running_task_status.equals("還沒完成")){

                        myUsersDatabase.child("friend_point").setValue(double_friend_point+5);
                        myUsersDatabase.child("running_task_status").setValue("完成");
                    }

                }else {
                    susses_text_view.setText("當前完成"+all_task+"公里");
                    myUsersDatabase.child("running_task_status").setValue("還沒完成");
                }


                //task_statu="還沒完成任務";
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
                    Log.i("點擊","成功");
                    break;
            }

            return true;
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.task_menu, menu);
        MenuItem task_friend = menu.findItem(R.id.task_friend);
        if(susses_text_view.getText().equals("你獲得10點friendpoint")){
            task_friend.setVisible(false);
        }
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
                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                        String userStatus=dataSnapshot.child("exercise_count").child("running").child("today_record").getValue().toString();
                        userRunning=Double.parseDouble(userStatus);
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
