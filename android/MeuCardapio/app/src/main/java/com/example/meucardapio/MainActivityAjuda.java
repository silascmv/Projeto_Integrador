package com.example.meucardapio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.meucardapio.model.UsuarioLogado;

public class MainActivityAjuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ajuda);

        //VARIAVEL PRA SETAR VALOR NO CAMPO DE TEXTO NO USUÁRIO LOGADO.
        UsuarioLogado usuarioLogado = getIntent().getExtras().getParcelable("usuarioLogado");
        final TextView txtUsuarioLogado = findViewById(R.id.usuarioLogado);
        txtUsuarioLogado.setText(usuarioLogado.getNomeUsuarioLogado());
    }
}
