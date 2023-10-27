package Q3.serializar;
import java.net.*;
import java.io.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import Q3.model.*;

import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class Cliente {

	public Cliente() {
		// TODO Auto-generated constructor stub
	}
	public Aluno envia(Aluno aluno) {
	    // Serializa o objeto Aluno em JSON
	    String JSON = object2JSON(aluno);

	    // Define variáveis para as streams e o socket
	    Socket s = null;
	    InputStream in = null;
	    OutputStream out = null;
	    DataOutputStream dout = null;
	    DataInputStream din = null;

	    try {
	        int serverPort = 7896;
	        s = new Socket("localhost", serverPort);
	        in = s.getInputStream();
	        out = s.getOutputStream();
	        dout = new DataOutputStream(out);
	        din = new DataInputStream(in);
	        // Converte o JSON em bytes e envia para o servidor
	        byte[] JsonBytes = JSON.getBytes("UTF-8");
	        dout.writeInt(JsonBytes.length);
	        out.write(JsonBytes);
	        // Lê a resposta do servidor
	        int tamanho = din.readInt();
	        byte[] JsonBytesR = new byte[tamanho];
	        in.read(JsonBytesR);
	        // Converte a resposta do JSON de volta para um objeto Aluno
	        String receivedJSON = new String(JsonBytesR, "UTF-8");
	        Aluno receivedAluno = JSON2Object(receivedJSON);

	        return receivedAluno;
	    } catch (UnknownHostException e) {
	        System.out.println("Socket:" + e.getMessage());
	    } catch (IOException e) {
	        System.out.println("IO Exception:" + e.getMessage());
	    } finally {
	        try {
	            // Feche as streams e o socket
	        	if(dout!=null)
	        		dout.close();
	        	if(din!=null)
	        		din.close();
	            if (in != null)
	                in.close();
	            if (out != null)
	                out.close();
	            if (s != null)
	                s.close();
	        } catch (IOException e) {
	            System.out.println("Close:" + e.getMessage());
	        }
	    }

	    // Em caso de erro retorna null
	    return null;
	}
	private String object2JSON(Aluno aluno) {
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
	private Aluno JSON2Object(String JSON) {
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
