package yaobeidanci.view.book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import yaobeidanci.MyUtil;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;

public class SetNumActivity extends AppCompatActivity {
    public static String bookid;//当前书籍id
    public static int inputcnt;//当前选择要背的单词数
    EditText inputnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_num);
        Intent intent=getIntent();
        TextView totalcnt=(TextView)findViewById(R.id.text_max_word_num);
        TextView nowbook=(TextView)findViewById(R.id.now_book_chosen);
        inputnum=(EditText)findViewById(R.id.edit_word_num);
        if(intent.getStringExtra("bookid")!=null){
            bookid=intent.getStringExtra("bookid");
            totalcnt.setText(ConstantConfig.wordTotalNumberById(bookid)+"");
            nowbook.setText(intent.getStringExtra("bookname"));
        }
    }
    public void getNumber(View view){
        inputcnt=Integer.parseInt(inputnum.getText().toString());//获取输入的要背的单词数
        JSONObject object = new JSONObject();
        try {
            object.put("uid", "266c0c9fc2446658333fb249d10e3cdf");
            object.put("book_id", bookid);
            object.put("num_daily", inputcnt);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyUtil.httpGet(MyUtil.BASE_URL + "/api/setSchedule", object, new MyUtil.MyCallback() {
            @Override
            public void onSuccess(Object result) {
                String res = (String) result;
                //Toast.makeText(MainActivity.getContext(),res,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Object result) {

            }
        }, true);
        Intent intent=new Intent(SetNumActivity.this,StudyPlan.class);
        startActivity(intent);
    }
}