/*app.post('/listarTodosProdutos/', (req, res) => {

    pool.getConnection((err, pool) => {
        var id = req.param("id")
        var query = 'SELECT NOME_PRODUTO,VALOR,DESCRICAO,IMAGEM_PATH FROM PRODUTOS WHERE ID_PRODUTO =' + id;
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



})*/

/*app.post('/listarTodosTeste/', (req, res) => {

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



}) */


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


  app.post('/listarProdutonNovo/', (req, res) => {

    pool.getConnection((err, pool) => {
        var id = req.param("id")
        var query = 'SELECT NOME_PRODUTO,VALOR,DESCRICAO,IMAGEM_PATH FROM PRODUTOS WHERE ID_PRODUTO =' + id;
        pool.query(query, (error, results, fields) => {
            if (error) {
                console.log(error)
            }
            async function retrieveFoto() {
                //res.sendFile(__dirname + results[0].IMAGEM_PATH);



            }
            //Executando função assincrona.
            retrieveFoto();


        })
    })



})