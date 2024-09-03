package com.matriculas.matriculas.Models;

import com.matriculas.matriculas.Enums.StatusDisciplina;
import org.springframework.cglib.core.Local;
import java.time.LocalDate;
import java.util.ArrayList;

public class Turma {

    private final Disciplina disciplina;
    private final StatusDisciplina status;
    private int vagasDisponiveis;
    private Professor professor;
    private ArrayList<Aluno> matriculados;
    private String semestreAno;

    public Turma(Disciplina disciplina, StatusDisciplina status, int vagasDisponiveis, Professor professor) {
        this.disciplina = disciplina;
        this.status = status;
        this.vagasDisponiveis = vagasDisponiveis;
        this.professor = professor;
        this.semestreAno = criarStringData();
    }

    public StatusDisciplina verificarDisponibilidade() {
        return this.status;
    }

    public boolean adicionarAluno(Aluno aluno) {
        if (verificarDisponibilidade() == StatusDisciplina.DISPONIVEL && !matriculados.contains(aluno)) {
            this.matriculados.add(aluno);
            vagasDisponiveis--;
            return true;
        }
        return false;
    }

    public boolean removerAluno(Aluno aluno) {
        boolean result = this.matriculados.remove(aluno);
        if (result) {
            vagasDisponiveis++;
            return true;
        }
        return false;
    }

    public String criarStringData() {
        var data = LocalDate.now();
        var semestre = (int) Math.ceil((double) data.getMonthValue() / 6);
        var ano = data.getYear();
        return semestre + "°/" + ano;
    }


    //#region GETTERS | SETTERS
    public ArrayList<Aluno> getMatriculados() {
        return this.matriculados;
    }

    public Disciplina getDisciplina() {
        return this.disciplina;
    }

    public StatusDisciplina getStatus() {
        return this.status;
    }

    public Professor getProfessor() {
        return this.professor;
    }
    //#endregion
}
