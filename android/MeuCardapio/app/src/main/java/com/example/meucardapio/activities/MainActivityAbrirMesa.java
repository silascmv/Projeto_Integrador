package com.example.meucardapio.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.meucardapio.R;
import com.example.meucardapio.model.AddMesa;
import com.example.meucardapio.model.Preferencias;
import com.example.meucardapio.model.UsuarioLogado;
import com.example.meucardapio.service.CodeStatus;
import com.example.meucardapio.service.HtppServiceAddMesa;
import com.google.zxing.Result;

import java.util.concurrent.ExecutionException;

import static android.app.PendingIntent.getActivity;
import static com.example.meucardapio.R.*;

public class MainActivityAbrirMesa extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    //PREFERENCIAS
    Preferencias preferencias = new Preferencias(MainActivityAbrirMesa.this);

    //LOG
    private static final String TAG = "MyActivity";
    //ATIVIDADE
    private MainActivity parent;
    // OBJETO USADO NAS OPERAÇÕES
    AddMesa abrirMesa = new AddMesa();
    //IMPORTAÇÃO BIBLITECA DO SCANNER
    private ZXingScannerView mScannerView;
    //VERIFICAÇÃO FLASH
    Boolean verificaFlash = false;
    //VARIAVEL GLOBAL
    RelativeLayout rl;
    private Object activity;
    UsuarioLogado usuarioLogado;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        usuarioLogado = getIntent().getExtras().getParcelable("usuarioLogado");
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_abrir_mesa);
        Log.i(TAG,  "------> ON CREATE <--------- ");
        rl = (RelativeLayout) findViewById(id.relative_scan);
        mScannerView = new ZXingScannerView(this);


    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "------> ON  RESUME <--------- ");
        setContentView(layout.activity_main_abrir_mesa);
        //VARIAVEIS PARA TRABALHAR COM O FLASH e CAMERA - LAYOUT
        final ImageView btnflash = findViewById(id.btnFlash);
        final ImageView btnFlashDesligado = findViewById(R.id.btnFlashDesligado);
        rl = (RelativeLayout) findViewById(id.relative_scan);
        //Variavies pra trabalhar com o scanner e configuração do mesmo
        mScannerView = new ZXingScannerView(this);
        rl.addView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.setBorderColor(Color.WHITE);
        mScannerView.setLaserColor(Color.RED);
        mScannerView.setBorderLineLength(30);
        mScannerView.startCamera();


        //FUNÇÃO PARA LIGAR E DESLIGAR FLASH
        btnflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verificaFlash == false) {

                    mScannerView.setFlash(true);
                    verificaFlash = true;

                    btnflash.setVisibility(View.INVISIBLE);
                    btnFlashDesligado.setVisibility(View.VISIBLE);
                } else {

                    mScannerView.setFlash(false);

                    verificaFlash = false;
                }


            }
        });

        btnFlashDesligado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verificaFlash == true) {

                    mScannerView.setFlash(false);
                    verificaFlash = false;
                    btnflash.setVisibility(View.VISIBLE);
                    btnFlashDesligado.setVisibility(View.INVISIBLE);
                } else {

                    mScannerView.setFlash(false);

                    verificaFlash = false;
                }


            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "------> ON PAUSE <--------- ");
        mScannerView.stopCameraPreview();
        // Stop camera on pause
        //Camera camera = CameraUtils.getCameraInstance();
        //if (camera != null) {
        //   camera.release();

        // }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,  "------> ON STOP <--------- ");
        mScannerView.stopCamera();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i(TAG, "------> ON RESTART <--------- ");
        mScannerView.startCamera();          // Start camera on resume

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,  "------> ON DESTROY <--------- ");
        mScannerView.stopCameraPreview(); //stopPreview
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {

        if (result.getText() != null) {

            abrirMesa.setQr_code(result.getText());
            abrirMesa.setId_cliente(preferencias.getIdUsuarioLogado());
            abrirMesa.setId_funcionario(1);
            abrirMesa.setTp_pagamento(1);


            try {
                CodeStatus retornoAbrirMesa = new HtppServiceAddMesa(abrirMesa).execute().get();
                //TOAST RETORNO USUÁRIO
                Toast toast = Toast.makeText(getApplicationContext(), retornoAbrirMesa.status, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                preferencias.salvarIdComanda(retornoAbrirMesa.getId_comanda());
                preferencias.salvarMesaUsuario(retornoAbrirMesa.getMesa_cliente());
                preferencias.snAbriuComanda(true);
                preferencias.SalvarIdPagamento(0,false);
                Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityPrincipal.class);

                //SCANNER
                mScannerView.startCamera();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScannerView.resumeCameraPreview(MainActivityAbrirMesa.this);
                        startActivity(intentTelaCadastro);

                    }
                }, 2000);
            } catch (ExecutionException e) {
                e.printStackTrace();
                Log.i(TAG,  "------> ON DESTROY <--------- " + e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Não foi possível realizar operação, tente novamente.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

    }





}