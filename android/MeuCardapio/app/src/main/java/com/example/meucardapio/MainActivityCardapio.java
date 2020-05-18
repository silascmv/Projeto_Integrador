package com.example.meucardapio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.UsuarioLogado;
import com.example.meucardapio.service.HttpServiceListarCardapio;

import java.util.concurrent.ExecutionException;

public class MainActivityCardapio extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cardapio);
        //VARIAVEL PRA SETAR VALOR NO CAMPO DE TEXTO NO USUÁRIO LOGADO.
        UsuarioLogado usuarioLogado = getIntent().getExtras().getParcelable("usuarioLogado");
        final TextView txtUsuarioLogado = findViewById(R.id.usuarioLogado);
        txtUsuarioLogado.setText(usuarioLogado.getNomeUsuarioLogado());
        try {
            Cardapio listarCardapioCompleto = new HttpServiceListarCardapio().execute().get();
            Log.i(TAG, getClasseName() + "JSON CONVERTION DO OBJETO " + listarCardapioCompleto);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
