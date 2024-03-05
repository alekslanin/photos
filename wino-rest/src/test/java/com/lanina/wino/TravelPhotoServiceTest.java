package com.lanina.wino;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TravelPhotoServiceTest {

    @Test
    void getLocations() {
        var a = "hello my name is bob";
        var b = "this is my friend bob";
        var result = getCommon(a, b);

        assertNotNull(result);
        assertTrue(result.contains("bob"));
        assertTrue(result.contains("is"));
        assertFalse(result.contains("hello"));
    }

    List<String> getCommon(String a, String b) {
        var lista = Arrays.stream(a.split(" ")).sorted().toList();
        var listb = Arrays.stream(b.split(" ")).sorted().toList();
        // bob hello is my name
        // bob friend is my this

        var retval = new ArrayList<String>();
        lista.forEach(x -> {
            for( int i = 0; i != listb.size(); i++) {
                var y = listb.get(i);
                if(x.equals(y)) {
                    retval.add(x);
                } else if(x.indexOf(0) > y.indexOf(0)) {
                    break;
                }
            };
        });

        return retval;
    }

    // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, and 55
    @Test
    void getFib() {
        assertEquals(0, fibonacci(0));
        assertEquals(1, fibonacci(1));
        assertEquals(1, fibonacci(2));
        assertEquals(2, fibonacci(3));
        assertEquals(55, fibonacci(10));
    }

    static int fibonacci(int n) {
        return (n < 2) ? n : calc(n);
    }

    static int calc(int n) {
        int n0 = 0, n1 = 1;
        for (int i = 2; i <= n; i++) {
            var temp = n0 + n1;
            n0 = n1;
            n1 = temp;
        }
        return n1;
    }

    void collection() {
        Map m = Collections.synchronizedMap(new LinkedHashMap());

    }
}