import java.io.*;
import java.net.*;

public class SingleThreadWebServer {	
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(6000);
		System.out.println("Server is listening on port 6000");
		while (true) {
			Socket connection = socket.accept();
			handleRequest(connection);
		}
	}

	private static void handleRequest(Socket connection) throws IOException {
		System.out.printf("Accepted socket: %s\n", connection.getRemoteSocketAddress());
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

