package org.dolphy;

import java.math.BigDecimal;
import java.util.*;

class SortBigDecimal {

    public static void main(String[] args) {
        //Input
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] s = new String[n + 2];
        for (int i = 0; i < n; i++) {
            s[i] = sc.next();
        }
        sc.close();

        Arrays.sort(s, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (Objects.equals(o1, o2)) {
                    return 0;
                }
                if (o1 == null || o1.isEmpty()) {
                    return 1;
                }
                if (o2 == null || o2.isEmpty()) {
                    return -1;
                }
                return new BigDecimal(o2).compareTo(new BigDecimal(o1));
            }
        });

        //Output
        for(int i=0;i<n;i++)
        {
            System.out.println(s[i]);
        }
    }
}
