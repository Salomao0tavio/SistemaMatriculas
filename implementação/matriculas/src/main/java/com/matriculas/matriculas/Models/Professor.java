package com.matriculas.matriculas.Models;

import com.matriculas.matriculas.Enums.Role;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario{

    private final List<Disciplina> disciplinas;

    /**
     * Construtor para criar uma nova instância de Professor.
     *
     * @param nome  Nome do professor.
     */
    public Professor(String nome, int id, String senha ) {
        super(id, nome, senha, Role.PROFESSOR);
        this.disciplinas = new ArrayList<>();
    }

    public List<Aluno> consultarAlunosMatriculados(Turma turma) {
        ArrayList<Aluno> alunos = turma.getMatriculados();
        if(!alunos.isEmpty())
            return alunos;
        return new ArrayList<>();
    }

}
