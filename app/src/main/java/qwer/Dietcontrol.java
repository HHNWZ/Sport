package qwer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.a888888888.sport.R;

public class Dietcontrol extends Fragment implements View.OnTouchListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qwer_dietcontrol, null);
        view.setOnTouchListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
