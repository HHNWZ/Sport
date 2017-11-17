package necowneco.tablayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;


public class habaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Allsport.OnFragmentInteractionListener,
        Runsport.OnFragmentInteractionListener,Walksport.OnFragmentInteractionListener,
        Airsport.OnFragmentInteractionListener,Sitsport.OnFragmentInteractionListener,
        Pushsport.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haba_neco);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView selsport=(TextView)findViewById(R.id.sel_sport);
        final Button spall = (Button)findViewById(R.id.sp_all);
        final Button sprun = (Button)findViewById(R.id.sp_run);
        final Button spwalk = (Button)findViewById(R.id.sp_walk);
        final Button spair = (Button)findViewById(R.id.sp_air);
        final Button spsit = (Button)findViewById(R.id.sp_sit);
        final Button sppush = (Button)findViewById(R.id.sp_push);
        spall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selsport.setText("所有運動動");
                onBackPressed();
                Allsport all=Allsport.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.haba,
                        all,
                        all.getTag()
                ).commit();
            }
        });
        sprun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selsport.setText("跑跑跑跑步");
                onBackPressed();
                Runsport run=Runsport.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.haba,
                        run,
                        run.getTag()
                ).commit();
            }
        });
        spwalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selsport.setText("走走走走路");
                onBackPressed();
                Walksport walk=Walksport.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.haba,
                        walk,
                        walk.getTag()
                ).commit();
            }
        });
        spair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selsport.setText("有有有氧氧");
                onBackPressed();
                Airsport air=Airsport.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.haba,
                        air,
                        air.getTag()
                ).commit();
            }
        });
        spsit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selsport.setText("仰仰臥起坐坐");
                onBackPressed();
                Sitsport sit=Sitsport.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.haba,
                        sit,
                        sit.getTag()
                ).commit();
            }
        });
        sppush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selsport.setText("伏地地挺身身");
                onBackPressed();
                Pushsport push=Pushsport.newInstance("param1","param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.haba,
                        push,
                        push.getTag()
                ).commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
// Do something.
            Intent intentHome= new Intent(habaActivity.this,MainActivity.class);
            startActivity(intentHome);
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }//返回鍵的設定
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.haba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String Tag, String number) {

    }
}
