package com.example.meucardapio.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Cardapio {

    @SerializedName("imagem")
    private String imagem;
    @SerializedName("nome")
    private String nome;
    @SerializedName("valor")
    private Double valor;
    @SerializedName("descricao")
    private String descricao;


    public Cardapio(String imagem, String nome, Double valor, String descricao) {
        this.imagem = imagem;
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
    }

    public Cardapio() {

    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
