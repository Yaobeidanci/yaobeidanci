package yaobeidanci.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import yaobeidanci.MyUtil;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.login.RandomNumber;
import yaobeidanci.view.login.SendEmail;

import yaobeidanci.view.R;

public class PhoneLoginActivity extends AppCompatActivity {

    private EditText emailEnter;
    private EditText validcode;
    private Button getCode;
    private Button login;

    private long verificationCode=0; //生成的验证码
    private String email; //邮箱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_phone_code_confirm);

        Button back_button = (Button) findViewById(R.id.phonenum_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneLoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        init();
    }

    private void init() {
        emailEnter = (EditText) findViewById(R.id.emailEnter);
        validcode = (EditText) findViewById(R.id.validateCodeEnter);
        getCode = (Button) findViewById(R.id.getValidate);
        login = (Button) findViewById(R.id.login_password_login);

        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailEnter.getText().toString();
                sendVerificationCode(email); //发送验证码
                Toast.makeText(PhoneLoginActivity.this , "验证码已发送", Toast.LENGTH_SHORT).show();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                judgeVerificationCode(); //判断输入的验证码是否正确

                JSONObject object = new JSONObject();
                try {
                    object.put("username", email);   // tsluqn
                    object.put("password", "123456");
                    object.put("phone", "123456");
                    MyUtil.httpPost(MyUtil.BASE_URL + "/api/register", object, true, new MyUtil.MyCallback() {
                        @Override
                        public void onSuccess(MyUtil.Res result) {
                            try{
                                JSONObject jsonObject = new JSONObject((String) result.data);
//                                JSONObject status = jsonObject.getJSONObject("msg");
                                if(jsonObject.getString("status").equals("200")){
                                    MyUtil.setUid(jsonObject.getString("uid"));
                                    Toast.makeText(PhoneLoginActivity.this, (String) result.data, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(PhoneLoginActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                Toast.makeText(PhoneLoginActivity.this, (String) result.data, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(MyUtil.Res result) {
                            Toast.makeText(PhoneLoginActivity.this, result.msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //发送验证码
    private void sendVerificationCode(final String email) {
        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        RandomNumber rn = new RandomNumber();
                        verificationCode = rn.getRandomNumber(6);
                        SendEmail se = new SendEmail(email);
                        se.sendHtmlEmail(verificationCode);//发送html邮件
                        Toast.makeText(PhoneLoginActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断输入的验证码是否正确
    private void judgeVerificationCode() {
        if(Integer.parseInt(validcode.getText().toString())==verificationCode){ //验证码和输入一致
            Toast.makeText(PhoneLoginActivity.this,"验证成功",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(PhoneLoginActivity.this, "验证失败", Toast.LENGTH_LONG).show();
        }
    }

}
