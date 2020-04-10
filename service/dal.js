class DataAcessLayer {

    constructor() {
        const express = require('express');
        const bodyParser = require('body-parser');
        const mysql = require('mysql');
        const cors = require('cors');
        const https = require('https');
        const fs = require('fs');
        var path = require('path');

    };



    insertCliente(req, connection) {



        let cd_login_request = req.param("cd_login");
        console.log(cd_login_request)
        let cd_senha_request = req.param("cd_senha");
        console.log(cd_senha_request)
        let sn_ativo_login = 1;
        let tp_login = 'cliente'
        var resultado_sql = '';

        var query = 'INSERT INTO LOGIN VALUES ('
            + 'NULL,'
            + "'" + cd_login_request + "'" + ','
            + "'" + cd_senha_request + "'" + ','
            + sn_ativo_login + ','
            + "'" + tp_login + "'" + ')'

        console.log("Query ----> " + query);

        try {

            const retorno_sql = connection.query(query, function (err, results, fields) {
                if (!err) {

                    console.log("Chegou aqui")
                    console.log("Usuário Inserido com Sucesso");
                    console.log(results);
                    return results;
                } else if(err.code === 'ER_DUP_ENTRY'){
                    console.log('Já existe um usuário com esse ID');
                }

                
            });

            

        } catch (err) {


          console.log("Errrrrrrouuuuur");

        }



    }







}

module.exports = DataAcessLayer;