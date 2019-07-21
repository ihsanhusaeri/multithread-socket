import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
	private static ServerSocket serverSocket;
	private static final int PORT = 5000;
	
	public static void main(String[] args) throws IOException{
		try{
			serverSocket = new ServerSocket(PORT);
		}catch(IOException ex){
			System.out.println("\nUnable...");
			System.exit(1);
		}
		do{
			Socket client = serverSocket.accept();
			
			System.out.println("\nNew client accepted");
			
			ClientHandler handler = new ClientHandler(client);
			
			handler.start();
			
		}while(true);
	}
}
class ClientHandler extends Thread{
	private Socket client;
	private Scanner input;
	private PrintWriter output;
	
	public ClientHandler(Socket socket){
		client = socket;
		
		try{
			input = new Scanner(client.getInputStream());
			output = new PrintWriter(client.getOutputStream(), true);
						
		}catch(IOException err){
			System.out.println("Cannot disconnect");
		}
	}
	public void run(){
		String messageReceived;
		do{
			messageReceived = input.nextLine();
			output.println("SERVER: "+messageReceived);
			
		}while(!messageReceived.equalsIgnoreCase("esc"));
		try{
			if(client!=null){
				System.out.println("Closing "+ client.getInetAddress().getHostName()+"connection...");
				client.close();
			}
		}catch(IOException exception){
			System.out.println("Cannot disconnect");
		}
	}
}