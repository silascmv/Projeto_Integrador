package com.example.meucardapio.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.meucardapio.model.RetrofitClient;

import java.io.IOException;

import retrofit2.Response;

public class HttpServicePagamentoConta extends AsyncTask<Void, Void, CodeStatus> {

    private double valorTotal;
    private int tpPagamento;
    private int idComanda;

    CodeStatus codeStatus;

    public HttpServicePagamentoConta(double valorTotal, int tpPagamento, int idComanda) {
        this.valorTotal = valorTotal;
        this.tpPagamento = tpPagamento;
        this.idComanda = idComanda;
    }


    @Override
    protected CodeStatus doInBackground(Void... voids) {

        retrofit2.Call<CodeStatus> call = RetrofitClient.getInstance().getApi().realizarPagamentoAndroid(this.valorTotal, this.tpPagamento, this.idComanda);

        try {
            Response<CodeStatus> rp = call.execute();
            codeStatus = rp.body();
            //codeStatus = gson.fromJson(retorno_api,CodeStatus.class);


        } catch (IOException e) {

            e.printStackTrace();


        }


        return codeStatus;



    }
}
