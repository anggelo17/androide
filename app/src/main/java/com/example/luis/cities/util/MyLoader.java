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

        Gson gs=new Gson();
        InputStream in = null;
        try {
            in = getContext().getResources().openRawResource(R.raw.cities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader buffer=new BufferedReader(new InputStreamReader(in));

        Data[] lst= gs.fromJson(buffer,Data[].class);


        Log.d("lst","---finish reading file"+lst[0].getName());


        ArrayMap<Integer,Data> arrayMap=new ArrayMap<>();
        Trie trie = new Trie();
        trie.createRoot();

        for (Data d:lst) {
            trie.insert(trie.rootNode,d.getName().toLowerCase().replaceAll("[^a-z]", ""),Integer.parseInt(d.get_id()));
            arrayMap.put(Integer.parseInt(d.get_id()),d);
        }

        this.trie=trie;
        this.arrMap= arrayMap;

        List<Data> lista= Arrays.asList(lst);
        Log.d("lst","---finish filling map"+lista.get(0).getName());


        long s=System.nanoTime();
        String comp = trie.printSuggestions(trie.rootNode,"sydney");
        Log.d("trie","--"+comp);
        double e= (double)(System.nanoTime()-s) / 1000000000.0;
        Log.d("trie","time=="+ e+" size=="+trie.lstRes.size());

        for(int k=0;k<trie.lstRes.size();k++){

            Data d=arrayMap.get(trie.lstRes.get(k));
            Log.d("trie",d.get_id()+"--"+d.getCountry()+"--"+d.getName() );

        }




        Collections.sort(lista,new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                int r= o1.getName().compareTo(o2.getName());
                if(r==0)
                    return o1.getCountry().compareTo(o2.getCountry()) ;
                else
                    return r;
            }
        });

        Log.d("lst","---finish sorting"+lista.get(0).getName());


        return lista;
    }

    @Override
    public void deliverResult(List<Data> data) {
        cacheData=data;
        super.deliverResult(data);
    }
}
