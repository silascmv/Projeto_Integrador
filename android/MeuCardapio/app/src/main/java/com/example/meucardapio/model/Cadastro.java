package com.example.meucardapio.model;

import android.widget.EditText;

public class Cadastro {

    private String cd_login;
    private String cd_senha;
    private String nome;
    private String email;
    private String endereco;
    private String telefone;
    private String celular;

    public String getCd_login() {
        return cd_login;
    }

    public void setCd_login(String cd_login) {
        this.cd_login = cd_login;
    }

    public String getCd_senha() {
        return cd_senha;
    }

    public void setCd_senha(String cd_senha) {
        this.cd_senha = cd_senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
