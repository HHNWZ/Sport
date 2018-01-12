package kelvin.tablayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by 888888888 on 2017/11/24.
 */

public class StartTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public static final String HOUR_OF_START_TIME_PICKER_FRAGMENT="hour_of_start_time_picker_fragment";
    public static final String MINUTE_OF_START_TIME_PICKER_FRAGMENT="minute_of_start_time_picker_fragment";

    public StartTimeFragment() {
        // Required empty public constructor
    }

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

        //Toast.makeText(getActivity(), "hour="+hourOfDay+", min="+minute, Toast.LENGTH_SHORT).show();

        /*boolean pressed = true;
        Intent intent = new Intent(getActivity(), kelvin_running_invitation.class);
        intent.putExtra("hour", hourOfDay);
        intent.putExtra("minute", minute);
        intent.putExtra("pressed", pressed);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().finish();
        startActivity(intent);*/
        String hour_of_time_picker_fragment= Integer.toString(hourOfDay);
        String minute_of_time_picker_fragment=Integer.toString(minute);
        if(getTargetFragment() == null){
            return;
        }else{
            Intent intent = new Intent();
            intent.putExtra(HOUR_OF_START_TIME_PICKER_FRAGMENT,hour_of_time_picker_fragment);
            intent.putExtra(MINUTE_OF_START_TIME_PICKER_FRAGMENT,minute_of_time_picker_fragment);
            getTargetFragment().onActivityResult(kelvin_running_invitation.start_time_data_of_running_invitation,Activity.RESULT_OK,intent);
            getTargetFragment().onActivityResult(kelvin_walking_invitation.start_time_data_of_walking_invitation,Activity.RESULT_OK,intent);
            getTargetFragment().onActivityResult(kelvin_aerobic_exercise_invitation.start_time_data_of_aerobic_exercise_invitation, Activity.RESULT_OK,intent);
            getTargetFragment().onActivityResult(kelvin_push_up_invitation.start_time_data_of_push_up_invitation, Activity.RESULT_OK,intent);
            getTargetFragment().onActivityResult(kelvin_sit_up_invitation.start_time_data_of_sit_up_invitation, Activity.RESULT_OK,intent);
        }

    }


}
