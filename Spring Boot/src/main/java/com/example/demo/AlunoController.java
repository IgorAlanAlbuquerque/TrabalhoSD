package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.model.Aluno;
import com.example.model.AlunoService;
 
@RestController
public class AlunoController {
	
	@Autowired
	private AlunoService alunoService;
	
    @GetMapping("/alunos/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable int id) {
        Aluno aluno = alunoService.getAlunoById(id);
 
        return ResponseEntity.ok(aluno);
    }
    
    @PostMapping("/alunos")
    public ResponseEntity<Aluno> createAluno(@RequestBody Aluno aluno) {
        Aluno savedAluno = alunoService.addAluno(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAluno);
    }
    
    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable int id) {
        boolean deleted = alunoService.deleteAluno(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/alunos/{id}")
    public ResponseEntity<Aluno> updateAluno(@PathVariable int id, @RequestBody Aluno aluno) {
        Aluno updatedAluno = alunoService.updateAluno(id, aluno);
        if (updatedAluno != null) {
            return ResponseEntity.ok(updatedAluno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
