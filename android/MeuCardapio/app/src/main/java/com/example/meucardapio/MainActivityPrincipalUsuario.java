package com.example.meucardapio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meucardapio.model.AddMesa;
import com.example.meucardapio.model.UsuarioLogado;
import com.example.meucardapio.service.CodeStatus;
import com.example.meucardapio.service.HtppServiceAddMesa;
import com.example.meucardapio.service.HttpServiceCadastro;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.concurrent.ExecutionException;

public class MainActivityPrincipalUsuario extends AppCompatActivity {

    Button btnIniciarPedidos;
    private static final String TAG = "MyActivity";
    AddMesa abrirMesa = new AddMesa();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal_usuario);
        //VARIAVEL PRA SETAR VALOR NO CAMPO DE TEXTO NO USUÁRIO LOGADO.
        UsuarioLogado usuarioLogado = getIntent().getExtras().getParcelable("usuarioLogado");
        final TextView txtUsuarioLogado = findViewById(R.id.usuarioLogado);
        txtUsuarioLogado.setText(usuarioLogado.getNomeUsuarioLogado());
        //OBJETO PARA MANIPULAÇÃO
        abrirMesa.setId_cliente(usuarioLogado.getIdUsuarioLogado());
        abrirMesa.setId_funcionario(1);
        abrirMesa.setTp_pagamento(1);
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

                    abrirMesa.setQr_code(result.getContents());

                    try {
                        CodeStatus retornoAbrirMesa = new HtppServiceAddMesa(abrirMesa).execute().get();
                        Toast toast = Toast.makeText(getApplicationContext(),retornoAbrirMesa.status, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"Não foi possível realizar operação, tente novamente.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        super.onActivityResult(requestCode, resultCode, data);


        }


    }

    private String getClasseName()
    {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
