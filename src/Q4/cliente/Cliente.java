package Q4.cliente;

import java.util.ArrayList;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import Q4.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class Cliente {
	private static Socket socket;
	private static DataInputStream in;
	private static DataOutputStream out;
	private static Scanner scan;
	private static MulticastSocket mcs;
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
				for(Candidato c : candidatos) {
					System.out.println("Nome: "+c.getNome()+" Numero: "+c.getNumero());
				}
				System.out.println("Digite o numero do candidato");
				int numero = scan.nextInt();
				out.writeInt(numero);
				String v = in.readUTF();
				if(v.equals("V")) System.out.println("Voto contabilizado");
				else System.out.println("Erro: Voto nao contabilizado por algum motivo");
				int op;
				System.out.println("Digite 0 para sair ou continue logado para receber mensagens de administradores");
				op = scan.nextInt();
				while(op!=0){
					mcs = new MulticastSocket(12347);
					InetAddress grp = InetAddress.getByName("239.0.0.1");
					mcs.joinGroup(grp);
					
					byte rec[] = new byte[256];
					DatagramPacket pkg = new DatagramPacket(rec, rec.length);
					mcs.receive(pkg);
					
					String data = new String(pkg.getData());
					System.out.println("Mensagem: " + data);
					System.out.println("Digite 0 para sair ou continue logado para receber mensagens de administradores");
					op = scan.nextInt();
				}
			}else System.out.println("Não pode votar agora");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(mcs!=null)
				mcs.close();
		}
	}

	private static void Adm() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				System.out.println(in.readUTF());
				int escolha = scan.nextInt();
				out.writeInt(escolha);
				if(escolha==1) NAdm();
				else if(escolha==2) {
					ArrayList<Eleitor> praReceber = new ArrayList<Eleitor>();
					int qtd = in.readInt();
					for(int i=0;i<qtd;i++) {
						int tam = in.readInt();
						byte[] JsonBytesR = new byte[tam];
				        in.read(JsonBytesR);
				        String receivedJSON = new String(JsonBytesR, "UTF-8");
				        Eleitor receivedCand = (Eleitor) JSON2Object(receivedJSON);
				        praReceber.add(receivedCand);
					}
					for(Eleitor elei : praReceber) {
						System.out.println("Nome: "+elei.getNome());
					}
					System.out.println("Digite o nome do eleitor a ser colocado como candidato");
					String nomeElei = scan.nextLine();
					out.writeUTF(nomeElei);
					String res = in.readUTF();
					if(res.equals("E")) {
						System.out.println("Digite o numero a ser associado ao candidato");
						int numeroCan = scan.nextInt();
						out.writeInt(numeroCan);
					}else System.out.println("Candidato nao encontrado");
				}else if(escolha==3) continue;
				else if(escolha==4) {
					System.out.println(in.readUTF());
					String mensagem = scan.nextLine();
					out.writeUTF(mensagem);
				}else if(escolha==5) {
					ArrayList<Candidato> praReceber = new ArrayList<Candidato>();
					int qtd = in.readInt();
					for(int i=0;i<qtd;i++) {
						int tam = in.readInt();
						byte[] JsonBytesR = new byte[tam];
				        in.read(JsonBytesR);
				        String receivedJSON = new String(JsonBytesR, "UTF-8");
				        Candidato receivedCand = (Candidato) JSON2Object(receivedJSON);
				        praReceber.add(receivedCand);
					}
					for(Candidato can : praReceber) {
						System.out.println("Nome: "+can.getNome());
					}
					System.out.println("Digite o nome do Candidato a ser removido");
					String nomeCan = scan.nextLine();
					out.writeUTF(nomeCan);
					String res = in.readUTF();
					if(res.equals("E")) System.out.println("Candidato removido");
					else System.out.println("Candidato nao encontrado");
				}else return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
