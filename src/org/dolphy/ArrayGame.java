package org.dolphy;

import java.util.*;

public class ArrayGame {
    private static List<Integer> visited = new ArrayList<>();

    private static boolean canWin(int pos, int leap, int[] game) {
        // Return true if you can win the game; otherwise, return false.
        if (visited.contains(pos)) {
            return false;
        }
        visited.add(pos);
        int n = game.length;
        if (pos == n - 1 || pos + leap >= n) {
            return true;
        }
        if (pos > 0 && game[pos-1] == 0) {
            if (canWin(pos-1, leap, game)) {
                return true;
            }
        }
        if (game[pos + 1] == 0) {
            if (canWin(pos+1, leap, game)) {
                return true;
            }
        }
        if (game[pos + leap] == 0) {
            if (canWin(pos+leap, leap, game)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int q = scan.nextInt();
        while (q-- > 0) {
            visited.clear();
            int n = scan.nextInt();
            int leap = scan.nextInt();

            int[] game = new int[n];
            for (int i = 0; i < n; i++) {
                game[i] = scan.nextInt();
            }

            System.out.println( (canWin(0, leap, game)) ? "YES" : "NO" );
        }
        scan.close();
    }
}
