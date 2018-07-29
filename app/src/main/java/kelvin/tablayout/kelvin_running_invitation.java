package kelvin.tablayout;

import android.content.Intent;
import android.support.v4.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.a888888888.sport.R;
import java.lang.String;
import java.text.DecimalFormat;
import java.util.Date;


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
    public static final int start_time_data_of_running_invitation =1;
    public static final int start_date_data_of_running_invitation =2;
    public static final int end_time_data_of_running_invitation =3;
    public static final int end_date_data_of_running_invitation =4;
    public String start_hour_of_kelvin_running_invitation;
    public int start_hour_int_of_kelvin_running_invitation;
    public String start_minute_of_kelvin_running_invitation;
    public int start_minute_int_of_kelvin_running_invitation;
    public String start_day_of_kelvin_running_invitation;
    public String start_month_of_kelvin_running_invitation;
    public String start_year_of_kelvin_running_invitation;
    public int start_year_int_of_kelvin_running_invitation;
    public int start_month_int_of_kelvin_running_invitation;
    public int start_day_int_of_kelvin_running_invitation;
    public TextView show_start_time_of_running_invitation;
    public TextView show_start_date_of_running_invitation;

    public String end_hour_of_kelvin_running_invitation;
    public int end_hour_int_of_kelvin_running_invitation;
    public String end_minute_of_kelvin_running_invitation;
    public int end_minute_int_of_kelvin_running_invitation;
    public String end_day_of_kelvin_running_invitation;
    public String end_month_of_kelvin_running_invitation;
    public String end_year_of_kelvin_running_invitation;
    public int end_year_int_of_kelvin_running_invitation;
    public int end_month_int_of_kelvin_running_invitation;
    public int end_day_int_of_kelvin_running_invitation;
    public TextView show_end_time_of_running_invitation;
    public TextView show_end_date_of_running_invitation;
    DecimalFormat mDecimalFormat = new DecimalFormat("##00");
    public EditText edit_text_on_distance;
    public InputMethodManager imm_of_running_invitation;
    public Toast toast;
    public Button button_of_running_invitation_confirm;
    public Button button_of_running_invitation_social_media_friends;

    private View view;
    private Toolbar runningToolbar;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        imm_of_running_invitation = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("year_int1", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
        Log.i("year_string1", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
        view = inflater.inflate(R.layout.fragment_kelvin_running_invitation, null);
        view.setOnTouchListener(this);
        runningToolbar=(Toolbar)view.findViewById(R.id.running_bar);
        //runningToolbar.setBackgroundColor(16711680);
        runningToolbar.setTitle("跑步邀請內容");
        runningToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        runningToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
                Log.i("year_int2", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
                Log.i("year_string2", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            }
        });

        edit_text_on_distance=(EditText)view.findViewById(R.id.editText_of_distance);
        edit_text_on_distance.requestFocus();

        /*Spinner spinner_of_running_place =(Spinner)view.findViewById(R.id.spinner_of_running_place);
        String[]list_of_running_place=getResources().getStringArray(R.array.spinner_of_running_place);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list_of_running_place);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_of_running_place.setAdapter(adapter);*/
        Date mDate = new Date();
        Log.i("year_int3", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
        Log.i("year_string3", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
        start_hour_int_of_kelvin_running_invitation= mDate.getHours();
        start_minute_int_of_kelvin_running_invitation=mDate.getMinutes();
        start_year_int_of_kelvin_running_invitation=mDate.getYear()+1900;
        start_month_int_of_kelvin_running_invitation=mDate.getMonth()+1;
        start_day_int_of_kelvin_running_invitation=mDate.getDate();
        end_hour_int_of_kelvin_running_invitation= mDate.getHours();
        end_minute_int_of_kelvin_running_invitation=mDate.getMinutes();
        end_year_int_of_kelvin_running_invitation=mDate.getYear()+1900;
        Log.i("year_int4", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
        Log.i("year_string4", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
        end_month_int_of_kelvin_running_invitation=mDate.getMonth()+1;
        end_day_int_of_kelvin_running_invitation=mDate.getDate();
        Log.i("year_int5", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
        Log.i("year_string5", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);

        show_start_time_of_running_invitation=(TextView)view.findViewById(R.id.show_start_time_of_running_invitation);
        show_start_time_of_running_invitation.setText(mDecimalFormat.format(start_hour_int_of_kelvin_running_invitation) +":"+mDecimalFormat.format(start_minute_int_of_kelvin_running_invitation) );
        Log.i("year_int6", String.valueOf(++end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
        Log.i("year_string6", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);

        show_start_time_of_running_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new StartTimeFragment();

                newFragment.setTargetFragment(kelvin_running_invitation.this, start_time_data_of_running_invitation);
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
                Log.i("year_int7", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
                Log.i("year_string7", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            }
        });
        show_start_date_of_running_invitation=(TextView)view.findViewById(R.id.show_start_date_of_running_invitation);
        show_start_date_of_running_invitation.setText(start_year_int_of_kelvin_running_invitation+"年"+start_month_int_of_kelvin_running_invitation+"月"+start_day_int_of_kelvin_running_invitation);
        Log.i("year_int8", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
        Log.i("year_string8", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
        show_start_date_of_running_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new StartDateFragment();
                newFragment.setTargetFragment(kelvin_running_invitation.this, start_date_data_of_running_invitation);
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                Log.i("year_int9", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
                Log.i("year_string9", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            }
        });

        show_end_time_of_running_invitation=(TextView)view.findViewById(R.id.show_end_time_of_running_invitation);
        show_end_time_of_running_invitation.setText(mDecimalFormat.format(end_hour_int_of_kelvin_running_invitation) +":"+mDecimalFormat.format(end_minute_int_of_kelvin_running_invitation) );
        Log.i("year_int10", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
        Log.i("year_string10", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
        show_end_time_of_running_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new EndTimeFragment();

                newFragment.setTargetFragment(kelvin_running_invitation.this, end_time_data_of_running_invitation);
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
                Log.i("year_int11", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
                Log.i("year_string12", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            }
        });
        show_end_date_of_running_invitation=(TextView)view.findViewById(R.id.show_end_date_of_running_invitation);
        show_end_date_of_running_invitation.setText(end_year_int_of_kelvin_running_invitation+"年"+end_month_int_of_kelvin_running_invitation+"月"+end_day_int_of_kelvin_running_invitation);
        Log.i("year_int13", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
        Log.i("year_string13", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
        show_end_date_of_running_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new EndDateFragment();
                newFragment.setTargetFragment(kelvin_running_invitation.this, end_date_data_of_running_invitation);
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                Log.i("year_int14", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
                Log.i("year_string14", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            }
        });
        /*spinner_of_running_place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[]place_of_running=getResources().getStringArray(R.array.spinner_of_running_place);
                //Toast.makeText(getActivity(),"你點擊的是"+place_of_running[position], Toast.LENGTH_LONG).show();
                placeOfrunning=place_of_running[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/




        button_of_running_invitation_confirm=(Button)view.findViewById(R.id.button_of_running_invitation_confirm);
        button_of_running_invitation_confirm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if("".equals(edit_text_on_distance.getText().toString().trim())){
                    toast.makeText(getActivity(), getResources().getString(R.string.empty_of_edit_text), Toast.LENGTH_SHORT).show();
                    edit_text_on_distance.requestFocus();
                    //imm2 = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm_of_running_invitation.showSoftInput(edit_text_on_distance, 0);

                }else{
                    distance_of_running=Integer.parseInt(edit_text_on_distance.getText().toString());
                        if(distance_of_running<100||distance_of_running>5000){
                            toast.makeText(getActivity(), getResources().getString(R.string.value_maximum_and_minimum_of_running_distance), Toast.LENGTH_SHORT).show();
                            edit_text_on_distance.setText(" ");
                            imm_of_running_invitation.showSoftInput(edit_text_on_distance, 0);
                        }else{
                            if(start_hour_of_kelvin_running_invitation==null||start_minute_of_kelvin_running_invitation==null){
                                toast.makeText(getActivity(), "請選擇時間", Toast.LENGTH_SHORT).show();
                                DialogFragment newFragment = new StartTimeFragment();
                                newFragment.setTargetFragment(kelvin_running_invitation.this, start_time_data_of_running_invitation);
                                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
                            }else{
                                FriendsFragment2 kelvin_running_tag_friend1 = FriendsFragment2.newInstance("跑步","距離",edit_text_on_distance.getText().toString(),"米",start_year_of_kelvin_running_invitation,start_month_of_kelvin_running_invitation,start_day_of_kelvin_running_invitation,start_hour_of_kelvin_running_invitation,start_minute_of_kelvin_running_invitation,end_year_of_kelvin_running_invitation,end_month_of_kelvin_running_invitation,end_day_of_kelvin_running_invitation,end_hour_of_kelvin_running_invitation,end_minute_of_kelvin_running_invitation);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.fragment_kelvin_running_invitation,kelvin_running_tag_friend1);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }



                        }
                }

            }


        });

        button_of_running_invitation_social_media_friends=(Button)view.findViewById(R.id.button_of_running_invitation_social_media_friends);
        button_of_running_invitation_social_media_friends.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "一起運動吧！\n運動種類:跑步\n距離:"+edit_text_on_distance.getText().toString()+"米\n開始日期:"+start_year_of_kelvin_running_invitation+"年"+start_month_of_kelvin_running_invitation+"月"+start_day_of_kelvin_running_invitation+"號\n開始時間:"+start_hour_of_kelvin_running_invitation+":"+start_minute_of_kelvin_running_invitation+"\n結束日期:"+end_year_of_kelvin_running_invitation+"年"+end_month_of_kelvin_running_invitation+"月"+end_day_of_kelvin_running_invitation+"號\n結束時間"+end_hour_of_kelvin_running_invitation+":"+end_minute_of_kelvin_running_invitation);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));





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

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == start_time_data_of_running_invitation){
            Log.i("為什麼2", "我在這裡");
            Log.i("year_int15", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
            Log.i("year_string15", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            start_minute_of_kelvin_running_invitation= mDecimalFormat.format(Double.parseDouble(data.getStringExtra(StartTimeFragment.MINUTE_OF_START_TIME_PICKER_FRAGMENT)));
            start_hour_of_kelvin_running_invitation = mDecimalFormat.format(Double.parseDouble(data.getStringExtra(StartTimeFragment.HOUR_OF_START_TIME_PICKER_FRAGMENT)));
            show_start_time_of_running_invitation.setText(start_hour_of_kelvin_running_invitation+":"+start_minute_of_kelvin_running_invitation);
            Log.i("year_int16", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
            Log.i("year_string16", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            Log.i("為什麼3", "我在這裡");
        }

        if(requestCode == start_date_data_of_running_invitation){
            Log.i("為什麼4", "我在這裡");
            Log.i("year_int17", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
            Log.i("year_string17", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            start_day_of_kelvin_running_invitation =data.getStringExtra(StartDateFragment.DAY_OF_START_Date_PICKER_FRAGMENT);
            start_month_of_kelvin_running_invitation =data.getStringExtra(StartDateFragment.MONTH_OF_START_Date_PICKER_FRAGMENT);
            start_year_of_kelvin_running_invitation =data.getStringExtra(StartDateFragment.YEAR_OF_START_Date_PICKER_FRAGMENT);
            show_start_date_of_running_invitation.setText(start_year_of_kelvin_running_invitation +"年"+ start_month_of_kelvin_running_invitation +"月"+ start_day_of_kelvin_running_invitation);
            Log.i("year_int18", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
            Log.i("year_string18", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            Log.i("為什麼5", "我在這裡");
        }

        if(requestCode == end_time_data_of_running_invitation){
            Log.i("為什麼", "我在這裡");
            Log.i("year_int19", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
            Log.i("year_string19", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            end_minute_of_kelvin_running_invitation= mDecimalFormat.format(Double.parseDouble(data.getStringExtra(EndTimeFragment.MINUTE_OF_END_TIME_PICKER_FRAGMENT)));
            end_hour_of_kelvin_running_invitation = mDecimalFormat.format(Double.parseDouble(data.getStringExtra(EndTimeFragment.HOUR_OF_END_TIME_PICKER_FRAGMENT)));
            show_end_time_of_running_invitation.setText(end_hour_of_kelvin_running_invitation+":"+end_minute_of_kelvin_running_invitation);
            Log.i("year_int20", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
            Log.i("year_string20", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            Log.i("為什麼7", "我在這裡");
        }

        if(requestCode == end_date_data_of_running_invitation){

            Log.i("為什麼1", "我在這裡");
            Log.i("year_int21", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
            Log.i("year_string21", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            end_day_of_kelvin_running_invitation =data.getStringExtra(EndDateFragment.DAY_OF_END_Date_PICKER_FRAGMENT);
            end_month_of_kelvin_running_invitation =data.getStringExtra(EndDateFragment.MONTH_OF_END_Date_PICKER_FRAGMENT);
            end_year_of_kelvin_running_invitation =data.getStringExtra(EndDateFragment.YEAR_OF_END_Date_PICKER_FRAGMENT);
            if(end_day_of_kelvin_running_invitation!=null){
                show_end_date_of_running_invitation.setText(end_year_of_kelvin_running_invitation +"年"+ end_month_of_kelvin_running_invitation +"月"+ end_day_of_kelvin_running_invitation);
            }
            Log.i("year_int22", String.valueOf(+end_year_int_of_kelvin_running_invitation+":"+end_month_int_of_kelvin_running_invitation+":"+end_day_int_of_kelvin_running_invitation));
            Log.i("year_string22", end_day_of_kelvin_running_invitation+end_month_of_kelvin_running_invitation+end_year_of_kelvin_running_invitation);
            Log.i("為什麼8", "我在這裡");
        }

    }
}





