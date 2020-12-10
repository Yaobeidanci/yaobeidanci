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

    private void FindViews(){
        button_return = findViewById(R.id.button_return);
        button_edit = findViewById(R.id.button_edit);
        radioGroup = findViewById(R.id.radioGroup);
    }

    private void SetListeners(){
        button_return.setOnClickListener(onClickListener);
        button_edit.setOnClickListener(onClickListener);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    /**
     * @description: 初始化碎片，并显示复习中的单词列表碎片
     */
    private void InitFragments(){
        List<List<DailyList>> lists = AcquireLists();

        reviewingWordListFragment = new ReviewingWordListFragment(lists.get(0));
        masteredWordListFragment = new MasteredWordListFragment(lists.get(1),lists.get(2),lists.get(3));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_list, reviewingWordListFragment,"ONE");
        fragmentTransaction.commit();
    }

    /**
     * @description: 获取后端的数据，并拆分为4个列表，分别对应复习中，已掌握全部单词，已掌握系统判断，已掌握自行标记
     * @param:
     * @return: 四个单词列表
     */
    private  List<List<DailyList>> AcquireLists(){
        //自编数据
        List<List<DailyList>> lists = new ArrayList<>();

        List<DailyList> list0 = new ArrayList<>();
        DailyList dailyList0 = new DailyList("今天");
        dailyList0.addItem("apple");
        dailyList0.addItem("banana");
        dailyList0.addItem("cat");
        dailyList0.addItem("dog");
        dailyList0.addItem("eggplant");
        list0.add(dailyList0);
        DailyList type = new DailyList("昨天");
        type.addItem("abandon");
        type.addItem("abolish");
        type.addItem("aboard");
        type.addItem("abroad");
        list0.add(type);
        lists.add(list0);

        List<DailyList> list1 = new ArrayList<>();
        DailyList dailyList1 = new DailyList("昨天");
        dailyList1.addItem("excellent");
        dailyList1.addItem("awesome");
        dailyList1.addItem("unbelievable");
        dailyList1.addItem("incredible");
        dailyList1.addItem("fantastic");
        list1.add(dailyList1);
        lists.add(list1);

        List<DailyList> list2 = new ArrayList<>();
        DailyList dailyList2 = new DailyList("昨天");
        dailyList2.addItem("unbelievable");
        dailyList2.addItem("incredible");
        dailyList2.addItem("fantastic");
        list2.add(dailyList2);
        lists.add(list2);

        List<DailyList> list3 = new ArrayList<>();
        DailyList dailyList3 = new DailyList("昨天");
        dailyList3.addItem("excellent");
        dailyList3.addItem("awesome");
        list3.add(dailyList3);
        lists.add(list3);

        return lists;
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){
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
            if(i == R.id.radioButton_reviewing){
                fragmentTransaction.replace(R.id.fragment_list, reviewingWordListFragment,"ONE");
//                Log.d(TAG, "onCheckedChanged: reviewing");
            }else if(i == R.id.radioButton_mastered){
                fragmentTransaction.replace(R.id.fragment_list, masteredWordListFragment,"TWO");
//                Log.d(TAG, "onCheckedChanged: mastered");
            }
            fragmentTransaction.commit();
        }
    };

}