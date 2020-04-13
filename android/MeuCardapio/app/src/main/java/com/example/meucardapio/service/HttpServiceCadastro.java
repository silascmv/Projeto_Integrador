package com.example.meucardapio.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.meucardapio.model.Cadastro;
import com.example.meucardapio.model.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpServiceCadastro extends AsyncTask<Void, Void, CodeStatus> {

    private static final String TAG = "MyActivity";
    static String retorno_api ;
    Cadastro cadastro = new Cadastro();
    CodeStatus codeStatus;
    Gson gson = new Gson();
    private String json;

    public HttpServiceCadastro(Cadastro cadastro) {
        this.cadastro = cadastro;
    }

    @Override
    protected CodeStatus doInBackground(Void... voids) {
        retrofit2.Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addCliente(this.cadastro.getCd_login(),this.cadastro.getCd_senha(),this.cadastro.getNome(),this.cadastro.getEmail(),this.cadastro.getEndereco(),this.cadastro.getTelefone(),this.cadastro.getCelular());

        Log.i(TAG, getClasseName() + "Sucesso --------------->" + call.toString());


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.i(TAG, getClasseName() + "Sucesso");
                try {


                    retorno_api = response.body().string();
                    codeStatus = gson.fromJson(retorno_api,CodeStatus.class);
                    Log.i(TAG, getClasseName() + "Sucesso CONVERSÃO JSON body --------------->" + codeStatus);
                    if(codeStatus.getCode_status() == 1){
                        Log.i(TAG, getClasseName() + "Usuário cadastrado com Sucesso" + codeStatus.getClass().getName());
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }


            }



            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, getClasseName() + "Falhou");

            }
        });


        return codeStatus;

    }


    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
