package com.example.meucardapio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meucardapio.R;
import com.example.meucardapio.listener.RecyclerItemClickListener;
import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.Conta;
import com.example.meucardapio.model.ItemPedido;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ContaAdapter extends RecyclerView.Adapter<ContaAdapter.ViewHolder> {


    private static final String TAG = "MyActivity";

    private ArrayList<Conta> contas = new ArrayList<>();
    private Context context;
    private RecyclerItemClickListener.OnItemClickListener mListener;

    public ContaAdapter(Context context, ArrayList<Conta> contasModel) {
        this.contas = contasModel;
        this.context = context;

    }

    public void setOnItemClickListener(RecyclerItemClickListener.OnItemClickListener listener) {
        RecyclerItemClickListener.OnItemClickListener mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView nomeProduto, quantidadeProduto, valorTotalProduto,statusProduto;


        public ViewHolder(@NonNull View itemView, final RecyclerItemClickListener.OnItemClickListener listener) {
            super(itemView);


            nomeProduto = (TextView) itemView.findViewById(R.id.nomeProduto);
            quantidadeProduto = (TextView) itemView.findViewById(R.id.quantidadeProduto);
            valorTotalProduto = (TextView) itemView.findViewById(R.id.valorTotalProduto);
            statusProduto = (TextView) itemView.findViewById(R.id.statusProduto);


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


        }

    }







    @NonNull
    @Override
    public ContaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listacontacliente, viewGroup, false);
        return new ContaAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nomeProduto.setText(contas.get(position).getNome_produto());
        holder.quantidadeProduto.setText("Quantidade : " + String.valueOf(contas.get(position).getQuantidade()));
        holder.valorTotalProduto.setText("Valor Total R$ " + String.valueOf(contas.get(position).getValor_total()));
        holder.statusProduto.setText("Status do Pedido : " + contas.get(position).getStatusProduto());



    }

    @Override
    public int getItemCount() {
        return contas.size();
    }

    public void limparConta(){

        contas.clear();

    }







    }

