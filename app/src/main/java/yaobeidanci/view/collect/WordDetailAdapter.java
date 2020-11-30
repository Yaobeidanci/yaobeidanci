package yaobeidanci.view.collect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import yaobeidanci.bean.Word;
import yaobeidanci.view.R;

public class WordDetailAdapter extends ArrayAdapter<Word> {
    private int resourceId;

    //构造函数
    public WordDetailAdapter(Context context, int textViewResourceId, List<Word> word){
        super(context,textViewResourceId,word);
        resourceId=textViewResourceId;
    }

    @Override
    //convertView 缓存加载好的布局
    public View getView(int position , View convertView, ViewGroup parent){
        Word word= (Word) getItem(position);

        View view ;
        ViewHolder viewHolder;
        if(convertView==null){//如果convertView为空，则加载布局
            view= LayoutInflater.from(getContext()).inflate(R.layout.layout_collect_word_detail_item,parent,false);

            //获取控件
            viewHolder=new ViewHolder();
            viewHolder.text=view.findViewById(R.id.word_dtext_tv);
            //将ViewHolder存储在View中
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        //获取控件实例，并显示出来
        viewHolder.text.setText(word.getText());
        return view;
    }

    //对控件实例进行缓存的类
    class ViewHolder{
        TextView text;
    }
}
