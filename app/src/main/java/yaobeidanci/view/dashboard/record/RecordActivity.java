package yaobeidanci.view.dashboard.record;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yaobeidanci.view.R;

public class RecordActivity extends AppCompatActivity {
    private static final String TAG = "MESSAGE";
    private Button button_return = null;
    private RadioGroup radioGroup = null;
    private Button button_edit = null;
    private ReviewingWordListFragment reviewingWordListFragment;
    private MasteredWordListFragment masteredWordListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard_record);

        FindViews();
        SetListeners();
        InitFragments();
    }

    private void FindViews() {
        button_return = findViewById(R.id.button_return);
        button_edit = findViewById(R.id.button_edit);
        radioGroup = findViewById(R.id.radioGroup);
    }

    private void SetListeners() {
        button_return.setOnClickListener(onClickListener);
        button_edit.setOnClickListener(onClickListener);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    /**
     * @description: 初始化碎片，并显示复习中的单词列表碎片
     */
    private void InitFragments() {
        List<List<DailyList>> lists = AcquireLists();

        reviewingWordListFragment = new ReviewingWordListFragment(lists.get(0));
        masteredWordListFragment = new MasteredWordListFragment(lists.get(1), lists.get(2), lists.get(3));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_list, reviewingWordListFragment, "ONE");
        fragmentTransaction.commit();
    }

    /**
     * @description: 获取后端的数据，并拆分为4个列表，分别对应复习中，已掌握全部单词，已掌握系统判断，已掌握自行标记
     * @param:
     * @return: 四个单词列表
     */
    private List<List<DailyList>> AcquireLists() {

        List<List<DailyList>> lists = new ArrayList<>();


        // 请求复习中的单词列表
        // 返回具体的单词和复习的日期

        Date date = new Date();
        DailyList dailyList = null;
        List<DailyList> list_study = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
//            date = ;
            String word = "";
            if (dailyList == null || !dailyList.getDate().equals(date)) {
                if (dailyList != null) {
                    list_study.add(dailyList);
                }
                dailyList = new DailyList(getTimeTag(date));

            }
            dailyList.addItem(word);
        }
        lists.add(list_study);


        //请求已掌握的单词列表
        //返回具体的单词、掌握的日期、标记的类型（系统判定/手动标记）

        DailyList dailyList_mastered = null;
        List<DailyList> list_mastered = new ArrayList<>();
        DailyList dailyList_sys = null;
        List<DailyList> list_sys = new ArrayList<>();
        DailyList dailyList_manual = null;
        List<DailyList> list_manual = new ArrayList<>();
        String word = "";
        boolean flag = true;
        for (int i = 0; i < 5; i++) {
//            date = ;
            word = "";
            flag = !flag;
            if (dailyList_mastered == null || !dailyList_mastered.getDate().equals(date)) {
                if (dailyList_mastered != null) {
                    // 添加列表
                    list_mastered.add(dailyList_mastered);
                    if(list_sys.size()!=0){
                        list_sys.add(dailyList_sys);
                    }
                    if(list_manual.size()!=0){
                        list_manual.add(dailyList_manual);
                    }

                }
                dailyList_mastered = new DailyList(getTimeTag(date));
                dailyList_sys = new DailyList(getTimeTag(date));
                dailyList_manual = new DailyList(getTimeTag(date));
            }
            dailyList_mastered.addItem(word);
            if(flag){
                dailyList_sys.addItem(word);
            }else{
                dailyList_manual.addItem(word);
            }
        }

        lists.add(list_mastered);
        lists.add(list_sys);
        lists.add(list_manual);

        return lists;
    }

    private String getTimeTag(Date date) {
        Date curDate;
        String timeTag;
        switch (1) {
            case 0:
                timeTag = "今天";
                break;
            case 1:
                timeTag = "昨天";
                break;
            case 2:
                timeTag = "前天";
                break;
            default:
                timeTag = "";
                break;
        }
        return timeTag;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.button_return:
                    finish();
                    break;
                case R.id.button_edit:
                    break;
                default:
                    break;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            /**
             * @description: 根据选项显示/隐藏对应的碎片
             */
            if (i == R.id.radioButton_reviewing) {
                fragmentTransaction.replace(R.id.fragment_list, reviewingWordListFragment, "ONE");
//                Log.d(TAG, "onCheckedChanged: reviewing");
            } else if (i == R.id.radioButton_mastered) {
                fragmentTransaction.replace(R.id.fragment_list, masteredWordListFragment, "TWO");
//                Log.d(TAG, "onCheckedChanged: mastered");
            }
            fragmentTransaction.commit();
        }
    };

}