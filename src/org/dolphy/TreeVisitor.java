package org.dolphy;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

import java.util.ArrayList;
import java.util.Scanner;

enum Color {
    RED, GREEN
}

abstract class Tree {

    private int value;
    private Color color;
    private int depth;

    Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

    private ArrayList<Tree> children = new ArrayList<>();

    TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {

    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis
{
    public abstract int getResult();
    public abstract void visitNode(TreeNode node);
    public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
    private int sum = 0;
    private int visited = 0;

    public int getResult() {
        //implement this
        return visited;
    }

    public void visitNode(TreeNode node) {
        //implement this
        visited += 1;
    }

    public void visitLeaf(TreeLeaf leaf) {
        //implement this
        //System.out.printf("Visit leaf: %d\n", leaf.getValue());
        sum += leaf.getValue();
        visited += 1;
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    private int product = 1;
    public int getResult() {
        //implement this
        return product;
    }

    public void visitNode(TreeNode node) {
        //implement this
        if (node.getColor() == Color.RED) {
            product *= node.getValue();
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        //implement this
        if (leaf.getColor() == Color.RED) {
            product *= leaf.getValue();
        }
    }
}

class FancyVisitor extends TreeVis {
    private int greenLeafSum = 0;
    private int nonLeafEvenSum = 0;

    public int getResult() {
        //implement this
        return Math.abs(greenLeafSum - nonLeafEvenSum);
    }

    public void visitNode(TreeNode node) {
        //implement this
        if (node.getDepth() % 2 == 0) {
            nonLeafEvenSum += node.getValue();
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        //implement this
        if (leaf.getColor() == Color.GREEN) {
            greenLeafSum += leaf.getValue();
        }
    }
}

public class TreeVisitor {
    private static Map<Integer, Tree> visited = new HashMap<>();

    private static Tree solve() {
        //read the tree from STDIN and return its root as a return value of this function
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        // Read values
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scan.nextInt();
        }
        // Read colors
        Color[] colors = new Color[n];
        for (int i = 0; i < n; i++) {
            if (scan.nextInt() == 0) {
                colors[i] = Color.RED;
            } else {
                colors[i] = Color.GREEN;
            }
        }
        // Read edges
        Map<Integer, List<Integer>> edges = new HashMap<>(n);
        for (int i = 0; i < n-1; i++) {
            int u = scan.nextInt() - 1;
            int v = scan.nextInt() - 1;
            if (!edges.containsKey(u)) {
                edges.put(u, new ArrayList<Integer>());
            }
            edges.get(u).add(v);
            System.out.printf("Edge: %d -> %d\n", u, v);
        }

        // Create nodes
        return createNode(0, 0, values, colors, edges);
    }

    static Tree createNode(int index, int depth, int[] values, Color[] colors, Map<Integer, List<Integer>> edges) {
        if (visited.containsKey(index)) {
            System.out.printf("Already visited: %d\n", index);
            return visited.get(index);
        }
        System.out.printf("Adding index: %d\n", index);
        Tree node;
        if (!edges.containsKey(index)) {
            node = new TreeLeaf(values[index], colors[index], depth);
        } else {
            node = new TreeNode(values[index], colors[index], depth);
            List<Integer> children = edges.get(index);
            System.out.printf("Children of %d: %s\n", index, children);
            for (int child  : children) {
                ((TreeNode)node).addChild(createNode(child, depth + 1, values, colors, edges));
            }
        }
        return node;
    }

    public static void main(String[] args) {
        Tree root = solve();
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
//        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
//        FancyVisitor vis3 = new FancyVisitor();

        root.accept(vis1);
//        root.accept(vis2);
//        root.accept(vis3);

        int res1 = vis1.getResult();
//        int res2 = vis2.getResult();
//        int res3 = vis3.getResult();

        System.out.println(res1);
//        System.out.println(res2);
//        System.out.println(res3);
    }
}
