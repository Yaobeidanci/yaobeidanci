package yaobeidanci.view.collect;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import yaobeidanci.bean.SentenceObject;
import yaobeidanci.view.R;

import java.util.List;


public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.ViewHolder> {

    private List<SentenceObject> sList;
    private int [] bg;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bg_iv;
        TextView text_tv;
        TextView from_tv;
        CardView cv;
        public ViewHolder(View itemView) {
            super(itemView);
            bg_iv = (ImageView)itemView.findViewById(R.id.sentence_bg);
            text_tv = (TextView)itemView.findViewById(R.id.sentence_text);
            from_tv = (TextView)itemView.findViewById(R.id.sentence_from);
            cv = (CardView)itemView.findViewById(R.id.sentence_cv);
        }
    }

    public SentenceAdapter(List<SentenceObject> sList) {
        this.sList = sList;
        bg=new int[5];

        bg[0]=R.drawable.a;
        bg[1]=R.drawable.b;
        bg[2]=R.drawable.c;
        bg[3]=R.drawable.d;
        bg[4]=R.drawable.f;
    }

    //用来创建ViewHolder实例，并把加载出来的布局传入构造函数中，最后将ViewHolder的实例返回
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_collect_sentence_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //用于对RecycleView子项的数据进行赋值的，会在每个子项滚动到屏幕内的时候执行
    @Override
    public void onBindViewHolder(final SentenceAdapter.ViewHolder holder, int position) {
        SentenceObject s = sList.get(position);
        holder.bg_iv.setImageResource(bg[s.getBackgroundId()]);
        holder.from_tv.setText(s.getOrigin_title());
        holder.text_tv.setText(s.getSentence());
        holder.text_tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        holder.from_tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        //holder.fruitName.setWidth(fruit.getName().length()*20);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= holder.getAdapterPosition();
                SentenceObject s=sList.get(position);
                Toast.makeText(v.getContext(),"你点击了： "+s.getId(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    //告诉RecyclerView一共有多少个子项，直接返回数据源的长度
    @Override
    public int getItemCount() {
        return sList.size();
    }

}

