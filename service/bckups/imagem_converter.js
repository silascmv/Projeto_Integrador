class ConversaoImagem {
    
    constructor() {
        const path = require('path');
    
    };

    decode_base64(base64str, filename) {
        return new Promise((resolve, reject) => {
            let buf = Buffer.from(base64str, 'base64');
            resolve(buf);

        })
    }

    salvarFoto(arq_imagem,nome,fs) {        
        fs.readFile('./', (err, arq_imagem) => {
            const newPath = nome + '.jpg'
            fs.writeFile(newPath, arq_imagem, () => {
                console.log("Arquivo Criado com Sucesso.")
            });
           });
        

    }



}

module.exports = ConversaoImagem