package com.example.luis.cities;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.luis.cities.ui.FragmentOne;

public class ScrollingActivity extends BaseActivity{


   private FragmentOne fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);


        showFirstFragment();

    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();

    }



    private void showFirstFragment() {
        FragmentOne f1=new FragmentOne();
        fragment=f1;
        fragmentTransaction(FragmentOne.class.getSimpleName(),f1,false,1);
    }


    private void whiteBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }



}
