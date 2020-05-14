package com.example.meucardapio.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Cardapio {

    @SerializedName("IMAGEM")
    private String IMAGEM;
    @SerializedName("NOME")
    private String NOME;
    @SerializedName("VALOR")
    private Double VALOR;
    @SerializedName("DESCRICAO")
    private String DESCRICAO;
    @SerializedName("CODIGO_BARRA")
    private String CODIGO_BARRA;
    @SerializedName("TIPO")
    private String TIPO;

    public Cardapio(String IMAGEM, String NOME, Double VALOR, String DESCRICAO, String CODIGO_BARRA, String TIPO) {
        this.IMAGEM = IMAGEM;
        this.NOME = NOME;
        this.VALOR = VALOR;
        this.DESCRICAO = DESCRICAO;
        this.CODIGO_BARRA = CODIGO_BARRA;
        this.TIPO = TIPO;
    }

    public Cardapio() {

    }

    public String getIMAGEM() {
        return IMAGEM;
    }

    public void setIMAGEM(String IMAGEM) {
        this.IMAGEM = IMAGEM;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public Double getVALOR() {
        return VALOR;
    }

    public void setVALOR(Double VALOR) {
        this.VALOR = VALOR;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public String getCODIGO_BARRA() {
        return CODIGO_BARRA;
    }

    public void setCODIGO_BARRA(String CODIGO_BARRA) {
        this.CODIGO_BARRA = CODIGO_BARRA;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }
}
