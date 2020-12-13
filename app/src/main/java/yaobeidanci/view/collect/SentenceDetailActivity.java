package yaobeidanci.view.collect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import yaobeidanci.bean.SentenceObject;
import yaobeidanci.view.R;

public class SentenceDetailActivity extends AppCompatActivity {
    private List<SentenceObject> sList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collect_activity_sentence_detail);

        //初始化句子数据


        //创建自定义适配器，并且与数据entryList连接
        SentenceDetailAdapter adapter=new SentenceDetailAdapter(SentenceDetailActivity.this,R.layout.layout_collect_sentence_detail_item,sList);

        //将适配器与listView连接
        ListView listView =findViewById(R.id.sentence_detail_ls);
        listView.setAdapter(adapter);

        //设置监听器，实现点击显示效果
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SentenceObject s=sList.get(position);
                Toast.makeText(SentenceDetailActivity.this, "Hi！我是"+s.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}