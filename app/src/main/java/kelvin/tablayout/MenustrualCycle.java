package kelvin.tablayout;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenustrualCycle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenustrualCycle extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int mParam1;
    private int mParam2;
    // TODO: Rename and change types of parameters

    private EditText theday;
    private EditText thetimes;
    private View view;
    private Button subday;
    private Button addday;
    private Button subtimes;
    private Button addtimes;

    private String subdayString;
    private String adddaySting;
    private String subtimesString;
    private String addtimesString;

    private int subdayInt;
    private int adddayInt;
    private int subtimesInt;
    private int addtimesInt;
    private int cdayyear;
    private int cdaymonth;
    private int cdayday;
    private EventDecorator mydates;
    private HashSet<CalendarDay> dates=new HashSet<>();
    private int[] DayNumOfMon={31,28,31,30,31,30,31,31,30,31,30,31};
    private OnFragmentInteractionListener mListener;



    public MenustrualCycle() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenustrualCycle.
     */
    // TODO: Rename and change types and number of parameters
    public static MenustrualCycle newInstance(int param1, int param2) {
        MenustrualCycle fragment = new MenustrualCycle();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        Log.i("傳值1",""+ARG_PARAM1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menustrual_cycle, null);
        EditText myday=view.findViewById(R.id.theday);
        EditText mytimes=view.findViewById(R.id.thetimes);
        myday.setText(Integer.toString(mParam1));
        mytimes.setText(Integer.toString(mParam2));
        Button adD=view.findViewById(R.id.addday);
        adD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MonitoringTool)getActivity()).days++;
                ((MonitoringTool)getActivity()).restart();
            }
        });
        Button suD=view.findViewById(R.id.subday);
        suD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mParam1>0){
                    ((MonitoringTool)getActivity()).days--;
                    ((MonitoringTool)getActivity()).restart();
                }
            }
        });
        Button adT=view.findViewById(R.id.addtimes);
        adT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MonitoringTool)getActivity()).times++;
                ((MonitoringTool)getActivity()).restart();
            }
        });
        Button suT=view.findViewById(R.id.subtimes);
        suT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mParam2>0){
                    ((MonitoringTool)getActivity()).times--;
                    ((MonitoringTool)getActivity()).restart();
                }
            }
        });
        final MaterialCalendarView materialCalendarView=(MaterialCalendarView)view.findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(DayOfWeek.of(Calendar.MONDAY))
                .setMinimumDate(CalendarDay.from(2017,12,31))
                .setMaximumDate(CalendarDay.from(2100,12,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        materialCalendarView.setDateSelected(((MonitoringTool)getActivity()).seleDAY,true);//預設選擇今天
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {

            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {//Toast.makeText(getActivity(),""+date,Toast.LENGTH_LONG).show();預設顯示日期
                Toast.makeText(getActivity(),
                        showTrueDate1(date),
                        Toast.LENGTH_LONG).show();
                ((MonitoringTool) getActivity()).myDayChanged(date);//選擇日期
                setMyDate(date,((MonitoringTool)getActivity()).days,((MonitoringTool)getActivity()).times);
                mydates=new EventDecorator(Color.RED,dates);
                widget.removeDecorators();
                widget.addDecorator(mydates);
            }
        });

        return view;
    }

    private  void setMyDate(CalendarDay date,int daysNumber,int timesNumber){
        dates.clear();
        int overMon=0;
        ArrayList<Integer> ck = new ArrayList<>();
        CalendarDay dirDay=date;
        for(int i=0;i<6;i++){
            for(int j=0;j<daysNumber+timesNumber;j++){
                if(j<daysNumber) {
                    dates.add(dirDay);
                }
                //Toast.makeText(getActivity(),"IS："+dirDay.getMonth() , Toast.LENGTH_SHORT).show();
                if(dirDay.getDay()<DayNumOfMon[dirDay.getMonth()-1]){//跨日
                    dirDay=CalendarDay.from(
                            dirDay.getYear(),
                            dirDay.getMonth(),
                            dirDay.getDay()+1
                    );
                }else{
                    if(dirDay.getMonth()<12){//跨月
                        dirDay=CalendarDay.from(
                                dirDay.getYear(),
                                dirDay.getMonth()+1,
                                1

                        );
                        ck.add(dirDay.getMonth());
                    }else{//跨年
                        dirDay=CalendarDay.from(
                                dirDay.getYear()+1,
                                1,
                                1
                        );ck.add(dirDay.getMonth());
                    }
                }
            }
        }
        
    }


    private String showTrueDate1(CalendarDay cDay){//抓取分別的年月日
        cdayyear=cDay.getYear();
        cdaymonth=(cDay.getMonth()+1);
        cdayday=cDay.getDay();
        return cDay.getYear()+"/"+(cDay.getMonth()+1)+"/"+cDay.getDay();
    }//



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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Tag,String number);
    }

}
