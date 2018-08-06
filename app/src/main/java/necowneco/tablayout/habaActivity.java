package necowneco.tablayout;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a888888888.sport.BackHandlerHelper;
import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    final ArrayList<ArrayList> resName=new ArrayList<ArrayList>();//貼文留言記名列表
    final ArrayList<ArrayList> resList=new ArrayList<>();//貼文留言ID [ 該貼文列表 ]，resList.get(ID).size=取得留言數
    final ArrayList<ArrayList> artgood=new ArrayList<ArrayList>();//貼文讚數列表
    final ArrayList<String> artRETitle=new ArrayList<String>();//貼文回覆之標題列表
    final ArrayList<Integer> shsolID=new ArrayList<Integer>();//搜尋&篩選ID列表(位置)，searchsol=shsol
    final ArrayList<String> shsolTitle=new ArrayList<String>();//搜尋&篩選標題列表
    final ArrayList<String> shsolAut=new ArrayList<String>();
    final ArrayList<String> shsolCon=new ArrayList<String>();
    final ArrayList<Integer> shsolGd=new ArrayList<Integer>();
    final ArrayList<Integer> shsolRn=new ArrayList<Integer>();
    public static int fragcount = 0,BacktheArt=0,BacktoID = 0;
    final String nowuser="369";//測試用之預設使用者
    String isReArt=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haba_neco);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //填入測試用資料
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
        artRETitle.add(null);
        artRETitle.add(null);
        artRETitle.add(null);
        artClass.add(SportList[3]);
        artClass.add(SportList[5]);
        artClass.add(SportList[1]);
        artCon.add("愉快，太愉快了。此時此刻，吾輩的雙足成為了地球上最快的傳說。吾輩名曰----夸父。");
        artCon.add("還差一點...不能放棄!!夢想中的六十四塊腹肌就在眼前，要是放棄的話，就只能回到六十三塊腹肌的生活了!!!!!");
        artCon.add("用人體本身具備的韻律感、平衡感等堪稱生物學奧秘的要素，達成與自然界的共鳴。...是的，我是一棵樹。");
        artgood.add(new ArrayList<String>());
        artgood.add(new ArrayList<String>());
        artgood.add(new ArrayList<String>());
        artgood.get(0).add("456");artgood.get(0).add("789");
        artgood.get(1).add("123");artgood.get(1).add("789");
        artgood.get(2).add("123");artgood.get(2).add("456");artgood.get(2).add("369");
        resName.add(new ArrayList<String>());
        resName.add(new ArrayList<String>());
        resName.add(new ArrayList<String>());
        resList.add(new ArrayList<String>());
        resList.add(new ArrayList<String>());
        resList.add(new ArrayList<String>());//連接資料庫後，請按照上述方法另建"取得資料"之函式
        BackArtList();//展開貼文列表
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
            public void onClick(View view) {//<跳頁>新增貼文
                fragcount++;
                if(isReArt==null){
                    AddArt addart=AddArt.newInstance(nowuser, "新增",artID.size(),null,null,null);//賦予addArt頁面"新增"狀態
                    FragmentManager manager=getSupportFragmentManager();
                    manager.beginTransaction().replace(
                            R.id.haba,
                            addart,
                            addart.getTag()
                    ).commit();
                }else{
                    reTheArt(isReArt);
                }
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    public void BackArtList(){//<跳頁>回到貼文列表
        Allsport all=Allsport.newInstance(artID,artTitle,autID,artCon,artgoodCount(),artresCount());
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                all,
                all.getTag()
        ).commit();
    }


    public void ToSearchList(){//<跳頁>進入搜尋與篩選頁面
        SearchArtList searchartlist=SearchArtList.newInstance(shsolID,shsolTitle,shsolAut,shsolCon,shsolGd,shsolRn);
        fragcount++;
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                searchartlist,
                searchartlist.getTag()
        ).commit();
    }
    public void toArtcon(int TargetID){//<跳頁>查看貼文內容
        fragcount++;
        theArt theart=theArt.newInstance(
                TargetID,
                artTitle.get(TargetID),
                autID.get(TargetID),
                artClass.get(TargetID),
                artCon.get(TargetID),
                artgood.get(TargetID).size(),
                resList.get(TargetID),
                nowuser,
                artRETitle.get(TargetID)
        );
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.haba,theart,null)
                .commit();
    }
    public void toResList(int TargetID){//<跳頁>查看該貼文之全部留言
        BacktheArt = 1;
        BacktoID = TargetID;
        theArtRes theartres=theArtRes.newInstance(resName.get(TargetID),resList.get(TargetID),artTitle.get(TargetID),TargetID);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                theartres,
                theartres.getTag()
        ).commit();
    }

    public void reAddArtDATA(int theartID,String theTitle, String theClass,String  theCon){//<跳頁>編輯已發佈的貼文內容
        AddArt addart=AddArt.newInstance(nowuser,"編輯",theartID,theTitle,theClass,theCon);//進入addArt頁面時賦予"編輯"狀態
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                addart,
                addart.getTag()
        ).commit();
    }
    public void reTheArt(String theTitle) {//<跳頁>新增一則新貼文來回覆某一貼文
        AddArt addart=AddArt.newInstance(nowuser,"回覆",artID.size(),theTitle,null,theTitle);//進入addArt頁面時賦予"回覆"狀態
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.haba,
                addart,
                addart.getTag()
        ).commit();

    }
    private ArrayList<Integer> artgoodCount() {//<資料處理>取得貼文列表中每則貼文之讚數，展開貼文列表時使用
        ArrayList<Integer> mycount=new ArrayList<Integer>();
        for(int i=0;i<artID.size();i++){
            mycount.add(artgood.get(i).size());
        }
        return mycount;
    }
    public ArrayList<Integer> artresCount(){//<資料處理>取得貼文列表中每則貼文之留言數，展開貼文列表時使用
        ArrayList<Integer> mycount=new ArrayList<Integer>();
        for(int i=0;i<artID.size();i++){
            mycount.add(resList.get(i).size());
        }
        return mycount;
    }
    private void SelAndSearch(String SearchValue, boolean FunctionType) {//<資料處理>預先準備好符合搜尋或篩選條件的貼文項目列表
        //Type=true:搜尋,false:篩選
        shsolID.clear();
        shsolTitle.clear();
        shsolAut.clear();
        shsolCon.clear();
        shsolGd.clear();
        shsolRn.clear();
        if(FunctionType) {//使用搜尋元件
            for (int i = 0; i < artID.size(); i++) {
                if (artTitle.get(i).contains(SearchValue)||artCon.get(i).contains(SearchValue)) {
                    shsolID.add(i);
                    shsolTitle.add(artTitle.get(i));
                    shsolAut.add(autID.get(i));
                    shsolCon.add(artCon.get(i));
                    shsolGd.add(artgood.get(i).size());
                    shsolRn.add(resList.get(i).size());
                }
            }
            Toast.makeText(this, "搜尋："+SearchValue, Toast.LENGTH_SHORT).show();
        }else{//使用側拉式選單
            for (int i = 0; i < artID.size(); i++) {
                if (artClass.get(i)==SearchValue) {
                    shsolID.add(i);
                    shsolTitle.add(artTitle.get(i));
                    shsolAut.add(autID.get(i));
                    shsolCon.add(artCon.get(i));
                    shsolGd.add(artgood.get(i).size());
                    shsolRn.add(resList.get(i).size());
                }
            }
            Toast.makeText(this, "篩選："+SearchValue, Toast.LENGTH_SHORT).show();
        }
        ToSearchList();//呼叫進入搜尋與篩選頁面的函式
    }
    public void addartDATA(String theTitle, String theClass,String  theCon,String RETar){//<資料處理>新增貼文
        int addid=artID.size();
        artID.add("00"+(addid+1));
        autID.add(nowuser);
        artTitle.add(theTitle);
        artClass.add(theClass);
        artCon.add(theCon);
        artgood.add(new ArrayList<String>());
        resList.add(new ArrayList<String>());
        artRETitle.add(RETar);
        //以上為：將新增之貼文存於資料列
        toArtcon(addid);//直接檢視發布之貼文
    }
    public void IINe(int TargetID,boolean gooded){//<資料處理>點讚&收回
        if(gooded) {
            artgood.get(TargetID).remove(nowuser);
        }else{
            artgood.get(TargetID).add(nowuser);
        }
    }
    public void addRes(int TargetID,String resCon){//<資料處理>新增留言
        resName.get(TargetID).add(nowuser);
        resList.get(TargetID).add(resCon);
    }
    public void reSetArtDATA(int theartID,String theTitle, String theClass,String  theCon){//<資料處理>編輯後更新貼文內容
        artTitle.set(theartID,theTitle);
        artClass.set(theartID,theClass);
        artCon.set(theartID,theCon);
        toArtcon(theartID);//回到該貼文內容
    }
    public void deletartDATA(int Target){//<資料處理>自資料列中刪除一則貼文
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
        BackArtList();//回到貼文列表
    }
    public void fabOut() {//<版面控制>浮動按鈕隱藏
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
    }
    public void fabIn(String isRethis) {//<版面控制>浮動按鈕出現
        isReArt=isRethis;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
    }
    public void downloadTheImg(Bitmap bitmap) {//<資料處理>將本頁(貼文內容)圖片儲存至手機內部儲存空間/DCIM中
        FileOutputStream fOut;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);//取得前往DCIM之路徑
            if (!dir.exists()) {//若無該路徑則自行建立
                dir.mkdir();
            }
            String tmp = dir+"/girl.jpg";//設定儲存圖片之路徑+檔名
            fOut = new FileOutputStream(tmp);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);//儲存圖片
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//<資料處理>上傳圖片至APP(接收端)
        if (resultCode == RESULT_OK) {//當使用者選擇圖片後
            Uri uri = data.getData();//取得圖檔的路徑位置
            Log.e("uri", uri.toString());//寫log
            ContentResolver cr = this.getContentResolver();//抽象資料的接口
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));//由抽象資料接口轉換圖檔路徑為Bitmap
                ImageView imageView = (ImageView) findViewById(R.id.myIMG);//取得圖片控制項ImageView
                imageView.setImageBitmap(bitmap);// 將Bitmap設定到ImageView
                imageView.setVisibility(View.VISIBLE);//將預設"隱藏"的ImageView改為"顯示"
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (BacktheArt == 1) {//回貼文內容
            BacktheArt = 0;
            theArt theart=theArt.newInstance(
                    BacktoID,
                    artTitle.get(BacktoID),
                    autID.get(BacktoID),
                    artClass.get(BacktoID),
                    artCon.get(BacktoID),
                    artgood.get(BacktoID).size(),
                    resList.get(BacktoID),
                    nowuser,
                    artRETitle.get(BacktoID)
            );
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.haba,theart,null)
                    .commit();
        }
        else if (fragcount == 0) {//回主頁面
            Intent intentHome= new Intent(habaActivity.this,MainActivity.class);//kk
            Toast.makeText(habaActivity.this, "按返回鍵會用到這裡", Toast.LENGTH_SHORT).show();
            startActivity(intentHome);
            this.finish();
        }
        else if (!BackHandlerHelper.handleBackPress(this)) { //回貼文列表
            fragcount--;
            /*Toast.makeText(kelvin_tab_layout.this, "按返回鍵會用到這裡3", Toast.LENGTH_SHORT).show();不用刪除，因為fragment的返回鍵會無反應*/
            BackArtList();
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



}
