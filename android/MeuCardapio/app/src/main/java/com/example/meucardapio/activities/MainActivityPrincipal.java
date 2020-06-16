package com.example.meucardapio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.meucardapio.model.UsuarioLogado;

public class MainActivityPrincipal extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    UsuarioLogado usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);

        final LinearLayout abrirComanda = findViewById(R.id.abrirComanda);
        final LinearLayout abrirCardapio = findViewById(R.id.abrirCardapio);
        final LinearLayout abrirCupom = findViewById(R.id.abrirCupom);
        final LinearLayout abrirCarteira = findViewById(R.id.abrirCarteira);
        final LinearLayout abrirAjuda = findViewById(R.id.abrirAjuda);
        final LinearLayout abrirLocalizar = findViewById(R.id.abrirLocalizar);

        if(verificaComandaAberta() == true) {


            abrirComanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuário já está associado a uma mesa, fique a vontade pra realizar seus pedidos atráves do menu de cardápio", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            });



        }else{


            abrirComanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityAbrirMesa.class );
                    intentTelaCadastro.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                    startActivity(intentTelaCadastro);

                }
            });
        }


        /* if(verificaComandaAberta() == true){

            abrirComanda.setVisibility(View.INVISIBLE);
            abrirCarteira.setVisibility(View.VISIBLE);

        }else{

            abrirComanda.setVisibility(View.VISIBLE);
            abrirCarteira.setVisibility(View.INVISIBLE);

        } */





      //  Log.i(TAG, getClasseName() + "ID : ----->" + usuarioLogado.getIdUsuarioLogado());




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
        abrirCarteira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCarteira = new Intent(getApplicationContext(), MainActivityCarteira.class );
                intentTelaCarteira.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                startActivity(intentTelaCarteira);
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

    @Override
    public void onResume() {
        super.onResume();
        //Log.i(TAG, getClasseName() + "MESA : ----->" + usuarioLogado.getMesaUsuarioLogado());

        final LinearLayout abrirComanda = findViewById(R.id.abrirComanda);
        final LinearLayout abrirCarteira = findViewById(R.id.abrirCarteira);

        if(verificaComandaAberta() == true) {

            abrirComanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuário já está associado a uma mesa, fique a vontade pra realizar seus pedidos atráves do menu de cardápio", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            });



        }else{


            abrirComanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityAbrirMesa.class );
                    intentTelaCadastro.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                    startActivity(intentTelaCadastro);

                }
            });
        }


    }

    private String getClasseName()
    {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
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


}