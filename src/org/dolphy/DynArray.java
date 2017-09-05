package org.dolphy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DynArray {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int q = scan.nextInt();

        List<List<Integer>> seqList = new ArrayList<List<Integer>>();
        int lastAnswer = 0;

        for (int i = 0; i < n; i++) {
            seqList.add(new ArrayList<Integer>());
        }

        System.out.printf("seqList size: %d\n", seqList.size());

        while (scan.hasNext()) {
            int cmd = scan.nextInt();
            int x = scan.nextInt();
            int y = scan.nextInt();
            if (cmd == 1) {
                seqList.get((x ^ lastAnswer) % n).add(y);
                System.out.printf("Added %d to sequence %d\n", y, (x ^ lastAnswer) % n);
            }
            if (cmd == 2) {
                System.out.printf("Get sequence %d\n", (x ^ lastAnswer) % n);
                List<Integer> seq = seqList.get((x ^ lastAnswer) % n);
                System.out.printf("Get element %d from sequence %d\n", y % seq.size(), (x ^ lastAnswer) % n);
                lastAnswer = seq.get(y % seq.size());
                System.out.println(lastAnswer);
            }
        }
    }
}
