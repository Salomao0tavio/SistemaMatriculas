package com.matriculas.matriculas.Models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.matriculas.matriculas.Enums.Role;

public class Usuario {
    private int id;
    private String nome;
    private String senha;
    private Role role;

    public boolean Login(Usuario u, String senha) throws NoSuchAlgorithmException{
        return u.getSenha().equals(encriptandoSenha(senha)) ? true : false;
    }

    public String getSenha() {
        return this.senha;
    }

    public void Cadastro(String nome, String senha, Role role) throws NoSuchAlgorithmException{
        this.nome = nome;
        this.senha = encriptandoSenha(senha);
        this.role = role;        
    }

    public String encriptandoSenha(String senha) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(senha.getBytes());

        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException{
        Usuario u = new Usuario();
        u.Cadastro("Carlos", "1", Role.ALUNO);
        System.out.println(u.Login(u, "1"));
    }

}


