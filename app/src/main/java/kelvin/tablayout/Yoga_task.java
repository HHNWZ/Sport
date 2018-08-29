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

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Yoga_task extends AppCompatActivity {

    private Toolbar yoga_task_toolbar;
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
    private RecyclerView yoga_task_recycler_view;
    private View mMainView;
    public static long myYoga,userYoga,all_task;
    public static long k;
    public static float same_task,chang_task;
    public static int j=0;
    public static int i;
    public int int_friend_point;
    public Data yoga_data=new Data();
    public static DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_task);
        yoga_task_toolbar=(Toolbar)findViewById(R.id.yoga_task_toolbar);
        setSupportActionBar(yoga_task_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("瑜伽每週共同任務");
        actionBar.setSubtitle("點擊右邊的圖標和朋友一起完成");
        yoga_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();
        taskDatabase= FirebaseDatabase.getInstance().getReference().child("Task_yoga").child(mAuth.getCurrentUser().getUid());//共同任務資料庫
        myUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mUsersDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mDisplayImage = (CircleImageView) findViewById(R.id.user_single_image);

        myName = (TextView) findViewById(R.id.user_single_name);
        myStatus = (TextView) findViewById(R.id.user_single_status);
        exercise_week_data=(TextView)findViewById(R.id.exercise_week_data);
        susses_text_view=(TextView)findViewById(R.id.susses_text_view);
        exercise_week_data.setText(Time.getYoga_data(System.currentTimeMillis()));
        yoga_task_recycler_view=(RecyclerView)findViewById(R.id.yoga_task_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(Yoga_task.this);
        yoga_task_recycler_view.setHasFixedSize(true);
        yoga_task_recycler_view.setLayoutManager(layoutManager);
        myUsersDatabase.keepSynced(true);
        //yoga_data.setYoga_task_status("還沒完成");
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
                        mystatu=dataSnapshot.child("exercise_count").child("yoga").child("today_time").getValue().toString();
                        String yoga_task_status=dataSnapshot.child("yoga_task_status").getValue().toString();
                        int_friend_point=Integer.parseInt(friend_point);
                        myYoga=Long.parseLong(mystatu);
                        Log.i("朋友的時間",""+yoga_data.getFriend_yoga_task_data());
                        Log.i("我的時間",""+myYoga);
                        all_task=yoga_data.getFriend_yoga_task_data()+myYoga;
                        Log.i("合計時間",""+all_task);
                        chang_task=Time.yogaWeekminute(all_task);
                        Log.i("轉換時間",""+chang_task);
                        same_task=Float.parseFloat(exercise_week_data.getText().toString());




                        if(chang_task>=same_task&&yoga_data.getFriend_yoga_task_data()!=0&&myYoga!=0&&same_task!=0){
                            susses_text_view.setText("你獲得10點friendpoint");
                            actionBar.setSubtitle("你和朋友完成任務");

                            if(yoga_task_status.equals("還沒完成")){

                                myUsersDatabase.child("friend_point").setValue(int_friend_point+10);
                                myUsersDatabase.child("yoga_task_status").setValue("完成");
                            }

                        }else {
                            susses_text_view.setText("當前完成"+Time.changeYogaTime(all_task));
                            myUsersDatabase.child("yoga_task_status").setValue("還沒完成");
                        }
                        myName.setText(myname);
                        myStatus.setText("瑜伽今日記錄:"+Time.changeYogaTime(myYoga));
                        if(!image.equals("default")){
                            Picasso.with(Yoga_task.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                                    .placeholder(R.drawable.default_avatar).into(mDisplayImage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {

                                    Picasso.with(Yoga_task.this).load(image).placeholder(R.drawable.default_avatar).into(mDisplayImage);

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
            Intent intent = new Intent(Yoga_task.this,Exercise_main.class);
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
                    Intent intent = new Intent(Yoga_task.this,FriendActivity.class);
                    intent.putExtra("Task_req","Task_req_yoga");
                    intent.putExtra("Task","Task_yoga");
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
        FirebaseRecyclerAdapter<Friends,YogaTaskViewHolder> yogaTaskViewHolderFirebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Friends, YogaTaskViewHolder>(
                Friends.class,
                R.layout.task_single_layout,
                YogaTaskViewHolder.class,
                taskDatabase

        ) {
            public int getItenCount(){
                int itemCount =super.getItemCount();

                return itemCount;
            }

            @Override
            protected void populateViewHolder(YogaTaskViewHolder viewHolder, Friends model, int position) {
                //k=0;
                final String list_user_id = getRef(position).getKey();
                Log.i("k2值",""+k);
                Log.i("j2值",""+j);

                mUsersDatabase.child(list_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                        String userStatus=dataSnapshot.child("exercise_count").child("yoga").child("today_time").getValue().toString();
                        userYoga=Long.parseLong(userStatus);

                        Log.i("最終get&set","已經set");
                        Log.i("k3值",""+k);
                        Log.i("j3值",""+j);
                        if(j<=getItenCount()){
                            Log.i("k4值",""+k);
                            k=k+userYoga;
                            Log.i("k5值",""+k);
                            yoga_data.setFriend_yoga_task_data(k);
                            Log.i("j4值",""+j);
                            j=j+1;
                            Log.i("j5值",""+j);
                        }

                        Log.i("朋友跑步距離",""+k);
                        viewHolder.setName(userName);
                        viewHolder.setSatus("瑜伽今日記錄:"+Time.changeYogaTime(userYoga));
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
        yoga_task_recycler_view.setAdapter(yogaTaskViewHolderFirebaseRecyclerAdapter);
        j=0;
        k=0;
        Log.i("k7值",""+k);
        Log.i("j7值",""+j);
    }


    public static class YogaTaskViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public YogaTaskViewHolder(View itemView) {
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
