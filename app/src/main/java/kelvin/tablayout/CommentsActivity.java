package kelvin.tablayout;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView CommentsList;
    private ImageButton PostCommentButton;
    private EditText CommentInputText;

    private DatabaseReference UsersRef,PostsRef;
    private static DatabaseReference FriendRef;

    private FirebaseAuth mAuth;

    private String Post_Key,current_user_id;
    private Toolbar comment_toolbar;
    private ActionBar comment_action_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        comment_toolbar=(Toolbar)findViewById(R.id.comment_toolbar);
        setSupportActionBar(comment_toolbar);
        comment_action_bar=getSupportActionBar();
        comment_action_bar.setTitle("帖文留言");
        comment_action_bar.setDisplayHomeAsUpEnabled(true);

        Post_Key=getIntent().getExtras().get("PostKey").toString();
        mAuth=FirebaseAuth.getInstance();
        current_user_id=mAuth.getCurrentUser().getUid();
        UsersRef=FirebaseDatabase.getInstance().getReference().child("Users");
        FriendRef=FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef=FirebaseDatabase.getInstance().getReference().child("Posts").child(Post_Key).child("Comments");





        CommentsList=(RecyclerView)findViewById(R.id.comment_list);
        CommentsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setStackFromEnd(true);
        CommentsList.setLayoutManager(linearLayoutManager);



        CommentInputText=(EditText)findViewById(R.id.comment_input);
        PostCommentButton=(ImageButton)findViewById(R.id.post_comment_btn);

        PostCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsersRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            String userName=dataSnapshot.child("username").getValue().toString();

                            ValidateComment(userName);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Comments, CommentsViewHolder>firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Comments, CommentsViewHolder>(
                        Comments.class,
                        R.layout.all_comments_layout,
                        CommentsViewHolder.class,
                        PostsRef) {
            @Override
            protected void populateViewHolder(CommentsViewHolder viewHolder, Comments model, int position) {
                viewHolder.setUsername(model.getUsername());
                viewHolder.setComment(model.getComment());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                viewHolder.setUid(model.getUid(),getApplication());
            }
        };

        CommentsList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public CommentsViewHolder(View itemView){
            super(itemView);

            mView =itemView;
        }

        public void setUsername(String username){
            TextView myUserName=(TextView)mView.findViewById(R.id.comment_username);
            myUserName.setText("@"+username+"  ");
        }

        public void setComment(String comment){
            TextView myComment =(TextView)mView.findViewById(R.id.comment_text);
            myComment.setText(comment);
        }

        public void setDate(String date){
            TextView myDate=(TextView) mView.findViewById(R.id.comment_date);
            myDate.setText(" 日期: "+date);
        }

        public void setTime(String time){
            TextView myTime=(TextView) mView.findViewById(R.id.comment_time);
            myTime.setText(" 時間: "+time);
        }

        public void setUid(String uid,Context ctx){
            Log.i("123456",uid);
            FriendRef.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String thumb_image=dataSnapshot.child("thumb_image").getValue().toString();
                        Log.i("123456",thumb_image);
                        CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.comment_username_image);
                        Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }

    private void ValidateComment(String userName) {

        String commentText=CommentInputText.getText().toString();

        if(TextUtils.isEmpty(commentText))
        {
            Toast.makeText(this,"請輸入留言",Toast.LENGTH_SHORT).show();
        }else {
            Calendar calFordDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            final String saveCurrentDate = currentDate.format(calFordDate.getTime());

            Calendar calFordTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            final String saveCurrentTime = currentTime.format(calFordDate.getTime());

            final String RandomKey =current_user_id + saveCurrentDate+saveCurrentTime;

            HashMap commentsMap = new HashMap();
                commentsMap.put("uid",current_user_id);
                commentsMap.put("comment",commentText);
                commentsMap.put("date",saveCurrentDate);
                commentsMap.put("time",saveCurrentTime);
                commentsMap.put("username",userName);

            PostsRef.child(RandomKey).updateChildren(commentsMap)
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(CommentsActivity.this,"你的留言已經上傳",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CommentsActivity.this,"你的留言有錯",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(CommentsActivity.this, PhotoBlog.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
