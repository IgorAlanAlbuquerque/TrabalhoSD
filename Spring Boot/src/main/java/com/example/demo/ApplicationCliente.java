package com.example.demo;

import com.example.model.Aluno;
import java.util.Scanner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApplicationCliente {
	
	public static void main(String[] args) {
		AlunoClient alunoClient = new AlunoClient();
		Scanner scan = new Scanner(System.in);
		while(true) {
			int escolha=0;
			System.out.println("Opcoes: (1) Inserir aluno, (2) buscar aluno, (3) atualizar aluno, (4) delete aluno");
			escolha = scan.nextInt();
			if(escolha==1) {
				Aluno aluno = lerDados(scan, true, 0);
				alunoClient.createAluno(aluno);
			}else if(escolha==2) {
				System.out.println("Digite a matricula: ");
				int matricula = scan.nextInt();
				ResponseEntity<Aluno> response = alunoClient.getAlunoById(matricula);
				if (response.getStatusCode() == HttpStatus.OK) {
			        Aluno aluno = response.getBody();
			        if (aluno != null) {
			            System.out.println(aluno.toString());
			        } else {
			            System.out.println("Aluno não encontrado para matrícula: " + matricula);
			        }
			    } else {
			        System.out.println("Erro ao buscar o aluno. Status: " + response.getStatusCodeValue());
			    }
			}else if(escolha==3) {
				System.out.println("Digite a matricula do aluno: ");
				int matricula = scan.nextInt();
				ResponseEntity<Aluno> response = alunoClient.getAlunoById(matricula);
				if (response.getStatusCode() == HttpStatus.OK) {
			        Aluno aluno = response.getBody();
			        if (aluno != null) {
			            System.out.println(aluno.toString());
			            aluno = lerDados(scan,false,matricula);
						alunoClient.updateAluno(matricula, aluno);
			        } else {
			            System.out.println("Aluno não encontrado para matrícula: " + matricula);
			        }
			    } else {
			        System.out.println("Erro ao buscar o aluno. Status: " + response.getStatusCodeValue());
			    }
			}else if(escolha==4) {
				System.out.println("Digite a matricula: ");
				int matricula = scan.nextInt();
				alunoClient.deleteAluno(matricula);
			}else break;
		}
		scan.close();
		System.out.println("Aplicação Encerrada");
		return;
	}
	
	public static Aluno lerDados(Scanner scan, boolean M, int matricula) {
		System.out.println("digite o nome: ");
		String nome = scan.next();
		System.out.println("digite o CPF: ");
		String CPF = scan.next();
		if(M) {
			System.out.println("digite a matricula: ");
			matricula = scan.nextInt();
		}
		System.out.println("Digite o IRA: ");
		double IRA = scan.nextDouble();
		System.out.println("Curso: ");
		String curso = scan.next();
		System.out.println("Idade: ");
		int idade = scan.nextInt();
		System.out.println("senha: ");
		String senha = scan.next();
		Aluno aluno = new Aluno(nome,CPF,matricula,IRA,idade,curso,senha);
		return aluno;
	}
}
