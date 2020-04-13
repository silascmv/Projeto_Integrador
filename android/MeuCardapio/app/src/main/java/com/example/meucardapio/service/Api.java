package com.example.meucardapio.service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface Api {


@FormUrlEncoded
@POST("addCliente")
    Call<ResponseBody> addCliente(
            @Field("cd_login") String cd_login,
            @Field("nome") String nome,
            @Field("cd_senha") String cd_senha,
            @Field("email") String email,
            @Field("endereco") String endereco,
            @Field("telefone") String telefone,
            @Field("celular") String celular

);


}
