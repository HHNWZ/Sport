package kelvin.tablayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.View;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.DatePickerDialog;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.example.a888888888.sport.R;

import java.util.Calendar;

/**
 * Created by 888888888 on 2017/11/24.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(getActivity(), "hour="+hourOfDay+", min="+minute, Toast.LENGTH_SHORT).show();
    }


}
// Toast.makeText(getActivity(), "hour="+hourOfDay+", min="+minute, Toast.LENGTH_SHORT).show();