package yaobeidanci.view.collect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yaobeidanci.bean.WordExplanationObject;
import yaobeidanci.view.R;

public class ExpAdapter extends ArrayAdapter<WordExplanationObject> {
    private int resourceId;

    //构造函数
    public ExpAdapter(Context context, int textViewResourceId, List<WordExplanationObject> exps){
        super(context,textViewResourceId,exps);
        resourceId=textViewResourceId;
    }

    @Override
    //convertView 缓存加载好的布局
    public View getView(int position , View convertView, ViewGroup parent){
        WordExplanationObject exp= (WordExplanationObject) getItem(position);

        View view ;
        ViewHolder viewHolder;
        if(convertView==null){//如果convertView为空，则加载布局
            view= LayoutInflater.from(getContext()).inflate(R.layout.layout_collect_explanation_item,parent,false);

            //获取控件
            viewHolder=new ViewHolder();
            viewHolder.cexp_tv=view.findViewById(R.id.cexp_tv);
            viewHolder.eexp_tv=view.findViewById(R.id.eexp_tv);

            //将ViewHolder存储在View中
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        //获取控件实例，并显示出来
        viewHolder.cexp_tv.setText(exp.getExplain_c());
        viewHolder.eexp_tv.setText(exp.getExplain_e());
        return view;
    }

    //对控件实例进行缓存的类
    class ViewHolder{
        TextView cexp_tv;
        TextView eexp_tv;
    }
}
