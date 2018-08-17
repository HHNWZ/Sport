package kelvin.tablayout;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a888888888.sport.BackHandlerHelper;
import com.example.a888888888.sport.FragmentBackHandler;
import com.example.a888888888.sport.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelvinPushUpFragment extends Fragment implements FragmentBackHandler {

    private static final int DEFAULT_DATA = 0;
    private static final int SUBCOLUMNS_DATA = 1;
    private static final int STACKED_DATA = 2;
    private static final int NEGATIVE_SUBCOLUMNS_DATA = 3;
    private static final int NEGATIVE_STACKED_DATA = 4;

    private ColumnChartView chart_of_push_up_today_record;
    private ColumnChartData data_of_push_up_today_record;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private int dataType = DEFAULT_DATA;
    public String pTime;
    public Button button_of_task_execution,button_of_sports_monitoring;
    private static DatabaseReference mDatabase,mDatabase1;
    private static FirebaseAuth mAuth;
    public static ArrayList<Post>list;
    private RecyclerView mSquatsRecoedList;


    //private static ArrayList<Post> list = new ArrayList<>();

    public KelvinPushUpFragment() {
        // Required empty public constructor////
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mDatabase1= FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase1.keepSynced(true);
        View rootView= inflater.inflate(R.layout.fragment_squats, container, false);
        mSquatsRecoedList=(RecyclerView)rootView.findViewById(R.id.squats_list);
        TextView text_view_of_today_record_data=(TextView)rootView.findViewById(R.id.text_view_of_today_record_data);
        TextView text_view_of_lowest_record_data=(TextView)rootView.findViewById(R.id.text_view_of_lowest_record_data) ;
        TextView text_view_of_highest_record_data=(TextView)rootView.findViewById(R.id.text_view_of_highest_record_data);
        TextView text_view_of_today_record_unit=(TextView)rootView.findViewById(R.id.text_view_of_today_record_unit);
        TextView text_view_of_lowest_record_unit=(TextView)rootView.findViewById(R.id.text_view_of_lowest_record_unit) ;
        TextView text_view_of_highest_record_unit=(TextView)rootView.findViewById(R.id.text_view_of_highest_record_unit);

        //text_view_of_today_record_data.setText("10");
        //text_view_of_highest_record_data.setText("15");
        //text_view_of_lowest_record_data.setText("5");
        text_view_of_today_record_unit.setText("次");
        text_view_of_lowest_record_unit.setText("次");
        text_view_of_highest_record_unit.setText("次");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String big_count=dataSnapshot.child("exercise_count").child("squats").child("big_count").getValue().toString();
                String small_count=dataSnapshot.child("exercise_count").child("squats").child("small_count").getValue().toString();
                String today_count=dataSnapshot.child("exercise_count").child("squats").child("today_count").getValue().toString();
                text_view_of_highest_record_data.setText(big_count);
                text_view_of_lowest_record_data.setText(small_count);
                text_view_of_today_record_data.setText(today_count);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        final Button button_of_invitation=(Button)rootView.findViewById(R.id.button_of_invitation);
        button_of_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_kelvin_running_invitation,new kelvin_push_up_invitation(),null)
                        .addToBackStack(null)
                        .commit();
            }

        });
        pTime=Week.getWeek(System.currentTimeMillis());
        Log.i("今天是",pTime);


       button_of_task_execution=(Button)rootView.findViewById(R.id.button_of_task_execution);
        if(pTime.equals("五")){
            button_of_task_execution.setVisibility(View.VISIBLE);
        }else{
            button_of_task_execution.setVisibility(View.INVISIBLE);
        }
        button_of_task_execution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Push_up_task.class);
                startActivity(intent);

            }
        });

        //chart_of_push_up_today_record.setZoomEnabled(false);
        button_of_sports_monitoring=(Button)rootView.findViewById(R.id.button_of_sports_monitoring);
        button_of_sports_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SquatsMonitor.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<Users, NewUsersViewHolderSquats> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, NewUsersViewHolderSquats>(
                Users.class,
                R.layout.users_single_layout,
                NewUsersViewHolderSquats.class,
                mDatabase1
        ) {
            @Override
            protected void populateViewHolder(NewUsersViewHolderSquats newUsersViewHolderSquats, Users users, int position) {
                newUsersViewHolderSquats.setDisplayName(users.getName());
                newUsersViewHolderSquats.setUserStatus("深蹲總記錄");
                newUsersViewHolderSquats.setUserImage(users.getThumb_image(),getContext());
                newUsersViewHolderSquats.setCrunchesAllCount(users.getCrunches_all_count());
            }
        };
        mSquatsRecoedList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class NewUsersViewHolderSquats extends RecyclerView.ViewHolder {

        View mView;

        public NewUsersViewHolderSquats(View itemView) {
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
            crunches_all_count_view.setText(""+crunches_all_count);

        }


    }



}
