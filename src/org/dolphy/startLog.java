package org.dolphy;

import java.io.*;

public class startLog {
	public static void main(String[] args) {
		try {
			PrintWriter writer = new PrintWriter(System.out);
			LogWriter log = new LogWriter(writer);
			log.start();
			log.log("Hello!");
			log.log("Hello from the other side!");
			System.out.println("Press any key to stop");
			try {
        	    System.in.read();
	        } catch(Exception e) {
				System.out.println("Error reading keyboard: " + e);
			}  
			log.stop();
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
}
