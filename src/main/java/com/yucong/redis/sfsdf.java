package com.yucong.redis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class sfsdf {

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 11);
        map.put("b", 22);
        map.put("c", 33);
        map.put("d", 44);

        Collection<Object> values = map.values();

        System.out.println(values);
    }
}
