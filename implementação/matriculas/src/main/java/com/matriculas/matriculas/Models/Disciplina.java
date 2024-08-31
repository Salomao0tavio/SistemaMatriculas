package com.matriculas.matriculas.Models;

import com.matriculas.matriculas.Enums.TipoDisciplina;

import java.util.ArrayList;

public class Disciplina {

    private final String codigo;
    private final String nome;
    private final int creditos;
    private final TipoDisciplina tipo;
    private ArrayList<Turma> turmas = new ArrayList<>();

    public Disciplina(String codigo, String nome, int creditos, TipoDisciplina tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.creditos = creditos;
        this.tipo = tipo;
    }

    //#region GETTERS | SETTERS

    public TipoDisciplina getTipo() {
        return this.tipo;
    }

    public String getNome() {
        return this.nome;
    }

    //#endregion
}
