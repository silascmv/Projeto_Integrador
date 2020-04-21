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


    insertFotoTransaction(req, pool) {

        let nome_image = req.param("nome_imagem");
        console.log(nome_image);
        let path_imagem = req.param("caminho_imagem");
        console.log(path_imagem);

        var query = '';

        query = 'INSERT INTO IMAGEM_TESTE VALUES ('
            + 'NULL,'
            + "'" + path_imagem + "'" + ","
            + "'" + nome_image + "'" + ')'

        return new Promise((resolve, reject) => {

            //Iniciando Transação
            pool.beginTransaction(function (err) {
                if (err) {
                    throw err;
                }
                pool.query(query, function (error, results, fields) {
                    if (!error) {
                        pool.commit(function (err) {
                            //Finalização do cadastro.
                            if (!err) {
                                var resultado_cliente = JSON.parse('{"status":"Imagem Cadastrada com Sucesso","code_status":"01"}');
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
                        var resultado = JSON.parse('{"status":"Operação desconhecida, entre em contato com TI","code_status":"00"}');
                        resolve(resultado);

                    }
                    pool.release();
                });

            });

        });





    }





}

module.exports = DataAcessLayer;