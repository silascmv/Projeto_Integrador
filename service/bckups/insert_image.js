var mysql = require('mysql');
var fs = require("fs");
 
var connection = mysql.createConnection({
    host: 'remotemysql.com',
    user: 'wIp1MpiKOt',
    password: 'pyiEqBNdgy',
    database: 'wIp1MpiKOt',
    debug: false,
});
connection.connect();
 
var dog = {
    IMAGEM: fs.readFileSync(__dirname + '/dog.jpg'),
    NOME_IMAGEM: 'Dog'
};
 
var cat = {
    IMAGEM: fs.readFileSync(__dirname + '/cat.jpg'),
    NOME_IMAGEM: 'Cat'
};
 
var query = connection.query('INSERT INTO IMAGEM_TESTE SET ?', dog, function(err,
    result) {
    console.log(result);
    console.log(err);
});
 
query = connection.query('INSERT INTO IMAGEM_TESTE SET ?', cat, function(err,
    result) {
    console.log(result);
    console.log(err);
});
 
connection.end();