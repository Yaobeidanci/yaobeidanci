package yaobeidanci.view.collect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class SentenceDetailActivity extends AppCompatActivity {
    private static List<SentenceObject> sList=new ArrayList<>();

    /**
     * 启动一个新的word页面，并加载单词
     * @param src 调用者
     */
    public static void startIt(final Activity src){
        final JSONObject object = new JSONObject();

        try {
            object.put("uid", MyUtil.getUid());
            MyUtil.httpGet(MyUtil.BASE_URL + "/resource/starSentences", object, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    try {
                        JSONObject res = new JSONObject((String) result.data);
                        String words_json = res.getString("data");
                        sList = new Gson().fromJson(words_json, new TypeToken<List<SentenceObject>>(){}.getType());
                        Intent intent = new Intent(src, SentenceDetailActivity.class);
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
                Toast.makeText(SentenceDetailActivity.this, "Hi！我是"+s.getSentence_id(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}