package com.example.meucardapio.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meucardapio.R;
import com.example.meucardapio.activities.MainActivityCardapio;
import com.example.meucardapio.listener.RecyclerItemClickListener;
import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.ItemPedido;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardapioAdapter extends RecyclerView.Adapter<CardapioAdapter.ViewHolder> {

    private static final String TAG = "MyActivity";

    private static MainActivityCardapio cardapio;

    private OnItemClick mCallback;


    private ArrayList<Cardapio> cardapioModel = new ArrayList<>();
    private List<ItemPedido> itensCarrinho = new ArrayList<>();

    private Context context;
    private RecyclerItemClickListener.OnItemClickListener mListener;

    private int contadorTexto;
    private double valorTexto;
    private int somaQuantidadeCarrinho;


    public CardapioAdapter(Context context, ArrayList<Cardapio> cardapioModel, OnItemClick listener) {
        this.cardapioModel = cardapioModel;
        this.context = context;
        this.mCallback = listener;

    }

    public void setOnItemClickListener(RecyclerItemClickListener.OnItemClickListener listener) {
        mListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagemProduto;
        private TextView nomeProduto, descricaoProduto, valorProduto, somarProduto, subtrairProduto, txtContador, textQuantidade, valorTotal;
        private LinearLayout linearLayoutSomar, linearLayoutSubtrair;

        private TextView teste;


        //private ImageButton somarProduto,subtrairProduto;

        public ViewHolder(@NonNull View itemView, final RecyclerItemClickListener.OnItemClickListener listener) {
            super(itemView);


            imagemProduto = (ImageView) itemView.findViewById(R.id.imagemProduto);
            nomeProduto = (TextView) itemView.findViewById(R.id.nomeProduto);
            descricaoProduto = (TextView) itemView.findViewById(R.id.descricaoProduto);
            valorProduto = (TextView) itemView.findViewById(R.id.valorProduto);
            somarProduto = (TextView) itemView.findViewById((R.id.txtSomar));
            subtrairProduto = (TextView) itemView.findViewById(R.id.txtSubtrair);
            txtContador = (TextView) itemView.findViewById(R.id.txtContador);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);

                        }
                    }
                }
            });


            somarProduto.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    int contador = Integer.parseInt(txtContador.getText().toString());
                    ++contador;
                    Cardapio produtoSelecionado = cardapioModel.get(getAdapterPosition());
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setIdProduto(produtoSelecionado.getId_produto());
                    itemPedido.setNomeProduto(produtoSelecionado.getNome());
                    itemPedido.setPreco(produtoSelecionado.getValor());
                    itemPedido.setQuantidade(contador);
                    Log.i(TAG, "CLICOU NO SOMAR" + itemPedido.getQuantidade());
                    //mCallback.onClick(itemPedido.getQuantidade());
                    txtContador.setText(String.valueOf(contador));

                    if (itensCarrinho.isEmpty() == true) {

                        itensCarrinho.add(itemPedido);
                        valorTexto += itemPedido.getQuantidade() * itemPedido.getPreco();
                        contadorTexto += itemPedido.getQuantidade();
                        mCallback.onClickSoma(1);


                        Log.i(TAG, "CLICOU NO SOMAR aq 01");


                        mCallback.onClickSomarTotalCompra( itemPedido.getQuantidade() * itemPedido.getPreco());


                        Log.i(TAG, "CLICOU NO SOMAR aq 01");


                    } else {

                        boolean existeProduto = false;

                        for (int i = 0; i < itensCarrinho.size(); ++i) {

                            if (itensCarrinho.get(i).equals(itemPedido)) {
                                itensCarrinho.get(i).setQuantidade(contador);

                                existeProduto = true;
                                break;
                            }


                            Log.i(TAG, "CLICOU NO SOMAR aq 02");


                        }

                        if (existeProduto != true) {
                            itensCarrinho.add(itemPedido);
                            existeProduto = false;

                            Log.i(TAG, "CLICOU NO SOMAR aq 03");

                            mCallback.onClickSoma(1);
                            mCallback.onClickSomarTotalCompra(itemPedido.getPreco());

                        } else {

                            Log.i(TAG, "CLICOU NO SOMAR aq 03");

                            mCallback.onClickSoma(1);
                            mCallback.onClickSomarTotalCompra(itemPedido.getPreco());
                        }


                    }


                }


            });

            subtrairProduto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "CLICOU NO subtrairProduto");
                    int subtracao = Integer.parseInt(txtContador.getText().toString());
                    --subtracao;

                    Cardapio produtoSelecionado = cardapioModel.get(getAdapterPosition());
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setIdProduto(produtoSelecionado.getId_produto());
                    itemPedido.setNomeProduto(produtoSelecionado.getNome());
                    itemPedido.setPreco(produtoSelecionado.getValor());


                    if (subtracao == -1 || itensCarrinho.isEmpty() == true) {
                        Log.i(TAG, "O CARRINHO TA VAZIO");
                        subtracao = 0;

                        txtContador.setText(String.valueOf(subtracao));
                    }

                    if (subtracao == 0) {
                        boolean existeProduto = false;

                        txtContador.setText(String.valueOf(subtracao));
                        for (int i = 0; i < itensCarrinho.size(); ++i) {
                            if (itensCarrinho.get(i).equals(itemPedido)) {
                                itensCarrinho.remove(i);
                                mCallback.onClickSubtrair(1);
                                mCallback.onClickSubtrairTotalCompra(itemPedido.getPreco());
                                break;
                            }
                        }


                    } else {
                        for (int i = 0; i < itensCarrinho.size(); ++i) {
                            if (itensCarrinho.get(i).equals(itemPedido)) {
                                Log.i(TAG, "JÁ EXISTE ESSE PROUDO NA LISTA - SUBTRAÇÃO");
                                itensCarrinho.get(i).setQuantidade(subtracao);

                                break;
                            }
                        }
                        txtContador.setText(String.valueOf(subtracao));
                        mCallback.onClickSubtrair(1);
                        mCallback.onClickSubtrairTotalCompra(itemPedido.getPreco());

                    }
                }
            });


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listacardapio, viewGroup, false);
        return new CardapioAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nomeProduto.setText(cardapioModel.get(position).getNome());
        holder.descricaoProduto.setText(cardapioModel.get(position).getDescricao());
        holder.valorProduto.setText(cardapioModel.get(position).getValor().toString());
        holder.somarProduto.setTextSize(20);
        holder.somarProduto.setText("+");
        holder.subtrairProduto.setTextSize(20);
        holder.txtContador.setTextSize(20);
        Picasso.get().load(cardapioModel.get(position).getImagem()).into(holder.imagemProduto);


    }

    @Override
    public int getItemCount() {
        return cardapioModel.size();
    }

    public List<ItemPedido> retornaCarrinho() {
        return itensCarrinho;

    }

    public void limparCarrinho() {

        itensCarrinho.clear();

    }

    public int getTotalProdutos() {

        return contadorTexto;


    }

    public void definirIdComanda(int idComanda) {


        for (int i = 0; i < itensCarrinho.size(); i++) {

            itensCarrinho.get(i).setIdComanda(idComanda);
            itensCarrinho.get(i).setStatusProduto("Pendente");


        }


    }


}
