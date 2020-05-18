//IMPORTAÇÃO DE BIBLIOTECAS EXPRESS(API),MYSQL(DATABASE),BODYPARSER(JSON)
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const fs = require("fs");
const http = require('http');
const {
    promisify
} = require('util');
var session = require('express-session');
const publicIp = require('public-ip');

var externalip = require('externalip');

//NOME_HOST_API


//APP INICIANDO O EXPRESS
const app = express();
//CONEXÃO COM O BANCO - IMPORTAÇÃO DA CLASSE DE CONEXÃO
var pool = require(__dirname + '/database.js');
//BIBLIOTECA PARA TRABALHAR COM UPLOAD DE IMAGENS
var upload = require(__dirname + '/config_multer');
//CONVERSOR BLOB TO IMAGEM
//const Image_Converter = require(__dirname + '/image_converter');
//APAGAR ARQUIVOS
const unlinkAsync = promisify(fs.unlink)
//Serving Static Files
app.use('/my-uploads', express.static(__dirname + '/my-uploads'));

//
app.use(session({
    secret: 'secret',
    resave: false,
    saveUninitialized: true,
    cookie: {
        secure: true
    }
}))



//CLASSE QUE REALIZA AS TRANSAÇÕES COM O BANCO
var DataAcessLayer = require((__dirname) + "/dal.js");
var dal = new DataAcessLayer();

//Verificar se o Objeto está vazio
function isEmptyObject(obj) {
    return !Object.keys(obj).length;
}

//configurando o body parser para pegar POSTS mais tarde
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
app.use(cors());
//Definindo Rotas
const router = express.Router();
//Criação de API'S
//Acessando HOME e Informando que o serviço está ON
router.get('/', (req, res) => res.json({
    message: 'API Funcionando!'
}));
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

app.post('/addProduto/', upload.single('IMG'), (req, res) => {

    var data = new Date()
    //console.log('Caminho : ' + req.file.path);
    //variavel pra setar a hora na foto
    var data_atual = data.getDate();
    var mes_atual = data.getMonth() + 1;
    var ano_atual = data.getFullYear() + 1;
    var segundos = data.getSeconds();
    var mseg = data.getMilliseconds();
    let data_final = data_atual + '-' + mes_atual + '-' + ano_atual + '-' + segundos + '-' + mseg;
    //conversão de data pra armazenar no banco
    data = (req.param("VALIDADE"));
    if (req.file.filename === undefined) {

        console.log("ENTROU NO IF")
        req.file.filename = 'SemFoto'
    }

    var produto = {

        NOME_PRODUTO: req.param("NOME_PRODUTO"),
        ID_FORNECEDOR: req.param("ID_FORNECEDOR"),
        VALOR: req.param("VALOR"),
        DESCRICAO: req.param("DESCRICAO"),
        CODIGO_BARRA: req.param("CODIGO_BARRA"),
        TIPO: req.param("TIPO"),
        VALIDADE: data,
        IMAGEM_PATH: '/my-uploads/' + req.file.filename
    }

    pool.getConnection(function (err, pool) {
        const objeto_dal = new DataAcessLayer();
        try {
            async function uploadFoto() {
                await objeto_dal.insertProduto(req, pool, produto).then(resultado => {
                    if (resultado.code_status === '01') {
                        req.session.destroy(function (err) {
                            res.send(resultado).end();
                        })
                    } else if (resultado.code_status === '04') {
                        //Remover arquivo caso não seja cadastrado
                        unlinkAsync(req.file.destination + '/' + req.file.filename);
                        res.send(resultado).end();
                    } else if (resultado.code_status === '00') {
                        //Remover arquivo caso não seja cadastrado
                        unlinkAsync(req.file.destination + '/' + req.file.filename);
                        res.send(resultado).end();

                    } else {



                    }

                });
            }
            uploadFoto();

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
        var query = 'SELECT CLIENTE.ID_CLIENTE,LOGIN.CD_LOGIN,LOGIN.CD_SENHA FROM LOGIN JOIN CLIENTE ON CLIENTE.ID_LOGIN = LOGIN.ID_LOGIN WHERE CD_LOGIN = ' + filter_login + filter_password
        pool.query(query, function (error, results, fields) {
            // Pegando a 'resposta' do servidor pra nossa requisição. Ou seja, aqui ele vai mandar nossos dados.
            console.log(query);
            console.log(results);
            if (isEmptyObject(results)) {

                res.json({
                    status: "Usuario ou Senha Invalido",
                    code_status: 00
                });
                pool.release();
                pool.destroy();
            } else {
                res.json({
                    status: "Login Realizado com Sucesso",
                    code_status: 01,
                    usuario: results[0].ID_CLIENTE
                });
                pool.release();
                pool.destroy();
            }




        });
    });

});

app.get('/listarTodosProdutos/', (req, res) => {
    pool.getConnection((err, pool) => {
        var query = 'SELECT NOME_PRODUTO,VALOR,DESCRICAO,IMAGEM_PATH,CODIGO_BARRA,TIPO FROM PRODUTOS';
        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }

            if (results.length == 0) {
                res.json({
                    status: "Não existem produtos cadastrados",
                    code_status: 00
                });
            } else {

                var objeto_array = new Array();
                for (var i = 0; i < results.length; i++) {

                    var objeto_retorno = {
                        'IMAGEM': 'http://app-84c469d6-9c06-4181-9a74-5d84696798cf.cleverapps.io' + (results[i].IMAGEM_PATH),
                        'NOME': results[i].NOME_PRODUTO,
                        'VALOR': results[i].VALOR,
                        'DESCRICAO': results[i].DESCRICAO,
                        'CODIGO_BARRA': results[i].CODIGO_BARRA,
                        'TIPO': results[i].TIPO

                    }

                    objeto_array.push(objeto_retorno);

                }
            }
            res.json(objeto_array);

        })
    })

});

