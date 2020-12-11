package yaobeidanci.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import yaobeidanci.MyUtil;
import yaobeidanci.view.collect.CollectMainActivity;
import yaobeidanci.view.learn.WordMainActivity;
import yaobeidanci.view.welcome.Welcome;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        Button button = findViewById(R.id.startLearnBt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordMainActivity.startIt(MainActivity.this);
            }
        });
        Button collectBt = findViewById(R.id.startCollectBt);
        collectBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollectMainActivity.class);
                startActivity(intent);
            }
        });

        Button button1 = findViewById(R.id.get_bt);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();
                try {
                    object.put("book_id", "CET4luan_2");
                    object.put("total", 5);
                    MyUtil.httpGet("http://10.0.2.2:5000/resource/wordList", object, new MyUtil.SuccessCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            try {
                                JSONObject jsonObject = new JSONObject((String) result);
                                String res = jsonObject.getString("data");
                                MyUtil.writeFile(MainActivity.this, "word.json", res);
                                Log.d("qwe", "onSuccess: " + res);
                                Toast.makeText(MainActivity.this, MainActivity.this.getFilesDir().getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Button button2 = findViewById(R.id.post_bt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();
                try {
                    object.put("username", "tsluqn");
                    object.put("password", "123456");
                    MyUtil.httpPost("http://10.0.2.2:5000/api/login", object, true, new MyUtil.SuccessCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            Toast.makeText(MainActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void click_book(View view) {
        Intent intent = new Intent(MainActivity.this, Welcome.class);
        startActivity(intent);
    }

    public static Context getContext() {
        return context;
    }
}