package Q3.model;
import Q3.interfaces.Remuneravel;

public class AlunoIniciacaoCientifica extends AlunoGraduacao implements Remuneravel {
	
	private int diasTrabalhados = 0;


	private static final long serialVersionUID = 1L;

	public AlunoIniciacaoCientifica(String nome, String CPF, int matricula, double IRA, int idade, String curso, String senha) {
		super(nome, CPF, matricula, IRA, idade, curso, senha);
	}

	public double calcularSalario() {
		// TODO Auto-generated method stub
		
		return ((Remuneravel.valorDaBolsa+50)/Remuneravel.diasTrabalhar)*diasTrabalhados;
	}

	public int getDiasTrabalhados() {
		return diasTrabalhados;
	}

	public void setDiasTrabalhados(int diasTrabalhados) {
		this.diasTrabalhados = diasTrabalhados;
	}

	public int getDiasTrabalhar() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getValorBolsa() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
