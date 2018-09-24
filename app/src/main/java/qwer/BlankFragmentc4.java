package qwer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import kelvin.tablayout.Data;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragmentc4.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragmentc4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentc4 extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mycm,mykg;
    private TextView title_need_running_exercise,need_running_exercise,title_database_today_running_exercise,database_today_running_exercise,title_check_exercise_running,check_exercise_running;
    private Data need_exercise_data;
    private DecimalFormat df = new DecimalFormat("0.00");
    private DatabaseReference runningDatabase;
    private static FirebaseAuth mAuth;
    private static double double_today_running_record;
    public static double TargetKM;


    private OnFragmentInteractionListener mListener;

    public BlankFragmentc4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragmentc4.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentc4 newInstance(String param1, String param2) {
        BlankFragmentc4 fragment = new BlankFragmentc4();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank_fragmentc4, null);
        view.setOnTouchListener(this);
        final int[] mySport = {0};
        final EditText userCM=(EditText)view.findViewById(R.id.myCM);
        final EditText userKG=(EditText)view.findViewById(R.id.myKG);
        final Spinner spinner=(Spinner)view.findViewById(R.id.SportListSpinner);
        final String[] SportList = {"請選擇運動項目","有氧運動","走路","跑步","伏地挺身","仰臥起坐"};
        final LinearLayout selelayout=(LinearLayout)view.findViewById(R.id.seleLayout);
        final LinearLayout sporttarget=(LinearLayout)view.findViewById(R.id.SportTargetLayout);
        final TextView sportitem=(TextView)view.findViewById(R.id.SportItem);
        final TextView progressnum=(TextView)view.findViewById(R.id.SportProgressNum);
        need_running_exercise=(TextView)view.findViewById(R.id.need_running_exercise);
        title_need_running_exercise=(TextView)view.findViewById(R.id.title_need_running_exercise);
        title_database_today_running_exercise=(TextView)view.findViewById(R.id.title_database_today_running_exercise);
        database_today_running_exercise=(TextView)view.findViewById(R.id.database_today_running_exercise);
        title_check_exercise_running=(TextView)view.findViewById(R.id.title_check_exercise_running);
        check_exercise_running=(TextView)view.findViewById(R.id.check_exercise_running);
        need_running_exercise.setVisibility(View.INVISIBLE);
        title_need_running_exercise.setVisibility(View.INVISIBLE);
        title_database_today_running_exercise.setVisibility(View.INVISIBLE);
        database_today_running_exercise.setVisibility(View.INVISIBLE);
        title_check_exercise_running.setVisibility(View.INVISIBLE);
        check_exercise_running.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> sportlist = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                SportList);
        spinner.setAdapter(sportlist);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mySport[0] =i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mySport[0] =0;
            }
        });//以下拉式列表選擇運動項目
        final Button checkbtn=(Button)view.findViewById(R.id.seleSport);
        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userCM.getText()==null||userKG.getText()==null){
                    Toast.makeText(getActivity(), "請先輸入身高體重", Toast.LENGTH_SHORT).show();

                }else if(mySport[0]==0){
                    Toast.makeText(getActivity(), "請先選擇運動項目", Toast.LENGTH_SHORT).show();
                }else{
                    mycm=Integer.valueOf(userCM.getText().toString());
                    mykg=Integer.valueOf(userKG.getText().toString());
                    Toast.makeText(getActivity(), mycm+"、"+mykg, Toast.LENGTH_SHORT).show();
                    //selelayout.setVisibility(View.GONE);

                    //sporttarget.setVisibility(View.VISIBLE);
                    sportitem.setText(SportList[mySport[0]]);
                    gototheSport(mycm,mykg,mySport[0]);//前往運動

                }
            }
        });//選擇運動項目後方可進入下一階段：運動進度

        final ProgressBar sportprogress=(ProgressBar)view.findViewById(R.id.SportProgressBar);
        final Button dothesport=(Button)view.findViewById(R.id.DoTheSport);
        final ImageView sportfinishimg=(ImageView)view.findViewById(R.id.SportFinishIMG);
        int progressMax=100;
        sportprogress.setMax(progressMax);
        dothesport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sportprogress.incrementProgressBy(10);//每次點擊時增加進度條(10%)
                if(sportprogress.getProgress()<progressMax) {//檢查進度條是否已滿
                    progressnum.setText("目前運動進度：" +
                            Integer.toString(sportprogress.getProgress()) + "/100");
                }else{//進度條達到100%，顯示目標達成畫面
                    progressnum.setText("今天的運動進度已完成(100/100)");
                    dothesport.setVisibility(View.GONE);
                    sportfinishimg.setVisibility(View.VISIBLE);
                }
            }
        });

        final TextView tdbk,tdlh,tddn,tdkll;//今日飲食紀錄
        tdbk=(TextView)view.findViewById(R.id.tdBK);
        tdlh=(TextView)view.findViewById(R.id.tdLH);
        tddn=(TextView)view.findViewById(R.id.tdDN);
        tdkll=(TextView)view.findViewById(R.id.tdKLL);
        theDate todate=((MainActivity)getActivity()).getTodayEaetdInfo();//取得今日日記資訊
        if(todate==null){}else {
            tdbk.setText("早餐："+Integer.toString(todate.todayKLL(0)));
            tdlh.setText("午餐："+Integer.toString(todate.todayKLL(1)));
            tddn.setText("晚餐："+Integer.toString(todate.todayKLL(2)));
            tdkll.setText("今日累計熱量：" + todate.todayKLL() + "大卡");
        }


        ImageButton qwera2=(ImageButton)view.findViewById(R.id.imageButtona2);
        qwera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new BlankFragmentc2(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        ImageButton qwera3=(ImageButton)view.findViewById(R.id.imageButtona3);
        qwera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new BlankFragmentc3(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        ImageButton qwera1=(ImageButton)view.findViewById(R.id.imageButtona1);
        qwera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new BlankFragmentc1(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void gototheSport(int mycm, int mykg, int mysp) {
        switch (mysp){
            case 1:
                break;
            case 2:
                break;
            case 3://跑步

                //TargetKM=((mycm*6.25-105)+(mykg*10))/(mykg*1.036)*0.2;


                //請以參數 TargetKM 作為目標距離進行運動偵測
                mAuth = FirebaseAuth.getInstance();
                Log.i("我的id:",""+mAuth.getCurrentUser().getUid());
                //need_running_exercise.setVisibility(View.VISIBLE);
                //title_need_running_exercise.setVisibility(View.VISIBLE);
                //need_running_exercise.setText(""+df.format(TargetKM)+"公里");

                runningDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
                runningDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TargetKM=((mycm*6.25-105)+(mykg*10))/(mykg*1.036)*0.2;
                        need_running_exercise.setVisibility(View.VISIBLE);
                        title_need_running_exercise.setVisibility(View.VISIBLE);
                        need_running_exercise.setText(""+df.format(TargetKM)+"公里");
                        title_database_today_running_exercise.setVisibility(View.VISIBLE);
                        database_today_running_exercise.setVisibility(View.VISIBLE);
                        Log.i("比較距離",""+TargetKM);
                        String today_running_record=dataSnapshot.child("exercise_count").child("running").child("today_record").getValue().toString();
                        double_today_running_record=Double.parseDouble(today_running_record);
                        database_today_running_exercise.setText(""+double_today_running_record+"公里");
                        title_check_exercise_running.setVisibility(View.VISIBLE);
                        check_exercise_running.setVisibility(View.VISIBLE);
                        if(double_today_running_record>=TargetKM){
                            check_exercise_running.setText("有達標");
                        }else{
                            check_exercise_running.setText("沒有達標");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //title_database_today_running_exercise.setVisibility(View.INVISIBLE);
                //database_today_running_exercise.setVisibility(View.INVISIBLE);


                break;
            case 4:
                break;

            case 5:
                break;
            default:
                break;

        }
    }

    private String getEatedString(int[] foods) {
        String Count = null;
        for(int i=0;i<9;i++){
            if(foods[i]>0){
                //Count.concat(((MainActivity)getActivity()).food_list.get(i)+"、");
            }
        }
        return "尚未紀錄";
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
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
