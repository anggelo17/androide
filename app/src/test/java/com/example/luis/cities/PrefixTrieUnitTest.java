package com.example.luis.cities;

import com.example.luis.cities.model.Data;
import com.example.luis.cities.util.Trie;

import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PrefixTrieUnitTest {

    Trie trie;


    public PrefixTrieUnitTest(){
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream("cities.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        trie=new Trie();
        trie.insertAll(in);
    }



    @Test
    public void prefix_search_unique_city(){

        String expect="Alabama";
        trie.printSuggestions(trie.rootNode,expect);
        String actual= trie.getListResults().get(0).getName();
        assertEquals(expect,actual);


    }

    @Test
    public void prefix_search_many_cities(){

//        4829764--US--Alabama
//        713724--UA--Alabash Konrat
//        4829762--US--Alabaster
//        1731749--PH--Alabat
        String prefix="Alaba";

        List<Data> expectList= Arrays.asList(new Data("4829764","Alabama","US"),new Data("713724","Alabash Konrat","UA"),
                new Data("4829762","Alabaster","US"),new Data("1731749","Alabat","PH"));

        trie.printSuggestions(trie.rootNode,prefix);
        List<Data> res= trie.getListResults();
        assertArrayEquals(expectList.toArray(),res.toArray());

    }


    @Test
    public void prefix_search_notFound(){

        String prefix="xxxx";
        trie.printSuggestions(trie.rootNode,prefix);
        List<Data> actual= trie.getListResults();
        assertEquals(Collections.EMPTY_LIST,actual);

    }
}
