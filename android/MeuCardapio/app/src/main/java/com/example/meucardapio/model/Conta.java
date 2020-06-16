package com.example.meucardapio.model;

public class Conta {

    private String descrica;
    private String nome_produto;
    private int quantidade;
    private double valor_total;




    public String getDescrica() {
        return descrica;
    }

    public void setDescrica(String descrica) {
        this.descrica = descrica;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }
}
