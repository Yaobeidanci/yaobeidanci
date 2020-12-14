package yaobeidanci.view.collect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
import yaobeidanci.view.learn.WordMainActivity;

public class CollectMainActivity extends AppCompatActivity {

    private static List<WordObject> wordList = new ArrayList<>();
    private static List<SentenceObject> sList=new ArrayList<>();
    public SlidingDrawer sd;
    public ListView exp_ls;
    public TextView word_title_tv;
    public TextView base_exp_tv;
    RecyclerView word_rv;
    RecyclerView sentence_rv;
    CardView word_tips;
    CardView sentence_tips;

    /**
     * 启动一个新的word页面，并加载单词
     * @param src 调用者
     */
    public static void startIt(final Activity src){
        final JSONObject object1 = new JSONObject();
        final JSONObject object2 = new JSONObject();
        try {
            object1.put("uid", MyUtil.getUid());
            MyUtil.httpGet(MyUtil.BASE_URL + "/resource/starWords", object1, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    try {
                        JSONObject res = new JSONObject((String) result.data);
                        String words_json = res.getString("data");
                        wordList = new Gson().fromJson(words_json, new TypeToken<List<WordObject>>(){}.getType());
                        Intent intent = new Intent(src, CollectMainActivity.class);
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

        try {
            object2.put("uid", MyUtil.getUid());
            MyUtil.httpGet(MyUtil.BASE_URL + "/resource/starSentences", object2, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    try {
                        JSONObject res = new JSONObject((String) result.data);
                        String sentences_json = res.getString("data");
                        sList = new Gson().fromJson(sentences_json, new TypeToken<List<SentenceObject>>(){}.getType());
                        Intent intent = new Intent(src, CollectMainActivity.class);
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
        setContentView(R.layout.layout_collect_activity_main);

        //获取布局
        exp_ls = findViewById(R.id.exp_detail_ls);
        sd=findViewById(R.id.drawer);
        word_title_tv=findViewById(R.id.word_title);
        base_exp_tv=findViewById(R.id.base_exp);
        word_tips=findViewById(R.id.none_word_tips);
        sentence_tips=findViewById(R.id.none_sentence_tips);
        TextView detail_tv1=(TextView) findViewById(R.id.word_detail_tv);
        TextView detail_tv2=(TextView) findViewById(R.id.sentence_detail_tv);

        //卡片word设置适配器
        wordList.clear();
        if (wordList.size()!=0){
            word_rv = (RecyclerView) findViewById(R.id.word_view);
            word_rv.setVisibility(View.VISIBLE);
            word_tips.setVisibility(View.GONE);
            //recyclerView.addItemDecoration(new StaggeredDividerItemDecoration(this, 2));
            StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(wordList.size(), StaggeredGridLayoutManager.VERTICAL);
            word_rv.setLayoutManager(layoutManager1);
            WordAdapter adapter1 = new WordAdapter(this,wordList);
            word_rv.setAdapter(adapter1);
            detail_tv1.setClickable(true);
        }else {
            detail_tv1.setClickable(false);
        }

        //卡片sentence设置适配器
        sList.clear();
        if (sList.size()!=0){
            sentence_rv = (RecyclerView) findViewById(R.id.sentence_view);
            sentence_rv.setVisibility(View.VISIBLE);
            sentence_tips.setVisibility(View.GONE);
            StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(sList.size(), StaggeredGridLayoutManager.VERTICAL);
            sentence_rv.setLayoutManager(layoutManager2);
            SentenceAdapter adapter2 = new SentenceAdapter(sList);
            sentence_rv.setAdapter(adapter2);
            detail_tv2.setClickable(true);
        }else {
            detail_tv2.setClickable(false);

        }

        detail_tv1.setText("共有"+wordList.size()+"词");
        detail_tv2.setText("共有"+sList.size()+"句");

        detail_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordDetailActivity.startIt(CollectMainActivity.this);
            }
        });

        detail_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SentenceDetailActivity.startIt(CollectMainActivity.this);
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
                        if (startY<event.getY()){
                            if (sd.isOpened()){
                                if (event.getY()>1520) {
                                    System.out.println("start: " + startY + "Now: " + event.getY());
                                    sd.setVisibility(View.GONE);
                                }
                            }else {
                                System.out.println("start: "+startY+"Now: "+event.getY());
                                sd.setVisibility(View.GONE);
                            }
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

}