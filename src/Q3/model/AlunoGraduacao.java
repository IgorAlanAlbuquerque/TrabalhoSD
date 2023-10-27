package Q3.model;

public class AlunoGraduacao extends Aluno {

	private static final long serialVersionUID = 1L;

	public AlunoGraduacao(String nome, String CPF, int matricula, double IRA, int idade, Curso curso) {
		super(nome, CPF, matricula, IRA, idade, curso);
	}
}
