package yaobeidanci.view.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import yaobeidanci.view.R;

public class BuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
    }
    public void buyclick(View view){
        Toast toast=Toast.makeText(getApplicationContext(), "已经卖光了哦~", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void backpage3(View view){
        Intent intent=new Intent(BuyActivity.this, SelfPage.class);
        startActivity(intent);
    }
}