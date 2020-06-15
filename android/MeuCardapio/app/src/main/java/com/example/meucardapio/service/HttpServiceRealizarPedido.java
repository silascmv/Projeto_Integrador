package com.example.meucardapio.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.meucardapio.model.ItemPedido;
import com.example.meucardapio.model.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HttpServiceRealizarPedido extends AsyncTask<Void, Void, CodeStatus> {


    private static final String TAG = "MyActivity";

    private static CodeStatus codeStatus;

    private List<ItemPedido> itensCarrinho = new ArrayList<>();

    public HttpServiceRealizarPedido(List<ItemPedido> itensCarrinho) {
        this.itensCarrinho = itensCarrinho;
    }

    @Override
    protected CodeStatus doInBackground(Void... voids) {


        // retrofit2.Call<CodeStatus> call = RetrofitClient.getInstance().getApi().realizarPedidoAndroid(this.itensCarrinho., this.abrirMesa.getId_cliente(), this.abrirMesa.getId_funcionario(), this.abrirMesa.getTp_pagamento());

        Call<CodeStatus> call = RetrofitClient.getInstance().getApi().realizarPedido(this.itensCarrinho);


        try {

            Response<CodeStatus> rp = call.execute();
            codeStatus = rp.body();
            Log.i(TAG, "PEDIDO ENVIADO PARA O SERVIDOR" + codeStatus.getCode_status());


        } catch (IOException e) {
            e.printStackTrace();
            
        }


        return codeStatus;
    }
}
