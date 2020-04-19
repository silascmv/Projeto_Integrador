package com.example.meucardapio.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.meucardapio.model.Cadastro;
import com.example.meucardapio.model.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.transform.Result;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpServiceCadastro extends AsyncTask<Void, Void, CodeStatus> {

    private static final String TAG = "MyActivity";
    static String retorno_api ;
    Cadastro cadastro = new Cadastro();
    Gson gson = new Gson();
    private String json;

    public HttpServiceCadastro(Cadastro cadastro) {
        this.cadastro = cadastro;
    }

    @Override
    protected CodeStatus doInBackground(Void... voids) {
        retrofit2.Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addCliente(this.cadastro.getCd_login(),this.cadastro.getCd_senha(),this.cadastro.getNome(),this.cadastro.getEmail(),this.cadastro.getEndereco(),this.cadastro.getTelefone(),this.cadastro.getCelular());

        final CodeStatus[] codeStatus = {new CodeStatus()};
        Log.i(TAG, getClasseName() + "Sucesso --------------->" + call.toString());


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, getClasseName() + "Sucesso");
                try {
                    retorno_api = response.body().string();
                    codeStatus[0] = gson.fromJson(retorno_api,CodeStatus.class);
                    Log.i(TAG, getClasseName() + "Sucesso CONVERSÃO JSON body --------------->" + codeStatus[0]);
                    if(codeStatus[0].getCode_status() == 1){
                        Log.i(TAG, getClasseName() + "Usuário cadastrado com Sucesso" + codeStatus[0].getClass().getName());
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


        Log.i(TAG, getClasseName() + "CODE STATUS ANTES DO RETORNO --------------->" + Arrays.toString(codeStatus));

        return codeStatus[0];

    }


    protected void onPostExecute(CodeStatus codeStatus){

        Log.i(TAG, getClasseName() + "-----------> ONPOST ----------->"  + codeStatus.toString() );


    }

    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
