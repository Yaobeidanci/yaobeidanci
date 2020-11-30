package yaobeidanci.view.collect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yaobeidanci.bean.Word;
import yaobeidanci.view.R;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private List<Word> wordList = new ArrayList<>();
    private CollectMainActivity ma;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView wordText;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            wordText = (TextView)itemView.findViewById(R.id.word_text);
            cv = (CardView)itemView.findViewById(R.id.word_cv);
        }
    }

    public WordAdapter(CollectMainActivity ma, List<Word> wordList) {
        this.ma=ma;
        this.wordList = wordList;
    }

    //用来创建ViewHolder实例，并把加载出来的布局传入构造函数中，最后将ViewHolder的实例返回
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_collect_word_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //用于对RecycleView子项的数据进行赋值的，会在每个子项滚动到屏幕内的时候执行
    @Override
    public void onBindViewHolder(final WordAdapter.ViewHolder holder, int position) {
        final Word word = wordList.get(position);
        holder.wordText.setText(word.getText());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= holder.getAdapterPosition();
                Word w=wordList.get(position);
                Toast.makeText(v.getContext(),"你点击了： "+w.getText(),Toast.LENGTH_SHORT).show();
                ma.sd.setVisibility(View.VISIBLE);
                ma.word_title_tv.setText(word.getText());
                ma.base_exp_tv.setText(word.getcExp());
                //解释explanation设置适配器
                ExpAdapter expAdapter=new  ExpAdapter(ma,R.layout.layout_collect_explanation_item,w.getExp());
                //将适配器与listView连接
                ma.exp_ls.setAdapter(expAdapter);
            }
        });
    }


    //告诉RecyclerView一共有多少个子项，直接返回数据源的长度
    @Override
    public int getItemCount() {
        return wordList.size();
    }

}

