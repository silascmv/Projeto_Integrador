package com.example.meucardapio.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meucardapio.R;
import com.example.meucardapio.adapter.CardapioAdapter;
import com.example.meucardapio.adapter.ContaAdapter;
import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.Conta;
import com.example.meucardapio.model.ItemPedido;
import com.example.meucardapio.model.UsuarioLogado;
import com.example.meucardapio.service.CodeStatus;
import com.example.meucardapio.service.HttpServiceConta;
import com.example.meucardapio.service.HttpServiceListarCardapio;
import com.example.meucardapio.service.HttpServiceRealizarPedido;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.dmoral.toasty.Toasty;

public class MainActivityCarteira extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    private RecyclerView conta_recyclerview;
    private ContaAdapter contaAdapter;
    private List<Conta> contas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_carteira);
            buildRecyclerView();
        Button btnRealizarPagamento = (Button) findViewById(R.id.realizarPagamento);

        btnRealizarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDialog();



            }
        });



    }

    public void buildRecyclerView() {
        //VARIAVEIS RECYCLEVIEW
        conta_recyclerview = (RecyclerView) findViewById(R.id.contaRecyclerView);
        conta_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        try {
            contas = new HttpServiceConta(getIdComanda()).execute().get();
            contaAdapter = new ContaAdapter(MainActivityCarteira.this, (ArrayList<Conta>) contas);
            conta_recyclerview.setAdapter(contaAdapter);

            for(int i = 0 ; i < contas.size(); i++){

                Log.i(TAG,  "JSON CONVERTION DO OBJETO DENTRO DA CLASSE ------------> " + contas.get(i).getQuantidade());

                Log.i(TAG,  "JSON CONVERTION DO OBJETO DENTRO DA CLASSE ------------> " + contas.get(i).getNome_produto());

                Log.i(TAG,  "JSON CONVERTION DO OBJETO DENTRO DA CLASSE ------------> " + contas.get(i).getValor_total());


            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivityCarteira.this);
        alertDialog.setTitle("Escolha o metódo de Pagamento");
        String[] items = {"Dinheiro","Cartão de Crédito/Debito","Pagamento Online","Ticket"};
        int checkedItem = 1;
        final int[] tipoDePagamentoSelecionado = new int[1];
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(MainActivityCarteira.this, "Dinheiro", Toast.LENGTH_LONG).show();
                        tipoDePagamentoSelecionado[0] = which;
                        break;
                    case 1:
                        Toast.makeText(MainActivityCarteira.this, "Cartão de Crédito/Debito", Toast.LENGTH_LONG).show();
                        tipoDePagamentoSelecionado[0] = which;
                        break;
                    case 2:
                        Toast.makeText(MainActivityCarteira.this, "Pagamento Online", Toast.LENGTH_LONG).show();
                        tipoDePagamentoSelecionado[0] = which;
                        break;
                    case 3:
                        Toast.makeText(MainActivityCarteira.this, "Ticket", Toast.LENGTH_LONG).show();
                        tipoDePagamentoSelecionado[0] = which;
                        break;
                }
            }
        });


        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (tipoDePagamentoSelecionado[0] == 0) {
                    pagamentoDinheiro();

                } else if (tipoDePagamentoSelecionado[0] == 1) {

                    pagamentoCartao();

                } else if (tipoDePagamentoSelecionado[0] == 2)

                   pagamentoCartaoOnline();
            }


        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }



    public void pagamentoDinheiro(){

        Toast toast = Toast.makeText(getApplicationContext(), "Aguarde alguns instantes, um garçom irá se direcionar até você pra receber o pagamento em dinheiro.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();


    }

    public void pagamentoCartao(){

        Toast toast = Toast.makeText(getApplicationContext(), "Aguarde alguns instantes, um garçom irá se direcionar até você com uma máquina de cartão de crédito/debito para receber o pagamento em cartão..", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();


    }

    private void pagamentoCartaoOnline() {
        //Caixa de Dialogo para pegar quantidade de produtos para adicionar ao carrinho.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Digite o Numero do Cartão");
        //Variavel pra armazenar a quantidade de produtos adicionado ao carrinho,definindo valores padrões e definindo visualização
        final EditText editCodCartao = new EditText(this);
        final EditText editCvv = new EditText(this);
        editCvv.setText("CVV");
        editCodCartao.setText("");
        builder.setView(editCodCartao);
        //builder.setView(editCvv);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Variavel pra armazenar o valor digitado na caixa de dialogo
                String codCartao = editCodCartao.getText().toString().trim();


                Log.i(TAG, "TAMANHO DA LISTA" + codCartao);



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




    public int getIdComanda(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        int idComandaUsuario = sharedPreferences.getInt("idComandaUsuario", 0);

        return idComandaUsuario;


    }
}
