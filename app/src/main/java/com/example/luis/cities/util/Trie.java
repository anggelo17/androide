package com.example.luis.cities.util;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.example.luis.cities.R;
import com.example.luis.cities.model.Data;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*

In order to perform prefix filtering I have implemented a Trie Structure where each node represents a letter
from a to z. Moreover each node has a flag endOfWord to tell the if the current node is the end of a word or not.
Since we can found cities with the same name even in the same country; we can differentiate them having a list of id
for the same name of  city. For example  city Athens,US and  city Athens,Greece ; every time we insert the word Athens we will
insert their id also because they are differents cities.
An arrayMap was also used because once we have the results(list of ids), we can get the whole information wit the id.
This map has an id as key and a object Data as value.

For retrieving the cities given a prefix we have to traverse the trie in alphabetic order; looking from
a to z; in that order . In this way we can get the cties ordered in alphabetic order. We will get a list of id as a result
. this List will be used for getting the whole Dat object info like name,country and coordinates.

Complexity for retrieving the cities given a prefix is O(P) + O(N); where P is the length of the prefix and
N is the total number of nodes in the trie.

 */


public class Trie {

    final int ALPHABET_SIZE = 26;

    public TrieNode rootNode;

    public ArrayMap<Integer,Data> arrayMap;



    public class TrieNode

    {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];


        boolean isEndOfWord;
        ArrayList<Integer> idlst;
    }


    public TrieNode createRoot(){

        if (rootNode==null) {
            rootNode = new TrieNode();

            rootNode.isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                rootNode.children[i] = null;
        }

        return rootNode;

    }

    public static ArrayList<Integer> lstRes=new ArrayList<>();


    // If not present, inserts key into trie
    // If the key is prefix of trie node, just mark it
    public void insert(TrieNode root,String key,int id)
    {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = root;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();

            pCrawl = pCrawl.children[index];
        }

        // mark last node as leaf
        pCrawl.isEndOfWord = true;
        if(pCrawl.idlst==null){
            pCrawl.idlst= new ArrayList<>();
            pCrawl.idlst.add(id);
        }
        else
            pCrawl.idlst.add(id);
    }

    private List<Data> initLst;

    public  List<Data> quickSort(List<Data> list)
    {
        if (list.size() <= 1)
            return list; // Already sorted

        List<Data> sorted = new ArrayList<Data>();
        List<Data> lesser = new ArrayList<Data>();
        List<Data> greater = new ArrayList<Data>();
        Data pivot = list.get(list.size()-1); // Use last as pivot
        for (int i = 0; i < list.size()-1; i++)
        {

            if (list.get(i).compareTo(pivot) < 0)
                lesser.add(list.get(i));
            else
                greater.add(list.get(i));
        }

        lesser = quickSort(lesser);
        greater = quickSort(greater);

        lesser.add(pivot);
        lesser.addAll(greater);
        sorted = lesser;

        return sorted;
    }

    public List<Data> initData(){

        initLst= quickSort(initLst);
        return initLst;

    }


    //insert all the data frm json file.
    public void insertAll(InputStream inputStream){

        Gson gs=new Gson();
        InputStream in = inputStream;

        BufferedReader buffer=new BufferedReader(new InputStreamReader(in));

        Data[] lst= gs.fromJson(buffer,Data[].class);

        initLst= Arrays.asList(lst);



        arrayMap=new ArrayMap<>();
        TrieNode root= this.createRoot();

        for (Data d:lst) {
            this.insert(root,d.getName().toLowerCase().replaceAll("[^a-z]", ""),Integer.parseInt(d.get_id()));
            arrayMap.put(Integer.parseInt(d.get_id()),d);
        }

    }

    //given the root and prefix, this will traverse the trie and add the id to a list of results.

    public String printSuggestions(TrieNode root,String prefix){

        TrieNode crawl= root;

        lstRes= new ArrayList<>();

        prefix = prefix.toLowerCase();

        int level;
        for(level=0;level<prefix.length();level++){

            int index= prefix.charAt(level)-'a';
            if(crawl.children[index]==null){
                return "Not Found";
                //return lst;
            }

            crawl= crawl.children[index];
        }

        Boolean isWord= (crawl.isEndOfWord==true);
        Boolean isLast= isLastNode(crawl);

        if(isWord && isLast){
            for(int idd:crawl.idlst)
                lstRes.add(idd);

           // System.out.println(prefix);
            return "Found 1";
        }

        if(!isLast){
            String pref= prefix;
            suggestion(crawl,pref);
            return "Found more than 1";

        }

        return null;



    }

    private void suggestion(TrieNode crawl, String pref) {
        // TODO Auto-generated method stub
        if(crawl.isEndOfWord){
            for(int idd:crawl.idlst){
                lstRes.add(idd);
                //System.out.println(pref+"--ids--"+idd);
            }
        }

        if(isLastNode(crawl)) return;

        for (int i = 0; i < ALPHABET_SIZE; i++)
        {
            if (crawl.children[i] != null)
            {

                char[] c = Character.toChars(97+i);

                suggestion(crawl.children[i], pref+ c[0]);
            }
        }

    }

    private Boolean isLastNode(TrieNode crawl) {
        // TODO Auto-generated method stub
        for (int i = 0; i < ALPHABET_SIZE; i++)
            if (crawl.children[i]!=null)
                return false;
        return true;
    }

    public List<Data> getListResults(){

        List<Data> filteredList = new ArrayList<>();

        for (int k = 0; k < this.lstRes.size(); k++) {

            Data d = (Data) arrayMap.get(this.lstRes.get(k));
            filteredList.add(d);

        }

        return filteredList;
    }



}
