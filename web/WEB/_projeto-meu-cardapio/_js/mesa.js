function cadastroMesa() {

    const NOME_MESA = $('#NOME_MESA').val();
    const DESCRICAO = $('#NOME_MESA').val();
    const SN_ATIVO = $('#SN_ATIVO:checked').length;
    const QR_CODE = NOME_MESA; //wtf????? MERMA COISA, TIO
    const IMG = $('#qrcodeMesa>img').attr('src');

    const nome_imagem = NOME_MESA + '.png'
    var file = dataURLtoFile(IMG, nome_imagem);
    console.log(file);

    const formData = new FormData();
    formData.append('NOME_MESA', NOME_MESA)
    formData.append('DESCRICAO', DESCRICAO)
    formData.append('SN_ATIVO', SN_ATIVO)
    formData.append('QR_CODE', QR_CODE)
    formData.append('IMG', file)

    fetch('http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io/addMesa/', {
        method: 'POST',
        body: formData

    }).then(response => response.json()).then(data => alert(data.status)).then(clearModal()).then(reload());
}

// function dataURItoBlob(dataURI, type) {
//     console.log(dataURI)
//     // convert base64 to raw binary data held in a string
//     //    var byteString = atob(dataURI.split(',')[1]);
//     var byteString = dataURI;
//     // var byteString = "data:image/png;base64,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"

//     // separate out the mime component
//     var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

//     // write the bytes of the string to an ArrayBuffer
//     var ab = new ArrayBuffer(byteString.length);
//     var ia = new Uint8Array(ab);
//     for (var i = 0; i < byteString.length; i++) {
//         ia[i] = byteString.charCodeAt(i);
//     }

//     // write the ArrayBuffer to a blob, and you're done
//     var bb = new Blob([ab], {
//         type: type
//     });
//     return bb;
// }

// function imagem_converter(dataURI) {
//     var image = new Image(),
//         containerWidth = null,
//         containerHeight = null;

//     image.onload = function () {
//         containerWidth = image.width;
//         containerHeight = image.height;
//     }
//     image.src = dataURI;
//     return image;
// }

function dataURLtoFile(dataurl, filename) {

    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);

    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }

    return new File([u8arr], filename, {
        type: mime
    });
}