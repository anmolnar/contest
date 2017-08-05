package org.dolphy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Andor!");

        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<String, Integer>());
        Map<String, Integer> myMap = new HashMap<String, Integer>();

        myMap.put("andor", 3);
        myMap.put("bela", 5);

        System.out.printf("Andor = %d\n", myMap.get("andor"));

        myMap.forEach((k,v) -> {
            System.out.printf("%s => %d\n", k, v);
        });

        System.out.println("\n=== Testing Hidden iterator ===");

        HiddenIterator it = new HiddenIterator();
        it.addTenThings();
    }
}
