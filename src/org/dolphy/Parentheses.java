package org.dolphy;

import java.util.*;

class Parentheses {

    public static void main(String []argh)
    {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String input=sc.next();
            //Complete the code
            System.out.println(isCorrect(input));
        }

    }

    private static Boolean isCorrect(String input) {
        if ("".equals(input)) {
            return true;
        }
        Stack<Character> p = new Stack<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(' ||
                    input.charAt(i) == '[' ||
                    input.charAt(i) == '{') {
                p.push(input.charAt(i));
            }
            if (input.charAt(i) == ')') {
                if (p.empty() || p.peek() != '(')
                {
                    return false;
                }
                p.pop();
            }
            if (input.charAt(i) == ']') {
                if (p.empty() || p.peek() != '[')
                {
                    return false;
                }
                p.pop();
            }
            if (input.charAt(i) == '}') {
                if (p.empty() || p.peek() != '{')
                {
                    return false;
                }
                p.pop();
            }
        }
        return p.empty();
    }
}
