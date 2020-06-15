package com.example.meucardapio.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.meucardapio.model.Conta;
import com.example.meucardapio.model.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HttpServiceConta extends AsyncTask<Void, Void, List<Conta>> {

    private static final String TAG = "MyActivity";
    private static List<Conta> contas;
    private static int idComanda;

    public HttpServiceConta(int idComanda) {
        this.idComanda = idComanda;

    }

    @Override
    protected List<Conta> doInBackground(Void... voids) {


        Call<List<Conta>> call = RetrofitClient.getInstance().getApi().consultarConta(this.idComanda);

        try {
            Response<List<Conta>> rp = call.execute();
            contas = rp.body();

            Log.i(TAG,  "Sucesso CONVERSÃO JSON body --------------->" + contas);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return contas;
    }
}
