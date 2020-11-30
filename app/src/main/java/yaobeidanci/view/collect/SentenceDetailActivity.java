package yaobeidanci.view.collect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yaobeidanci.bean.Sentence;
import yaobeidanci.view.R;

public class SentenceDetailActivity extends AppCompatActivity {
    private List<Sentence> sList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collect_activity_sentence_detail);

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

        //创建自定义适配器，并且与数据entryList连接
        SentenceDetailAdapter adapter=new SentenceDetailAdapter(SentenceDetailActivity.this,R.layout.layout_collect_sentence_detail_item,sList);

        //将适配器与listView连接
        ListView listView =findViewById(R.id.sentence_detail_ls);
        listView.setAdapter(adapter);

        //设置监听器，实现点击显示效果
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sentence s=sList.get(position);
                Toast.makeText(SentenceDetailActivity.this, "Hi！我是"+s.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}