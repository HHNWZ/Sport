package kelvin.tablayout;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.a888888888.sport.R;

public class DiarySelect extends AppCompatActivity {

    private Toolbar diary_select_toolbar;
    private ActionBar diary_select_actionbar;
    private static String get_date_from_diary_calendar;

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(DiarySelect.this, DiaryCalendar.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
