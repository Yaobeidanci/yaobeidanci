package yaobeidanci.view.collect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yaobeidanci.bean.Explanation;
import yaobeidanci.bean.Sentence;
import yaobeidanci.bean.Word;
import yaobeidanci.view.R;

public class CollectMainActivity extends AppCompatActivity {

    private List<Word> wordList = new ArrayList<>();
    private List<Sentence> sList=new ArrayList<>();
    public SlidingDrawer sd;
    public ListView exp_ls;
    public TextView word_title_tv;
    public TextView base_exp_tv;
    RecyclerView word_rv;
    RecyclerView sentence_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collect_activity_main);
        //初始化单词数据
        Word w1 = new Word("recycle");
        wordList.add(w1);
        Word w2 = new Word("banana");
        wordList.add(w2);
        Word w3 = new Word("cat");
        wordList.add(w3);
        Word w4 = new Word("pluralism");
        wordList.add(w4);
        Word w5 = new Word("hahahahahahhaha");
        wordList.add(w5);
        Word w6 = new Word("hahahahahahhaha");
        wordList.add(w6);
        Word w7 = new Word("hahahahahahhaha");
        wordList.add(w7);

        //初始化句子数据
        Sentence s1 =new Sentence("She uses accomplices, vagrants that she pays off if they'll do a little jail time.",
                "疑犯追踪 第三季","1",R.drawable.a,R.drawable.yfzz);
        sList.add(s1);
        Sentence s2 =new Sentence("She uses accomplices, vagrants that she pays off if they'll do a little jail time.",
                "疑犯追踪 第三季","2",R.drawable.b,R.drawable.yfzz);
        sList.add(s2);
        Sentence s3 =new Sentence("She uses accomplices, vagrants that she pays off if they'll do a little jail time.",
                "疑犯追踪 第三季","3",R.drawable.c,R.drawable.yfzz);
        sList.add(s3);

        //初始化解释
        Explanation e1=new Explanation("adj.回收利用；再利用","to treat things that have already been used so that they can be used again");
        w1.addExp(e1);
        w1.addExp(e1);
        w1.addExp(e1);

        //获取布局
        exp_ls = findViewById(R.id.exp_detail_ls);
        sd=findViewById(R.id.drawer);
        word_title_tv=findViewById(R.id.word_title);
        base_exp_tv=findViewById(R.id.base_exp);

        //卡片word设置适配器
        word_rv = (RecyclerView) findViewById(R.id.word_view);
        //recyclerView.addItemDecoration(new StaggeredDividerItemDecoration(this, 2));
        StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(wordList.size(), StaggeredGridLayoutManager.VERTICAL);
        word_rv.setLayoutManager(layoutManager1);
        WordAdapter adapter1 = new WordAdapter(this,wordList);
        word_rv.setAdapter(adapter1);

        //卡片sentence设置适配器
        sentence_rv = (RecyclerView) findViewById(R.id.sentence_view);
        StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(sList.size(), StaggeredGridLayoutManager.VERTICAL);
        sentence_rv.setLayoutManager(layoutManager2);
        SentenceAdapter adapter2 = new SentenceAdapter(sList);
        sentence_rv.setAdapter(adapter2);

        TextView detail_tv1=(TextView) findViewById(R.id.word_detail_tv);
        TextView detail_tv2=(TextView) findViewById(R.id.sentence_detail_tv);

        detail_tv1.setText("共有"+adapter1.getItemCount()+"词");
        detail_tv2.setText("共有"+adapter2.getItemCount()+"句");

        detail_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CollectMainActivity.this,WordDetailActivity.class);
                startActivity(intent);
            }
        });

        detail_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CollectMainActivity.this,SentenceDetailActivity.class);
                startActivity(intent);
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