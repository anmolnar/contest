package org.dolphy;

import java.util.Scanner;

class RegexSolution1 {

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while(in.hasNext()){
            String IP = in.next();
            System.out.println(IP.matches(new MyRegex().pattern));
        }

    }
}

class MyRegex {
    final String pattern = "(25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])\\." +
            "(25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])\\." +
            "(25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])\\." +
            "(25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])";
}

