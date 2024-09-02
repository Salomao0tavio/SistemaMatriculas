package com.matriculas.matriculas;

import com.matriculas.matriculas.Enums.TipoDisciplina;
import com.matriculas.matriculas.Models.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MatriculasApplication {

  private static List<Disciplina> disciplinas;
  private static List<Aluno> alunos;
  private static Administrador administrador;

  public static void main(String[] args) {
    // SpringApplication.run(MatriculasApplication.class, args);
    inicializarDados();

    Scanner scanner = new Scanner(System.in);
    int opcao;

    do {
      exibirMenu();
      opcao = scanner.nextInt();
      scanner.nextLine();

      switch (opcao) {
        case 1:
          criarAluno(scanner);
          break;
        case 2:
          criarDisciplina(scanner);
          break;
        case 3:
          matricularAluno(scanner);
          break;
        case 4:
          cancelarMatricula(scanner);
          break;
        case 5:
          consultarDisciplinasDisponiveis(scanner);
          break;
        case 6:
          verificarAtivacaoDisciplinas();
          break;
        case 0:
          System.out.println("Saindo do sistema...");
          break;
        default:
          System.out.println("Opção inválida! Tente novamente.");
      }

    } while (opcao != 0);

    scanner.close();
  }

  private static void exibirMenu() {
    System.out.println("\nMenu de Opções:");
    System.out.println("1. Criar Novo Aluno");
    System.out.println("2. Criar Nova Disciplina");
    System.out.println("3. Matricular Aluno em Disciplina");
    System.out.println("4. Cancelar Matrícula de Aluno");
    System.out.println("5. Consultar Disciplinas Disponíveis para Aluno");
    System.out.println("6. Verificar Ativação de Disciplinas");
    System.out.println("0. Sair");
    System.out.print("Escolha uma opção: ");
  }

  private static void inicializarDados() {
    disciplinas = new ArrayList<>();
    alunos = new ArrayList<>();
    administrador = new Administrador("Administrador");
  }

  private static void criarAluno(Scanner scanner) {
    System.out.print("Digite o nome do aluno: ");
    String nome = scanner.nextLine();
    Aluno novoAluno = new Aluno(nome);
    alunos.add(novoAluno);
    System.out.println("Aluno " + nome + " criado com sucesso!");
  }

  private static void criarDisciplina(Scanner scanner) {
    System.out.print("Digite o código da disciplina: ");
    String codigo = scanner.nextLine();

    System.out.print("Digite o nome da disciplina: ");
    String nome = scanner.nextLine();

    System.out.print("Digite o nome do professor responsável: ");
    String nomeProfessor = scanner.nextLine();
    Professor professor = new Professor(nomeProfessor);

    System.out.print("Digite o número de créditos da disciplina: ");
    int creditos = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Digite o número de vagas disponíveis: ");
    int vagasDisponiveis = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Digite o tipo da disciplina (1 para OBRIGATORIA, 2 para OPTATIVA): ");
    int tipoOpcao = scanner.nextInt();
    scanner.nextLine();

    TipoDisciplina tipo = tipoOpcao == 1 ? TipoDisciplina.OBRIGATORIA : TipoDisciplina.OPTATIVA;

    Disciplina novaDisciplina = new Disciplina(codigo, nome, professor, creditos, vagasDisponiveis, tipo);
    disciplinas.add(novaDisciplina);
    System.out.println("Disciplina " + nome + " criada com sucesso!");
  }

  private static void matricularAluno(Scanner scanner) {
    Aluno aluno = selecionarAluno(scanner);
    Disciplina disciplina = selecionarDisciplina(scanner);

    if (aluno != null && disciplina != null) {
      boolean sucesso = aluno.matricularEmDisciplina(disciplina);
      if (sucesso) {
        System.out.println("Aluno matriculado com sucesso!");
      } else {
        System.out.println("Não foi possível matricular o aluno na disciplina.");
      }
    }
  }

  private static void cancelarMatricula(Scanner scanner) {
    Aluno aluno = selecionarAluno(scanner);
    Disciplina disciplina = selecionarDisciplina(scanner);

    if (aluno != null && disciplina != null) {
      boolean sucesso = aluno.cancelarMatricula(disciplina);
      if (sucesso) {
        System.out.println("Matrícula cancelada com sucesso!");
      } else {
        System.out.println("O aluno não está matriculado nesta disciplina.");
      }
    }
  }

  private static void consultarDisciplinasDisponiveis(Scanner scanner) {
    Aluno aluno = selecionarAluno(scanner);

    if (aluno != null) {
      ArrayList<Disciplina> disciplinasDisponiveis = aluno
          .consultarDisciplinasDisponiveis(new ArrayList<>(disciplinas));
      System.out.println("Disciplinas disponíveis para " + aluno.getNome() + ":");
      for (Disciplina disciplina : disciplinasDisponiveis) {
        System.out.println("- " + disciplina.getNome());
      }
    }
  }

  private static void verificarAtivacaoDisciplinas() {
    administrador.verificarAtivacaoDisciplinas(disciplinas);
    System.out.println("Verificação de ativação de disciplinas concluída.");
  }

  private static Aluno selecionarAluno(Scanner scanner) {
    System.out.println("Selecione o aluno:");
    for (int i = 0; i < alunos.size(); i++) {
      System.out.println((i + 1) + ". " + alunos.get(i).getNome());
    }
    int opcaoAluno = scanner.nextInt();
    scanner.nextLine();

    if (opcaoAluno > 0 && opcaoAluno <= alunos.size()) {
      return alunos.get(opcaoAluno - 1);
    } else {
      System.out.println("Aluno inválido!");
      return null;
    }
  }

  private static Disciplina selecionarDisciplina(Scanner scanner) {
    System.out.println("Selecione a disciplina:");
    for (int i = 0; i < disciplinas.size(); i++) {
      System.out.println((i + 1) + ". " + disciplinas.get(i).getNome());
    }
    int opcaoDisciplina = scanner.nextInt();
    scanner.nextLine();

    if (opcaoDisciplina > 0 && opcaoDisciplina <= disciplinas.size()) {
      return disciplinas.get(opcaoDisciplina - 1);
    } else {
      System.out.println("Disciplina inválida!");
      return null;
    }
  }
}
