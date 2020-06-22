package com.example.meucardapio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.meucardapio.R;
import com.example.meucardapio.model.Preferencias;
import com.example.meucardapio.model.UsuarioLogado;

public class MainActivityCupom extends AppCompatActivity {

    Preferencias preferencias = new Preferencias(MainActivityCupom.this);

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



        preferencias.salvarIdComanda(0);
        preferencias.snRealizouPedido(false);
        preferencias.snAbriuComanda(false);
        preferencias.SalvarIdPagamento(0,false);

    }
}
