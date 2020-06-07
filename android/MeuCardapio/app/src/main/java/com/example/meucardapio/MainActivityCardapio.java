package com.example.meucardapio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meucardapio.adapter.CardapioAdapter;
import com.example.meucardapio.adapter.CarrinhoAdapter;
import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.ItemPedido;
import com.example.meucardapio.model.UsuarioLogado;
import com.example.meucardapio.service.HttpServiceListarCardapio;
import com.example.meucardapio.listener.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.dmoral.toasty.Toasty;

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
    private int contadorTexto;
    private int valorTexto;
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
        //
        textQuantidade.setText("Quantidade: 0" + "  |");
        valorTotal.setText("Valor Total - R$00,00");

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

            btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, getClasseName() + "CLICOU FLOATING BUTTON ");
                    String JsonObject = gson.toJson(itensCarrinho);
                    Log.i(TAG, getClasseName() + "JSON CONVERTIODO" + JsonObject);
                    //confirmarPedido(JsonObject, (ArrayList<ItemPedido>) itensCarrinho);
                    confirmarPedido(JsonObject, (ArrayList<ItemPedido>) itensCarrinho);

                }
            });


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
        editQuantidade.setText("1");
        builder.setView(editQuantidade);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Variavel pra armazenar o valor digitado na caixa de dialogo
                String quantidade = editQuantidade.getText().toString().trim();
                //Objetos pra pegar os valores clicado na tela e adicionar a lista de pedidos
                Cardapio produtoSelecionado = listarCardapioCompleto.get(posicao);
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setIdProduto(produtoSelecionado.getId_produto());
                itemPedido.setNomeProduto(produtoSelecionado.getNome());
                itemPedido.setPreco(produtoSelecionado.getValor());
                itemPedido.setQuantidade(Integer.parseInt(quantidade));

                Log.i(TAG, getClasseName() + "TAMANHO DA LISTA" + itensCarrinho.size());

                if (itensCarrinho.isEmpty() == true) {
                    Log.i(TAG, getClasseName() + "PRIMEIRO IF");
                    itensCarrinho.add(itemPedido);
                    valorTexto += itemPedido.getQuantidade() * itemPedido.getPreco();
                    contadorTexto += itemPedido.getQuantidade();
                    textQuantidade.setText("Quantidade: " + String.valueOf(contadorTexto) + "  |");
                    valorTotal.setText("Valor Total - R$" + String.valueOf(valorTexto));
                } else {
                    boolean existeProduto = false;
                    for (int i = 0; i < itensCarrinho.size(); ++i) {


                        Log.i(TAG, getClasseName() + "VALOR DO PEDIDO" + i);
                        Log.i(TAG, getClasseName() + "VALOR DO PEDIDO" + itemPedido.getIdProduto());
                        Log.i(TAG, getClasseName() + "VALOR DO carinnho" + itensCarrinho.get(i).getIdProduto());
                        if (itensCarrinho.get(i).equals(itemPedido)) {
                            Log.i(TAG, getClasseName() + "JÁ EXISTE ESSE PROUDO NA LISTA");
                            itensCarrinho.get(i).setQuantidade(itensCarrinho.get(i).getQuantidade() + itemPedido.getQuantidade());
                            Log.i(TAG, getClasseName() + "TOTAL DO PRODUTO" + itensCarrinho.get(i).getQuantidade());
                            valorTexto += itemPedido.getQuantidade() * itemPedido.getPreco();
                            contadorTexto += itemPedido.getQuantidade();
                            //Definir valores na tela
                            textQuantidade.setText("Quantidade: " + String.valueOf(contadorTexto) + "  |");
                            valorTotal.setText("Valor Total - R$" + String.valueOf(valorTexto));
                            existeProduto = true;
                            break;
                        }
                    }

                    if (existeProduto != true) {

                        Log.i(TAG, getClasseName() + "SE-SENAO");
                        itensCarrinho.add(itemPedido);
                        valorTexto += itemPedido.getQuantidade() * itemPedido.getPreco();
                        contadorTexto += itemPedido.getQuantidade();
                        textQuantidade.setText("Quantidade: " + String.valueOf(contadorTexto) + "  |");
                        valorTotal.setText("Valor Total - R$" + String.valueOf(valorTexto));
                        existeProduto = false;

                    }

                }

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();


        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorPrimaryDark);
                 }
        });


        dialog.show();

    }

    private void confirmarPedido(String json, ArrayList<ItemPedido> itensCarrinho) {
        AlertDialog.Builder caixaDeDialogo = new AlertDialog.Builder(MainActivityCardapio.this);
        caixaDeDialogo.setTitle("Deseja confirmar seu Pedido?");

        String[] listaPedidos = new String[itensCarrinho.size()];


        for (int i = 0; i < itensCarrinho.size(); i++) {
            listaPedidos[i] = itensCarrinho.get(i).getNomeProduto() + "|Quantidade: " + itensCarrinho.get(i).getQuantidade();
        }

        if(listaPedidos.length == 0){
            String[] mensagem = {"Adicione produtos ao carrinho para finalizar seu pedido."};
            listaPedidos = new String[]{mensagem[0]};
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
                itensCarrinho.clear();
                Toasty.custom(MainActivityCardapio.this,"Pedido realizado com sucesso!",null,Toast.LENGTH_SHORT,false).show();



            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = caixaDeDialogo.create();

        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorPrimaryDark);
            }
        });


        //lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();


      //  dialog.getWindow().setAttributes(lp);
        //dialog.show();
       // dialog.getWindow().setLayout(700,700);


    }


    public void abrirCarrinho(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View rowList = getLayoutInflater().inflate(R.layout.row, null);
        listView = (ListView) findViewById(R.id.listView);
        String[] listaPedidos = new String[itensCarrinho.size()];

        for (int i = 0; i < itensCarrinho.size(); i++) {
            listaPedidos[i] = itensCarrinho.get(i).getNomeProduto() + "|Quantidade: " + itensCarrinho.get(i).getQuantidade();
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaPedidos);

        itemsAdapter.notifyDataSetChanged();
        listView.setAdapter( itemsAdapter);
        alertDialog.setView(rowList);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
