//a test
package yaobeidanci.view.welcome;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;
import yaobeidanci.view.book.StudyPlan;
import yaobeidanci.view.login.LoginActivity;

public class Welcome extends AppCompatActivity {
    // 壁纸
    private ImageView imgBackground;
    // 每日一句卡片
    private CardView cardWelCome;
    // 缩放动画
    private ScaleAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome_activity_welcome);
        cardWelCome = findViewById(R.id.card_wel);
        imgBackground = findViewById(R.id.img_wel_bg);
        // 设置透明度
        cardWelCome.getBackground().setAlpha(200);
        // 从原图大小，放大到1.5倍
        animation = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, 1, 0.5f);
        // 设置持续时间
        animation.setDuration(4000);
        // 设置动画结束之后的状态是否是动画的最终状态
        animation.setFillAfter(true);
        // 设置动画结束后事件
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(Welcome.this, LoginActivity.class);
                startActivity(intent);
                // 销毁启动动画界面
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imgBackground.startAnimation(animation);
    }
}