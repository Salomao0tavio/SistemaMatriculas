package com.matriculas.matriculas.Models;

import com.matriculas.matriculas.Enums.StatusDisciplina;
import com.matriculas.matriculas.Enums.TipoDisciplina;

import java.util.ArrayList;

public class Aluno {

    private int id;
    private final String nome;
    private final ArrayList<Turma> turmas;

    public Aluno(String nome) {
        this.nome = nome;
        this.turmas = new ArrayList<>();
    }

    public boolean matricularEmDisciplina(Turma turma) {

        if (jaMatriculado(turma) || turma.getStatus() != StatusDisciplina.DISPONIVEL) {
            return false;
        }

        TipoDisciplina tipo = turma.getDisciplina().getTipo();
        if ((tipo == TipoDisciplina.OBRIGATORIA && getTotalObrigatorias() >= 4) ||
                (tipo == TipoDisciplina.OPTATIVA && getTotalOptativas() >= 2)) {
            return false;
        }

        this.turmas.add(turma);
        turma.adicionarAluno(this);

        Matricula novaMatricula = new Matricula(this, turma);
        SistemaCobranca.registrarCobranca(novaMatricula);

        return true;
    }

    public boolean cancelarMatricula(Turma turma) {
        if (!jaMatriculado(turma)) {
            return false;
        }
        this.turmas.remove(turma);
        return true;
    }

    public ArrayList<Turma> consultarTurmasDisponiveis(ArrayList<Turma> turmas) {
        int qntObrigatoriasDisponiveis = 4 - getTotalObrigatorias();
        int qntOptativasDisponiveis = 2 - getTotalOptativas();

        ArrayList<Turma> turmasDisponiveis = new ArrayList<>();

        for (Turma turma : turmas) {
            if (turma.getStatus() != StatusDisciplina.DISPONIVEL)
                break;
            TipoDisciplina tipo = turma.getDisciplina().getTipo();

            if (tipo == TipoDisciplina.OBRIGATORIA && qntObrigatoriasDisponiveis > 0) {
                turmasDisponiveis.add(turma);
            } else if (tipo == TipoDisciplina.OPTATIVA && qntOptativasDisponiveis > 0) {
                turmasDisponiveis.add(turma);
            }
        }
        return turmasDisponiveis;
    }

    public void receberNotificacao(String mensagem) {
        //TODO: Implementacao
    }

    private boolean jaMatriculado(Turma turma) {
        for (Turma t : this.turmas) {
            if (t == turma)
                return true;
        }
        return false;
    }

    //#region GETTERS | SETTERS
    private int getTotalOptativas() {
        int optativas = 0;
        for (Turma turma : this.turmas) {
            if (turma.getDisciplina().getTipo() == TipoDisciplina.OPTATIVA)
                optativas++;
        }
        return optativas;
    }

    private int getTotalObrigatorias() {
        int obrigatorias = 0;
        for (Turma turma : this.turmas) {
            if (turma.getDisciplina().getTipo() == TipoDisciplina.OBRIGATORIA)
                obrigatorias++;
        }
        return obrigatorias;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    //#endregion
}
