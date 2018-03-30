package com.example.a888888888.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import qwer.BlankFragment2;
import qwer.BlankFragment3;
import qwer.BlankFragmentc1;
import qwer.BlankFragmentc2;
import qwer.BlankFragmentc3;
import qwer.BlankFragmentc4;
import qwer.Dietcontrol;

public class  MainActivity extends AppCompatActivity
        implements Over.OnFragmentInteractionListener,Sport.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener, BlankFragment3.OnFragmentInteractionListener
        ,Run.OnFragmentInteractionListener,Walk.OnFragmentInteractionListener,Air.OnFragmentInteractionListener,Sit.OnFragmentInteractionListener,Push.OnFragmentInteractionListener,Login.OnFragmentInteractionListener,
        BlankFragmentc1.OnFragmentInteractionListener , BlankFragmentc2.OnFragmentInteractionListener , BlankFragmentc3.OnFragmentInteractionListener , BlankFragmentc4.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener
        ,Userdata.OnFragmentInteractionListener{

    private String showUri = "http://172.30.4.40:1335/test123.php";//連至資料庫
    private TextView rundata;
    private TextView walkdata;
    private TextView airdata;
    private TextView pushdata;
    private TextView sitdata;
    private MyDBHelper dbHelper; //內建資料庫
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolboar;
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
        mToolboar=(Toolbar)findViewById(R.id.nav_action); //替換toolbar會爆
        setSupportActionBar(mToolboar);//Toolbar取代原本的ActionBar
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);//必須用字串資源檔
        mDrawerLayout.addDrawerListener(mToggle);//監聽選單按鈕是否被觸擊
        mToggle.syncState();//隱藏顯示箭頭返回
        //讓 ActionBar 中的返回箭號置換成 Drawer 的三條線圖示。並且把這個觸發器指定給 layDrawer 。
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);//清單觸發監聽事件

        Button kel = (Button)findViewById(R.id.button); //連至書輝的按鈕
        Button hal = (Button)findViewById(R.id.button1); //連至弘盛的按鈕
        Button del = (Button)findViewById(R.id.button2); //連至琨城的按鈕
        Button over = (Button)findViewById(R.id.button3); //連至直播的按鈕
        Button sport = (Button)findViewById(R.id.button4); //連至運動的按鈕
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        rundata =(TextView)findViewById(R.id.textView6);
        walkdata =(TextView)findViewById(R.id.textView7);
        airdata =(TextView)findViewById(R.id.textView8);
        pushdata =(TextView)findViewById(R.id.textView10);
        sitdata =(TextView)findViewById(R.id.textView9);
        getData();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){//當按下左上三條線或顯示工具列
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Login) {
            Login login=Login.newInstance("param1","param2");
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().addToBackStack(null).replace(
                    R.id.content_main,
                    login,
                    login.getTag()
            ).commit();
        }
        else if (id == R.id.navItemAbout)
        {
            Userdata userdata=Userdata.newInstance("param1","param2");
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().addToBackStack(null).replace(
                    R.id.content_main,
                    userdata,
                    userdata.getTag()
            ).commit();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
