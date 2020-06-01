package com.example.meucardapio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.meucardapio.adapter.CardapioAdapter;
import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.ItemPedido;
import com.example.meucardapio.model.UsuarioLogado;
import com.example.meucardapio.service.HttpServiceListarCardapio;
import com.example.meucardapio.listener.RecyclerItemClickListener;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivityCardapio extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    //Objeto para Armazenar os itens que estão sendo adicionados ao carrinho
    private List<ItemPedido> itensCarrinho = new ArrayList<>();
    private List<Cardapio> listarCardapioCompleto;

    //Listener e Adapter para Recyclerview
    private CardapioAdapter cardapioAdapter;
    private RecyclerView cardapio_recycleview;

    //Variaveis para setar o valor total dos produtos no carrinho.
    private TextView textQuantidade;
    private TextView valorTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cardapio);

        //Quantidade de itens no carrinho
        textQuantidade = findViewById(R.id.quantidade);

        //VARIAVEL PRA SETAR VALOR NO CAMPO DE TEXTO NO USUÁRIO LOGADO.
        UsuarioLogado usuarioLogado = getIntent().getExtras().getParcelable("usuarioLogado");
        //      final TextView txtUsuarioLogado = findViewById(R.id.usuarioLogado);
//        txtUsuarioLogado.setText(usuarioLogado.getNomeUsuarioLogado());

        //VARIAVEIS RECYCLEVIEW
        cardapio_recycleview = (RecyclerView) findViewById(R.id.cardapioRecyclerview);
        cardapio_recycleview.setLayoutManager(new LinearLayoutManager(this));

        try {
            listarCardapioCompleto = new HttpServiceListarCardapio().execute().get();
            cardapioAdapter = new CardapioAdapter(MainActivityCardapio.this, (ArrayList<Cardapio>) listarCardapioCompleto);
            cardapio_recycleview.setAdapter(cardapioAdapter);
            Log.i(TAG, getClasseName() + "JSON CONVERTION DO OBJETO DENTRO DA CLASSE x " + listarCardapioCompleto.toArray());

            cardapio_recycleview.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            this,
                            cardapio_recycleview,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //

                                    Log.i(TAG, getClasseName() + "clicou ");
                                    confirmarQuantidade(position);
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {

                                }

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                }
                            }
                    )
            );


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void confirmarQuantidade(final int posicao) {
        //Caixa de Dialogo para pegar quantidade de produtos para adicionar ao carrinho.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quantidade");
        builder.setMessage("Digite a quantidade de produtos para adicionar ao carrinho:");
        //Variavel pra armazenar a quantidade de produtos adicionado ao carrinho,definindo valores padrões e definindo visualização
        final EditText editQuantidade = new EditText(this);
        editQuantidade.setText("0");
        builder.setView(editQuantidade);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String quantidade = editQuantidade.getText().toString();

                Log.i(TAG, getClasseName() + "clicou ->");

                Cardapio produtoSelecionado = listarCardapioCompleto.get(posicao);
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setIdProduto(produtoSelecionado.getId_produto());
                itemPedido.setNomeProduto(produtoSelecionado.getNome());
                itemPedido.setPreco(produtoSelecionado.getValor());
                itemPedido.setQuantidade(Integer.parseInt(quantidade));

                itensCarrinho.add(itemPedido);

                Log.i(TAG, getClasseName() + "clicou ->" + itensCarrinho.get(posicao).getPreco());
                int count;
                count = (Integer.valueOf(itensCarrinho.get(0).getQuantidade()));
                //quantidade.setText(count);
                Log.i(TAG, getClasseName() + "clicou ->" + itensCarrinho.get(posicao).getQuantidade());
                Log.i(TAG, getClasseName() + "clicou ->" + editQuantidade.getText());
                editQuantidade.setText(String.valueOf(itensCarrinho.get(posicao).getQuantidade()));
                Log.i(TAG, getClasseName() + "depois ->" + editQuantidade.getText());

                textQuantidade.setText(String.valueOf(itensCarrinho.get(posicao).getQuantidade()));

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
