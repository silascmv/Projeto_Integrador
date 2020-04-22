var multer = require("multer");

var storage = multer.diskStorage({
<<<<<<< HEAD
  destination: function(req, file, cb) {
    cb(null, __dirname + "/tmp/my-uploads");
  },
  filename: function(req, file, cb) {
    cb(null, Date.now() + "-" + file.originalname);
  }
});
=======
    destination: function (req, file, cb) {
      cb(null, __dirname + '/my-uploads')
    },
    filename: function (req, file, cb) {
      cb(null,  Date.now()+ '-' + file.originalname)
    }
  });


  const upload = multer({storage: storage});

  module.exports = upload;
>>>>>>> 12bd59101b9ec722ff902f35b71d932ebf8a9511

const upload = multer({ storage: storage });

module.exports = upload;
