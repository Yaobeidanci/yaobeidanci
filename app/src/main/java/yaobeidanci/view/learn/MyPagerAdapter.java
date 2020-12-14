package yaobeidanci.view.learn;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import yaobeidanci.MyUtil;
import yaobeidanci.bean.PhraseObject;
import yaobeidanci.bean.RelateWordObject;
import yaobeidanci.bean.SentenceObject;
import yaobeidanci.bean.WordExplanationObject;
import yaobeidanci.bean.WordObject;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;

import static android.view.View.VISIBLE;

/**
 * The base class of adapters
 */
public class MyPagerAdapter extends PagerAdapter {
    // The data to be loaded
    protected WordObject wordObject;
    // The context
    protected Context mContext;
    // The view pager bound to the adapter
    protected ViewPager boundView;

    public MyPagerAdapter(Context mContext, ViewPager boundView) {
        this.mContext = mContext;
        this.boundView = boundView;
    }

    public void setWordObject(WordObject wordObject) {
        this.wordObject = wordObject;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * Manage the three function area, left, main and right
     */
    public static class OuterViewPagerAdapter extends MyPagerAdapter {
        View mainPage;
        LinearLayout optionLayout = null;
        TextView answer = null;

        /**
         * 显示答案
         * @param selectBt 被选择的按钮
         * @param correctAns 正确答案
         */
        public void showAnswer(Button selectBt, final String correctAns){
            // 处理底栏
            OuterViewPager pager = (OuterViewPager) boundView;
            VerticalButtonViewPager verticalButtonViewPager = pager.connected;
            verticalButtonViewPager.showTheAns.setVisibility(View.GONE);
            verticalButtonViewPager.afterAns.setVisibility(VISIBLE);

            // 处理逻辑
            Button correctBt = null;
            for(int i=0;i<4;i++){
                Button bt = (Button) optionLayout.getChildAt(i);
                if (bt.getText().toString().equals(correctAns)){
                    correctBt=bt;
                    break;
                }
            }
            int result;
            if (selectBt==null){
                result = 0;
            }else if(selectBt!=correctBt){
                MyUtil.playAudio("https://downsc.chinaz.net/Files/DownLoad/sound1/201907/11750.mp3");
                result = 1;
            }else {
                MyUtil.playAudio("https://downsc.chinaz.net/Files/DownLoad/sound1/202011/13562.mp3");
                result=1;
            }

            JSONObject object = new JSONObject();
            try {
                object.put("uid", MyUtil.getUid());
                object.put("word_id", wordObject.word_id);
                object.put("mode", 1);
                object.put("result", result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MyUtil.httpGet(MyUtil.BASE_URL + "/api/setResult", object, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    Log.d("net-", "onSuccess: " + (String)result.data);
                }

                @Override
                public void onError(MyUtil.Res result) {
                    Toast.makeText(MainActivity.getContext(), result.msg, Toast.LENGTH_SHORT).show();
                }
            }, true);

            if (selectBt!=null){
                if (correctBt!=selectBt){
                    selectBt.setBackgroundColor(Color.rgb(0xff, 0x33, 0x33));
                }
            }
            if (correctBt!=null){
                correctBt.setBackgroundColor(Color.rgb(0x00, 0xff, 0x33));
            }


            // 得用 handler 作延时，其他线程无法操作UI
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // GONE 不占空间
                    optionLayout.setVisibility(View.GONE);
                    answer.setText(correctAns);
                    answer.setVisibility(VISIBLE);
                }
            };
            handler.postDelayed(runnable, 1000);
        }

        public OuterViewPagerAdapter(Context mContext, ViewPager boundView) {
            super(mContext, boundView);
        }


