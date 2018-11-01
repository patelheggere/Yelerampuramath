package com.yelerampura.math.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yelerampura.math.R;
import com.yelerampura.math.base.BaseActivity;
import com.yelerampura.math.helper.SharedPrefsHelper;

import static com.yelerampura.math.helper.AppUtils.Constants.LANGUAGE;

public class LanguageActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonKannada, radioButtonEnglish;
    private Button btnNext;

    @Override
    protected int getContentView() {
        return R.layout.activity_language;
    }

    @Override
    protected void initView() {
        radioGroup = findViewById(R.id.rg_language);
        radioButtonEnglish = findViewById(R.id.rb_english);
        radioButtonKannada = findViewById(R.id.rb_kannada);
        btnNext = findViewById(R.id.btn_next);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
                startActivity(new Intent(LanguageActivity.this, WelcomeActivity.class));
                finish();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb_kannada) {
                    Log.d(TAG, "onCheckedChanged: kannada");
                    SharedPrefsHelper.getInstance().save(LANGUAGE, "ka");
                }
                else if(i==R.id.rb_english)
                {
                    Log.d(TAG, "onCheckedChanged: english");
                    SharedPrefsHelper.getInstance().save(LANGUAGE, "en");
                }

            }

        });




    }
}

