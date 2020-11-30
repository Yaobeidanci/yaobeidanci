package yaobeidanci.view.collect;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

import yaobeidanci.bean.Explanation;
import yaobeidanci.bean.Word;
import yaobeidanci.view.R;
public class WordDetailActivity extends AppCompatActivity {

    private List<Word> wordList = new ArrayList<>();
    public SlidingDrawer sd;
    public ListView exp_ls;
    public TextView word_title_tv;
    public TextView base_exp_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collect_activity_word_detail);

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

        //初始化解释
        Explanation e1=new Explanation("adj.回收利用；再利用","to treat things that have already been used so that they can be used again");
        w1.addExp(e1);
        w1.addExp(e1);
        w1.addExp(e1);

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
                Word word=wordList.get(position);
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