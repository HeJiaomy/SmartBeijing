package com.smartbeijing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartbeijing.R;
import com.smartbeijing.utils.PrefUtil;

public class SplashActivity extends AppCompatActivity {

    ImageView launchImg;
    RelativeLayout launchRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        launchImg = findViewById(R.id.launch_img);
        launchRl = findViewById(R.id.launch_rl);

        //旋转动画
        RotateAnimation rotateAnim = new RotateAnimation(0, -360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(1000);
        rotateAnim.setFillAfter(true);//保持动画完成时状态

        //缩放动画
        ScaleAnimation scaleAnim = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(1000);
        scaleAnim.setFillAfter(true);

        //渐变动画
        AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
        alphaAnim.setDuration(2000);
        alphaAnim.setFillAfter(true);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(rotateAnim);
        set.addAnimation(scaleAnim);
        set.addAnimation(alphaAnim);
//        launchImg.setAnimation(set);
        launchRl.setAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束
                boolean isFirstEnter = PrefUtil.getBoolean(SplashActivity.this, "is_first_enter", true);
                Intent intent;

                if (isFirstEnter) {
                    //进入新手引导
                    intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                } else {
                    //进入主界面
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
