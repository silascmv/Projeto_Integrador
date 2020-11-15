package com.example.meucardapio.service;

import android.os.AsyncTask;
import android.util.Log;
import com.example.meucardapio.model.RetrofitClient;
import com.example.meucardapio.model.StatusCliente;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;

public class HttpServiceStatusMesa  extends AsyncTask<Void, Void, StatusCliente> {

    private static final String TAG = "MyActivity";
    static String retorno_api ;
    int id_cliente ;
    Gson gson = new Gson();
    StatusCliente statusCliente;
    private String json;

    public HttpServiceStatusMesa(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    protected StatusCliente doInBackground(Void... voids) {
        retrofit2.Call<StatusCliente> call = RetrofitClient.getInstance().getApi().verificarStatusMesaUsuario(this.id_cliente);
        try {
            Response<StatusCliente> rp = call.execute();
            statusCliente = rp.body();
            //codeStatus = gson.fromJson(retorno_api,CodeStatus.class);
            Log.i(TAG, "Sucesso CONVERSÃO JSON body --------------->" + statusCliente);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return statusCliente;

    }
}
