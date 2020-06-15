class DataAcessLayer {

    constructor() {
        const express = require('express');
        const bodyParser = require('body-parser');
        const mysql = require('mysql');
        const cors = require('cors');
    };

    insertLoginTransaction(req, pool) {
        // Parâmetros para TABELA-LOGIN
        let cd_login_request = req.param("cd_login");
        let cd_senha_request = req.param("cd_senha");
        let sn_ativo_login = 1;
        let tp_login = 'cliente'
        //Query para inserir na tabela Login
        let query = 'INSERT INTO LOGIN VALUES ('
            + 'NULL,'
            + "'" + cd_login_request + "'" + ','
            + "'" + cd_senha_request + "'" + ','
            + sn_ativo_login + ','
            + "'" + tp_login + "'" + ')'

        //Baseado no resultado da promessa irá mandar uma mensagem de retorno e será enviado como retorno da aplicação(Resolve).
        return new Promise((resolve, reject) => {

            //Iniciando Transação
            pool.beginTransaction(function (err) {
                if (err) {
                    throw err;
                }
                pool.query(query, function (error, results, fields) {
                    if (!error) {
                        //Retorno do MYSQL com o ÚLTIMO ID ADICIONADO NA TABELA LOGIN
                        var id_login = results.insertId;
                        // Variavéis do Request
                        let nome = req.param("nome");
                        let email = req.param("email");
                        let endereco = req.param("endereco");
                        let sn_ativo = 1;
                        let telefone = req.param("telefone");
                        let celular = req.param("celular");
                        //Query para inserir na tabela CLIENTE
                        query = 'INSERT INTO CLIENTE VALUES ('
                            + 'NULL,'
                            + "'" + id_login + "'" + ","
                            + "'" + nome + "'" + ','
                            + "'" + email + "'" + ','
                            + "'" + endereco + "'" + ','
                            + sn_ativo + ','
                            + "'" + telefone + "'" + ','
                            + "'" + celular + "'" + ')'

                        pool.query(query, function (error, results, fields) {
                            //Se a query de inserção não falhar irá realizar o commit da operação
                            if (!error) {
                                pool.commit(function (err) {

                                    //Finalização do cadastro.
                                    if (!err) {
                                        var resultado_cliente = JSON.parse('{"status":"Cliente Cadastrado com Sucesso","code_status":"01"}');
                                        resolve(resultado_cliente);

                                    } else {
                                        //Caso apresente algua falha no commit irá enviar essa informação.
                                        return pool.rollback(function () {
                                            var resultado_cliente = JSON.parse('{"status":"Falha ao realizar o cadastro tenta novamente","code_status":"00"}');
                                            resolve(resultado_cliente);
                                        });

                                    }
                                });

                            } else if (error.code === 'ER_DUP_ENTRY') {
                                //Captura de erros e retornos para api
                                return pool.rollback(function () {
                                    var resultado_cliente = JSON.parse('{"status":"Já existe um Cliente com esse E-mail Cadastrado","code_status":"02"}');
                                    resolve(resultado_cliente);
                                });
                            } else if (error.code === 'ER_DATA_TOO_LONG') {
                                //Captura de erros e retornos para api
                                var resultado = JSON.parse('{"status":"Coluna com o valor maior do que o permitido","code_status":"03"}');
                                resolve(resultado);
                            } else {
                                var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                                resolve(resultado);

                            }

                        });




                    } else if (error.code === 'ER_DUP_ENTRY') {
                        return pool.rollback(function () {
                            var resultado = JSON.parse('{"status":"Já existe um usuário com esse Login","code_status":"04"}');
                            resolve(resultado);
                        });
                    } else {
                        var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                        resolve(resultado);

                    }

                    pool.release();
                });



            });

        });

    }


    insertFuncionarioTransaction(req, pool) {
        // Parâmetros para TABELA-LOGIN
        let cd_login_request = req.param("CD_LOGIN");
        let cd_senha_request = req.param("CD_SENHA");
        let tp_login = req.param("TIPO_USUARIO");
        let sn_ativo_login = req.param("SN_ATIVO");

        //Query para inserir na tabela Login
        let query = 'INSERT INTO LOGIN VALUES ('
            + 'NULL,'
            + "'" + cd_login_request + "'" + ','
            + "'" + cd_senha_request + "'" + ','
            + sn_ativo_login + ','
            + "'" + tp_login + "'" + ')';

        //Baseado no resultado da promessa irá mandar uma mensagem de retorno e será enviado como retorno da aplicação(Resolve).
        return new Promise((resolve, reject) => {

            //Iniciando Transação
            pool.beginTransaction(function (err) {
                if (err) {
                    throw err;
                }
                pool.query(query, function (error, results, fields) {
                    if (!error) {
                        //Retorno do MYSQL com o ÚLTIMO ID ADICIONADO NA TABELA LOGIN
                        var id_login = results.insertId;
                        // Variavéis do Request
                        let nome = req.param("NOME_FUNCIONARIO");
                        let cpf = req.param("CPF")
                        let telefone = req.param("TELEFONE");
                        let setor = req.param("SETOR");
                        let sn_ativo = 1;

                        //Query para inserir na tabela FUNCIONARIO
                        query = 'INSERT INTO FUNCIONARIO VALUES ('
                            + 'NULL,'
                            + "'" + id_login + "'" + ","
                            + "'" + nome + "'" + ','
                            + "'" + cpf + "'" + ','
                            + "'" + telefone + "'" + ','
                            + "'" + setor + "'" + ')'


                        console.log("QUERY" + query)

                        pool.query(query, function (error, results, fields) {
                            //Se a query de inserção não falhar irá realizar o commit da operação
                            if (!error) {
                                pool.commit(function (err) {

                                    //Finalização do cadastro.
                                    if (!err) {
                                        var resultado_cliente = JSON.parse('{"status":"Funcionário Cadastrado com Sucesso","code_status":"01"}');
                                        resolve(resultado_cliente);

                                    } else {
                                        //Caso apresente algua falha no commit irá enviar essa informação.
                                        return pool.rollback(function () {
                                            var resultado_cliente = JSON.parse('{"status":"Falha ao realizar o cadastro, tenta novamente","code_status":"00"}');
                                            resolve(resultado_cliente);
                                        });

                                    }
                                });

                            } else if (error.code === 'ER_DUP_ENTRY') {
                                //Captura de erros e retornos para api
                                return pool.rollback(function () {
                                    var resultado_cliente = JSON.parse('{"status":"Já existe um funcionário com esse Nome ou CPF","code_status":"02"}');
                                    resolve(resultado_cliente);
                                });
                            } else if (error.code === 'ER_DATA_TOO_LONG') {
                                //Captura de erros e retornos para api
                                var resultado = JSON.parse('{"status":"Coluna com o valor maior do que o permitido","code_status":"03"}');
                                resolve(resultado);
                            } else {
                                var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                                resolve(resultado);

                            }

                        });




                    } else if (error.code === 'ER_DUP_ENTRY') {
                        return pool.rollback(function () {
                            var resultado = JSON.parse('{"status":"Já existe um usuário com esse Login","code_status":"04"}');
                            resolve(resultado);
                        });
                    } else {
                        console.log('Erro ::' + err)
                        var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                        resolve(resultado);

                    }

                    pool.release();
                });



            });

        });

    }

    insertProduto(req, pool, objeto_produto) {
        return new Promise((resolve, reject) => {

            //Iniciando Transação
            pool.beginTransaction(function (err) {
                if (err) {
                    throw err;
                }
                pool.query('INSERT INTO PRODUTOS SET ?', objeto_produto, function (error, results, fields) {
                    if (!error) {
                        pool.commit(function (err) {
                            //Finalização do cadastro.
                            if (!err) {
                                var resultado_cliente = JSON.parse('{"status":"Produto Cadastrado com Sucesso","code_status":"01"}');
                                resolve(resultado_cliente);

                            } else if (err == 'ER_DUP_ENTRY') {
                                var resultado = JSON.parse('{"status":"Já existe um produto cadastro com esse nome","code_status":"04"}');
                                resolve(resultado);

                            } else {
                                //Caso apresente algua falha no commit irá enviar essa informação.
                                return pool.rollback(function () {
                                    var resultado_cliente = JSON.parse('{"status":"Falha ao realizar o cadastro tenta novamente","code_status":"00"}');
                                    resolve(resultado_cliente);
                                });

                            }
                        });


                    } else if (error.code === 'ER_DUP_ENTRY') {
                        return pool.rollback(function () {
                            var resultado = JSON.parse('{"status":"Já existe um produto cadastrado com esse nome","code_status":"04"}');
                            resolve(resultado);
                        });
                    } else {
                        console.log('ERROOO ????' + error)
                        var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                        resolve(resultado);

                    }
                    pool.release();
                });

            });

        });





    }

    insertProdutoLocal(req, pool, objeto_produto) {

        console.log(objeto_produto.IMAGEM)

        return new Promise((resolve, reject) => {

            //Iniciando Transação
            pool.beginTransaction(function (err) {
                if (err) {
                    throw err;
                }
                pool.query('INSERT INTO PRODUTOS SET ?', objeto_produto, function (error, results, fields) {
                    if (!error) {
                        pool.commit(function (err) {
                            //Finalização do cadastro.
                            if (!err) {
                                var resultado_cliente = JSON.parse('{"status":"Produto Cadastrado com Sucesso","code_status":"01"}');
                                resolve(resultado_cliente);

                            } else {
                                //Caso apresente algua falha no commit irá enviar essa informação.
                                return pool.rollback(function () {
                                    var resultado_cliente = JSON.parse('{"status":"Falha ao realizar o cadastro tenta novamente","code_status":"00"}');
                                    resolve(resultado_cliente);
                                });

                            }
                        });


                    } else if (error.code === 'ER_DUP_ENTRY') {
                        return pool.rollback(function () {
                            var resultado = JSON.parse('{"status":"Já existe um usuário com esse Login","code_status":"04"}');
                            resolve(resultado);
                        });
                    } else {
                        console.log('ERROOO ????' + error)
                        var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                        resolve(resultado);

                    }
                    pool.release();
                });

            });

        });





    }


    abrirMesa(req, pool) {

        //Baseado no resultado da promessa irá mandar uma mensagem de retorno e será enviado como retorno da aplicação(Resolve).
        return new Promise((resolve, reject) => {

            //Iniciando Transação
            pool.beginTransaction(function (err) {
                if (err) {
                    throw err;
                }
                //variaveis request
                let qr_code = req.param("QR_CODE");
                let id_usuario = req.param("ID_CLIENTE");
                let id_funcionario = req.param("ID_FUNCIONARIO");
                let tipo_pagamento = req.param("TP_PAGAMENTO");
                let valor_inicial = 0.0;
                let porcentagem_garcom_inicial = 0.0;
                let sn_pago_inicial = 0;
                let observacao = 'MESA INICIADA'


                var query_verifica_mesa = 'SELECT ID_MESA FROM MESAS WHERE QR_CODE = ' + "'" + qr_code + "'"
                console.log(query_verifica_mesa);
                pool.query(query_verifica_mesa, function (error, results, fields) {

                    if (!error) {

                        if (results.length == 0) {

                            console.log('NÃO EXISTE MESA CADASTRADA')

                            var resultado_mesa_ocupada = JSON.parse('{"status":"Não existe mesas cadastradas com esse QR_CODE","code_status":"03"}');
                            resolve(resultado_mesa_ocupada);
                            return;

                        }




                    } else {


                        console.log(error);
                        console.log('ERRO SEGUNDA ELSE')

                    }


                })


                //Query para verificar se a mesa está ocupada.
                var query = 'SELECT ID_MESA FROM MESAS WHERE SN_ATIVO = 1 AND SN_DISPONIVEL = 1 AND QR_CODE =' + "'" + qr_code + "'";
                console.log('QUERY 01 = ' + query)
                pool.query(query, function (error, results, fields) {
                    if (!error) {


                        if (results.length == 0) {

                            var resultado_mesa_ocupada = JSON.parse('{"status":"A mesa está ocupada/reservada no momento.","code_status":"02"}');
                            resolve(resultado_mesa_ocupada);


                        } else {

                            console.log('Resultado do primeiro SELECT ' + results[0].ID_MESA);
                            var id_mesa = results[0].ID_MESA;

                            var data = 'NOW()'

                            var query_insert_comanda = 'INSERT INTO COMANDA VALUES ('
                                + 'NULL,'
                                + id_usuario + ","
                                + id_funcionario + ','
                                + tipo_pagamento + ','
                                + id_mesa + ','
                                + valor_inicial + ','
                                + porcentagem_garcom_inicial + ','
                                + sn_pago_inicial + ','
                                + "'" + observacao + "'" + ','
                                + data + ','
                                + 'NULL)'

                            console.log(query_insert_comanda);


                            pool.query(query_insert_comanda, function (error, results, fields) {
                                //Se a query de inserção não falhar irá realizar o commit da operação
                                if (!error) {
                                    pool.commit(function (err) {


                                        //Finalização do cadastro.
                                        if (!err) {
                                            var resposta = {
                                                'status': 'Mesa Aberta com Sucesso',
                                                'code_status': '01',
                                                'mesa_cliente': id_mesa,
                                                'id_comanda': results.insertId

                                            }

                                            console.log(resposta);
                                            resolve(resposta);
                                            console.log("teste");

                                        } else {
                                            //Caso apresente algua falha no commit irá enviar essa informação.
                                            return pool.rollback(function () {
                                                var resultado_cliente = JSON.parse('{"status":"Falha ao realizar o cadastro tenta novamente","code_status":"00"}');
                                                resolve(resultado_cliente);
                                            });

                                        }
                                    });

                                } else if (error.code === 'ER_DUP_ENTRY') {
                                    //Captura de erros e retornos para api
                                    return pool.rollback(function () {
                                        var resultado_cliente = JSON.parse('{"status":"Já existe um Cliente com esse E-mail Cadastrado","code_status":"02"}');
                                        resolve(resultado_cliente);
                                    });
                                } else if (error.code === 'ER_DATA_TOO_LONG') {
                                    //Captura de erros e retornos para api
                                    var resultado = JSON.parse('{"status":"Coluna com o valor maior do que o permitido","code_status":"03"}');
                                    resolve(resultado);
                                } else {
                                    var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                                    resolve(resultado);

                                }

                            });
                        }




                    } else if (error.code === 'ER_DUP_ENTRY') {
                        return pool.rollback(function () {
                            var resultado = JSON.parse('{"status":"Já existe um usuário com esse Login","code_status":"04"}');
                            resolve(resultado);
                        });
                    } else {
                        var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                        resolve(resultado);

                    }

                    pool.release();
                });



            });








        })


    }

    cadastrarMesa(req, pool, objeto_mesa) {

        return new Promise((resolve, reject) => {

            //Iniciando Transação
            pool.beginTransaction(function (err) {
                if (err) {
                    throw err;
                }
                pool.query('INSERT INTO MESAS SET ?', objeto_mesa, function (error, results, fields) {
                    if (!error) {
                        pool.commit(function (err) {
                            //Finalização do cadastro.
                            if (!err) {
                                var resultado_cliente = JSON.parse('{"status":"Mesa Cadastrada com Sucesso","code_status":"01"}');
                                resolve(resultado_cliente);

                            } else if (err == 'ER_DUP_ENTRY') {
                                var resultado = JSON.parse('{"status":"Já existe um produto cadastro com esse nome","code_status":"04"}');
                                resolve(resultado);

                            } else {
                                //Caso apresente algua falha no commit irá enviar essa informação.
                                return pool.rollback(function () {
                                    var resultado_cliente = JSON.parse('{"status":"Falha ao realizar o cadastro tenta novamente","code_status":"00"}');
                                    resolve(resultado_cliente);
                                });

                            }
                        });


                    } else if (error.code === 'ER_DUP_ENTRY') {
                        return pool.rollback(function () {
                            var resultado = JSON.parse('{"status":"Já existe uma mesa cadastrada com esse QRCODE","code_status":"04"}');
                            resolve(resultado);
                        });
                    } else {
                        console.log('ERROOO ????' + error)
                        var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                        resolve(resultado);

                    }

                });

            });

        });



    }


    realizadrPedidoAndroid(req, pool) {

        return new Promise((resolve, reject) => {

        var listaObjetos = new Array();
        var listaProdutosComanda = new Array();
        listaObjetos = req.body;




        console.log('CORPO DA REQUISIÇÃO')
        console.log(req.body);

        for (var i = 0; i < listaObjetos.length; i++) {

            for (var j = 0; j < listaObjetos[i].quantidade; j++) {

                var objeto_retorno = {
                    'ID_COMANDA_FK': listaObjetos[i].idComanda,
                    'ID_PRODUTO_FK': listaObjetos[i].idProduto

                }
                listaProdutosComanda.push(objeto_retorno);

            }

        }


        var query = 'INSERT INTO COMANDA_PRODUTO (ID_COMANDA_FK,ID_PRODUTO_FK) VALUES ?';

        pool.query(query, [listaProdutosComanda.map(item => [item.ID_COMANDA_FK, item.ID_PRODUTO_FK])], (error, results, fields) => {


            if (error) {
                var resposta = {
                    'status': 'Não foi possivel realizar pedido!',
                    'code_status': '00'         
                    
                }

                console.log(error);

                resolve(resposta);

            } else {

                var resposta = {
                    'status': 'Pedido Realizado com Sucesso',
                    'code_status': '01'
                 }

                resolve(resposta);

          }
       

        })







    });



    }









}

module.exports = DataAcessLayer;