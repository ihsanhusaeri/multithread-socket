import java.io.*;
import java.net.*;
import java.util.*;

public class Client{
	private static InetAddress host;
	private static final int PORT = 5000;
	
	public static void main(String[] args){
		try{
			host = InetAddress.getLocalHost();
		}catch(UnknownHostException exception){
			System.out.println("Host not found");
			System.exit(1);
		}
		sendMessage();
	}
	private static void sendMessage(){
		Socket socket = null;
		try{
			socket = new Socket(host, PORT);
			Scanner networkInput = new Scanner(socket.getInputStream());
			PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(), true);
			
			Scanner userEntry = new Scanner(System.in);
			
			String message, response;
			do{
				System.out.print("Enter message ('esc' to exit): ");
				
				message = userEntry.nextLine();
				networkOutput.println(message);
				
				response = networkInput.nextLine();
				
				System.out.println(response);
			}while(!message.equalsIgnoreCase("esc"));
			
		}catch(IOException exception){
			exception.printStackTrace();
		}finally{
			try{
				System.out.println("\nClosing connection...");
				socket.close();
			}catch(IOException e){
				System.out.println("Cannot disconnect");
				System.exit(1);
			}
		}
	}
}