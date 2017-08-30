package org.dolphy;

import java.util.*;

/*
 * Create the Student and Priorities classes here.
 */

class Student implements Comparable<Student> {
    private final Integer id;
    private final String name;
    private final Double cgpa;

    public Student(int id, String name, Double cgpa) {
        this.id = id;
        this.name = name;
        this.cgpa = cgpa;
    }

    public Integer getID() {
        return this.id;
    }

    String getName() {
        return this.name;
    }

    public Double getCGPA() {
        return this.cgpa;
    }

    @Override
    public int compareTo(Student o) {
        if (o.cgpa.equals(this.cgpa)) {
            if (o.name.equals(this.name)) {
                return o.id.compareTo(this.id);
            }
            return this.name.compareTo(o.name);
        }
        return o.cgpa.compareTo(this.cgpa);
    }

    @Override
    public String toString() {
        return this.name;
    }
}

class Priorities {
    private final Queue<Student> q = new PriorityQueue<>();

    List<Student> getStudents(List<String> events) {
        for (String e : events) {
            Scanner s = new Scanner(e);
            String cmd = s.next();
            if ("SERVED".equals(cmd)) {
                System.out.println(q);
                System.out.printf("Served: %s\n", q.poll());
            }
            if ("ENTER".equals(cmd)) {
                String name = s.next();
                double cgpa = s.nextDouble();
                int id = s.nextInt();
                q.add(new Student(id, name, cgpa));
            }
        }
        List<Student> l = new ArrayList<Student>();
        while (!q.isEmpty()) {
            l.add(q.poll());
        }
        return l;
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