package com.matriculas.matriculas.Models;

import com.matriculas.matriculas.Enums.TipoDisciplina;

import java.util.ArrayList;

public class Disciplina {

    private final String codigo;
    private final String nome;
    private final Professor professor;
    private final int creditos;
    private int vagasDisponiveis;
    private final TipoDisciplina tipo;
    private ArrayList<Aluno> matriculados;

    /**
     * Construtor para criar uma nova instância de Disciplina.
     *
     * @param codigo          Código único da disciplina.
     * @param nome            Nome da disciplina.
     * @param professor       Professor responsável pela disciplina.
     * @param creditos        Número de créditos da disciplina.
     * @param vagasDisponiveis Número inicial de vagas disponíveis.
     * @param tipo            Tipo da disciplina (Obrigatória ou Optativa).
     */
    public Disciplina(String codigo, String nome, Professor professor, int creditos, int vagasDisponiveis, TipoDisciplina tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.professor = professor;
        this.creditos = creditos;
        this.vagasDisponiveis = vagasDisponiveis;
        this.tipo = tipo;
        this.matriculados = new ArrayList<>();
    }

    /**
     * Verifica se há vagas disponíveis na disciplina.
     *
     * @return true se houver pelo menos uma vaga disponível, false caso contrário.
     */
    public boolean verificarDisponibilidade() {
        return vagasDisponiveis >= 1;
    }

    /**
     * Obtém o tipo da disciplina (Obrigatória ou Optativa).
     *
     * @return Tipo da disciplina.
     */
    public TipoDisciplina getTipo() {
        return this.tipo;
    }

    /**
     * Adiciona um aluno à disciplina, caso haja vagas disponíveis e o aluno ainda não esteja matriculado.
     *
     * @param aluno Aluno a ser matriculado.
     * @return true se o aluno foi matriculado com sucesso, false caso contrário.
     */
    public boolean adicionarAluno(Aluno aluno) {
        if (verificarDisponibilidade() && !matriculados.contains(aluno)) {
            this.matriculados.add(aluno);
            vagasDisponiveis--;
            return true;
        }
        return false;
    }

    /**
     * Obtém o nome da Disciplina
     * @return String nome da disciplina
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Consulta dos alunos matriculados
     * @return Lista dos alunos
     */
    public ArrayList<Aluno> getMatriculados() {
        return this.matriculados;
    }

    /**
     * Remove um aluno da disciplina.
     *
     * @param aluno Aluno a ser removido.
     * @return true se o aluno foi removido com sucesso, false caso contrário.
     */
    public boolean removerAluno(Aluno aluno) {
        boolean result = this.matriculados.remove(aluno);
        if (result) {
            vagasDisponiveis++;
            return true;
        }
        return false;
    }
}
