package com.example.meucardapio.model;

public class StatusCliente {

    String id_comanda;
    String id_mesa ;
    boolean sn_pago ;

    public StatusCliente(String id_comanda, String id_mesa, boolean sn_pago) {
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

    public boolean isSn_pago() {
        return sn_pago;
    }

    public void setSn_pago(boolean sn_pago) {
        this.sn_pago = sn_pago;
    }
}
