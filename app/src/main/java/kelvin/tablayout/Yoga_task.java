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

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class Yoga_task extends AppCompatActivity {

    private Toolbar yoga_task_toolbar;
    public static ActionBar actionBar;
    private static FirebaseAuth mAuth;

    private DatabaseReference yoga_task_Database;
    private DatabaseReference yoga_task_friendDatabase;
    private DatabaseReference yoga_task_myDatabase;
    private DatabaseReference yoga_task_confirm_database;
    private DatabaseReference yoga_task_friend_point_database;

    private TextView yoga_task_data;
    private TextView yoga_susses_text_view;

    private CircleImageView my_yoga_task_image;
    private TextView my_yoga_task_name;
    private TextView  my_yoga_task_finish_count_data;

    private CircleImageView friend_yoga_task_image;
    private TextView friend_yoga_task_name;
    private TextView friend_yoga_task_finish_count;
    private TextView friend_yoga_task_finish_count_data;

    private TextView yoga_task_text_and;
    private TextView yoga_task_friend_point;
    private Button confirm_yoga_task_button;

    private static String yoga_task_my_name;
    private static String yoga_task_my_image;
    private static String yoga_task_my_count;
    private static String yoga_task_my_friend_point;

    private static String yoga_task_friend_name;
    private static String yoga_task_friend_image;
    private static String yoga_task_friend_count;

    private static long yoga_task_my_count_long;
    private static int yoga_task_my_friend_point_int;
    private static long yoga_task_friend_count_long;
    private static long yoga_progress;
    private static long yoga_task_data_long;





    private Data yoga_data=new Data();
    public CircularSeekBar yoga_task_seek_bar;
    

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

        yoga_task_myDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        yoga_task_friend_point_database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        yoga_task_Database=FirebaseDatabase.getInstance().getReference();
        yoga_task_confirm_database=FirebaseDatabase.getInstance().getReference();
        yoga_task_friendDatabase=FirebaseDatabase.getInstance().getReference().child("Users");

        yoga_task_seek_bar=(CircularSeekBar)findViewById(R.id.yoga_task_seek_bar);
        yoga_task_data=(TextView)findViewById(R.id.yoga_task_data);
        yoga_susses_text_view=(TextView)findViewById(R.id.yoga_susses_text_view);

        my_yoga_task_image=(CircleImageView)findViewById(R.id.my_yoga_task_image);
        my_yoga_task_name=(TextView)findViewById(R.id.my_yoga_task_name);
        my_yoga_task_finish_count_data=(TextView)findViewById(R.id.my_yoga_task_finish_count_data);

        friend_yoga_task_image=(CircleImageView)findViewById(R.id.friend_yoga_task_image);
        friend_yoga_task_name=(TextView)findViewById(R.id.friend_yoga_task_name);
        friend_yoga_task_finish_count=(TextView)findViewById(R.id.friend_yoga_task_finish_count);
        friend_yoga_task_finish_count_data=(TextView)findViewById(R.id.friend_yoga_task_finish_count_data);

        yoga_task_text_and=(TextView)findViewById(R.id.yoga_task_text_and);
        yoga_task_friend_point=(TextView)findViewById(R.id.yoga_task_friend_point);
        confirm_yoga_task_button=(Button)findViewById(R.id.confirm_yoga_task_button);

        yoga_task_data.setText("20");
        yoga_task_seek_bar.setMax(Float.parseFloat(yoga_task_data.getText().toString()));
        yoga_susses_text_view.setText("目前沒有朋友");

        yoga_task_myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                yoga_task_my_name=dataSnapshot.child("name").getValue().toString();
                yoga_task_my_image=dataSnapshot.child("thumb_image").getValue().toString();
                yoga_task_my_count=dataSnapshot.child("exercise_count").child("yoga").child("today_time").getValue().toString();
                yoga_task_my_friend_point=dataSnapshot.child("friend_point").getValue().toString();

                yoga_task_my_friend_point_int=Integer.parseInt(yoga_task_my_friend_point);
                yoga_data.setMy_task_friend_point(yoga_task_my_friend_point_int);

                yoga_task_my_count_long=Long.parseLong(yoga_task_my_count);
                yoga_data.setMy_task_long_exercise_data(yoga_task_my_count_long);

                my_yoga_task_name.setText(yoga_task_my_name);
                my_yoga_task_finish_count_data.setText(""+Time.changeYogaTime(yoga_task_my_count_long));

                if(yoga_task_my_image.equals("default")){
                    Picasso.get().load(R.drawable.default_avatar).into(my_yoga_task_image);
                }else{
                    Picasso.get().load(yoga_task_my_image).into(my_yoga_task_image);
                }

                yoga_task_Database.child("Task_yoga").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){
                            yoga_task_toolbar.setOnMenuItemClickListener(null);
                            final String list_user_id =dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("id").getValue().toString();
                            yoga_task_text_and.setVisibility(View.VISIBLE);
                            friend_yoga_task_name.setVisibility(View.VISIBLE);
                            friend_yoga_task_image.setVisibility(View.VISIBLE);
                            friend_yoga_task_finish_count.setVisibility(View.VISIBLE);
                            friend_yoga_task_finish_count_data.setVisibility(View.VISIBLE);

                            yoga_task_friendDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (yoga_task_text_and.getVisibility() == View.VISIBLE) {
                                        yoga_task_friend_name = dataSnapshot.child("name").getValue().toString();
                                        yoga_task_friend_image = dataSnapshot.child("thumb_image").getValue().toString();
                                        yoga_task_friend_count = dataSnapshot.child("exercise_count").child("yoga").child("today_time").getValue().toString();
                                        yoga_task_friend_count_long = Long.parseLong(yoga_task_friend_count);

                                        friend_yoga_task_name.setText(yoga_task_friend_name);
                                        friend_yoga_task_finish_count_data.setText("" + Time.changeYogaTime(yoga_task_friend_count_long));


                                        if (yoga_task_friend_image.equals("default")) {
                                            Picasso.get().load(R.drawable.default_avatar).into(friend_yoga_task_image);
                                        } else {
                                            Picasso.get().load(yoga_task_friend_image).into(friend_yoga_task_image);
                                        }

                                        yoga_progress = yoga_task_friend_count_long + yoga_data.getMy_task_long_exercise_data();
                                        Log.i("進度條的進度", "" + yoga_progress);


                                        yoga_task_data_long = Long.parseLong(yoga_task_data.getText().toString()) * 60 * 1000;
                                        Log.i("仰臥起坐共同任務運動量", "" + yoga_task_data_long);
                                        if (yoga_progress >= yoga_task_data_long) {
                                            yoga_task_seek_bar.setProgress(Float.parseFloat(yoga_task_data.getText().toString()));
                                            yoga_susses_text_view.setText("你們已經完成");
                                            yoga_task_friend_point.setVisibility(View.VISIBLE);
                                            confirm_yoga_task_button.setVisibility(View.VISIBLE);
                                            confirm_yoga_task_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    yoga_task_text_and.setVisibility(View.INVISIBLE);
                                                    friend_yoga_task_name.setVisibility(View.INVISIBLE);
                                                    friend_yoga_task_image.setVisibility(View.INVISIBLE);
                                                    friend_yoga_task_finish_count.setVisibility(View.INVISIBLE);
                                                    friend_yoga_task_finish_count_data.setVisibility(View.INVISIBLE);
                                                    yoga_task_friend_point.setVisibility(View.INVISIBLE);
                                                    yoga_task_Database.child("Task_yoga").child(mAuth.getCurrentUser().getUid()).child("id").removeValue();
                                                    yoga_task_friend_point_database.child("friend_point").setValue(yoga_data.getMy_task_friend_point() + 10);
                                                    yoga_susses_text_view.setText("目前沒有朋友");
                                                    yoga_task_seek_bar.setProgress((0));
                                                    yoga_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
                                                    confirm_yoga_task_button.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                        } else if (yoga_progress < yoga_task_data_long) {
                                            yoga_susses_text_view.setText("你們目前完成\n    " + Time.changeYogaTime(yoga_progress));
                                            yoga_task_seek_bar.setProgress((float) yoga_progress);
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

    
}
