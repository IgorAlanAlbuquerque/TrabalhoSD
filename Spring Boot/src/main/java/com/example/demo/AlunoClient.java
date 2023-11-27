package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.example.model.Aluno;

public class AlunoClient {

	private final String URL = "http://localhost:8080";
	private final RestTemplate restTemplate;
	
	public AlunoClient() {
        this.restTemplate = new RestTemplate();
    }
	
	public ResponseEntity<Aluno> getAlunoById(int id) {
        String url = URL + "/alunos/" + id;
        return restTemplate.getForEntity(url, Aluno.class);
    }

    public ResponseEntity<Aluno> createAluno(Aluno aluno) {
        String url = URL + "/alunos";
        return restTemplate.postForEntity(url, aluno, Aluno.class);
    }

    public ResponseEntity<Void> deleteAluno(int id) {
        String url = URL + "/alunos/" + id;
        restTemplate.delete(url);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Aluno> updateAluno(int id, Aluno aluno) {
        String url = URL + "/alunos/" + id;
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(aluno), Aluno.class);
    }

}
