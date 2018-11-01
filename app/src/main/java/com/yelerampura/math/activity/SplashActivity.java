package com.yelerampura.math.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;

import com.yelerampura.math.R;
import com.yelerampura.math.base.BaseActivity;
import com.yelerampura.math.helper.SharedPrefsHelper;

import static com.yelerampura.math.helper.AppUtils.Constants.FIRST_TIME;
import static com.yelerampura.math.helper.AppUtils.Constants.THREE_SECOND;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(SharedPrefsHelper.getInstance().get(FIRST_TIME, true)) {
                    Intent i = new Intent(SplashActivity.this, LanguageActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }

                // close this activity
                finish();

            }



        }, THREE_SECOND);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
