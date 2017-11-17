package kelvin.tablayout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a888888888.sport.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelvinPushUpFragment extends Fragment {


    public KelvinPushUpFragment() {
        // Required empty public constructor////
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment kkkkkkkkkkkkkkkkkkk
        View rootView= inflater.inflate(R.layout.fragment_kelvin_exercise, container, false);
        TextView text_View_of_exercise_title=(TextView)rootView.findViewById(R.id.exercise_title);
        TextView text_view_of_today_record_data=(TextView)rootView.findViewById(R.id.text_view_of_today_record_data);
        TextView text_view_of_lowest_record_data=(TextView)rootView.findViewById(R.id.text_view_of_lowest_record_data) ;
        TextView text_view_of_highest_record_data=(TextView)rootView.findViewById(R.id.text_view_of_highest_record_data);
        text_View_of_exercise_title.setText("伏地挺身個人記錄");
        text_view_of_today_record_data.setText("10次");
        text_view_of_highest_record_data.setText("15次");
        text_view_of_lowest_record_data.setText("5次");
        return rootView;
    }

}