app.get('/listarCardapioAndroid/', (req, res) => {
    pool.getConnection((err, pool) => {
        var query = 'SELECT NOME_PRODUTO,VALOR,DESCRICAO,IMAGEM_PATH FROM PRODUTOS';
        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }

            if (results.length == 0) {
                res.json({
                    status: "Não existem produtos cadastrados",
                    code_status: 00
                });
            } else {

                var objeto_array = new Array();
                for (var i = 0; i < results.length; i++) {

                    var objeto_retorno = {
                        'imagem': 'http://app-84c469d6-9c06-4181-9a74-5d84696798cf.cleverapps.io' + (results[i].IMAGEM_PATH),
                        'nome': results[i].NOME_PRODUTO,
                        'valor': results[i].VALOR,
                        'descricao': results[i].DESCRICAO

                    }

                    objeto_array.push(objeto_retorno);

                }
            }
            res.json(objeto_array);

        })
    })

});

app.post('/abrirMesa', (req, res) => {



    pool.getConnection(function (err, pool) {
        const objeto_dal = new DataAcessLayer();
        try {
            async function abrirMesa() {
                await objeto_dal.abrirMesa(req, pool).then(resultado => {
                    if (resultado.code_status === '01') {
                        req.session.destroy(function (err) {
                            res.send(resultado).end();
                        })
                    } else if (resultado.code_status === '04') {
                        //Remover arquivo caso não vá cadastrar                        
                        res.send(resultado).end();
                    } else if (resultado.code_status === '00') {

                        res.send(resultado).end();

                    } else {



                    }

                });
            }
            abrirMesa();

        } catch (err) {
            var error_status = JSON.parse('{"status":"Não foi possível realizar sua operação, entre em contato com o administrador.","code_status":"00"}');
            console.log(err);
            res.send(error_status);

        }
    });










});

