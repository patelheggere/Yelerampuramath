package com.yelerampura.math.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yelerampura.math.R;
import com.yelerampura.math.YelarampuraApplication;
import com.yelerampura.math.base.BaseActivity;
import com.yelerampura.math.helper.AppUtils;
import com.yelerampura.math.helper.SharedPrefsHelper;
import com.yelerampura.math.models.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static com.yelerampura.math.helper.AppUtils.Constants.EMAIL;
import static com.yelerampura.math.helper.AppUtils.Constants.FIRST_TIME;
import static com.yelerampura.math.helper.AppUtils.Constants.MOBILE;
import static com.yelerampura.math.helper.AppUtils.Constants.NAME;

public class RegistrationActivity extends BaseActivity {

    private static final String TAG = "RegistrationActivity";
    private ActionBar mActionBar;
    private Spinner spinner;
    private TextInputEditText textInputEditTextName, textInputEditTextPhone, textInputEditTextEmail;
    private String mInterest;
    private List<String> listKula;
    private ArrayAdapter<String> adapter;
    private Button mButtonSubmit;
    private DatabaseReference databaseReference;

    @Override
    protected int getContentView() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initView() {
        addListofInterest();
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(getString(R.string.registration));
        spinner = findViewById(R.id.sp_kula);
        textInputEditTextEmail = findViewById(R.id.et_email);
        textInputEditTextName = findViewById(R.id.et_name);
        textInputEditTextPhone = findViewById(R.id.et_phone);
        mButtonSubmit = findViewById(R.id.btn_register);
    }

    private void addListofInterest() {
        listKula = new ArrayList<>();
        listKula.add("Select one");
        listKula.add("Bellenavaru");
        listKula.add("Eredukeryavaru");
        listKula.add("Janukallavaru");
        listKula.add("Havinavaru");
        listKula.add("Andenavaru");
        listKula.add("Basalenavaru");
        listKula.add("Kagenavaru");
        listKula.add("Jeerigeyavaru");
        listKula.add("Kataradavaru");
        listKula.add("Jaldenavaru");
        listKula.add("Aluvanavaru");
        listKula.add("Raginavaru");
        listKula.add("Alpenavaru");
    }

    @Override
    protected void initData() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listKula);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mInterest = listKula.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDetails();
            }
        });
    }

    private void submitDetails() {
        try {
            if(textInputEditTextName.getText()==null || textInputEditTextName.getText().length()<3)
            {
                textInputEditTextName.setError(getString(R.string.name_required));
                return;
            }
            if(textInputEditTextPhone.getText()==null || textInputEditTextPhone.getText().length()!=10)
            {
                textInputEditTextPhone.setError(getString(R.string.phone_correct));
                return;
            }
            if(mInterest.contains("Select one"))
            {
                AppUtils.showSnackBar(activity, getString(R.string.please_bedagu));
                return;
            }
           /* if(textInputEditTextEmail.getText()!=null) {
                if (!(Patterns.EMAIL_ADDRESS.matcher(textInputEditTextEmail.getText()).matches())) {
                    return;
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        databaseReference = YelarampuraApplication.getFireBaseRef().child("users").child(mInterest);
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(textInputEditTextEmail.getText().toString());
        userDetails.setInterest(mInterest);
        userDetails.setMobile(textInputEditTextPhone.getText().toString());
        userDetails.setName(textInputEditTextName.getText().toString());
        databaseReference.child(textInputEditTextPhone.getText().toString()).setValue(userDetails);
        SharedPrefsHelper.getInstance().save(FIRST_TIME, false);
        SharedPrefsHelper.getInstance().save(NAME,textInputEditTextName.getText().toString());
        SharedPrefsHelper.getInstance().save(EMAIL, textInputEditTextEmail.getText().toString());
        SharedPrefsHelper.getInstance().save(MOBILE, textInputEditTextPhone.getText().toString());
        startActivity(new Intent(activity, MainActivity.class));
        finish();
    }
}

