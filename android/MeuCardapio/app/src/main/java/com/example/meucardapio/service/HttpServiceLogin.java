package com.example.meucardapio.service;


import android.os.AsyncTask;
import android.util.Log;


import com.example.meucardapio.model.*;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class HttpServiceLogin extends  AsyncTask<Void, Void, CodeStatus>{

    private final String login;
    private final String cd_senha;
    private static final String TAG = "MyActivity";
    JSONObject jsonObj;

    public HttpServiceLogin(String login,String cd_senha) {
        this.login = login;
        this.cd_senha = cd_senha;
    }

    @Override
    protected CodeStatus doInBackground(Void... voids) {
        Gson gson = new Gson();

        StringBuilder resposta = new StringBuilder();
        //Se não for nulo o login e a senha realiza a consulta ao webservice
        if(this.login != null && this.cd_senha !=null){
            try{

                URL url = new URL("https://app-63e8a389-b098-4421-abd4-cc50f39f4df1.cleverapps.io/realizarLogin/" + this.login + "&" + this.cd_senha);
                Log.i(TAG, getClasseName() + "Criou a URL");
                Log.i(TAG, getClasseName() + "URL ::::::::::::" + url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    resposta.append(scanner.next());
                }

                Log.i(TAG, getClasseName() + "Resposta : ----->" + resposta.getClass().getName());
                Log.i(TAG, getClasseName() + "Resposta : ----->" + resposta);
                Log.i(TAG, getClasseName() + "Resposta : ANTES DO JSON");
                jsonObj = new JSONObject(resposta.toString());
                Log.i(TAG, getClasseName() + "Resposta : ----->" + jsonObj);
                Log.i(TAG, getClasseName() + "Resposta : ----->" + jsonObj.getClass().getName());


            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else{


        }

        CodeStatus retorno_objeto = gson.fromJson(String.valueOf(jsonObj), CodeStatus.class);



        return retorno_objeto;
    }

    private String getClasseName()
    {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }





}
