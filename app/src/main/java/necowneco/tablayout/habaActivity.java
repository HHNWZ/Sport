package necowneco.tablayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;

import java.util.ArrayList;


public class habaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Allsport.OnFragmentInteractionListener,
        AddArt.OnFragmentInteractionListener,theArt.OnFragmentInteractionListener,
        SearchArtList.OnFragmentInteractionListener{

    final String[] SportList = {"所有運動","有氧運動","走路","跑步","伏地挺身","仰臥起坐"};//只是對照表
    final ArrayList<String> artID=new ArrayList<String>();//貼文ID列表
    final ArrayList<String> artTitle=new ArrayList<String>();//貼文標題列表
    final ArrayList<String> autID=new ArrayList<String>();//貼文作者ID列表
    final ArrayList<String> artClass=new ArrayList<String>();//貼文類別列表
    final ArrayList<String> artCon=new ArrayList<String>();//貼文內容列表
    final ArrayList<ArrayList> resList=new ArrayList<>();//貼文留言ID [ 該貼文列表 ]，resList.get(ID).size=取得貼文數
    final ArrayList<Integer> artgood=new ArrayList<Integer>();//貼文讚數列表
    final ArrayList<Integer> shsolID=new ArrayList<Integer>();//搜尋&篩選ID列表(位置)，searchsol=shsol
    final ArrayList<String> shsolTitle=new ArrayList<String>();//搜尋&篩選標題列表
    final ArrayList<String> shsolAut=new ArrayList<String>();
    final ArrayList<String> shsolCon=new ArrayList<String>();
    final ArrayList<Integer> shsolGd=new ArrayList<Integer>();
    final ArrayList<Integer> shsolRn=new ArrayList<Integer>();
    final String nowuser="369";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haba_neco);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Button spall = (Button)findViewById(R.id.sp_all);
        final Button sprun = (Button)findViewById(R.id.sp_run);
        final Button spwalk = (Button)findViewById(R.id.sp_walk);
        final Button spair = (Button)findViewById(R.id.sp_air);
        final Button spsit = (Button)findViewById(R.id.sp_sit);
        final Button sppush = (Button)findViewById(R.id.sp_push);
        final EditText SearchValue=(EditText) findViewById(R.id.search_Value);
        final Button SearchBtn=(Button)findViewById(R.id.search_btn);
        artID.add("001");
        artID.add("002");
        artID.add("003");
        autID.add("123");
        autID.add("456");
        autID.add("789");
        artTitle.add("遊俠遠程機具");
        artTitle.add("遊俠入門武器");
        artTitle.add("超遠程攻擊武器");
        artClass.add(SportList[3]);
        artClass.add(SportList[5]);
        artClass.add(SportList[1]);
        artCon.add("衝鋒槍射速高攻擊力低跑速快");
        artCon.add("弓箭可蓄力同時編製術式強力擊發");
        artCon.add("重型狙擊槍一發秒殺破壞地形強無敵");
        artgood.add(10);
        artgood.add(20);
        artgood.add(30);
        resList.add(new ArrayList<String>());
        resList.add(new ArrayList<String>());
        resList.add(new ArrayList<String>());
        resList.get(0).add("以下為留言");
        resList.get(1).add("以下為留言");
        resList.get(2).add("以下為留言");
        BackArtList();
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelAndSearch(SearchValue.getText().toString(),true);
                SearchValue.setText("");
                onBackPressed();

            }
        });
        spall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackArtList();
                onBackPressed();
            }
        });
        sprun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelAndSearch(SportList[1],false);
                onBackPressed();
            }
        });
        spwalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelAndSearch(SportList[2],false);
                onBackPressed();
            }
        });
        spair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelAndSearch(SportList[3],false);
                onBackPressed();
            }
        });
        spsit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelAndSearch(SportList[4],false);
                onBackPressed();
            }
        });
        sppush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelAndSearch(SportList[5],false);
                onBackPressed();
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
    public void testfunction(){
        Toast.makeText(this, "嘎啦", Toast.LENGTH_SHORT).show();
    }
    private void SelAndSearch(String SearchValue, boolean FunctionType) {//Type=true:搜尋,false:篩選
        shsolID.clear();
        shsolTitle.clear();
        shsolAut.clear();
        shsolCon.clear();
        shsolGd.clear();
        shsolRn.clear();
        if(FunctionType) {
            for (int i = 0; i < artID.size(); i++) {
                if (artTitle.get(i).contains(SearchValue)||artCon.get(i).contains(SearchValue)) {
                    shsolID.add(i);
                    shsolTitle.add(artTitle.get(i));
                    shsolAut.add(autID.get(i));
                    shsolCon.add(artCon.get(i));
                    shsolGd.add(artgood.get(i));
                    shsolRn.add(resList.get(i).size());
                }
            }
            Toast.makeText(this, "搜尋："+SearchValue, Toast.LENGTH_SHORT).show();
        }else{
            for (int i = 0; i < artID.size(); i++) {
                if (artClass.get(i)==SearchValue) {
                    shsolID.add(i);
                    shsolTitle.add(artTitle.get(i));
                    shsolAut.add(autID.get(i));
                    shsolCon.add(artCon.get(i));
                    shsolGd.add(artgood.get(i));
                    shsolRn.add(resList.get(i).size());
                }
            }
            Toast.makeText(this, "篩選："+SearchValue, Toast.LENGTH_SHORT).show();
        }
        ToSearchList();
    }
    public ArrayList<Integer> artresCount(){
        ArrayList<Integer> mycount=new ArrayList<Integer>();
        for(int i=0;i<artID.size();i++){
            mycount.add(resList.get(i).size());
        }
        return mycount;
    }
    public void BackArtList(){
        Allsport all=Allsport.newInstance(artID,artTitle,autID,artCon,artgood,artresCount());
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                all,
                all.getTag()
        ).commit();
    }
    public void ToSearchList(){
        SearchArtList searchartlist=SearchArtList.newInstance(shsolID,shsolTitle,shsolAut,shsolCon,shsolGd,shsolRn);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                searchartlist,
                searchartlist.getTag()
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.haba, menu);
        MenuItem menuSearchItem = menu.findItem(R.id.my_search);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuSearchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SelAndSearch(query,true);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
        artgood.add(0);
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
