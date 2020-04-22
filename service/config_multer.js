var multer = require('multer');

var storage = multer.diskStorage({
<<<<<<< HEAD
    destination: function (req, file, cb) {
      cb(null, __dirname + '/my-uploads')
    },
    filename: function (req, file, cb) {
      cb(null,  Date.now()+ '-' + file.originalname)
    }
  });


  const upload = multer({storage: storage});

  module.exports = upload;
=======
  destination: function(req, file, cb) {
    cb(null, __dirname + "/my-uploads");
  },
  filename: function(req, file, cb) {
    cb(null, Date.now() + "-" + file.originalname);
  }
});
>>>>>>> f653ef9738211481a72c7f99af5689e4e631bc3a


