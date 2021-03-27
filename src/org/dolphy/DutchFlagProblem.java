package org.dolphy;

import java.util.*;

class DutchFlagProblem {

    public static void main(String[] args) {
        int n = 50;
        int[] arr = new int[n];

        Random rnd = new Random();
        int pivot = 0;

        for (int i=0; i<n; i++) {
            if (i > 45)
                arr[i] = pivot;
            else
                arr[i] = rnd.nextInt() % 100;
        }

        System.out.println(Arrays.toString(arr));

        int low = 0;
        int high = n - 1;
   
        int i = 0;
        while (i <= high) {
            if (arr[i] > pivot) {
                swap(arr, i, high);
                high--;
            } else if (arr[i] == pivot) {
                i++;
            } else {
                swap(arr, i, low);
                low++;
                i++;
            }
        }

        System.out.println("Low = " + low);
        System.out.println("High = " + high);
        System.out.println(Arrays.toString(arr));
    }

    static void swap(int[] a, int i, int j) {
        int s = a[i];
        a[i] = a[j];
        a[j] = s;
    }
}
