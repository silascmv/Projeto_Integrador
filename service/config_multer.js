var multer = require('multer');


//Variaveis para salvar no servidor
let data = new Date()
let data_atual = data.getDate();
let mes_atual = data.getMonth() + 1;
let ano_atual = data.getFullYear();
let segundos = data.getSeconds();
var mseg    = data.getMilliseconds(); 
let data_final = data_atual + '-' + mes_atual + '-' + ano_atual + '-' + segundos + '-' + mseg;

var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, __dirname + '/my-uploads')
  },
  filename: function (req, file, cb) {
    cb(null, data_final + '-' + req.param("NOME_PRODUTO") + '.jpg')
            
  }
});


const upload = multer({ storage: storage });

module.exports = upload;