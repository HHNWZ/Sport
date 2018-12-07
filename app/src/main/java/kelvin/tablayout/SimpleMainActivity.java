package kelvin.tablayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SimpleMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener {
    private static DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private ActionBarDrawerToggle toggle;
    public static Menu menu;
    public static MenuItem menu_email_login,menu_email_register,menu_chat_room,menu_setting_account,menu_Logout,simple_main_activity,complex_main_activity;
    public  NavigationView navigationView;
    public  View hView;
    private DrawerLayout drawer;
    private TextView username;
    private ImageView userImage;
    private PieChart mChart;
    private Legend l;
    private ArrayList<PieEntry> pieEntryList;
    private ArrayList<Integer> colors;
    private PieEntry running,walking,yoga,squats,crunches;
    private PieDataSet pieDataSet;
    private PieData pieData;
    public  TextView textView6;
    public  TextView textView7;
    public  TextView textView8;
    public  TextView textView10;
    public  TextView textView9;
    private static String crunches_week_record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("不再邊緣運動");


         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        hView=navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
        username=(TextView)hView.findViewById(R.id.text_user_name);
        userImage=(ImageView)hView.findViewById(R.id.user_image);

        menu = navigationView.getMenu();
        menu_email_login = menu.findItem(R.id.email_login);
        //blog=menu.findItem(R.id.blog);
        menu_email_register = menu.findItem(R.id.email_register);
        menu_chat_room = menu.findItem(R.id.chat_room);
        menu_setting_account = menu.findItem(R.id.setting_account);
        menu_Logout = menu.findItem(R.id.Logout);
        simple_main_activity=menu.findItem(R.id.simple_main_activity);
        complex_main_activity=menu.findItem(R.id.complex_main_activity);
        mChart=findViewById(R.id.chart1);
        textView6=(TextView)findViewById(R.id.textView6);
        textView7=(TextView)findViewById(R.id.textView7);
        textView8=(TextView)findViewById(R.id.textView8);
        textView10=(TextView)findViewById(R.id.textView10);
        textView9=(TextView)findViewById(R.id.textView9);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            OneSignal.sendTag("Uid",mAuth.getCurrentUser().getUid());
            menu_email_login.setVisible(false);
            menu_email_register.setVisible(false);
            menu_chat_room.setVisible(true);
            menu_setting_account.setVisible(true);
            menu_Logout.setVisible(true);
            simple_main_activity.setVisible(false);
            complex_main_activity.setVisible(true);
            mChart.setVisibility(View.VISIBLE);
            textView6.setVisibility(View.VISIBLE);
            textView7.setVisibility(View.VISIBLE);
            textView8.setVisibility(View.VISIBLE);
            textView10.setVisibility(View.VISIBLE);
            textView9.setVisibility(View.VISIBLE);
            // 设置 pieChart 图表基本属性
            mChart.setUsePercentValues(true);            //使用百分比显示
            mChart.getDescription().setEnabled(false);    //设置pieChart图表的描述
            mChart.setBackgroundColor(android.graphics.Color.rgb(50,61,77));      //设置pieChart图表背景色
            mChart.setDragDecelerationFrictionCoef(0.95f);//设置pieChart图表转动阻力摩擦系数[0,1]
            mChart.setRotationAngle(0);                   //设置pieChart图表起始角度
            mChart.setRotationEnabled(true);              //设置pieChart图表是否可以手动旋转
            mChart.setHighlightPerTapEnabled(true);       //设置piecahrt图表点击Item高亮是否可用
            mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);// 设置pieChart图表展示动画效果

            // 设置 pieChart 图表Item文本属性
            mChart.setDrawEntryLabels(true);              //设置pieChart是否只显示饼图上百分比不显示文字（true：下面属性才有效果）
            mChart.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
            //mChart.setEntryLabelTypeface(mItalic);
            mChart.setEntryLabelTextSize(10f);            //设置pieChart图表文本字体大小

            mChart.setDrawHoleEnabled(true);              //是否显示PieChart内部圆环(true:下面属性才有意义)
            mChart.setHoleRadius(31f);                    //设置PieChart内部圆的半径(这里设置28.0f)
            mChart.setTransparentCircleRadius(31f);       //设置PieChart内部透明圆的半径(这里设置31.0f)
            mChart.setTransparentCircleColor(Color.BLACK);//设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
            mChart.setTransparentCircleAlpha(0);         //设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明
            mChart.setHoleColor(Color.rgb(50,61,77));             //设置PieChart内部圆的颜色
            mChart.setDrawCenterText(true);               //是否绘制PieChart内部中心文本（true：下面属性才有意义）

            mChart.setCenterText("每週\n總消耗\n卡路里");                 //设置PieChart内部圆文字的内容
            mChart.setCenterTextSize(10f);                //设置PieChart内部圆文字的大小
            mChart.setCenterTextColor(Color.WHITE);         //设置PieChart内部圆文字的颜色

            l = mChart.getLegend();
            l.setEnabled(false);                    //是否启用图列（true：下面属性才有意义）

            mChart.setOnChartValueSelectedListener(this);
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            mUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()){
                        String user_name = dataSnapshot.child("name").getValue().toString();
                        String user_image = dataSnapshot.child("thumb_image").getValue().toString();
                        crunches_week_record=dataSnapshot.child("exercise_count").child("crunches").child("week_record").getValue().toString();
                        String running_week_record=dataSnapshot.child("exercise_count").child("running").child("week_record").getValue().toString();
                        String squats_week_record=dataSnapshot.child("exercise_count").child("squats").child("week_record").getValue().toString();
                        String walking_week_record=dataSnapshot.child("exercise_count").child("walking").child("week_record").getValue().toString();
                        String yoga_week_record=dataSnapshot.child("exercise_count").child("yoga").child("week_record").getValue().toString();
                        String crunches_week_calorie=dataSnapshot.child("exercise_count").child("crunches").child("week_calorie").getValue().toString();
                        String running_week_calorie=dataSnapshot.child("exercise_count").child("running").child("week_calorie").getValue().toString();
                        String squats_week_calorie=dataSnapshot.child("exercise_count").child("squats").child("week_calorie").getValue().toString();
                        String walking_week_calorie=dataSnapshot.child("exercise_count").child("walking").child("week_calorie").getValue().toString();
                        String yoga_week_calorie=dataSnapshot.child("exercise_count").child("yoga").child("week_calorie").getValue().toString();
                        String DateCheck=dataSnapshot.child("DateCheck").getValue().toString();
                        String Week= kelvin.tablayout.Week.getWeek(System.currentTimeMillis());
                        String nowDate=Time.getToDate(System.currentTimeMillis());
                        //float crunches_week_record_float=Float.parseFloat(crunches_week_record);
                        float running_week_record_float=Float.parseFloat(running_week_record);
                        //float squats_week_record_float=Float.parseFloat(squats_week_record);
                        float walking_week_record_float=Float.parseFloat(walking_week_record);
                        long yoga_week_record_long=Long.parseLong(yoga_week_record);
                        float crunches_week_calorie_float=Float.parseFloat(crunches_week_calorie);
                        float running_week_calorie_float=Float.parseFloat(running_week_calorie);
                        float squats_week_calorie_float=Float.parseFloat(squats_week_calorie);
                        float walking_week_calorie_float=Float.parseFloat(walking_week_calorie);
                        float yoga_week_calorie_float=Float.parseFloat(yoga_week_calorie);

                        DecimalFormat df = new DecimalFormat("#");
                        if(Week.equals("一")){
                            if(DateCheck.equals(nowDate)){

                                pieEntryList = new ArrayList<PieEntry>();
                                colors = new ArrayList<Integer>();
                                colors.add(Color.parseColor("#38b048"));
                                colors.add(Color.parseColor("#189428"));
                                colors.add(Color.parseColor("#349bb3"));
                                colors.add(Color.parseColor("#2671ab"));
                                colors.add(Color.parseColor("#2c618a"));

                                //饼图实体 PieEntry
                                if(running_week_calorie_float!=0){
                                    running = new PieEntry(running_week_calorie_float, "跑步:"+df.format(running_week_calorie_float)+"大卡");
                                }else {
                                    running = new PieEntry(running_week_calorie_float, "");
                                }
                                if(walking_week_calorie_float!=0){
                                    walking = new PieEntry(walking_week_calorie_float, "步行:"+df.format(walking_week_calorie_float)+"大卡");
                                }else {
                                    walking = new PieEntry(walking_week_calorie_float, "");
                                }
                                if(yoga_week_calorie_float!=0){
                                    yoga = new PieEntry(yoga_week_calorie_float, "瑜伽:"+df.format(yoga_week_calorie_float)+"大卡");
                                }else {
                                    yoga = new PieEntry(yoga_week_calorie_float, "");
                                }
                                if(squats_week_calorie_float!=0){
                                    squats = new PieEntry(squats_week_calorie_float, "深蹲:"+df.format(squats_week_calorie_float)+"大卡");
                                }else {
                                    squats = new PieEntry(squats_week_calorie_float, "");
                                }
                                if(crunches_week_calorie_float!=0){
                                    crunches = new PieEntry(crunches_week_calorie_float, "仰臥起坐:"+df.format(crunches_week_calorie_float)+"大卡");
                                }else {
                                    crunches = new PieEntry(crunches_week_calorie_float, "");
                                }
                                pieEntryList.add(running);
                                pieEntryList.add(walking);
                                pieEntryList.add(yoga);
                                pieEntryList.add(squats);
                                pieEntryList.add(crunches);
                                //饼状图数据集 PieDataSet
                                pieDataSet = new PieDataSet(pieEntryList, "每週總消耗卡路里");
                                pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
                                pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
                                pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
                                //最终数据 PieData
                                pieData = new PieData(pieDataSet);
                                pieData.setDrawValues(false);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
                                pieData.setValueTextColor(Color.BLUE);  //设置所有DataSet内数据实体（百分比）的文本颜色
                                pieData.setValueTextSize(20f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
                                //pieData.setValueTypeface(mTfLight);     //设置所有DataSet内数据实体（百分比）的文本字体样式
                                pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
                                mChart.setData(pieData);
                                mChart.highlightValues(null);
                                mChart.invalidate();                    //将图表重绘以显示设置的属性和数据


                                textView6.setText(""+running_week_record_float+"公里");
                                textView7.setText(""+walking_week_record_float+"公里");
                                textView8.setText(""+Time.changeYogaTime(yoga_week_record_long));
                                textView10.setText(""+squats_week_record+"次");
                                textView9.setText(""+crunches_week_record+"次");
                            }else{


                                pieEntryList = new ArrayList<PieEntry>();
                                colors = new ArrayList<Integer>();
                                colors.add(Color.parseColor("#38b048"));
                                colors.add(Color.parseColor("#189428"));
                                colors.add(Color.parseColor("#349bb3"));
                                colors.add(Color.parseColor("#2671ab"));
                                colors.add(Color.parseColor("#2c618a"));

                                //饼图实体 PieEntry
                                running = new PieEntry(0, "跑步:0大卡");
                                walking = new PieEntry(0, "步行:0大卡");
                                yoga = new PieEntry(0, "瑜伽:0大卡");
                                squats = new PieEntry(0, "深蹲:0大卡");
                                crunches = new PieEntry(0, "仰臥起坐:0大卡");
                                pieEntryList.add(running);
                                pieEntryList.add(walking);
                                pieEntryList.add(yoga);
                                pieEntryList.add(squats);
                                pieEntryList.add(crunches);
                                //饼状图数据集 PieDataSet
                                pieDataSet = new PieDataSet(pieEntryList, "每週總消耗卡路里");
                                pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
                                pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
                                pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
                                //最终数据 PieData
                                pieData = new PieData(pieDataSet);
                                pieData.setDrawValues(false);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
                                pieData.setValueTextColor(Color.BLUE);  //设置所有DataSet内数据实体（百分比）的文本颜色
                                pieData.setValueTextSize(20f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
                                //pieData.setValueTypeface(mTfLight);     //设置所有DataSet内数据实体（百分比）的文本字体样式
                                pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
                                mChart.setData(pieData);
                                mChart.highlightValues(null);
                                mChart.invalidate();                    //将图表重绘以显示设置的属性和数据



                                //pieChart 选择监听


                                textView6.setText("0公里");
                                textView7.setText("0公里");
                                textView8.setText("0秒");
                                textView10.setText("0次");
                                textView9.setText("0次");
                                mUserRef.child("DateCheck").setValue(nowDate);
                                mUserRef.child("exercise_count").child("crunches").child("week_record").setValue(0);
                                mUserRef.child("exercise_count").child("running").child("week_record").setValue(0);
                                mUserRef.child("exercise_count").child("squats").child("week_record").setValue(0);
                                mUserRef.child("exercise_count").child("walking").child("week_record").setValue(0);
                                mUserRef.child("exercise_count").child("yoga").child("week_record").setValue(0);
                                mUserRef.child("exercise_count").child("crunches").child("week_calorie").setValue(0);
                                mUserRef.child("exercise_count").child("running").child("week_calorie").setValue(0);
                                mUserRef.child("exercise_count").child("squats").child("week_calorie").setValue(0);
                                mUserRef.child("exercise_count").child("walking").child("week_calorie").setValue(0);
                                mUserRef.child("exercise_count").child("yoga").child("week_calorie").setValue(0);

                            }

                        }else {
                            pieEntryList = new ArrayList<PieEntry>();
                            colors = new ArrayList<Integer>();
                            colors.add(Color.parseColor("#38b048"));
                            colors.add(Color.parseColor("#189428"));
                            colors.add(Color.parseColor("#349bb3"));
                            colors.add(Color.parseColor("#2671ab"));
                            colors.add(Color.parseColor("#2c618a"));

                            //饼图实体 PieEntry
                            if(running_week_calorie_float!=0){
                                running = new PieEntry(running_week_calorie_float, "跑步:"+df.format(running_week_calorie_float)+"大卡");
                            }else {
                                running = new PieEntry(running_week_calorie_float, "");
                            }
                            if(walking_week_calorie_float!=0){
                                walking = new PieEntry(walking_week_calorie_float, "步行:"+df.format(walking_week_calorie_float)+"大卡");
                            }else {
                                walking = new PieEntry(walking_week_calorie_float, "");
                            }
                            if(yoga_week_calorie_float!=0){
                                yoga = new PieEntry(yoga_week_calorie_float, "瑜伽:"+df.format(yoga_week_calorie_float)+"大卡");
                            }else {
                                yoga = new PieEntry(yoga_week_calorie_float, "");
                            }
                            if(squats_week_calorie_float!=0){
                                squats = new PieEntry(squats_week_calorie_float, "深蹲:"+df.format(squats_week_calorie_float)+"大卡");
                            }else {
                                squats = new PieEntry(squats_week_calorie_float, "");
                            }
                            if(crunches_week_calorie_float!=0){
                                crunches = new PieEntry(crunches_week_calorie_float, "仰臥起坐:"+df.format(crunches_week_calorie_float)+"大卡");
                            }else {
                                crunches = new PieEntry(crunches_week_calorie_float, "");
                            }




                            pieEntryList.add(running);
                            pieEntryList.add(walking);
                            pieEntryList.add(yoga);
                            pieEntryList.add(squats);
                            pieEntryList.add(crunches);
                            //饼状图数据集 PieDataSet
                            pieDataSet = new PieDataSet(pieEntryList, "每週總消耗卡路里");
                            pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
                            pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
                            pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
                            //最终数据 PieData
                            pieData = new PieData(pieDataSet);
                            pieData.setDrawValues(false);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
                            pieData.setValueTextColor(Color.BLUE);  //设置所有DataSet内数据实体（百分比）的文本颜色
                            pieData.setValueTextSize(20f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
                            //pieData.setValueTypeface(mTfLight);     //设置所有DataSet内数据实体（百分比）的文本字体样式
                            pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
                            mChart.setData(pieData);
                            mChart.highlightValues(null);
                            mChart.invalidate();                    //将图表重绘以显示设置的属性和数据
                            textView6.setText(""+running_week_record_float+"公里");
                            textView7.setText(""+walking_week_record_float+"公里");
                            textView8.setText(""+Time.changeYogaTime(yoga_week_record_long));
                            textView10.setText(""+squats_week_record+"次");
                            textView9.setText(""+crunches_week_record+"次");

                        }
                        username.setText(user_name);
                        Picasso.get().load(user_image).placeholder(R.drawable.default_avatar).into(userImage);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }else {
            menu_email_login.setVisible(true);
            menu_email_register.setVisible(true);
            menu_chat_room.setVisible(false);
            menu_setting_account.setVisible(false);
            menu_Logout.setVisible(false);
            simple_main_activity.setVisible(false);
            complex_main_activity.setVisible(false);
            mChart.setVisibility(View.INVISIBLE);
            mChart.setVisibility(View.INVISIBLE);
            textView6.setVisibility(View.INVISIBLE);
            textView7.setVisibility(View.INVISIBLE);
            textView8.setVisibility(View.INVISIBLE);
            textView10.setVisibility(View.INVISIBLE);
            textView9.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){//當按下左上三條線或顯示工具列
            invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.email_login) {
            /*Intent i = new Intent(SimpleMainActivity.this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);*/

        }

        else if (id == R.id.email_register)
        {

            /*Intent i = new Intent(SimpleMainActivity.this,RegisterActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);*/
        }
        else if(id==R.id.chat_room){
            /*Intent i = new Intent(SimpleMainActivity.this,MainActivityFireBase.class);
            startActivity(i);*/
        }
        else if(id==R.id.setting_account){
            /*Intent i = new Intent(SimpleMainActivity.this,SettingsActivity.class);
            startActivity(i);*/
        }
        else if(id==R.id.complex_main_activity){
            Intent i = new Intent(SimpleMainActivity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        /*else if(id==R.id.Logout){

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
            OneSignal.deleteTag("Uid");


            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(SimpleMainActivity.this,MainActivity.class);
            finish();
            startActivity(i);

        }*/


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
