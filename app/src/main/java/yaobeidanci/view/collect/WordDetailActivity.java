package yaobeidanci.view.collect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yaobeidanci.MyUtil;
import yaobeidanci.bean.SentenceObject;
import yaobeidanci.bean.WordObject;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;
public class WordDetailActivity extends AppCompatActivity {

    private static List<WordObject> wordList = new ArrayList<>();
    public SlidingDrawer sd;
    public ListView exp_ls;
    public TextView word_title_tv;
    public TextView base_exp_tv;

    /**
     * 启动一个新的word页面，并加载单词
     * @param src 调用者
     */
    public static void startIt(final Activity src){
        final JSONObject object = new JSONObject();

        try {
            object.put("uid", MyUtil.getUid());
            MyUtil.httpGet(MyUtil.BASE_URL + "/resource/starWords", object, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    try {
                        JSONObject res = new JSONObject((String) result.data);
                        String words_json = res.getString("data");
                        wordList = new Gson().fromJson(words_json, new TypeToken<List<WordObject>>(){}.getType());
                        Intent intent = new Intent(src, WordDetailActivity.class);
                        src.startActivity(intent);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collect_activity_word_detail);

        //初始化单词数据


        //初始化解释


        //获取组件
        exp_ls = findViewById(R.id.exp_detail_ls);
        sd=findViewById(R.id.drawer);
        word_title_tv=findViewById(R.id.word_title);
        base_exp_tv=findViewById(R.id.base_exp);


        //创建自定义适配器，并且与数据entryList连接
        WordDetailAdapter adapter=new  WordDetailAdapter(WordDetailActivity.this,R.layout.layout_collect_word_detail_item,wordList);

        //将适配器与listView连接
        ListView listView =findViewById(R.id.word_detail_ls);
        listView.setAdapter(adapter);

        //设置监听器，实现点击显示效果
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WordObject word=wordList.get(position);
                Toast.makeText(WordDetailActivity.this, "Hi！我是"+word.getText(), Toast.LENGTH_SHORT).show();
                sd.setVisibility(View.VISIBLE);
                word_title_tv.setText(word.getText());
                base_exp_tv.setText(word.getcExp());
                //解释explanation设置适配器
                ExpAdapter expAdapter=new  ExpAdapter(WordDetailActivity.this,R.layout.layout_collect_explanation_item,word.getExp());
                //将适配器与listView连接
                exp_ls.setAdapter(expAdapter);
            }
        });

        sd.setOnTouchListener(new View.OnTouchListener() {
            float startX,startY,moveX,moveY;
            long currentMS;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Toast.makeText(MainActivity.this, "诶呀！  ", Toast.LENGTH_SHORT).show();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();//float DownX
                        startY = event.getY();//float DownY
                        //Toast.makeText(MainActivity.this, "诶呀！Y:"+sd.getTop()+sd.getBottom(), Toast.LENGTH_SHORT).show();
                        moveX = 0;
                        moveY = 0;
                        currentMS = System.currentTimeMillis();//long currentMS     获取系统时间
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (startY<event.getY()&&event.getY()>1520){
                            System.out.println("start: "+startY+"Now: "+event.getY());
                            sd.setVisibility(View.GONE);
                        }
                        //sd.setY(moveY);
                        break;
                    case MotionEvent.ACTION_UP:
                        long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                        //判断是否继续传递信号
                        if(moveTime>200&&(moveX>20||moveY>20)){
                            return true; //不再执行后面的事件，在这句前可写要执行的触摸相关代码。点击事件是发生在触摸弹起后
                        }
                        break;
                }
                return false;
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.word_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}