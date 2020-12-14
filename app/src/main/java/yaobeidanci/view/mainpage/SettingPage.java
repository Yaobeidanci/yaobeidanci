package yaobeidanci.view.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import yaobeidanci.MyUtil;
import yaobeidanci.view.R;
import yaobeidanci.view.login.LoginActivity;

public class SettingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);

        Button logout = findViewById(R.id.logout_bt);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtil.setUid("");
                Intent intent = new Intent(SettingPage.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        TextView buy = findViewById(R.id.buy_cource_bt);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingPage.this, ClassActivity.class);
                startActivity(intent);
            }
        });

        TextView private_bt = findViewById(R.id.private_bt);
        private_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingPage.this, PrivacyActivity.class);
                startActivity(intent);
            }
        });

        TextView duihuan = findViewById(R.id.duihuan_bt);
        duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingPage.this, BuyActivity.class);
                startActivity(intent);
            }
        });
    }
}