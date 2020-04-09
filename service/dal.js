class DataAcessLayer {

    constructor() {
        const express = require('express');
        const bodyParser = require('body-parser');
        const mysql = require('mysql');
        const cors = require('cors');
        const https = require('https');
        const fs = require('fs');
        var path = require('path');

    }

    insertCliente(req){

        const cd_login = 'teste_01'
        const cd_login_request =  req.param('cd_login');
        console.log(cd_login_request)
        const cd_senha = 'senha01'
        const cd_senha_request = req.param("cd_senha");
        console.log(cd_senha_request)
        const sn_ativo_login = 1;
        const tp_login = 'cliente'

        var query = 'INSERT INTO LOGIN VALUES ('
            + 'NULL,'
            + "'" + cd_login + "'" + ','
            + "'" + cd_senha + "'" + ','
            + sn_ativo_login + ','
            + "'" + tp_login + "'" + ')'

        console.log("Query" + query);

        return query;

    }







}

module.exports = DataAcessLayer;