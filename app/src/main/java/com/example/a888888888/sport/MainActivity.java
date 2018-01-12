package com.example.a888888888.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kelvin.tablayout.kelvin_tab_layout;
import necowneco.tablayout.habaActivity;
import qwer.BlankFragment;
import qwer.BlankFragment1;
import qwer.BlankFragment2;
import qwer.BlankFragment3;
import qwer.Dietcontrol;

public class  MainActivity extends AppCompatActivity
        implements Over.OnFragmentInteractionListener,Sport.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener, BlankFragment1.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener, BlankFragment3.OnFragmentInteractionListener
        ,Run.OnFragmentInteractionListener,Walk.OnFragmentInteractionListener,Air.OnFragmentInteractionListener,Sit.OnFragmentInteractionListener,Push.OnFragmentInteractionListener,Login.OnFragmentInteractionListener
{

    private String showUri = "http://172.30.4.40:1335/test123.php";//連至資料庫
    private TextView rundata;
    private TextView walkdata;
    private TextView airdata;
    private TextView pushdata;
    private TextView sitdata;
    private MyDBHelper dbHelper; //內建資料庫
    com.android.volley.RequestQueue requestQueue;
    private void getData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,showUri, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            JSONArray data = response.getJSONArray("data");
                            //這邊要和上面json的名稱一樣
                            //下邊是把全部資料都印出來
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jasondata = data.getJSONObject(i);
                                /*ContentValues values = new ContentValues();
                                values.put("run", jasondata.getString("run"));*/
                                rundata.setText(jasondata.getString("run"));
                                walkdata.setText(jasondata.getString("walk"));
                                airdata.setText(jasondata.getString("air"));
                                sitdata.setText(jasondata.getString("sit"));
                                pushdata.setText(jasondata.getString("push"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button kel = (Button)findViewById(R.id.button); //連至書輝的按鈕
        Button hal = (Button)findViewById(R.id.button1); //連至弘盛的按鈕
        Button del = (Button)findViewById(R.id.button2); //連至琨城的按鈕
        Button over = (Button)findViewById(R.id.button3); //連至直播的按鈕
        Button sport = (Button)findViewById(R.id.button4); //連至運動的按鈕
        Button login = (Button)findViewById(R.id.button5);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        rundata =(TextView)findViewById(R.id.textView6);
        walkdata =(TextView)findViewById(R.id.textView7);
        airdata =(TextView)findViewById(R.id.textView8);
        pushdata =(TextView)findViewById(R.id.textView10);
        sitdata =(TextView)findViewById(R.id.textView9);
        getData();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().addToBackStack(null).replace(
                        R.id.content_main,
                        login,
                        login.getTag()
                ).commit();

            }
        });

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
