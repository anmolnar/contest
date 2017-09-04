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

    public int getResult() {
        //implement this
        return sum;
    }

    public void visitNode(TreeNode node) {
        //implement this
    }

    public void visitLeaf(TreeLeaf leaf) {
        //implement this
        sum += leaf.getValue();
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    private long product = 1;
    private int m = (int)Math.pow(10, 9) + 7;

    public int getResult() {
        //implement this
        return (int)product;
    }

    public void visitNode(TreeNode node) {
        //implement this
        if (node.getColor() == Color.RED) {
            product = (product * node.getValue()) % m;
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        //implement this
        if (leaf.getColor() == Color.RED) {
            product = (product * leaf.getValue()) % m;
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
    private static Set<Integer> visited = new HashSet<Integer>();

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
        Map<Integer, Set<Integer>> edges = new HashMap<>(n);
        for (int i = 0; i < n-1; i++) {
            int u = scan.nextInt() - 1;
            int v = scan.nextInt() - 1;
            if (!edges.containsKey(u)) {
                edges.put(u, new HashSet<Integer>());
            }
            if (!edges.containsKey(v)) {
                edges.put(v, new HashSet<Integer>());
            }
            edges.get(u).add(v);
            edges.get(v).add(u);
        }

        // Create nodes
        return createNode(0, 0, values, colors, edges);
    }

    static Tree createNode(int index, int depth, int[] values, Color[] colors, Map<Integer, Set<Integer>> edges) {
        if (visited.contains(index)) {
            return null;
        }
        visited.add(index);
        Tree node;
        if (!edges.containsKey(index)) {
            node = new TreeLeaf(values[index], colors[index], depth);
        } else {
            Set<Integer> childEdges = edges.get(index);
            Set<Tree> children = new HashSet<Tree>();
            for (int childIndex : childEdges) {
                Tree childNode = createNode(childIndex, depth + 1, values, colors, edges);
                if (childNode != null) {
                    children.add(childNode);
                }
            }
            if (children.isEmpty()) {
                node = new TreeLeaf(values[index], colors[index], depth);
            } else {
                node = new TreeNode(values[index], colors[index], depth);
                for (Tree childNode : children) {
                    ((TreeNode) node).addChild(childNode);
                }
            }
        }
        return node;
    }

    public static void main(String[] args) {
        Tree root = solve();
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();

        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);

        int res1 = vis1.getResult();
        int res2 = vis2.getResult();
        int res3 = vis3.getResult();

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}
