package Q3.serializar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import Q3.model.Aluno;
import Q3.model.Curso;

public class Servidor {
	
	public static void main(String[] args) throws IOException {
		Curso CC = new Curso("Ciencia da Computação", 4, null);
		Curso SI = new Curso("Sistema de informação", 4, null);
		Socket s = null;
	    InputStream in = null;
	    OutputStream out = null;
	    DataOutputStream dout = null;
	    DataInputStream din = null;
		while(true) {
			int serverPort = 7896;
	        try {
				s = new Socket("localhost", serverPort);
				in = s.getInputStream();
		        out = s.getOutputStream();
		        dout = new DataOutputStream(out);
		        din = new DataInputStream(in);
		        int tam = din.readInt();
				byte[] JsonBytesR = new byte[tam];
		        in.read(JsonBytesR);
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
		        out.write(JsonBytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(s!=null)
					s.close();
			}
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
