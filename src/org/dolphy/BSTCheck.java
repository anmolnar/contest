package org.dolphy;

import java.util.*;

class BSTCheck {
    static class Node {
        int data;
        Node left;
        Node right;
    }

    public static void main(String[] args) {
        int primes[] = { 1, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53 };
        System.out.println(Arrays.toString(primes));

        Node tree = new Node();
        tree.data = 1;

        for (int i=1; i<primes.length; i++)
            add(tree, primes[i]);
        
        inorder(tree);

        boolean result = check(tree, 0, 100);
        System.out.println(result);
    }

    static boolean check(Node tree, int min, int max) {
        if (tree == null)
            return true;
        if (tree.data < min || tree.data > max)
            return false;
        return check(tree.left, min, tree.data) && check(tree.right, tree.data, max);
    }

    static void add(Node node, int value) {
        if (value <= node.data) {
            if (node.left != null)
                add(node.left, value);
            else {
                node.left = new Node();
                node.left.data = value;
            }
        } else {
            if (node.right != null)
                add(node.right, value);
            else {
                node.right = new Node();
                node.right.data = value;
            }
        }
    }

    static void inorder(Node node) {
        if (node == null)
            return;
        inorder(node.left);
        System.out.println(node.data);
        inorder(node.right);
    }
}
