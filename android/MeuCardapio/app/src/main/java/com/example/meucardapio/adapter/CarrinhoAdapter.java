package com.example.meucardapio.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meucardapio.R;
import com.example.meucardapio.model.ItemPedido;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoAdapter extends BaseAdapter {

    private final List<ItemPedido> itensPedido;
    private final Activity act;
    public CarrinhoAdapter(List<ItemPedido> itensPedido, Activity act) {
        this.itensPedido = (List<ItemPedido>) itensPedido;
        this.act = act;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.lista_cardapio_personalizada, parent, false);

        ItemPedido itemPedido = itensPedido.get(position);

        TextView nome = (TextView)
                view.findViewById(R.id.lista_cardapio_personalizada_nome_produto);
        TextView quantidade = (TextView)
                view.findViewById(R.id.lista_cardapio_personalizada_qtd_produto);
        /* ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_curso_personalizada_imagem);
*/
        nome.setText(itemPedido.getNomeProduto());
        quantidade.setText(itemPedido.getQuantidade());

        return view;
    }
}
