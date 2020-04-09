/* eslint-disable handle-callback-err */
/* eslint-disable prettier/prettier */
// Isso aqui é no arquivo routes.js!
//IMPORTAÇÃO DE BIBLIOTECAS EXPRESS(API),MYSQL(DATABASE),BODYPARSER(JSON)
const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');
const cors = require('cors');
const https = require('https');
const fs = require('fs');
var path = require('path');
var DataAcessLayer = require((__dirname)+"/dal.js");
var dal = new DataAcessLayer();


//Verificar se o Objeto está vazio
function isEmptyObject(obj) {
    return !Object.keys(obj).length;
}

//CONEXÃO COM O BANCO
const connection = mysql.createPool({
    host: 'remotemysql.com',
    user: 'wIp1MpiKOt',
    password: 'pyiEqBNdgy',
    database: 'wIp1MpiKOt',
});
//APP INICIANDO O EXPRESS
const app = express();
//PORTA NO QUAL A API IRA EXECUTAR
const port = 3000; //porta padrão

//configurando o body parser para pegar POSTS mais tarde
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cors());
//Definindo Rotas
const router = express.Router();

//Criação de API'S
//Acessando HOME e Informando que o serviço está ON
router.get('/', (req, res) => res.json({ message: 'API Funcionando!' }));
app.use('/', router);


app.get('/inserirclienteteste', function (req, res) {
    // Conectando ao banco para usar na API.
    connection.getConnection(function (err, connection) {

        //Função para Inserir Clientes
        var retorno = dal.insertCliente(req);

        console.log(retorno);

        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        connection.query(query, function (error, results, fields) {

            console.log("Executou query");

        });
    });
});


// -------------SEÇÃO DE API PARA CLIENTE-----------------
//CONSULTAR TODOS OS CLIENTE
app.get('/consultaTodosClientes', function (req, res) {
    // Conectando ao banco para usar na API.
    connection.getConnection(function (err, connection) {

        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        connection.query('SELECT * FROM CLIENTE', function (error, results, fields) {
            // Caso ocorra algum erro, não irá executar corretamente.if (error) throw error;
            if (isEmptyObject(results)) {

                res.json({ Status: "Não existe nenhum cliente cadastrado", Code_Status: 00 });
                connection.destroy();
            } else {
                // Pegando a 'resposta' do servidor pra nossa requisição. Ou seja, aqui ele vai mandar nossos dados.
                res.send(results)
                console.log('Consulta de todos os clientes Realizada com Sucesso');
                connection.destroy()
            }

        });
    });
});

//CONSULTANDO CLIENTE POR ID
app.get('/consultaClienteId/:id', function (req, res) {
    // Conectando ao banco para usar na API.
    connection.getConnection(function (err, connection) {
        //variavel para filtro
        let filter = '';
        //validação se o id não está nulo
        if (req.params.id) filter = parseInt(req.params.id);
        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        connection.query('SELECT * FROM CLIENTE WHERE ID_CLIENTE = ' + filter, function (error, results, fields) {
            // Pegando a 'resposta' do servidor pra nossa requisição. Ou seja, aqui ele vai mandar nossos dados.
            if (isEmptyObject(results)) {

                res.json({ Status: "Informar um ID válido", Code_Status: 00 });
                connection.destroy();
            } else {
                res.send(results)
                console.log('Consulta por id Realizada com Sucesso');

            }
        });
    });
});


//EXCLUINDO CLIENTE POR ID
app.delete('/apagarClienteId/:id', function (req, res) {

    connection.getConnection(function (err, connection) {
        //variavel para filtro
        let filter = '';
        //validação se o id não está nulo
        if (req.params.id) filter = parseInt(req.params.id);
        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        connection.query('DELETE FROM CLIENTE WHERE ID_CLIENTE = ' + filter, function (error, results, fields) {
            // Pegando a 'resposta' do servidor pra nossa requisição. Ou seja, aqui ele vai mandar nossos dados.
            if (results["affectedRows"] < 1) {
                console.log('Não existe usuário para ser deletado');
                res.json({ Status: "Informar um ID válido", Code_Status: 00 });
                connection.destroy();
            } else {
                res.json({ Status: "Deleção Realizada com Sucesso", Code_Status: 01 })

            }
        });
    });

})

app.get('/realizarLogin/:login&:password', function (req, res) {

    connection.getConnection(function (err, connection) {
        //variavel para filtro
        let filter_login = '';
        let filter_password = '';
        //validação se o id não está nulo
        if (req.params.login) filter_login = "'" + (req.params.login) + "'";
        if (req.params.password) filter_password = 'AND CD_SENHA =' + "'" + (req.params.password) + "'";
        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        connection.query('SELECT CD_LOGIN,CD_SENHA FROM LOGIN WHERE CD_LOGIN = ' + filter_login + filter_password, function (error, results, fields) {
            // Pegando a 'resposta' do servidor pra nossa requisição. Ou seja, aqui ele vai mandar nossos dados.
            if (isEmptyObject(results)) {

                res.json({ Status: "Usuário ou Senha Inválido", Code_Status: 00 });
                connection.destroy();
            } else {
                res.json({ Status: "Login Realizado com Sucesso", Code_Status: 01 });
                connection.destroy();
            }


        });
    });

})


app.post('/addCliente/', (req, res) => {

    connection.getConnection(function (err, connection) {

        async function insertCliente(req) {

            try {

            var cd_login = req.param('cd_login');
            console.log(cd_login);
            var cd_senha = req.param('cd_senha');
            console.log(cd_senha);
            var sn_ativo_login = 1;
            var tp_login = 'cliente'

            var query = 'INSERT INTO LOGIN VALUES ('
                + 'NULL,'
                + "'" + cd_login + "'" + ','
                + "'" + cd_senha + "'" + ','
                + sn_ativo_login + ','
                + "'" + tp_login + "'" + ')'


                connection.query(query, function (error, results, fields) {
                    console.log(results);
                    if (error);
                    return results;
                
                });

                console.log("Fim do TRY");
                
            } catch (error) {

                throw new Error(err);
                
            }

            

        }

        insertCliente(req,(error,  res) => {

            if(error) res.send(error);
            res.json(res);

        });


        

        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        /*connection.query(query, function (error, results, fields) {
            
            console.log("Executou query");
            console.log(results);
            console.log(fields);
            res.send(results);
            connection.destroy();

        });*/
    });


    //criar função para pegar o login;
    //    const id_login;
    //const nome = req.body.nome.substring(0, 150);
    //const sn_ativo_cliente = 1;
    //const telefone = req.body.telefone.substring(0, 11);
    //const celular = req.body.celular.substring(0, 11);

});


//CONFIGURAÇÃO PARA HTTPS,VÁRIAVIES PARA CERTIFICADO.
//var key = fs.readFileSync(path.resolve('./service/key.pem'));
//var cert = fs.readFileSync(path.resolve('./service/cert.pem'));
/*https.createServer({
    key: fs.readFileSync(__dirname + '/key.pem'),
    cert: fs.readFileSync(__dirname + '/cert.pem'),
    passphrase: 'keypem'
  }, app)
  .listen(8080, function () {
    console.log('Example app listening on port 8080! Go to https://localhost:8080/')
  }) */

app.listen(8080, () => {
    console.log('Service is UP - LocalHost:8080');
});
