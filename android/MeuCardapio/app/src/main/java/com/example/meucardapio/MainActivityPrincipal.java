package com.example.meucardapio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meucardapio.model.UsuarioLogado;

public class MainActivityPrincipal extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);
        //VARIAVEL PRA SETAR VALOR NO CAMPO DE TEXTO NO USUÁRIO LOGADO.
        UsuarioLogado usuarioLogado = getIntent().getExtras().getParcelable("usuarioLogado");
        final TextView txtUsuarioLogado = findViewById(R.id.usuarioLogado);
        txtUsuarioLogado.setText(usuarioLogado.getNomeUsuarioLogado());
        //VARIAVEIS PARA TRABALHAR COM OS ICONES DA TELA.
        final LinearLayout abrirComanda = findViewById(R.id.abrirComanda);
        final LinearLayout abrirCardapio = findViewById(R.id.abrirCardapio);
        final LinearLayout abrirCupom = findViewById(R.id.abrirCupom);
        final LinearLayout abrirCarteira = findViewById(R.id.abrirCarteira);
        final LinearLayout abrirAjuda = findViewById(R.id.abrirAjuda);
        final LinearLayout abrirLocalizar = findViewById(R.id.abrirLocalizar);


        Log.i(TAG, getClasseName() + "ID : ----->" + usuarioLogado.getIdUsuarioLogado());

        abrirComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityAbrirMesa.class );
                intentTelaCadastro.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                startActivity(intentTelaCadastro);

            }
        });

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


    private String getClasseName()
    {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }


}