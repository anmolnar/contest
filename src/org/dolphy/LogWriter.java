package org.dolphy;

import java.io.*;
import java.util.concurrent.*;

public class LogWriter {
	private final int CAPACITY = 10;
	private final BlockingQueue<String> queue;
	private final LoggerThread logger;
	private boolean isShutdown;
	private int reservations;

	public LogWriter(PrintWriter writer) {
		this.queue = new LinkedBlockingQueue<String>(CAPACITY);
		this.logger = new LoggerThread(writer);
	}

	public void start() { logger.start(); }

	public void stop() {
		synchronized (this) {
			isShutdown = true;
		}
		logger.interrupt();
	}

	public void log(String msg) throws InterruptedException {
		synchronized (this) {
			if (isShutdown)
				throw new IllegalStateException();
			++reservations;
		}
		System.out.println("new message: " + msg);
		queue.put(msg);
	}

	private class LoggerThread extends Thread {
		private final PrintWriter writer;

		public LoggerThread(PrintWriter writer) {
			this.writer = writer;
		}

		public void run() {
			try {
				while (true) {
					try {
						synchronized (LogWriter.this) {
							if (isShutdown && reservations == 0)
								break;
						}
						System.out.println("Waiting for element");
						String msg = queue.take();
						synchronized (LogWriter.this) {
							--reservations;
						}
						writer.println(msg);
						writer.flush();
					} catch (InterruptedException e) { /* retry */ }
				}	
			} finally {
				writer.close();
			}			
		}
	}
}


