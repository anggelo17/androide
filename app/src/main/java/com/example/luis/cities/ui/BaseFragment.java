package com.example.luis.cities.ui;


import android.support.v4.app.Fragment;

import com.example.luis.cities.BaseActivity;

public class BaseFragment extends Fragment{

    public void fragmentTransaction(String tagName, Fragment fragment, boolean addToBackStack, int style) {
        ((BaseActivity) getActivity()).fragmentTransaction(tagName, fragment, addToBackStack, style);
    }



}


