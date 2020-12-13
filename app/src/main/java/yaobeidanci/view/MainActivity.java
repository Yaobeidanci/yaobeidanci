package yaobeidanci.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import yaobeidanci.view.book.StudyPlan;
import yaobeidanci.view.collect.CollectMainActivity;
import yaobeidanci.view.learn.WordMainActivity;
import yaobeidanci.view.mainpage.SelfPage;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // 进入背单词页面
        Button learn_gate = findViewById(R.id.learn_gate);
        learn_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordMainActivity.startIt(MainActivity.this);
            }
        });

        //进入收藏页面
        ImageView collect = findViewById(R.id.imageView);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectMainActivity.startIt(MainActivity.this);
            }
        });

        ImageView book_bt = findViewById(R.id.book_bt);
        book_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudyPlan.class);
                startActivity(intent);
            }
        });
    }

    public static Context getContext() {
        return context;
    }
}