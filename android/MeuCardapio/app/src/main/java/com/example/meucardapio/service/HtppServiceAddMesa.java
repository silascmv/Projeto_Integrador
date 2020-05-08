package com.example.meucardapio.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.meucardapio.model.AddMesa;
import com.example.meucardapio.model.Cadastro;
import com.example.meucardapio.model.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;

public class HtppServiceAddMesa extends AsyncTask<Void, Void, CodeStatus> {


    private static final String TAG = "MyActivity";
    static String retorno_api;
    private AddMesa abrirMesa = new AddMesa();
    Gson gson = new Gson();
    CodeStatus codeStatus;
    private String json;


    public HtppServiceAddMesa(AddMesa abrirMesa) {
        this.abrirMesa = abrirMesa;
    }

    @Override
    protected CodeStatus doInBackground(Void... voids) {
        retrofit2.Call<CodeStatus> call = RetrofitClient.getInstance().getApi().addMesa(this.abrirMesa.getQr_code(), this.abrirMesa.getId_cliente(), this.abrirMesa.getId_funcionario(), this.abrirMesa.getTp_pagamento());

        try {
            Response<CodeStatus> rp = call.execute();
            codeStatus = rp.body();
            //codeStatus = gson.fromJson(retorno_api,CodeStatus.class);
            Log.i(TAG, getClasseName() + "Sucesso CONVERSÃO JSON body --------------->" + codeStatus);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return codeStatus;
    }

    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }


    }



