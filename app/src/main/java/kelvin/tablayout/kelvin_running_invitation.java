package kelvin.tablayout;

import android.app.Dialog;
import android.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.Fragment;

import com.example.a888888888.sport.R;
import java.lang.String;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link kelvin_running_invitation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@linkkelvin_running_invitation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class kelvin_running_invitation extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button doSetTime;
    private TextView textDate;
    private TimePickerDialog timePickerDialog;
    static  String apple;
    int hour_x;
    int minute_x;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String placeOfrunning;
    int distance_of_running;
    private OnFragmentInteractionListener mListener;

    public kelvin_running_invitation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment kelvin_running_invitation.
     */
    // TODO: Rename and change types and number of parameters
    public static kelvin_running_invitation newInstance(String param1, String param2) {
        kelvin_running_invitation fragment = new kelvin_running_invitation();
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
        View view = inflater.inflate(R.layout.fragment_kelvin_running_invitation, null);
        view.setOnTouchListener(this);
        doSetTime=(Button)view.findViewById(R.id.buttonTime);
        doSetTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment=new TimePickerFragment();
                        android.support.v4.app.FragmentManager fm =getActivity().getSupportFragmentManager();
                        //newFragment.show(fm,"timePicker");要怎樣呼叫TimePickerFragment
                    }
                }
        );

        Spinner spinner_of_running_place =(Spinner)view.findViewById(R.id.spinner_of_running_place);
        String[]list_of_running_place=getResources().getStringArray(R.array.spinner_of_running_place);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list_of_running_place);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_of_running_place.setAdapter(adapter);
        spinner_of_running_place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[]place_of_running=getResources().getStringArray(R.array.spinner_of_running_place);
                //Toast.makeText(getActivity(),"你點擊的是"+place_of_running[position], Toast.LENGTH_LONG).show();
                placeOfrunning=place_of_running[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final EditText edit_text_on_distance=(EditText)view.findViewById(R.id.editText_of_distance);
        Button button_of_running_invitation_confirm=(Button)view.findViewById(R.id.button_of_running_invitation_confirm);





        button_of_running_invitation_confirm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if("".equals(edit_text_on_distance.getText().toString().trim())){
                    Toast.makeText(getActivity(), getResources().getString(R.string.empty_of_edit_text), Toast.LENGTH_SHORT).show();
                }else{
                    distance_of_running=Integer.parseInt(edit_text_on_distance.getText().toString());
                        if(distance_of_running<100||distance_of_running>5000){
                            Toast.makeText(getActivity(), getResources().getString(R.string.value_maximum_and_minimum_of_running_distance), Toast.LENGTH_SHORT).show();
                        }
                }
                Toast.makeText(getActivity(), "場地是:"+placeOfrunning+"距離是:"+distance_of_running, Toast.LENGTH_SHORT).show();
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

}
