package org.dolphy;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.*;

class LifecycleWebServer {
	private final int N_THREADS = 2;
	private final ExecutorService exec = new CancellingExecutor(N_THREADS, N_THREADS,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    private final Queue<Future<Object>> clients = new ConcurrentLinkedQueue<Future<Object>>();
	private ServerSocket socket;

	void start() throws IOException {
		socket = new ServerSocket(6000);
		System.out.println("Server is listening on port 6000");
		while (!exec.isShutdown()) {
			try {
				System.out.println("Waiting for accept...");
				final Socket connection = socket.accept();
				System.out.println("Accepted");
				clients.add(exec.submit(new SocketUsingTask<Object>() {
                    @Override
                    public Object call() throws Exception {
                        setSocket(connection);
						try {
							handleRequest(connection);
						} catch (IOException e) {
							System.out.println("IOException in task: " + e);
						}
						return null;
                    }
                }));
			} catch (RejectedExecutionException e) {
				if (!exec.isShutdown())
					System.out.println("task submission rejected: " + e);
			}
		}
	}

	void stop() {
		// Closing server socket
		try { socket.close(); } catch (IOException ignored) {}

		// Closing clients
        while (!clients.isEmpty()) {
		    clients.poll().cancel(true);
        }

    	exec.shutdown();
		System.out.println("Server terminated");
	}

	private void handleRequest(Socket connection) throws IOException {
		System.out.printf("Accepted socket: %s on thread id %d\n", connection.getRemoteSocketAddress(), Thread.currentThread().getId());
		InputStream stream = connection.getInputStream();
		OutputStream outbound = connection.getOutputStream();
		int i = 0;
		byte[] buffer = new byte[1024];
		while ((i = stream.read(buffer, 0, buffer.length)) > 0) {
			System.out.printf("Read bytes from stream: %d\n", i);
    		outbound.write(buffer, 0, i);
			outbound.flush();
		}
		connection.close();
		System.out.println("Connection closed");
	}
}
