package kelvin.tablayout;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryCalendar extends AppCompatActivity implements OnDateSelectedListener, OnDateLongClickListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy年MMMd日 EEE");

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @BindView(R.id.textView)
    TextView textView;


    private Toolbar diary_calendar_toolbar;
    private ActionBar diary_calendar_actionbar;

    private static DatabaseReference breakfast_database,my_database;
    private static FirebaseAuth mAuth;
    private  TextView breakfast_data;
    private  TextView breakfast_data1;
    private TextView breakfast_data2;
    private TextView breakfast_data3;
    private TextView breakfast_data4;
    private TextView breakfast_data5;
    private TextView breakfast_data6;
    private TextView breakfast_data7;
    private TextView breakfast_data8;
    private TextView breakfast_data9;
    private TextView breakfast_cal;
    private  TextView lunch_data;
    private  TextView lunch_data1;
    private TextView lunch_data2;
    private TextView lunch_data3;
    private TextView lunch_data4;
    private TextView lunch_data5;
    private TextView lunch_data6;
    private TextView lunch_data7;
    private TextView lunch_data8;
    private TextView lunch_data9;
    private TextView lunch_cal;
    private  TextView dinner_data;
    private  TextView dinner_data1;
    private TextView dinner_data2;
    private TextView dinner_data3;
    private TextView dinner_data4;
    private TextView dinner_data5;
    private TextView dinner_data6;
    private TextView dinner_data7;
    private TextView dinner_data8;
    private TextView dinner_data9;
    private TextView dinner_cal;
    private TextView total_cal;
    private static LocalDate min;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_calendar);
        ButterKnife.bind(this);

        widget.setOnDateChangedListener(this);
        widget.setOnDateLongClickListener(this);

        final LocalDate instance = LocalDate.now();
        Log.i("日期1234",""+instance.toString());
        widget.setSelectedDate(instance);

        diary_calendar_toolbar=(Toolbar)findViewById(R.id. diary_calendar_toolbar);
        setSupportActionBar(diary_calendar_toolbar);
        diary_calendar_actionbar=getSupportActionBar();
        diary_calendar_actionbar.setTitle("日記");
        diary_calendar_actionbar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        breakfast_database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Food_note");

        breakfast_data=(TextView)findViewById(R.id.breakfast_data);
        breakfast_data1=(TextView)findViewById(R.id.breakfast_data1);
        breakfast_data2=(TextView)findViewById(R.id.breakfast_data2);
        breakfast_data3=(TextView)findViewById(R.id.breakfast_data3);
        breakfast_data4=(TextView)findViewById(R.id.breakfast_data4);
        breakfast_data5=(TextView)findViewById(R.id.breakfast_data5);
        breakfast_data6=(TextView)findViewById(R.id.breakfast_data6);
        breakfast_data7=(TextView)findViewById(R.id.breakfast_data7);
        breakfast_data8=(TextView)findViewById(R.id.breakfast_data8);
        breakfast_data9=(TextView)findViewById(R.id.breakfast_data9);
        breakfast_cal=(TextView)findViewById(R.id.breakfast_cal);
        lunch_data=(TextView)findViewById(R.id.lunch_data);
        lunch_data1=(TextView)findViewById(R.id.lunch_data1);
        lunch_data2=(TextView)findViewById(R.id.lunch_data2);
        lunch_data3=(TextView)findViewById(R.id.lunch_data3);
        lunch_data4=(TextView)findViewById(R.id.lunch_data4);
        lunch_data5=(TextView)findViewById(R.id.lunch_data5);
        lunch_data6=(TextView)findViewById(R.id.lunch_data6);
        lunch_data7=(TextView)findViewById(R.id.lunch_data7);
        lunch_data8=(TextView)findViewById(R.id.lunch_data8);
        lunch_data9=(TextView)findViewById(R.id.lunch_data9);
        lunch_cal=(TextView)findViewById(R.id.lunch_cal);
        dinner_data=(TextView)findViewById(R.id.dinner_data);
        dinner_data1=(TextView)findViewById(R.id.dinner_data1);
        dinner_data2=(TextView)findViewById(R.id.dinner_data2);
        dinner_data3=(TextView)findViewById(R.id.dinner_data3);
        dinner_data4=(TextView)findViewById(R.id.dinner_data4);
        dinner_data5=(TextView)findViewById(R.id.dinner_data5);
        dinner_data6=(TextView)findViewById(R.id.dinner_data6);
        dinner_data7=(TextView)findViewById(R.id.dinner_data7);
        dinner_data8=(TextView)findViewById(R.id.dinner_data8);
        dinner_data9=(TextView)findViewById(R.id.dinner_data9);
        dinner_cal=(TextView)findViewById(R.id.dinner_cal);
        total_cal=(TextView)findViewById(R.id.total_cal);
        Log.i("日期12345",""+Time.formatCalendar(System.currentTimeMillis()));

      //widget.addDecorator(new EventDecorator(Color.RED,doInBackground()));






        //Setup initial text
        textView.setText("點選日期");
    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected){
                textView.setText(selected ? FORMATTER.format(date.getDate()) : "沒有選擇日期");
                my_database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Food_note").child(FORMATTER.format(date.getDate()));
                my_database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            int today_calorie=0;
                            Log.i("今天所攝取的卡路里4",""+today_calorie);
                            if(dataSnapshot.child("breakfast").hasChildren()){
                                breakfast_data.setVisibility(View.VISIBLE);
                                if(dataSnapshot.child("breakfast").hasChild("food_item1")){
                                    String breakfast_food_item1=dataSnapshot.child("breakfast").child("food_item1").getValue().toString();
                                    Log.i("早餐食物1",breakfast_food_item1);
                                    breakfast_data1.setVisibility(View.VISIBLE);
                                    breakfast_data1.setText(breakfast_food_item1);
                                }else {
                                    breakfast_data1.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("breakfast").hasChild("food_item2")){
                                    String breakfast_food_item2=dataSnapshot.child("breakfast").child("food_item2").getValue().toString();
                                    Log.i("早餐食物2",breakfast_food_item2);
                                    breakfast_data2.setVisibility(View.VISIBLE);
                                    breakfast_data2.setText(breakfast_food_item2);
                                }else {
                                    breakfast_data2.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("breakfast").hasChild("food_item3")){
                                    String breakfast_food_item3=dataSnapshot.child("breakfast").child("food_item3").getValue().toString();
                                    Log.i("早餐食物3",breakfast_food_item3);
                                    breakfast_data3.setVisibility(View.VISIBLE);
                                    breakfast_data3.setText(breakfast_food_item3);
                                }else {
                                    breakfast_data3.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("breakfast").hasChild("food_item4")){
                                    String breakfast_food_item4=dataSnapshot.child("breakfast").child("food_item4").getValue().toString();
                                    Log.i("早餐食物4",breakfast_food_item4);
                                    breakfast_data4.setVisibility(View.VISIBLE);
                                    breakfast_data4.setText(breakfast_food_item4);
                                }else {
                                    breakfast_data4.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("breakfast").hasChild("food_item5")){
                                    String breakfast_food_item5=dataSnapshot.child("breakfast").child("food_item5").getValue().toString();
                                    Log.i("早餐食物5",breakfast_food_item5);
                                    breakfast_data5.setVisibility(View.VISIBLE);
                                    breakfast_data5.setText(breakfast_food_item5);
                                }else {
                                    breakfast_data5.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("breakfast").hasChild("food_item6")){
                                    String breakfast_food_item6=dataSnapshot.child("breakfast").child("food_item6").getValue().toString();
                                    Log.i("早餐食物6",breakfast_food_item6);
                                    breakfast_data6.setVisibility(View.VISIBLE);
                                    breakfast_data6.setText(breakfast_food_item6);
                                }else {
                                    breakfast_data6.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("breakfast").hasChild("food_item7")){
                                    String breakfast_food_item7=dataSnapshot.child("breakfast").child("food_item7").getValue().toString();
                                    Log.i("早餐食物7",breakfast_food_item7);
                                    breakfast_data7.setVisibility(View.VISIBLE);
                                    breakfast_data7.setText(breakfast_food_item7);
                                }else {
                                    breakfast_data7.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("breakfast").hasChild("food_item8")){
                                    String breakfast_food_item8=dataSnapshot.child("breakfast").child("food_item8").getValue().toString();
                                    Log.i("早餐食物8",breakfast_food_item8);
                                    breakfast_data8.setVisibility(View.VISIBLE);
                                    breakfast_data8.setText(breakfast_food_item8);
                                }else {
                                    breakfast_data8.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("breakfast").hasChild("food_item9")){
                                    String breakfast_food_item9=dataSnapshot.child("breakfast").child("food_item9").getValue().toString();
                                    Log.i("早餐食物9",breakfast_food_item9);
                                    breakfast_data9.setVisibility(View.VISIBLE);
                                    breakfast_data9.setText(breakfast_food_item9);
                                }else {
                                    breakfast_data9.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("breakfast").hasChild("total_cal")){
                                    String breakfast_total_cal=dataSnapshot.child("breakfast").child("total_cal").getValue().toString();
                                    breakfast_cal.setVisibility(View.VISIBLE);
                                    breakfast_cal.setText("早餐總攝取卡路里:"+breakfast_total_cal+"大卡");
                                    int breakfast_total_cal_int=Integer.parseInt(breakfast_total_cal);
                                    today_calorie=today_calorie+breakfast_total_cal_int;
                                    Log.i("今天所攝取的卡路里1",""+today_calorie);

                                }else{
                                    breakfast_cal.setVisibility(View.GONE);

                                }
                            }else {
                                breakfast_data.setVisibility(View.GONE);
                                breakfast_data1.setVisibility(View.GONE);
                                breakfast_data2.setVisibility(View.GONE);
                                breakfast_data3.setVisibility(View.GONE);
                                breakfast_data4.setVisibility(View.GONE);
                                breakfast_data5.setVisibility(View.GONE);
                                breakfast_data6.setVisibility(View.GONE);
                                breakfast_data7.setVisibility(View.GONE);
                                breakfast_data8.setVisibility(View.GONE);
                                breakfast_data9.setVisibility(View.GONE);
                                breakfast_cal.setVisibility(View.GONE);

                            }
                            if(dataSnapshot.child("lunch").hasChildren()){
                                lunch_data.setVisibility(View.VISIBLE);
                                if(dataSnapshot.child("lunch").hasChild("food_item1")){
                                    String lunch_food_item1=dataSnapshot.child("lunch").child("food_item1").getValue().toString();
                                    Log.i("午餐食物1",lunch_food_item1);
                                    lunch_data1.setVisibility(View.VISIBLE);
                                    lunch_data1.setText(lunch_food_item1);
                                }else {
                                    lunch_data1.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("lunch").hasChild("food_item2")){
                                    String lunch_food_item2=dataSnapshot.child("lunch").child("food_item2").getValue().toString();
                                    Log.i("午餐食物2",lunch_food_item2);
                                    lunch_data2.setVisibility(View.VISIBLE);
                                    lunch_data2.setText(lunch_food_item2);
                                }else {
                                    lunch_data2.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("lunch").hasChild("food_item3")){
                                    String lunch_food_item3=dataSnapshot.child("lunch").child("food_item3").getValue().toString();
                                    Log.i("午餐食物3",lunch_food_item3);
                                    lunch_data3.setVisibility(View.VISIBLE);
                                    lunch_data3.setText(lunch_food_item3);
                                }else {
                                    lunch_data3.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("lunch").hasChild("food_item4")){
                                    String lunch_food_item4=dataSnapshot.child("lunch").child("food_item4").getValue().toString();
                                    Log.i("午餐食物4",lunch_food_item4);
                                    lunch_data4.setVisibility(View.VISIBLE);
                                    lunch_data4.setText(lunch_food_item4);
                                }else {
                                    lunch_data4.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("lunch").hasChild("food_item5")){
                                    String lunch_food_item5=dataSnapshot.child("lunch").child("food_item5").getValue().toString();
                                    Log.i("午餐食物5",lunch_food_item5);
                                    lunch_data5.setVisibility(View.VISIBLE);
                                    lunch_data5.setText(lunch_food_item5);
                                }else {
                                    lunch_data5.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("lunch").hasChild("food_item6")){
                                    String lunch_food_item6=dataSnapshot.child("lunch").child("food_item6").getValue().toString();
                                    Log.i("午餐食物6",lunch_food_item6);
                                    lunch_data6.setVisibility(View.VISIBLE);
                                    lunch_data6.setText(lunch_food_item6);
                                }else {
                                    lunch_data6.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("lunch").hasChild("food_item7")){
                                    String lunch_food_item7=dataSnapshot.child("lunch").child("food_item7").getValue().toString();
                                    Log.i("午餐食物7",lunch_food_item7);
                                    lunch_data7.setVisibility(View.VISIBLE);
                                    lunch_data7.setText(lunch_food_item7);
                                }else {
                                    lunch_data7.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("lunch").hasChild("food_item8")){
                                    String lunch_food_item8=dataSnapshot.child("lunch").child("food_item8").getValue().toString();
                                    Log.i("午餐食物8",lunch_food_item8);
                                    lunch_data8.setVisibility(View.VISIBLE);
                                    lunch_data8.setText(lunch_food_item8);
                                }else {
                                    lunch_data8.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("lunch").hasChild("food_item9")){
                                    String lunch_food_item9=dataSnapshot.child("lunch").child("food_item9").getValue().toString();
                                    Log.i("午餐食物9",lunch_food_item9);
                                    lunch_data9.setVisibility(View.VISIBLE);
                                    lunch_data9.setText(lunch_food_item9);
                                }else {
                                    lunch_data9.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("lunch").hasChild("total_cal")){
                                    String lunch_total_cal=dataSnapshot.child("lunch").child("total_cal").getValue().toString();
                                    Log.i("午餐總卡路里",lunch_total_cal);
                                    lunch_cal.setVisibility(View.VISIBLE);
                                    lunch_cal.setText("午餐總攝取總卡路里:"+lunch_total_cal+"大卡");
                                    int lunch_total_cal_int=Integer.parseInt(lunch_total_cal);
                                    today_calorie=today_calorie+lunch_total_cal_int;
                                    Log.i("今天所攝取的卡路里2",""+today_calorie);

                                }else{
                                    lunch_cal.setVisibility(View.GONE);

                                }
                            }else {
                                lunch_data.setVisibility(View.GONE);
                                lunch_data1.setVisibility(View.GONE);
                                lunch_data2.setVisibility(View.GONE);
                                lunch_data3.setVisibility(View.GONE);
                                lunch_data4.setVisibility(View.GONE);
                                lunch_data5.setVisibility(View.GONE);
                                lunch_data6.setVisibility(View.GONE);
                                lunch_data7.setVisibility(View.GONE);
                                lunch_data8.setVisibility(View.GONE);
                                lunch_data9.setVisibility(View.GONE);
                                lunch_cal.setVisibility(View.GONE);

                            }
                            if(dataSnapshot.child("dinner").hasChildren()){
                                dinner_data.setVisibility(View.VISIBLE);
                                if(dataSnapshot.child("dinner").hasChild("food_item1")){
                                    String dinner_food_item1=dataSnapshot.child("dinner").child("food_item1").getValue().toString();
                                    Log.i("晚餐食物1",dinner_food_item1);
                                    dinner_data1.setVisibility(View.VISIBLE);
                                    dinner_data1.setText(dinner_food_item1);
                                }else {
                                    dinner_data1.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("dinner").hasChild("food_item2")){
                                    String dinner_food_item2=dataSnapshot.child("dinner").child("food_item2").getValue().toString();
                                    Log.i("晚餐食物2",dinner_food_item2);
                                    dinner_data2.setVisibility(View.VISIBLE);
                                    dinner_data2.setText(dinner_food_item2);
                                }else {
                                    dinner_data2.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("dinner").hasChild("food_item3")){
                                    String dinner_food_item3=dataSnapshot.child("dinner").child("food_item3").getValue().toString();
                                    Log.i("晚餐食物3",dinner_food_item3);
                                    dinner_data3.setVisibility(View.VISIBLE);
                                    dinner_data3.setText(dinner_food_item3);
                                }else {
                                    dinner_data3.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("dinner").hasChild("food_item4")){
                                    String dinner_food_item4=dataSnapshot.child("dinner").child("food_item4").getValue().toString();
                                    Log.i("晚餐食物4",dinner_food_item4);
                                    dinner_data4.setVisibility(View.VISIBLE);
                                    dinner_data4.setText(dinner_food_item4);
                                }else {
                                    dinner_data4.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("dinner").hasChild("food_item5")){
                                    String dinner_food_item5=dataSnapshot.child("dinner").child("food_item5").getValue().toString();
                                    Log.i("晚餐食物5",dinner_food_item5);
                                    dinner_data5.setVisibility(View.VISIBLE);
                                    dinner_data5.setText(dinner_food_item5);
                                }else {
                                    dinner_data5.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("dinner").hasChild("food_item6")){
                                    String dinner_food_item6=dataSnapshot.child("dinner").child("food_item6").getValue().toString();
                                    Log.i("晚餐食物6",dinner_food_item6);
                                    dinner_data6.setVisibility(View.VISIBLE);
                                    dinner_data6.setText(dinner_food_item6);
                                }else {
                                    dinner_data6.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("dinner").hasChild("food_item7")){
                                    String dinner_food_item7=dataSnapshot.child("dinner").child("food_item7").getValue().toString();
                                    Log.i("晚餐食物7",dinner_food_item7);
                                    dinner_data7.setVisibility(View.VISIBLE);
                                    dinner_data7.setText(dinner_food_item7);
                                }else {
                                    dinner_data7.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("dinner").hasChild("food_item8")){
                                    String dinner_food_item8=dataSnapshot.child("dinner").child("food_item8").getValue().toString();
                                    Log.i("晚餐食物8",dinner_food_item8);
                                    dinner_data8.setVisibility(View.VISIBLE);
                                    dinner_data8.setText(dinner_food_item8);
                                }else {
                                    dinner_data8.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("dinner").hasChild("food_item9")){
                                    String dinner_food_item9=dataSnapshot.child("dinner").child("food_item9").getValue().toString();
                                    Log.i("晚餐食物9",dinner_food_item9);
                                    dinner_data9.setVisibility(View.VISIBLE);
                                    dinner_data9.setText(dinner_food_item9);
                                }else {
                                    dinner_data9.setVisibility(View.GONE);
                                }
                                if(dataSnapshot.child("dinner").hasChild("total_cal")){
                                    String dinner_total_cal=dataSnapshot.child("dinner").child("total_cal").getValue().toString();
                                    Log.i("晚餐總卡路里",dinner_total_cal);
                                    dinner_cal.setVisibility(View.VISIBLE);
                                    dinner_cal.setText("晚餐總攝取卡路里:"+dinner_total_cal+"大卡");
                                    int dinner_total_cal_int=Integer.parseInt(dinner_total_cal);
                                    today_calorie=today_calorie+dinner_total_cal_int;
                                    Log.i("今天所攝取的卡路里3",""+today_calorie);


                                }else{
                                    dinner_cal.setVisibility(View.GONE);

                                }
                            }else {
                                dinner_data.setVisibility(View.GONE);
                                dinner_data1.setVisibility(View.GONE);
                                dinner_data2.setVisibility(View.GONE);
                                dinner_data3.setVisibility(View.GONE);
                                dinner_data4.setVisibility(View.GONE);
                                dinner_data5.setVisibility(View.GONE);
                                dinner_data6.setVisibility(View.GONE);
                                dinner_data7.setVisibility(View.GONE);
                                dinner_data8.setVisibility(View.GONE);
                                dinner_data9.setVisibility(View.GONE);
                                dinner_cal.setVisibility(View.GONE);

                            }
                            total_cal.setVisibility(View.VISIBLE);
                            total_cal.setText("今天所攝取的總卡路里:"+today_calorie);
                        }else {
                            breakfast_data.setVisibility(View.GONE);
                            breakfast_data1.setVisibility(View.GONE);
                            breakfast_data2.setVisibility(View.GONE);
                            breakfast_data3.setVisibility(View.GONE);
                            breakfast_data4.setVisibility(View.GONE);
                            breakfast_data5.setVisibility(View.GONE);
                            breakfast_data6.setVisibility(View.GONE);
                            breakfast_data7.setVisibility(View.GONE);
                            breakfast_data8.setVisibility(View.GONE);
                            breakfast_data9.setVisibility(View.GONE);
                            breakfast_cal.setVisibility(View.GONE);
                            lunch_data.setVisibility(View.GONE);
                            lunch_data1.setVisibility(View.GONE);
                            lunch_data2.setVisibility(View.GONE);
                            lunch_data3.setVisibility(View.GONE);
                            lunch_data4.setVisibility(View.GONE);
                            lunch_data5.setVisibility(View.GONE);
                            lunch_data6.setVisibility(View.GONE);
                            lunch_data7.setVisibility(View.GONE);
                            lunch_data8.setVisibility(View.GONE);
                            lunch_data9.setVisibility(View.GONE);
                            lunch_cal.setVisibility(View.GONE);
                            dinner_data.setVisibility(View.GONE);
                            dinner_data1.setVisibility(View.GONE);
                            dinner_data2.setVisibility(View.GONE);
                            dinner_data3.setVisibility(View.GONE);
                            dinner_data4.setVisibility(View.GONE);
                            dinner_data5.setVisibility(View.GONE);
                            dinner_data6.setVisibility(View.GONE);
                            dinner_data7.setVisibility(View.GONE);
                            dinner_data8.setVisibility(View.GONE);
                            dinner_data9.setVisibility(View.GONE);
                            dinner_cal.setVisibility(View.GONE);
                            total_cal.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }





    @Override
    public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        final String text = String.format(FORMATTER.format(date.getDate()));
        if(text.equals(Time.formatCalendar(System.currentTimeMillis()))){
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(DiaryCalendar.this,DiarySelect.class);
            intent.putExtra("put_date",text);
            startActivity(intent);
        }else {
            Toast.makeText(this, "只能長安今天的日期", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(DiaryCalendar.this,ControlDie.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(DiaryCalendar.this,ControlDie.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        min = LocalDate.of(2018, Month.OCTOBER, 1);
        LocalDate temp = LocalDate.now().minusMonths(2);
        LocalDate temp2 = LocalDate.now();
        final ArrayList<CalendarDay> dates = new ArrayList<>();
        breakfast_database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Food_note");

        for (int i = 0; i < 36; i++) {
            final CalendarDay day = CalendarDay.from(min);
            Log.i("为什么1",""+FORMATTER.format(min));

            breakfast_database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    Log.i("为什么e",""+FORMATTER.format(min));
                    if(dataSnapshot.hasChild(FORMATTER.format(min))){
                        dates.add(day);
                        Log.i("为什么2",""+dates.add(day));
                        min = min.plusDays(1);
                        Log.i("为什么3",""+min.toString());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

        return dates;
    }


}
