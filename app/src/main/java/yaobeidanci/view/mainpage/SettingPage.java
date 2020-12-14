package yaobeidanci.view.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

        EditText editTextTextPersonName15 = findViewById(R.id.editTextTextPersonName15);
        editTextTextPersonName15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingPage.this, PaperActivity.class);
                startActivity(intent);
            }
        });
    }
}