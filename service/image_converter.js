const path = require("path");
const fs = require("fs");

function decode_base64(base64str, filename) {
  return new Promise((resolve, reject) => {
    let buf = Buffer.from(base64str, "base64");
    resolve(buf);
  });
}
module.exports = decode_base64;
