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
import android.widget.Button;
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
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class Running_task extends AppCompatActivity {

    private Toolbar running_task_toolbar;
    public static ActionBar actionBar;
    private static FirebaseAuth mAuth;

    private DatabaseReference running_task_Database;
    private DatabaseReference running_task_friendDatabase;
    private DatabaseReference running_task_myDatabase;
    private DatabaseReference running_task_confirm_database;
    private DatabaseReference running_task_friend_point_database;


    private TextView running_task_data;
    private TextView running_susses_text_view;

    private CircleImageView my_running_task_image;
    private TextView my_running_task_name;
    private TextView  my_running_task_finish_count_data;

    private CircleImageView friend_running_task_image;
    private TextView friend_running_task_name;
    private TextView friend_running_task_finish_count;
    private TextView friend_running_task_finish_count_data;

    private TextView running_task_text_and;
    private TextView running_task_friend_point;
    private TextView running_task_friend_point1;
    private TextView running_task_friend_point2;

    private Button confirm_running_task_button;

    private static String running_task_my_name;
    private static String running_task_my_image;
    private static String running_task_my_count;
    private static String running_task_my_friend_point;

    private static String running_task_friend_name;
    private static String running_task_friend_image;
    private static String running_task_friend_count;

    private static double running_task_my_count_double;
    private static int running_task_my_friend_point_int;
    private static double running_task_friend_count_double;
    private static double running_progress;
    private static double running_task_data_double;

    private Data running_data=new Data();
    public CircularSeekBar running_task_seek_bar;
    private String myID;
    private TextView running_susses_text_view_data;
    private DatabaseReference kelvin_running_today_count_database;
    //private double control=0.1;
    DecimalFormat df = new DecimalFormat("0.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("我在Running_task的","onCreate");
        setContentView(R.layout.activity_running_task);
        GlobalVariable task=(GlobalVariable)getApplicationContext();
        task.setTask("Task_running");
        task.setTask_reg("Task_req_running");

        running_task_toolbar=(Toolbar)findViewById(R.id.running_task_toolbar);
        setSupportActionBar(running_task_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("跑步每週共同任務");
        actionBar.setSubtitle("點擊右邊的圖標和朋友一起完成");
        running_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();
        myID = mAuth.getCurrentUser().getUid();

        running_task_myDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        running_task_friend_point_database=FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        running_task_Database=FirebaseDatabase.getInstance().getReference();
        running_task_confirm_database=FirebaseDatabase.getInstance().getReference();
        running_task_friendDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        kelvin_running_today_count_database=FirebaseDatabase.getInstance().getReference().child("Users");

        running_task_seek_bar=(CircularSeekBar)findViewById(R.id.running_task_seek_bar);
        running_task_data=(TextView)findViewById(R.id.running_task_data);
        running_susses_text_view=(TextView)findViewById(R.id.running_susses_text_view);

        my_running_task_image=(CircleImageView)findViewById(R.id.my_running_task_image);
        my_running_task_name=(TextView)findViewById(R.id.my_running_task_name);
        my_running_task_finish_count_data=(TextView)findViewById(R.id.my_running_task_finish_count_data);

        friend_running_task_image=(CircleImageView)findViewById(R.id.friend_running_task_image);
        friend_running_task_name=(TextView)findViewById(R.id.friend_running_task_name);
        friend_running_task_finish_count=(TextView)findViewById(R.id.friend_running_task_finish_count);
        friend_running_task_finish_count_data=(TextView)findViewById(R.id.friend_running_task_finish_count_data);

        running_task_text_and=(TextView)findViewById(R.id.running_task_text_and);
        running_task_friend_point=(TextView)findViewById(R.id.running_task_friend_point);
        running_task_friend_point1=(TextView)findViewById(R.id.running_task_friend_point1);
        running_task_friend_point2=(TextView)findViewById(R.id.running_task_friend_point2);
        confirm_running_task_button=(Button)findViewById(R.id.confirm_running_task_button);

        running_susses_text_view_data=(TextView)findViewById(R.id.running_susses_text_view_data);

        running_task_data.setText("30");
        running_task_seek_bar.setMax(Float.parseFloat(running_task_data.getText().toString()));
        running_susses_text_view_data.setVisibility(View.GONE);
        running_susses_text_view.setText("目前沒有朋友");




        running_task_myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                running_task_my_name=dataSnapshot.child("name").getValue().toString();
                running_task_my_image=dataSnapshot.child("thumb_image").getValue().toString();
                running_task_my_count=dataSnapshot.child("exercise_count").child("running").child("today_record").getValue().toString();
                running_task_my_friend_point=dataSnapshot.child("friend_point").getValue().toString();

                running_task_my_friend_point_int=Integer.parseInt(running_task_my_friend_point);
                running_data.setMy_task_friend_point(running_task_my_friend_point_int);

                running_task_my_count_double=Double.parseDouble(running_task_my_count);
                running_data.setMy_task_double_exercise_data(running_task_my_count_double);

                my_running_task_name.setText(running_task_my_name);
                my_running_task_finish_count_data.setText(df.format(running_task_my_count_double)+"公里");

                if(running_task_my_image.equals("default")){
                    Picasso.get().load(R.drawable.default_avatar).into(my_running_task_image);
                }else{
                    Picasso.get().load(running_task_my_image).into(my_running_task_image);
                }

                running_task_Database.child("Running_Task").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(myID)){
                            running_task_toolbar.setOnMenuItemClickListener(null);
                            final String list_user_id =dataSnapshot.child(myID).child("id").getValue().toString();
                            running_task_text_and.setVisibility(View.VISIBLE);
                            friend_running_task_name.setVisibility(View.VISIBLE);
                            friend_running_task_image.setVisibility(View.VISIBLE);
                            friend_running_task_finish_count.setVisibility(View.VISIBLE);
                            friend_running_task_finish_count_data.setVisibility(View.VISIBLE);

                            running_task_friendDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (running_task_text_and.getVisibility() == View.VISIBLE) {
                                        running_task_friend_name = dataSnapshot.child("name").getValue().toString();
                                        running_task_friend_image = dataSnapshot.child("thumb_image").getValue().toString();
                                        running_task_friend_count = dataSnapshot.child("exercise_count").child("running").child("today_record").getValue().toString();
                                        running_task_friend_count_double = Double.parseDouble(running_task_friend_count);

                                        friend_running_task_name.setText(running_task_friend_name);
                                        friend_running_task_finish_count_data.setText(df.format(running_task_friend_count_double)+ "公里");


                                        if (running_task_friend_image.equals("default")) {
                                            Picasso.get().load(R.drawable.default_avatar).into(friend_running_task_image);
                                        } else {
                                            Picasso.get().load(running_task_friend_image).into(friend_running_task_image);
                                        }

                                        running_progress = running_task_friend_count_double + running_data.getMy_task_double_exercise_data();
                                        Log.i("進度條的進度", "" + running_progress);

                                        running_task_data_double = Double.parseDouble(running_task_data.getText().toString());
                                        Log.i("仰臥起坐共同任務運動量", "" + running_task_data_double);
                                        if (running_progress >= running_task_data_double) {
                                            running_task_seek_bar.setProgress((float) running_task_data_double);
                                            running_susses_text_view_data.setVisibility(View.GONE);
                                            running_susses_text_view.setText("你們已經完成");
                                            running_task_friend_point.setVisibility(View.VISIBLE);
                                            running_task_friend_point1.setVisibility(View.VISIBLE);
                                            running_task_friend_point2.setVisibility(View.VISIBLE);
                                            confirm_running_task_button.setVisibility(View.VISIBLE);
                                            running_susses_text_view_data.setVisibility(View.GONE);
                                            confirm_running_task_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    running_task_text_and.setVisibility(View.INVISIBLE);
                                                    friend_running_task_name.setVisibility(View.INVISIBLE);
                                                    friend_running_task_image.setVisibility(View.INVISIBLE);
                                                    friend_running_task_finish_count.setVisibility(View.INVISIBLE);
                                                    friend_running_task_finish_count_data.setVisibility(View.INVISIBLE);
                                                    running_task_friend_point.setVisibility(View.INVISIBLE);
                                                    running_task_friend_point1.setVisibility(View.INVISIBLE);
                                                    running_task_friend_point2.setVisibility(View.INVISIBLE);
                                                    running_task_Database.child("Task_running").child(myID).child("id").removeValue();
                                                    running_task_friend_point_database.child("friend_point").setValue(running_data.getMy_task_friend_point() + 10);
                                                    kelvin_running_today_count_database.child("1jZbs9r78DM54p5FkzANcPruYSG3").child("exercise_count").child("running").child("today_record").setValue(14);
                                                    running_susses_text_view.setText("目前沒有朋友");
                                                    running_susses_text_view_data.setVisibility(View.GONE);
                                                    running_task_seek_bar.setProgress((0));
                                                    running_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
                                                    confirm_running_task_button.setVisibility(View.INVISIBLE);
                                                    Intent intent = new Intent(Running_task.this,Exercise_main.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else if (running_progress < running_task_data_double) {
                                            running_susses_text_view.setText("你們目前完成");
                                            running_susses_text_view_data.setVisibility(View.VISIBLE);
                                            running_susses_text_view_data.setText(df.format(running_progress)+"公里");
                                            running_task_seek_bar.setProgress((float) running_progress);
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
        


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Running_task.this,Exercise_main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Running_task.this,Exercise_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.task_friend:
                    Intent intent = new Intent(Running_task.this,RunningTaskFriend.class);
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
    protected void onStart(){
        super.onStart();
        Log.i("我在Running_task的","onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("我在Running_task的","onResume");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("我在Running_task的","onRestart");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("我在Running_task的","onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("我在Running_task的","onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("我在Running_task的","onDestroy");
    }
  

    


    








}
