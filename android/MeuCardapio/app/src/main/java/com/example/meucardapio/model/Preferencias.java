package com.example.meucardapio.model;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private static Context context;


    public Preferencias(Context context){
        this.context = context;
    }

    public final static String PREFS_NAME = "preferencias";

    public Boolean getRealizouPedido(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Boolean resultado = sharedPreferences.getBoolean("snRealizouPedido", false);

        if (resultado == true) {

            return true;

        } else {

            return false;
        }




    }


    public int getIdUsuarioLogado() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        int idUsuarioLogado = sharedPreferences.getInt("idUsuarioLogado", 0);
        return idUsuarioLogado;

    }

    public void salvarIdComanda(int idComandaUsuario) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idComandaUsuario", idComandaUsuario);
        editor.apply();
    }

    public void salvarIdUsuarioLogadoPreferencia(int idUsuarioLogado) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idUsuarioLogado", idUsuarioLogado);
        editor.apply();

    }

    public void salvarLoginPreferencia(Boolean chave) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("sn_ativo", chave);
        editor.apply();

    }


    public void salvarMesaUsuario(int idMesaUsuario){

        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idMesaUsuario", idMesaUsuario);
        editor.apply();


    }

    public void snRealizouPedido(Boolean chave){

        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("snRealizouPedido", chave);
        editor.apply();

    }

    public Boolean snVerificaLoginSalvo() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Boolean resultado = sharedPreferences.getBoolean("sn_ativo", false);

        if (resultado == true) {

            return true;
        } else {

            return false;
        }


    }




}
