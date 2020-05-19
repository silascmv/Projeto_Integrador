


/*app.post('/addClienteOLD', function (req, res) {
    //Abrindo Conexão com o Banco de Dados (Objeto Pool Importado da Classe DataBase)
    pool.getConnection(function (err, pool) {

        const objeto_dal = new DataAcessLayer();

        try {
            async function realizarCadastro() {
                const resultados_login = await objeto_dal.insertLogin(req, res, pool).then(resultado => {

                    console.log('RESULTADO DO RETORNO DO LOGIN - ' + resultado);
                    return resultado;
                });

                if (Number(resultados_login.code_status) === 2) {
                    res.send(resultados_login);
                    return;
                } else {
                    const resultado = await objeto_dal.insertCliente(req, res, pool, resultados_login).then(resultado_cliente => {
                        if (typeof (resultado_cliente) !== 'number') {
                            res.send(resultado_cliente);
                        } else {
                            res.send(resultado_cliente)
                        }
                    });
                }

            }

            realizarCadastro();


        } catch (err) {
            console.log('ERRRRO DOS GRANDES.')

        }


    });



}); */
