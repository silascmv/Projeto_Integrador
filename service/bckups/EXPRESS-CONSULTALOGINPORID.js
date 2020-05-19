//CONSULTANDO CLIENTE POR ID
app.get('/consultaClienteId/:id', function (req, res) {
    // Conectando ao banco para usar na API.
    pool.getConnection(function (err, pool) {
        //variavel para filtro
        let filter = '';
        //validação se o id não está nulo
        if (req.params.id) filter = parseInt(req.params.id);
        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        pool.query('SELECT * FROM CLIENTE WHERE ID_CLIENTE = ' + filter, function (error, results, fields) {
            // Pegando a 'resposta' do servidor pra nossa requisição. Ou seja, aqui ele vai mandar nossos dados.
            if (isEmptyObject(results)) {

                res.json({ Status: "Informar um ID válido", Code_Status: 00 });
                pool.destroy();
            } else {
                res.send(results)
                console.log('Consulta por id Realizada com Sucesso');

            }
        });
    });
});
