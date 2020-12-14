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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
        MarkDays();
    }

    private List<Calendar> AcquireDates() {
        List<Calendar> calendars = new ArrayList<>();


        // 请求近两个月的签到信息
        // 返回签到日期（String）数组

        List<String> dates = new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {
            String[] date = dates.get(i).split("-");
            Calendar calendar = new Calendar();
            calendar.setYear(Integer.parseInt(date[0]));
            calendar.setMonth(Integer.parseInt(date[1]));
            calendar.setDay(Integer.parseInt(date[2]));
            calendars.add(calendar);
        }

        return calendars;
    }

    private void MarkDays() {
        int seq = 0;

        List<Calendar> calendars = AcquireDates();
        for (int i = 0; i < calendars.size(); i++) {
            Calendar calendar = calendars.get(i);
            calendarView.addSchemeDate(calendar);

            // 计算连续签到天数
            if (calendar.differ(calendars.get(i - 1)) == 1) {
                seq += 1;
            } else {
                seq = 0;
            }
        }

        calendarView.setMonthView(MyCalenderView.class);

        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                tv_year_month.setText(calendarView.getCurYear() + "年" + calendarView.getCurMonth() + "月");
            }
        });

        textView_days_seq.setText(String.valueOf(seq));
        textView_days_total.setText(String.valueOf(5));
    }

    public void returnStudyPlan(View view) {
        Intent intent = new Intent(CalenderActivity.this, StudyPlan.class);
        startActivity(intent);
    }
}