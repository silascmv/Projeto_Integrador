package com.example.meucardapio.service;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;


public class HttpServiceLogin extends  AsyncTask<Void, Void, String>{

    private final String login;
    private final String cd_senha;
    private static final String TAG = "MyActivity";

    public HttpServiceLogin(String login,String cd_senha) {
        this.login = login;
        this.cd_senha = cd_senha;
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        //Se não for nulo o login e a senha realiza a consulta ao webservice
        if(this.login != null && this.cd_senha !=null){
            try{

                URL url = new URL("https://app-63e8a389-b098-4421-abd4-cc50f39f4df1.cleverapps.io/realizarLogin/" + this.login + "&" + this.cd_senha);
                Log.i(TAG, getClasseName() + "Criou a URL");

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

                Log.i(TAG, getClasseName() + "Resposta : ----->" + resposta);

            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }else{


        }

        return resposta.toString();
    }

    private String getClasseName()
    {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }





}
