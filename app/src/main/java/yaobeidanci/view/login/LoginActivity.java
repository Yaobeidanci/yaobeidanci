package yaobeidanci.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import yaobeidanci.view.R;

public class LoginActivity extends AppCompatActivity {

    private Button password_login;
    private Button phonenumber_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_loginmain);

        password_login = (Button) findViewById(R.id.login_password_login);
        password_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UsernameActivity.class);
                startActivity(intent);
            }
        });

        phonenumber_login = (Button) findViewById((R.id.button_phone_login));
        phonenumber_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}