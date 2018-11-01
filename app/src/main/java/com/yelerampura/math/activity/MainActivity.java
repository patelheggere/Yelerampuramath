package com.yelerampura.math.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yelerampura.math.R;
import com.yelerampura.math.base.BaseActivity;
import com.yelerampura.math.fragments.AboutFragment;
import com.yelerampura.math.fragments.EventFragment;
import com.yelerampura.math.fragments.GalleryFragment;
import com.yelerampura.math.fragments.MessageFragment;
import com.yelerampura.math.helper.SharedPrefsHelper;

import static com.yelerampura.math.helper.AppUtils.Constants.LANGUAGE;

public class MainActivity extends BaseActivity implements
        AboutFragment.OnFragmentInteractionListener,
        EventFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener,
        MessageFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
            Fragment curFrag = fragmentManager.getPrimaryNavigationFragment();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d(TAG, "onNavigationItemSelected: ");
                    if (curFrag != null) {
                        fragmentTransaction.detach(curFrag);
                    }
                    Fragment fragment = fragmentManager.findFragmentByTag("ABOUT");
                    if (fragment == null) {
                        fragment = new AboutFragment();
                        fragmentTransaction.add(R.id.contentFrame, fragment, "ABOUT");
                    } else {
                        fragmentTransaction.attach(fragment);
                    }
                    fragmentTransaction.setPrimaryNavigationFragment(fragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.commitNowAllowingStateLoss();

                    return true;

                case R.id.navigation_hitanudi:
                    if (curFrag != null) {
                        fragmentTransaction.detach(curFrag);
                    }
                    fragment = fragmentManager.findFragmentByTag("MSG");
                    if (fragment == null) {
                        fragment = new MessageFragment();
                        fragmentTransaction.add(R.id.contentFrame, fragment, "MSG");
                    } else {
                        fragmentTransaction.attach(fragment);
                    }
                    fragmentTransaction.setPrimaryNavigationFragment(fragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.commitNowAllowingStateLoss();
                    // mTextMessage.setText(R.string.title_dashboard);
                    return true;

                case R.id.navigation_dashboard:
                    if (curFrag != null) {
                        fragmentTransaction.detach(curFrag);
                    }
                    fragment = fragmentManager.findFragmentByTag("EVENT");
                    if (fragment == null) {
                        fragment = new EventFragment();
                        fragmentTransaction.add(R.id.contentFrame, fragment, "EVENT");
                    } else {
                        fragmentTransaction.attach(fragment);
                    }
                    fragmentTransaction.setPrimaryNavigationFragment(fragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.commitNowAllowingStateLoss();
                    // mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_gallery:
                    if (curFrag != null) {
                        fragmentTransaction.detach(curFrag);
                    }
                    fragment = fragmentManager.findFragmentByTag("GALLERY");
                    if (fragment == null) {
                        fragment = new GalleryFragment();
                        fragmentTransaction.add(R.id.contentFrame, fragment, "GALLERY");
                    } else {
                        fragmentTransaction.attach(fragment);
                    }
                    fragmentTransaction.setPrimaryNavigationFragment(fragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.commitNowAllowingStateLoss();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;

            }
            return false;
        }
    };
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_kannada) {
            showConfirmationDialog("ka");
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_english) {
            showConfirmationDialog("en");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showConfirmationDialog(final String language) {
        SharedPrefsHelper.getInstance().save(LANGUAGE, language);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle(R.string.confirmation);
        alertBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPrefsHelper.getInstance().save(LANGUAGE, language);
                //restartActivity();
                recreate();
            }
        });
        alertBuilder.setNegativeButton(getString(R.string.no), null);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
