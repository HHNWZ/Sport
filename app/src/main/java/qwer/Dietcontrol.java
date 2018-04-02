package qwer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a888888888.sport.R;

public class Dietcontrol extends Fragment implements View.OnTouchListener,addDiary.OnFragmentInteractionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qwer_dietcontrol, null);
        view.setOnTouchListener(this);

        Button angry_btn=(Button)view.findViewById(R.id.angry_btn);
        angry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new BlankFragment(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button angry_btn1=(Button)view.findViewById(R.id.angry_bt1);
        angry_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new BlankFragmentc1(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button angry_btn2=(Button)view.findViewById(R.id.angry_bt2);
        angry_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new BlankFragment2(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button angry_btn3=(Button)view.findViewById(R.id.angry_btn3);
        angry_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new BlankFragment3(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    @Override

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }
    /*public void BackToCalendar(String mydate){
        DL.remove(mydate);
    }
    public void toAddDiary(String mydate) {
        DL.add(mydate.toString());
        addDiary adddiary=addDiary.newInstance(mydate,null);
        FragmentManager manager=SupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.content_main,adddiary,null)
                .addToBackStack(null)
                .commit();
    }
*/
}
