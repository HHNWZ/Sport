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

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class Walking_task extends AppCompatActivity {

    private Toolbar walking_task_toolbar;
    public static ActionBar actionBar;
    private static FirebaseAuth mAuth;

    private DatabaseReference walking_task_Database;
    private DatabaseReference walking_task_friendDatabase;
    private DatabaseReference walking_task_myDatabase;
    private DatabaseReference walking_task_confirm_database;
    private DatabaseReference walking_task_friend_point_database;

    private TextView walking_task_data;
    private TextView walking_susses_text_view;

    private CircleImageView my_walking_task_image;
    private TextView my_walking_task_name;
    private TextView  my_walking_task_finish_count_data;

    private CircleImageView friend_walking_task_image;
    private TextView friend_walking_task_name;
    private TextView friend_walking_task_finish_count;
    private TextView friend_walking_task_finish_count_data;

    private TextView walking_task_text_and;
    private TextView walking_task_friend_point;
    private TextView walking_task_friend_point1;
    private TextView walking_task_friend_point2;
    private TextView walking_susses_text_view_data;
    private Button confirm_walking_task_button;

    private static String walking_task_my_name;
    private static String walking_task_my_image;
    private static String walking_task_my_count;
    private static String walking_task_my_friend_point;

    private static String walking_task_friend_name;
    private static String walking_task_friend_image;
    private static String walking_task_friend_count;

    private static double walking_task_my_count_double;
    private static int walking_task_my_friend_point_int;
    private static double walking_task_friend_count_double;
    private static double walking_progress;
    private static double walking_task_data_double;

    private Data walking_data=new Data();
    public CircularSeekBar walking_task_seek_bar;
    private String myID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_task);
        GlobalVariable task=(GlobalVariable)getApplicationContext();
        task.setTask("Task_walking");
        task.setTask_reg("Task_req_walking");
        walking_task_toolbar=(Toolbar)findViewById(R.id.walking_task_toolbar);
        setSupportActionBar(walking_task_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("步行每週共同任務");
        actionBar.setSubtitle("點擊右邊的圖標和朋友一起完成");
        walking_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        mAuth = FirebaseAuth.getInstance();
        myID = mAuth.getCurrentUser().getUid();
        walking_task_myDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        walking_task_friend_point_database=FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        walking_task_Database=FirebaseDatabase.getInstance().getReference();
        walking_task_confirm_database=FirebaseDatabase.getInstance().getReference();
        walking_task_friendDatabase=FirebaseDatabase.getInstance().getReference().child("Users");

        walking_task_seek_bar=(CircularSeekBar)findViewById(R.id.walking_task_seek_bar);
        walking_task_data=(TextView)findViewById(R.id.walking_task_data);
        walking_susses_text_view=(TextView)findViewById(R.id.walking_susses_text_view);
        walking_susses_text_view_data=(TextView)findViewById(R.id.walking_susses_text_view_data);

        my_walking_task_image=(CircleImageView)findViewById(R.id.my_walking_task_image);
        my_walking_task_name=(TextView)findViewById(R.id.my_walking_task_name);
        my_walking_task_finish_count_data=(TextView)findViewById(R.id.my_walking_task_finish_count_data);

        friend_walking_task_image=(CircleImageView)findViewById(R.id.friend_walking_task_image);
        friend_walking_task_name=(TextView)findViewById(R.id.friend_walking_task_name);
        friend_walking_task_finish_count=(TextView)findViewById(R.id.friend_walking_task_finish_count);
        friend_walking_task_finish_count_data=(TextView)findViewById(R.id.friend_walking_task_finish_count_data);

        walking_task_text_and=(TextView)findViewById(R.id.walking_task_text_and);
        walking_task_friend_point=(TextView)findViewById(R.id.walking_task_friend_point);
        walking_task_friend_point1=(TextView)findViewById(R.id.walking_task_friend_point1);
        walking_task_friend_point2=(TextView)findViewById(R.id.walking_task_friend_point2);
        confirm_walking_task_button=(Button)findViewById(R.id.confirm_walking_task_button);

        walking_task_data.setText("100");
        walking_task_seek_bar.setMax(Float.parseFloat(walking_task_data.getText().toString()));
        walking_susses_text_view.setText("目前沒有朋友");

        walking_task_myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                walking_task_my_name=dataSnapshot.child("name").getValue().toString();
                walking_task_my_image=dataSnapshot.child("thumb_image").getValue().toString();
                walking_task_my_count=dataSnapshot.child("exercise_count").child("walking").child("today_record").getValue().toString();
                walking_task_my_friend_point=dataSnapshot.child("friend_point").getValue().toString();

                walking_task_my_friend_point_int=Integer.parseInt(walking_task_my_friend_point);
                walking_data.setMy_task_friend_point(walking_task_my_friend_point_int);

                walking_task_my_count_double=Double.parseDouble(walking_task_my_count);
                walking_data.setMy_task_double_exercise_data(walking_task_my_count_double);

                my_walking_task_name.setText(walking_task_my_name);
                my_walking_task_finish_count_data.setText(walking_task_my_count+"公里");

                if(walking_task_my_image.equals("default")){
                    Picasso.get().load(R.drawable.default_avatar).into(my_walking_task_image);
                }else{
                    Picasso.get().load(walking_task_my_image).into(my_walking_task_image);
                }

                walking_task_Database.child("Task_walking").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(myID)){
                            walking_task_toolbar.setOnMenuItemClickListener(null);
                            final String list_user_id =dataSnapshot.child(myID).child("id").getValue().toString();
                            walking_task_text_and.setVisibility(View.VISIBLE);
                            friend_walking_task_name.setVisibility(View.VISIBLE);
                            friend_walking_task_image.setVisibility(View.VISIBLE);
                            friend_walking_task_finish_count.setVisibility(View.VISIBLE);
                            friend_walking_task_finish_count_data.setVisibility(View.VISIBLE);

                            walking_task_friendDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (walking_task_text_and.getVisibility() == View.VISIBLE) {
                                        walking_task_friend_name = dataSnapshot.child("name").getValue().toString();
                                        walking_task_friend_image = dataSnapshot.child("thumb_image").getValue().toString();
                                        walking_task_friend_count = dataSnapshot.child("exercise_count").child("walking").child("today_record").getValue().toString();
                                        walking_task_friend_count_double = Double.parseDouble(walking_task_friend_count);

                                        friend_walking_task_name.setText(walking_task_friend_name);
                                        friend_walking_task_finish_count_data.setText(walking_task_friend_count + "次");


                                        if (walking_task_friend_image.equals("default")) {
                                            Picasso.get().load(R.drawable.default_avatar).into(friend_walking_task_image);
                                        } else {
                                            Picasso.get().load(walking_task_friend_image).into(friend_walking_task_image);
                                        }

                                        walking_progress = walking_task_friend_count_double + walking_data.getMy_task_double_exercise_data();
                                        Log.i("進度條的進度", "" + walking_progress);

                                        walking_task_data_double = Double.parseDouble(walking_task_data.getText().toString());
                                        Log.i("仰臥起坐共同任務運動量", "" + walking_task_data_double);
                                        if (walking_progress >= walking_task_data_double) {
                                            walking_task_seek_bar.setProgress((float) walking_task_data_double);
                                            walking_susses_text_view.setText("你們已經完成");
                                            walking_task_friend_point.setVisibility(View.VISIBLE);
                                            walking_task_friend_point1.setVisibility(View.VISIBLE);
                                            walking_task_friend_point2.setVisibility(View.VISIBLE);
                                            confirm_walking_task_button.setVisibility(View.VISIBLE);
                                            confirm_walking_task_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    walking_task_text_and.setVisibility(View.INVISIBLE);
                                                    friend_walking_task_name.setVisibility(View.INVISIBLE);
                                                    friend_walking_task_image.setVisibility(View.INVISIBLE);
                                                    friend_walking_task_finish_count.setVisibility(View.INVISIBLE);
                                                    friend_walking_task_finish_count_data.setVisibility(View.INVISIBLE);
                                                    walking_task_friend_point.setVisibility(View.INVISIBLE);
                                                    walking_task_friend_point1.setVisibility(View.INVISIBLE);
                                                    walking_task_friend_point2.setVisibility(View.INVISIBLE);
                                                    walking_task_Database.child("Task_walking").child(myID).child("id").removeValue();
                                                    walking_task_friend_point_database.child("friend_point").setValue(walking_data.getMy_task_friend_point() + 10);
                                                    walking_susses_text_view.setText("目前沒有朋友");
                                                    walking_susses_text_view_data.setVisibility(View.GONE);
                                                    walking_task_seek_bar.setProgress((0));
                                                    walking_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
                                                    confirm_walking_task_button.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                        } else if (walking_progress < walking_task_data_double) {
                                            walking_susses_text_view.setText("你們目前完成");
                                            walking_susses_text_view_data.setVisibility(View.VISIBLE);
                                            walking_susses_text_view_data.setText(walking_progress+"次");
                                            walking_task_seek_bar.setProgress((float) walking_progress);
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
            Intent intent = new Intent(Walking_task.this,Exercise_main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Walking_task.this,Exercise_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.task_friend:
                    Intent intent = new Intent(Walking_task.this,FriendActivity.class);
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

   
}
