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
public class ExerciseRunning extends Fragment {
    private DatabaseReference mUsersDatabase,mDatabase;
    private static FirebaseAuth mAuth;
    private LinearLayoutManager mLayoutManager;
    private View mMainView;
    private RecyclerView mUsersList1;
    public Button button_of_task_execution,button_of_sports_monitoring,button_of_invitation;
    public String pTime;
    public static CircleImageView userImageView,first_image;

    public ExerciseRunning() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mMainView = inflater.inflate(R.layout.fragment_exercise_running, container, false);
        mUsersList1=(RecyclerView) mMainView.findViewById(R.id.running_list);
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
                String long_distance=dataSnapshot.child("exercise_count").child("running").child("long_distance").getValue().toString();
                String short_distance=dataSnapshot.child("exercise_count").child("running").child("short_distance").getValue().toString();
                String today_record=dataSnapshot.child("exercise_count").child("running").child("today_record").getValue().toString();
                String DateCheck=dataSnapshot.child("exercise_count").child("running").child("DateCheck").getValue().toString();
                String nowDate=Time.getToDate(System.currentTimeMillis());
                Log.i("現在是1",DateCheck);
                Log.i("現在是",nowDate);
                if(DateCheck.equals(nowDate)){
                    text_view_of_today_record_data.setText(today_record);
                }else {

                    mDatabase.child("exercise_count").child("running").child("DateCheck").setValue(nowDate);
                    mDatabase.child("exercise_count").child("running").child("today_count").setValue(0);
                    text_view_of_today_record_data.setText("0");
                }
                text_view_of_highest_record_data.setText(long_distance);
                text_view_of_lowest_record_data.setText(short_distance);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button_of_invitation=(Button)mMainView.findViewById(R.id.button_of_invitation);
        button_of_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kelvin_sit_up_invitation kelvin_sit_up_invitation1=kelvin_sit_up_invitation.newInstance("跑步邀請內容","去跑步:","公里");
                FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.content1_main,kelvin_sit_up_invitation1);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });
        pTime=Week.getWeek(System.currentTimeMillis());
        Log.i("今天是",pTime);


        button_of_task_execution=(Button)mMainView.findViewById(R.id.button_of_task_execution);
        if(pTime.equals("二")){
            button_of_task_execution.setVisibility(View.VISIBLE);
        }else{
            button_of_task_execution.setVisibility(View.INVISIBLE);
        }
        button_of_task_execution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Sit_up_task.class);
                intent.putExtra("ToolbarTitle","跑步每週任務");
                intent.putExtra("exerciseWeekTitle","去跑步");
                intent.putExtra("exerciseWeekUnit","公里");
                startActivity(intent);

            }
        });

        button_of_sports_monitoring=(Button)mMainView.findViewById(R.id.button_of_sports_monitoring);
        button_of_sports_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),RunningMonitor.class);
                startActivity(intent);
            }
        });

        return mMainView;
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<Users,RunningNewUsersViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Users, RunningNewUsersViewHolder>(
                Users.class,
                R.layout.users_single_layout,
                RunningNewUsersViewHolder.class,
                mUsersDatabase.orderByChild("running_all_count_sort")
        ) {
            @Override
            protected void populateViewHolder(RunningNewUsersViewHolder runningNewUsersViewHolder, Users users, int position) {
                runningNewUsersViewHolder.setDisplayName(users.getName());
                runningNewUsersViewHolder.setUserStatus("跑步全部記錄:");
                runningNewUsersViewHolder.setUserImage(users.getThumb_image(),getContext());
                runningNewUsersViewHolder.setRunningAllCount(users.getRunning_all_count());
                first_image=(CircleImageView)runningNewUsersViewHolder.mView.findViewById(R.id.first_image);
                if(position==0){
                    first_image.setVisibility(View.VISIBLE);
                    first_image.setImageResource(R.drawable.goldmedal);
                }
                if(position==1){
                    first_image.setVisibility(View.VISIBLE);
                    first_image.setImageResource(R.drawable.secondprize);
                }
                if(position==2){
                    first_image.setVisibility(View.VISIBLE);
                    first_image.setImageResource(R.drawable.bronzemedal);
                }

            }
        };

        mUsersList1.setAdapter(firebaseRecyclerAdapter);
    }

    public static class RunningNewUsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public RunningNewUsersViewHolder(View itemView) {
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

            userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

        public void setRunningAllCount(double running_all_count){
            TextView crunches_all_count_view=(TextView) mView.findViewById(R.id.crunches_all_count);
            crunches_all_count_view.setText(""+running_all_count+"公里");

        }


    }

}
