package Q3.model;

import java.util.ArrayList;

public class Curso {
	private String nomeDoCruso;
	private int duracao;
	private ArrayList<Aluno> alunos;

	public Curso(String nomeDoCruso, int duracao) {
		super();
		this.nomeDoCruso = nomeDoCruso;
		this.duracao = duracao;
		this.alunos = new ArrayList<Aluno>();
	}

	public ArrayList<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(ArrayList<Aluno> alunos) {
		this.alunos = alunos;
	}
	public void addAluno(Aluno aluno) {
		alunos.add(aluno);
	}

	public String getNomeDoCruso() {
		return nomeDoCruso;
	}

	public void setNomeDoCruso(String nomeDoCruso) {
		this.nomeDoCruso = nomeDoCruso;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	@Override
	public String toString() {
		return "Curso [nomeDoCruso=" + nomeDoCruso + ", duracao=" + duracao + ", alunos=" + alunos + "]";
	}
}
