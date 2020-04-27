var mysql = require('mysql');
// node -v must > 8.x 
var util = require('util');


var pool = mysql.createPool({
    connectionLimit: 10,
    host: 'db4free.net',
    user: 'dbaservice',
    password: 'pyiEqBNdgy',
    database: 'databaseservice',
    connectionLimit: 10,
    multipleStatements: true
});

pool.getConnection((err, connection) => {
    if (err) {
        
        if (err.code === 'PROTOCOL_CONNECTION_LOST') {
            console.error('Database connection was closed.')
        }
        if (err.code === 'ER_CON_COUNT_ERROR') {
            console.error('Database has too many connections.')
        }
        if (err.code === 'ECONNREFUSED') {
            console.error('Database connection was refused.')
        }
    }
    if (connection) connection.release()
    return
})

pool.query = util.promisify(pool.query);

module.exports = pool
