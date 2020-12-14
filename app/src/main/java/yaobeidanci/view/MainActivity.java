package yaobeidanci.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.ims.ImsMmTelManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import yaobeidanci.MyUtil;
import yaobeidanci.view.book.ChooseBook;
import yaobeidanci.view.book.StudyPlan;
import yaobeidanci.view.collect.CollectMainActivity;
import yaobeidanci.view.learn.WordMainActivity;
import yaobeidanci.view.mainpage.SelfPage;
import yaobeidanci.view.mainpage.SettingPage;

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

        // 进入计划统计页面
        ImageView book_bt = findViewById(R.id.book_bt);
        book_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudyPlan.class);
                startActivity(intent);
            }
        });

        JSONObject object = new JSONObject();
        try {
            object.put("uid", MyUtil.getUid());
            MyUtil.httpGet(MyUtil.BASE_URL + "/resoucre/schedule", object, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    try {
                        JSONObject jsonObject = new JSONObject((String) result.data);
                        int code = jsonObject.getInt("status");
                        if (code==404){
                            Toast.makeText(MainActivity.getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ChooseBook.class);
                            startActivity(intent);
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

    public void onclick(View view){
        Intent intent=new Intent(MainActivity.this, SelfPage.class);
        startActivity(intent);
    }

    public static Context getContext() {
        return context;
    }
}