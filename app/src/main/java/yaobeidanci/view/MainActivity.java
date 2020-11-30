package yaobeidanci.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import yaobeidanci.view.collect.CollectMainActivity;
import yaobeidanci.view.learn.WordMainActivity;
import yaobeidanci.view.welcome.Welcome;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        Button button = findViewById(R.id.startLearnBt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WordMainActivity.class);
                startActivity(intent);
            }
        });
        Button collectBt = findViewById(R.id.startCollectBt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollectMainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void click_book(View view) {
        Intent intent=new Intent(MainActivity.this, Welcome.class);
        startActivity(intent);
    }
    public static Context getContext() {
        return context;
    }
}