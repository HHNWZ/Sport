package kelvin.tablayout;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseYoga extends Fragment {
    private DatabaseReference mUsersDatabase,mDatabase;
    private static FirebaseAuth mAuth;
    private LinearLayoutManager mLayoutManager;
    private View mMainView;
    private RecyclerView mUsersList1;
    public Button button_of_task_execution,button_of_sports_monitoring,button_of_invitation;
    public String pTime;
    public static CircleImageView userImageView,first_image;
    private SwipeRefreshLayout mRefreshLayout;

    public ExerciseYoga() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mMainView = inflater.inflate(R.layout.fragment_exercise_yoga, container, false);
        mUsersList1=(RecyclerView) mMainView.findViewById(R.id.yoga_list);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);
        mRefreshLayout=(SwipeRefreshLayout)mMainView.findViewById(R.id.yoga_swipe_layout);
        mRefreshLayout.setColorSchemeColors(Color.rgb(115,196,217));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                        onStart();
                    }}, 1000);
            }
        });
        mUsersList1.setHasFixedSize(true);
        mUsersList1.setLayoutManager(new LinearLayoutManager(getContext()));
        TextView text_view_of_today_record_data=(TextView)mMainView.findViewById(R.id.text_view_of_today_record_data);
        TextView text_view_of_lowest_record_data=(TextView)mMainView.findViewById(R.id.text_view_of_lowest_record_data) ;
        TextView text_view_of_highest_record_data=(TextView)mMainView.findViewById(R.id.text_view_of_highest_record_data);
        Log.i("為什麼","1");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String long_time=dataSnapshot.child("exercise_count").child("yoga").child("long_time").getValue().toString();
                String short_time=dataSnapshot.child("exercise_count").child("yoga").child("short_time").getValue().toString();
                String today_time=dataSnapshot.child("exercise_count").child("yoga").child("today_time").getValue().toString();
                String DateCheck=dataSnapshot.child("exercise_count").child("yoga").child("DateCheck").getValue().toString();
                long today_time1=Long.parseLong(today_time);
                long long_time1=Long.parseLong(long_time);
                long short_time1=Long.parseLong(short_time);
                String nowDate=Time.getToDate(System.currentTimeMillis());
                Log.i("現在是1",DateCheck);
                Log.i("現在是",nowDate);
                if(DateCheck.equals(nowDate)){
                    Log.i("我進來了","是");
                    Log.i("今天瑜伽運動量",""+today_time1);
                    Log.i("轉換後的運動量",""+Time.changeYogaTime(today_time1));
                    text_view_of_today_record_data.setText(""+Time.changeYogaTime(today_time1));

                }else{

                    mDatabase.child("exercise_count").child("yoga").child("DateCheck").setValue(nowDate);
                    mDatabase.child("exercise_count").child("yoga").child("today_time").setValue(0);
                    text_view_of_today_record_data.setText("0秒");
                }
                text_view_of_highest_record_data.setText(""+Time.changeYogaTime(long_time1));
                text_view_of_lowest_record_data.setText(""+Time.changeYogaTime(short_time1));



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button_of_invitation=(Button)mMainView.findViewById(R.id.button_of_invitation);
        button_of_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Yoga_dare.class);
                startActivity(intent);
            }

        });
        pTime=Week.getWeek(System.currentTimeMillis());
        Log.i("今天是",pTime);


        button_of_task_execution=(Button)mMainView.findViewById(R.id.button_of_task_execution);
        if(pTime.equals("十")){
            button_of_task_execution.setVisibility(View.VISIBLE);
        }else{
            button_of_task_execution.setVisibility(View.INVISIBLE);
        }
        button_of_task_execution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Yoga_task.class);
                startActivity(intent);

            }
        });

        button_of_sports_monitoring=(Button)mMainView.findViewById(R.id.button_of_sports_monitoring);
        button_of_sports_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),YogaDayHistory.class);
                startActivity(intent);
            }
        });

        return mMainView;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Users,YogaNewUsersViewHolder>firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<Users, YogaNewUsersViewHolder>(
                Users.class,
                R.layout.users_single_layout,
                YogaNewUsersViewHolder.class,
                mUsersDatabase.orderByChild("yoga_all_count_sort")
        ) {
            @Override
            protected void populateViewHolder(YogaNewUsersViewHolder yogaNewUsersViewHolder, Users users, int position) {
                yogaNewUsersViewHolder.setDisplayName(users.getName());
                yogaNewUsersViewHolder.setUserStatus("瑜伽全部記錄:");
                yogaNewUsersViewHolder.setUserImage(users.getThumb_image());
                yogaNewUsersViewHolder.setYogaAllCount(Time.changeYogaTime(users.getYoga_all_count()));
                first_image=(CircleImageView)yogaNewUsersViewHolder.mView.findViewById(R.id.first_image);
                if(position==0){
                    first_image.setVisibility(View.VISIBLE);
                    first_image.setImageResource(R.drawable.goldmedal);//
                }
                else if(position==1){
                    first_image.setVisibility(View.VISIBLE);
                    first_image.setImageResource(R.drawable.secondprize);
                }
                else if(position==2){
                    first_image.setVisibility(View.VISIBLE);
                    first_image.setImageResource(R.drawable.bronzemedal);
                }else {
                    first_image.setVisibility(View.INVISIBLE);
                }
            }
        };
        mUsersList1.setAdapter(firebaseRecyclerAdapter);
    }

    public static class YogaNewUsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public YogaNewUsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayName(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setUserStatus(String status){

            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(status);


        }

        public void setUserImage(String thumb_image){

            userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);

            Picasso.get().load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

        public void setYogaAllCount(String yogaAllCount){
            TextView crunches_all_count_view=(TextView) mView.findViewById(R.id.crunches_all_count);
            crunches_all_count_view.setText(yogaAllCount);
        }


    }



}