app.post('/addMesa', upload.single('IMG'), (req, res) => {

    var controle_disponibilidade = parseInt(req.param("SN_ATIVO"));


    if (!controle_disponibilidade === 1) {

        controle_disponibilidade = 0
    }

    var mesa = {

        DESCRICAO: req.param("DESCRICAO"),
        SN_ATIVO: req.param("SN_ATIVO"),
        QR_CODE: req.param("QR_CODE"),
        PATH_QR_CODE: '/my-uploads/' + req.file.filename,
        SN_DISPONIVEL: controle_disponibilidade
    }

    pool.getConnection(function (err, pool) {
        const objeto_dal = new DataAcessLayer();
        try {
            async function cadMesa() {
                await objeto_dal.cadastrarMesa(req, pool, mesa).then(resultado => {
                    if (resultado.code_status === '01') {
                        req.session.destroy(function (err) {
                            res.send(resultado).end();
                        })
                    } else if (resultado.code_status === '04') {
                        //Remover arquivo caso não seja cadastrado
                        unlinkAsync(req.file.destination + '/' + req.file.filename);
                        res.send(resultado).end();
                    } else if (resultado.code_status === '00') {
                        //Remover arquivo caso não seja cadastrado
                        unlinkAsync(req.file.destination + '/' + req.file.filename);
                        res.send(resultado).end();

                    } else {



                    }

                });
            }
            cadMesa();

        } catch (err) {
            var error_status = JSON.parse('{"status":"Não foi possível realizar sua operação, entre em contato com o administrador.","code_status":"00"}');
            res.send(error_status);

        }
    });
});

app.get('/listarTodasMesas/', (req, res) => {


    pool.getConnection((err, pool) => {

        let query = 'SELECT ID_MESA,DESCRICAO,SN_ATIVO,QR_CODE,PATH_QR_CODE,SN_DISPONIVEL FROM MESAS';



        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }

            if (results.length == 0) {
                res.json({
                    status: "Não existem mesas cadastrados",
                    code_status: 00
                });
            } else {

                var objeto_array = new Array();
                for (var i = 0; i < results.length; i++) {

                    var objeto_retorno = {
                        'ID_MESA': results[i].ID_MESA,
                        'DESCRICAO': results[i].DESCRICAO,
                        'SN_ATIVO': results[i].SN_ATIVO,
                        'QR_CODE': results[i].QR_CODE,
                        'IMAGEM_MESA': 'http://app-84c469d6-9c06-4181-9a74-5d84696798cf.cleverapps.io' + results[i].PATH_QR_CODE,
                        'SN_DISPONIVEL': results[i].SN_DISPONIVEL

                    }

                    objeto_array.push(objeto_retorno);

                }
            }
            res.json(objeto_array);

        })
    })

});

app.post('/apagarProduto/', (req, res) => {



    pool.getConnection((err, pool) => {

        var id_produto = req.param("ID_PRODUTO")

        let query = 'DELETE FROM PRODUTOS WHERE ID_PRODUTO = ' + id_produto;



        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }

            if (results.affectedRows == 0) {
                res.json({
                    status: "Não existe produto com esse ID",
                    code_status: 00
                });
            } else {

                res.json({
                    status: "Produto deletado com Sucesso",
                    code_status: 01
                })

            }


        })
    })






})


app.get('/listarIP', (req, res) => {

    var
        // Local ip address that we're trying to calculate
        address
        // Provides a few basic operating-system related utility functions (built-in)
        , os = require('os')
        // Network interfaces
        ,
        ifaces = os.networkInterfaces();


    // Iterate over interfaces ...
    for (var dev in ifaces) {

        // ... and find the one that matches the criteria
        var iface = ifaces[dev].filter(function (details) {
            return details.family === 'IPv4' && details.internal === false;
        });

        if (iface.length > 0) address = iface[0].address;
    }

    // Print the result
    console.log(address);


    res.send(address);

})

app.listen(8080, () => {
    console.log('Service is UP - LocalHost:8080');
});