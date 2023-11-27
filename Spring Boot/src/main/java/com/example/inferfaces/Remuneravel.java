package com.example.inferfaces;

public interface Remuneravel {
	
	int diasTrabalhar = 30;
	int valorDaBolsa = 700;

    // M�todos que as classes que implementam a interface devem fornecer
    double calcularSalario();
    
    public int getDiasTrabalhados();

	public void setDiasTrabalhados(int diasTrabalhados);

	public int getDiasTrabalhar();

	public double getValorBolsa();
}
