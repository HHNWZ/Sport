package necowneco.tablayout;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class habaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Allsport.OnFragmentInteractionListener,
        AddArt.OnFragmentInteractionListener,theArt.OnFragmentInteractionListener,
        SearchArtList.OnFragmentInteractionListener,theArtRes.OnFragmentInteractionListener{

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
        artID.add("001");
        artID.add("002");
        artID.add("003");
        autID.add("123");
        autID.add("456");
        autID.add("789");
        artTitle.add("總有一天要追上太陽");
        artTitle.add("來自地獄的腹筋壓榨術");
        artTitle.add("使全身充滿氧氣");
        artClass.add(SportList[3]);
        artClass.add(SportList[5]);
        artClass.add(SportList[1]);
        artCon.add("愉快，太愉快了。此時此刻，吾輩的雙足成為了地球上最快的傳說。吾輩名曰----夸父。");
        artCon.add("還差一點...不能放棄!!夢想中的六十四塊腹肌就在眼前，要是放棄的話，就只能回到六十三塊腹肌的生活了!!!!!");
        artCon.add("用人體本身具備的韻律感、平衡感等堪稱生物學奧秘的要素，達成與自然界的共鳴。...是的，我是一棵樹。");
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
                AddArt addart=AddArt.newInstance(nowuser, "新增",artID.size(),null,null,null);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //當使用者按下確定後
        if (resultCode == RESULT_OK) {
            //取得圖檔的路徑位置
            Uri uri = data.getData();
            //寫log
            Log.e("uri", uri.toString());
            //抽象資料的接口
            ContentResolver cr = this.getContentResolver();
            try {
                //由抽象資料接口轉換圖檔路徑為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //取得圖片控制項ImageView
                ImageView imageView = (ImageView) findViewById(R.id.myIMG);
                // 將Bitmap設定到ImageView
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void reAddArtDATA(int theartID,String theTitle, String theClass,String  theCon){
        AddArt addart=AddArt.newInstance(nowuser,"編輯",theartID,theTitle,theClass,theCon);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                addart,
                addart.getTag()
        ).commit();
    }
    public void reSetArtDATA(int theartID,String theTitle, String theClass,String  theCon){
        artTitle.set(theartID,theTitle);
        artClass.set(theartID,theClass);
        artCon.set(theartID,theCon);
        toArtcon(theartID);
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
    public void deletartDATA(int Target){
        artID.remove(Target);
        autID.remove(Target);
        artTitle.remove(Target);
        artClass.remove(Target);
        artCon.remove(Target);
        resList.remove(Target);
        artgood.remove(Target);
        Toast.makeText(this,
                "已刪除貼文",
                Toast.LENGTH_SHORT).
                show();
        BackArtList();
    }
    public void toArtcon(int TargetID){
        theArt theart=theArt.newInstance(
                TargetID,
                artTitle.get(TargetID),
                autID.get(TargetID),
                artClass.get(TargetID),
                artCon.get(TargetID),
                artgood.get(TargetID),
                resList.get(TargetID),
                nowuser
        );
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.haba,theart,null)
                .commit();
    }
    public void addRes(int TargetID,String resCon){
        resList.get(TargetID).add(resCon);
    }
    public void IINe(int TargetID){//讚
        artgood.set(TargetID,artgood.get(TargetID)+1);
    }
    public void toResList(int TargetID){
        theArtRes theartres=theArtRes.newInstance(resList.get(TargetID),artTitle.get(TargetID),TargetID);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                theartres,
                theartres.getTag()
        ).commit();
    }

}
