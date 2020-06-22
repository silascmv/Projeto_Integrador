package com.example.meucardapio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.meucardapio.R;
import com.example.meucardapio.activities.MainActivityAbrirMesa;
import com.example.meucardapio.activities.MainActivityAjuda;
import com.example.meucardapio.activities.MainActivityCardapio;
import com.example.meucardapio.activities.MainActivityCarteira;
import com.example.meucardapio.activities.MainActivityCupom;
import com.example.meucardapio.activities.MainActivityLocalizar;
import com.example.meucardapio.model.Preferencias;
import com.example.meucardapio.model.UsuarioLogado;

public class MainActivityPrincipal extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    UsuarioLogado usuarioLogado;
    Preferencias preferencias = new Preferencias(MainActivityPrincipal.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);
        buildLayout();

    }

    @Override
    protected void onResume() {
        super.onResume();
        buildLayout();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        buildLayout();
    }

    private void buildLayout(){
        final LinearLayout abrirComanda = findViewById(R.id.abrirComanda);
        final LinearLayout abrirCardapio = findViewById(R.id.abrirCardapio);
        final LinearLayout abrirCupom = findViewById(R.id.abrirCupom);
        final LinearLayout abrirCarteira = findViewById(R.id.abrirCarteira);
        final LinearLayout abrirAjuda = findViewById(R.id.abrirAjuda);
        final LinearLayout abrirLocalizar = findViewById(R.id.abrirLocalizar);

        Log.i("TESTE","TESTE" + preferencias.getSnAbriuComanda());

        Log.i("TESTE","REALIZOU PEDIDO" + preferencias.getRealizouPedido());

        if(preferencias.getSnAbriuComanda() != false) {

            Log.i("CHEGOU AQUI NO IF","IF" + preferencias.getSnAbriuComanda());

            abrirComanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuário já está associado a uma mesa, fique a vontade pra realizar seus pedidos atráves do menu de cardápio", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            });
            abrirCarteira.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentTelaCarteira = new Intent(getApplicationContext(), MainActivityCarteira.class );
                    intentTelaCarteira.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                    startActivity(intentTelaCarteira);
                }
            });



        }else if(!preferencias.getRealizouPedido()){


            Log.i("CHEGOU AQUI NO ELSE IF","IF" + preferencias.getRealizouPedido());




            abrirCarteira.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Para consultar os produtos em sua conta, é necessário que tenha abeto a mesa e realizado alguns pedidos através do cardápio.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            });


            abrirComanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityAbrirMesa.class );
                    intentTelaCadastro.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                    startActivity(intentTelaCadastro);

                }
            });


        }else{

            Log.i("CHEGOU AQUI NO ELSE","ELSE" + preferencias.getRealizouPedido());

            abrirComanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityAbrirMesa.class );
                    intentTelaCadastro.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                    startActivity(intentTelaCadastro);

                }
            });
        }




        abrirCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCardapio = new Intent(getApplicationContext(), MainActivityCardapio.class );
                intentTelaCardapio.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                startActivity(intentTelaCardapio);

            }
        });
        abrirCupom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCupom = new Intent(getApplicationContext(), MainActivityCupom.class );
                intentTelaCupom.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                startActivity(intentTelaCupom);
            }
        });

        abrirAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaAjuda = new Intent(getApplicationContext(), MainActivityAjuda.class );
                intentTelaAjuda.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                startActivity(intentTelaAjuda);
            }
        });

        abrirLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaLocalizar = new Intent(getApplicationContext(), MainActivityLocalizar.class );
                intentTelaLocalizar.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                startActivity(intentTelaLocalizar);
            }
        });




    }


    private boolean verificaComandaAberta(){

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        int resultado = sharedPreferences.getInt("idComandaUsuario", 0);


        if(resultado == 0){

            return false;

        }else{

            return true;
        }

    }



    private String getClasseName(){
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }


}