package Q4.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import Q4.model.*;
import Q4.urna.Urna;
import java.net.ServerSocket;

public class Servidor{

	public Servidor() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<User> usuarios = new ArrayList<User>();
		Urna urna = new Urna();
		usuarios.add(new Eleitor("Jao", "ehujao", "jao123"));
		usuarios.add(new Eleitor("Maria", "ehamaria", "maria123"));
		usuarios.add(new Eleitor("Pedro", "ehupedro", "pedro123"));
		usuarios.add(new Eleitor("Ricardo", "ehuricardo", "ricardo123"));
		usuarios.add(new Eleitor("Pedro", "ehupedro", "pedro123"));
		usuarios.add(new Eleitor("Zé", "ehuze", "ze123"));
		usuarios.add(new Eleitor("Igor", "ehuigor", "igor123"));
		usuarios.add(new Admin("Chico", "ehuchico", "chico123"));
		usuarios.add(new Admin("Jose", "ehjose","jose123"));
		while(true) {
			try {
				System.out.println("Servidor iniciado");
				int serverPort = 7896; // the server port
				try (ServerSocket listenSocket = new ServerSocket(serverPort)) {
					while (true) {
						Socket clientSocket = listenSocket.accept();
						System.out.println("conexão estabelecida");
						Connection c = new Connection(clientSocket, usuarios, urna);
					}
				}
			} catch (IOException e) {
				System.out.println("Listen socket:" + e.getMessage());
			}
		}

	}
}
