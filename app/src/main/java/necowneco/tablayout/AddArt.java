package necowneco.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a888888888.sport.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddArt.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddArt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddArt extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    // TODO: Rename and change types of parameters
    private String nowuser;
    private String addType;
    private int theID;
    private String theTitle;
    private String theClass;
    private String theCon;


    private OnFragmentInteractionListener mListener;
    public AddArt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @param param4 Parameter 4.
     * @param param5 Parameter 5.
     * @param param6 Parameter 6.   @return A new instance of fragment AddArt.
     */
    // TODO: Rename and change types and number of parameters
    public static AddArt newInstance(String param1, String param2, int param3, String param4, String param5,String param6 ) {
        AddArt fragment = new AddArt();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nowuser = getArguments().getString(ARG_PARAM1);
            addType = getArguments().getString(ARG_PARAM2);
            theID = getArguments().getInt(ARG_PARAM3);
            theTitle = getArguments().getString(ARG_PARAM4);
            theClass = getArguments().getString(ARG_PARAM5);
            theCon = getArguments().getString(ARG_PARAM6);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_add_art, null);
        view.setOnTouchListener(this);
        final Spinner spinner=(Spinner)view.findViewById(R.id.SportClassSpinner);
        Button submitArt=(Button)view.findViewById(R.id.inputart);
        final EditText ct=(EditText)view.findViewById(R.id.ConTitle);
        final EditText cc=(EditText)view.findViewById(R.id.Concon);
        final TextView at=(TextView)view.findViewById(R.id.theAddType);
        final String[] SportList = {"所有運動","有氧運動","走路","跑步","伏地挺身","仰臥起坐"};
        ArrayAdapter<String> sportlist = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                SportList);
        spinner.setAdapter(sportlist);
        if(addType=="編輯"){
            at.setText(addType+"貼文");
            ct.setText(theTitle);
            int ci;
            for(ci=0;theClass!=SportList[ci];ci++){}
            spinner.setSelection(ci);
            cc.setText(theCon);
        }
        submitArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onButtonPressed(spinner.getSelectedItem().toString(),ct.getText().toString());
                if(addType=="新增"){
                    ((habaActivity) getActivity()).addartDATA(
                            ct.getText().toString(),
                            spinner.getSelectedItem().toString(),
                            cc.getText().toString()
                    );
                }else{//編輯
                    ((habaActivity)getActivity()).reSetArtDATA(
                            theID,ct.getText().toString(),
                            spinner.getSelectedItem().toString(),
                            cc.getText().toString()
                    );
                }
            }
        });
        // Inflate the layout for this fragment
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
        void onFragmentInteraction(String Tag,String number);
    }

}
