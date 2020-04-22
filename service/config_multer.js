var multer = require('multer');

var storage = multer.diskStorage({
<<<<<<< HEAD
<<<<<<< HEAD
    destination: function (req, file, cb) {
      cb(null, __dirname + '/my-uploads')
=======
    destination: function (req, file, cb) {
      cb(null, __dirname + '/tmp/my-uploads')
>>>>>>> parent of dcb0235... 🌺🐪 Checkpoint
    },
    filename: function (req, file, cb) {
      cb(null,  Date.now()+ '-' + file.originalname)
    }
  });


  const upload = multer({storage: storage});

  module.exports = upload;
<<<<<<< HEAD
=======
  destination: function(req, file, cb) {
    cb(null, __dirname + "/my-uploads");
  },
  filename: function(req, file, cb) {
    cb(null, Date.now() + "-" + file.originalname);
  }
});
>>>>>>> f653ef9738211481a72c7f99af5689e4e631bc3a
=======
>>>>>>> parent of dcb0235... 🌺🐪 Checkpoint


