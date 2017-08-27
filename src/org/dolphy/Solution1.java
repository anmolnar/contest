package org.dolphy;

import java.util.Scanner;

public class Solution1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine().trim();
        scan.close();
        // Write your code here.
        if (s.length() == 0) {
            System.out.println(0);
        } else {
            String[] tokens = s.split("[^A-Za-z]+");
            System.out.println(tokens.length);
            for (String t : tokens) {
                System.out.println(t);
            }
        }
    }
}
