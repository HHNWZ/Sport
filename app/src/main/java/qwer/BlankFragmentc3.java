package qwer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.a888888888.sport.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragmentc3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragmentc3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentc3 extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BlankFragmentc3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragmentc3.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentc3 newInstance(String param1, String param2) {
        BlankFragmentc3 fragment = new BlankFragmentc3();
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
        final int[] dayday = new int[1];
        final int[] dayday1 = new int[1];
        View view = inflater.inflate(R.layout.fragment_blank_fragmentc3, null);
        view.setOnTouchListener(this);
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
        ImageButton left1=(ImageButton)view.findViewById(R.id.left1);
        ImageButton left2=(ImageButton)view.findViewById(R.id.left2);
        ImageButton right1=(ImageButton)view.findViewById(R.id.right1);
        ImageButton right2=(ImageButton)view.findViewById(R.id.right2);
        final EditText day1=(EditText)view.findViewById(R.id.dsy1);
        day1.setText("25");
        final EditText day2=(EditText)view.findViewById(R.id.dsy2);
        day2.setText("5");
        //左按鈕減少
        left1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayday[0] = Integer.parseInt(day1.getText().toString());
                dayday[0]=dayday[0]-1;
                day1.setText(String.valueOf( dayday[0]));
            }});
        //右按鈕增加
        right1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayday[0] = Integer.parseInt(day1.getText().toString());
                dayday[0]=dayday[0]+1;
                day1.setText(String.valueOf( dayday[0]));
            }});
        //左按鈕減少
        left2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayday1[0] = Integer.parseInt(day2.getText().toString());
                dayday1[0]=dayday1[0]-1;
                day2.setText(String.valueOf( dayday1[0]));
            }});
        //右按鈕增加
        right2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayday1[0] = Integer.parseInt(day2.getText().toString());
                dayday1[0]=dayday1[0]+1;
                day2.setText(String.valueOf( dayday1[0]));
            }});
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
