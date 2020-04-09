package com.example.buscarcep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.buscarcep.model.Login;
import com.example.buscarcep.service.HttpServiceLogin;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    //Capturar a informação do cep digitado
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClasseName() + "CHEGOU AQUI--------");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_login);
        //final EditText cep = findViewById(R.id.etMain_cep);
        final EditText login = findViewById(R.id.username);
        final EditText senha = findViewById(R.id.password);

        Log.i(TAG, getClasseName() + "CHEGOU AQUI--------" + login);
        Log.i(TAG, getClasseName() + "CHEGOU AQUI--------" + senha);

        //Escrever resposta na tela
        final TextView resposta = findViewById(R.id.etMain_resposta);
        //Criação de Objeto: Botão, e armazenamento.
        Button btnLogin = findViewById(R.id.login);
        //Criação de metodo para escutar o click do botão
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String retorno = new HttpServiceLogin(login.getText().toString(),senha.getText().toString()).execute().get();
                    Log.i(TAG, getClasseName() + "CHEGOU AQUI--------" + retorno.toString());
                    resposta.setText(retorno.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
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
