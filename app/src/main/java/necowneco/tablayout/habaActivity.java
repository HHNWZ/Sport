package necowneco.tablayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;

import java.util.ArrayList;


public class habaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Allsport.OnFragmentInteractionListener,
        Runsport.OnFragmentInteractionListener,Walksport.OnFragmentInteractionListener,
        Airsport.OnFragmentInteractionListener,Sitsport.OnFragmentInteractionListener,
        Pushsport.OnFragmentInteractionListener,AddArt.OnFragmentInteractionListener,
        theArt.OnFragmentInteractionListener{

    final String[] SportList = {"所有運動","有氧運動","走路","跑步","伏地挺身","仰臥起坐"};
    final ArrayList<String> artID=new ArrayList<String>();
    final ArrayList<String> autID=new ArrayList<String>();
    final ArrayList<String> artTitle=new ArrayList<String>();
    final ArrayList<String> artClass=new ArrayList<String>();
    final ArrayList<String> artCon=new ArrayList<String>();
    final ArrayList<ArrayList> resList=new ArrayList<>();
    final String nowuser="369";
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
        artID.add("001");
        artID.add("002");
        artID.add("003");
        autID.add("123");
        autID.add("456");
        autID.add("789");
        artTitle.add("幹什麼");
        artTitle.add("幹三小");
        artTitle.add("幹朋友");
        artClass.add(SportList[3]);
        artClass.add(SportList[5]);
        artClass.add(SportList[1]);
        artCon.add("反覆橫跳100次!!");
        artCon.add("反覆橫跳200次!!");
        artCon.add("反覆橫跳300次!!");
        resList.add(new ArrayList<String>());
        resList.add(new ArrayList<String>());
        resList.add(new ArrayList<String>());
        resList.get(0).add("以下為留言");
        resList.get(1).add("以下為留言");
        resList.get(2).add("以下為留言");
        BackArtList();

        spall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selsport.setText("全部");
                BackArtList();
                onBackPressed();
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
                AddArt addart=AddArt.newInstance(nowuser,"param2");
                FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.haba,
                        addart,
                        addart.getTag()
                ).commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void BackArtList(){
        Allsport all=Allsport.newInstance(artTitle,"param2");
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                all,
                all.getTag()
        ).commit();
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
        BackArtList();
    }
    public void addartDATA(String theTitle, String theClass,String  theCon){
        int addid=artID.size();
        artID.add("00"+(addid+1));
        autID.add(nowuser);
        artTitle.add(theTitle);
        artClass.add(theClass);
        artCon.add(theCon);
        resList.add(new ArrayList<String>());
        addRes(addid,"以下為留言");
        Toast.makeText(this,
                "使用者"+autID.get(addid)+"新增"+artID.get(addid)+"號"+artTitle.get(addid),
                Toast.LENGTH_SHORT).
                show();
        toArtcon(addid);
    }

    public void toArtcon(int TargetID){
        theArt theart=theArt.newInstance(artTitle.get(TargetID),autID.get(TargetID),resList.get(TargetID),TargetID,artCon.get(TargetID));
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.haba,theart,null)
                .commit();
    }
    public void addRes(int TargetID,String resCon){
        resList.get(TargetID).add(resCon);
    }
}
