package com.matriculas.matriculas.Models;

import com.matriculas.matriculas.Enums.StatusDisciplina;

import com.matriculas.matriculas.Enums.Role;

import java.util.List;

public class Administrador extends Usuario{

    /**
     * Construtor para criar uma nova instância de Administrador.
     *
     * @param nome Nome do administrador.
     */
    public Administrador(String nome, String senha, int id) {
        super(id, nome, senha, Role.ADMINISTRADOR);
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
