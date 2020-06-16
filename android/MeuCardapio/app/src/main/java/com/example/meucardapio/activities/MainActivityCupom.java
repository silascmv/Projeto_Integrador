package com.example.meucardapio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.meucardapio.R;
import com.example.meucardapio.model.UsuarioLogado;

public class MainActivityCupom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cupom);
        //VARIAVEL PRA SETAR VALOR NO CAMPO DE TEXTO NO USUÁRIO LOGADO.
        //UsuarioLogado usuarioLogado = getIntent().getExtras().getParcelable("usuarioLogado");
        //final TextView txtUsuarioLogado = findViewById(R.id.usuarioLogado);
        //txtUsuarioLogado.setText(usuarioLogado.getNomeUsuarioLogado());

        deslogarUsuario();
    }




    private void deslogarUsuario(){



        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("sn_ativo", false );
        editor.putInt("idComandaUsuario",0);
        editor.commit();

    }
}
