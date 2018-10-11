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

public class Squats_task extends AppCompatActivity {
    private Toolbar squats_task_toolbar;
    public static ActionBar actionBar;
    private static FirebaseAuth mAuth;

    private DatabaseReference squats_task_Database;
    private DatabaseReference squats_task_friendDatabase;
    private DatabaseReference squats_task_myDatabase;
    private DatabaseReference squats_task_confirm_database;
    private DatabaseReference squats_task_friend_point_database;

    private TextView squats_task_data;
    private TextView squats_susses_text_view;

    private CircleImageView my_squats_task_image;
    private TextView my_squats_task_name;
    private TextView  my_squats_task_finish_count_data;

    private CircleImageView friend_squats_task_image;
    private TextView friend_squats_task_name;
    private TextView friend_squats_task_finish_count;
    private TextView friend_squats_task_finish_count_data;

    private TextView squats_task_text_and;
    private TextView squats_task_friend_point;
    private Button confirm_squats_task_button;

    private static String squats_task_my_name;
    private static String squats_task_my_image;
    private static String squats_task_my_count;
    private static String squats_task_my_friend_point;

    private static String squats_task_friend_name;
    private static String squats_task_friend_image;
    private static String squats_task_friend_count;

    private static int squats_task_my_count_int;
    private static int squats_task_my_friend_point_int;
    private static int squats_task_friend_count_int;
    private static int squats_progress;
    private static int squats_task_data_int;





    private Data squats_data=new Data();
    public CircularSeekBar squats_task_seek_bar;

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

        squats_task_myDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        squats_task_friend_point_database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        squats_task_Database=FirebaseDatabase.getInstance().getReference();
        squats_task_confirm_database=FirebaseDatabase.getInstance().getReference();
        squats_task_friendDatabase=FirebaseDatabase.getInstance().getReference().child("Users");

        squats_task_seek_bar=(CircularSeekBar)findViewById(R.id.squats_task_seek_bar);
        squats_task_data=(TextView)findViewById(R.id.squats_task_data);
        squats_susses_text_view=(TextView)findViewById(R.id.squats_susses_text_view);

        my_squats_task_image=(CircleImageView)findViewById(R.id.my_squats_task_image);
        my_squats_task_name=(TextView)findViewById(R.id.my_squats_task_name);
        my_squats_task_finish_count_data=(TextView)findViewById(R.id.my_squats_task_finish_count_data);

        friend_squats_task_image=(CircleImageView)findViewById(R.id.friend_squats_task_image);
        friend_squats_task_name=(TextView)findViewById(R.id.friend_squats_task_name);
        friend_squats_task_finish_count=(TextView)findViewById(R.id.friend_squats_task_finish_count);
        friend_squats_task_finish_count_data=(TextView)findViewById(R.id.friend_squats_task_finish_count_data);

        squats_task_text_and=(TextView)findViewById(R.id.squats_task_text_and);
        squats_task_friend_point=(TextView)findViewById(R.id.squats_task_friend_point);
        confirm_squats_task_button=(Button)findViewById(R.id.confirm_squats_task_button);

        squats_task_data.setText("100");
        squats_task_seek_bar.setMax(Float.parseFloat(squats_task_data.getText().toString()));
        squats_susses_text_view.setText("目前沒有朋友");

        squats_task_myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                squats_task_my_name=dataSnapshot.child("name").getValue().toString();
                squats_task_my_image=dataSnapshot.child("thumb_image").getValue().toString();
                squats_task_my_count=dataSnapshot.child("exercise_count").child("squats").child("today_count").getValue().toString();
                squats_task_my_friend_point=dataSnapshot.child("friend_point").getValue().toString();

                squats_task_my_friend_point_int=Integer.parseInt(squats_task_my_friend_point);
                squats_data.setMy_task_friend_point(squats_task_my_friend_point_int);

                squats_task_my_count_int=Integer.parseInt(squats_task_my_count);
                squats_data.setMy_task_int_exercise_data(squats_task_my_count_int);

                my_squats_task_name.setText(squats_task_my_name);
                my_squats_task_finish_count_data.setText(squats_task_my_count+"次");

                if(squats_task_my_image.equals("default")){
                    Picasso.get().load(R.drawable.default_avatar).into(my_squats_task_image);
                }else{
                    Picasso.get().load(squats_task_my_image).into(my_squats_task_image);
                }

                squats_task_Database.child("Task_squats").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){
                            squats_task_toolbar.setOnMenuItemClickListener(null);
                            final String list_user_id =dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("id").getValue().toString();
                            squats_task_text_and.setVisibility(View.VISIBLE);
                            friend_squats_task_name.setVisibility(View.VISIBLE);
                            friend_squats_task_image.setVisibility(View.VISIBLE);
                            friend_squats_task_finish_count.setVisibility(View.VISIBLE);
                            friend_squats_task_finish_count_data.setVisibility(View.VISIBLE);

                            squats_task_friendDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(squats_task_text_and.getVisibility()==View.VISIBLE){
                                        squats_task_friend_name=dataSnapshot.child("name").getValue().toString();
                                        squats_task_friend_image=dataSnapshot.child("thumb_image").getValue().toString();
                                        squats_task_friend_count=dataSnapshot.child("exercise_count").child("squats").child("today_count").getValue().toString();
                                        squats_task_friend_count_int=Integer.parseInt(squats_task_friend_count);

                                        friend_squats_task_name.setText(squats_task_friend_name);
                                        friend_squats_task_finish_count_data.setText(squats_task_friend_count+"次");



                                        if(squats_task_friend_image.equals("default")){
                                            Picasso.get().load(R.drawable.default_avatar).into(friend_squats_task_image);
                                        }else {
                                            Picasso.get().load(squats_task_friend_image).into(friend_squats_task_image);
                                        }

                                        squats_progress=squats_task_friend_count_int+squats_data.getMy_task_int_exercise_data();
                                        Log.i("進度條的進度",""+squats_progress);

                                        squats_task_data_int=Integer.parseInt(squats_task_data.getText().toString());
                                        Log.i("仰臥起坐共同任務運動量",""+squats_task_data_int);
                                        if(squats_progress>=squats_task_data_int){
                                            squats_task_seek_bar.setProgress((float)squats_task_data_int);
                                            squats_susses_text_view.setText("你們已經完成");
                                            squats_task_friend_point.setVisibility(View.VISIBLE);
                                            confirm_squats_task_button.setVisibility(View.VISIBLE);
                                            confirm_squats_task_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    squats_task_text_and.setVisibility(View.INVISIBLE);
                                                    friend_squats_task_name.setVisibility(View.INVISIBLE);
                                                    friend_squats_task_image.setVisibility(View.INVISIBLE);
                                                    friend_squats_task_finish_count.setVisibility(View.INVISIBLE);
                                                    friend_squats_task_finish_count_data.setVisibility(View.INVISIBLE);
                                                    squats_task_friend_point.setVisibility(View.INVISIBLE);
                                                    squats_task_Database.child("Task_squats").child(mAuth.getCurrentUser().getUid()).child("id").removeValue();
                                                    squats_task_friend_point_database.child("friend_point").setValue(squats_data.getMy_task_friend_point()+10);
                                                    squats_susses_text_view.setText("目前沒有朋友");
                                                    squats_task_seek_bar.setProgress((0));
                                                    squats_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
                                                    confirm_squats_task_button.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                        }else if(squats_progress<squats_task_data_int){
                                            squats_susses_text_view.setText("你們目前完成\n        "+squats_progress+"次");
                                            squats_task_seek_bar.setProgress((float)squats_progress);
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

   
}
