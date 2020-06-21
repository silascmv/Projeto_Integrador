package com.example.meucardapio.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meucardapio.R;
import com.example.meucardapio.adapter.CardapioAdapter;
import com.example.meucardapio.adapter.OnItemClick;
import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.ItemPedido;
import com.example.meucardapio.model.Preferencias;
import com.example.meucardapio.service.CodeStatus;
import com.example.meucardapio.service.HttpServiceListarCardapio;
import com.example.meucardapio.service.HttpServiceRealizarPedido;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.dmoral.toasty.Toasty;

public class MainActivityCardapio extends AppCompatActivity implements OnItemClick {
    //Preferencias
    Preferencias preferencias = new Preferencias(MainActivityCardapio.this);

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
    private TextView txtSomar;



    private int contadorTexto;
    private int valorTexto;
    private

    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();




    //ListView
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cardapio);

        //Quantidade de itens no carrinho
        textQuantidade = findViewById(R.id.quantidade);
        valorTotal = findViewById(R.id.valor);
        FloatingActionButton btnRealizarPedido = (FloatingActionButton) findViewById(R.id.addCarrinho);
        txtSomar = findViewById(R.id.txtSomar);

        textQuantidade.setText("Quantidade: 0" + "  |");
        valorTotal.setText("Valor Total - R$00,00");

        buildRecyclerView();

        btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, getClasseName() + "CLICOU FLOATING BUTTON ");
                String JsonObject = gson.toJson(cardapioAdapter.retornaCarrinho());
                cardapioAdapter.definirIdComandaItensCarrinho(getIdComandaCliente());
                Log.i(TAG, getClasseName() + "JSON ITENS DO CARRINHO TESTE RETORNO ----->" + cardapioAdapter.retornaCarrinho());
                confirmarPedido(JsonObject, (ArrayList<ItemPedido>) cardapioAdapter.retornaCarrinho());


            }
        });


    }


    private void confirmarPedido(String json, ArrayList<ItemPedido> itensCarrinho) {
        AlertDialog.Builder caixaDeDialogo = new AlertDialog.Builder(MainActivityCardapio.this);
        caixaDeDialogo.setTitle("Deseja confirmar seu Pedido?");

        String[] listaPedidos = new String[itensCarrinho.size()];

        boolean verificaCarrinho = false;


        if(getIdComandaCliente() == 0 ){
            listaPedidos = new String[1];
            listaPedidos[0] = "Para iniciar seus pedidos é necessário iniciar uma mesa no restaurante.";

        }else if(listaPedidos.length == 0) {
            String[] mensagem = {"Adicione produtos ao carrinho para finalizar seu pedido."};
            listaPedidos = new String[]{mensagem[0]};
            verificaCarrinho = true;

        }else{
            listaPedidos = new String[itensCarrinho.size()];
        for (int i = 0; i < itensCarrinho.size(); i++) {
            listaPedidos[i] = itensCarrinho.get(i).getNomeProduto() + "|Quantidade: " + itensCarrinho.get(i).getQuantidade();
        }

        }

        caixaDeDialogo.setItems(listaPedidos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, getClasseName() + "TOTAL DO PRODUTO" + itensCarrinho.get(which).getNomeProduto());


            }


        });


        caixaDeDialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                textQuantidade.setText("Quantidade: 0 |  ");
                valorTotal.setText("Valor Total - R$00,00");
                buildRecyclerView();
                Log.i(TAG, "LISTA DE OBJETOS NO CARRINHO" + json);
                Log.i(TAG, "COMANDA USUARIO" + getComandaUsuario());

                try {
                    CodeStatus retornoRealizarPedido = new HttpServiceRealizarPedido(itensCarrinho).execute().get();

                    if (retornoRealizarPedido.getCode_status() == 1) {

                        Toasty.custom(MainActivityCardapio.this, "Pedido realizado com sucesso!", null, Toast.LENGTH_SHORT, false).show();
                        itensCarrinho.clear();
                        cardapioAdapter.limparCarrinho();
                        preferencias.snRealizouPedido(true);



                    } else {
                        Toasty.custom(MainActivityCardapio.this, "Não foi possivel realizar pedido", null, Toast.LENGTH_SHORT, false).show();


                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = caixaDeDialogo.create();

        String[] finalListaPedidos = listaPedidos;
        boolean finalVerificaCarrinho = verificaCarrinho;
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface arg0) {
                if(getIdComandaCliente() == 0 || finalVerificaCarrinho ==true){

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);

                }else{
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorPrimaryDark);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);
                }
            }
        });


        dialog.show();


    }


    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }

    public void buildRecyclerView() {
        //VARIAVEIS RECYCLEVIEW
        cardapio_recycleview = (RecyclerView) findViewById(R.id.cardapioRecyclerview);
        cardapio_recycleview.setLayoutManager(new LinearLayoutManager(this));

        try {
            listarCardapioCompleto = new HttpServiceListarCardapio().execute().get();
            cardapioAdapter = new CardapioAdapter(MainActivityCardapio.this, (ArrayList<Cardapio>) listarCardapioCompleto,this);
            cardapio_recycleview.setAdapter(cardapioAdapter);
            Log.i(TAG, getClasseName() + "JSON CONVERTION DO OBJETO DENTRO DA CLASSE x " + listarCardapioCompleto.toArray());


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int getComandaUsuario() {

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        int comandaUsuario = sharedPreferences.getInt("idComandaUsuario", 0);
        return comandaUsuario;
    }

    public int getIdComandaCliente() {

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        int comandaUsuario = sharedPreferences.getInt("idComandaUsuario", 0);
        return comandaUsuario;

    }


    @Override
    public void onClickSoma(int value) {
        textQuantidade = findViewById(R.id.quantidade);
        contadorTexto += value;
        textQuantidade.setText("Quantidade: " +  (String.valueOf(contadorTexto) + " " + "|"));
;
        Log.i(TAG,"CLICOUU");
    }

    @Override
    public void onClickSubtrair(int value) {

        textQuantidade = findViewById(R.id.quantidade);
        contadorTexto -= value;
        textQuantidade.setText("Quantidade: " +  (String.valueOf(contadorTexto) + " " + "|"));
        ;
        Log.i(TAG,"CLICOUU");

    }
}
