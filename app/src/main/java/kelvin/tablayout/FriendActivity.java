package kelvin.tablayout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a888888888.sport.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendActivity extends AppCompatActivity {

    private Toolbar activity_friend_toolbar;
    public static ActionBar actionBar;
    public static String now_date;
    private RecyclerView activity_friend_recycler_view;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference friendDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;
    private String Task_reg;
    private String Task;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        activity_friend_toolbar=(Toolbar)findViewById(R.id.activity_friend_toolbar);
        setSupportActionBar(activity_friend_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("朋友列表");
        actionBar.setSubtitle("點擊列表");
        actionBar.setDisplayHomeAsUpEnabled(true);
        now_date=Week.getWeek(System.currentTimeMillis());
        activity_friend_recycler_view=(RecyclerView)findViewById(R.id.activity_friend_recycler_view);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        Task_reg=getIntent().getStringExtra("Task_req");
        Task=getIntent().getStringExtra("Task");
        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        friendDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id);
        mUsersDatabase.keepSynced(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(FriendActivity.this);
        activity_friend_recycler_view.setHasFixedSize(true);
        activity_friend_recycler_view.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if(now_date.equals("一")){
                Intent intent = new Intent(FriendActivity.this,Walking_task.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            if(now_date.equals("二")){
                Intent intent = new Intent(FriendActivity.this,Running_task.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            if(now_date.equals("三")){
                Intent intent = new Intent(FriendActivity.this,Yoga_task.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Friends,ActivityFriendsViewHolder> activityFriendsViewHolderFirebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Friends, ActivityFriendsViewHolder>(
                Friends.class,
                R.layout.users_single_layout,
                ActivityFriendsViewHolder.class,
                mFriendsDatabase
        ) {
            @Override
            protected void populateViewHolder(ActivityFriendsViewHolder viewHolder, Friends model, int position) {
                final String list_user_id = getRef(position).getKey();
                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                        viewHolder.setName(userName);
                        viewHolder.setUserImage(userThumb, getApplication());
                        viewHolder.setDate("");

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent taskIntent = new Intent();
                                taskIntent.setClass(FriendActivity.this  , TaskProfile.class);
                                taskIntent.putExtra("user_id",list_user_id);
                                taskIntent.putExtra("Task_req",Task_reg);
                                taskIntent.putExtra("Task",Task);
                                startActivity(taskIntent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        activity_friend_recycler_view.setAdapter(activityFriendsViewHolderFirebaseRecyclerAdapter);
    }

    public static class ActivityFriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ActivityFriendsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }



        public void setName(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

        public void setDate(String date){

            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(date);

        }




    }
}
