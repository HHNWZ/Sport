package qwer;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragmentc2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragmentc2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentc2 extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Toolbar calorie_toolbar;

    public BlankFragmentc2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragmentc2.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentc2 newInstance(String param1, String param2) {
        BlankFragmentc2 fragment = new BlankFragmentc2();
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
        final int[] w1 = new int[1];
        final int[] h1 = new int[1];
        final int[] a1 = new int[1];
        final double[] wha = new double[1];
        final double[] wha1 = new double[1];
        final double[] work = new double[1];
        final double[] add5 = new double[1];
        final double[] add10 = new double[1];
        final double[] dowm20 = new double[1];
        final double[] dowm10 = new double[1];
        final int[] boygirl = {0};

        final View view = inflater.inflate(R.layout.fragment_blank_fragmentc2, null);
        view.setOnTouchListener(this);
        calorie_toolbar=(Toolbar)view.findViewById(R.id.calorie_toolbar);
        calorie_toolbar.setTitle("所需卡路里計算");
        calorie_toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        calorie_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new Dietcontrol(),null)
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
        ImageButton qwera4=(ImageButton)view.findViewById(R.id.imageButtona4);
        qwera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new BlankFragmentc4(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button qa1=(Button)view.findViewById(R.id.button19);
        final EditText edi3=(EditText)view.findViewById(R.id.editText3);
        final EditText edi4=(EditText)view.findViewById(R.id.editText4);
        final EditText edi5=(EditText)view.findViewById(R.id.editText5);
        final TextView tivw41=(TextView)view.findViewById(R.id.textView41);
        //feagment中增加多選一按鈕觸發事件(判斷性別)
        final RadioGroup rg = (RadioGroup)view.findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.radioButtonboy:
                        // switch to fragment 1
                        boygirl[0] =5;
                        break;
                    case R.id.radioButtongirl:
                        // Fragment 2
                        boygirl[0] =-161;
                        break;
                }
            }
        });
        //feagment中增加多選一按鈕觸發事件(判斷活動程度)
        final RadioGroup rg2 = (RadioGroup)view.findViewById(R.id.radioGroup2);
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.radioButton3:
                        // switch to fragment 1
                        work[0] =1.2;
                        break;
                    case R.id.radioButton4:
                        // Fragment 2
                        work[0] =1.55;
                        break;
                    case R.id.radioButton5:
                        // Fragment 2
                        work[0] =1.9;
                        break;
                }
            }
        });
        qa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !("".equals(edi3.getText().toString())//避免沒有輸入參數
                        || "".equals(edi4.getText().toString())) )//避免沒有輸入參數
                {
                    a1[0] =Integer.parseInt(edi3.getText().toString());
                    w1[0] =Integer.parseInt(edi4.getText().toString());
                    h1[0] =Integer.parseInt(edi5.getText().toString());
                    wha[0] =(10* w1[0])+(6.25* h1[0])-(5* a1[0])+boygirl[0];
                    wha1[0] =wha[0];
                    wha[0]=wha[0]*work[0];
                    add5[0]=wha[0]*0.05+wha[0];
                    add10[0]=wha[0]*0.1+wha[0];
                    dowm10[0]=wha[0]-wha[0]*0.1;
                    dowm20[0]=wha[0]-wha[0]*0.2;
                    tivw41.setText(String.valueOf(String.format("%.0f", wha[0])));
                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(getActivity());
                    //功能視窗表
                    dialog1.setTitle("檢測報告");
                    dialog1.setMessage("\n◎維持身材:    "+String.valueOf(String.format("%.0f", wha[0]))+" kcal(大卡)\n\n"+getString(R.string.Calories)+"\n◎增加肌肉:    "+String.valueOf(String.format("%.0f", add5[0]))+" – "+String.valueOf(String.format("%.0f", add10[0]))+"  kcal\n\n"+getString(R.string.AddCalories)+"\n◎減少脂肪:    "+String.valueOf(String.format("%.0f", dowm20[0]))+" – "+String.valueOf(String.format("%.0f", dowm10[0]))+"  kcal\n\n"+getString(R.string.DownCalories)+"\n※基礎代謝率:    "+String.valueOf(String.format("%.0f", wha1[0]))+"  kcal"+getString(R.string.metabolism));
                    dialog1.setNeutralButton("關閉",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getActivity(), "介面有顯示您維持身材的卡路里",Toast.LENGTH_SHORT).show();
                        }

                    });
                    dialog1.show();
                }
            }
        });
        return view; }

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
