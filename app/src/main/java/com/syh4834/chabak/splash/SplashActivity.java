package com.syh4834.chabak.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.bumptech.glide.Glide;
import com.syh4834.chabak.InitialActivity;
import com.syh4834.chabak.MainActivity;
import com.syh4834.chabak.R;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {
    private Animation splashRight;
    private Animation splashLeft;

    private SharedPreferences sharedPreferences;

    private ImageView imgLogo;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LottieAnimationView animationView = findViewById(R.id.animationView);
        imgLogo = findViewById(R.id.img_logo);

        sharedPreferences = getSharedPreferences("chabak", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        Log.e("splash_token", token);


        splashRight = AnimationUtils.loadAnimation(this, R.anim.splash_right);
        splashLeft = AnimationUtils.loadAnimation(this, R.anim.splash_left);
        splashLeft.setFillAfter(true);
        splashRight.setFillAfter(true);

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //splashRight.setAnimationListener();
                animationView.startAnimation(splashRight);
                imgLogo.setVisibility(View.VISIBLE);
                imgLogo.startAnimation(splashLeft);



                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(token == "") {
                            Log.e("splash_token", token);
                            Intent intent = new Intent(getBaseContext(), InitialActivity.class);
                            startActivity(intent);
                        } else {
                            Log.e("splash_token", token);
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }, 900);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

//        ImageView aniSplash = findViewById(R.id.splash_car);
//
//        Glide
//                .with(this)
//                .load(R.raw.splash)
//                .into(aniSplash);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void setUpAnimation(LottieAnimationView animationView) {
        // 재생할 애니메이션 넣어준다.
        animationView.setAnimation("data.json");
        // 반복횟수를 무한히 주고 싶을 땐 LottieDrawable.INFINITE or 원하는 횟수
        animationView.setRepeatCount(1);
        // 시작
        animationView.playAnimation();
    }
}