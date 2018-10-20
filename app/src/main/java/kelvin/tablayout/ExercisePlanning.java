package kelvin.tablayout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExercisePlanning#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExercisePlanning extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference exercise_plan_database,exercise_count_database;
    private static FirebaseAuth mAuth;

    private static TextView walking_exercise_plan3;
    private static TextView running_exercise_plan3;
    private static TextView yoga_exercise_plan3;
    private static TextView squats_exercise_plan3;
    private static TextView crunches_exercise_plan3;

    private static TextView walking_exercise_plan4;
    private static TextView running_exercise_plan4;
    private static TextView yoga_exercise_plan4;
    private static TextView squats_exercise_plan4;
    private static TextView crunches_exercise_plan4;

    private Button walking_exercise_planning;
    private  Button running_exercise_planning;
    private  Button yoga_exercise_planning;
    private  Button squats_exercise_planning;
    private  Button crunches_exercise_planning;

    private SeekBar walking_seek_bar;
    private SeekBar running_seek_bar;
    private SeekBar yoga_seek_bar;
    private SeekBar squats_seek_bar;
    private SeekBar crunches_seek_bar;

    public Data exercise_plan =new Data();

    public ExercisePlanning() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExercisePlanning.
     */
    // TODO: Rename and change types and number of parameters
    public static ExercisePlanning newInstance(String param1, String param2) {
        ExercisePlanning fragment = new ExercisePlanning();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
        exercise_plan_database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise_plan");
        exercise_count_database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise_count");
        Log.i("我的id",""+mAuth.getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_planning, null);

        walking_exercise_plan3=(TextView)view.findViewById(R.id.walking_exercise_plan3);
        running_exercise_plan3=(TextView)view.findViewById(R.id.running_exercise_plan3) ;
        yoga_exercise_plan3=(TextView)view.findViewById(R.id.yoga_exercise_plan3);
        squats_exercise_plan3=(TextView)view.findViewById(R.id.squats_exercise_plan3);
        crunches_exercise_plan3=(TextView)view.findViewById(R.id.crunches_exercise_plan3);

        walking_exercise_plan4=(TextView)view.findViewById(R.id.walking_exercise_plan4);
        running_exercise_plan4=(TextView)view.findViewById(R.id.running_exercise_plan4) ;
        yoga_exercise_plan4=(TextView)view.findViewById(R.id.yoga_exercise_plan4);
        squats_exercise_plan4=(TextView)view.findViewById(R.id.squats_exercise_plan4);
        crunches_exercise_plan4=(TextView)view.findViewById(R.id.crunches_exercise_plan4);

        walking_exercise_planning=(Button) view.findViewById(R.id.walking_exercise_planning);
        running_exercise_planning=(Button) view.findViewById(R.id.running_exercise_planning);
        yoga_exercise_planning=(Button) view.findViewById(R.id.yoga_exercise_planning);
        squats_exercise_planning=(Button)view.findViewById(R.id.squats_exercise_planning);
        crunches_exercise_planning=(Button)view.findViewById(R.id.crunches_exercise_planning);

        walking_seek_bar=(SeekBar)view.findViewById(R.id.walking_seek_bar);
        running_seek_bar=(SeekBar)view.findViewById(R.id.running_seek_bar);
        yoga_seek_bar=(SeekBar)view.findViewById(R.id.yoga_seek_bar);
        squats_seek_bar=(SeekBar)view.findViewById(R.id.squats_seek_bar);
        crunches_seek_bar=(SeekBar)view.findViewById(R.id.crunches_seek_bar);
        walking_seek_bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        running_seek_bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        yoga_seek_bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        squats_seek_bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        crunches_seek_bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        exercise_plan_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String walking_exercise_plan_data=dataSnapshot.child("walking").getValue().toString();
                String running_exercise_plan_data=dataSnapshot.child("running").getValue().toString();
                String squats_exercise_plan_data=dataSnapshot.child("squats").getValue().toString();
                String crunches_exercise_plan_data=dataSnapshot.child("crunches").getValue().toString();
                String yoga_exercise_plan_data=dataSnapshot.child("yoga").getValue().toString();

                double walking_exercise_plan_data_double=Double.parseDouble(walking_exercise_plan_data);
                double running_exercise_plan_data_double=Double.parseDouble(running_exercise_plan_data);
                float yoga_exercise_plan_data_float=Float.parseFloat(yoga_exercise_plan_data);
                int squats_exercise_plan_data_int=Integer.parseInt(squats_exercise_plan_data);
                int crunches_exercise_plan_data_int=Integer.parseInt(crunches_exercise_plan_data);

                exercise_plan.setWalking_exercise_plan_data(walking_exercise_plan_data_double);
                exercise_plan.setRunning_exercise_plan_data(running_exercise_plan_data_double);
                exercise_plan.setYoga_exercise_plan_data(yoga_exercise_plan_data_float);
                exercise_plan.setSquats_exercise_plan_data(squats_exercise_plan_data_int);
                exercise_plan.setCrunches_exercise_plan_data(crunches_exercise_plan_data_int);

                if (walking_exercise_plan_data_double==0){
                    Toast.makeText(getContext(),"你未輸入BMI,請點擊BMI計算",Toast.LENGTH_SHORT).show();
                    walking_exercise_planning.setVisibility(View.INVISIBLE);
                    running_exercise_planning.setVisibility(View.INVISIBLE);
                    yoga_exercise_planning.setVisibility(View.INVISIBLE);
                    squats_exercise_planning.setVisibility(View.INVISIBLE);
                    crunches_exercise_planning.setVisibility(View.INVISIBLE);

                }else {
                    walking_exercise_planning.setVisibility(View.VISIBLE);
                    running_exercise_planning.setVisibility(View.VISIBLE);
                    yoga_exercise_planning.setVisibility(View.VISIBLE);
                    squats_exercise_planning.setVisibility(View.VISIBLE);
                    crunches_exercise_planning.setVisibility(View.VISIBLE);
                    walking_exercise_plan3.setText("你今天需要步行"+walking_exercise_plan_data+"公里");
                    running_exercise_plan3.setText("你今天需要跑步"+running_exercise_plan_data+"公里");
                    yoga_exercise_plan3.setText("你今天需要做瑜伽"+yoga_exercise_plan_data+"分鐘");
                    squats_exercise_plan3.setText("你今天需要做深蹲"+squats_exercise_plan_data+"次");
                    crunches_exercise_plan3.setText("你今天需要做仰臥起"+crunches_exercise_plan_data+"次");
                    exercise_count_database.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String walking_today_record=dataSnapshot.child("walking").child("today_record").getValue().toString();
                            String running_today_record=dataSnapshot.child("running").child("today_record").getValue().toString();
                            String yoga_today_time=dataSnapshot.child("yoga").child("today_time").getValue().toString();
                            String squats_today_count=dataSnapshot.child("squats").child("today_count").getValue().toString();
                            String crunches_today_count=dataSnapshot.child("crunches").child("today_count").getValue().toString();
                            double walking_today_record_double=Double.parseDouble(walking_today_record);
                            double running_today_record_double=Double.parseDouble(running_today_record);
                            long  yoga_today_record_long=Long.parseLong(yoga_today_time);
                            float  yoga_today_record_float=Time.yogaWeekminute(yoga_today_record_long);
                            int squats_today_count_int=Integer.parseInt(squats_today_count);
                            int crunches_today_count_int=Integer.parseInt(crunches_today_count);
                            Log.i("步行今天記錄",""+walking_today_record_double);
                            Log.i("今天需要步行的距離",""+exercise_plan.getWalking_exercise_plan_data());
                            Log.i("跑步今天記錄",""+running_today_record_double);
                            Log.i("跑步需要步行的距離",""+exercise_plan.getRunning_exercise_plan_data());
                            Log.i("瑜伽今天記錄",""+yoga_today_record_float);
                            Log.i("今天需要做瑜伽的時間",""+exercise_plan.getYoga_exercise_plan_data());
                            Log.i("深蹲今天記錄",""+squats_today_count_int);
                            Log.i("今天需要做深蹲的次數",""+exercise_plan.getSquats_exercise_plan_data());
                            Log.i("仰臥起坐今天記錄",""+crunches_today_count_int);
                            Log.i("今天需要做仰臥起坐的次數",""+exercise_plan.getCrunches_exercise_plan_data());

                            walking_seek_bar.setMax((int)exercise_plan.getWalking_exercise_plan_data());
                            running_seek_bar.setMax((int)exercise_plan.getRunning_exercise_plan_data());
                            yoga_seek_bar.setMax((int)exercise_plan.getYoga_exercise_plan_data());
                            squats_seek_bar.setMax((int)exercise_plan.getSquats_exercise_plan_data());
                            crunches_seek_bar.setMax(exercise_plan.getCrunches_exercise_plan_data());
                            if(walking_today_record_double>=exercise_plan.getWalking_exercise_plan_data()){
                                walking_exercise_plan4.setText("你已經完成目標");
                                walking_seek_bar.setProgress((int)exercise_plan.getWalking_exercise_plan_data());
                                walking_exercise_planning.setVisibility(View.INVISIBLE);
                            }else if(walking_today_record_double<exercise_plan.getWalking_exercise_plan_data()){
                                walking_exercise_plan4.setText("你目前完成"+walking_today_record_double+"公里");
                                walking_seek_bar.setProgress((int)walking_today_record_double);
                                walking_exercise_planning.setVisibility(View.VISIBLE);
                            }
                            if(running_today_record_double>=exercise_plan.getRunning_exercise_plan_data()){
                                running_exercise_plan4.setText("你已經完成目標");
                                running_seek_bar.setProgress((int)exercise_plan.getRunning_exercise_plan_data());
                                running_exercise_planning.setVisibility(View.INVISIBLE);
                            }else if(running_today_record_double<exercise_plan.getRunning_exercise_plan_data()){
                                running_exercise_plan4.setText("你目前完成"+running_today_record_double+"公里");
                                running_seek_bar.setProgress((int)running_today_record_double);
                                running_exercise_planning.setVisibility(View.VISIBLE);
                            }
                            if(yoga_today_record_float>=exercise_plan.getYoga_exercise_plan_data()){
                                yoga_exercise_plan4.setText("你已經完成目標");
                                yoga_seek_bar.setProgress((int)exercise_plan.getYoga_exercise_plan_data());
                                yoga_exercise_planning.setVisibility(View.INVISIBLE);
                            }else if(yoga_today_record_float<exercise_plan.getYoga_exercise_plan_data()){
                                yoga_exercise_plan4.setText("你目前完成"+Time.changeYogaTime(yoga_today_record_long));
                                yoga_seek_bar.setProgress((int)yoga_today_record_float);
                                yoga_exercise_planning.setVisibility(View.VISIBLE);
                            }
                            if(squats_today_count_int>=exercise_plan.getSquats_exercise_plan_data()){
                                squats_exercise_plan4.setText("你已經完成目標");
                                squats_seek_bar.setProgress(exercise_plan.getSquats_exercise_plan_data());
                                squats_exercise_planning.setVisibility(View.INVISIBLE);

                            }else if(squats_today_count_int<exercise_plan.getSquats_exercise_plan_data()){
                                squats_exercise_plan4.setText("你目前完成"+squats_today_count_int+"次");
                                squats_seek_bar.setProgress(squats_today_count_int);
                                squats_exercise_planning.setVisibility(View.VISIBLE);
                            }
                            if(crunches_today_count_int>=exercise_plan.getCrunches_exercise_plan_data()){
                                crunches_exercise_plan4.setText("你已經完成目標");
                                crunches_seek_bar.setProgress(exercise_plan.getCrunches_exercise_plan_data());
                                crunches_exercise_planning.setVisibility(View.INVISIBLE);

                            }else if(crunches_today_count_int<exercise_plan.getCrunches_exercise_plan_data()){
                                crunches_exercise_plan4.setText("你目前完成"+crunches_today_count_int+"次");
                                crunches_seek_bar.setProgress(crunches_today_count_int);
                                crunches_exercise_planning.setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
