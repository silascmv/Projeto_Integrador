package com.example.meucardapio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivityPrincipalUsuario extends AppCompatActivity {

    Button btnIniciarPedidos;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal_usuario);








        btnIniciarPedidos = findViewById(R.id.btnIniciarPedidos);
        final Activity activity = this;


        btnIniciarPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setOrientationLocked(false);
                integrator.setPrompt("Inicie seus pedidos lendo o QRCODE em cima da mesa");
                integrator.setCameraId(0);
                integrator.initiateScan();


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){

                if(result.getContents() != null){
                    Toast toast = Toast.makeText(getApplicationContext(),result.getContents(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"Não foi possível realizar operação, tente novamente.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        super.onActivityResult(requestCode, resultCode, data);


        }


    }
}
