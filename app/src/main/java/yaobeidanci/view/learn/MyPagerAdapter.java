package yaobeidanci.view.learn;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import yaobeidanci.bean.PhraseObject;
import yaobeidanci.bean.RelateWordObject;
import yaobeidanci.bean.SentenceObject;
import yaobeidanci.bean.WordExplanationObject;
import yaobeidanci.bean.WordObject;
import yaobeidanci.view.R;

import static android.view.View.INVISIBLE;
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
    ViewPager boundView;

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
                    TextView word = viewItem.findViewById(R.id.word);
                    word.setText(wordObject.word);
                    TextView pronunciation = viewItem.findViewById(R.id.pronunciation);
                    pronunciation.setText("[" + wordObject.phonetic_uk + "]" + "["  + wordObject.phonetic_us + "]");
                    LinearLayout linearLayout = viewItem.findViewById(R.id.options);
                    for (int i = 0; i < 4; i++) {
                        Button button = (Button) linearLayout.getChildAt(i);
                        button.setText("ffg");
                        final View finalView = viewItem;
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                View layout = finalView.findViewById(R.id.options);
                                layout.setVisibility(INVISIBLE);
                                TextView answer = finalView.findViewById(R.id.showedAns);
                                answer.setText("the answer");
                                answer.setVisibility(VISIBLE);
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

            SentenceObject sentenceObject = sentenceList.get(position);
            title.setText(sentenceObject.origin_title);
            chinese.setText(sentenceObject.translation);
            english.setText(sentenceObject.sentence);

            if (view != null) {
                container.addView(view);
            }
            return view;
        }
    }
}