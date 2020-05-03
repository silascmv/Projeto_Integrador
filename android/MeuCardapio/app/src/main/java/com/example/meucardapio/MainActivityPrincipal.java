package com.example.meucardapio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivityPrincipal extends AppCompatActivity {

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

        abrirComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityPrincipalUsuario.class );
                startActivity(intentTelaCadastro);

            }
        });

        abrirCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCardapio = new Intent(getApplicationContext(), MainActivityCardapio.class );
                startActivity(intentTelaCardapio);

            }
        });
        abrirCupom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCupom = new Intent(getApplicationContext(), MainActivityCupom.class );
                startActivity(intentTelaCupom);
            }
        });
        abrirCarteira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCarteira = new Intent(getApplicationContext(), MainActivityCarteira.class );
                startActivity(intentTelaCarteira);
            }
        });
        abrirAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaAjuda = new Intent(getApplicationContext(), MainActivityAjuda.class );
                startActivity(intentTelaAjuda);
            }
        });

        abrirLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaLocalizar = new Intent(getApplicationContext(), MainActivityLocalizar.class );
                startActivity(intentTelaLocalizar);
            }
        });




    }
}