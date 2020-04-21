//EXCLUINDO CLIENTE POR ID
app.delete('/apagarClienteId/:id', function (req, res) {

    pool.getConnection(function (err, pool) {
        //variavel para filtro
        let filter = '';
        //validação se o id não está nulo
        if (req.params.id) filter = parseInt(req.params.id);
        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        pool.query('DELETE FROM CLIENTE WHERE ID_CLIENTE = ' + filter, function (error, results, fields) {
            // Pegando a 'resposta' do servidor pra nossa requisição. Ou seja, aqui ele vai mandar nossos dados.
            if (results["affectedRows"] < 1) {
                console.log('Não existe usuário para ser deletado');
                res.json({ Status: "Informar um ID válido", Code_Status: 00 });
                pool.destroy();
            } else {
                res.json({ Status: "Deleção Realizada com Sucesso", Code_Status: 01 })

            }
        });
    });

})