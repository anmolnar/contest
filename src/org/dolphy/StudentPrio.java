package org.dolphy;

import java.util.*;

/*
 * Create the Student and Priorities classes here.
 */
class Student {
    private final int id;
    private final String name;
    private final double cgpa;

    public Student(int id, String name, double cgpa) {
        this.id = id;
        this.name = name;
        this.cgpa = cgpa;
    }

    public int getID() {
        return this.id;
    }

    String getName() {
        return this.name;
    }

    public double getCGPA() {
        return this.cgpa;
    }
}

class Priorities {
    private final Queue<Student> q = new PriorityQueue();

    List<Student> getStudents(List<String> events) {
        
    }
}

public class StudentPrio {
    private final static Scanner scan = new Scanner(System.in);
    private final static Priorities priorities = new Priorities();

    public static void main(String[] args) {
        int totalEvents = Integer.parseInt(scan.nextLine());
        List<String> events = new ArrayList<>();

        while (totalEvents-- != 0) {
            String event = scan.nextLine();
            events.add(event);
        }

        List<Student> students = priorities.getStudents(events);

        if (students.isEmpty()) {
            System.out.println("EMPTY");
        } else {
            for (Student st: students) {
                System.out.println(st.getName());
            }
        }
    }
}