package com.example.luis.cities.util;


import android.content.Context;

import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.example.luis.cities.R;
import com.example.luis.cities.model.Data;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MyLoader extends AsyncTaskLoader<List<Data>> {

    private List<Data> cacheData;
    public Trie trie;
    public ArrayMap arrMap;


    public MyLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if(cacheData==null)
            forceLoad();
        else
            super.deliverResult(cacheData);
    }

    @Override
    public List<Data> loadInBackground() {

        InputStream in = null;
        try {
            in = getContext().getResources().openRawResource(R.raw.cities);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Trie trie = new Trie();
        trie.insertAll(in);
        this.trie=trie;

        List<Data> lista= trie.initData();
        Log.d("lst","---finish filling map"+lista.get(0).getName());

        return lista;
    }

    @Override
    public void deliverResult(List<Data> data) {
        cacheData=data;
        super.deliverResult(data);
    }
}