        @Override
        public int getCount() {
            return 3;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View viewItem = null;
            CardViewPagerAdapter cardViewPagerAdapter;
            ViewPager viewPager;
            // left view, main view and right view
            switch (position) {
                // left view, load the card view pager with type 0 and word detail data
                case 0:
                    viewItem = View.inflate(mContext, R.layout.layout_learn_word_aside_page, null);
                    viewPager = viewItem.findViewById(R.id.asideCardContainer);
                    cardViewPagerAdapter = new CardViewPagerAdapter(mContext, viewPager, CardViewPagerAdapter.TYPE.LEFT);
                    viewPager.setAdapter(cardViewPagerAdapter);
                    cardViewPagerAdapter.setWordObject(wordObject);
                    // bind the indicator with the view pager, it may not be a good choice to use a static variable
                    VerticalButtonViewPager.leftTitlePageIndicator.setViewPager(viewPager);
                    break;
                // right view, load the card view pager with type 1 and example sentences data
                case 2:
                    viewItem = View.inflate(mContext, R.layout.layout_learn_word_aside_page, null);
                    viewPager = viewItem.findViewById(R.id.asideCardContainer);
                    cardViewPagerAdapter = new CardViewPagerAdapter(mContext, viewPager, CardViewPagerAdapter.TYPE.RIGHT);
                    viewPager.setAdapter(cardViewPagerAdapter);
                    cardViewPagerAdapter.setWordObject(wordObject);
                    // bind the indicator with the view pager, it may not be a good choice to use a static variable
                    VerticalButtonViewPager.rightTitlePageIndicator.setViewPager(viewPager);
                    break;
                // main view, load the word and options data
                case 1:
                    viewItem = View.inflate(mContext, R.layout.layout_learn_word_main_page, null);
                    final TextView word = viewItem.findViewById(R.id.word);
                    word.setText(wordObject.word);
                    TextView phonetic_uk_text = viewItem.findViewById(R.id.phonetic_uk_text);
                    phonetic_uk_text.setText("[" + wordObject.phonetic_uk + "]");
                    TextView phonetic_us_text = viewItem.findViewById(R.id.phonetic_us_text);
                    phonetic_us_text.setText("["  + wordObject.phonetic_us + "]");
                    optionLayout = viewItem.findViewById(R.id.options);
                    answer = viewItem.findViewById(R.id.showedAns);

                    // 音频按钮
                    ImageView phonetic_uk_bt = viewItem.findViewById(R.id.phonetic_uk_bt);
                    phonetic_uk_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = "/resource/voice?type=0&word=" + wordObject.word;
                            MyUtil.playAudio(MyUtil.BASE_URL + url);
                        }
                    });
                    ImageView phonetic_us_bt = viewItem.findViewById(R.id.phonetic_us_bt);
                    phonetic_us_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = "/resource/voice?type=1&word=" + wordObject.word;
                            MyUtil.playAudio(MyUtil.BASE_URL + url);
                        }
                    });

                    // 星标按钮
                    final ImageView star_bt = viewItem.findViewById(R.id.star_bt);

                    star_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            JSONObject object = new JSONObject();
                            try {
                                object.put("uid", MyUtil.getUid());
                                object.put("word_id", wordObject.word_id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            MyUtil.httpGet(MyUtil.BASE_URL + "/api/addStarWord", object, new MyUtil.MyCallback() {
                                @Override
                                public void onSuccess(MyUtil.Res result) {
                                    Toast.makeText(mContext, "星标成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(MyUtil.Res result) {
                                    Toast.makeText(MainActivity.getContext(), result.msg, Toast.LENGTH_SHORT).show();
                                }
                            }, true);
                        }
                    });


                    // 问题按钮
                    for (int i = 0; i < 4; i++) {
                        Button button = (Button) optionLayout.getChildAt(i);
                        WordExplanationObject explanationObject = wordObject.questions.get(i);
                        button.setText(explanationObject.prop + ". " + explanationObject.explain_c);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Button bt = (Button) v;
                                WordExplanationObject correct = wordObject.explains.get(0);
                                showAnswer(bt, correct.prop + ". " + correct.explain_c);
                            }
                        });

                    }
                    mainPage = viewItem;
                    break;
                default:
                    break;
            }
            if (viewItem != null) {
                container.addView(viewItem);
            }
            return viewItem;
        }
    }


    /**
     * Manage the left or right cards
     */
    public static class CardViewPagerAdapter extends MyPagerAdapter {

        List<Object> itemList;
        List<String> titleList;

        enum TYPE {LEFT, RIGHT}

        TYPE type;

        public CardViewPagerAdapter(Context mContext, ViewPager boundView, TYPE type) {
            super(mContext, boundView);
            this.type = type;
        }

        @Override
        public void setWordObject(WordObject wordObject) {
            super.setWordObject(wordObject);
            if (this.type == TYPE.LEFT) {
                this.itemList = new ArrayList<>();
                this.itemList.add(wordObject.phrases);
                this.itemList.add(wordObject.relate_words);
                this.titleList = Arrays.asList(wordObject.phrases_label,wordObject.relate_words_label);
            } else if (this.type == TYPE.RIGHT) {
                this.itemList = new ArrayList<>();
                this.itemList.addAll(wordObject.explains);
                this.titleList = Collections.singletonList("释义");
            }
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return itemList == null ? 0 : itemList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (type == TYPE.LEFT) {
                return this.titleList.get(position);
            }else {
                return this.titleList.get(0);
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = null;
            // for the left cards
            if (this.type == TYPE.LEFT) {
                view = View.inflate(mContext, R.layout.layout_learn_word_card_left, null);
                LinearLayout linearLayout = view.findViewById(R.id.detail_content);

                TextView word = view.findViewById(R.id.wordText);
                word.setText(wordObject.word);
                TextView pronun = view.findViewById(R.id.pronunciationText);
                pronun.setText("[" + wordObject.phonetic_uk + "]" + "["  + wordObject.phonetic_us + "]");
                if (position == 0) {
                    // word related
                    for (PhraseObject phraseObject : wordObject.phrases){
                        View item = View.inflate(mContext, R.layout.layout_learn_linear_item, null);
                        TextView word0 = item.findViewById(R.id.word);
                        TextView prop0 = item.findViewById(R.id.prop);
                        TextView explain0 = item.findViewById(R.id.explain);
                        word0.setText(phraseObject.phrase);
                        explain0.setText(phraseObject.explain);
                        linearLayout.addView(item);
                    }
                } else if(position == 1) {
                    // word similar
                    for (RelateWordObject relateWordObject : wordObject.relate_words){
                        View item = View.inflate(mContext, R.layout.layout_learn_linear_item, null);
                        TextView word0 = item.findViewById(R.id.word);
                        TextView prop0 = item.findViewById(R.id.prop);
                        TextView explain0 = item.findViewById(R.id.explain);
                        word0.setText(relateWordObject.word);
                        prop0.setText(relateWordObject.prop);
                        explain0.setText(relateWordObject.explain);
                        linearLayout.addView(item);
                    }
                }
                // for the right cards, load the example sentences card and the explain
            } else if (this.type == TYPE.RIGHT) {
                view = View.inflate(mContext, R.layout.layout_learn_word_card_right, null);
                ViewPager viewPager = view.findViewById(R.id.exampleContainer);
                CirclePageIndicator circlePageIndicator = view.findViewById(R.id.exampleIndicator);
                ExampleViewPagerAdapter exampleViewPagerAdapter = new ExampleViewPagerAdapter(mContext, viewPager);
                viewPager.setAdapter(exampleViewPagerAdapter);
                exampleViewPagerAdapter.setSentenceList(wordObject.sentences);
                circlePageIndicator.setViewPager(viewPager);

                WordExplanationObject explainObject = wordObject.explains.get(position);
                TextView explain_c = view.findViewById(R.id.explain_c);
                TextView explain_e = view.findViewById(R.id.explain_e);
                explain_c.setText(explainObject.prop + ". " + explainObject.explain_c);
                explain_e.setText(explainObject.explain_e);
            }

            if (view != null) {
                container.addView(view);
            }
            return view;
        }
    }

    /**
     * The adapter of the example sentences view
     */
    public static class ExampleViewPagerAdapter extends MyPagerAdapter {
        List<SentenceObject> sentenceList;

        public ExampleViewPagerAdapter(Context mContext, ViewPager boundView) {
            super(mContext, boundView);
        }

        public void setSentenceList(List<SentenceObject> sentenceList) {
            this.sentenceList = sentenceList;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return sentenceList == null ? 0 : sentenceList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = null;
            view = View.inflate(mContext, R.layout.layout_learn_word_example_item, null);
            TextView title = view.findViewById(R.id.title);
            TextView chinese = view.findViewById(R.id.chinese);
            TextView english = view.findViewById(R.id.english);

            final SentenceObject sentenceObject = sentenceList.get(position);
            title.setText(sentenceObject.origin_title);
            chinese.setText(sentenceObject.translation);
            english.setText(sentenceObject.sentence);

            ImageView star_bt = view.findViewById(R.id.star_bt);
            star_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("uid", MyUtil.getUid());
                        object.put("sentence_id", sentenceObject.sentence_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MyUtil.httpGet(MyUtil.BASE_URL + "/api/addStarSentence", object, new MyUtil.MyCallback() {
                        @Override
                        public void onSuccess(MyUtil.Res result) {
                            Toast.makeText(mContext, "星标成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(MyUtil.Res result) {
                            Toast.makeText(MainActivity.getContext(), result.msg, Toast.LENGTH_SHORT).show();
                        }
                    }, true);
                }
            });

            if (view != null) {
                container.addView(view);
            }
            return view;
        }
    }
}