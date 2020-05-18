package com.example.meucardapio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.meucardapio.adapter.CardapioAdapter;
import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.UsuarioLogado;
import com.example.meucardapio.service.HttpServiceListarCardapio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivityCardapio extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    Cardapio cardapio_teste = new Cardapio();
    List<Cardapio> listarCardapioCompleto;
    private CardapioAdapter cardapioAdapter;
    private RecyclerView cardapio_recycleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cardapio);

        //VARIAVEL PRA SETAR VALOR NO CAMPO DE TEXTO NO USUÁRIO LOGADO.
        UsuarioLogado usuarioLogado = getIntent().getExtras().getParcelable("usuarioLogado");
  //      final TextView txtUsuarioLogado = findViewById(R.id.usuarioLogado);
//        txtUsuarioLogado.setText(usuarioLogado.getNomeUsuarioLogado());

        //VARIAVEIS RECYCLEVIEW
        cardapio_recycleview=(RecyclerView)findViewById(R.id.cardapioRecyclerview);
        cardapio_recycleview.setLayoutManager(new LinearLayoutManager(this));




        try {
            listarCardapioCompleto = new HttpServiceListarCardapio().execute().get();
            cardapioAdapter = new CardapioAdapter(MainActivityCardapio.this, (ArrayList<Cardapio>) listarCardapioCompleto);
            cardapio_recycleview.setAdapter(cardapioAdapter);
            Log.i(TAG, getClasseName() + "JSON CONVERTION DO OBJETO DENTRO DA CLASSE x " + listarCardapioCompleto.toArray());

            int contador = listarCardapioCompleto.size();
            int i ;            for (i = 0; i < contador; i++){
                Log.i(TAG, getClasseName() + "DENTRO DO LAÇO" + listarCardapioCompleto.get(i));
                cardapio_teste = listarCardapioCompleto.get(i);
                Log.i(TAG, getClasseName() + "DENTRO DO LAÇO" + cardapio_teste.getImagem());

            }


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
