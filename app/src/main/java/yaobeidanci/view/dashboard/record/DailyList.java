package yaobeidanci.view.dashboard.record;

import java.util.ArrayList;
import java.util.List;

/**
 * @version: V1.0
 * @author: 欧阳泽鹏
 * @className: DailyList
 * @packageName: com.example.dashboard.record
 * @description: 这是单词列表类，按一天为一列
 * @data: 2020-11-30 17：31
 **/
public class DailyList {
    private String timeTag;
    private List<String> wordList;
    /**
     * @description: 构造函数
     * @param: timeTag
     */
    public DailyList(String timeTag){
        this.timeTag = timeTag;
        wordList = new ArrayList<>();
    }
    /**
     * @description: 添加一个单词
     * @param:  word
     */
    public void addItem(String word) {
        wordList.add(word);
    }

    /**
     * @description: 根据位置获取时间标签或一个单词
     * @param:  position
     * @return: 时间标签或一个单词
     */
    public String getItem(int position) {
        if (position == 0) {
            return timeTag;
        } else {
            return wordList.get(position - 1);
        }
    }

    /**
     * @return: item数目，为集合大小+1
     */
    public int size() {
        return wordList.size() + 1;
    }
}
