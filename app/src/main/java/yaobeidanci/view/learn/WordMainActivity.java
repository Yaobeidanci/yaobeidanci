package yaobeidanci.view.learn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import yaobeidanci.MyUtil;
import yaobeidanci.bean.SentenceObject;
import yaobeidanci.bean.WordObject;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;

public class WordMainActivity extends AppCompatActivity {
    static WordObject wordObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* ********************* *
         * Main
         * ********************* */
        setContentView(R.layout.layout_learn_main_page);

        OuterViewPager outerViewPager = findViewById(R.id.outerPageView);
        VerticalButtonViewPager verticalButtonViewPager = findViewById(R.id.verticalPageView);
        outerViewPager.connected = verticalButtonViewPager;
        verticalButtonViewPager.connected = outerViewPager;
        outerViewPager.setWordObject(wordObject);

        // By default, there are only 2 view remaining in the viewpager, one is show and the other is off screen
        // So set the limit to 2, to make all the 3 page remain
        verticalButtonViewPager.setOffscreenPageLimit(2);
        // Set the initial page to the page in middle
        outerViewPager.setCurrentItem(1);
        verticalButtonViewPager.setCurrentItem(1);
    }

    /**
     * 启动一个新的word页面，并加载单词
     * @param src 调用者
     */
    public static void startIt(final Activity src, final boolean finish_it){
        JSONObject object = new JSONObject();
        try {
            object.put("uid", MyUtil.getUid());
            MyUtil.httpGet(MyUtil.BASE_URL + "/resource/word", object, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    try {
                        JSONObject res = new JSONObject((String) result.data);
                        String word_json = res.getString("data");
                        wordObject = new Gson().fromJson(word_json, WordObject.class);
                        Intent intent = new Intent(src, WordMainActivity.class);
                        src.startActivity(intent);
                        if (finish_it) {
                            src.finish();
                        }
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
//
//        JSONObject object = new JSONObject();
//        try {
//            object.put("uid", "266c0c9fc2446658333fb249d10e3cdf");
//            MyUtil.httpGet(MyUtil.BASE_URL + "/resource/starWords", object, new MyUtil.MyCallback() {
//                @Override
//                public void onSuccess(Object result) {
//                    try {
//                        JSONObject res = new JSONObject((String) result);
//                        String word_json = res.getString("data");
//                        List<WordObject> myConvertList = new Gson().fromJson(word_json, new TypeToken<List<WordObject>>() {}.getType());
//                        Log.d("hahaha", "onSuccess: ");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(Object result) {
//
//                }
//            },true);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

}