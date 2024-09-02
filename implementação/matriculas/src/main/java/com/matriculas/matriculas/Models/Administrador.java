package com.matriculas.matriculas.Models;

import java.util.List;

public class Administrador extends Usuario{
    private int id;
    private final String nome;

    /**
     * Construtor para criar uma nova instância de Administrador.
     *
     * @param nome Nome do administrador.
     */
    public Administrador(String nome) {
        this.nome = nome;
    }

    /**
     * Verifica se as disciplinas devem ser ativadas com base no número de alunos matriculados.
     *
     * Se uma disciplina tiver menos de 3 alunos matriculados, ela será cancelada.
     * Caso contrário, ela será ativada.
     *
     * @param disciplinas Lista de disciplinas a serem verificadas.
     */
    public void verificarAtivacaoDisciplinas(List<Disciplina> disciplinas) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.verificarDisponibilidade()) {
                notificarAlunosESistema(disciplina, "Ativada");
            } else {
                notificarAlunosESistema(disciplina, "Cancelada");
            }
        }
    }

    /**
     * Notifica os alunos e o sistema sobre o status de ativação de uma disciplina.
     *
     * @param disciplina A disciplina cujo status será notificado.
     * @param status     O status da disciplina ("Ativada" ou "Cancelada").
     */
    public void notificarAlunosESistema(Disciplina disciplina, String status) {
        // Notificar todos os alunos matriculados
        for (Aluno aluno : disciplina.getMatriculados()) {
            aluno.receberNotificacao("A disciplina " + disciplina.getNome() + " foi " + status + ".");
        }

        // Notificar o sistema de cobranças, caso a disciplina seja cancelada
        if ("Cancelada".equalsIgnoreCase(status)) {
            SistemaCobranca.cancelarCobrancaParaDisciplina(disciplina);
        }
    }
}
