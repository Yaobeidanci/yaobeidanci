package yaobeidanci.view.dashboard.record;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.util.List;

import yaobeidanci.view.R;

/**
 * @version: V1.0
 * @author: 欧阳泽鹏
 * @className: ReviewingWordListFragment
 * @packageName: com.example.dashboard.record
 * @description: 这是复习中单词列表碎片类
 * @data: 2020-11-30 17：43
 **/
@SuppressLint("ValidFragment")
public class ReviewingWordListFragment extends Fragment {
    private View view;
    private List<DailyList> list;
    private ListView listView;
    /**
     * @description: 构造函数
     * @param: 复习中单词列表
     */
    @SuppressLint("ValidFragment")
    public ReviewingWordListFragment(List<DailyList> list){
        this.list = list;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.layout_dashboard_word_list_reviewing,container,false);

        LoadList();
        return view;
    }
    /**
     * @description: 将单词列表加载到对应listView中
     */
    private void LoadList(){
        listView =  view.findViewById(R.id.list);
        WordListAdapter adapter = new WordListAdapter(view.getContext(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //点击单词的监听事件
            Toast.makeText(view.getContext(),adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
        }
    };
}
