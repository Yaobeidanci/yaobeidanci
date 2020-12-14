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

public class RegisterActivity extends AppCompatActivity {

    EditText username;
    EditText first_password;
    EditText second_password;

    String username_string;
    String first_password_string;
    String second_password_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_password);

        username = (EditText) findViewById(R.id.register_username_enter);
        first_password = (EditText) findViewById(R.id.first_password_enter);
        second_password = (EditText) findViewById(R.id.second_password_enter);

        Button back_button = (Button) findViewById(R.id.register_back);
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button register_button = (Button) findViewById(R.id.register_confirm);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username_string = username.getText().toString();
                first_password_string = first_password.getText().toString();
                second_password_string = second_password.getText().toString();

                if(!first_password_string.equals(second_password_string)){
                    Toast.makeText(RegisterActivity.this, (String) "密码不一致!", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject object = new JSONObject();
                try {
                    object.put("username", username_string);   // tsluqn
                    object.put("password", first_password_string);
                    object.put("phone", second_password_string);
                    MyUtil.httpPost(MyUtil.BASE_URL + "/api/register", object, true, new MyUtil.MyCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            try{
                                JSONObject jsonObject = new JSONObject((String) result);
//                                JSONObject status = jsonObject.getJSONObject("msg");
                                if(jsonObject.getString("status").equals("200")){
                                    Toast.makeText(RegisterActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(RegisterActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                Toast.makeText(RegisterActivity.this, (String) result, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Object result) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
