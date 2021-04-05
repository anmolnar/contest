package org.dolphy;

import java.util.*;

class StringGraph {
    static class Vertex {
        String word;
        List<Vertex> vertices;

        Vertex(String w) {
            this.word = w;
            this.vertices = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        List<String> dict = new ArrayList<>(List.of("bat", "cot", "dog", "dag", "dot", "cat", "bag", "bor", "bir"));
        Map<Integer, List<Integer>> edges = new HashMap<>();

        String a = "cat";
        String b = "dog";
        int ai = -1;
        int bi = -1;

        for (int i=0; i<dict.size()-1; i++) {
            if (ai == -1 && a.equals(dict.get(i)))
                ai = i;
            if (bi == -1 && b.equals(dict.get(i)))
                bi = i;
            for (int j=i+1; j<dict.size(); j++) 
                if (isAdjacent(dict.get(i), dict.get(j))) {
                    List<Integer> fromI = edges.get(i);
                    if (fromI == null) {
                        fromI = new ArrayList<>();
                        edges.put(i, fromI);
                    }
                    fromI.add(j);

                    List<Integer> fromJ = edges.get(j);
                    if (fromJ == null) {
                        fromJ = new ArrayList<>();
                        edges.put(j, fromJ);
                    }
                    fromJ.add(i);
                }
        }

        System.out.println(edges);

        List<String> path = new ArrayList<>();
        System.out.printf("%s -> %s = %b\n", a, b, produces(dict, edges, ai, bi, path));
        System.out.println(path);
       
    }

    static boolean produces(List<String> dict, Map<Integer, List<Integer>> edges, int ai, int bi, List<String> path) {
        if (ai == -1 || bi == -1)
            return false;

        Queue<Integer> bfsHelper = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        bfsHelper.add(ai);
        while (bfsHelper.peek() != null) {
            ai = bfsHelper.remove();
            path.add(dict.get(ai));
            if (ai == bi)
                return true;
            List<Integer> adjacents = edges.get(ai);
            boolean added = false;
            if (adjacents != null) {
                for (Integer aa : adjacents)
                    if (!visited.contains(aa)) {
                        bfsHelper.add(aa);
                        added = true;
                    }
            }
            if (!added)
                path.remove(ai);
            visited.add(ai);
        }

        return false;
    }

    static Vertex buildGraph(List<String> dict) {
        Queue<Vertex> words = new LinkedList<>();
        Vertex root = new Vertex(dict.get(0));
        dict.remove(0);
        int i = 0;
        Vertex current = root;
        while (dict.size() > 0) {
            if (i >= dict.size()) {
                current = words.remove();
                i=0;
            }
            String nextWord = dict.get(i);
            if (isAdjacent(current.word, nextWord)) {
                Vertex v = new Vertex(nextWord);
                words.add(v);
                current.vertices.add(v);
                dict.remove(i);
            } else {
                i++;
            }
        }
        return root;
     }

    static void printGraph(Vertex root, int depth) {
        if (root != null) {
            for (int i=0; i<depth; i++)
                System.out.print(" ");
            System.out.println(root.word);
            for (Vertex v : root.vertices) 
                printGraph(v, depth + 4);
        }
    }

    static boolean isAdjacent(String a, String b) {
        if (a.length() != b.length())
            return false;

        int diff = 0;
        for (int i=0; diff <= 1 && i<a.length(); i++)
            if (a.charAt(i) != b.charAt(i))
                diff++;
        
        return diff == 1;
    }
}
