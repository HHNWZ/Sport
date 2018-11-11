package kelvin.tablayout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiarySelect extends AppCompatActivity {

    private Toolbar diary_select_toolbar;
    private ActionBar diary_select_actionbar;
    private static String get_date_from_diary_calendar;
    FoodAdapter dataAdapter=null;
    private String time_slot_select="";
    private RadioGroup select_ead_time_slot_radio_group;
    private RadioButton breakfast;
    private RadioButton lunch;
    private RadioButton dinner;
    private static int total_cal=0;
    private String my_id;

    private FirebaseAuth mAuth;
    private DatabaseReference food_note_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_select);
        diary_select_toolbar=(Toolbar) findViewById(R.id.diary_select_toolbar);
        setSupportActionBar(diary_select_toolbar);
        diary_select_actionbar=getSupportActionBar();
        diary_select_actionbar.setDisplayHomeAsUpEnabled(true);
        get_date_from_diary_calendar=getIntent().getStringExtra("put_date");
        Log.i("取得日期",get_date_from_diary_calendar);
        diary_select_actionbar.setTitle(get_date_from_diary_calendar);
        mAuth=FirebaseAuth.getInstance();
        my_id=mAuth.getCurrentUser().getUid();
        Log.i("我的id",my_id);
        food_note_database=FirebaseDatabase.getInstance().getReference().child("Users").child(my_id).child("Food_note").child(get_date_from_diary_calendar);

        displaylistView();
        checkButtonClick();
    }

    private void displaylistView() {
        ArrayList<Food_note> food_notes_list=new ArrayList<Food_note>();
        Food_note food_note=new Food_note(R.drawable.rice_icon,"米飯",225,1,"碗",false);
        food_notes_list.add(food_note);
        food_note=new Food_note(R.drawable.banana_icon,"香蕉",120,1,"條",false);
        food_notes_list.add(food_note);
        food_note=new Food_note(R.drawable.milk_icon,"牛奶",150,1,"杯",false);
        food_notes_list.add(food_note);
        food_note=new Food_note(R.drawable.cow_icon,"牛肉",140,1,"片",false);
        food_notes_list.add(food_note);
        food_note=new Food_note(R.drawable.donut_icon,"甜甜圈",270,1,"個",false);
        food_notes_list.add(food_note);
        food_note=new Food_note(R.drawable.fish_icon,"魚肉",206,1,"條",false);
        food_notes_list.add(food_note);
        food_note=new Food_note(R.drawable.vegetables_icon,"蔬菜",65,1,"份",false);
        food_notes_list.add(food_note);
        food_note=new Food_note(R.drawable.chicken_icon,"雞肉",239,1,"份",false);
        food_notes_list.add(food_note);
        food_note=new Food_note(R.drawable.egg_icon,"雞蛋",106,1,"顆",false);
        food_notes_list.add(food_note);

        dataAdapter=new FoodAdapter(this,R.layout.food_note_layout,food_notes_list);
        ListView listView=(ListView)findViewById(R.id.food_list);

        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food_note food_note =(Food_note) parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(),"clicked on row:"+states.getName(),Toast.LENGTH_SHORT).show();
                Log.i("我在這裡1",""+position+""+food_note.getFood_name());
            }
        });

    }

    private  class FoodAdapter extends ArrayAdapter<Food_note>{

        private  ArrayList<Food_note> food_notes_list;

        public FoodAdapter(Context context,int textviewResoureid,ArrayList<Food_note>food_notes_list){
            super(context,textviewResoureid,food_notes_list);
            this.food_notes_list=new ArrayList<Food_note>();
            this.food_notes_list.addAll(food_notes_list);
        }

        private class ViewHolder{
            CircleImageView food_icon1;
            TextView food_name_text_view1;
            TextView food_calorie_data_text_view1;
            TextView number_food_component_textview1;
            TextView number_food_component_unit_textview1;
            Button add_food_component1;
            Button sub_food_component1;
            CheckBox food_check_box1;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            ViewHolder holder=null;
            Log.v("ConvertView",String.valueOf(position));
            if(convertView==null){
                LayoutInflater vi=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=vi.inflate(R.layout.food_note_layout,null);

                holder=new ViewHolder();
                holder.food_icon1=(CircleImageView)convertView.findViewById(R.id.food_icon);
                holder.food_name_text_view1=(TextView)convertView.findViewById(R.id.food_name);
                holder.food_calorie_data_text_view1=(TextView)convertView.findViewById(R.id.food_calorie_data);
                holder.number_food_component_textview1=(TextView)convertView.findViewById(R.id.number_food_component);
                holder.number_food_component_unit_textview1=(TextView)convertView.findViewById(R.id.number_food_component_unit);
                holder.add_food_component1=(Button)convertView.findViewById(R.id.add_food_component);
                holder.sub_food_component1=(Button)convertView.findViewById(R.id.sub_food_component);
                holder.food_check_box1=(CheckBox)convertView.findViewById(R.id.food_check_box);

                convertView.setTag(holder);

                holder.food_check_box1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox food_check_box=(CheckBox)v;
                        Food_note food_note=(Food_note)food_check_box.getTag();
                        food_note.setSelected(food_check_box.isChecked());
                        if(food_check_box.isChecked()){
                            Log.i("打勾",food_note.getFood_name());
                        }else{
                            Log.i("去勾",food_note.getFood_name());

                        }
                    }
                });

                final ViewHolder add_food_component_finalHolder=holder;
                holder.add_food_component1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Food_note food_note=food_notes_list.get(position);
                        Log.i("第"+position+"代碼是",""+food_note.getNumber_food_component());
                        Log.i("第"+position+"按鈕","被點解了");
                        int add_food_component=food_note.getNumber_food_component()+1;
                        food_note.setNumber_food_component(add_food_component);
                        Log.i("第"+position+"的食物分量",""+food_note.getNumber_food_component());
                        int add_food_calorie;
                        switch (position){
                            case 0:
                                add_food_calorie=225*add_food_component;
                                Log.i("米飯目前的分量",""+add_food_component);
                                Log.i("米飯目前的卡里路",""+add_food_calorie);
                                food_note.setFood_calorie(add_food_calorie);
                                break;
                            case 1:
                                add_food_calorie=120*add_food_component;
                                food_note.setFood_calorie(add_food_calorie);
                                break;
                            case 2:
                                add_food_calorie=150*add_food_component;
                                food_note.setFood_calorie(add_food_calorie);
                                break;
                            case 3:
                                add_food_calorie=140*add_food_component;
                                food_note.setFood_calorie(add_food_calorie);
                                break;
                            case 4:
                                add_food_calorie=270*add_food_component;
                                food_note.setFood_calorie(add_food_calorie);
                                break;
                            case 5:
                                add_food_calorie=206*add_food_component;
                                food_note.setFood_calorie(add_food_calorie);
                                break;
                            case 6:
                                add_food_calorie=65*add_food_component;
                                food_note.setFood_calorie(add_food_calorie);
                                break;
                            case 7:
                                add_food_calorie=239*add_food_component;
                                food_note.setFood_calorie(add_food_calorie);
                                break;
                            case 8:
                                add_food_calorie=106*add_food_component;
                                food_note.setFood_calorie(add_food_calorie);
                                break;
                            default:
                                break;
                        }
                        add_food_component_finalHolder.food_calorie_data_text_view1.setText(""+food_note.getFood_calorie());
                        add_food_component_finalHolder.number_food_component_textview1.setText(""+food_note.getNumber_food_component());
                        if(food_note.getNumber_food_component()==1){
                            add_food_component_finalHolder.sub_food_component1.setVisibility(View.INVISIBLE);
                        }
                        if(food_note.getNumber_food_component()>1) {
                            //add_food_component_finalHolder.add_food_component1.setVisibility(View.INVISIBLE);
                            add_food_component_finalHolder.sub_food_component1.setVisibility(View.VISIBLE);
                        }
                        if(food_note.getNumber_food_component()==5){
                            add_food_component_finalHolder.add_food_component1.setVisibility(View.INVISIBLE);
                        }if(food_note.getNumber_food_component()<5){
                            add_food_component_finalHolder.add_food_component1.setVisibility(View.VISIBLE);
                        }
                    }
                });
                final ViewHolder sub_food_component_finalHolder=holder;
                holder.sub_food_component1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Food_note food_note=food_notes_list.get(position);
                        Log.i("第"+position+"代碼是",""+food_note.getNumber_food_component());
                        Log.i("第"+position+"按鈕","被點解了");
                        int sub_food_component=food_note.getNumber_food_component()-1;
                        food_note.setNumber_food_component(sub_food_component);
                        Log.i("第"+position+"的食物分量",""+food_note.getNumber_food_component());
                        int sub_food_calorie;
                        switch (position){
                            case 0:
                                sub_food_calorie=225*sub_food_component;
                                Log.i("米飯目前的分量",""+sub_food_component);
                                Log.i("米飯目前的卡里路",""+sub_food_calorie);
                                food_note.setFood_calorie(sub_food_calorie);
                                break;
                            case 1:
                                sub_food_calorie=120*sub_food_component;
                                food_note.setFood_calorie(sub_food_calorie);
                                break;
                            case 2:
                                sub_food_calorie=150*sub_food_component;
                                food_note.setFood_calorie(sub_food_calorie);
                                break;
                            case 3:
                                sub_food_calorie=140*sub_food_component;
                                food_note.setFood_calorie(sub_food_calorie);
                                break;
                            case 4:
                                sub_food_calorie=270*sub_food_component;
                                food_note.setFood_calorie(sub_food_calorie);
                                break;
                            case 5:
                                sub_food_calorie=206*sub_food_component;
                                food_note.setFood_calorie(sub_food_calorie);
                                break;
                            case 6:
                                sub_food_calorie=65*sub_food_component;
                                food_note.setFood_calorie(sub_food_calorie);
                                break;
                            case 7:
                                sub_food_calorie=239*sub_food_component;
                                food_note.setFood_calorie(sub_food_calorie);
                                break;
                            case 8:
                                sub_food_calorie=106*sub_food_component;
                                food_note.setFood_calorie(sub_food_calorie);
                                break;
                            default:
                                break;
                        }
                        sub_food_component_finalHolder.food_calorie_data_text_view1.setText(""+food_note.getFood_calorie());
                        sub_food_component_finalHolder.number_food_component_textview1.setText(""+food_note.getNumber_food_component());
                        if(food_note.getNumber_food_component()==1){
                            add_food_component_finalHolder.sub_food_component1.setVisibility(View.INVISIBLE);
                        }
                        if(food_note.getNumber_food_component()>1) {
                            //add_food_component_finalHolder.add_food_component1.setVisibility(View.INVISIBLE);
                            add_food_component_finalHolder.sub_food_component1.setVisibility(View.VISIBLE);
                        }
                        if(food_note.getNumber_food_component()==5){
                            add_food_component_finalHolder.add_food_component1.setVisibility(View.INVISIBLE);
                        }if(food_note.getNumber_food_component()<5){
                            add_food_component_finalHolder.add_food_component1.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
            else {
                holder=(ViewHolder)convertView.getTag();
            }

            Food_note food_note=food_notes_list.get(position);
            holder.food_icon1.setImageResource(food_note.getFood_icon());
            holder.food_name_text_view1.setText(food_note.getFood_name());
            holder.food_calorie_data_text_view1.setText(""+food_note.getFood_calorie());
            holder.number_food_component_textview1.setText(""+food_note.getNumber_food_component());
            holder.number_food_component_unit_textview1.setText(food_note.getNumber_food_component_unit());
            holder.food_check_box1.setChecked(food_note.isSelected());
            if(food_note.getNumber_food_component()<=1){
                holder.sub_food_component1.setVisibility(View.INVISIBLE);
            }
            if(food_note.getNumber_food_component()>=5){
                holder.add_food_component1.setVisibility(View.INVISIBLE);
            }
            holder.food_check_box1.setTag(food_note);
            return convertView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(DiarySelect.this, DiaryCalendar.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(DiarySelect.this, DiaryCalendar.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void checkButtonClick(){
        Button confirm_food_note_button=(Button)findViewById(R.id.confirm_food_note_button);
        confirm_food_note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakfast=(RadioButton)findViewById(R.id.breakfast);
                lunch=(RadioButton)findViewById(R.id.lunch);
                dinner=(RadioButton)findViewById(R.id.dinner);
                select_ead_time_slot_radio_group=(RadioGroup)findViewById(R.id.select_ead_time_slot_radio_group);

                if(breakfast.isChecked()||lunch.isChecked()||dinner.isChecked()){
                    Log.i("時斷","有被選擇");
                    switch (select_ead_time_slot_radio_group.getCheckedRadioButtonId()){
                        case R.id.breakfast:
                            time_slot_select="breakfast";
                            food_note_database.child(time_slot_select).setValue(null);
                            break;
                        case R.id.lunch:
                            time_slot_select="lunch";
                            food_note_database.child(time_slot_select).setValue(null);
                            break;
                        case R.id.dinner:
                            time_slot_select="dinner";
                            food_note_database.child(time_slot_select).setValue(null);
                            break;
                    }
                    StringBuffer responseText=new StringBuffer();
                    //responseText.append(time_slot_select+"你吃了\n");

                    ArrayList<Food_note> food_notes_list =dataAdapter.food_notes_list;
                    int food_item=1;
                    for(int i=0;i<food_notes_list.size();i++){
                        Food_note food_note =food_notes_list.get(i);

                        if(food_note.isSelected()){
                            total_cal=total_cal+food_note.getFood_calorie();
                            responseText.append(food_note.getFood_name()+" "+food_note.getNumber_food_component()+" "+food_note.getNumber_food_component_unit()+"卡路里是:"+food_note.getFood_calorie()+"\n");
                            food_note_database.child(time_slot_select).child("food_item"+food_item).setValue(food_note.getFood_name()+" "+food_note.getNumber_food_component()+food_note.getNumber_food_component_unit()+" 卡路里是:"+food_note.getFood_calorie()+"大卡");
                            food_item=food_item+1;
                        }
                    }

                    if(responseText.length()==0){
                        Toast.makeText(getApplicationContext(),"請打勾食物",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),responseText+"\n總卡路里:"+total_cal,Toast.LENGTH_LONG).show();
                        food_note_database.child(time_slot_select).child("total_cal").setValue(total_cal);
                        Log.i("資料庫讀取資料",""+responseText.length());
                        total_cal=0;
                        time_slot_select="";
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"選擇用餐時斷",Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}
