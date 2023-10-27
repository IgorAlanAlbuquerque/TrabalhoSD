package Q3.model;
import Q3.interfaces.Remuneravel;

public class AlunoIniciacaoCientifica extends AlunoGraduacao implements Remuneravel {

	private static final long serialVersionUID = 1L;

	public AlunoIniciacaoCientifica(String nome, String CPF, int matricula, double IRA, int idade, Curso curso) {
		super(nome, CPF, matricula, IRA, idade, curso);
	}

	

}
