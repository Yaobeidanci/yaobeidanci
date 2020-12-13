package yaobeidanci.view.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import yaobeidanci.view.R;
import android.widget.ImageView;

public class SelfPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_self);

    }
    public void onclick_setting(View view){
        Intent intent=new Intent(SelfPage.this,SettingPage.class);
        startActivity(intent);


    }
}