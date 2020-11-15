package com.example.meucardapio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meucardapio.R;
import com.example.meucardapio.model.Preferencias;
import com.example.meucardapio.model.UsuarioLogado;
import com.example.meucardapio.service.CodeStatus;
import com.example.meucardapio.service.HttpServiceLogin;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Preferencias preferencias = new Preferencias(MainActivity.this);


    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView cadastrarUsuario = findViewById(R.id.lblCadastrar);
        Button btnLogin = findViewById(R.id.login);

        if (preferencias.snVerificaLoginSalvo() == true) {
            Intent intent_tela_principal = new Intent(getApplicationContext(), MainActivityPrincipal.class);
            startActivity(intent_tela_principal);

        } else {

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realizarLogin();
                }
            });

            cadastrarUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentTelaCadastro = new Intent(getApplicationContext(), MainActivityCadastro.class);
                    startActivity(intentTelaCadastro);

                }
            });


        }
    }


    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }

    private void realizarLogin() {

        final EditText login = findViewById(R.id.username);
        final EditText senha = findViewById(R.id.password);
        final Switch swSalvarUsuario = (Switch) findViewById(R.id.switch1);
        Log.i(TAG, getClasseName() + "CHEGOU AQUI " + senha);

        try {

            String verificaUsuario = login.getText().toString();
            String verificaSenha = senha.getText().toString();
            if (verificaUsuario.matches("") || verificaSenha.matches((""))) {
                Toast.makeText(getApplicationContext(), "Usuário e senha são campos obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            CodeStatus retorno;
            retorno = new HttpServiceLogin(login.getText().toString(), senha.getText().toString()).execute().get();
            if (retorno == null) {
                Toast.makeText(getApplicationContext(), "Falha ao realizar o login, tente novamente.", Toast.LENGTH_LONG).show();

            }


            if (retorno.getCode_status() == 0) {
                Toast.makeText(getApplicationContext(), "Usuário ou Senha Inválido", Toast.LENGTH_SHORT).show();

            } else {
                Intent intent_tela_princiapl = new Intent(getApplicationContext(), MainActivityPrincipal.class);
                UsuarioLogado usuarioLogado = new UsuarioLogado(login.getText().toString(), 0, retorno.getUsuarioLogado());
                intent_tela_princiapl.putExtra("usuarioLogado", (Parcelable) usuarioLogado);
                int idUsuarioLogado = retorno.getUsuarioLogado();

                if (swSalvarUsuario.isChecked() == true) {

                    preferencias.salvarLoginPreferencia(true);

                }

                preferencias.salvarIdUsuarioLogadoPreferencia(retorno.getUsuarioLogado());
                startActivity(intent_tela_princiapl);


            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }






}
