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

public class Crunches_task extends AppCompatActivity {

    private Toolbar crunches_task_toolbar;
    public static ActionBar actionBar;
    private static FirebaseAuth mAuth;

    private DatabaseReference crunches_task_Database;
    private DatabaseReference crunches_task_friendDatabase;
    private DatabaseReference crunches_task_myDatabase;
    private DatabaseReference crunches_task_confirm_database;
    private DatabaseReference crunches_task_friend_point_database;

    private TextView crunches_task_data;
    private TextView crunches_susses_text_view;
    private TextView crunches_susses_text_view_data;

    private CircleImageView my_crunches_task_image;
    private TextView my_crunches_task_name;
    private TextView  my_crunches_task_finish_count_data;

    private CircleImageView friend_crunches_task_image;
    private TextView friend_crunches_task_name;
    private TextView friend_crunches_task_finish_count;
    private TextView friend_crunches_task_finish_count_data;

    private TextView crunches_task_text_and;
    private TextView crunches_task_friend_point;
    private TextView crunches_task_friend_point1;
    private TextView crunches_task_friend_point2;
    private Button confirm_crunches_task_button;

    private static String crunches_task_my_name;
    private static String crunches_task_my_image;
    private static String crunches_task_my_count;
    private static String crunches_task_my_friend_point;

    private static String crunches_task_friend_name;
    private static String crunches_task_friend_image;
    private static String crunches_task_friend_count;

    private static int crunches_task_my_count_int;
    private static int crunches_task_my_friend_point_int;
    private static int crunches_task_friend_count_int;
    private static int crunches_progress;
    private static int crunches_task_data_int;
    private String myID;




