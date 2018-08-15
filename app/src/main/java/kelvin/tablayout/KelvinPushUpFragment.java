package kelvin.tablayout;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a888888888.sport.BackHandlerHelper;
import com.example.a888888888.sport.FragmentBackHandler;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelvinPushUpFragment extends Fragment implements FragmentBackHandler {

    private static final int DEFAULT_DATA = 0;
    private static final int SUBCOLUMNS_DATA = 1;
    private static final int STACKED_DATA = 2;
    private static final int NEGATIVE_SUBCOLUMNS_DATA = 3;
    private static final int NEGATIVE_STACKED_DATA = 4;

    private ColumnChartView chart_of_push_up_today_record;
    private ColumnChartData data_of_push_up_today_record;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private int dataType = DEFAULT_DATA;
    public String pTime;
    public Button button_of_task_execution,button_of_sports_monitoring;
    private static DatabaseReference mDatabase;
    private static FirebaseAuth mAuth;

    public KelvinPushUpFragment() {
        // Required empty public constructor////
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        View rootView= inflater.inflate(R.layout.fragment_kelvin_exercise, container, false);
        TextView text_View_of_exercise_title=(TextView)rootView.findViewById(R.id.exercise_title);
        TextView text_view_of_today_record_data=(TextView)rootView.findViewById(R.id.text_view_of_today_record_data);
        TextView text_view_of_lowest_record_data=(TextView)rootView.findViewById(R.id.text_view_of_lowest_record_data) ;
        TextView text_view_of_highest_record_data=(TextView)rootView.findViewById(R.id.text_view_of_highest_record_data);
        TextView text_view_of_today_record_unit=(TextView)rootView.findViewById(R.id.text_view_of_today_record_unit);
        TextView text_view_of_lowest_record_unit=(TextView)rootView.findViewById(R.id.text_view_of_lowest_record_unit) ;
        TextView text_view_of_highest_record_unit=(TextView)rootView.findViewById(R.id.text_view_of_highest_record_unit);
        text_View_of_exercise_title.setText("深蹲身個人記錄");
        //text_view_of_today_record_data.setText("10");
        //text_view_of_highest_record_data.setText("15");
        //text_view_of_lowest_record_data.setText("5");
        text_view_of_today_record_unit.setText("次");
        text_view_of_lowest_record_unit.setText("次");
        text_view_of_highest_record_unit.setText("次");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String DataIdcheck=dataSnapshot.child("exercise").child("squats").child("DataIdcheck").getValue().toString();
                String big_count=dataSnapshot.child("exercise_count").child("squats").child("big_count").getValue().toString();
                String small_count=dataSnapshot.child("exercise_count").child("squats").child("small_count").getValue().toString();
                String count=dataSnapshot.child("exercise_count").child("squats").child("count").getValue().toString();
                String today_count=dataSnapshot.child("exercise_count").child("squats").child("today_count").getValue().toString();
                String all_count=dataSnapshot.child("exercise_count").child("squats").child("all_count").getValue().toString();
                String dataId=dataSnapshot.child("exercise").child("squats").child("dataId").getValue().toString();

                int count1=Integer.parseInt(count);
                int today_count1=Integer.parseInt(today_count);
                int all_count1=Integer.parseInt(all_count);
                //Toast.makeText(getContext(), "DataIdcheck"+DataIdcheck, Toast.LENGTH_SHORT).show();
                if(DataIdcheck.equals(dataId)){
                    //Toast.makeText(getContext(), "DataIdcheck=dataID", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), "DataIdcheck"+DataIdcheck, Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(getContext(), "DataIdcheck!=dataID", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), "DataIdcheck"+DataIdcheck, Toast.LENGTH_SHORT).show();
                    today_count1=today_count1+count1;
                    all_count1=all_count1+count1;

                    mDatabase.child("exercise_count").child("squats").child("today_count").setValue(today_count1);
                    mDatabase.child("exercise_count").child("squats").child("all_count").setValue(all_count1);
                    mDatabase.child("exercise").child("squats").child("DataIdcheck").setValue(dataId);
                }


                text_view_of_highest_record_data.setText(big_count);
                text_view_of_lowest_record_data.setText(small_count);
                text_view_of_today_record_data.setText(""+today_count1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final Button button_of_invitation=(Button)rootView.findViewById(R.id.button_of_invitation);
        button_of_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_kelvin_running_invitation,new kelvin_push_up_invitation(),null)
                        .addToBackStack(null)
                        .commit();
            }

        });
        pTime=Week.getWeek(System.currentTimeMillis());
        Log.i("今天是",pTime);
       button_of_task_execution=(Button)rootView.findViewById(R.id.button_of_task_execution);
        if(pTime.equals("五")){
            button_of_task_execution.setVisibility(View.VISIBLE);
        }else{
            button_of_task_execution.setVisibility(View.INVISIBLE);
        }
        button_of_task_execution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Push_up_task.class);
                startActivity(intent);

            }
        });
        chart_of_push_up_today_record = (ColumnChartView) rootView.findViewById(R.id.chart_of_running_today_record);
        //chart.setOnValueTouchListener(new ValueTouchListener());
        chart_of_push_up_today_record.setZoomEnabled(false);
        button_of_sports_monitoring=(Button)rootView.findViewById(R.id.button_of_sports_monitoring);
        button_of_sports_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SquatsMonitor.class);
                startActivity(intent);
            }
        });
        generateData();
        return rootView;
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }

    private void generateDefaultData() {
        int numSubcolumns = 1;
        int numColumns = 100;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 10f + 5, ChartUtils.COLOR_GREEN));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data_of_push_up_today_record = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("X軸");
                axisY.setName("Y軸");
                axisX.setLineColor(Color.BLACK);// 设置X轴轴线颜色
                axisY.setLineColor(Color.BLACK);// 设置Y轴轴线颜色
                axisX.setTextColor(Color.BLACK);// 设置X轴文字颜色
                axisY.setTextColor(Color.BLACK);// 设置Y轴文字颜色
                axisX.setTextSize(14);// 设置X轴文字大小
                axisY.setTextSize(14);


            }
            data_of_push_up_today_record.setAxisXBottom(axisX);
            data_of_push_up_today_record.setAxisYLeft(axisY);
        } else {
            data_of_push_up_today_record.setAxisXBottom(null);
            data_of_push_up_today_record.setAxisYLeft(null);
        }

        chart_of_push_up_today_record.setColumnChartData(data_of_push_up_today_record);

    }

    private int getSign() {
        int[] sign = new int[]{-1, 1};
        return sign[Math.round((float) Math.random())];
    }

    private void generateData() {
        switch (dataType) {
            case DEFAULT_DATA:
                generateDefaultData();
                break;
            case SUBCOLUMNS_DATA:
                //generateSubcolumnsData();
                break;
            case STACKED_DATA:
                //generateStackedData();
                break;
            case NEGATIVE_SUBCOLUMNS_DATA:
                //generateNegativeSubcolumnsData();
                break;
            case NEGATIVE_STACKED_DATA:
                //generateNegativeStackedData();
                break;
            default:
                generateDefaultData();
                break;
        }
    }

}
