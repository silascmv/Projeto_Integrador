package com.example.meucardapio.service;

public class CodeStatus {

        public String status ;
        public int code_status;
        public int usuario;
        public int mesa_cliente;
        public int id_comanda;

    public int getId_comanda() {
        return id_comanda;
    }

    public void setId_comanda(int id_comanda) {
        this.id_comanda = id_comanda;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getMesa_cliente() {
        return mesa_cliente;
    }

    public void setMesa_cliente(int mesa_cliente) {
        this.mesa_cliente = mesa_cliente;
    }

    public int getUsuarioLogado() {
        return usuario;
    }

    public void setUsuarioLogado(int usuarioLogado) {
        this.usuario = usuarioLogado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode_status() {
        return code_status;
    }

    public void setCode_status(int code_status) {
        this.code_status = code_status;
    }

    @Override
    public String toString() {
        return "CodeStatus{" +
                "status='" + status + '\'' +
                ", code_status=" + code_status +
                ", usuario=" + usuario +
                ", mesa_cliente=" + mesa_cliente +
                ", id_comanda=" + id_comanda +
                '}';
    }
}
