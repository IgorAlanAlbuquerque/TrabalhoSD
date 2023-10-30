package Q4.cliente;

import java.util.ArrayList;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import Q3.model.Aluno;
import Q4.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	private static Socket socket;
	private static DataInputStream in;
	private static DataOutputStream out;
	private static Scanner scan;
	private int serverPort;
	
	public Cliente() {
		serverPort = 7896;
		try {
			socket = new Socket("localhost",serverPort);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			scan = new Scanner(System.in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Digite o Login");
			String login = scan.nextLine();
			System.out.println("Digite a senha");
			String senha = scan.nextLine();
			out.writeUTF(login);
			out.writeUTF(senha);
			String retorno = in.readUTF();
			if(retorno.equals("Adm")) Adm();
			else if(retorno.equals("Nao")) NAdm();
			else System.out.println(retorno);
		} catch (UnknownHostException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			if (socket != null)
				try {
					socket.close();
					scan.close();
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
				}
		}
		

	}

	private static void NAdm() {
		// TODO Auto-generated method stub
		ArrayList<Candidato> candidatos = new ArrayList<Candidato>();
		try {
			String PouN = in.readUTF();
			if(PouN.equals("P")) {
				int qtd = in.readInt();
				for(int i=0;i<qtd;i++) {
					int tam = in.readInt();
					byte[] JsonBytesR = new byte[tam];
			        in.read(JsonBytesR);
			        String receivedJSON = new String(JsonBytesR, "UTF-8");
			        Candidato receivedCand = (Candidato) JSON2Object(receivedJSON);
			        candidatos.add(receivedCand);
				}
			}else System.out.println("Não pode votar agora");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void Adm() {
		// TODO Auto-generated method stub
		
	}
	private static User JSON2Object(String JSON) {
		// Cria uma inst�ncia do XStream configurado para processar JSON usando o driver Jettison
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		// Define um alias "aluno" para a classe 'Aluno'
		xstream.alias("user", User.class);
		// Adiciona permissao para converter qualquer tipo de objeto durante a desserializacao
		xstream.addPermission(AnyTypePermission.ANY);
		// Converte o JSON de volta para um objeto 'Aluno'
		User user = (User) xstream.fromXML(JSON);
		return user;		
	}

}
