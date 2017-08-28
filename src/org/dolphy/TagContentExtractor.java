package org.dolphy;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagContentExtractor {
    public static void main(String[] args){
        String regex = "<(\\p{Print}+)>([^<>]+)</\\1>";
        Pattern p = Pattern.compile(regex);

        Scanner in = new Scanner(System.in);
        int testCases = Integer.parseInt(in.nextLine());
        while(testCases>0){
            String line = in.nextLine();
            Matcher m = p.matcher(line);

            //Write your code here
            int count = 0;
            while (m.find()) {
                count++;
                for (int i = 2; i <= m.groupCount(); i += 2) {
                    System.out.println(m.group(i));
                }
            }
            if (count == 0) {
                System.out.println("None");
            }

            testCases--;
        }
    }
}
