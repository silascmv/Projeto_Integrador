package com.example.meucardapio.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meucardapio.R;
import com.example.meucardapio.listener.RecyclerItemClickListener;
import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.ItemPedido;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardapioAdapter extends RecyclerView.Adapter<CardapioAdapter.ViewHolder> {

    private static final String TAG = "MyActivity";


    private ArrayList<Cardapio> cardapioModel = new ArrayList<>();
    private List<ItemPedido> itensCarrinho = new ArrayList<>();

    private Context context;
    private RecyclerItemClickListener.OnItemClickListener mListener;

    public CardapioAdapter(Context context, ArrayList<Cardapio> cardapioModel) {
        this.cardapioModel = cardapioModel;
        this.context = context;

    }

    public void setOnItemClickListener(RecyclerItemClickListener.OnItemClickListener listener) {
        mListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagemProduto;
        private TextView nomeProduto, descricaoProduto, valorProduto, contadorProduto, somarProduto, subtrairProduto, txtContador;
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
                    txtContador.setText(String.valueOf(contador));

                    if (itensCarrinho.isEmpty() == true) {

                        itensCarrinho.add(itemPedido);

                    } else {

                        boolean existeProduto = false;

                        for (int i = 0; i < itensCarrinho.size(); ++i) {

                            if (itensCarrinho.get(i).equals(itemPedido)) {
                                // Log.i(TAG, getClasseName() + "JÁ EXISTE ESSE PROUDO NA LISTA");
                                itensCarrinho.get(i).setQuantidade(contador);
                                existeProduto = true;
                                break;
                            }


                        }

                        if (existeProduto != true) {
                            itensCarrinho.add(itemPedido);
                            existeProduto = false;

                        }


                    }

                    for (int i = 0; i < itensCarrinho.size(); ++i) {

                        Log.i(TAG, "---PRODUTOS NO CARRINHO----");
                        Log.i(TAG, "---NOME DO PRODUTO NO CARRINHO----" + itensCarrinho.get(i).getNomeProduto());
                        Log.i(TAG, "---QUANTIDADE DO PRODUTO CARRINHO----" + itensCarrinho.get(i).getQuantidade());


                    }


                }


            });


            subtrairProduto.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    Log.i(TAG, "CLICOU NO subtrairProduto");

                    int subtracao = Integer.parseInt(txtContador.getText().toString());
                    --subtracao;
                    txtContador.setText(String.valueOf(subtracao));

                    Cardapio produtoSelecionado = cardapioModel.get(getAdapterPosition());
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setIdProduto(produtoSelecionado.getId_produto());
                    itemPedido.setNomeProduto(produtoSelecionado.getNome());
                    itemPedido.setPreco(produtoSelecionado.getValor());


                    if (subtracao == 0) {
                        boolean existeProduto = false;
                        txtContador.setText(String.valueOf(subtracao));
                        for(int i = 0 ; i < itensCarrinho.size(); ++i){

                            if (itensCarrinho.get(i).equals(itemPedido)) {
                                itensCarrinho.remove(i);
                                break;
                            }

                        }

                      //  itemPedido.setQuantidade(subtracao);

                    }else{


                        for (int i = 0; i < itensCarrinho.size(); ++i) {

                            if (itensCarrinho.get(i).equals(itemPedido)) {
                                 Log.i(TAG,  "JÁ EXISTE ESSE PROUDO NA LISTA - SUBTRAÇÃO");
                                itensCarrinho.get(i).setQuantidade(subtracao);
                                break;
                            }


                        }


                    }





                /*    if(subtracao == -1){

                        subtracao = 0;
                        return;

                    }
*/
  /*                  if (subtracao == 0) {
                        txtContador.setText(String.valueOf(0));
                        Log.i(TAG, "IF SUBTRAÇÃO IGUAL A 0");
                        for (int i = 0; i < itensCarrinho.size(); ++i) {

                            if (itensCarrinho.get(i).equals(itemPedido)) {
                                Log.i(TAG,  "ENTROU NO FOR SENÃO IGUAL A 0");
                                ultimoItemLista = true;
                               // getPosicao = i;
                                break;
                            }

                        }





                    } else {

                        Log.i(TAG, "SENÃO");

                        --subtracao;
                        txtContador.setText(String.valueOf(subtracao));


                        for (int i = 0; i < itensCarrinho.size(); ++i) {

                            if (itensCarrinho.get(i).equals(itemPedido)) {
                                // Log.i(TAG, getClasseName() + "JÁ EXISTE ESSE PROUDO NA LISTA");
                              //  getPosicao = i;
                                break;
                            }

                        }

                      //  itensCarrinho.get(getPosicao).setQuantidade(subtracao);


                    } */

/*                    if (itensCarrinho.isEmpty() == true) {

                        return;

                    }

*/
  /*                  if (existeProduto != false) {

                      //  itensCarrinho.remove(getPosicao);
                    }


                    for (int i = 0; i < itensCarrinho.size(); ++i) {

                        Log.i(TAG, "---PRODUTOS NO CARRINHO - SUBTRAÇÃO----");
                        Log.i(TAG, "---NOME DO PRODUTO NO CARRINHO----" + itensCarrinho.get(i).getNomeProduto());
                        Log.i(TAG, "---QUANTIDADE DO PRODUTO CARRINHO----" + itensCarrinho.get(i).getQuantidade());


                    }

*/
                }
            });

            //          somarProduto=(ImageButton) itemView.findViewById(R.id.somar);
            //        subtrairProduto=(ImageButton) itemView.findViewById(R.id.subtrair);
            //      contadorProduto = (TextView) itemView.findViewById(R.id.contador);

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
        holder.somarProduto.setTextSize(40);
        holder.somarProduto.setText("+");
        holder.subtrairProduto.setTextSize(40);
        holder.txtContador.setTextSize(30);

        Picasso.get().load(cardapioModel.get(position).getImagem()).into(holder.imagemProduto);

//       holder.contadorProduto.setText(""+ 0);


    }

    @Override
    public int getItemCount() {
        return cardapioModel.size();
    }


}
