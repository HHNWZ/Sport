package kelvin.tablayout;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.android.gms.common.api.internal.BackgroundDetector;
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
import qwer.EventDecorator;

public class DiaryCalendar extends AppCompatActivity implements OnDateSelectedListener, OnDateLongClickListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy年MMMd日 EEE");

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @BindView(R.id.textView)
    TextView textView;

    private Button diary_message_button;
    private Toolbar diary_calendar_toolbar;
    private ActionBar diary_calendar_actionbar;

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
        diary_message_button=(Button)findViewById(R.id.diary_message_button);

        Log.i("日期12345",""+Time.formatCalendar(System.currentTimeMillis()));

      widget.addDecorator(new EventDecorator(Color.RED,doInBackground()));





        //Setup initial text
        textView.setText("No Selection");
    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
                textView.setText(selected ? FORMATTER.format(date.getDate()) : "沒有選擇日期");
                diary_message_button.setVisibility(View.VISIBLE);




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
    protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDate min = LocalDate.of(2018, Month.OCTOBER, 1);
        LocalDate temp = LocalDate.now().minusMonths(2);
        LocalDate temp2 = LocalDate.now();
        final ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            final CalendarDay day = CalendarDay.from(min);
            Log.i("为什么1",""+FORMATTER.format(min));
            if(i%2==0){
                dates.add(day);
                Log.i("为什么2",""+dates.add(day));


            }
            min = min.plusDays(1);
            Log.i("为什么3",""+min.toString());
        }

        return dates;
    }
}
