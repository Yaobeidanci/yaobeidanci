package yaobeidanci.view.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import yaobeidanci.view.R;

public class ChooseBook extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    //private Spinner spinner;
    //private ArrayAdapter<String> arr_adapter;
    ArrayList<BookItemEntity> data = new ArrayList<>();
    //ArrayList<String> choice_list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_book_choose_book);
        //通过findViewById拿到RecyclerView实例
        mRecyclerView =  findViewById(R.id.recycler_book_list);
        //设置RecyclerView管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        /*
        书籍列表初始化
         */
        /*String temp = " item";
        for(int i = 0; i < 20; i++) {
            data.add(new BookItemEntity(i,"title "+i,i*100));
        }*/
        data.add(new BookItemEntity(ConstantConfig.CET4,ConstantConfig.bookNameById(ConstantConfig.CET4),ConstantConfig.wordTotalNumberById(ConstantConfig.CET4),ConstantConfig.bookPicById(ConstantConfig.CET4),ConstantConfig.bookIntrById(ConstantConfig.CET4)));
        data.add(new BookItemEntity(ConstantConfig.CET6,ConstantConfig.bookNameById(ConstantConfig.CET6),ConstantConfig.wordTotalNumberById(ConstantConfig.CET6),ConstantConfig.bookPicById(ConstantConfig.CET6),ConstantConfig.bookIntrById(ConstantConfig.CET6)));
        data.add(new BookItemEntity(ConstantConfig.ZHUAN4,ConstantConfig.bookNameById(ConstantConfig.ZHUAN4),ConstantConfig.wordTotalNumberById(ConstantConfig.ZHUAN4),ConstantConfig.bookPicById(ConstantConfig.ZHUAN4),ConstantConfig.bookIntrById(ConstantConfig.ZHUAN4)));
        data.add(new BookItemEntity(ConstantConfig.KAOYAN,ConstantConfig.bookNameById(ConstantConfig.KAOYAN),ConstantConfig.wordTotalNumberById(ConstantConfig.KAOYAN),ConstantConfig.bookPicById(ConstantConfig.KAOYAN),ConstantConfig.bookIntrById(ConstantConfig.KAOYAN)));
        data.add(new BookItemEntity(ConstantConfig.ZHUAN8,ConstantConfig.bookNameById(ConstantConfig.ZHUAN8),ConstantConfig.wordTotalNumberById(ConstantConfig.ZHUAN8),ConstantConfig.bookPicById(ConstantConfig.ZHUAN8),ConstantConfig.bookIntrById(ConstantConfig.ZHUAN8)));
        /*data.add(new BookItemEntity(ConstantConfig.CET4,ConstantConfig.bookNameById(ConstantConfig.CET4),ConstantConfig.wordTotalNumberById(ConstantConfig.CET4)));
        data.add(new BookItemEntity(ConstantConfig.CET6_MOST,ConstantConfig.bookNameById(ConstantConfig.CET6_MOST),ConstantConfig.wordTotalNumberById(ConstantConfig.CET6_MOST)));
        data.add(new BookItemEntity(ConstantConfig.CET6_ALL,ConstantConfig.bookNameById(ConstantConfig.CET6_ALL),ConstantConfig.wordTotalNumberById(ConstantConfig.CET6_ALL)));
        data.add(new BookItemEntity(ConstantConfig.KAOYAN,ConstantConfig.bookNameById(ConstantConfig.KAOYAN),ConstantConfig.wordTotalNumberById(ConstantConfig.KAOYAN)));
        data.add(new BookItemEntity(ConstantConfig.GRE,ConstantConfig.bookNameById(ConstantConfig.GRE),ConstantConfig.wordTotalNumberById(ConstantConfig.GRE)));
*/
        BookListAdapter bookListAdapter=new BookListAdapter(data);

//       //初始化适配器
//        mAdapter = new BookListAdapter(data);
//        //设置适配器
//        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAdapter(bookListAdapter);
    }
    public void returnPlan(View view) {
        Intent intent=new Intent(ChooseBook.this,StudyPlan.class);
        startActivity(intent);
    }
}