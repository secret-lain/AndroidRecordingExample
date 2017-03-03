package com.echo.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.echo.R;

/**
 * Created by admin on 2017-03-03.
 *
 * 사용자 플로우
 * 첫 사용자인지 판단해서, 첫사용자는 OnBoarding 으로 넘어간다.
 * 첫 사용자가 아닌자는 MainActivity로 넘어간다.
 *
 * OnBoarding은 다 보고나서 프로필설정화면으로 자동으로 넘어간다.
 * 프로필설정화면에서 onBackPress 를 눌렀을때 아예 정보가 입력되지 않은 상태라면 앱종료 확인으로 넘어간다.
 */

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    SharedPreferences sharedPreferences;

    /** 처음 액티비티가 생성될때 불려진다. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_activity_layout);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                boolean isFirstTime = sharedPreferences.getBoolean(getString(R.string.is_first_app_started), true);

                if(isFirstTime){
                    /* 첫 실행시에는 onBoarding으로 이동한다
                     *  지금은 걍 넘어간다
                     */
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else{
                    /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }



            }
        }, SPLASH_DISPLAY_LENGTH);

        Glide.with(getApplicationContext())
                .load(R.drawable.stop_64px_blue400)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .preload();
        Glide.with(getApplicationContext())
                .load(R.drawable.recording_64px_red400)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .preload();
    }
}
