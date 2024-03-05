package com.lanina.wino;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrieTest {
    @Test
    public void testTrie() {
        Trie trie = new Trie();
        trie.insert("cat");
        trie.insert("car");
        trie.insert("cart");
        trie.insert("cartoon");

        assertTrue(trie.contains("cart"));
        assertFalse(trie.contains("dog"));
        assertTrue(trie.startsWith("ca"));
        assertFalse(trie.startsWith("d"));

        var g = new int[2][3];
        g[0][0] = 0;
        g[0][1] = 0;
        g[0][2] = 1;
        g[1][0] = 1;
        g[1][1] = 0;
        g[1][2] = 1;
        int counter = 0;
        int total = 0;
        for(int i = 0; i != g.length; i++) {
            for(int j = 0; j != g[i].length; j++) {
                if(g[i][j] != 0) counter++;
                total++;
            }
        }

        double a = (double) counter /total;
        System.out.println("" + counter);
        System.out.println("" + total);
        System.out.println("" + a);
    }
}