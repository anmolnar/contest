package org.dolphy;

import java.util.*;

class BinarySearchFirst {
    public static void main(String[] args) {
        int n = 50;
        int[] arr = new int[n];
        Random rnd = new Random();
        for (int i=0; i<arr.length; i++)
            arr[i] = rnd.nextInt() % 100;

        int a = arr[0];
        
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));            

        int i = binarySearchFirst(arr, a);

        System.out.printf("a = %d, i = %d, a[i]=%d a[i-1]=%d\n", a, i, arr[i], arr[i-1]);       
    }

    static int binarySearchFirst(int[] arr, int a) {
        int L = 0;
        int H = arr.length - 1;

        while (L <= H) {
            int M = L + (H - L) / 2;
            System.out.printf("L = %d M = %d H = %d\n", L, M, H);
            if (arr[M] < a)
                L = M + 1;
            if (arr[M] >= a)
                H = M - 1;
        }

        return L;
    }
}

