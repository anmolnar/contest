package org.dolphy;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

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

        Deque<String> dekk = new ArrayDeque<>();
        dekk.add("andor");
        dekk.addFirst("bela");
        System.out.println("\nDeque = " + dekk);
        System.out.println("Peek = " + dekk.peekLast());

        TestHarness harness = new TestHarness();
        try {
            long elapsed = harness.timeTasks(10, new Runnable() {
                @Override
                public void run() {
                    System.out.println("hello");
                }
            });
            System.out.println("Elapsed = " + elapsed);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
}
