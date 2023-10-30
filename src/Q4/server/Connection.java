package Q4.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import Q4.model.*;
import Q4.urna.Urna;

public class Connection extends Thread {

	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	ArrayList<User> usuarios;
	Urna urna;

	public Connection(Socket aClientSocket, ArrayList<User> usuarios, Urna urna) {
		try {
			clientSocket = aClientSocket;
			this.usuarios = usuarios;
			this.urna = urna;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		} catch (IOException e) {
			System.out.println("Connection:" + e.getMessage());
		}
	}

	public void run() {
		try { // an echo server

			String login = in.readUTF(); // read a line of data from the stream
			String senha = in.readUTF();
			boolean flag= false;
			for(User u : usuarios) {
				if(u.getLogin().equals(login) && u.getSenha().equals(senha)) {
					flag = true;
					if(u instanceof Admin) {
						out.writeUTF("Adm");
						comoAdmin(u);
					}
					else{
						out.writeUTF("Nao");
						comoEleitor(u);
					}
				}
			}
			if(!flag) out.writeUTF("Login ou senha invalidos");
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				/* close failed */
			}
		}

	}
	private void comoEleitor(User u) {
		try {
			if(urna.isPodeVotar()) {
				out.writeUTF("P");
				out.writeInt(urna.listaCandidato().size());
				for(Candidato c : urna.listaCandidato()) {
					String JSON = object2JSON(c);
					byte[] JsonBytes = JSON.getBytes("UTF-8");
			        out.writeInt(JsonBytes.length);
			        out.write(JsonBytes);
				}
		        int numCandidato = in.read();
				if(!u.isVotou() && urna.addVoto(numCandidato)) {
					u.setVotou(true);
					out.writeUTF("voto contabilizado");
					return;
				}else out.writeUTF("Numero errado");
			}else {
				out.writeUTF("N");
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	private void comoAdmin(User u) {
		try {
			out.writeUTF("digite: (1) votar, (2) adicionar candidato, (3) iniciar a votação, (4) enviar mensagem (5) remover Candidato ou qualquer outra tecla para sair");
			int e = in.readInt();
			while(e==1 || e ==2 || e==3 || e ==4 || e==5) {
				if(e==1) {
					comoEleitor(u);
				}else if(e==2) {
					ArrayList<Eleitor> praEnviar = new ArrayList<Eleitor>();
					for(User user : usuarios)
						if(user instanceof Eleitor)
							praEnviar.add((Eleitor) user);
					for(Eleitor elei : praEnviar) {
						String JSON = object2JSON(elei);
						byte[] JsonBytes = JSON.getBytes("UTF-8");
				        out.writeInt(JsonBytes.length);
				        out.write(JsonBytes);
					}
					String escolha = in.readUTF();
					for(User user : usuarios) {
						if(user.getNome().equals(escolha)) {
							out.writeUTF("digite o numero do candidato");
							int numeroDoCandidato = in.readInt();
							usuarios.remove(u);
							usuarios.add(new Candidato(u.getNome(),u.getLogin(),u.getSenha(),numeroDoCandidato));
						}
					}					
				}else if(e==3) {
					urna.iniVotacao();		
				}else if(e==4) {
					out.writeUTF("digite a mensagem a ser enviada");
					String mensagem = in.readUTF();
					new Multcast(mensagem);
				}else {
					//remover candidato
				}
			}
			return;
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	private static String object2JSON(User user) {
		// Cria uma instancia do XStream configurado para gerar JSON usando o driver Jettison
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		// Define que nao devem ser incluidas referencias aos objetos serializados
        xstream.setMode(XStream.NO_REFERENCES);
        // Define um alias "aluno" para a classe 'Aluno' para uso na serializacao
        xstream.alias("Usuario", User.class);
        // Converte o objeto 'Aluno' em JSON (XML formatado como JSON)
		String JSON = xstream.toXML(user);
		//retorna o json criado
		return JSON;
	}
}
