package com.matriculas.matriculas;

import com.matriculas.matriculas.Dados.*;
import com.matriculas.matriculas.Enums.Role;
import com.matriculas.matriculas.Enums.TipoDisciplina;
import com.matriculas.matriculas.Models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatriculasApplication {

    public static void main(String[] args) {
    PersistenciaUsuario persistenciaUsuario = new PersistenciaUsuario();
    PersistenciaAluno persistenciaAluno = new PersistenciaAluno();
    PersistenciaDisciplina persistenciaDisciplina = new PersistenciaDisciplina();
    PersistenciaMatricula persistenciaMatricula = new PersistenciaMatricula();

        Scanner scanner = new Scanner(System.in);

        // Login
        System.out.println("Bem-vindo ao sistema de matrícula!");
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        Usuario usuarioLogado = persistenciaUsuario.autenticar(nome, senha);

        if (usuarioLogado == null) {
            System.out.println("Nome ou senha inválidos.");
            return;
        }

        System.out.println("Login bem-sucedido! Bem-vindo, " + usuarioLogado.getNome());

        boolean executando = true;
        while (executando) {
            if (usuarioLogado instanceof Aluno) {
                exibirMenuAluno(scanner, (Aluno) usuarioLogado, persistenciaDisciplina, persistenciaMatricula);
            } else if (usuarioLogado instanceof Professor) {
                exibirMenuProfessor(scanner, (Professor) usuarioLogado);
            } else if (usuarioLogado instanceof Administrador) {
                exibirMenuAdministrador(scanner, (Administrador) usuarioLogado);
            }

            System.out.println("Deseja realizar outra ação? (s/n)");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("s")) {
                executando = false;
            }
        }

        System.out.println("Obrigado por usar o sistema!");
    }

    private static void exibirMenuAluno(Scanner scanner, Aluno aluno, PersistenciaDisciplina persistenciaDisciplina, PersistenciaMatricula persistenciaMatricula) {
        System.out.println("\n--- Menu do Aluno ---");
        System.out.println("1. Matricular em disciplina");
        System.out.println("2. Cancelar matrícula");
        System.out.println("3. Consultar disciplinas disponíveis");

        int opcao = Integer.parseInt(scanner.nextLine());

        switch (opcao) {
            case 1:
                System.out.print("Digite o código da disciplina: ");
                String codigoDisciplina = scanner.nextLine();
                Disciplina disciplina = persistenciaDisciplina.carregar(codigoDisciplina);
                if (disciplina != null && aluno.matricularEmDisciplina(disciplina)) {
                    System.out.println("Matriculado com sucesso!");
                } else {
                    System.out.println("Erro ao matricular.");
                }
                break;
            case 2:
                System.out.print("Digite o código da disciplina: ");
                codigoDisciplina = scanner.nextLine();
                disciplina = persistenciaDisciplina.carregar(codigoDisciplina);
                if (disciplina != null && aluno.cancelarMatricula(disciplina)) {
                    System.out.println("Matrícula cancelada com sucesso!");
                } else {
                    System.out.println("Erro ao cancelar matrícula.");
                }
                break;
            case 3:
                List<Disciplina> disciplinasDisponiveis = aluno.consultarDisciplinasDisponiveis(new ArrayList<>()); // Considerando que lista de disciplinas está vazia apenas como exemplo
                for (Disciplina d : disciplinasDisponiveis) {
                    System.out.println(d.getNome());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void exibirMenuProfessor(Scanner scanner, Professor professor) {
        System.out.println("\n--- Menu do Professor ---");
        System.out.println("1. Consultar alunos matriculados");

        int opcao = Integer.parseInt(scanner.nextLine());

        switch (opcao) {
            case 1:
                System.out.print("Digite o código da disciplina: ");
                String codigoDisciplina = scanner.nextLine();
                // Aqui você deveria carregar a disciplina e passar para consultarAlunosMatriculados
                // Exemplo simplificado:
                List<Aluno> alunos = professor.consultarAlunosMatriculados(new Disciplina(codigoDisciplina, "Disciplina", 4, TipoDisciplina.Obrigatoria));
                for (Aluno aluno : alunos) {
                    System.out.println(aluno.getNome());
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void exibirMenuAdministrador(Scanner scanner, Administrador administrador) {
        System.out.println("\n--- Menu do Administrador ---");
        System.out.println("1. Verificar ativação de turmas");
        System.out.println("2. Notificar alunos");
        System.out.println("3. Notificar sistema");

        int opcao = Integer.parseInt(scanner.nextLine());

        switch (opcao) {
            case 1:
                // Lista de turmas, exemplo:
                List<Turma> turmas = new ArrayList<>();
                administrador.verificarAtivacaoturma(turmas);
                System.out.println("Ativação de turmas verificada.");
                break;
            case 2:
                System.out.print("Digite o status da turma: ");
                String status = scanner.nextLine();
                // Considerando uma turma como exemplo
                administrador.notificarAlunos(new Turma(), status);
                System.out.println("Alunos notificados.");
                break;
            case 3:
                System.out.print("Digite o status da turma: ");
                status = scanner.nextLine();
                administrador.notificarSistema(new Turma(), status);
                System.out.println("Sistema notificado.");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
}
