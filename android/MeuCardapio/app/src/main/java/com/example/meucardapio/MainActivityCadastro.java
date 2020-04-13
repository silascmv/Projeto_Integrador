
package com.example.meucardapio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meucardapio.model.Cadastro;
import com.example.meucardapio.model.RetrofitClient;
import com.example.meucardapio.service.Api;
import com.example.meucardapio.service.CodeStatus;
import com.example.meucardapio.service.HttpServiceCadastro;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivityCadastro extends AppCompatActivity {

    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cadastro);


        //Variavies para pegar valores da tela
        final EditText login = findViewById(R.id.inputUsuario);
        Log.i(TAG, getClasseName() + "CHEGOU AQUI " + login.toString());
        final EditText senha = findViewById(R.id.inputSenha);
        final EditText senha2 = findViewById(R.id.inputSenha2);
        final EditText nome = findViewById(R.id.inputNome);
        final EditText email = findViewById(R.id.inputEmail);
        final EditText endereco = findViewById(R.id.inputEndereco);
        final EditText telefone = findViewById(R.id.inputTelefone);
        final EditText celular = findViewById(R.id.inputCelular);
        //Botão
        final Button btnCadastrar = findViewById(R.id.btnCadastrar);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Variavel para verificar Checkbox Quando clicar
                CheckBox checkBox = findViewById(R.id.checkBox);

                if (login.getText().toString().matches("") || senha.getText().toString().matches("") || senha2.getText().toString().matches("")
                        || senha2.getText().toString().matches("") || nome.getText().toString().matches("") || email.getText().toString().matches("")
                        || endereco.getText().toString().matches("") || celular.getText().toString().matches("") || !checkBox.isChecked()) {
                    Toast.makeText(getApplicationContext(), "É necessário preencher todos os campos obrigatórios", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    Cadastro cadastro_usuario = new Cadastro();
                    cadastro_usuario.setCd_login(login.getText().toString());
                    cadastro_usuario.setCd_senha(senha.getText().toString());
                    cadastro_usuario.setNome(nome.getText().toString());
                    cadastro_usuario.setEmail(email.getText().toString());
                    cadastro_usuario.setEndereco(endereco.getText().toString());
                    cadastro_usuario.setTelefone(telefone.getText().toString());
                    cadastro_usuario.setCelular(celular.getText().toString());
                    //
                    Log.i(TAG, getClasseName() + "CHEGOU AQUI " + cadastro_usuario.toString());
                    Gson gson = new Gson();
                    String jsonconvertido = gson.toJson(cadastro_usuario);
                    Log.i(TAG, getClasseName() + "JSON CONVERTION DO OBJETO " + jsonconvertido);
                    //
                    try {
                        CodeStatus cadastro = new HttpServiceCadastro(cadastro_usuario).execute().get();

                        /*if(cadastro.getCode_status() == 1){
                            Toast.makeText(getApplicationContext(),"Cadastro Realizado com Sucesso!", Toast.LENGTH_SHORT).show();

                        }else if(cadastro.getCode_status() == 2){
                            Toast.makeText(getApplicationContext(),"Já existe outro usuário com esse Login, tente novamente!", Toast.LENGTH_SHORT).show();

                        }else if(cadastro.getCode_status() == 0){
                            Toast.makeText(getApplicationContext(),"Falha ao Realizar a Operação.!", Toast.LENGTH_SHORT).show();

                        }*/





                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }


            }
        });
        //Criação de Objeto para armazenar Valores
        //Mudar para construtor na classe


    }


    private String getClasseName() {
        //retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
