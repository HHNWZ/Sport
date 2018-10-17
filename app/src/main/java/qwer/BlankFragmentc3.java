package qwer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.DayOfWeek;

import java.util.Calendar;

import necowneco.tablayout.ArtListItem;

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
    private static final String ARG_PARAM3 = "param3";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private OnFragmentInteractionListener mListener;
    private int cdayyear;
    private int cdaymonth;
    private int cdayday;

    private Toolbar phy_cal_toolbar;

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
    public static BlankFragmentc3 newInstance(String param1, String param2,String param3) {
        BlankFragmentc3 fragment = new BlankFragmentc3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final int[] dayday = new int[1];
        final int[] dayday1 = new int[1];
        final String[] day= {"hello","world","java"};
        final String asd="123";
        View view = inflater.inflate(R.layout.fragment_blank_fragmentc3, null);
        view.setOnTouchListener(this);
        phy_cal_toolbar=(Toolbar)view.findViewById(R.id.phy_cal_toolbar);
        phy_cal_toolbar.setTitle("生理期計算工具");
        phy_cal_toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        phy_cal_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new Dietcontrol(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        ImageButton qwera2=(ImageButton)view.findViewById(R.id.imageButtona2);
        ListView listView=(ListView)view.findViewById(R.id.dynamic);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, day);

        listView.setAdapter(adapter);
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

        //行事曆
        final MaterialCalendarView materialCalendarView=(MaterialCalendarView)view.findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(DayOfWeek.MONDAY)
                .setMinimumDate(CalendarDay.from(2017,12,31))
                .setMaximumDate(CalendarDay.from(2100,12,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        materialCalendarView.setDateSelected(((MainActivity)getActivity()).seleDAY,true);//預設選擇今天
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {//Toast.makeText(getActivity(),""+date,Toast.LENGTH_LONG).show();預設顯示日期
                if(date== ((MainActivity)getActivity()).seleDAY) {//雙擊日期時
                   BlankFragmentDay blankfragmentday=BlankFragmentDay.newInstance(day1.getText().toString(),day2.getText().toString());
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_main,blankfragmentday,null)
                            .addToBackStack(null)
                            .commit();
                    ((MainActivity)getActivity()).toBFD(day1.getText().toString(),day2.getText().toString());//傳值到主頁面
                }else {//單擊日期時
                    Toast.makeText(getActivity(),
                            showTrueDate1(date),
                            Toast.LENGTH_LONG).show();
                    //由於日曆元件的月份是由0月開始計算，顯示正確月份時要+1
                }

                ((MainActivity) getActivity()).myDayChanged(date);//選擇日期
            }
        });
        return view;
    }
    private String showTrueDate1(CalendarDay cDay){//抓取分別的年月日
        cdayyear=cDay.getYear();
        cdaymonth=(cDay.getMonth()+1);
        cdayday=cDay.getDay();
        return cDay.getYear()+"/"+(cDay.getMonth()+1)+"/"+cDay.getDay();
    }//

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
