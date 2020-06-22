package com.example.meucardapio.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meucardapio.R;
import com.example.meucardapio.adapter.ContaAdapter;
import com.example.meucardapio.model.Conta;
import com.example.meucardapio.model.Preferencias;
import com.example.meucardapio.service.CodeStatus;
import com.example.meucardapio.service.HtppServiceAddMesa;
import com.example.meucardapio.service.HttpServiceConta;
import com.example.meucardapio.service.HttpServicePagamentoConta;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivityCarteira extends AppCompatActivity {
    Preferencias preferencias = new Preferencias(MainActivityCarteira.this);

    private static final String TAG = "MyActivity";
    private RecyclerView conta_recyclerview;
    private ContaAdapter contaAdapter;
    private List<Conta> contas;

    private Boolean booleanBarraProgresso = false;
    Button btnRealizarPagamento;

    private TextView statusPagamento;

    private TextView txtStatusPagamento;

    private TextView txttotalConta;


    private TextView valorTotalConta;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_carteira);

        statusPagamento = (TextView) findViewById(R.id.statusPagamento);
        txtStatusPagamento = (TextView) findViewById(R.id.txtStatusPagamentoCarteira);

        txttotalConta = (TextView) findViewById(R.id.txttotalConta);

        btnRealizarPagamento = (Button) findViewById(R.id.realizarPagamento);


        Log.i("CHEGOU AQUI NO MENU CARTEIRA", "Chegou antes do  if");

        if (preferencias.getRealizouPedido() != true) {


            Log.i("CHEGOU AQUI NO MENU CARTEIRA", "Chegou aqui no if");


            Toast toast = Toast.makeText(getApplicationContext(), "Você ainda não adicionou nada ao seu carrinho, fique a vontade pra realizar pedidos atráves do menu de cárdapio.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            btnRealizarPagamento.setEnabled(false);

            statusPagamento.setText("");
            txtStatusPagamento.setText("Você ainda não tem produtos na sua conta.");
            txttotalConta.setText("");


        }else{


            Log.i("Chegou aqui", "AQUIII else" ) ;

            Log.i("Chegou aqui", "AQUIII else" + preferencias.getIdComandaCliente()) ;



            if(preferencias.getSnPago() == true){

                btnRealizarPagamento.setEnabled(false);

                Toast toast = Toast.makeText(getApplicationContext(), "Você ainda não adicionou nada ao seu carrinho, fique a vontade pra realizar pedidos atráves do menu de cárdapio.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                btnRealizarPagamento.setEnabled(false);

                statusPagamento.setText("");
                txtStatusPagamento.setText("Você ainda não tem produtos na sua conta.");
                txttotalConta.setText("");


            }else{

                buildRecyclerView();

                statusPagamento = findViewById(R.id.statusPagamento);

                valorTotalConta = findViewById(R.id.txttotalConta);

                valorTotalConta.setText("Valor total R$ " + String.valueOf(retornoValorTotal()));



                btnRealizarPagamento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showAlertDialog();


                }
            });

            }

        }

    }


    public void buildRecyclerView() {
        //VARIAVEIS RECYCLEVIEW
        conta_recyclerview = (RecyclerView) findViewById(R.id.contaRecyclerView);
        conta_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        try {
            contas = new HttpServiceConta(preferencias.getIdComandaCliente()).execute().get();
            contaAdapter = new ContaAdapter(MainActivityCarteira.this, (ArrayList<Conta>) contas);
            conta_recyclerview.setAdapter(contaAdapter);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivityCarteira.this);
        alertDialog.setTitle("Escolha o metódo de Pagamento");
        String[] items = {"Dinheiro", "Máquina de Cartão Crédito/Debito", "Pagamento Online"};
        int checkedItem = 0;
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

                }
            }
        });


        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (tipoDePagamentoSelecionado[0] == 0) {
                    try {
                        pagamentoDinheiro();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setStatusDePagamento("Pendente de Recebimento ");
                    statusPagamento.setText(getStatusDePagamento());

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


    public void pagamentoDinheiro() throws ExecutionException, InterruptedException {
        btnRealizarPagamento.setEnabled(false);
        CodeStatus retornoRealizarPagamento = new HttpServicePagamentoConta(retornoValorTotal(),1,preferencias.getIdComandaCliente()).execute().get();
        //TOAST RETORNO USUÁRIO
        Toast toast = Toast.makeText(getApplicationContext(), retornoRealizarPagamento.status, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        preferencias.SalvarIdPagamento(1,false);






    }

    public void pagamentoCartao() {

        Toast toast = Toast.makeText(getApplicationContext(), "Aguarde alguns instantes, um garçom irá se direcionar até você  com uma máquina de pagamentos para o recebimento do valor", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        btnRealizarPagamento.setEnabled(false);


    }

    private void pagamentoCartaoOnline() {
        //Caixa de Dialogo para pegar quantidade de produtos para adicionar ao carrinho.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Digite o Numero do Cartão");
        //Variavel pra armazenar a quantidade de produtos adicionado ao carrinho,definindo valores padrões e definindo visualização
        final EditText editCodCartao = new EditText(this);
        final EditText editCvv = new EditText(this);
        editCvv.setText("");
        editCodCartao.setText("");
        builder.setView(editCodCartao);
        //builder.setView(editCvv);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Variavel pra armazenar o valor digitado na caixa de dialogo
                String codCartao = editCodCartao.getText().toString().trim();

                getCvv();


                Log.i(TAG, "TAMANHO DA LISTA" + codCartao);


            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.setCancelable(false);


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorPrimaryDark);
            }
        });


        dialog.show();

    }

    private void getCvv() {

        //Caixa de Dialogo para pegar quantidade de produtos para adicionar ao carrinho.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Digite o Código de Segurança do Cartão");
        //Variavel pra armazenar a quantidade de produtos adicionado ao carrinho,definindo valores padrões e definindo visualização

        final EditText editCvv = new EditText(this);
        editCvv.setText("");
        builder.setView(editCvv);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Variavel pra armazenar o valor digitado na caixa de dialogo
                String codCartao = editCvv.getText().toString().trim();
                getNomeCartao();
                Log.i(TAG, "TAMANHO DA LISTA" + codCartao);


            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.setCancelable(false);


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorPrimaryDark);
            }
        });


        dialog.show();


    }

    private void getNomeCartao() {

        //Caixa de Dialogo para pegar quantidade de produtos para adicionar ao carrinho.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Digite o nome como está no Cartão");
        //Variavel pra armazenar a quantidade de produtos adicionado ao carrinho,definindo valores padrões e definindo visualização

        final EditText editNomeCartao = new EditText(this);
        editNomeCartao.setText("");
        builder.setView(editNomeCartao);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Variavel pra armazenar o valor digitado na caixa de dialogo
                String nomeCartao = editNomeCartao.getText().toString().trim();
                // getDataValidade().show();
                getDataValidade();

                Log.i(TAG, "TAMANHO DA LISTA" + nomeCartao);


            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.setCancelable(false);


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorPrimaryDark);
            }
        });


        dialog.show();


    }


    private void barraDeProgressoPagamento() {

        progress = new ProgressDialog(this);
        progress.setMessage("Processando Pagamento");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setCancelable(false);
        progress.setProgress(0);

        progress.show();


        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }

                if (jumpTime >= 100) {

                    // sleep 2 seconds, so that you can see the 100%
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // close the progress bar dialog

                    MainActivityCarteira.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progress.dismiss();
                            Toast toast = Toast.makeText(MainActivityCarteira.this, "PAGAMENTO REALIZADO COM SUCESSO.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            setStatusDePagamento("Pago");
                            statusPagamento.setText(getStatusDePagamento());
                            valorTotalConta.setText("");
                            contaAdapter.limparConta();
                            preferencias.snRealizouPedido(false);


                        }
                    });


                }


            }
        };
        t.start();


    }


    private void getDataValidade() {

        int yearSelected;
        int monthSelected;
        String customTitle = "Informe a data de validade do seu Cartão";

        //Set default values
        Calendar calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);

        MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                .getInstance(monthSelected, yearSelected, customTitle);

        dialogFragment.show(getSupportFragmentManager(), null);

        dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int year, int monthOfYear) {
                // do something
                barraDeProgressoPagamento();

                Toast.makeText(MainActivityCarteira.this, "Dinheiro", Toast.LENGTH_LONG).show();


            }
        });


    }

    private boolean verificaComandaAberta() {

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        int resultado = sharedPreferences.getInt("idComandaUsuario", 0);


        if (resultado == 0) {

            return false;

        } else {

            return true;
        }

    }


    private Double retornoValorTotal() {

        double calcularTotal = 0;

        for (int i = 0; i < contas.size(); i++) {


            calcularTotal += contas.get(i).getValor_total();

        }


        return calcularTotal;
    }


    public int getIdComanda() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        int idComandaUsuario = sharedPreferences.getInt("idComandaUsuario", 0);

        return idComandaUsuario;


    }

    private void setStatusDePagamento(String statusPagamento) {

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("statusPagamento", statusPagamento);
        editor.apply();


    }

    public String getStatusDePagamento() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String statusPagamento = sharedPreferences.getString("statusPagamento", "em Aberto");


        return statusPagamento;

    }


}
