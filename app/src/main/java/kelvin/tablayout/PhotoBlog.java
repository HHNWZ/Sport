package kelvin.tablayout;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhotoBlog extends AppCompatActivity {


    private RecyclerView postList;
    private Toolbar mToolbar;

    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;


    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, PostsRef,LikesRef,userDataBase,FileNameRef;


    String currentUserID;
    Boolean LikeChecker=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_blog);
        Log.i("我在PhotoBlog的","onCreate");
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        LikesRef=FirebaseDatabase.getInstance().getReference().child("Likes");
        FileNameRef=FirebaseDatabase.getInstance().getReference().child("Posts");

        UsersRef.keepSynced(true);
        PostsRef.keepSynced(true);
        LikesRef.keepSynced(true);
        FileNameRef.keepSynced(true);


        mToolbar = (Toolbar) findViewById(R.id.activity_photo_blog_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("運動經驗交流");

        userDataBase=FirebaseDatabase.getInstance().getReference().child("Users");



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        postList = (RecyclerView) findViewById(R.id.all_users_post_list);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PhotoBlog.this,LinearLayoutManager.VERTICAL,true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);
        DisplayAllUsersPosts();










    }

     @Override
    protected void onStart(){
        super.onStart();
        Log.i("我在PhotoBlog的","onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("我在PhotoBlog的","onResume");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        GlobalVariable User = (GlobalVariable)getApplicationContext();
        Log.i("我在PhotoBlog的","onRestart");

        User.setWord("haha");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("我在PhotoBlog的","onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("我在PhotoBlog的","onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("我在PhotoBlog的","onDestroy");
    }


    private void DisplayAllUsersPosts()
    {
        FirebaseRecyclerAdapter<Posts, PostsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Posts, PostsViewHolder>
                        (
                                Posts.class,
                                R.layout.all_post_layout,
                                PostsViewHolder.class,
                                PostsRef
                        )
                {
                    @Override
                    protected void populateViewHolder(PostsViewHolder viewHolder, Posts model, int position)
                    {

                        Log.i("我在這裡:","1");
                        final String Postkey=getRef(position).getKey();
                        Log.i("帖文id",Postkey);

                        //viewHolder.setFullname(model.getFullname());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setDescription(model.getDescription());
                        //viewHolder.setProfileimage(model.getProfileimage());
                        viewHolder.setPostimage(model.getPostimage());
                        final String post_user_id=model.getUid();
                        Log.i("post_user_id"+position,post_user_id);
                        userDataBase.child(post_user_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChildren()){
                                    if(dataSnapshot.hasChild("name")){
                                        String postUserName=dataSnapshot.child("name").getValue().toString();
                                        viewHolder.setFullname(postUserName);
                                    }
                                    if(dataSnapshot.hasChild("thumb_image")){
                                        String postUserImage=dataSnapshot.child("thumb_image").getValue().toString();
                                        viewHolder.setProfileimage(postUserImage);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        FileNameRef.child(Postkey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("file_name")){
                                    String file_name=dataSnapshot.child("file_name").getValue().toString();
                                    Log.i("圖片名稱",file_name);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        viewHolder.setLikeButtonStatus(Postkey);

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent clickPostIntent =new Intent(PhotoBlog.this,ClickPostActivity.class);
                                clickPostIntent.putExtra("PostKey",Postkey);
                                startActivity(clickPostIntent);

                            }
                        });

                        viewHolder.CommentPostButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent commentsIntent =new Intent(PhotoBlog.this,CommentsActivity.class);
                                commentsIntent.putExtra("PostKey",Postkey);
                                startActivity(commentsIntent);
                            }
                        });

                        Log.i("我在這裡:","2");
                        viewHolder.LikePostButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LikeChecker=true;
                                Log.i("我在這裡:","3");
                                LikesRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Log.i("我在這裡:","4");
                                        if(LikeChecker.equals(true)){
                                            if(dataSnapshot.child(Postkey).hasChild(currentUserID)){
                                                LikesRef.child(Postkey).child(currentUserID).removeValue();
                                                LikeChecker=false;
                                            }else {
                                                LikesRef.child(Postkey).child(currentUserID).setValue(true);
                                                LikeChecker=false;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                    }
                };
        postList.setAdapter(firebaseRecyclerAdapter);
    }



    public static class PostsViewHolder extends RecyclerView.ViewHolder
    {

        View mView;

        ImageButton LikePostButton,CommentPostButton;
        TextView DisplayNoOfLikes;
        int countLikes;
        String currentUserId;
        DatabaseReference LikesRef;

        public PostsViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;

            LikePostButton=(ImageButton)mView.findViewById(R.id.like_button);
            CommentPostButton=(ImageButton)mView.findViewById(R.id.comment_button);
            DisplayNoOfLikes=(TextView)mView.findViewById(R.id.display_no_of_like);

            LikesRef=FirebaseDatabase.getInstance().getReference().child("Likes");
            currentUserId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        public void setLikeButtonStatus(final String PostKey){
            LikesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(PostKey).hasChild(currentUserId))
                    {
                        countLikes=(int)dataSnapshot.child(PostKey).getChildrenCount();
                        LikePostButton.setImageResource(R.drawable.like);
                        DisplayNoOfLikes.setText((Integer.toString(countLikes))+(" 個贊"));
                    }else {
                        countLikes=(int)dataSnapshot.child(PostKey).getChildrenCount();
                        LikePostButton.setImageResource(R.drawable.dislike2);
                        DisplayNoOfLikes.setText((Integer.toString(countLikes))+(" 個贊"));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void setFullname(String fullname)
        {
            TextView username = (TextView) mView.findViewById(R.id.post_user_name);
            username.setText(fullname);
        }

        public void setProfileimage(String profileimage)
        {
            CircleImageView image = (CircleImageView) mView.findViewById(R.id.post_profile_image);
            Picasso.get().load(profileimage).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().noFade().into(image);

        }

        public void setTime(String time)
        {
            TextView PostTime = (TextView) mView.findViewById(R.id.post_time);
            PostTime.setText("    " + time);
        }

        public void setDate(String date)
        {
            TextView PostDate = (TextView) mView.findViewById(R.id.post_date);
            PostDate.setText("    " + date);
        }

        public void setDescription(String description)
        {
            TextView PostDescription = (TextView) mView.findViewById(R.id.click_post_description);
            PostDescription.setText(description);
        }

        public void setPostimage(String postimage)
        {
            ImageView PostImage = (ImageView) mView.findViewById(R.id.click_post_image);
            Picasso.get().load(postimage).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().noFade().into(PostImage);
            Picasso.get().setIndicatorsEnabled(true);
        }
    }





    private void SendUserToPostActivity() {
        Intent addNewPostIntent = new Intent(PhotoBlog.this,PostActivity.class);
        startActivity(addNewPostIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(PhotoBlog.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(PhotoBlog.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private final Toolbar.OnMenuItemClickListener onMenuItemClickListener;

    {
        onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.post:
                        SendUserToPostActivity();
                        break;

                }
                return true;
            }
        };
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.photoblog_menu, menu);
        return true;
    }









}

