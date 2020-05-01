package com.example.meucardapio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivityPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);

        final TextView abrirComanda = findViewById(R.id.abrirComanda);

        abrirComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityPrincipalUsuario.class );
                startActivity(intentTelaCadastro);

            }
        });




    }
}
