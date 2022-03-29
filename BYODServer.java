import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;


public class BYODServer {
	
	private static final String CORRECT_USER_NAME = "golfoman";
	private static final String CORRECT_PASSWORD = "golfoman";

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException,          
                           InterruptedException {

		
		// perpetually listen for clients
		SSLServerSocketFactory socketFactory = (SSLServerSocketFactory)
		SSLServerSocketFactory.getDefault();
		SSLServerSocket serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
		while (true) {

		// wait for client connection and check login information
		try {
		System.err.println("Waiting for connection...");
						
		Socket socket = serverSocket.accept();

		// open BufferedReader for reading data from client
		BufferedReader input = new BufferedReader(new
                               InputStreamReader(socket.getInputStream()));
			// open PrintWriter for writing data to client
			PrintWriter output = new PrintWriter(new 
                        OutputStreamWriter(socket.getOutputStream()));
			String userName = input.readLine();
			String password = input.readLine();
			String message = input.readLine();
			FileWriter fichero = null;
			if (userName.equals(CORRECT_USER_NAME)								&& password.equals(CORRECT_PASSWORD)) {
		//		output.println("Welcome, " + userName);
				
				output.println("Se ha recibido el siguiente mensaje: "+ message);
				
				try {
				DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
				fichero = new FileWriter("mensajes\\mensajes.txt",true);
				fichero.write(dtf2.format(LocalDateTime.now())+"; "+userName+": "+message+"\n");
				fichero.close();
				} catch (Exception ex) {
					System.out.println("Mensaje de la excepción: " + ex.getMessage());
				}
				
			} else {
				output.println("Login Failed.");
			}
			

		output.close();
		input.close();
		socket.close();

		} // end try

		// handle exception communicating with client
		catch (IOException ioException) {
			ioException.printStackTrace();
		}

	} // end while

   }

}
