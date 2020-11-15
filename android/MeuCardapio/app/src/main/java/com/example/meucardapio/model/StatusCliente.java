package com.example.meucardapio.model;

public class StatusCliente {

    String id_comanda;
    String id_mesa;
    String status;
    int sn_pago;
    int code_status;

    public StatusCliente(String id_comanda, String id_mesa, int sn_pago) {
        this.id_comanda = id_comanda;
        this.id_mesa = id_mesa;
        this.sn_pago = sn_pago;
    }


    public String getId_comanda() {
        return id_comanda;
    }

    public void setId_comanda(String id_comanda) {
        this.id_comanda = id_comanda;
    }

    public String getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(String id_mesa) {
        this.id_mesa = id_mesa;
    }

    public int getSn_pago() {
        return sn_pago;
    }

    public void setSn_pago(int sn_pago) {
        this.sn_pago = sn_pago;
    }

    public int getCode_status() {
        return code_status;
    }

    public void setCode_status(int code_status) {
        this.code_status = code_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}