package kelvin.tablayout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a888888888.sport.R;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
    public static MenustrualCycle newInstance(String param1, String param2) {
        MenustrualCycle fragment = new MenustrualCycle();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menustrual_cycle, null);
        theday=(EditText) view.findViewById(R.id.theday);
        thetimes=(EditText)view.findViewById(R.id.thetimes);
        subday=(Button)view.findViewById(R.id.subday);
        addday=(Button)view.findViewById(R.id.addday);
        addtimes=(Button)view.findViewById(R.id.addtimes);
        subtimes=(Button)view.findViewById(R.id.subtimes);
        theday.setText("7");
        thetimes.setText("30");
        subdayString=theday.getText().toString();
        subdayInt=Integer.parseInt(subdayString);
        subtimesString=thetimes.getText().toString();
        subtimesInt=Integer.parseInt(subtimesString);

        if(subtimesInt<=1){
            subtimes.setVisibility(View.INVISIBLE);
        }else {
            subtimes.setVisibility(View.VISIBLE);
        }
        subday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subdayInt=subdayInt-1;
                theday.setText(""+subdayInt);
                if(subdayInt<=1){
                    subday.setVisibility(View.INVISIBLE);
                }else {
                    subday.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

}
