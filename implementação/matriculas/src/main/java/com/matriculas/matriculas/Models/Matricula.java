package com.matriculas.matriculas.Models;

import java.util.Date;

public class Matricula {
    private int id;
    private final Aluno aluno;
    private final Disciplina disciplina;
    private final Date dataMatricula;

    /**
     * Construtor para criar uma nova instância de Matricula.
     *
     * @param aluno      O aluno que está se matriculando.
     * @param disciplina A disciplina na qual o aluno está se matriculando.
     */
    public Matricula(Aluno aluno, Disciplina disciplina) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.dataMatricula = new Date(); // Define a data da matrícula como a data atual
    }

    /**
     * Retorna a disciplina associada a esta matrícula.
     *
     * @return A disciplina em que o aluno está matriculado.
     */
    public Disciplina getDisciplina() {
        return this.disciplina;
    }

}
