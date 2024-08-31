package com.matriculas.matriculas.Models;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    private int id;
    private final String nome;
    private final List<Turma> turmas;

    public Professor(String nome) {
        this.nome = nome;
        this.turmas = new ArrayList<>();
    }

    public List<Aluno> consultarAlunosMatriculados(Turma turma) {
        ArrayList<Aluno> alunos = turma.getMatriculados();
        if(!alunos.isEmpty())
            return alunos;
        return new ArrayList<>();
    }

}
