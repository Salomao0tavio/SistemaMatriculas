package com.matriculas.matriculas.Dados;

import com.matriculas.matriculas.Enums.Role;
import com.matriculas.matriculas.Models.Aluno;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaAluno implements IPersistencia<Aluno> {
    private static final String ARQUIVO_ALUNOS = "alunos.txt";

    @Override
    public void salvar(Aluno aluno) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_ALUNOS, true))) {
            writer.write(alunoToCSV(aluno) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Aluno carregar(Object id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_ALUNOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Aluno aluno = csvToAluno(linha);
                if (aluno.getId() == (int) id) {
                    return aluno;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(Aluno aluno) {
        List<Aluno> alunos = carregarTodos();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_ALUNOS))) {
            for (Aluno a : alunos) {
                if (a.getId() == aluno.getId()) {
                    writer.write(alunoToCSV(aluno) + "\n");
                } else {
                    writer.write(alunoToCSV(a) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Object id) {
        List<Aluno> alunos = carregarTodos();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_ALUNOS))) {
            for (Aluno aluno : alunos) {
                if (aluno.getId() != (int) id) {
                    writer.write(alunoToCSV(aluno) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String alunoToCSV(Aluno aluno) {
        return aluno.getId() + "," + aluno.getNome() + "," + aluno.getSenha() + "," + aluno.getRole();
    }

    private Aluno csvToAluno(String csv) {
        String[] dados = csv.split(",");
        int id = Integer.parseInt(dados[0]);
        String nome = dados[1];
        String senha = dados[2];
        Role role = Role.valueOf(dados[3]);
        return new Aluno( nome,senha, id);
    }

    private List<Aluno> carregarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_ALUNOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                alunos.add(csvToAluno(linha));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alunos;
    }
}
