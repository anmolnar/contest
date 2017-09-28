package org.dolphy;

import java.util.Scanner;

public class GameOfTwoStacks {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int g = in.nextInt();
        for(int a0 = 0; a0 < g; a0++){
            System.out.printf("Loading game %d\n", a0);

            int n = in.nextInt();
            int m = in.nextInt();
            int x = in.nextInt();

            int[] a = new int[n];
            for(int a_i=0; a_i < n; a_i++){
                a[a_i] = in.nextInt();
            }
            int[] b = new int[m];
            for(int b_i=0; b_i < m; b_i++){
                b[b_i] = in.nextInt();
            }
            // your code goes here
            System.out.printf("Playing game ... %d %d\n", a0, x);

            int sum = 0;
            int i = 0;
            int j = 0;
            int count = 0;

            while (i < n && sum+a[i] <= x) {
                sum += a[i];
                i++;
            }
            count = i;

            while (j < m) {
                sum += b[j];
                j++;

                while (sum > x && i > 0) {
                    i--;
                    sum -= a[i];
                }

                if (sum <= x && i+j > count) {
                    count = i+j;
                }
            }

            System.out.println(count);
        }
    }


}