package com.example.meucardapio.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HttpServiceListarCardapio extends AsyncTask<Void, Void, List<Cardapio>> {


    private static final String TAG = "MyActivity";
    static String retorno_api;
    Gson gson = new Gson();
    //List<Cardapio> cardapio = (List<Cardapio>) new Cardapio();
    List<Cardapio> cardapioretorno;
    JsonObject jsonConversao;
    CodeStatus codeStatus;
    private String json;


    @Override
    protected List<Cardapio> doInBackground(Void... voids) {

        Call<List<Cardapio>> call = RetrofitClient.getInstance().getApi().getCardapio();

        try {
            Response<List<Cardapio>> rp = call.execute();
            cardapioretorno = rp.body();
            Log.i(TAG, getClasseName() + "Sucesso CONVERSÃO JSON body --------------->" );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  cardapioretorno;


    }

    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
