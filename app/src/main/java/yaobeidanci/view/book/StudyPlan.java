package yaobeidanci.view.book;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import yaobeidanci.MyUtil;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;
import yaobeidanci.view.dashboard.calender.CalenderActivity;
import yaobeidanci.view.dashboard.overview.OverviewActivity;

public class StudyPlan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_book_study_plan);
        //Intent intent=getIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final TextView dailyplan=(TextView)findViewById(R.id.daily_num);
        final TextView bookname=(TextView)findViewById(R.id.text_plan_name);
        final TextView totalcount=(TextView)findViewById(R.id.total_count);
        final TextView nowcount=(TextView)findViewById(R.id.now_count);
        final TextView todaystudy=(TextView)findViewById(R.id.today_count);
        final TextView todaytime=(TextView)findViewById(R.id.today_study_time);
        final TextView alreadystudy=(TextView)findViewById(R.id.already_study_count);
        final TextView totaltime=(TextView)findViewById(R.id.total_study_time);
        final ImageView img=(ImageView)findViewById(R.id.img_plan_book);
        // 从后端获取schedule
        final JSONObject object = new JSONObject();
        try {
            object.put("uid", MyUtil.getUid());
            MyUtil.httpGet(MyUtil.BASE_URL + "/resource/schedule", object, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    try {
                        JSONObject jsonObject = new JSONObject((String) result.data);
                        int code = jsonObject.getInt("status");
                        if (code==404){
                            Toast.makeText(MainActivity.getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }else{
                            JSONObject object1 = jsonObject.getJSONObject("data");
                            SetNumActivity.bookid = object1.getString("book_id");
                            // 每天计划背多少
                            SetNumActivity.inputcnt = object1.getInt("num_daily");
                            // 今天已经学了多少个
                            int today_already = object1.getInt("learn_day");
                            // 今天学了几分钟
                            int time_today_already = object1.getInt("time_day");
                            // 累计学了多少分钟
                            int time_total = object1.getInt("time_progress");
                            // 累计学了多少个
                            int num_total = object1.getInt("current_progress");


                            bookname.setText(ConstantConfig.bookNameById(SetNumActivity.bookid));
                            dailyplan.setText(SetNumActivity.inputcnt+"词");
                            totalcount.setText(ConstantConfig.wordTotalNumberById(SetNumActivity.bookid)+"词");
                            nowcount.setText("已完成：" + num_total + "/");
                            todaystudy.setText(today_already + "词");
                            todaytime.setText(time_today_already +  "分钟");
                            alreadystudy.setText(num_total + "词");
                            totaltime.setText(time_total + "分钟");
                            img.setImageResource(ConstantConfig.bookPicById(SetNumActivity.bookid));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(MyUtil.Res result) {
                    Toast.makeText(MainActivity.getContext(), result.msg, Toast.LENGTH_SHORT).show();
                }
            }, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void changeBook(View view) {
        Intent intent=new Intent(StudyPlan.this,ChooseBook.class);
        startActivity(intent);
    }
    public void returnMain(View wiew){
        Intent intent=new Intent(StudyPlan.this, MainActivity.class);
        startActivity(intent);
    }
    public void toOverview(View view){
        Intent intent=new Intent(StudyPlan.this, OverviewActivity.class);
        startActivity(intent);
    }
    public void toCalender(View view){
        Intent intent=new Intent(StudyPlan.this, CalenderActivity.class);
        startActivity(intent);
    }
}