package kelvin.tablayout;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
public class ExerciseCrunches extends Fragment {
    private DatabaseReference mUsersDatabase,mDatabase;
    private static FirebaseAuth mAuth;
    private LinearLayoutManager mLayoutManager;
    private View mMainView;
    private RecyclerView mUsersList1;
    public Button button_of_task_execution,button_of_sports_monitoring,button_of_invitation;
    public String pTime;

    public ExerciseCrunches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mMainView = inflater.inflate(R.layout.fragment_exercise_crunches, container, false);
        mUsersList1=(RecyclerView) mMainView.findViewById(R.id.crunches_list);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        mUsersList1.setHasFixedSize(true);
        mUsersList1.setLayoutManager(new LinearLayoutManager(getContext()));
        TextView text_view_of_today_record_data=(TextView)mMainView.findViewById(R.id.text_view_of_today_record_data);
        TextView text_view_of_lowest_record_data=(TextView)mMainView.findViewById(R.id.text_view_of_lowest_record_data) ;
        TextView text_view_of_highest_record_data=(TextView)mMainView.findViewById(R.id.text_view_of_highest_record_data);
        Log.i("為什麼","1");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String big_count=dataSnapshot.child("exercise_count").child("crunches").child("big_count").getValue().toString();
                String small_count=dataSnapshot.child("exercise_count").child("crunches").child("small_count").getValue().toString();
                String today_count=dataSnapshot.child("exercise_count").child("crunches").child("today_count").getValue().toString();
                text_view_of_highest_record_data.setText(big_count);
                text_view_of_lowest_record_data.setText(small_count);
                text_view_of_today_record_data.setText(today_count);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button_of_invitation=(Button)mMainView.findViewById(R.id.button_of_invitation);
        button_of_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kelvin_sit_up_invitation kelvin_sit_up_invitation1=kelvin_sit_up_invitation.newInstance("仰臥起坐邀請內容","做仰臥起坐:","次");
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.content1_main,kelvin_sit_up_invitation1);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });
        pTime=Week.getWeek(System.currentTimeMillis());
        Log.i("今天是",pTime);


        button_of_task_execution=(Button)mMainView.findViewById(R.id.button_of_task_execution);
        if(pTime.equals("五")){
            button_of_task_execution.setVisibility(View.VISIBLE);
        }else{
            button_of_task_execution.setVisibility(View.INVISIBLE);
        }
        button_of_task_execution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Sit_up_task.class);
                intent.putExtra("ToolbarTitle","仰臥起坐每週任務");
                intent.putExtra("exerciseWeekTitle","做仰臥起坐");
                intent.putExtra("exerciseWeekUnit","次");
                startActivity(intent);

            }
        });

        button_of_sports_monitoring=(Button)mMainView.findViewById(R.id.button_of_sports_monitoring);
        button_of_sports_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CrunchesMonitor.class);
                startActivity(intent);
            }
        });

        return mMainView;
    }

    @Override
    public void  onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<Users,CrunchesNewUsersViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, CrunchesNewUsersViewHolder>(
                Users.class,
                R.layout.users_single_layout,
                CrunchesNewUsersViewHolder.class,
                mUsersDatabase
        ) {
            @Override
            protected void populateViewHolder(CrunchesNewUsersViewHolder crunchesNewUsersViewHolder, Users users, int position) {
                crunchesNewUsersViewHolder.setDisplayName(users.getName());
                crunchesNewUsersViewHolder.setUserStatus("仰臥起坐全部記錄:");
                crunchesNewUsersViewHolder.setUserImage(users.getThumb_image(),getContext());
                crunchesNewUsersViewHolder.setCrunchesAllCount(users.getCrunches_all_count());
            }
        };

        mUsersList1.setAdapter(firebaseRecyclerAdapter);
    }


    public static class CrunchesNewUsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public CrunchesNewUsersViewHolder(View itemView) {
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

        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

        public void setCrunchesAllCount(int crunches_all_count){
            TextView crunches_all_count_view=(TextView) mView.findViewById(R.id.crunches_all_count);
            crunches_all_count_view.setText(""+crunches_all_count+"次");

        }


    }

}
