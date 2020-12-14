package yaobeidanci.view.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;
import yaobeidanci.view.mainpage.SelfPage;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }
    public void backpage(View view){
        Intent intent=new Intent(MessageActivity.this, SelfPage.class);
        startActivity(intent);
    }
    public void toasttip(View view){
        Toast toast=Toast.makeText(getApplicationContext(), "暂无消息详情哦~", Toast.LENGTH_SHORT);
        toast.show();
    }
}