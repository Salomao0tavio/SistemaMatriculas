package com.matriculas.matriculas.Dados;

import com.matriculas.matriculas.Models.Aluno;
import com.matriculas.matriculas.Models.Disciplina;
import com.matriculas.matriculas.Models.Matricula;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaMatricula implements IPersistencia<Matricula> {
    private static final String ARQUIVO_MATRICULAS = "matriculas.txt";

    @Override
    public void salvar(Matricula matricula) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_MATRICULAS, true))) {
            writer.write(matriculaToCSV(matricula) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Matricula carregar(Object id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_MATRICULAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Matricula matricula = csvToMatricula(linha);
                if (matricula.getId() == (int) id) {
                    return matricula;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(Matricula matricula) {
        List<Matricula> matriculas = carregarTodas();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_MATRICULAS))) {
            for (Matricula m : matriculas) {
                if (m.getId() == matricula.getId()) {
                    writer.write(matriculaToCSV(matricula) + "\n");
                } else {
                    writer.write(matriculaToCSV(m) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Object id) {
        List<Matricula> matriculas = carregarTodas();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_MATRICULAS))) {
            for (Matricula matricula : matriculas) {
                if (matricula.getId() != (int) id) {
                    writer.write(matriculaToCSV(matricula) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String matriculaToCSV(Matricula matricula) {
        return matricula.getId() + "," + matricula.getAluno().getId() + "," + matricula.getDisciplina().getCodigo();
    }

    private Matricula csvToMatricula(String csv) {
        String[] dados = csv.split(",");
        int id = Integer.parseInt(dados[0]);
        int alunoId = Integer.parseInt(dados[1]);
        String disciplinaCodigo = dados[2];

        // Aqui você precisaria carregar o aluno e a disciplina usando suas persistências
        Aluno aluno = new PersistenciaAluno().carregar(alunoId);
        Disciplina disciplina = new PersistenciaDisciplina().carregar(disciplinaCodigo);

        return new Matricula(id, aluno, disciplina);
    }

    private List<Matricula> carregarTodas() {
        List<Matricula> matriculas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_MATRICULAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                matriculas.add(csvToMatricula(linha));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matriculas;
    }
}
