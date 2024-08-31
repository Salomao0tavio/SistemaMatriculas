package com.matriculas.matriculas.Models;

import com.matriculas.matriculas.Enums.StatusDisciplina;

import java.util.List;

public class Administrador {
    private int id;
    private final String nome;

    public Administrador(String nome) {
        this.nome = nome;
    }

    public void notificarAlunos(Turma turma, StatusDisciplina status) {
        for (Aluno aluno : turma.getMatriculados()) {
            aluno.receberNotificacao("A disciplina " + turma.getDisciplina().getNome() + " foi " + status + ".");
        }
    }
    public void notificarSistema(Turma turma, StatusDisciplina status){
        if (status == StatusDisciplina.DISPONIVEL) {
            SistemaCobranca.cancelarCobrancaParaTurma(turma);
        }
    }
}
