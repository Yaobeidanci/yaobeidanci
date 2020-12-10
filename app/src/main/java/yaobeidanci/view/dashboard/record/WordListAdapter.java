package yaobeidanci.view.dashboard.record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import yaobeidanci.view.R;

public class WordListAdapter extends BaseAdapter {
    private static final int TYPE_HEADER = 0;  //代表标题
    private static final int TYPE_ITEM = 1;    //代表项目item

    private List<DailyList> mList;
    private LayoutInflater inflater;

    public WordListAdapter(Context context, List<DailyList> list) {
        mList = list;
        inflater = LayoutInflater.from(context);
    }

    /**
     *
     * @return 所有项的总和
     */
    @Override
    public int getCount() {
        int count = 0;
        if (mList != null) {
            for (DailyList list : mList) {
                count += list.size();
            }
        }
        return count;
    }

    /**
     * 根据position的不同返回不同的值
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        int head = 0;  //标题位置
        for (DailyList list : mList) {
            int size = list.size();
            int current = position - head;
            if (current < size) {
                //返回对应位置的值
                return list.getItem(current);
            }
            head += size;
        }

        return null;
    }

    public int getSize(int position) {
        int head = 0;  //标题位置
        for (DailyList list : mList) {
            int size = list.size();
            int current = position - head;
            if (current < size) {
                //返回对应位置的值
                return list.size()-1;
            }
            head += size;
        }
        return 0;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        switch (getItemViewType(position)) {
            //分为两种情况加载item
            case TYPE_HEADER: //加载标题布局
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.layout_dashboard_word_list_header, parent, false);
                    viewHolder.time = convertView.findViewById(R.id.textView_time);
                    viewHolder.amount = convertView.findViewById(R.id.textView_amount);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.time.setText((CharSequence) getItem(position));
                viewHolder.amount.setText(String.valueOf(getSize(position)));
                break;
            case TYPE_ITEM: //加载数据项目布局
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.layout_dashboard_word_list_adapter, parent, false);
                    viewHolder.word = (TextView) convertView.findViewById(R.id.textView_word);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.word.setText((CharSequence) getItem(position));
                break;
        }

        return convertView;
    }

    /**
     *
     * @return 返回item类型数目
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 获取当前item的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int head = 0;
        for (DailyList type : mList) {
            int size = type.size();
            int current = position - head;
            if (current == 0) {
                return TYPE_HEADER;
            }
            head += size;
        }
        return TYPE_ITEM;
    }

    /**
     * 判断当前的item是否可以点击
     * @param position
     * @return
     */
    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != TYPE_HEADER;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    private class ViewHolder {
        TextView time,amount,word;
    }
}
