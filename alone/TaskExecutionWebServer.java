import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TaskExecutionWebServer {	
	private static final int N_THREADS = 2;
	private static final Executor exec = Executors.newFixedThreadPool(N_THREADS);

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(6000);
		System.out.println("Server is listening on port 6000");
		while (true) {
			final Socket connection = socket.accept();
			Runnable task = new Runnable() {
					public void run() {
						try {
							handleRequest(connection);
						} catch (IOException e) {
							System.out.println("IOException has been thrown in task" + e);
						}
					}
				};
			exec.execute(task);
		}
	}

	private static void handleRequest(Socket connection) throws IOException {
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

