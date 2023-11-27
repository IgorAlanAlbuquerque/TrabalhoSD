package com.example.model;

import java.io.*;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
	private ArrayList<Aluno> alunos;

	public AlunoService() {
		// TODO Auto-generated constructor stub
		alunos = new ArrayList<Aluno>();
		if(!fileExists("alunos.dat"))
			createFile("alunos.dat");
		loadFromFile();
	}

	private void loadFromFile() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("alunos.dat"))) {
            while (true) {
                Aluno aluno = (Aluno) ois.readObject();
                alunos.add(aluno);
            }
        } catch (EOFException e) {
            // EOFException indica o final do arquivo
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
		
	}

	private void createFile(String filename) {
		try {
			File file = new File(filename);
	        file.createNewFile();
	        System.out.println("Arquivo criado: " + file.getAbsolutePath());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}

	private boolean fileExists(String filename) {
		File file = new File(filename);
        return file.exists();
	}

	public Aluno addAluno(Aluno aluno) {
		alunos.add(aluno);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("alunos.dat"))) {
            oos.writeObject(aluno);
		}catch (IOException e) {
            e.printStackTrace();
        }
		return aluno;
	}
	public Aluno getAlunoById(int id) {
		// TODO Auto-generated method stub
		for(Aluno a : alunos) {
			if(a.getMatricula()==id) return a;
		}
		return null;
	}

	public boolean deleteAluno(int id) {
		// TODO Auto-generated method stub
		for(Aluno a : alunos) {
			if(a.getMatricula()==id) {
				alunos.remove(a);
				escreverAlunos();
				return true;
			}
		}
		return false;
	}

	private void escreverAlunos() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("alunos.dat", false))) {
			for(Aluno a : alunos) {
				oos.writeObject(a);
			}
		}catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	public Aluno updateAluno(int id, Aluno aluno) {
	    for (Aluno a : alunos) {
	        if (a.getMatricula() == id) {
	            alunos.remove(a);
	            alunos.add(aluno);
	            escreverAlunos();
	            return aluno;
	        }
	    }
	    return null;
	}
}