    private Data crunches_data=new Data();
    public CircularSeekBar crunches_task_seek_bar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches_task);
        GlobalVariable task=(GlobalVariable)getApplicationContext();
        task.setTask("Task_crunches");
        task.setTask_reg("Task_req_crunches");
        crunches_task_toolbar=(Toolbar)findViewById(R.id.crunches_task_toolbar);
        setSupportActionBar(crunches_task_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("仰臥起坐每週共同任務");
        actionBar.setSubtitle("點擊右邊的圖標和朋友一起完成");
        crunches_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);

        mAuth = FirebaseAuth.getInstance();
        myID = mAuth.getCurrentUser().getUid();
        crunches_task_myDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        crunches_task_friend_point_database=FirebaseDatabase.getInstance().getReference().child("Users").child(myID);
        crunches_task_Database=FirebaseDatabase.getInstance().getReference();
        crunches_task_confirm_database=FirebaseDatabase.getInstance().getReference();
        crunches_task_friendDatabase=FirebaseDatabase.getInstance().getReference().child("Users");

        crunches_task_seek_bar=(CircularSeekBar)findViewById(R.id.crunches_task_seek_bar);
        crunches_task_data=(TextView)findViewById(R.id.crunches_task_data);
        crunches_susses_text_view=(TextView)findViewById(R.id.crunches_susses_text_view);
        crunches_susses_text_view_data=(TextView)findViewById(R.id.crunches_susses_text_view_data);

        my_crunches_task_image=(CircleImageView)findViewById(R.id.my_crunches_task_image);
        my_crunches_task_name=(TextView)findViewById(R.id.my_crunches_task_name);
        my_crunches_task_finish_count_data=(TextView)findViewById(R.id.my_crunches_task_finish_count_data);

        friend_crunches_task_image=(CircleImageView)findViewById(R.id.friend_crunches_task_image);
        friend_crunches_task_name=(TextView)findViewById(R.id.friend_crunches_task_name);
        friend_crunches_task_finish_count=(TextView)findViewById(R.id.friend_crunches_task_finish_count);
        friend_crunches_task_finish_count_data=(TextView)findViewById(R.id.friend_crunches_task_finish_count_data);

        crunches_task_text_and=(TextView)findViewById(R.id.crunches_task_text_and);
        crunches_task_friend_point=(TextView)findViewById(R.id.crunches_task_friend_point);
        crunches_task_friend_point1=(TextView)findViewById(R.id.crunches_task_friend_point1);
        crunches_task_friend_point2=(TextView)findViewById(R.id.crunches_task_friend_point2);
        confirm_crunches_task_button=(Button)findViewById(R.id.confirm_crunches_task_button);

        crunches_task_data.setText("100");
        crunches_task_seek_bar.setMax(Float.parseFloat(crunches_task_data.getText().toString()));
        crunches_susses_text_view.setText("目前沒有朋友");

        crunches_task_myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                crunches_task_my_name=dataSnapshot.child("name").getValue().toString();
                crunches_task_my_image=dataSnapshot.child("thumb_image").getValue().toString();
                crunches_task_my_count=dataSnapshot.child("exercise_count").child("crunches").child("today_count").getValue().toString();
                crunches_task_my_friend_point=dataSnapshot.child("friend_point").getValue().toString();

                crunches_task_my_friend_point_int=Integer.parseInt(crunches_task_my_friend_point);
                crunches_data.setMy_task_friend_point(crunches_task_my_friend_point_int);

                crunches_task_my_count_int=Integer.parseInt(crunches_task_my_count);
                crunches_data.setMy_task_int_exercise_data(crunches_task_my_count_int);

                my_crunches_task_name.setText(crunches_task_my_name);
                my_crunches_task_finish_count_data.setText(crunches_task_my_count+"次");

                if(crunches_task_my_image.equals("default")){
                   Picasso.get().load(R.drawable.default_avatar).into(my_crunches_task_image);
                }else{
                    Picasso.get().load(crunches_task_my_image).into(my_crunches_task_image);
                }

                crunches_task_Database.child("Task_crunches").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(myID)){
                            crunches_task_toolbar.setOnMenuItemClickListener(null);
                            final String list_user_id =dataSnapshot.child(myID).child("id").getValue().toString();
                            crunches_task_text_and.setVisibility(View.VISIBLE);
                            friend_crunches_task_name.setVisibility(View.VISIBLE);
                            friend_crunches_task_image.setVisibility(View.VISIBLE);
                            friend_crunches_task_finish_count.setVisibility(View.VISIBLE);
                            friend_crunches_task_finish_count_data.setVisibility(View.VISIBLE);

                            crunches_task_friendDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (crunches_task_text_and.getVisibility() == View.VISIBLE) {
                                        crunches_task_friend_name = dataSnapshot.child("name").getValue().toString();
                                        crunches_task_friend_image = dataSnapshot.child("thumb_image").getValue().toString();
                                        crunches_task_friend_count = dataSnapshot.child("exercise_count").child("crunches").child("today_count").getValue().toString();
                                        crunches_task_friend_count_int = Integer.parseInt(crunches_task_friend_count);

                                        friend_crunches_task_name.setText(crunches_task_friend_name);
                                        friend_crunches_task_finish_count_data.setText(crunches_task_friend_count + "次");


                                        if (crunches_task_friend_image.equals("default")) {
                                            Picasso.get().load(R.drawable.default_avatar).into(friend_crunches_task_image);
                                        } else {
                                            Picasso.get().load(crunches_task_friend_image).into(friend_crunches_task_image);
                                        }

                                        crunches_progress = crunches_task_friend_count_int + crunches_data.getMy_task_int_exercise_data();
                                        Log.i("進度條的進度", "" + crunches_progress);

                                        crunches_task_data_int = Integer.parseInt(crunches_task_data.getText().toString());
                                        Log.i("仰臥起坐共同任務運動量", "" + crunches_task_data_int);
                                        if (crunches_progress >= crunches_task_data_int) {
                                            crunches_task_seek_bar.setProgress((float) crunches_task_data_int);
                                            crunches_susses_text_view.setText("你們已經完成");
                                            crunches_task_friend_point.setVisibility(View.VISIBLE);
                                            crunches_task_friend_point1.setVisibility(View.VISIBLE);
                                            crunches_task_friend_point2.setVisibility(View.VISIBLE);
                                            confirm_crunches_task_button.setVisibility(View.VISIBLE);
                                            confirm_crunches_task_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    crunches_task_text_and.setVisibility(View.INVISIBLE);
                                                    friend_crunches_task_name.setVisibility(View.INVISIBLE);
                                                    friend_crunches_task_image.setVisibility(View.INVISIBLE);
                                                    friend_crunches_task_finish_count.setVisibility(View.INVISIBLE);
                                                    friend_crunches_task_finish_count_data.setVisibility(View.INVISIBLE);
                                                    crunches_task_friend_point.setVisibility(View.INVISIBLE);
                                                    crunches_task_friend_point1.setVisibility(View.INVISIBLE);
                                                    crunches_task_friend_point2.setVisibility(View.INVISIBLE);
                                                    crunches_task_Database.child("Task_crunches").child(myID).child("id").removeValue();
                                                    crunches_task_friend_point_database.child("friend_point").setValue(crunches_data.getMy_task_friend_point() + 10);
                                                    crunches_susses_text_view.setText("目前沒有朋友");
                                                    crunches_susses_text_view_data.setVisibility(View.GONE);
                                                    crunches_task_seek_bar.setProgress((0));
                                                    crunches_task_toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
                                                    confirm_crunches_task_button.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                        } else if (crunches_progress < crunches_task_data_int) {
                                            crunches_susses_text_view.setText("你們目前完成");
                                            crunches_susses_text_view_data.setVisibility(View.VISIBLE);
                                            crunches_susses_text_view_data.setText(+crunches_progress+"次");
                                            crunches_task_seek_bar.setProgress((float) crunches_progress);
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
            Intent intent = new Intent(Crunches_task.this,Exercise_main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Crunches_task.this,Exercise_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.task_friend:
                    Intent intent = new Intent(Crunches_task.this,FriendActivity.class);
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

    }
}
