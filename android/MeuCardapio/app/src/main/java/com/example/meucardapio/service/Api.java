package com.example.meucardapio.service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.example.meucardapio.model.Cardapio;
import com.example.meucardapio.model.Conta;
import com.example.meucardapio.model.ItemPedido;
import com.example.meucardapio.model.StatusCliente;
import com.example.meucardapio.service.CodeStatus;

import java.util.List;


public interface Api {


    @FormUrlEncoded
    @POST("addCliente")
    Call<CodeStatus> addCliente(
            @Field("cd_login") String cd_login,
            @Field("nome") String nome,
            @Field("cd_senha") String cd_senha,
            @Field("email") String email,
            @Field("endereco") String endereco,
            @Field("telefone") String telefone,
            @Field("celular") String celular

    );

    @FormUrlEncoded
    @POST("abrirMesa")
    Call<CodeStatus> addMesa(
            @Field("QR_CODE") String qr_cde,
            @Field("ID_CLIENTE") Integer id_cliente,
            @Field("ID_FUNCIONARIO") Integer id_funcionario,
            @Field("TP_PAGAMENTO") Integer tp_pagamento

    );


    @FormUrlEncoded
    @POST("realizarPagamentoAndroid")
    Call<CodeStatus> realizarPagamentoAndroid(
            @Field("VALOR_TOTAL") double valorTotalPagamento,
            @Field("TP_PAGAMENTO") double tpPagamento,
            @Field("ID_COMANDA") int idComanda

    );


    @FormUrlEncoded
    @POST("consultarConta")
    Call<List<Conta>> consultarConta(
            @Field("ID_COMANDA")int idComanda);





  /*  @FormUrlEncoded
    @POST("realizarPedidoAndroid")
    Call<CodeStatus> realizarPedido(

            @Field("realizarPedidoAndroid") String realizarPedidoAndroid,
            @Field("idComanda") String idComanda,
            @Field("nomeProduto") String nomeProduto,
            @Field("preco") String preco,
            @Field("quantidade") String quantidade
    );
 */

    @POST("realizarPedidoAndroid")
    Call<CodeStatus> realizarPedido(@Body List<ItemPedido> itemPedido);

    @GET("listarCardapioAndroid")
    Call<List<Cardapio>> getCardapio();

    @GET("verificarStatusMesaUsuario")
    Call<StatusCliente> verificarStatusMesaUsuario(@Field("id_cliente") int id_cliente);

}
