package org.dolphy;

import java.util.Arrays;
import java.util.Scanner;

public class NegativeSubArrays {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scan = new Scanner(System.in);
        int l = scan.nextInt();
        int[] a = new int[l];
        for (int i = 0; i < l; i++) {
            a[i] = scan.nextInt();
        }
        scan.close();

        int count = 0;
        for (int s = 1; s <= a.length; s++) {
            for (int i =0; i <= a.length - s; i++) {
                if (arraySum(Arrays.copyOfRange(a, i, i + s)) < 0) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    private static int arraySum(int[] a) {
        int sum = 0;
        for (int anA : a) {
            sum += anA;
        }
        return sum;
    }
}
