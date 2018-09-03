package kelvin.tablayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private Toolbar pieChart_toolbar;
    public static ActionBar actionBar;
    private PieChart mChart;
    private Legend l;
    private ArrayList<PieEntry> pieEntryList;
    private ArrayList<Integer> colors;
    private PieEntry running,walking,yoga,squats,crunches;
    private PieDataSet pieDataSet;
    private PieData pieData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);

        pieChart_toolbar=(Toolbar)findViewById(R.id.pieChart_toolbar);
        setSupportActionBar(pieChart_toolbar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("piechart");
        actionBar.setDisplayHomeAsUpEnabled(true);
        mChart=findViewById(R.id.chart1);


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

        mChart.setCenterText("每週\n總消耗卡路里");                 //设置PieChart内部圆文字的内容
        mChart.setCenterTextSize(10f);                //设置PieChart内部圆文字的大小
        mChart.setCenterTextColor(Color.WHITE);         //设置PieChart内部圆文字的颜色

        l = mChart.getLegend();
        l.setEnabled(false);                    //是否启用图列（true：下面属性才有意义）





        pieEntryList = new ArrayList<PieEntry>();
        colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#38b048"));
        colors.add(Color.parseColor("#189428"));
        colors.add(Color.parseColor("#349bb3"));
        colors.add(Color.parseColor("#2671ab"));
        colors.add(Color.parseColor("#2c618a"));

        //饼图实体 PieEntry
        running = new PieEntry(20, "跑步:100大卡");
        walking = new PieEntry(20, "步行:100大卡");
        yoga = new PieEntry(20, "瑜伽:100大卡");
        squats = new PieEntry(20, "深蹲:100大卡");
        crunches = new PieEntry(20, "仰臥起坐:100大卡");
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
        mChart.setOnChartValueSelectedListener(this);






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(PieChartActivity.this, MainActivity .class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setData() {

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
