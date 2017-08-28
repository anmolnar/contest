package org.dolphy;

import java.util.Scanner;

public class HourGlass {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a[][] = new int[6][6];
        for(int i=0; i < 6; i++){
            for(int j=0; j < 6; j++){
                a[i][j] = in.nextInt();
            }
        }

        int largest = -10000;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int sum = calc(a, i, j);
                System.out.println(sum);
                if (sum > largest) {
                    largest = sum;
                }
            }
        }
        System.out.println(largest);
    }

    private static int calc(int[][] a, int x, int y) {
        int sum = 0;
        for (int j = y; j < y + 3; j++) {
            sum += a[x][j];
        }
        sum += a[x+1][y+1];
        for (int j = y; j < y + 3; j++) {
            sum += a[x+2][j];
        }
        return sum;
    }
}
