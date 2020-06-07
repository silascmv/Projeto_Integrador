package com.example.meucardapio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meucardapio.R;
import com.example.meucardapio.model.Cardapio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardapioAdapter extends RecyclerView.Adapter<CardapioAdapter.ViewHolder> {


    private ArrayList<Cardapio> cardapioModel = new ArrayList<>();
    private Context context;

    public CardapioAdapter(Context context, ArrayList<Cardapio> cardapioModel) {
        this.cardapioModel = cardapioModel;
        this.context = context;

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagemProduto;
        private TextView nomeProduto, descricaoProduto,valorProduto,contadorProduto,somarProduto;
        //private ImageButton somarProduto,subtrairProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemProduto = (ImageView) itemView.findViewById(R.id.imagemProduto);
            nomeProduto = (TextView) itemView.findViewById(R.id.nomeProduto);
            descricaoProduto = (TextView) itemView.findViewById(R.id.descricaoProduto);
            valorProduto=(TextView) itemView.findViewById(R.id.valorProduto);
            somarProduto=(TextView) itemView.findViewById((R.id.txtSomar));

  //          somarProduto=(ImageButton) itemView.findViewById(R.id.somar);
    //        subtrairProduto=(ImageButton) itemView.findViewById(R.id.subtrair);
      //      contadorProduto = (TextView) itemView.findViewById(R.id.contador);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listacardapio, viewGroup, false);
        return new CardapioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nomeProduto.setText(cardapioModel.get(position).getNome());
        holder.descricaoProduto.setText(cardapioModel.get(position).getDescricao());
        holder.valorProduto.setText(cardapioModel.get(position).getValor().toString());
        holder.somarProduto.setText(0);
        Picasso.get().load(cardapioModel.get(position).getImagem()).into(holder.imagemProduto);

//       holder.contadorProduto.setText(""+ 0);


    }

    @Override
    public int getItemCount() {
        return cardapioModel.size();
    }


}
