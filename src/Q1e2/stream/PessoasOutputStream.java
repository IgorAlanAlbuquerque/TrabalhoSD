package Q1e2.stream;

import java.io.*;
import java.net.*;

import Q1e2.pessoa.Pessoa;

public class PessoasOutputStream extends OutputStream {

	private OutputStream op;
	private Pessoa[] pessoas;
	
	public PessoasOutputStream() {}
	
	public PessoasOutputStream(Pessoa[] p, OutputStream os) {
		this.pessoas = p;
		this.op = os;
	}

	public void writeSystem() {
		
		PrintStream opLocal = new PrintStream(op);
		
		//envia quantidade de pessoas;
		int qtdpessoa = pessoas.length;
		opLocal.println("Quantidade de pessoas: "+qtdpessoa);
		
		//envia os dados de um conjunto (array) de Pessoas
		for (Pessoa pessoa : pessoas) {
			if (pessoa != null) {
				int tamanhoNomePessoa = pessoa.getNome().getBytes().length;
				String nome = pessoa.getNome();
				String cpf = pessoa.getCpf();
				int idade = pessoa.getIdade();
							
				opLocal.println(" tamanhoNomePessoa: "+tamanhoNomePessoa+ "\n"+
								" nomePessoa: "+nome+ "\n"+
								" cpf: "+cpf+ "\n"+
								" idade: "+idade);
			}
		}
	}

	public void writeFile() {
		// envia os dados de um conjunto (array) de Pessoas para um arquivo
		DataOutputStream dop = null;
		try {
			dop = new DataOutputStream(new FileOutputStream("salvo"));
			dop.writeInt(pessoas.length);
			for(Pessoa p : pessoas) {
				//pega o número de bytes do nome e cpf
				byte[] nomeBytes = p.getNome().getBytes("UTF-8");
	            byte[] cpfBytes = p.getCpf().getBytes("UTF-8");
	            dop.writeInt(nomeBytes.length); //escreve o numero de bytes do nome
	            dop.writeInt(cpfBytes.length); //escreve o numero de bytes do cpf
	            //escreve nome, cpf e idade da pessoa
	            dop.write(nomeBytes);
	            dop.write(cpfBytes);
	            dop.writeInt(p.getIdade());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dop.close();
				op.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void writeTCP() {
		// envia os dados de um conjunto (array) de Pessoas
		DataOutputStream dop = null;
		try {
			dop = new DataOutputStream(op);
			dop.writeInt(pessoas.length);
			for(Pessoa p : pessoas) {
				//pega o número de bytes do nome e cpf
				byte[] nomeBytes = p.getNome().getBytes("UTF-8");
                byte[] cpfBytes = p.getCpf().getBytes("UTF-8");
                dop.writeInt(nomeBytes.length); //envia o numero de bytes do nome
                dop.writeInt(cpfBytes.length); //envia o numero de bytes do cpf
                //envia nome, cpf e idade da pessoa
                dop.write(nomeBytes);
                dop.write(cpfBytes);
                dop.writeInt(p.getIdade());
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(op!=null) {
				try {
					dop.close();
					op.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}		
	
	@Override
	public void write(int b) throws IOException {
		
	}

	@Override
	public String toString() {
		return "Ola, mundo! Estamos sobrescrevendo o mÃ©todo toString()!\n"
				+ " PessoasOutputStream [ \n"
				+ " getClass()=" + getClass() +",\n"
				+ " hashCode()=" + hashCode() +",\n"
				+ " toString()="+ super.toString() + "]";
	}

}
