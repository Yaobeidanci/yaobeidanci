package yaobeidanci.view.book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder>{

    private ArrayList<BookItemEntity> mItemWordBookList;

    class MyViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView bookname,wordcnt,intr;
        ImageView bookimg;
        public MyViewHolder(View itemView) {
            super(itemView);
            bookname= (TextView) itemView.findViewById(R.id.item_text_book_name);
            wordcnt= (TextView) itemView.findViewById(R.id.item_text_book_word_num);
            bookimg= (ImageView) itemView.findViewById(R.id.item_img_book);
            intr=(TextView)itemView.findViewById(R.id.item_text_book_intr);
        }
   }
    public BookListAdapter(ArrayList<BookItemEntity> data) {
        this.mItemWordBookList = data;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book_book_item, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // 绑定数据
        final BookItemEntity bookitem=mItemWordBookList.get(position);
        Glide.with(MainActivity.getContext()).load(bookitem.getBookImg()).asBitmap().into(holder.bookimg);
        holder.bookname.setText(bookitem.getBookName());
        holder.wordcnt.setText(bookitem.getBookWordNum()+"");
        holder.intr.setText(bookitem.getBookIntr());
    }
    public int getItemCount() {
        return mItemWordBookList == null ? 0 : mItemWordBookList.size();
    }
}
