package Q3.serializar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import Q3.model.Aluno;
import Q3.model.Curso;

public class Servidor {
	
	public static void main(String[] args) throws IOException {
		Curso CC = new Curso("Ciencia da Computação", 4);
		Curso SI = new Curso("Sistema de informação", 4);
		int serverPort = 7896;
		ServerSocket s = null;
	    DataOutputStream dout = null;
	    DataInputStream din = null;
	    try {
			System.out.println("Servidor iniciado");
			s = new ServerSocket(serverPort);
			while (true) {
				Socket clientSocket = s.accept();
				System.out.println("conexão estabelecida");
			    dout = new DataOutputStream(clientSocket.getOutputStream());
			    din = new DataInputStream(clientSocket.getInputStream());
			    int tam = din.readInt();
			    byte[] JsonBytesR = new byte[tam];
			    din.read(JsonBytesR);
			    String receivedJSON = new String(JsonBytesR, "UTF-8");
			    Aluno receivedAluno = JSON2Object(receivedJSON);
			    if(receivedAluno.getCurso().equals("Ciencia da Computação")) {
			    	CC.addAluno(receivedAluno);
			    }else if(receivedAluno.getCurso().equals("Sitemas de Informação")) {
			        SI.addAluno(receivedAluno);
			    }
			    receivedAluno.setNome(receivedAluno.getNome().toUpperCase());
		        String JSON = object2JSON(receivedAluno);
		        byte[] JsonBytes = JSON.getBytes("UTF-8");
			    dout.writeInt(JsonBytes.length);
			    dout.write(JsonBytes);
			}
		} catch (IOException e) {
			System.out.println("Listen socket:" + e.getMessage());
		} finally {
			if(s!=null)
				s.close();
		}
	}

	private static String object2JSON(Aluno aluno) {
		// Cria uma instancia do XStream configurado para gerar JSON usando o driver Jettison
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		// Define que nao devem ser incluidas referencias aos objetos serializados
        xstream.setMode(XStream.NO_REFERENCES);
        // Define um alias "aluno" para a classe 'Aluno' para uso na serializacao
        xstream.alias("aluno", Aluno.class);
        // Converte o objeto 'Aluno' em JSON (XML formatado como JSON)
		String JSON = xstream.toXML(aluno);
		//retorna o json criado
		return JSON;
	}
	private static Aluno JSON2Object(String JSON) {
		// Cria uma inst�ncia do XStream configurado para processar JSON usando o driver Jettison
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		// Define um alias "aluno" para a classe 'Aluno'
		xstream.alias("aluno", Aluno.class);
		// Adiciona permissao para converter qualquer tipo de objeto durante a desserializacao
		xstream.addPermission(AnyTypePermission.ANY);
		// Converte o JSON de volta para um objeto 'Aluno'
		Aluno aluno = (Aluno) xstream.fromXML(JSON);
		return aluno;		
	}

}
