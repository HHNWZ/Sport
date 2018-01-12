package kelvin.tablayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by 888888888 on 2017/12/9.
 */

public class TimePickerFragmentOfAdvanced extends DialogFragment  {
    DatePickerDialog.OnDateSetListener ondateSet;

    public TimePickerFragmentOfAdvanced() {
        // Required empty public constructor
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    private int year, month, day;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if(activity == null) return null;
        DatePickerDialog da = new DatePickerDialog(activity, ondateSet, year, month, day);
        Calendar c = Calendar.getInstance();

        //  c.add(Calendar.DATE, 1);
        //Date newDate = c.getTime();
        //da.getDatePicker().setMinDate(newDate.getTime());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        long l = c.getTimeInMillis();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        if(c.getTimeInMillis() < l) {
            l = c.getTimeInMillis();
        }

        da.getDatePicker().setMinDate(l);
        return da;
        //  return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }
}
