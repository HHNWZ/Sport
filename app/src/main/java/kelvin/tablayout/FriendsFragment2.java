package kelvin.tablayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

public class FriendsFragment2 extends Fragment implements View.OnTouchListener{

    private RecyclerView mFriendsList;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mMainView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";
    private static final String ARG_PARAM9 = "param9";
    private static final String ARG_PARAM10= "param10";
    private static final String ARG_PARAM11 = "param11";
    private static final String ARG_PARAM12 = "param12";
    private static final String ARG_PARAM13 = "param13";
    private static final String ARG_PARAM14 = "param14";

    private TextView text_view_of_exercise_type,text_view_of_exercise_start_time,text_view_of_exercise_end_time;

    private String exercise_type;
    private String exercise_data_count;
    private String exercise_data;
    private String exercise_unit;
    private String start_year_of_invitation;
    private String start_month_of_invitation;
    private String start_day_of_invitation;
    private String start_hour_of_invitation;
    private String start_minute_of_invitation;
    private String end_year_of_invitation;
    private String end_month_of_invitation;
    private String end_day_of_invitation;
    private String end_hour_of_invitation;
    private String end_minute_of_invitation;

    private kelvin_running_tag_friend.OnFragmentInteractionListener mListener;


    public FriendsFragment2() {
        // Required empty public constructor
    }

    public static FriendsFragment2 newInstance(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9, String param10, String param11,String param12,String param13,String param14) {
        FriendsFragment2 fragment = new FriendsFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putString(ARG_PARAM8, param8);
        args.putString(ARG_PARAM9, param9);
        args.putString(ARG_PARAM10, param10);
        args.putString(ARG_PARAM11, param11);
        args.putString(ARG_PARAM12, param12);
        args.putString(ARG_PARAM13, param13);
        args.putString(ARG_PARAM14,param14);


        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exercise_type=getArguments().getString(ARG_PARAM1);
            exercise_data_count=getArguments().getString(ARG_PARAM2);
            exercise_data = getArguments().getString(ARG_PARAM3);
            exercise_unit=getArguments().getString(ARG_PARAM4);
            start_year_of_invitation = getArguments().getString(ARG_PARAM5);
            start_month_of_invitation = getArguments().getString(ARG_PARAM6);
            start_day_of_invitation = getArguments().getString(ARG_PARAM7);
            start_hour_of_invitation = getArguments().getString(ARG_PARAM8);
            start_minute_of_invitation = getArguments().getString(ARG_PARAM9);
            end_year_of_invitation = getArguments().getString(ARG_PARAM10);
            end_month_of_invitation = getArguments().getString(ARG_PARAM11);
            end_day_of_invitation = getArguments().getString(ARG_PARAM12);
            end_hour_of_invitation = getArguments().getString(ARG_PARAM13);
            end_minute_of_invitation= getArguments().getString(ARG_PARAM14);

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_friends_fragment2, container, false);

        mFriendsList = (RecyclerView) mMainView.findViewById(R.id.friends_list);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);




        mFriendsList.setHasFixedSize(true);
        mFriendsList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Friends, FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>(

                Friends.class,
                R.layout.users_single_layout,
                FriendsViewHolder.class,
                mFriendsDatabase


        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder friendsViewHolder, Friends friends, int i) {

                friendsViewHolder.setDate(friends.getDate());

                final String list_user_id = getRef(i).getKey();
                final String exercise_typeFF=exercise_type.toString();
                final String exercise_data_countFF=exercise_data_count.toString();
                final String exercise_dataFF=exercise_data.toString();
                final String exercise_unitFF=exercise_unit.toString();
                final String start_year_of_invitationFF=start_year_of_invitation.toString();
                final String start_month_of_invitationFF=start_month_of_invitation.toString();
                final String start_day_of_invitationFF=start_day_of_invitation.toString();
                final String start_hour_of_invitationFF=start_hour_of_invitation.toString();
                final String start_minute_of_invitationFF=start_minute_of_invitation.toString();
                final String end_year_of_invitationFF=end_year_of_invitation.toString();
                final String end_month_of_invitationFF=end_month_of_invitation.toString();
                final String end_day_of_invitationFF=end_day_of_invitation.toString();
                final String end_hour_of_invitationFF=end_hour_of_invitation.toString();
                final String end_minute_of_invitationFF=end_minute_of_invitation.toString();

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();

                        if(dataSnapshot.hasChild("online")) {

                            String userOnline = dataSnapshot.child("online").getValue().toString();
                            friendsViewHolder.setUserOnline(userOnline);

                        }

                        friendsViewHolder.setName(userName);
                        friendsViewHolder.setUserImage(userThumb, getContext());

                        friendsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                chatIntent.putExtra("user_id", list_user_id);
                                chatIntent.putExtra("user_name", userName);
                                chatIntent.putExtra("exercise_type",exercise_typeFF );
                                chatIntent.putExtra("exercise_data_count",exercise_data_countFF );
                                chatIntent.putExtra("exercise_data", exercise_dataFF);
                                chatIntent.putExtra("exercise_unit", exercise_unitFF);
                                chatIntent.putExtra("start_year_of_invitation",start_year_of_invitationFF );
                                chatIntent.putExtra("start_month_of_invitation", start_month_of_invitationFF);
                                chatIntent.putExtra("start_day_of_invitation",start_day_of_invitationFF );
                                chatIntent.putExtra("start_hour_of_invitation",start_hour_of_invitationFF );
                                chatIntent.putExtra("start_minute_of_invitation",start_minute_of_invitationFF );
                                chatIntent.putExtra("end_year_of_invitation", end_year_of_invitationFF);
                                chatIntent.putExtra("end_month_of_invitation", end_month_of_invitationFF);
                                chatIntent.putExtra("end_day_of_invitation", end_day_of_invitationFF);
                                chatIntent.putExtra("end_hour_of_invitation",end_hour_of_invitationFF );
                                chatIntent.putExtra("end_minute_of_invitation", end_minute_of_invitationFF);

                                startActivity(chatIntent);

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };

        mFriendsList.setAdapter(friendsRecyclerViewAdapter);


    }



    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDate(String date){

            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(date);

        }

        public void setName(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

        public void setUserOnline(String online_status) {

            ImageView userOnlineView = (ImageView) mView.findViewById(R.id.user_single_online_icon);

            if(online_status.equals("true")){

                userOnlineView.setVisibility(View.VISIBLE);

            } else {

                userOnlineView.setVisibility(View.INVISIBLE);

            }

        }


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String Tag, String number) {
        if (mListener != null) {
            mListener.onFragmentInteraction(Tag,number);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof kelvin_running_tag_friend.OnFragmentInteractionListener) {
            mListener = (kelvin_running_tag_friend.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Tag, String number);
    }


}
