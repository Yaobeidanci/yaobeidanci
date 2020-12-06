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

import java.util.List;

import yaobeidanci.bean.WordBean;
import yaobeidanci.view.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * The base class of adapters
 */
public class MyPagerAdapter extends PagerAdapter {
    // The data to be loaded
    protected WordBean wordBean;
    // The context
    protected Context mContext;
    // The view pager bound to the adapter
    ViewPager boundView;

    public MyPagerAdapter(Context mContext, ViewPager boundView) {
        this.mContext = mContext;
        this.boundView = boundView;
    }

    public void setWordBean(WordBean wordBean) {
        this.wordBean = wordBean;
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
                    cardViewPagerAdapter.setWordBean(wordBean);
                    // bind the indicator with the view pager, it may not be a good choice to use a static variable
                    VerticalButtonViewPager.leftTitlePageIndicator.setViewPager(viewPager);
                    break;
                // right view, load the card view pager with type 1 and example sentences data
                case 2:
                    viewItem = View.inflate(mContext, R.layout.layout_learn_word_aside_page, null);
                    viewPager = viewItem.findViewById(R.id.asideCardContainer);
                    cardViewPagerAdapter = new CardViewPagerAdapter(mContext, viewPager, CardViewPagerAdapter.TYPE.RIGHT);
                    viewPager.setAdapter(cardViewPagerAdapter);
                    cardViewPagerAdapter.setWordBean(wordBean);
                    // bind the indicator with the view pager, it may not be a good choice to use a static variable
                    VerticalButtonViewPager.rightTitlePageIndicator.setViewPager(viewPager);
                    break;
                // main view, load the word and options data
                case 1:
                    viewItem = View.inflate(mContext, R.layout.layout_learn_word_main_page, null);
                    TextView word = viewItem.findViewById(R.id.word);
                    word.setText(wordBean.word);
                    TextView pronunciation = viewItem.findViewById(R.id.pronunciation);
                    pronunciation.setText(wordBean.pronunciation);
                    LinearLayout linearLayout = viewItem.findViewById(R.id.options);
                    for (int i = 0; i < 4; i++) {
                        Button button = (Button) linearLayout.getChildAt(i);
                        button.setText(wordBean.explains[i]);
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

        List<WordBean.WordDetail> list;

        enum TYPE {LEFT, RIGHT}

        TYPE type;

        public CardViewPagerAdapter(Context mContext, ViewPager boundView, TYPE type) {
            super(mContext, boundView);
            this.type = type;
        }

        @Override
        public void setWordBean(WordBean wordBean) {
            super.setWordBean(wordBean);
            if (this.type == TYPE.LEFT) {
                this.list = wordBean.leftList;
            } else if (this.type == TYPE.RIGHT) {
                this.list = wordBean.rightList;
            }
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "title";
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            WordBean.WordDetail detail = list.get(position);
            View view = null;
            // for the left cards
            if (this.type == TYPE.LEFT) {
                view = View.inflate(mContext, R.layout.layout_learn_word_card_left, null);
                // word root
                if (detail instanceof WordBean.RootDetail) {
                    WordBean.RootDetail rootDetail = (WordBean.RootDetail) detail;
                    TextView word = view.findViewById(R.id.wordText);
                    word.setText(rootDetail.word);
                    TextView pronun = view.findViewById(R.id.pronunciationText);
                    pronun.setText(rootDetail.pronunciation);
                    TextView root = view.findViewById(R.id.wordrootText);
                    root.setText(rootDetail.root);
                }
                // for the right cards, load the example sentences card and the explain
            } else if (this.type == TYPE.RIGHT) {
                view = View.inflate(mContext, R.layout.layout_learn_word_card_right, null);
                ViewPager viewPager = view.findViewById(R.id.exampleContainer);
                CirclePageIndicator circlePageIndicator = view.findViewById(R.id.exampleIndicator);
                WordBean.ExampleSentences sentences = (WordBean.ExampleSentences) detail;
                ExampleViewPagerAdapter exampleViewPagerAdapter = new ExampleViewPagerAdapter(mContext, viewPager);
                viewPager.setAdapter(exampleViewPagerAdapter);
                exampleViewPagerAdapter.setList(sentences.list);
                circlePageIndicator.setViewPager(viewPager);

                TextView explain = view.findViewById(R.id.exampleExplain);
                explain.setText(sentences.explain);

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
        List<WordBean.ExampleItem> list;

        public ExampleViewPagerAdapter(Context mContext, ViewPager boundView) {
            super(mContext, boundView);
        }

        public void setList(List<WordBean.ExampleItem> list) {
            this.list = list;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = null;
            view = View.inflate(mContext, R.layout.layout_learn_word_example_item, null);
            TextView title = view.findViewById(R.id.title);
            TextView chinese = view.findViewById(R.id.chinese);
            TextView english = view.findViewById(R.id.english);

            WordBean.ExampleItem item = list.get(position);
            title.setText(item.title);
            chinese.setText(item.sentenceChinese);
            english.setText(item.sentence);

            if (view != null) {
                container.addView(view);
            }
            return view;
        }
    }
}