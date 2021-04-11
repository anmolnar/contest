package org.dolphy;

import java.util.*;

class Sudoku {
    static int EMPTY = 0;

    public static void main(String[] args) {
        List<List<Integer>> board = new ArrayList<>();

        for (int j=0; j<9; j++) {
            List<Integer> row = new ArrayList<>();
            for (int i=0; i<9; i++)
                row.add(EMPTY);
            board.add(row);
        }

        solveSudoku(board, 9, 0, 0);
        
        System.out.println(board);     

    }

    static boolean solveSudoku(List<List<Integer>> board, int n, int i, int j) {
        if (i == n) {
            i = 0;
            j++;
            if (j == n) {
                return true;
            }
        }

        for (int val = 1; val <= n; val++) {
            if (validToAdd(board, n, i, j, val)) {
                board.get(j).set(i, val);
                if (solveSudoku(board, n, i+1, j))
                    return true;
            }
        }
        board.get(j).set(i, EMPTY);

        return false;
    }

    static boolean validToAdd(List<List<Integer>> board, int n, int i, int j, int val) {
        // Check row assignment
        List<Integer> row = board.get(j);
        for (int l = 0; l < n; l++)
            if (row.get(l) == val)
                return false;

        // Check column assignment
        for (int l = 0; l < n; l++) 
            if (board.get(l).get(i) == val)
                return false;

        // Check region assignment
        int regionSize = (int)Math.sqrt(n);
        int I = i / regionSize;
        int J = j / regionSize;
        for (int k = 0; k < regionSize; k++)
            for (int l = 0; l < regionSize; l++)
                if (board.get(J * regionSize + k).get(I * regionSize + l) == val)
                    return false;
    
        return true;
    }

}
