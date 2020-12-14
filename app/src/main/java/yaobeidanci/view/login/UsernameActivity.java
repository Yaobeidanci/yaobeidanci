package yaobeidanci.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import yaobeidanci.MyUtil;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;
import yaobeidanci.view.learn.WordMainActivity;
import yaobeidanci.view.welcome.Welcome;

public class UsernameActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    String username_string;
    String password_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_username_passward);

        username = (EditText) findViewById(R.id.login_username_enter);
        password = (EditText) findViewById(R.id.login_password_enter);

        Button register_button = (Button) findViewById(R.id.register_user);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsernameActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button back_button = (Button) findViewById(R.id.username_password_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsernameActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button verify_button = (Button) findViewById(R.id.login_password_login);
        verify_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                username_string = username.getText().toString();
                password_string = password.getText().toString();

                Log.d("UTest", "\n\n" + username_string);
                Log.d("UTest", "\n\n" + password_string);

                JSONObject object = new JSONObject();
                try {
                    object.put("username", username_string);   // tsluqn
                    object.put("password", password_string);
                    MyUtil.httpPost(MyUtil.BASE_URL + "/api/login", object, true, new MyUtil.MyCallback() {
                        @Override
                        public void onSuccess(MyUtil.Res result) {
                            try{
                                JSONObject jsonObject = new JSONObject((String) result.data);
//                                JSONObject status = jsonObject.getJSONObject("msg");
                                if(jsonObject.getString("status").equals("200")){
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    MyUtil.setUid(data.getString("uid"));
                                    Toast.makeText(UsernameActivity.this, ((String) result.data) + " " + MyUtil.getUid(), Toast.LENGTH_SHORT).show();
//                                    WordMainActivity.startIt(UsernameActivity.this);
                                    Intent intent = new Intent(UsernameActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(UsernameActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(MyUtil.Res result) {
                            Toast.makeText(MainActivity.getContext(), result.msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
