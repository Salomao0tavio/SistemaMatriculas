package com.matriculas.matriculas.Models;

import com.matriculas.matriculas.Enums.TipoDisciplina;

import java.util.ArrayList;

public class Aluno {

    private int id;
    private final String nome;
    private ArrayList<Matricula> matriculas;

    /**
     * Construtor para criar uma nova instância de Aluno.
     *
     * @param nome  Nome do aluno.
     */
    public Aluno(String nome) {
        this.nome = nome;
        this.matriculas = new ArrayList<>();
    }

    /**
     * Matricula o aluno em uma disciplina, se houver disponibilidade e se o aluno não excedeu
     * o limite de disciplinas obrigatórias ou optativas.
     *
     * @param disciplina A disciplina em que o aluno deseja se matricular.
     * @return true se a matrícula for bem-sucedida, false caso contrário.
     */
    public boolean matricularEmDisciplina(Disciplina disciplina) {

        if (jaMatriculado(disciplina) || !disciplina.verificarDisponibilidade()) {
            return false;
        }

        TipoDisciplina tipo = disciplina.getTipo();
        if ((tipo == TipoDisciplina.OBRIGATORIA && getTotalObrigatorias() >= 4) ||
                (tipo == TipoDisciplina.OPTATIVA && getTotalOptativas() >= 2)) {
            return false;
        }

        Matricula novaMatricula = new Matricula(this, disciplina);

        this.matriculas.add(novaMatricula);
        disciplina.adicionarAluno(this);

        SistemaCobranca.registrarCobranca(novaMatricula);

        return true;
    }

    /**
     * Cancela a matrícula do aluno em uma disciplina.
     *
     * @param disciplina A disciplina da qual o aluno deseja cancelar a matrícula.
     * @return true se o cancelamento for bem-sucedido, false caso o aluno não esteja matriculado na disciplina.
     */
    public boolean cancelarMatricula(Disciplina disciplina) {
        if (!jaMatriculado(disciplina)) {
            return false;
        }
        this.matriculas.removeIf(matricula -> matricula.getDisciplina() == disciplina);
        return true;
    }

    /**
     * Consulta as disciplinas disponíveis para matrícula, considerando as vagas disponíveis
     * e o limite de disciplinas obrigatórias e optativas que o aluno pode cursar.
     *
     * @param todasDisciplinas Lista de todas as disciplinas disponíveis.
     * @return Lista de disciplinas que o aluno pode se matricular.
     */
    public ArrayList<Disciplina> consultarDisciplinasDisponiveis(ArrayList<Disciplina> todasDisciplinas) {
        int qntObrigatoriasDisponiveis = 4 - getTotalObrigatorias();
        int qntOptativasDisponiveis = 2 - getTotalOptativas();

        ArrayList<Disciplina> disciplinasDisponiveis = new ArrayList<>();

        for (Disciplina disciplina : todasDisciplinas) {
            TipoDisciplina tipo = disciplina.getTipo();

            if (tipo == TipoDisciplina.OBRIGATORIA && qntObrigatoriasDisponiveis > 0
                    && disciplina.verificarDisponibilidade()) {
                disciplinasDisponiveis.add(disciplina);
            } else if (tipo == TipoDisciplina.OPTATIVA && qntOptativasDisponiveis > 0
                    && disciplina.verificarDisponibilidade()) {
                disciplinasDisponiveis.add(disciplina);
            }
        }
        return disciplinasDisponiveis;
    }

    /**
     *
     * @param mensagem de aviso para o aluno
     */
    public void receberNotificacao(String mensagem){
        //TODO: Implementacao
    }
    /**
     * Conta o número de disciplinas optativas em que o aluno está matriculado.
     *
     * @return Número de disciplinas optativas.
     */
    private int getTotalOptativas() {
        int optativas = 0;
        for (Matricula matricula : matriculas) {
            if (matricula.getDisciplina().getTipo() == TipoDisciplina.OPTATIVA)
                optativas++;
        }
        return optativas;
    }

    /**
     * Conta o número de disciplinas obrigatórias em que o aluno está matriculado.
     *
     * @return Número de disciplinas obrigatórias.
     */
    private int getTotalObrigatorias() {
        int obrigatorias = 0;
        for (Matricula matricula : matriculas) {
            if (matricula.getDisciplina().getTipo() == TipoDisciplina.OBRIGATORIA)
                obrigatorias++;
        }
        return obrigatorias;
    }

    /**
     * Verifica se o aluno já está matriculado em uma disciplina específica.
     *
     * @param disciplina A disciplina a ser verificada.
     * @return true se o aluno já estiver matriculado na disciplina, false caso contrário.
     */
    private boolean jaMatriculado(Disciplina disciplina) {
        for (Matricula matricula : matriculas) {
            if (matricula.getDisciplina() == disciplina)
                return true;
        }
        return false;
    }
}
