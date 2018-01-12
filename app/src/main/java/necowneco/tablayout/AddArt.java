package necowneco.tablayout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public AddArt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddArt.
     */
    // TODO: Rename and change types and number of parameters
    public static AddArt newInstance(String param1, String param2) {
        AddArt fragment = new AddArt();
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

        final View view = inflater.inflate(R.layout.fragment_add_art, null);
        view.setOnTouchListener(this);
        final Spinner spinner=(Spinner)view.findViewById(R.id.SportClassSpinner);
        Button submitArt=(Button)view.findViewById(R.id.inputart);
        final EditText ct=(EditText)view.findViewById(R.id.ConTitle);
        final EditText cc=(EditText)view.findViewById(R.id.Concon);
        final TextView test = (TextView)view.findViewById(R.id.testtext);
        final String[] SportList = {"所有運動","有氧運動","走路","跑步","伏地挺身","仰臥起坐"};
        final String nowuser=mParam1;
        ArrayAdapter<String> sportlist = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                SportList);
        spinner.setAdapter(sportlist);
        submitArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText("按下送出");
                //onButtonPressed(spinner.getSelectedItem().toString(),ct.getText().toString());
                ((habaActivity) getActivity()).addartDATA(
                        ct.getText().toString(),
                        spinner.getSelectedItem().toString(),
                        cc.getText().toString()
                );

                /*getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.haba,new theArt(),null)
                        .addToBackStack(null)
                        .commit();*/
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
