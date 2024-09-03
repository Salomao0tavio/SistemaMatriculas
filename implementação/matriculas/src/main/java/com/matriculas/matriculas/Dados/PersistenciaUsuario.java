package com.matriculas.matriculas.Dados;

import com.matriculas.matriculas.Enums.Role;
import com.matriculas.matriculas.Models.Administrador;
import com.matriculas.matriculas.Models.Aluno;
import com.matriculas.matriculas.Models.Professor;
import com.matriculas.matriculas.Models.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuario implements IPersistencia<Usuario> {
    private static final String ARQUIVO_USUARIOS = "usuarios.txt";

    @Override
    public void salvar(Usuario usuario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS, true))) {
            writer.write(usuarioToCSV(usuario) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario carregar(Object id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Usuario usuario = csvToUsuario(linha);
                if (usuario.getId() == (int) id) {
                    return usuario;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(Usuario usuario) {
        List<Usuario> usuarios = carregarTodos();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario u : usuarios) {
                if (u.getId() == usuario.getId()) {
                    writer.write(usuarioToCSV(usuario) + "\n");
                } else {
                    writer.write(usuarioToCSV(u) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Object id) {
        List<Usuario> usuarios = carregarTodos();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario usuario : usuarios) {
                if (usuario.getId() != (int) id) {
                    writer.write(usuarioToCSV(usuario) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario autenticar(String nome, String senha) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Usuario usuario = csvToUsuario(linha);
                if (usuario.getNome().equals(nome) && usuario.getSenha().equals(senha)) {
                    return usuario;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String usuarioToCSV(Usuario usuario) {
        return usuario.getId() + "," + usuario.getNome() + "," + usuario.getSenha() + "," + usuario.getRole();
    }

    private Usuario csvToUsuario(String csv) {
        String[] dados = csv.split(",");
        int id = Integer.parseInt(dados[0]);
        String nome = dados[1];
        String senha = dados[2];
        Role role = Role.valueOf(dados[3]);

        switch (role) {
            case Role.ALUNO:
                return new Aluno(nome, senha, id);
            case Role.PROFESSOR:
                return new Professor(nome, id, senha);
            case Role.ADMINISTRADOR:
                return new Administrador(nome, senha, id);
            default:
                throw new IllegalArgumentException("Role desconhecido: " + role);
        }
    }

    private List<Usuario> carregarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                usuarios.add(csvToUsuario(linha));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
