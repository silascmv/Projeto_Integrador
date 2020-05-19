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
    CodeStatus codeStatus;
    private String json;

    public HttpServiceCadastro(Cadastro cadastro) {
        this.cadastro = cadastro;
    }




    @Override
    protected CodeStatus doInBackground(Void... voids) {

        retrofit2.Call<CodeStatus> call = RetrofitClient.getInstance().getApi().addCliente(this.cadastro.getCd_login(),this.cadastro.getCd_senha(),this.cadastro.getNome(),this.cadastro.getEmail(),this.cadastro.getEndereco(),this.cadastro.getTelefone(),this.cadastro.getCelular());

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

    /*protected void onPostExecute(CodeStatus codeStatus){
       // Log.i(TAG, getClasseName() + "-----------> ONPOST ----------->"  + codeStatus.toString() );

    }*/

    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
