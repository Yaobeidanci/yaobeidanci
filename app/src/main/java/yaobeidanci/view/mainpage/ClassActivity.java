package yaobeidanci.view.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import yaobeidanci.view.R;

public class ClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
    }
    public void classclick(View view){
        Toast toast=Toast.makeText(getApplicationContext(), "暂无课程详情哦~", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void backpage2(View view){
        Intent intent=new Intent(ClassActivity.this, SelfPage.class);
        startActivity(intent);
    }
}