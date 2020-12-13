package yaobeidanci.view.collect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yaobeidanci.bean.SentenceObject;
import yaobeidanci.view.R;

public class SentenceDetailAdapter extends ArrayAdapter<SentenceObject> {
    private int resourceId;

    //构造函数
    public SentenceDetailAdapter(Context context, int textViewResourceId, List<SentenceObject> sentence){
        super(context,textViewResourceId,sentence);
        resourceId=textViewResourceId;
    }

    @Override
    //convertView 缓存加载好的布局
    public View getView(int position , View convertView, ViewGroup parent){
        SentenceObject s= (SentenceObject) getItem(position);

        View view ;
        SentenceDetailAdapter.ViewHolder viewHolder;
        if(convertView==null){//如果convertView为空，则加载布局
            view= LayoutInflater.from(getContext()).inflate(R.layout.layout_collect_sentence_detail_item,parent,false);

            //获取控件
            viewHolder=new SentenceDetailAdapter.ViewHolder();
            viewHolder.context=view.findViewById(R.id.sentence_dtext_tv);
            viewHolder.fromig=view.findViewById(R.id.from_iv);
            viewHolder.from=view.findViewById(R.id.from_tv);
            //将ViewHolder存储在View中
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (SentenceDetailAdapter.ViewHolder) view.getTag();
        }

        //获取控件实例，并显示出来
        viewHolder.context.setText(s.getSentence());
        viewHolder.fromig.setImageResource(s.getFromImageId());
        viewHolder.from.setText(s.getOrigin_title());
        return view;
    }

    //对控件实例进行缓存的类
    class ViewHolder{
        TextView context;
        ImageView fromig;
        TextView from;
    }
}
