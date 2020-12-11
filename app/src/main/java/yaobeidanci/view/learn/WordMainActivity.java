package yaobeidanci.view.learn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import yaobeidanci.MyUtil;
import yaobeidanci.bean.WordObject;
import yaobeidanci.view.R;

public class WordMainActivity extends AppCompatActivity {
    static JSONArray jsonArray;
    static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("word main", "onCreate: ");

        WordObject wordObject = getWordObject();

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

    public static WordObject getWordObject(){
        String word_json = null;
        WordObject wordObject = null;
        try {
            word_json = jsonArray.getString(index);
            index++;
            if (index==jsonArray.length()){
                index=0;
            }
            wordObject = new Gson().fromJson(word_json, WordObject.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wordObject;
    }

    public static void startIt(Activity src){
        String allData = MyUtil.readFile(src, "word.json");
        Log.d("qwe", "startIt: " + allData);
        try {
            jsonArray = new JSONArray(allData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /* ***************
         * Logic
         * ***************/
        Intent intent = new Intent(src, WordMainActivity.class);
//        intent.putExtra("word", wordBean);
        src.startActivity(intent);
    }

}