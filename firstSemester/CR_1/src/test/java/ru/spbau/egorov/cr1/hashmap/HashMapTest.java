package ru.spbau.egorov.cr1.hashmap;

import com.sun.xml.internal.bind.v2.util.QNameMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {
    @Test
    void putOneElement() {
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("aba", 1);
        assertEquals((Integer) 1, hm.get("aba"));
    }
}