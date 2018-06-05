package qwer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;

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
        final Spinner spinner=(Spinner)view.findViewById(R.id.SportListSpinner);
        final String[] SportList = {"請選擇運動項目","有氧運動","走路","跑步","伏地挺身","仰臥起坐"};
        final LinearLayout selelayout=(LinearLayout)view.findViewById(R.id.seleLayout);
        final LinearLayout sporttarget=(LinearLayout)view.findViewById(R.id.SportTargetLayout);
        final TextView sportitem=(TextView)view.findViewById(R.id.SportItem);
        final TextView progressnum=(TextView)view.findViewById(R.id.SportProgressNum);
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
                if(mySport[0]==0){
                    Toast.makeText(getActivity(), "請先選擇運動項目", Toast.LENGTH_SHORT).show();
                }else{
                    selelayout.setVisibility(View.GONE);
                    sporttarget.setVisibility(View.VISIBLE);
                    sportitem.setText(SportList[mySport[0]]);
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
                    Toast.makeText(getActivity(), "今天的運動進度已完成(100/100)", Toast.LENGTH_SHORT).show();
                    progressnum.setText("今天的運動進度已完成");
                    dothesport.setVisibility(View.GONE);
                    sportfinishimg.setVisibility(View.VISIBLE);
                }
            }
        });


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
