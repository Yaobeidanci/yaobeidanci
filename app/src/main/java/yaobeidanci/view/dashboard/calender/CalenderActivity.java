package yaobeidanci.view.dashboard.calender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import yaobeidanci.view.R;
import yaobeidanci.view.book.StudyPlan;


import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

public class CalenderActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView textView_days_seq;
    private TextView textView_days_total;
    private TextView tv_year_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard_calender);

        FindViews();
        InitCalender();
    }

    private void FindViews() {
        calendarView = findViewById(R.id.calendarView);
        textView_days_seq = findViewById(R.id.textView_days_seq);
        textView_days_total = findViewById(R.id.textView_days_total);
        tv_year_month = findViewById(R.id.tv_year_month);
    }

    private void InitCalender() {
        textView_days_seq.setText(String.valueOf(2));
        textView_days_total.setText(String.valueOf(5));
        MarkDays();
    }

    private void MarkDays() {
        Calendar c = new Calendar();
        c.setYear(2020);
        c.setMonth(11);
        c.setDay(30);
        calendarView.addSchemeDate(c);
        for (int i = 0; i < 3; i++) {
            Calendar calendar = new Calendar();
            calendar.setYear(2020);
            calendar.setMonth(12);
            calendar.setDay(5 - i * 2);
            calendarView.addSchemeDate(calendar);
        }
        calendarView.setMonthView(MyCalenderView.class);

        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                tv_year_month.setText(calendarView.getCurYear() + "年" + calendarView.getCurMonth() + "月");
            }
        });

    }

    public void returnStudyPlan(View view) {
        Intent intent = new Intent(CalenderActivity.this, StudyPlan.class);
        startActivity(intent);
    }
}