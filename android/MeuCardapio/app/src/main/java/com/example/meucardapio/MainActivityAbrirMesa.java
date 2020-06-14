package com.example.meucardapio;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import me.dm7.barcodescanner.core.CameraUtils;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.meucardapio.model.AddMesa;
import com.example.meucardapio.model.UsuarioLogado;
import com.example.meucardapio.service.CodeStatus;
import com.example.meucardapio.service.HtppServiceAddMesa;
import com.google.zxing.Result;

import java.util.concurrent.ExecutionException;

import static android.app.PendingIntent.getActivity;
import static com.example.meucardapio.R.*;
import static com.example.meucardapio.R.drawable.bordaqrcode;

public class MainActivityAbrirMesa extends AppCompatActivity implements ZXingScannerView.ResultHandler {


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
        Log.i(TAG, getClasseName() + "------> ON CREATE <--------- ");
        rl = (RelativeLayout) findViewById(id.relative_scan);
        mScannerView = new ZXingScannerView(this);


    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, getClasseName() + "------> ON  RESUME <--------- ");
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
        Log.i(TAG, getClasseName() + "------> ON PAUSE <--------- ");
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
        Log.i(TAG, getClasseName() + "------> ON STOP <--------- ");
        mScannerView.stopCamera();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i(TAG, getClasseName() + "------> ON RESTART <--------- ");
        mScannerView.startCamera();          // Start camera on resume

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getClasseName() + "------> ON DESTROY <--------- ");
        mScannerView.stopCameraPreview(); //stopPreview
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {

        if (result.getText() != null) {

            abrirMesa.setQr_code(result.getText());
            abrirMesa.setId_cliente(getUsuarioLogado());
            abrirMesa.setId_funcionario(1);
            abrirMesa.setTp_pagamento(1);


            try {
                CodeStatus retornoAbrirMesa = new HtppServiceAddMesa(abrirMesa).execute().get();
                //TOAST RETORNO USUÁRIO
                Toast toast = Toast.makeText(getApplicationContext(), retornoAbrirMesa.status, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                salvarIdComanda(retornoAbrirMesa.getId_comanda());
                salvarMesaUsuario(retornoAbrirMesa.getMesa_cliente());

                //SCANNER
                mScannerView.startCamera();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScannerView.resumeCameraPreview(MainActivityAbrirMesa.this);
                    }
                }, 2000);
            } catch (ExecutionException e) {
                e.printStackTrace();
                Log.i(TAG, getClasseName() + "------> ON DESTROY <--------- " + e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Não foi possível realizar operação, tente novamente.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

    }


    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }

    private int getUsuarioLogado() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        int idUsuarioLogado = sharedPreferences.getInt("idUsuarioLogado", 0);
        return idUsuarioLogado;

    }

    private void salvarIdComanda(int idComandaUsuario) {

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idComandaUsuario", idComandaUsuario);
        editor.apply();
    }
    private void salvarMesaUsuario(int idMesaUsuario){

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idMesaUsuario", idMesaUsuario);
        editor.apply();


    }


}
