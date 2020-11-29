package yaobeidanci.view.learnpages;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import yaobeidanci.view.R;

public class VerticalButtonViewPager extends VerticalViewPager {
    View menu_1;
    Context context;
    OuterViewPager connected;
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
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = null;
            // the menu in main view, and the menu in left and right view
            switch (position){
                case 0:
                    view = View.inflate(context, R.layout.layout_menu_1, null);
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
                    view = View.inflate(context, R.layout.layout_menu_2, null);
                    ImageButton backBt = view.findViewById(R.id.backBt);
                    ImageButton editBt = view.findViewById(R.id.editBt);
                    ImageButton chatBt = view.findViewById(R.id.chatBt);
                    ImageButton deleteBt = view.findViewById(R.id.deleteBt);

                    TextView showTheAns = view.findViewById(R.id.showTheAns);
                    showTheAns.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    backBt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click the back", Toast.LENGTH_SHORT).show();
                        }
                    });

                    editBt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "click the edit", Toast.LENGTH_SHORT).show();
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
                    view = View.inflate(context, R.layout.layout_menu_3, null);
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
            if (view!=null) {
                container.addView(view);
            }
            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }
}
