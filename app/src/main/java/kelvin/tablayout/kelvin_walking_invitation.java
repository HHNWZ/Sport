package kelvin.tablayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link kelvin_walking_invitation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link kelvin_walking_invitation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class kelvin_walking_invitation extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int REQUEST=1;
    public static final int REQUEST2=1;

    public String hour_of_kelvin_walking_invitation_for_start_time;
    public String minute_of_kelvin_walking_invitation_for_start_time;
    public String hour_of_kelvin_walking_invitation_for_end_time;
    public String minute_of_kelvin_walking_invitation_for_end_time;
    public TextView text_of_select_start_time_for_walking_invitation;
    public TextView text_of_select_end_time_for_walking_invitation;
    public DecimalFormat mDecimalFormat = new DecimalFormat("##00");
    public EditText editText_of_walking_set_count;
    public Button button_of_walking_invitation_confirm;
    public Button button_of_start_time_for_walking_invitation;
    public Button button_of_end_time_for_walking_invitation;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public int walking_set_count;
    public InputMethodManager imm_of_walking_invitation;

    private OnFragmentInteractionListener mListener;

    public kelvin_walking_invitation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment kelvin_walking_invitation.
     */
    // TODO: Rename and change types and number of parameters
    public static kelvin_walking_invitation newInstance(String param1, String param2) {
        kelvin_walking_invitation fragment = new kelvin_walking_invitation();
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
        imm_of_walking_invitation = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_kelvin_walking_invitation, null);
        view.setOnTouchListener(this);
        editText_of_walking_set_count=(EditText)view.findViewById(R.id.editText_of_walking_set_count);
        button_of_walking_invitation_confirm=(Button)view.findViewById(R.id.button_of_walking_invitation_confirm);
        button_of_start_time_for_walking_invitation=(Button)view.findViewById(R.id.button_of_start_time_for_walking_invitation);
        button_of_end_time_for_walking_invitation=(Button)view.findViewById(R.id.button_of_end_time_for_walking_invitation);
        text_of_select_start_time_for_walking_invitation=(TextView)view.findViewById(R.id.text_of_select_start_time_for_walking_invitation);
        text_of_select_end_time_for_walking_invitation=(TextView)view.findViewById(R.id.text_of_select_end_time_for_walking_invitation);

        button_of_walking_invitation_confirm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if("".equals(editText_of_walking_set_count.getText().toString().trim())){
                    Toast.makeText(getActivity(), getResources().getString(R.string.empty_of_edit_text), Toast.LENGTH_SHORT).show();
                    editText_of_walking_set_count.requestFocus();
                    imm_of_walking_invitation.showSoftInput(editText_of_walking_set_count, 0);
                }else{
                    walking_set_count=Integer.parseInt(editText_of_walking_set_count.getText().toString());
                    if(walking_set_count<100||walking_set_count>50000){
                        Toast.makeText(getActivity(), "步數不能小於100或大於50000", Toast.LENGTH_SHORT).show();
                        editText_of_walking_set_count.requestFocus();
                        imm_of_walking_invitation.showSoftInput(editText_of_walking_set_count, 0);
                    }else{
                        Toast.makeText(getActivity(), "步數是:"+walking_set_count, Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_kelvin_running_invitation,new kelvin_running_tag_friend(),null)
                                .addToBackStack(null)
                                .commit();
                    }
                }

            }


        });

        button_of_start_time_for_walking_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment_of_walking_invitation_of_start_time = new TimePickerFragmentOfstartTime();
                newFragment_of_walking_invitation_of_start_time.setTargetFragment(kelvin_walking_invitation.this,REQUEST);
                newFragment_of_walking_invitation_of_start_time.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });

        button_of_end_time_for_walking_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment_of_walking_invitation_of_end_time = new TimePickerFragmentOfendTime();
                newFragment_of_walking_invitation_of_end_time.setTargetFragment(kelvin_walking_invitation.this,REQUEST2);
                newFragment_of_walking_invitation_of_end_time.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });


        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String Tag, String number) {//要從kelvin_tab_layout.java拿onFragmentInteraction的(String Tag, String number) 貼到這裡來
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
        void onFragmentInteraction(String Tag, String number);//要從kelvin_tab_layout.java拿onFragmentInteraction的(String Tag, String number) 貼到這裡來
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST){
            //hour_of_kelvin_running_invitation = data.getStringExtra(TimePickerFragmentOfstartTime.HOUR_OF_TIME_PICKER_FRAGMENT);
            minute_of_kelvin_walking_invitation_for_start_time= mDecimalFormat.format(Double.parseDouble(data.getStringExtra(TimePickerFragmentOfstartTime.MINUTE_OF_TIME_PICKER_FRAGMENT)));
            hour_of_kelvin_walking_invitation_for_start_time = mDecimalFormat.format(Double.parseDouble(data.getStringExtra(TimePickerFragmentOfstartTime.HOUR_OF_TIME_PICKER_FRAGMENT)));
            text_of_select_end_time_for_walking_invitation.setText("你選擇開始的時間是"+hour_of_kelvin_walking_invitation_for_start_time+":"+minute_of_kelvin_walking_invitation_for_start_time);

        }
        if(requestCode== REQUEST2){
            minute_of_kelvin_walking_invitation_for_end_time= mDecimalFormat.format(Double.parseDouble(data.getStringExtra(TimePickerFragmentOfendTime.MINUTE_END_OF_TIME_PICKER_FRAGMENT)));
            hour_of_kelvin_walking_invitation_for_end_time = mDecimalFormat.format(Double.parseDouble(data.getStringExtra(TimePickerFragmentOfendTime.HOUR_END_OF_TIME_PICKER_FRAGMENT)));
            text_of_select_end_time_for_walking_invitation.setText("你選擇結束的時間是"+hour_of_kelvin_walking_invitation_for_end_time+":"+minute_of_kelvin_walking_invitation_for_end_time);
        }




}
}
