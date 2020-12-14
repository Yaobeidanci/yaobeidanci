package yaobeidanci.view.book;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;
import yaobeidanci.view.dashboard.calender.CalenderActivity;
import yaobeidanci.view.dashboard.overview.OverviewActivity;

public class StudyPlan extends AppCompatActivity {
    public static String bookid;//当前书籍id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_book_study_plan);
        Intent intent=getIntent();
        TextView bookname=(TextView)findViewById(R.id.text_plan_name);
        TextView totalcount=(TextView)findViewById(R.id.total_count);
        TextView nowcount=(TextView)findViewById(R.id.now_count);
        TextView todaystudy=(TextView)findViewById(R.id.today_count);
        TextView todaytime=(TextView)findViewById(R.id.today_study_time);
        TextView alreadystudy=(TextView)findViewById(R.id.already_study_count);
        TextView totaltime=(TextView)findViewById(R.id.total_study_time);
        ImageView img=(ImageView)findViewById(R.id.img_plan_book);
        if(intent.getStringExtra("bookid")!=null){
            bookid=intent.getStringExtra("bookid");
            bookname.setText(intent.getStringExtra("bookname"));
            totalcount.setText(ConstantConfig.wordTotalNumberById(bookid)+"词");
            nowcount.setText("已完成：0/");
            todaystudy.setText("0词");
            todaytime.setText("0分钟");
            alreadystudy.setText("0词");
            totaltime.setText("0分钟");
            img.setImageResource(ConstantConfig.bookPicById(bookid));
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