package com.matriculas.matriculas.Models;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario{
    private int id;
    private final String nome;
    private final List<Disciplina> disciplinas;

    /**
     * Construtor para criar uma nova instância de Professor.
     *
     * @param nome  Nome do professor.
     */
    public Professor(String nome) {
        this.nome = nome;
        this.disciplinas = new ArrayList<>();
    }

    /**
     * Consulta a lista de alunos matriculados em uma disciplina específica.
     *
     * @param disciplina A disciplina que o professor deseja consultar.
     * @return Lista de alunos matriculados na disciplina, se nao houver retorna uma lista vazia.
     */
    public List<Aluno> consultarAlunosMatriculados(Disciplina disciplina) {
        ArrayList<Aluno> alunos = disciplina.getMatriculados();
        if(!alunos.isEmpty())
            return alunos;
        return new ArrayList<>();
    }

}
