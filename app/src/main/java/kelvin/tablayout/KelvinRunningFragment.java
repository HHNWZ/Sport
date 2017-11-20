package kelvin.tablayout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.a888888888.sport.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class KelvinRunningFragment extends Fragment {
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;

    public KelvinRunningFragment() {
        // Required empty public constructor kkkkkkkkkkkkk
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_kelvin_exercise, container, false);
        TextView text_View_of_exercise_title=(TextView)rootView.findViewById(R.id.exercise_title);
        TextView text_view_of_today_record_data=(TextView)rootView.findViewById(R.id.text_view_of_today_record_data);
        TextView text_view_of_lowest_record_data=(TextView)rootView.findViewById(R.id.text_view_of_lowest_record_data) ;
        TextView text_view_of_highest_record_data=(TextView)rootView.findViewById(R.id.text_view_of_highest_record_data);
        final Button button_of_invitation=(Button)rootView.findViewById(R.id.button_of_invitation);
        final Toolbar Toolbar_of_app=(Toolbar)rootView.findViewById(R.id.toolBar);

        text_View_of_exercise_title.setText("跑步個人記錄");
        text_view_of_today_record_data.setText("100米");
        text_view_of_highest_record_data.setText("200米");
        text_view_of_lowest_record_data.setText("50米");
        button_of_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_kelvin_running_invitation,new kelvin_running_invitation(),null)
                        .addToBackStack(null)
                        .commit();
                        button_of_invitation.setVisibility(View.INVISIBLE);
                        //Toolbar_of_app.setVisibility(View.INVISIBLE);
            }

        });
        return rootView;




    }




}


