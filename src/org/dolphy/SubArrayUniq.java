package org.dolphy;

import java.util.*;

public class SubArrayUniq {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Deque<Integer> deque = new ArrayDeque<>();
        Map<Integer, Integer> map = new HashMap<>();
        int n = in.nextInt();
        int m = in.nextInt();

        int qMax = 0;
        for (int i = 0; i < n; i++) {
            int num = in.nextInt();
            int removed = -1;
            if (map.containsKey(num)) {
                int val = map.get(num);
                map.put(num, val + 1);
            } else {
                map.put(num, 1);
            }
            deque.addLast(num);
            if (deque.size() > m) {
                removed = deque.removeFirst();
                int val = map.get(removed);
                if (val <= 1) {
                    map.remove(removed);
                } else {
                    map.put(removed, val - 1);
                }
            }
            if (deque.size() == m) {
                qMax = Math.max(qMax, map.size());
            }
            if (map.containsKey(7313026)) {
                System.out.printf("%d -> %d (%d , %d) 7313026 => %d\n", map.size(), deque.size(), removed, num, map.get(7313026));
            }
        }
        //System.out.println(map);
        System.out.println(qMax);
        System.out.println(deque.size());
    }
}
