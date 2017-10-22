package com.example.a888888888.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import kelvin.tablayout.kelvin_tab_layout;
import qwer.dietcontrol;

public class  MainActivity extends AppCompatActivity
        implements Over.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button kel = (Button)findViewById(R.id.button); //連至書輝的按鈕
        Button hel = (Button)findViewById(R.id.button1); //連至弘盛的按鈕
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
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, dietcontrol.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Over overs=Over.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.content_main,
                        overs,
                        overs.getTag()
                ).commit();
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,sport.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }
}
