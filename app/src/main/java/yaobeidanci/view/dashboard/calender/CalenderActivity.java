package yaobeidanci.view.dashboard.calender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import yaobeidanci.MyUtil;
import yaobeidanci.bean.WordObject;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;
import yaobeidanci.view.book.StudyPlan;
import yaobeidanci.view.collect.CollectMainActivity;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 54行
 */
public class CalenderActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView textView_days_seq;
    private TextView textView_days_total;
    private TextView tv_year_month;
    final List<Calendar> calendars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard_calender);

        FindViews();
        AcquireDates();
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

    private void AcquireDates() {

        final JSONObject object1 = new JSONObject();
        try {
            object1.put("uid", MyUtil.getUid());
            MyUtil.httpGet(MyUtil.BASE_URL + "/resource/mark", object1, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    try {
                        JSONObject res = new JSONObject((String) result.data);
                        JSONArray jsonArray = res.getJSONArray("data");
                        // 请求近两个月的签到信息
                        // 返回签到日期（String）数组 dates
                        List<String> dates = new ArrayList<>();

                        for(int i =0 ;i<jsonArray.length();i++){
                            dates.add((String) jsonArray.get(i));
                        }

                        for (int i = 0; i < dates.size(); i++) {
                            String[] date = dates.get(i).split("-");
                            Calendar calendar = new Calendar();
                            calendar.setYear(Integer.parseInt(date[0]));
                            calendar.setMonth(Integer.parseInt(date[1]));
                            calendar.setDay(Integer.parseInt(date[2]));
                            calendars.add(calendar);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(MyUtil.Res result) {
                    Toast.makeText(MainActivity.getContext(), result.msg, Toast.LENGTH_SHORT).show();
                }
            },true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void MarkDays() {
        int seq = 0;

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