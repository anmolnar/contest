package org.dolphy;

import java.util.*;

class QuickSort {
    public static void main(String[] args) {
        int n = 50;
        int[] arr = new int[n];
        Random rnd = new Random();
        for (int i=0; i<arr.length; i++)
            arr[i] = rnd.nextInt() % 100;

        System.out.println(Arrays.toString(arr));            

        quicksort(arr, 0, n-1);

        System.out.println(Arrays.toString(arr));
    }

    static void quicksort(int[] arr, int min, int max) {
        if (min >= max)
           return;
        int mid = partition(arr, min, max);
        quicksort(arr, min, mid-1);
        quicksort(arr, mid+1, max);
    }

    static int partition(int[] arr, int min, int max) {
        int pivot = arr[max];
        int i = min;
        for (int j = min; j < max; j++) {
            if (arr[j] < pivot) 
                swap(arr, i++, j);                
        }
        swap(arr, i, max);
        return i;
    }

    static void swap(int[] arr, int i, int j) {
        int s = arr[i];
        arr[i] = arr[j];
        arr[j] = s;
    }
}
