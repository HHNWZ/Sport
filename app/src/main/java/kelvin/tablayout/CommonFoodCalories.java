package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.a888888888.sport.R;

import java.util.ArrayList;

public class CommonFoodCalories extends AppCompatActivity {



    private ArrayList<Integer> CFCFoodImage=new ArrayList<>();
    private ArrayList<String> CFCFoodName=new ArrayList<>();
    private ArrayList<String> CFCFoodUnit=new ArrayList<>();
    private ArrayList<String> CFCFoodCalories=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_food_calories);
        Toolbar common_food_calories_toolbar= findViewById(R.id.common_food_calories_toolbar);
        setSupportActionBar(common_food_calories_toolbar);
        ActionBar common_food_calories_actionbar=getSupportActionBar();
        if (common_food_calories_actionbar != null) {
            common_food_calories_actionbar.setTitle(R.string.common_food_calories);
            common_food_calories_actionbar.setDisplayHomeAsUpEnabled(true);
        }
        initImageBitmaps();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(CommonFoodCalories.this,ControlDie.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void initImageBitmaps(){

        CFCFoodImage.add(R.drawable.rice_icon);
        CFCFoodName.add("米飯");
        CFCFoodUnit.add("◎1碗");
        CFCFoodCalories.add("※225(卡路里)");

        CFCFoodImage.add(R.drawable.banana_icon);
        CFCFoodName.add("香蕉");
        CFCFoodUnit.add("◎1條");
        CFCFoodCalories.add("※120(卡路里)");

        CFCFoodImage.add(R.drawable.milk_icon);
        CFCFoodName.add("牛奶");
        CFCFoodUnit.add("◎1杯244g");
        CFCFoodCalories.add("※150(卡路里)");

        CFCFoodImage.add(R.drawable.cow_icon);
        CFCFoodName.add("牛肉");
        CFCFoodUnit.add("◎1片72g");
        CFCFoodCalories.add("※140(卡路里)");

        CFCFoodImage.add(R.drawable.donut_icon);
        CFCFoodName.add("甜甜圈");
        CFCFoodUnit.add("◎1個");
        CFCFoodCalories.add("※270(卡路里)");

        CFCFoodImage.add(R.drawable.fish_icon);
        CFCFoodName.add("魚肉");
        CFCFoodUnit.add("◎1條");
        CFCFoodCalories.add("※206(卡路里)");

        CFCFoodImage.add(R.drawable.vegetables_icon);
        CFCFoodName.add("蔬菜");
        CFCFoodUnit.add("◎100g");
        CFCFoodCalories.add("※65(卡路里)");

        CFCFoodImage.add(R.drawable.chicken_icon);
        CFCFoodName.add("雞肉");
        CFCFoodUnit.add("◎100g");
        CFCFoodCalories.add("※239(卡路里)");

        CFCFoodImage.add(R.drawable.egg_icon);
        CFCFoodName.add("雞蛋");
        CFCFoodUnit.add("◎1顆");
        CFCFoodCalories.add("※106(卡路里)");

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView common_food_calories_recyclerView = findViewById(R.id.common_food_calories_recyclerView);
        RecyclerViewAdapter adapter= new RecyclerViewAdapter(CFCFoodImage,CFCFoodName,CFCFoodUnit,CFCFoodCalories,this);
        common_food_calories_recyclerView.setAdapter(adapter);
        common_food_calories_recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
