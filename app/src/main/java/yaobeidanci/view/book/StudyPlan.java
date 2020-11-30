package yaobeidanci.view.book;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;

public class StudyPlan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_book_study_plan);
    }
    public void changeBook(View view) {
        Intent intent=new Intent(StudyPlan.this,ChooseBook.class);
        startActivity(intent);
    }
    public void returnMain(View wiew){
        Intent intent=new Intent(StudyPlan.this, MainActivity.class);
        startActivity(intent);
    }
}