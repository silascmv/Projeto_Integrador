package com.example.meucardapio.service;

public class CodeStatus {

        public String status ;
        public int code_status;

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
                ", code_status='" + code_status + '\'' +
                '}';
    }
}
