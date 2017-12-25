package kelvin.tablayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;

/**
 * Created by 888888888 on 2017/12/22.
 */

public class DatePickerFragment1 extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public String date;
    public String DATE_OF_Date_PICKER_FRAGMENT="date_of_date_picker_fragment";
    public  static final String DAY_OF_Date_PICKER_FRAGMENT="day_of_date_picker_fragment";
    public  static final String MONTH_OF_Date_PICKER_FRAGMENT="month_of_date_picker_fragment";
    public  static final String YEAR_OF_Date_PICKER_FRAGMENT="year_of_date_picker_fragment";
    public String day;
    public String month;
    public String year1;

    public DatePickerFragment1() {
        // Required empty public constructor
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //得到Calendar类实例，用于获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //返回DatePickerDialog对象
        //因为实现了OnDateSetListener接口，所以第二个参数直接传入this
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    //实现OnDateSetListener接口的onDateSet()方法
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //这样子写就将选择时间的fragment和选择日期的fragment完全绑定在一起
        //使用的时候只需直接调用DatePickerFragment的show()方法
        //即可选择完日期后选择时间
        //将用户选择的日期传到TimePickerFragment
        date = year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日";
        day= Integer.toString(dayOfMonth);
        month=Integer.toString(monthOfYear+1);
        year1=Integer.toString(year);

        //Toast.makeText(getActivity(), "日期"+date , Toast.LENGTH_SHORT).show();
        if(getTargetFragment() == null){
            return;
        }else{
            Intent intent = new Intent();
            intent.putExtra(DAY_OF_Date_PICKER_FRAGMENT,day);
            intent.putExtra(MONTH_OF_Date_PICKER_FRAGMENT,month);
            intent.putExtra(YEAR_OF_Date_PICKER_FRAGMENT,year1);
            getTargetFragment().onActivityResult(kelvin_running_invitation.RE, Activity.RESULT_OK,intent);
        }
    }


}
