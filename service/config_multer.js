var multer = require('multer');

var storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, __dirname + '/my-uploads')
    },
    filename: function (req, file, cb) {
      cb(null,  req.param("NOME_PRODUTO") + '.jpg')
    }
  });


  const upload = multer({storage: storage});

  module.exports = upload;