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

app.post('/addCliente/', function (req, res) {
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

app.post('/addFuncionario/', function (req, res) {
    //Abrindo Conexão com o Banco de Dados (Objeto Pool Importado da Classe DataBase)
    pool.getConnection(function (err, pool) {
        const objeto_dal = new DataAcessLayer();
        try {
            async function realizarCadastro() {
                await objeto_dal.insertFuncionarioTransaction(req, pool).then(resultado => {
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


app.post('/addMesa/', upload.single('IMG'), (req, res) => {

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

app.post('/realizarLoginFuncionario/', (req, res) => {

    let cd_login = req.param("CD_LOGIN")
    let cd_senha = req.param("CD_SENHA")

    let query = 'SELECT FUNCIONARIO.ID_FUNCIONARIO,LOGIN.CD_LOGIN,LOGIN.CD_SENHA,LOGIN.TP_LOGIN FROM FUNCIONARIO JOIN LOGIN ON  FUNCIONARIO.ID_LOGIN = LOGIN.ID_LOGIN WHERE CD_LOGIN = ' + "'" + cd_login + "'" + ' ' + 'AND CD_SENHA=' + "'" + cd_senha + "'"
    console.log(query)
    pool.getConnection(function (err, pool) {

        pool.query(query, function (error, results, fields) {

            if (results.length == 0) {

                res.json({
                    status: "Usuario ou Senha Invalido",
                    code_status: 00
                });


            } else {

                res.json({
                    status: "Login Realizado com Sucesso",
                    code_status: 01,
                    usuario: results[0].ID_FUNCIONARIO,
                    tp_login: results[0].TP_LOGIN
                });

            }


        })

    })


})


app.get('/listarCardapioAndroid/', (req, res) => {
    pool.getConnection((err, pool) => {
        var query = 'SELECT ID_PRODUTO,NOME_PRODUTO,VALOR,DESCRICAO,IMAGEM_PATH FROM PRODUTOS';
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
                        'id_produto': results[i].ID_PRODUTO,
                        'imagem': 'http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io' + (results[i].IMAGEM_PATH),
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
                        'IMAGEM': 'http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io' + (results[i].IMAGEM_PATH),
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



app.get('/listarTodosFuncionarios/', (req, res) => {
    pool.getConnection((err, pool) => {
        var query = 'SELECT FUNCIONARIO.ID_FUNCIONARIO,FUNCIONARIO.NOME,FUNCIONARIO.CPF,FUNCIONARIO.TELEFONE,FUNCIONARIO.SETOR,LOGIN.TP_LOGIN,LOGIN.SN_ATIVO FROM FUNCIONARIO JOIN LOGIN ON FUNCIONARIO.ID_LOGIN = LOGIN.ID_LOGIN';
        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }

            if (results.length == 0) {
                res.json({
                    status: "Não existem funcionarios cadastrados",
                    code_status: 00
                });
            } else {

                var objeto_array = new Array();
                for (var i = 0; i < results.length; i++) {

                    var objeto_retorno = {
                        'ID_FUNCIONARIO': (results[i].ID_FUNCIONARIO),
                        'NOME': results[i].NOME,
                        'CPF': results[i].CPF,
                        'TELEFONE': results[i].TELEFONE,
                        'SETOR': results[i].SETOR,
                        'SN_ATIVO': results[i].SN_ATIVO,

                    };

                    objeto_array.push(objeto_retorno);

                }
            }
            res.json(objeto_array);

        })
    })

});


app.post('/desativarFuncionario/', (req, res) => {



    pool.getConnection(function (err, pool) {

        try {

            let id_funcionario = req.param("ID_FUNCIONARIO")

            let query = 'UPDATE LOGIN JOIN FUNCIONARIO  ON LOGIN.ID_LOGIN = FUNCIONARIO.ID_LOGIN SET LOGIN.SN_ATIVO = 0 WHERE FUNCIONARIO.ID_FUNCIONARIO =' + id_funcionario

            console.log(query)

            pool.query(query, function (error, results, fields) {

                if (!error) {
                    res.json({
                        status: "Funcionário desativado com sucesso",
                        code_status: 01

                    })
                } else {

                    console.log(error)
                    res.json({
                        status: "Não foi possível realizar operação,entre em contato com o administrador",
                        code_status: 00

                    })


                }


            })

        } catch (err) {
            var error_status = JSON.parse('{"status":"Não foi possível realizar sua operação, entre em contato com o administrador.","code_status":"00"}');
            console.log(err);
            res.send(error_status);

        }
    });





});


app.post('/abrirMesa/', (req, res) => {



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

                    } else if (resultado.code_status === '03') {

                        res.send(resultado).end();

                    } else if (resultado.code_status === '02') {

                        res.send(resultado).end();

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
                        'IMAGEM_MESA': 'http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io' + results[i].PATH_QR_CODE,
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

app.post('/realizarPedidoAndroid/', (req, res) => {


    pool.getConnection(function (err, pool) {

        const objeto_dal = new DataAcessLayer();

        async function realizarPedido() {
            await objeto_dal.realizadrPedidoAndroid(req, pool).then(resultado => {
                res.send(resultado);
            });
        }

        realizarPedido();

    });




})

app.post('/consultarConta/', (req, res) => {
    pool.getConnection((err, pool) => {

        var id_comanda = req.param("ID_COMANDA")

        var query = 'SELECT  MESAS.DESCRICAO,PRODUTOS.NOME_PRODUTO,COUNT(PRODUTOS.NOME_PRODUTO) AS QUANTIDADE,SUM(PRODUTOS.VALOR) AS ' + "'VALOR_TOTAL'" + 'FROM COMANDA_PRODUTO JOIN PRODUTOS ON PRODUTOS.ID_PRODUTO = ID_PRODUTO_FK JOIN COMANDA  ON COMANDA.ID_COMANDA = COMANDA_PRODUTO.ID_COMANDA_FK JOIN MESAS ON COMANDA.ID_MESA = MESAS.ID_MESA JOIN CLIENTE ON COMANDA.ID_CLIENTE = CLIENTE.ID_CLIENTE WHERE COMANDA_PRODUTO.ID_COMANDA_FK = ' + id_comanda + ' GROUP BY PRODUTOS.NOME_PRODUTO'
        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }

            if (results.length == 0) {
                res.json({
                    status: "Realiza seus pedidos através do Cárdapio",
                    code_status: 00
                });
            } else {

                var objeto_array = new Array();
                for (var i = 0; i < results.length; i++) {

                    var objeto_retorno = {
                        'descricao': results[i].DESCRICAO,
                        'nome_produto': results[i].NOME_PRODUTO,
                        'quantidade': results[i].QUANTIDADE,
                        'valor_total': results[i].VALOR_TOTAL,                      

                    }

                    objeto_array.push(objeto_retorno);

                }
            }
            res.json(objeto_array);

        })

    })

})

app.get('/consultarContaWeb/', (req, res) => {
    pool.getConnection((err, pool) => {

        var query = 'SELECT MESAS.ID_MESA,COMANDA.ID_COMANDA,COMANDA_PRODUTO.STATUS_PRODUTO,COMANDA.STATUS_COMANDA,MESAS.DESCRICAO,CLIENTE.NOME, COMANDA.DT_HR_INICIO_COMANDA, FUNCIONARIO.NOME AS FUNCIONARIO, COMANDA.OBSERVACAO,PRODUTOS.NOME_PRODUTO,COUNT(PRODUTOS.NOME_PRODUTO) AS QUANTIDADE,SUM(PRODUTOS.VALOR) AS ' + "'VALOR_TOTAL'" + 'FROM COMANDA_PRODUTO JOIN PRODUTOS ON PRODUTOS.ID_PRODUTO = ID_PRODUTO_FK JOIN COMANDA  ON COMANDA.ID_COMANDA = COMANDA_PRODUTO.ID_COMANDA_FK JOIN MESAS ON COMANDA.ID_MESA = MESAS.ID_MESA JOIN CLIENTE ON COMANDA.ID_CLIENTE = CLIENTE.ID_CLIENTE JOIN FUNCIONARIO ON COMANDA.ID_FUNCIONARIO = FUNCIONARIO.ID_FUNCIONARIO WHERE SN_PAGO = 0 GROUP BY MESAS.ID_MESA,COMANDA.ID_COMANDA,COMANDA_PRODUTO.STATUS_PRODUTO,COMANDA.STATUS_COMANDA,MESAS.DESCRICAO,CLIENTE.NOME, COMANDA.DT_HR_INICIO_COMANDA, FUNCIONARIO.NOME, COMANDA.OBSERVACAO,PRODUTOS.NOME_PRODUTO'
		
		pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }

            if (results.length == 0) {
                res.json({
                    status: "Realiza seus pedidos através do Cárdapio",
                    code_status: 00
                });
            } else {

                var objeto_array = new Array();
                for (var i = 0; i < results.length; i++) {

                    var objeto_retorno = {
                        'id_mesa': results[i].ID_MESA,
						'id_comanda': results[i].ID_COMANDA,
						'status_produto': results[i].STATUS_PRODUTO,
						'status_comanda': results[i].STATUS_COMANDA,
						'descricao': results[i].DESCRICAO,
						'nome': results[i].NOME_CLIENTE,
						'data_hr_inicio_comanda': results[i].DT_HR_INICIO_COMANDA,
						'funcionarios': results[i].FUNCIONARIO,
						'observacao': results[i].OBSERVACAO,
                        'nome_produto': results[i].NOME_PRODUTO,
                        'quantidade': results[i].QUANTIDADE,
                        'valor_total': results[i].VALOR_TOTAL,                      

                    }

                    objeto_array.push(objeto_retorno);

                }
            }
            res.json(objeto_array);

        })

    })

})






app.listen(8080, () => {
    console.log('Service is UP - LocalHost:8080');
});