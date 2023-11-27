package Q3.model;

import java.io.Serializable;

public class Aluno implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nome;
	private String CPF;
	private int matricula;
	private double IRA;
	private int idade;
	private String curso;
	private String senha;


	public Aluno(String nome, String CPF, int matricula, double IRA, int idade, String curso, String senha) {
		this.nome = nome;
		this.CPF = CPF;
		this.matricula = matricula;
		this.IRA = IRA;
		this.idade = idade;
		this.curso = curso;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public double getIRA() {
		return IRA;
	}

	public void setIRA(double iRA) {
		IRA = iRA;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "Aluno [nome=" + nome + ", CPF=" + CPF + ", matricula=" + matricula + ", IRA=" + IRA + ", idade=" + idade
				+ ", curso=" + curso + "]";
	}

}
