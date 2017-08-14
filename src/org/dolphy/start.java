package org.dolphy;

import java.io.*;

public class start {
	public static void main(String[] args) {
		LifecycleWebServer server = new LifecycleWebServer();
		Thread serverThread = new Thread(new Runnable() {
			public void run() {
				try {
					server.start();
				} catch (IOException e) {
					System.out.println("IOException is server: " + e);
				}
			}
		});
		serverThread.start();
		System.out.println("Web server started");
		System.out.println("Press any key to stop");
		try {
            System.in.read();
        } catch(Exception e) {
			System.out.println("Error reading keyboard: " + e);
		}  
		System.out.println("Initiating shutdown...");
		serverThread.interrupt();
		server.stop();
	}
}

