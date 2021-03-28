import java.util.*;
import java.io.*;

class FileMerge {

    static class Element {
        Scanner scanner;
        String line;

        Element(Scanner scanner, String line) {
            this.scanner = scanner;
            this.line = line;
        }

        @Override
        public String toString() {
            return line;
        }
    }

    static PriorityQueue<Element> minHeap;
    
    public static void main(String[] args) throws IOException {
        System.out.println("This is FileMerge");

        int numFile = args.length;
        System.out.println("Merging " + numFile + " number of files");
        if (numFile < 2) {
            System.err.println("Not enough files. At least 2 needed");
            System.exit(1);
        }

        minHeap = new PriorityQueue<>(numFile, new Comparator<Element>() {
            @Override
            public int compare(Element e1, Element e2) {
                return e1.line.compareTo(e2.line);
            }
        });

        
        for (String fn : args) {
            Scanner s = new Scanner(new File(fn));
            if (s.hasNextLine())
                minHeap.add(new Element(s, s.nextLine()));
        }

        FileWriter result = new FileWriter("result.txt");
        while (minHeap.size() > 0) {
            Element e = minHeap.poll();
            if (e.line.length() > 0) {
                result.write(e.line);
                result.write("\n");
            }
            if (e.scanner.hasNextLine()) {
                e.line = e.scanner.nextLine();
                minHeap.add(e);
            }
        }

        result.close(); 
    }
}

