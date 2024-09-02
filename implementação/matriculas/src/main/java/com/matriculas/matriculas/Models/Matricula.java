package com.matriculas.matriculas.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Matricula {
    private int id;
    private final Aluno aluno;
    private final Turma turma;
    private final LocalDateTime dataMatricula;

    public Matricula(Aluno aluno, Turma turma) {
        this.aluno = aluno;
        this.turma = turma;
        this.dataMatricula = LocalDateTime.now();
    }
    public Turma getTurma() {
        return this.turma;
    }
}
