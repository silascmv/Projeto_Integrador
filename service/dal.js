class DataAcessLayer {

    constructor() {
        const express = require('express');
        const bodyParser = require('body-parser');
        const mysql = require('mysql');
        const cors = require('cors');

    };



    insertLogin(req, res, pool) {


        let cd_login_request = req.param("cd_login");
        let cd_senha_request = req.param("cd_senha");
        let sn_ativo_login = 1;
        let tp_login = 'cliente'

        var query = 'INSERT INTO LOGIN VALUES ('
            + 'NULL,'
            + "'" + cd_login_request + "'" + ','
            + "'" + cd_senha_request + "'" + ','
            + sn_ativo_login + ','
            + "'" + tp_login + "'" + ')'

        return new Promise((resolve, reject) => {
            pool.query(query, res, function (err, results, fields) {
                if (!err) {
                    var resultado = JSON.parse('{"Resultado":"Login Criado com Sucesso","Code_Status":"01"}');
                    resolve(results.insertId);

                } else if (err.code === 'ER_DUP_ENTRY') {
                    var resultado = JSON.parse('{"Resultado":"Já existe um usuário com esse ID","Code_Status":"02"}');
                    resolve(resultado);

                } else {
                    reject(err);

                }
            });


        })





    }

    insertCliente(req, res, pool, id_login) {

        let nome = req.param("nome");
        console.log("NOMEEEE ------->>>>>>" + nome);
        let email = req.param("email");
        let endereco = req.param("endereco");
        let sn_ativo = 1;
        let telefone = req.param("telefone");
        let celular = req.param("celular");

        var query = 'INSERT INTO CLIENTE VALUES ('
            + 'NULL,'
            + "'" + id_login + "'" + ","
            + "'" + nome + "'" + ','
            + "'" + email + "'" + ','
            + "'" + endereco + "'" + ','
            + sn_ativo + ','
            + "'" + telefone + "'" + ','
            + "'" + celular + "'" + ')'

            console.log('QUERRRYYYYYYYY----->' + query);

        return new Promise((resolve, reject) => {
            pool.query(query, res, function (err, results, fields) {
                if (!err) {
                    var resultado_cliente = JSON.parse('{"status":"Cliente Cadastrado com Sucesso","code_status":"01"}');
                    resolve(resultado_cliente);

                } else if (err.code === 'ER_DUP_ENTRY') {
                    var resultado_cliente = JSON.parse('{"status":"Já existe um Cliente com esse E-mail Cadastrado","code_status":"02"}');
                    resolve(resultado_cliente);
                } else {
                    reject(err);

                }
            });


        })





    }


}

module.exports = DataAcessLayer;