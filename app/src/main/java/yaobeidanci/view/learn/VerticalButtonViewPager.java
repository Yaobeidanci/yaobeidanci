package yaobeidanci.view.learn;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityManagerCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.viewpagerindicator.TitlePageIndicator;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import yaobeidanci.bean.WordExplanationObject;
import yaobeidanci.view.R;

public class VerticalButtonViewPager extends VerticalViewPager {
    Context context;
    OuterViewPager connected;
    // use static variables may not be a good choice
    static TitlePageIndicator leftTitlePageIndicator;
    static TitlePageIndicator rightTitlePageIndicator;

    TextView showTheAns;
    View afterAns;

    public VerticalButtonViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setAdapter(new VerticalButtonViewPagerAdapter());
    }

    public class VerticalButtonViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = null;
            // the menu in main view, and the menu in left and right view
            switch (position) {
                case 0:
                    view = View.inflate(context, R.layout.layout_learn_menu_1, null);

                    // initial the color of the indicator
                    leftTitlePageIndicator = view.findViewById(R.id.leftPageIndicator);
                    leftTitlePageIndicator.setTextColor(Color.rgb(100, 100, 100));
                    leftTitlePageIndicator.setSelectedColor(Color.RED);

                    Button button1 = view.findViewById(R.id.bt1);
                    Button button2 = view.findViewById(R.id.bt2);
                    button1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click the bt1", Toast.LENGTH_SHORT).show();
                            connected.setCurrentItem(2);
                        }
                    });
                    button2.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click the bt2", Toast.LENGTH_SHORT).show();
                            connected.setCurrentItem(1);
                        }
                    });
                    break;
                case 1:
                    Log.d("ind", "instantiateItem: ");
                    view = View.inflate(context, R.layout.layout_learn_menu_2, null);
                    ImageButton backBt = view.findViewById(R.id.backBt);
                    ImageButton editBt = view.findViewById(R.id.editBt);
                    ImageButton chatBt = view.findViewById(R.id.chatBt);
                    ImageButton deleteBt = view.findViewById(R.id.deleteBt);

                    Button nextBt = view.findViewById(R.id.nextWord);
                    nextBt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Activity activity = (Activity) context;
                            WordMainActivity.startIt(activity);
                            activity.finish();
                        }
                    });

                    showTheAns = view.findViewById(R.id.showTheAns);
                    afterAns = view.findViewById(R.id.afterAns);
                    showTheAns.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyPagerAdapter.OuterViewPagerAdapter connectedAdapter = (MyPagerAdapter.OuterViewPagerAdapter) connected.getAdapter();
                            WordExplanationObject correct = connected.wordObject.explains.get(0);
                            connectedAdapter.showAnswer(null, correct.prop + ". " + correct.explain_c);
                        }
                    });

                    backBt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click the back", Toast.LENGTH_SHORT).show();
                            Activity activity = (Activity) context;
                            activity.finish();
                        }
                    });

                    editBt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(context, "click the edit", Toast.LENGTH_SHORT).show();
                        }
                    });

                    chatBt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click the chat", Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteBt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click the delete", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case 2:
                    view = View.inflate(context, R.layout.layout_learn_menu_3, null);
                    // initial the color of the indicator
                    rightTitlePageIndicator = view.findViewById(R.id.rightPageIndicator);
                    rightTitlePageIndicator.setTextColor(Color.rgb(100, 100, 100));
                    rightTitlePageIndicator.setSelectedColor(Color.RED);

                    Button button3 = view.findViewById(R.id.bt1);
                    Button button4 = view.findViewById(R.id.bt2);
                    button3.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click the bt1", Toast.LENGTH_SHORT).show();
                            connected.setCurrentItem(0);
                        }
                    });
                    button4.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click the bt2", Toast.LENGTH_SHORT).show();
                            connected.setCurrentItem(1);
                        }
                    });
                    break;
                default:
                    break;
            }
            if (view != null) {
                container.addView(view);
            }
            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }
}
