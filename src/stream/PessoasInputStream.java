package stream;

import java.io.*;
import java.net.UnknownHostException;

import pessoa.Pessoa;
import java.util.Scanner;

public class PessoasInputStream extends InputStream {

	private InputStream is;
	private Pessoa[] pessoas;
	
	public PessoasInputStream(Pessoa[] p, InputStream is) {
		this.pessoas = p;
		this.is = is;
	}

	public Pessoa[] readSystem() {
       
        Scanner sc = new Scanner(is);
        
		System.out.println("Informa o nome da pessoa:"); 
		String nome = sc.nextLine();
		System.out.println("Informe o cpf da pessoa:"); 
		String cpf = sc.nextLine();
		System.out.println("Informe a idade do pessoa:"); 
		int idade = sc.nextInt();
		
		pessoas[0] = new Pessoa(nome, cpf, idade);
		
		sc.close();
		
		return pessoas;
	}
	
	public Pessoa[] readFile() {
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream("salvo"));
			int numPessoas = dis.readInt();
			for(int i=0;i<numPessoas;i++) {
				//ler o nome
				int qtdBytesNome = dis.readInt();
				byte[] nomeBytes = new byte[qtdBytesNome];
				dis.readFully(nomeBytes);
				String nome = new String(nomeBytes, "UTF-8");
				//ler o cpf
				int qtdBytesCPF = dis.readInt();
				byte[] CPFBytes = new byte[qtdBytesCPF];
				dis.readFully(CPFBytes);
				String cpf = new String(CPFBytes, "UTF-8");
				//ler idade
				int idade = dis.readInt();
				//por pessoa no vetor
				pessoas[i] = new Pessoa(nome, cpf, idade);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pessoas;
	}
	
	public Pessoa[] readTCP() {
		DataInputStream dis = new DataInputStream(is);
		try {
			int numPessoas = dis.readInt();
			for(int i=0;i<numPessoas;i++) {
				//ler o nome lendo a quantidade de bytes, depois um vetor de bytes nessa quantidade e por fim colocando em uma string
				int qtdByteNome = dis.readInt();
				byte[] nomeBytes = new byte[qtdByteNome];
                dis.readFully(nomeBytes);
                String nome = new String(nomeBytes, "UTF-8");
                //ler o cpf lendo a quantidade de bytes, depois um vetor de bytes nessa quantidade e por fim colocando em uma string
                int qtdByteCPF = dis.readInt();
                byte[] CPFBytes = new byte[qtdByteCPF];
                dis.readFully(CPFBytes);
				String cpf = new String(CPFBytes, "UTF-8");
				//ler a idade
				int idade = dis.readInt();
				//criar a pessoa e por a pessoa no vetor
				pessoas[i] = new Pessoa(nome, cpf, idade);; 
			}
		}catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dis.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pessoas;
	}

	@Override
	public int read() throws IOException {
		
		return 0;
	}

}
