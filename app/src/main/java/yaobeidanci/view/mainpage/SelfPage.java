package yaobeidanci.view.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import yaobeidanci.view.R;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SelfPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_self);

        Button bt3 = findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toasts(v);
            }
        });
        Button bt5 = findViewById(R.id.button5);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfPage.this, MessageActivity.class);
                startActivity(intent);
            }
        });
        Button bt6 = findViewById(R.id.button6);
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toasts(v);
            }
        });

    }
    public void onclick_setting(View view){
        Intent intent=new Intent(SelfPage.this,SettingPage.class);
        startActivity(intent);
    }
    public void toasts(View view){
        Toast toast=Toast.makeText(getApplicationContext(), "该功能暂未实现哟~", Toast.LENGTH_SHORT);
        toast.show();
    }
}