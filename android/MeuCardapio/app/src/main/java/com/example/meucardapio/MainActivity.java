package com.example.meucardapio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meucardapio.service.CodeStatus;
import com.example.meucardapio.service.HttpServiceLogin;
import com.example.meucardapio.model.Login;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        //Pegar os Valores da Tela
        final EditText login = findViewById(R.id.username);
        final EditText senha = findViewById(R.id.password);
        Log.i(TAG, getClasseName() + "CHEGOU AQUI " + senha);
        final TextView cadastrarUsuario = findViewById(R.id.lblCadastrar);

        //Criação de Objeto btnLogin para caputar clique da tela:
        Button btnLogin = findViewById(R.id.login);
        //Criação de metodo para escutar o click do botão
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String verificaUsuario = login.getText().toString();
                    String verificaSenha = senha.getText().toString();
                    if(verificaUsuario.matches("") || verificaSenha.matches((""))){
                        Toast.makeText(getApplicationContext(),"Usuário e senha são campos obrigatórios", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    CodeStatus retorno;
                    retorno = new HttpServiceLogin(login.getText().toString(),senha.getText().toString()).execute().get();
                    if(retorno.getCode_status() == 0){
                        Toast.makeText(getApplicationContext(),"Usuário ou Senha Inválido", Toast.LENGTH_SHORT).show();

                    }else{
                        Intent intent_tela_princiapl = new Intent(getApplicationContext(), MainActivityPrincipalUsuario.class );
                        startActivity(intent_tela_princiapl);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        cadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityCadastro.class );
                startActivity(intentTelaCadastro);

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
