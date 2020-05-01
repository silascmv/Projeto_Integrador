package com.example.meucardapio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);

        final TextView abrirComanda = findViewById(R.id.abrirComanda);
        final TextView abrirCardapio = findViewById(R.id.abrirCardapio);
        final TextView abrirCupom = findViewById(R.id.abrirCupom);
        final TextView abrirCarteira = findViewById(R.id.abrirCarteira);
        final TextView abrirAjuda = findViewById(R.id.abrirAjuda );
        final ImageView abrirHelp = findViewById(R.id.abrirHelp );




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
        abrirHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaAjuda = new Intent(getApplicationContext(), MainActivityAjuda.class );
                startActivity(intentTelaAjuda);
            }
        });

    }
}
