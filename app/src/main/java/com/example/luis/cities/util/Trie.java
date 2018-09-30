package com.example.luis.cities.util;

import java.util.ArrayList;

public class Trie {

    final int ALPHABET_SIZE = 26;

    public TrieNode rootNode;

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
    // If the key is prefix of trie node,
    // just marks leaf node
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

    public String printSuggestions(TrieNode root,String prefix){

        TrieNode crawl= root;

        lstRes= new ArrayList<>();

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

            System.out.println(prefix);
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
                System.out.println(pref+"--ids--"+idd);
            }
        }

        if(isLastNode(crawl)) return;

        for (int i = 0; i < ALPHABET_SIZE; i++)
        {
            if (crawl.children[i] != null)
            {
                // append current character to currPrefix string
                char[] c = Character.toChars(97+i);
                //pref=;

                // recur over the rest
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



}
