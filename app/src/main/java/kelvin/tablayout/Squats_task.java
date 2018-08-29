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

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Squats_task extends AppCompatActivity {
    private Toolbar squats_task_toolbar;
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
    private RecyclerView squats_task_recycler_view;
    private View mMainView;
    public static int mySquats,userSquats,all_task,same_task;
    public static int k;
    public static int j=0;
    public static int i;
    public int int_friend_point;
    private Data squats_data=new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squats_task);
        squats_task_toolbar=(Toolbar)findViewById(R.id.squats_task_toolbar);
        setSupportActionBar(squats_task_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("深蹲每週共同任務");
        actionBar.setSubtitle("點擊右邊的圖標和朋友一起完成");
        squats_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();
        taskDatabase= FirebaseDatabase.getInstance().getReference().child("Task_squats").child(mAuth.getCurrentUser().getUid());//共同任務資料庫
        myUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mUsersDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mDisplayImage = (CircleImageView) findViewById(R.id.user_single_image);

        myName = (TextView) findViewById(R.id.user_single_name);
        myStatus = (TextView) findViewById(R.id.user_single_status);
        exercise_week_data=(TextView)findViewById(R.id.exercise_week_data);
        susses_text_view=(TextView)findViewById(R.id.susses_text_view);
        exercise_week_data.setText(Time.getSquats_data(System.currentTimeMillis()));
        squats_task_recycler_view=(RecyclerView)findViewById(R.id.squats_task_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(Squats_task.this);
        squats_task_recycler_view.setHasFixedSize(true);
        squats_task_recycler_view.setLayoutManager(layoutManager);
        myUsersDatabase.keepSynced(true);
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
                        mystatu=dataSnapshot.child("exercise_count").child("squats").child("today_count").getValue().toString();
                        String squats_task_status=dataSnapshot.child("squats_task_status").getValue().toString();
                        int_friend_point=Integer.parseInt(friend_point);
                        mySquats=Integer.parseInt(mystatu);

                        all_task=squats_data.getFriend_squats_task_data()+mySquats;

                        same_task=Integer.parseInt(exercise_week_data.getText().toString());
                        if(all_task>=same_task&&squats_data.getFriend_squats_task_data()!=0&&mySquats!=0&&same_task!=0){
                            susses_text_view.setText("你獲得10點friendpoint");
                            actionBar.setSubtitle("你和朋友完成任務");

                            if(squats_task_status.equals("還沒完成")){

                                myUsersDatabase.child("friend_point").setValue(int_friend_point+10);
                                myUsersDatabase.child("squats_task_status").setValue("完成");
                            }

                        }else {
                            susses_text_view.setText("當前完成"+all_task+"次");
                            myUsersDatabase.child("squats_task_status").setValue("還沒完成");
                        }

                        myName.setText(myname);
                        myStatus.setText("深蹲今日記錄:"+mystatu+"次");
                        if(!image.equals("default")){
                            Picasso.with(Squats_task.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                                    .placeholder(R.drawable.default_avatar).into(mDisplayImage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {

                                    Picasso.with(Squats_task.this).load(image).placeholder(R.drawable.default_avatar).into(mDisplayImage);

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
            Intent intent = new Intent(Squats_task.this,Exercise_main.class);
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
                    Intent intent = new Intent(Squats_task.this,FriendActivity.class);
                    intent.putExtra("Task_req","Task_req_squats");
                    intent.putExtra("Task","Task_squats");
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
        FirebaseRecyclerAdapter<Friends,Squats_task.SquatsTaskViewHolder> squatsTaskViewHolderFirebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Friends, Squats_task.SquatsTaskViewHolder>(
                Friends.class,
                R.layout.task_single_layout,
                Squats_task.SquatsTaskViewHolder.class,
                taskDatabase

        ) {
            public int getItenCount(){
                int itemCount =super.getItemCount();

                return itemCount;
            }

            @Override
            protected void populateViewHolder(Squats_task.SquatsTaskViewHolder viewHolder, Friends model, int position) {
                //k=0;
                final String list_user_id = getRef(position).getKey();

                mUsersDatabase.child(list_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                        String userStatus=dataSnapshot.child("exercise_count").child("squats").child("today_count").getValue().toString();
                        userSquats=Integer.parseInt(userStatus);

                        Log.i("k3值",""+k);
                        Log.i("j3值",""+j);
                        if(j<=getItenCount()){
                            Log.i("k4值",""+k);
                            k=k+userSquats;
                            Log.i("k5值",""+k);
                            squats_data.setFriend_squats_task_data(k);
                           
                            Log.i("j4值",""+j);
                            j=j+1;
                            Log.i("j5值",""+j);
                        }

                        Log.i("朋友次次",""+k);
                        viewHolder.setName(userName);
                        viewHolder.setSatus("深蹲今天記錄:"+userStatus+"次");
                        viewHolder.setUserImage(userThumb,getApplication());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }



        };
        j=0;
        k=0;
        squats_task_recycler_view.setAdapter(squatsTaskViewHolderFirebaseRecyclerAdapter);
        j=0;
        k=0;
    }

    public static class SquatsTaskViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public SquatsTaskViewHolder(View itemView) {
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
