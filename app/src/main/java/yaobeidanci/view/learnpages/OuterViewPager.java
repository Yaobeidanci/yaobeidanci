package yaobeidanci.view.learnpages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import yaobeidanci.bean.WordBean;

/**
 * The outer view of the word view
 */
public class OuterViewPager extends ViewPager {
    Context context;
    WordBean wordBean;
    VerticalButtonViewPager connected;
    MyPagerAdapter.OuterViewPagerAdapter outerViewPagerAdapter;

    // when click the answer
    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
        }
    };

    public OuterViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.outerViewPagerAdapter = new MyPagerAdapter.OuterViewPagerAdapter(context, this);
        this.setAdapter(outerViewPagerAdapter);

        // set the special animation
        this.setPageTransformer(false, new PageTransformer() {
            private static final float MIN_SCALE = 0.9f;
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();

                MyPagerAdapter.OuterViewPagerAdapter adapter = (MyPagerAdapter.OuterViewPagerAdapter) getAdapter();
                if (adapter != null) {
                    if (view == adapter.mainPage) {
                        float scaleFactor = MIN_SCALE
                                + (1 - MIN_SCALE) * (1 - Math.abs(position));
                        view.setScaleX(scaleFactor);
                        view.setScaleY(scaleFactor);
                        view.setTranslationX(-pageWidth * position);
                        view.setAlpha(1 - Math.abs(position));
//                        Log.d("ma", "+" + position);
                    }
                }
            }
        });
    }

    /**
     * Sync the scroll of this view pager and the button view pager
     * @param position
     * @param offset
     * @param offsetPixels
     */
    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        // notice: the scrollX/Y is relative to the initial item, which means if the left is the
        // initial page, setScrollY(height) will scroll to the main page. If the main page is the
        // initial page, it will scroll to the right page
        if (connected != null) {
            int pos = this.getCurrentItem();
            // exclude effect of the near end number
            if (Math.abs(offset-0)>1e-2&&Math.abs(offset-1)<1-1e-2){
                switch (pos){
                    case 0:
                        // at the left page, scroll to the main page
                        connected.setScrollY((int) (connected.getHeight()*(offset-1)));
                        break;
                    case 1:
                        // at the main page
                        if (position==0) {
                            // scroll to the left page
                            connected.setScrollY((int) (connected.getHeight()*(offset-1)));
                        }else if (position==1) {
                            // scroll to the right page
                            connected.setScrollY((int) (connected.getHeight()*(offset)));
                        }
                        break;
                    case 2:
                        // at the right page, scroll to the main page
                        connected.setScrollY((int) (connected.getHeight()*(offset)));
                        break;
                }
            }

        }
    }

    /**
     * Receive the word data
     * @param wordBean
     */
    public void setWordBean(WordBean wordBean) {
        this.wordBean = wordBean;
        this.outerViewPagerAdapter.setWordBean(wordBean);
    }
}
