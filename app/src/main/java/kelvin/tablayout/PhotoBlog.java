package kelvin.tablayout;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhotoBlog extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView postList;
    private Toolbar mToolbar;

    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;
    private ImageButton AddNewPostButton;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, PostsRef;

    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_blog);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");


        mToolbar = (Toolbar) findViewById(R.id.activity_photo_blog_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("運動經驗交流");


        AddNewPostButton = (ImageButton) findViewById(R.id.add_new_post_button);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(PhotoBlog.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);


        postList = (RecyclerView) findViewById(R.id.all_users_post_list);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);


        View navView = navigationView.inflateHeaderView(R.layout.navigation_header_photo_blog);
        NavProfileImage = (CircleImageView) navView.findViewById(R.id.nav_profile_image);
        NavProfileUserName = (TextView) navView.findViewById(R.id.nav_user_full_name);


        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.hasChild("name"))
                    {
                        String fullname = dataSnapshot.child("name").getValue().toString();
                        NavProfileUserName.setText(fullname);
                    }
                    if(dataSnapshot.hasChild("thumb_image"))
                    {
                        String image = dataSnapshot.child("thumb_image").getValue().toString();
                        Picasso.with(PhotoBlog.this).load(image).placeholder(R.drawable.default_avatar).into(NavProfileImage);
                    }
                    else
                    {
                        Toast.makeText(PhotoBlog.this, "資料讀取錯誤", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                UserMenuSelector(item);
                return false;
            }
        });


        AddNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SendUserToPostActivity();
            }
        });


        DisplayAllUsersPosts();

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
                        final String Postkey=getRef(position).getKey();

                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setDescription(model.getDescription());
                        viewHolder.setProfileimage(getApplicationContext(), model.getProfileimage());
                        viewHolder.setPostimage(getApplicationContext(), model.getPostimage());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent clickPostIntent =new Intent(PhotoBlog.this,ClickPostActivity.class);
                                clickPostIntent.putExtra("PostKey",Postkey);
                                startActivity(clickPostIntent);

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

        public PostsViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;

            LikePostButton=(ImageButton)mView.findViewById(R.id.like_button);
            CommentPostButton=(ImageButton)mView.findViewById(R.id.comment_button);
            DisplayNoOfLikes=(TextView)mView.findViewById(R.id.display_no_of_like);
        }

        public void setFullname(String fullname)
        {
            TextView username = (TextView) mView.findViewById(R.id.post_user_name);
            username.setText(fullname);
        }

        public void setProfileimage(Context ctx, String profileimage)
        {
            CircleImageView image = (CircleImageView) mView.findViewById(R.id.post_profile_image);
            Picasso.with(ctx).load(profileimage).into(image);
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

        public void setPostimage(Context ctx1,  String postimage)
        {
            ImageView PostImage = (ImageView) mView.findViewById(R.id.click_post_image);
            Picasso.with(ctx1).load(postimage).into(PostImage);
        }
    }





    private void SendUserToPostActivity() {
        Intent addNewPostIntent = new Intent(PhotoBlog.this,PostActivity.class);
        startActivity(addNewPostIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.nav_blog_profile:
                Toast.makeText(this,"個人主頁",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_back_main_activity:
                Intent intent = new Intent();
                intent.setClass(PhotoBlog.this, MainActivity.class);
                startActivity(intent);
                break;
            case  R.id.nav_blog_post:
                SendUserToPostActivity();
                break;
            case  R.id.nav_blog_view_friend_message:
                Toast.makeText(this,"瀏覽朋友運動帖文",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_friend:
                Toast.makeText(this,"朋友",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_find_friend:
                Toast.makeText(this,"尋找朋友",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_messages:
                Toast.makeText(this,"信息",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_blog_settings:
                Toast.makeText(this,"設置",Toast.LENGTH_SHORT).show();
                break;

        }
    }


}
