package org.dolphy;

import java.util.Scanner;

public class GameOfTwoStacks {

    static int moveMax = 0;
    static int sumMax = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int g = in.nextInt();
        for(int a0 = 0; a0 < g; a0++){
            int n = in.nextInt();
            int m = in.nextInt();
            int x = in.nextInt();

            moveMax = 0;
            sumMax = x;

            int[] a = new int[n];
            for(int a_i=0; a_i < n; a_i++){
                a[a_i] = in.nextInt();
            }
            int[] b = new int[m];
            for(int b_i=0; b_i < m; b_i++){
                b[b_i] = in.nextInt();
            }
            // your code goes here

            doMove(a, b, 0, 0, 0, 0);

            System.out.println(moveMax);
        }
    }

    private static void doMove(int[] a, int[] b, int an, int bn, int depth, int sum) {
        if (sum > sumMax) {
            if (depth-1 > moveMax) {
                moveMax = depth-1;
            }
            return;
        }

        if (an < a.length) {
            doMove(a, b, an+1, bn, depth+1, sum + a[an]);
        }

        if (bn < b.length) {
            doMove(a, b, bn, bn+1, depth+1, sum + b[bn]);
        }
    }

}