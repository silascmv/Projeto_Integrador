package com.example.meucardapio.model;

public class AddMesa {

    public String qr_code;
    public Integer id_cliente;
    public Integer id_funcionario;
    public Integer tp_pagamento;


    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(Integer id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public Integer getTp_pagamento() {
        return tp_pagamento;
    }

    public void setTp_pagamento(Integer tp_pagamento) {
        this.tp_pagamento = tp_pagamento;
    }
}
