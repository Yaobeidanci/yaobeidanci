package yaobeidanci.view.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import yaobeidanci.MyUtil;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

        final TextView username_text = findViewById(R.id.username);

        JSONObject object = new JSONObject();
        try {
            object.put("uid", MyUtil.getUid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyUtil.httpGet(MyUtil.BASE_URL + "/resource/user", object, new MyUtil.MyCallback() {
            @Override
            public void onSuccess(MyUtil.Res result) {
                try {
                    JSONObject object1 = new JSONObject((String) result.data).getJSONObject("data");
                    String username = object1.getString("username");
                    username_text.setText(username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(MyUtil.Res result) {
                Toast.makeText(MainActivity.getContext(), result.msg, Toast.LENGTH_SHORT).show();
            }
        }, true);

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