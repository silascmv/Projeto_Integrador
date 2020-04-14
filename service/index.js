//IMPORTAÇÃO DE BIBLIOTECAS EXPRESS(API),MYSQL(DATABASE),BODYPARSER(JSON)
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');




//APP INICIANDO O EXPRESS
const app = express();
//CONEXÃO COM O BANCO - IMPORTAÇÃO DA CLASSE DE CONEXÃO
var pool = require(__dirname + '/database.js');
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

        try{
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
                    pool.release();
                });
            }

        }

        realizarCadastro();
        

    }catch(err){
        console.log('ERRRRO DOS GRANDES.')

    }


    });



});


// -------------SEÇÃO DE API PARA CLIENTE-----------------
//CONSULTAR TODOS OS CLIENTE
app.get('/consultaTodosClientes', function (req, res) {
    // Conectando ao banco para usar na API.
    pool.getConnection(function (err, pool) {

        // Executando a query MySQL (selecionar todos os dados da tabela usuário).
        pool.query('SELECT * FROM CLIENTE', function (error, results, fields) {
            // Caso ocorra algum erro, não irá executar corretamente.if (error) throw error;
            if (isEmptyObject(results)) {

                res.json({ Status: "Não existe nenhum cliente cadastrado", Code_Status: 00 });
                pool.destroy();
            } else {
                // Pegando a 'resposta' do servidor pra nossa requisição. Ou seja, aqui ele vai mandar nossos dados.
                res.send(results)
                console.log('Consulta de todos os clientes Realizada com Sucesso');
                pool.destroy()
            }

        });
    });
});

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




//CONFIGURAÇÃO PARA HTTPS,VÁRIAVIES PARA CERTIFICADO.
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
