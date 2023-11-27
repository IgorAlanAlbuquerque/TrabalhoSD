package com.example.model;

import com.example.inferfaces.Remuneravel;

public class AlunoPosGraduacao extends Aluno implements Remuneravel {
	private int diasTrabalhados = 0;

	private static final long serialVersionUID = 1L;

	public AlunoPosGraduacao(String nome, String CPF, int matricula, double IRA, int idade, String curso, String senha) {
		super(nome, CPF, matricula, IRA, idade, curso, senha);
	}

	public double calcularSalario() {
		// TODO Auto-generated method stub
		
		return (Remuneravel.valorDaBolsa/Remuneravel.diasTrabalhar)*diasTrabalhados;
	}

	public int getDiasTrabalhados() {
		return diasTrabalhados;
	}

	public void setDiasTrabalhados(int diasTrabalhados) {
		this.diasTrabalhados = diasTrabalhados;
	}

	public int getDiasTrabalhar() {
		return Remuneravel.diasTrabalhar;
	}

	public double getValorBolsa() {
		return Remuneravel.valorDaBolsa;
	}
}
