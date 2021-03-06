package com.example.meucardapio.model;

public class Login {


    private String idUsuario;
    private String senhaUsuario;
    private String emailUsuario;
    private String resposta;
    private String code_status;

    public Login(String resposta, String code_status){
        this.resposta=resposta;
        this.code_status=code_status;

    }
    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    @Override
    public String toString() {
        return "Login{" +
                "idUsuario='" + idUsuario + '\'' +
                ", senhaUsuario='" + senhaUsuario + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", resposta='" + resposta + '\'' +
                '}';
    }
}