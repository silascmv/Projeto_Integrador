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
        if (req.params.login) filter_login = "'"+ (req.params.login) + "'";
        if (req.params.password) filter_password = 'AND CD_SENHA =' + "'" +  (req.params.password) + "'";
        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        connection.query('SELECT CD_LOGIN,CD_SENHA FROM LOGIN WHERE CD_LOGIN = '+ filter_login + filter_password, function (error, results, fields) {
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


//CONFIGURAÇÃO PARA HTTPS,VÁRIAVIES PARA CERTIFICADO.
//var key = fs.readFileSync(path.resolve('./service/key.pem'));
//var cert = fs.readFileSync(path.resolve('./service/cert.pem'));

console.log("Chegou aqui")


https.createServer({
    key: fs.readFileSync(__dirname + '/key.pem'),
    cert: fs.readFileSync(__dirname + '/cert.pem'),
    passphrase: 'keypem'
  }, app)
  .listen(8080, function () {
    console.log('Example app listening on port 8080! Go to https://localhost:8080/')
  })
