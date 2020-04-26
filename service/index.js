//IMPORTAÇÃO DE BIBLIOTECAS EXPRESS(API),MYSQL(DATABASE),BODYPARSER(JSON)
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const fs = require("fs");
const http = require('http');







//APP INICIANDO O EXPRESS
const app = express();
//CONEXÃO COM O BANCO - IMPORTAÇÃO DA CLASSE DE CONEXÃO
var pool = require(__dirname + '/database.js');
//BIBLIOTECA PARA TRABALHAR COM UPLOAD DE IMAGENS
var upload = require(__dirname + '/config_multer');
//CONVERSOR BLOB TO IMAGEM
const Image_Converter = require(__dirname + '/image_converter');

//CLASSE QUE REALIZA AS TRANSAÇÕES COM O BANCO
var DataAcessLayer = require((__dirname) + "/dal.js");
var dal = new DataAcessLayer();

//Verificar se o Objeto está vazio
function isEmptyObject(obj) {
    return !Object.keys(obj).length;
}

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

app.post('/addCliente', function (req, res) {
    //Abrindo Conexão com o Banco de Dados (Objeto Pool Importado da Classe DataBase)
    pool.getConnection(function (err, pool) {
        const objeto_dal = new DataAcessLayer();
        try {
            async function realizarCadastro() {
                await objeto_dal.insertLoginTransaction(req, pool).then(resultado => {
                    res.send(resultado);
                });
            }
            realizarCadastro();

        } catch (err) {
            var error_status = JSON.parse('{"status":"Não foi possível realizar sua operação, entre em contato com o administrador.","code_status":"00"}');
            res.send(error_status);

        }
    });
});


app.get('/realizarLogin/:login&:password', function (req, res) {

    pool.getConnection(function (err, pool) {
        //variavel para filtro
        let filter_login = '';
        let filter_password = '';
        //validação se o id não está nulo
        if (req.params.login) filter_login = "'" + (req.params.login) + "'";
        console.log(filter_login);
        if (req.params.password) filter_password = 'AND CD_SENHA =' + "'" + (req.params.password) + "'";
        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        pool.query('SELECT CD_LOGIN,CD_SENHA FROM LOGIN WHERE CD_LOGIN = ' + filter_login + filter_password, function (error, results, fields) {
            // Pegando a 'resposta' do servidor pra nossa requisição. Ou seja, aqui ele vai mandar nossos dados.

            console.log(results);
            if (isEmptyObject(results)) {

                res.json({ status: "Usuario ou Senha Invalido", code_status: 00 });
                pool.release();
                pool.destroy();
            } else {
                res.json({ status: "Login Realizado com Sucesso", code_status: 01 });
                pool.release();
                pool.destroy();
            }


        });
    });

})

app.post('/addProduto/', upload.single('IMG'), (req, res) => {
    //Abrindo Conexão com o Banco de Dados (Objeto Pool Importado da Classe DataBase)
    pool.getConnection(function (err, pool) {

        var data = new Date();
        data = (req.param("VALIDADE"));
        console.log(data);

        var produto = {

            NOME_PRODUTO: req.param("NOME_PRODUTO"),
            ID_FORNECEDOR: req.param("ID_FORNECEDOR"),
            VALOR: req.param("VALOR"),
            DESCRICAO: req.param("DESCRICAO"),
            CODIGO_BARRA: req.param("CODIGO_BARRA"),
            TIPO: req.param("TIPO"),
            VALIDADE: data,
            IMAGEM_PATH: './' + req.param("NOME_PRODUTO") + '.jpg'


        }
        
        const objeto_dal = new DataAcessLayer();
        try {
            async function uploadFoto() {
                await objeto_dal.insertProduto(req, pool, produto).then(resultado => {
                    fs.readFileSync(req.file.path)
                    res.send(resultado);
                });
            }
            uploadFoto();

        } catch (err) {
            var error_status = JSON.parse('{"status":"Não foi possível realizar sua operação, entre em contato com o administrador.","code_status":"00"}');
            res.send(error_status);

        }
    });
});


app.post('/listarProdutoId/', (req, res) => {

    pool.getConnection((err, pool) => {
        var id = req.param("id")
        var query = 'SELECT NOME_PRODUTO,VALOR,DESCRICAO,IMAGEM FROM PRODUTOS WHERE ID_PRODUTO =' + id;
        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }
            async function retrieveFoto() {
                await Image_Converter(results[0].IMAGEM, results[0].NOME_PRODUTO + '.jpg').then(resolve => {
                    var objeto_json = {


                    }

                    res.sendFile(__dirname + '/my-uploads/' + results[0].NOME_PRODUTO + '.jpg', 'Sucesso');

                });
            }

            //Executando função assincrona.
            retrieveFoto();


        })
    })



})

app.post('/listarTodosProdutos/', (req, res) => {

    pool.getConnection((err, pool) => {
        var id = req.param("id")
        var query = 'SELECT NOME_PRODUTO,VALOR,DESCRICAO,IMAGEM FROM PRODUTOS WHERE ID_PRODUTO =' + id;
        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }
            async function retrieveFoto() {
                await Image_Converter(results[0].IMAGEM, results[0].NOME_PRODUTO + '.jpg').then(buf => {
                    console.log(results.length);
                    
                    var objeto_json = {
                        NOME_PRODUTO: results[0].NOME_PRODUTO,
                        VALOR: results[0].VALOR,
                        DESCRICAO: results[0].DESCRICAO,
                        IMAGEM: buf.toString('base64')
                    }

                    //console.log(buf.toString('base64'));
                    

                    res.send(objeto_json);

                });
            }

            //Executando função assincrona.
            retrieveFoto();


        })
    })



})

app.post('/listarTodosTeste/', (req, res) => {

    pool.getConnection((err, pool) => {
        var id = req.param("id")
        var query = 'SELECT NOME_PRODUTO,VALOR,DESCRICAO,IMAGEM FROM PRODUTOS';
        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }
            async function retrieveFoto() {
                await Image_Converter(results[0].IMAGEM, results[0].NOME_PRODUTO + '.jpg').then(buf => {
                    console.log(results.length);                    
                    var objeto_json = {
                        NOME_PRODUTO: results[0].NOME_PRODUTO,
                        VALOR: results[0].VALOR,
                        DESCRICAO: results[0].DESCRICAO,
                        IMAGEM: buf.toString('base64')
                    }

                    //console.log(buf.toString('base64'));
                    

                    res.send(objeto_json);

                });
            }

            //Executando função assincrona.
            retrieveFoto();


        })
    })



})


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
