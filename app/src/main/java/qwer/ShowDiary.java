package qwer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowDiary.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowDiary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowDiary extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String todayDate;
    private String todayDiary;
    private int todayKLL;

    private OnFragmentInteractionListener mListener;

    public ShowDiary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment ShowDiary.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowDiary newInstance(String param1, String param2, int param3) {
        ShowDiary fragment = new ShowDiary();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3,param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todayDate = getArguments().getString(ARG_PARAM1);
            todayDiary = getArguments().getString(ARG_PARAM2);
            todayKLL=getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_show_diary, container, false);
        //Toast.makeText(getActivity(), mParam1+"："+mParam2, Toast.LENGTH_SHORT).show();
        ImageButton theBK=(ImageButton)view.findViewById(R.id.Diary_Btn_BK);//早餐
        ImageButton theLH=(ImageButton)view.findViewById(R.id.Diary_Btn_LH);//午餐
        ImageButton theDN=(ImageButton)view.findViewById(R.id.Diary_Btn_DN);//晚餐
        ImageButton theDiary=(ImageButton)view.findViewById(R.id.Diary_Btn_Diary);//日記
        TextView theday=(TextView)view.findViewById(R.id.theDAY);
        TextView showdiary=(TextView)view.findViewById(R.id.DiaryCon);
        TextView theKLL=(TextView)view.findViewById(R.id.theDayKLL);
        theday.setText(todayDate);
        showdiary.setText(todayDiary);
        theKLL.setText(Integer.toString(todayKLL));
        Button deletbtn=(Button)view.findViewById(R.id.deletBtn);
        Button okbtn=(Button)view.findViewById(R.id.OKBtn);
        theBK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toFoodList(0,0);
            }
        });
        theLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).toFoodList(1,0);
            }
        });
        theDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).toFoodList(2,0);
            }
        });
        theDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(todayDiary!=null){
                    ((MainActivity)getActivity()).deleOneDiary();
                }
                ((MainActivity)getActivity()).toAddDiary(todayDiary);
            }
        });
        deletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).deleOneDiary();
                Toast.makeText(getActivity(), "已刪除今日紀錄", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).ShowMyDiary();
            }
        });

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).writAllDiaryDATA();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new BlankFragment2(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String Tag,String number) {
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
        void onFragmentInteraction(String Tag,String number);
    }
}
