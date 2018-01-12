package com.example.a888888888.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import kelvin.tablayout.kelvin_tab_layout;
import necowneco.tablayout.habaActivity;
import qwer.BlankFragment;
import qwer.BlankFragment2;
import qwer.BlankFragment3;
import qwer.BlankFragmentc1;
import qwer.BlankFragmentc2;
import qwer.BlankFragmentc3;
import qwer.BlankFragmentc4;
import qwer.Dietcontrol;

public class  MainActivity extends AppCompatActivity
        implements Over.OnFragmentInteractionListener,Sport.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener, BlankFragment3.OnFragmentInteractionListener
        ,Run.OnFragmentInteractionListener,Walk.OnFragmentInteractionListener,Air.OnFragmentInteractionListener,Sit.OnFragmentInteractionListener,Push.OnFragmentInteractionListener, BlankFragmentc1.OnFragmentInteractionListener, BlankFragmentc2.OnFragmentInteractionListener, BlankFragmentc3.OnFragmentInteractionListener, BlankFragmentc4.OnFragmentInteractionListener
{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button kel = (Button)findViewById(R.id.button); //連至書輝的按鈕
        Button hal = (Button)findViewById(R.id.button1); //連至弘盛的按鈕
        Button del = (Button)findViewById(R.id.button2); //連至琨城的按鈕
        Button over = (Button)findViewById(R.id.button3); //連至直播的按鈕
        Button sport = (Button)findViewById(R.id.button4); //連至運動的按鈕
        kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,kelvin_tab_layout.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        hal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,habaActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dietcontrol del = new Dietcontrol();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_main,
                        del,
                        del.getTag()
                ).commit();

            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Over overs=Over.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_main,
                        overs,
                        overs.getTag()
                ).commit();
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Sport sport=Sport.newInstance("param1","param2");
                    FragmentManager manager=getSupportFragmentManager();
                    manager.beginTransaction().addToBackStack(null).replace(
                            R.id.content_main,
                            sport,
                            sport.getTag()
                    ).commit();
                }
        });
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }
}
