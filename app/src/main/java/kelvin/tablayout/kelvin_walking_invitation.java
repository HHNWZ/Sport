package kelvin.tablayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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

    public Calendar m = Calendar.getInstance();
    //public Calendar calendar = Calendar.getInstance();
    public TextView arrivalCalenderText;
    public TextView arrivalTimeText;
    long todayCheck;
    //    final int TIME_DIALOG_ID=0;

    int arrivalHour,arrivalMinute,arrivalYear,arrivalMonth,arrivalDay;

    // Calender fromCal is for managing "from" time
    Calendar fromCal = Calendar.getInstance();

    final Calendar genCal = Calendar.getInstance();

    // Calender toCal is for managing "To" time
    final Calendar toCal = Calendar.getInstance();
    public ScrollView scrollView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //public int walking_set_count;
    public InputMethodManager imm_of_walking_invitation;

    private OnFragmentInteractionListener mListener;
    public Button button_of_walking_invitation_confirm;

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

        return view;

    }

    private final DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            arrivalYear = year;
            arrivalMonth = monthOfYear;
            arrivalDay = dayOfMonth;

            Calendar p = Calendar.getInstance();
            p.set(year,monthOfYear,dayOfMonth);

            SimpleDateFormat format1 = new SimpleDateFormat("EEE");
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
            String g = format1.format(p.getTime());
            String cDate = format.format(p.getTime());

            Calendar cal = Calendar.getInstance();
            DateFormat currentTime = new SimpleDateFormat("hh:mm:ss aa", Locale.ENGLISH);
            currentTime.format(new Date());
            if(cal.get(Calendar.DAY_OF_MONTH)==dayOfMonth && cal.get(Calendar.MONTH)==monthOfYear && cal.get(Calendar.YEAR)==year) {
                arrivalCalenderText.setText("Today, " + cDate);
                todayCheck=0;
                fromCal.setTimeInMillis(cal.getTimeInMillis());
            } else {
                arrivalCalenderText.setText(g+", "+String.valueOf(dayOfMonth) + " " + month(String.valueOf(monthOfYear + 1)) + " " + String.valueOf(year));
                cal.set(Calendar.MONTH,monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                cal.set(Calendar.YEAR,year);
                todayCheck = cal.getTimeInMillis();
                fromCal.setTimeInMillis(cal.getTimeInMillis());
            }
            timeDialog();
        }
    };




    public void timeDialog(){

        AlertDialog.Builder timedialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewTime = inflater.inflate(R.layout.dialog_timepicker_layout, null);
        timedialog.setView(dialogViewTime);

        final TimeNumberPicker p1 = (TimeNumberPicker) dialogViewTime.findViewById(R.id.p1);
        final TimeNumberPicker p2 = (TimeNumberPicker) dialogViewTime.findViewById(R.id.p2);
        final TimeNumberPicker p3 = (TimeNumberPicker) dialogViewTime.findViewById(R.id.p3);
        final TimeNumberPicker p4 = (TimeNumberPicker) dialogViewTime.findViewById(R.id.p4);
        final TimeNumberPicker p5 = (TimeNumberPicker) dialogViewTime.findViewById(R.id.p5);
        final TimeNumberPicker p6 = (TimeNumberPicker) dialogViewTime.findViewById(R.id.p6);
        setNumberPickerTextColor(p1);
        setNumberPickerTextColor(p2);
        setNumberPickerTextColor(p3);
        setNumberPickerTextColor(p4);
        setNumberPickerTextColor(p5);
        setNumberPickerTextColor(p6);
        final TextView fromTime = (TextView) dialogViewTime.findViewById(R.id.fromTime);
        final TextView toTime = (TextView) dialogViewTime.findViewById(R.id.toTime);
        final Button ok = (Button) dialogViewTime.findViewById(R.id.confirmOk);
        final Button cancel = (Button) dialogViewTime.findViewById(R.id.confirmNotOk);

        final String[] hour = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        final String[] minute = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35","36", "37", "38", "39", "40", "41", "42", "43","44","45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55","56", "57", "58", "59"};
        final String[] aa = new String[]{"AM", "PM"};


        //set p1 values for "from" hour
        p1.setMaxValue(hour.length-1);
        p1.setDisplayedValues(hour);
        p1.setValue(oneToTwelve(fromCal.get(Calendar.HOUR_OF_DAY)) - 1);
        p1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //detects the change for p1 values
        p1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                p1.setValue(i1);
                fromCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue()),Integer.parseInt(minute[p2.getValue()]));

                Log.e("fromCal.set",""+fromCal.getTimeInMillis());
                Log.e("genCal.set",""+genCal.getTimeInMillis());

                //Set p3 value to AM or PM depending on Calender "fromCal" time
                if(AMorPM(fromCal.get(Calendar.HOUR_OF_DAY)) == 1){
                    p3.setValue(1);
                } else {
                    p3.setValue(0);
                }

                //To check the change from 11:59 AM to 12:00 PM
                if((i==10 && i1==11) && (p3.getValue() == 0)){
                    p3.setValue(1);
                }

                fromCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue()),Integer.parseInt(minute[p2.getValue()]));

                //Can't select past time
                if(fromCal.getTimeInMillis() < genCal.getTimeInMillis()){
                    p1.setValue(i);
                    fromCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue()),Integer.parseInt(minute[p2.getValue()]));
                }


                toCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue())+2,Integer.parseInt(minute[p2.getValue()]));
                Log.e("toCal.set",""+toCal.getTimeInMillis());
                p4.setValue(oneToTwelve(toCal.get(Calendar.HOUR_OF_DAY)) - 1);


                //Set p6 value to AM or PM depending on Calender "toCal" time
                if(AMorPM(toCal.get(Calendar.HOUR_OF_DAY)) == 1){
                    p6.setValue(1);
                } else {
                    p6.setValue(0);
                }

                //set the text according to the values in the number pickers
                fromTime.setText(hour[p1.getValue()]+":"+minute[p2.getValue()]+" "+aa[p3.getValue()]);//最后
                toTime.setText(hour[p4.getValue()]+":"+minute[p5.getValue()]+" "+aa[p6.getValue()]);//最后
            }
        });



        p2.setMaxValue(minute.length-1);
        p2.setDisplayedValues(minute);
        p2.setValue(fromCal.get(Calendar.MINUTE));
        p2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //detects the change for p2 values
        p2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                p2.setValue(i1);
                fromCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue()),Integer.parseInt(minute[p2.getValue()]));

                //Can't select past time based on minute
                if(fromCal.getTimeInMillis() < genCal.getTimeInMillis()){
                    p2.setValue(i);
                    fromCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue()),Integer.parseInt(minute[p2.getValue()]));
                }
                p5.setValue(p2.getValue());

                fromTime.setText(hour[p1.getValue()]+":"+minute[p2.getValue()]+" "+aa[p3.getValue()]);
                toTime.setText(hour[p4.getValue()]+":"+minute[p5.getValue()]+" "+aa[p6.getValue()]);
            }
        });

        //set p3 values for "from" AM or PM
        p3.setMinValue(0);
        p3.setMaxValue(aa.length-1);
        p3.setDisplayedValues(aa);
        if(AMorPM(fromCal.get(Calendar.HOUR_OF_DAY)) == 1){
            p3.setValue(1);
        } else {
            p3.setValue(0);
        }
        p3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //detects the change for p3 values
        p3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                p3.setValue(i1);
                fromCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue()),Integer.parseInt(minute[p2.getValue()]));

                Log.e("fromCal.set",""+fromCal.getTimeInMillis());
                Log.e("onetoTwenty",""+oneToTwentyFour(hour[p1.getValue()],p3.getValue()));

                //can't select the past time
                if(fromCal.getTimeInMillis() < genCal.getTimeInMillis()){
                    p3.setValue(i);
                    fromCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue()),Integer.parseInt(minute[p2.getValue()]));
                }

                toCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue())+2,Integer.parseInt(minute[p2.getValue()]));
                p4.setValue(oneToTwelve(toCal.get(Calendar.HOUR_OF_DAY)) - 1);

                //Set p6 value to AM or PM depending on Calender "toCal" time
                if(AMorPM(toCal.get(Calendar.HOUR_OF_DAY)) == 1){
                    p6.setValue(1);
                } else {
                    p6.setValue(0);
                }

                fromTime.setText(hour[p1.getValue()]+":"+minute[p2.getValue()]+" "+aa[p3.getValue()]);
                toTime.setText(hour[p4.getValue()]+":"+minute[p5.getValue()]+" "+aa[p6.getValue()]);
            }
        });

        toCal.set(fromCal.get(Calendar.YEAR),fromCal.get(Calendar.MONTH),fromCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p1.getValue()],p3.getValue())+2,Integer.parseInt(minute[p2.getValue()]));
        fromTime.setText(hour[p1.getValue()]+":"+minute[p2.getValue()]+" "+aa[p3.getValue()]);

        p4.setMinValue(0);
        p4.setMaxValue(hour.length-1);
        p4.setDisplayedValues(hour);
        p4.setValue(oneToTwelve(toCal.get(Calendar.HOUR_OF_DAY)) - 1);
        p4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        p4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                p4.setValue(i1);

                toCal.set(toCal.get(Calendar.YEAR),toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p4.getValue()],p6.getValue()),Integer.parseInt(minute[p5.getValue()]));
                Log.e("toCal.setp4",""+toCal.getTimeInMillis());

                if(toCal.getTimeInMillis() < fromCal.getTimeInMillis()){
                    p4.setValue(i);
                    toCal.set(toCal.get(Calendar.YEAR),toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p4.getValue()],p6.getValue()),Integer.parseInt(minute[p5.getValue()]));
                }

                //checking if time selected is more than 8 hours
                if(fromCal.getTimeInMillis() + 28800000 < toCal.getTimeInMillis()){
                    p4.setValue(i);
                    toCal.set(toCal.get(Calendar.YEAR),toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p4.getValue()],p6.getValue()),Integer.parseInt(minute[p5.getValue()]));
                }

                //Set p6 value to AM or PM depending on Calender "toCal" time
                if(AMorPM(toCal.get(Calendar.HOUR_OF_DAY)) == 1){
                    p6.setValue(1);
                } else {
                    p6.setValue(0);
                }

                toTime.setText(hour[p4.getValue()]+":"+minute[p5.getValue()]+" "+aa[p6.getValue()]);
            }
        });

        p5.setMinValue(0);
        p5.setMaxValue(minute.length-1);
        p5.setDisplayedValues(minute);
        p5.setValue(p2.getValue());
        p5.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        p5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                p5.setValue(i1);

                toCal.set(toCal.get(Calendar.YEAR),toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p4.getValue()],p6.getValue()),Integer.parseInt(minute[p5.getValue()]));

                if(toCal.getTimeInMillis() < fromCal.getTimeInMillis()){
                    p5.setValue(i);
                    toCal.set(toCal.get(Calendar.YEAR),toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p4.getValue()],p6.getValue()),Integer.parseInt(minute[p5.getValue()]));
                }

                //checking if time selected is more than 8 hours
                if(fromCal.getTimeInMillis() + 28800000 < toCal.getTimeInMillis()){
                    p5.setValue(i);
                    toCal.set(toCal.get(Calendar.YEAR),toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p4.getValue()],p6.getValue()),Integer.parseInt(minute[p5.getValue()]));
                }

                toTime.setText(hour[p4.getValue()]+":"+minute[p5.getValue()]+" "+aa[p6.getValue()]);
            }
        });

        p6.setMinValue(0);
        p6.setMaxValue(aa.length-1);
        p6.setDisplayedValues(aa);
        if(AMorPM(toCal.get(Calendar.HOUR_OF_DAY)) == 1){
            p6.setValue(1);
        } else {
            p6.setValue(0);
        }
        p6.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        toTime.setText(hour[p4.getValue()]+":"+minute[p5.getValue()]+" "+aa[p6.getValue()]);

        p6.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                p6.setValue(i1);
                toCal.set(toCal.get(Calendar.YEAR),toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p4.getValue()],p6.getValue()),Integer.parseInt(minute[p5.getValue()]));

                if(toCal.getTimeInMillis() < fromCal.getTimeInMillis()){
                    p6.setValue(i);
                    toCal.set(toCal.get(Calendar.YEAR),toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p4.getValue()],p6.getValue()),Integer.parseInt(minute[p5.getValue()]));
                }

                //checking if time selected is more than 8 hours
                if(fromCal.getTimeInMillis()+288  < toCal.getTimeInMillis()){
                    p6.setValue(i);
                    toCal.set(toCal.get(Calendar.YEAR),toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH),oneToTwentyFour(hour[p4.getValue()],p6.getValue()),Integer.parseInt(minute[p5.getValue()]));
                }

                toTime.setText(hour[p4.getValue()]+":"+minute[p5.getValue()]+" "+aa[p6.getValue()]);
            }
        });

        final Dialog d = timedialog.create();
        d.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
                String fTime = format.format(fromCal.getTime());
                String tTime = format.format(toCal.getTime());
                arrivalTimeText.setText(fTime+" - "+tTime);
                d.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }

    //returns in 12 hour format given 24 hour format as argument
    public int oneToTwelve(int hour){
        if(hour>12 && hour<=23){
            return hour-12;
        }
        return hour;
    }

    //returns if time is in AM or PM
    public int AMorPM(int hour){
        if(hour>=12 && hour<=23){
            return 1;
        }
        return 0;
    }

    //returns in 24 hour format when given in 12 hour format along with AM or PM as arguments
    public int oneToTwentyFour(String hour, int k){
        if((k == 0) && Integer.parseInt(hour) == 12){
            return 0;
        }
        if(k == 1 && Integer.parseInt(hour) <= 11){
            return Integer.parseInt(hour)+12;
        } else {
            return Integer.parseInt(hour);
        }
    }

    public static boolean setNumberPickerTextColor(TimeNumberPicker numberPicker)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((EditText)child).setTextSize(30);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    Log.w("setNumberPicker", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumberPicker", e);
                }
            }
        }
        return false;
    }

    public static String month(String months)
    {
        switch (months)
        {
            case "1":
                return "Jan";
            case "2":
                return "Feb";
            case "3":
                return "Mar";
            case "4":
                return "Apr";
            case "5":
                return "May";
            case "6":
                return "Jun";
            case "7":
                return "Jul";
            case "8":
                return "Aug";
            case "9":
                return "Sept";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
            default:
                return null;
        }

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





}
}
