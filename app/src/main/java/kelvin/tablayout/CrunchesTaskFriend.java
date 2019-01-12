package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CrunchesTaskFriend extends AppCompatActivity {
    private Toolbar activity_crunches_task_friend_toolbar;
    public static ActionBar actionBar;

    private RecyclerView activity_crunches_task_friend_recycler_view;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference friendDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches_task_friend);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        activity_crunches_task_friend_toolbar=(Toolbar)findViewById(R.id.activity_crunches_task_friend_toolbar);
        setSupportActionBar(activity_crunches_task_friend_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("朋友列表");
        actionBar.setSubtitle("點擊要挑戰的朋友");
        actionBar.setDisplayHomeAsUpEnabled(true);
        activity_crunches_task_friend_recycler_view=(RecyclerView)findViewById(R.id.activity_crunches_task_friend_recycler_view);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        friendDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id);
        mUsersDatabase.keepSynced(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(CrunchesTaskFriend.this);
        activity_crunches_task_friend_recycler_view.setHasFixedSize(true);
        activity_crunches_task_friend_recycler_view.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(CrunchesTaskFriend.this,Crunches_task.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(CrunchesTaskFriend.this,Crunches_task.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("我在CrunchesTaskFriend的","onStart");

        FirebaseRecyclerAdapter<Friends,ActivityCrunchesTaskFriendsViewHolderew> activityFriendsViewHolderFirebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Friends, ActivityCrunchesTaskFriendsViewHolderew>(
                Friends.class,
                R.layout.users_single_layout,
                ActivityCrunchesTaskFriendsViewHolderew.class,
                mFriendsDatabase
        ) {
            @Override
            protected void populateViewHolder(ActivityCrunchesTaskFriendsViewHolderew viewHolder, Friends model, int position) {
                final String list_user_id = getRef(position).getKey();
                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                        viewHolder.setName(userName);
                        viewHolder.setUserImage(userThumb);
                        viewHolder.setDate("");

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent taskIntent = new Intent();
                                taskIntent.setClass(CrunchesTaskFriend.this  , CrunchesTaskFriendProfile.class);
                                taskIntent.putExtra("user_id",list_user_id);
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
        activity_crunches_task_friend_recycler_view.setAdapter(activityFriendsViewHolderFirebaseRecyclerAdapter);
    }

    public static class ActivityCrunchesTaskFriendsViewHolderew extends RecyclerView.ViewHolder {

        View mView;

        public ActivityCrunchesTaskFriendsViewHolderew(View itemView) {
            super(itemView);

            mView = itemView;

        }


        public void setName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setUserImage(String thumb_image) {

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);
            Picasso.get().load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

        public void setDate(String date) {

            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(date);

        }
    }
}
