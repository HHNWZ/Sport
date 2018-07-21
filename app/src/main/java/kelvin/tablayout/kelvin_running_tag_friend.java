package kelvin.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.Null;
import com.example.a888888888.sport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link kelvin_running_tag_friend.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link kelvin_running_tag_friend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class kelvin_running_tag_friend extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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



    // TODO: Rename and change types of parameters
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


    private Toolbar toolbar;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Student> studentList;

    private Button btnSelection;
    private TextView text_view_of_exercise_type,text_view_of_exercise_start_time,text_view_of_exercise_end_time;

    private OnFragmentInteractionListener mListener;

    public kelvin_running_tag_friend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment kelvin_running_tag_friend.
     */
    // TODO: Rename and change types and number of parameters
    public static kelvin_running_tag_friend newInstance(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String param9, String param10, String param11,String param12,String param13,String param14) {
        kelvin_running_tag_friend fragment = new kelvin_running_tag_friend();
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
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_kelvin_running_tag_friend, null);
        text_view_of_exercise_type=(TextView)view.findViewById(R.id.text_view_of_exercise_type);
        text_view_of_exercise_start_time=(TextView)view.findViewById(R.id.text_view_of_exercise_start_time);
        text_view_of_exercise_end_time=(TextView)view.findViewById(R.id.text_view_of_exercise_end_time);
        text_view_of_exercise_type.setText(exercise_type+":"+exercise_data+exercise_unit);
        text_view_of_exercise_start_time.setText(start_year_of_invitation+"年"+start_month_of_invitation+"月"+start_day_of_invitation+"號 "+start_hour_of_invitation+":"+start_minute_of_invitation);
        text_view_of_exercise_end_time.setText(end_year_of_invitation+"年"+end_month_of_invitation+"月"+end_day_of_invitation+"號 "+end_hour_of_invitation+":"+end_minute_of_invitation);

        //Toast.makeText(getActivity(), "一起運動吧！\n運動種類:"+exercise_type+"\n"+exercise_data_count+":"+exercise_data+exercise_unit+"\n開始日期:"+start_year_of_invitation+"年"+start_month_of_invitation+"月"+start_day_of_invitation+"號\n開始時間:"+start_hour_of_invitation+":"+start_minute_of_invitation+"\n結束日期:"+end_year_of_invitation+"年"+end_month_of_invitation+"月"+end_day_of_invitation+"號\n結束時間"+end_hour_of_invitation+":"+end_minute_of_invitation, Toast.LENGTH_LONG).show();
        view.setOnTouchListener(this);
        List<Student> rowListItem = getAllItemList();
        btnSelection = (Button) view.findViewById(R.id.btnShow);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CardViewDataAdapter(studentList,rowListItem);
        mRecyclerView.setAdapter(mAdapter);
        btnSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                List<Student> stList = ((CardViewDataAdapter) mAdapter).getStudentList();

                for (int i = 0; i < stList.size(); i++) {
                    Student singleStudent = stList.get(i);
                    if (singleStudent.isSelected() == true) {

                        data = data + "\n" + singleStudent.getName().toString();
                        /*
                        * Toast.makeText( CardViewActivity.this, " " +
                        * singleStudent.getName() + " " +
                        * singleStudent.getEmailId() + " " +
                        * singleStudent.isSelected(),
                        * Toast.LENGTH_SHORT).show();
                        */
                    }

                }
                if (data == null || data.length() <= 0){
                    Toast.makeText(getActivity(), "no Selected Students", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getActivity(), "Selected Students: \n" + data, Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_kelvin_running_invitation,new Null(),null)
                            .addToBackStack(null)
                            .commit();
                }


            }
        });
        return view;
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
    private List<Student> getAllItemList() {
        List<Student> allItems = new ArrayList<Student>();
        allItems.add(new Student("何晉丞","B10456013",R.drawable.he, false));
        allItems.add(new Student("黃弘盛","B10456018",R.drawable.sheng,false));
        allItems.add(new Student("張晧寬","B10456026",R.drawable.kuang,false));
        allItems.add(new Student("黃琨城","B10456031",R.drawable.huang,false));
        allItems.add(new Student("黃書輝","B10456061",R.drawable.ng,false));




        return allItems;
    }


}
