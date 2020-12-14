package yaobeidanci.view.book;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import yaobeidanci.MyUtil;
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
            view=itemView;
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
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                final BookItemEntity itemWordBook = mItemWordBookList.get(position);
//                if(itemWordBook.getBookId()==ConstantConfig.CET6) {
//                    Toast.makeText(MainActivity.getContext(), "已经是"+itemWordBook.getBookId()+":"+itemWordBook.getBookName()+"了哦", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    // 传值
//                    Intent intent = new Intent(MainActivity.getContext(), StudyPlan.class);
//                    intent.putExtra("bookname",itemWordBook.getBookName());
//                    intent.putExtra("bookid",itemWordBook.getBookId());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    MainActivity.getContext().startActivity(intent);
//                }
//                JSONObject object = new JSONObject();
//                try {
//                    object.put("uid", "266c0c9fc2446658333fb249d10e3cdf");
//                    object.put("book_id", itemWordBook.getBookId());
//                    object.put("num_daily", 50);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                MyUtil.httpGet(MyUtil.BASE_URL + "/api/setSchedule", object, new MyUtil.MyCallback() {
//                    @Override
//                    public void onSuccess(Object result) {
//                        String res = (String) result;
//                        Toast.makeText(MainActivity.getContext(),res,Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Object result) {
//
//                    }
//                }, true);
                Intent intent = new Intent(MainActivity.getContext(), SetNumActivity.class);
                intent.putExtra("bookname",itemWordBook.getBookName());
                intent.putExtra("bookid",itemWordBook.getBookId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.getContext().startActivity(intent);
            }
        });
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
