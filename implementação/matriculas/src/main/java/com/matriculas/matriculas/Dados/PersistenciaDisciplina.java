package com.matriculas.matriculas.Dados;
import com.matriculas.matriculas.Enums.TipoDisciplina;
import com.matriculas.matriculas.Models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PersistenciaDisciplina implements IPersistencia<Disciplina> {
    private static final String ARQUIVO_DISCIPLINAS = "disciplinas.txt";

    @Override
    public void salvar(Disciplina disciplina) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_DISCIPLINAS, true))) {
            writer.write(disciplinaToCSV(disciplina) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Disciplina carregar(Object codigo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_DISCIPLINAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Disciplina disciplina = csvToDisciplina(linha);
                if (disciplina.getCodigo().equals(codigo)) {
                    return disciplina;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(Disciplina disciplina) {
        List<Disciplina> disciplinas = carregarTodas();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_DISCIPLINAS))) {
            for (Disciplina d : disciplinas) {
                if (d.getCodigo().equals(disciplina.getCodigo())) {
                    writer.write(disciplinaToCSV(disciplina) + "\n");
                } else {
                    writer.write(disciplinaToCSV(d) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Object codigo) {
        List<Disciplina> disciplinas = carregarTodas();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_DISCIPLINAS))) {
            for (Disciplina disciplina : disciplinas) {
                if (!disciplina.getCodigo().equals(codigo)) {
                    writer.write(disciplinaToCSV(disciplina) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String disciplinaToCSV(Disciplina disciplina) {
        return disciplina.getCodigo() + "," + disciplina.getNome() + "," + disciplina.getCreditos() + "," + disciplina.getTipo();
    }

    private Disciplina csvToDisciplina(String csv) {
        String[] dados = csv.split(",");
        String codigo = dados[0];
        String nome = dados[1];
        int creditos = Integer.parseInt(dados[2]);
        TipoDisciplina tipo = TipoDisciplina.valueOf(dados[3]);
        return new Disciplina(codigo, nome, creditos, tipo);
    }

    private List<Disciplina> carregarTodas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_DISCIPLINAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                disciplinas.add(csvToDisciplina(linha));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return disciplinas;
    }
}
