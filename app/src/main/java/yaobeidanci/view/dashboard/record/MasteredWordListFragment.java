package yaobeidanci.view.dashboard.record;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.util.List;

import yaobeidanci.view.R;

/**
 * @version: V1.0
 * @author: 欧阳泽鹏
 * @className: MasteredWordListFragment
 * @packageName: com.example.dashboard.record
 * @description: 这是已掌握单词列表碎片类，包含一个下拉选择框和三个列表控件
 * @data: 2020-11-30 17：43
 **/
@SuppressLint("ValidFragment")
public class MasteredWordListFragment extends Fragment {
    private View view;
    private List<DailyList> list_all,list_sys,list_manual;
    private ListView listView_all;
    private ListView listView_sys;
    private ListView listView_manual;
    /**
     * @description: 构造函数
     * @param: 全部单词列表，系统判断列表，自行标记列表
     */
    @SuppressLint("ValidFragment")
    public MasteredWordListFragment(List<DailyList> listAll,List<DailyList> listSys, List<DailyList> listManual){
        list_all = listAll;
        list_manual = listManual;
        list_sys = listSys;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.layout_dashboard_word_list_mastered,container,false);
        LoadList();
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(onItemSelectedListener);
        listView_all.setOnItemClickListener(onItemClickListener);
        listView_sys.setOnItemClickListener(onItemClickListener);
        listView_manual.setOnItemClickListener(onItemClickListener);
        return view;
    }
    /**
     * @description: 将单词列表加载到对应listView中
     */
    private void LoadList(){
        listView_all =  view.findViewById(R.id.list_all);
        WordListAdapter adapter = new WordListAdapter(view.getContext(), list_all);
        listView_all.setAdapter(adapter);

        listView_sys =  view.findViewById(R.id.list_sys);
        WordListAdapter adapter1 = new WordListAdapter(view.getContext(), list_sys);
        listView_sys.setAdapter(adapter1);

        listView_manual =  view.findViewById(R.id.list_manual);
        WordListAdapter adapter2 = new WordListAdapter(view.getContext(), list_manual);
        listView_manual.setAdapter(adapter2);
    }

    /**
     * @description: 将想要显示的listView显示出来，并将其他的隐藏
     */
    private void HideAndShow(ListView shownListView){
        listView_all.setVisibility(View.INVISIBLE);
        listView_sys.setVisibility(View.INVISIBLE);
        listView_manual.setVisibility(View.INVISIBLE);

        shownListView.setVisibility(View.VISIBLE);
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        /**
         * @description: 根据选项显示/隐藏相应的listView
         */
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String content = adapterView.getItemAtPosition(i).toString();
            switch (content) {
                case "全部单词":
                    HideAndShow(listView_all);
                    break;
                case "系统判断":
                    HideAndShow(listView_sys);
                    break;
                case "自行标记":
                    HideAndShow(listView_manual);
                    break;
            }
        }
        /**
         * @description: 默认显示全部单词列表
         */
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            HideAndShow(listView_all);
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //点击单词的监听事件
            Toast.makeText(view.getContext(),adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
        }
    };

}
