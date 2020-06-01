function cadastroMesa() {

    const NOME_MESA = $('#NOME_MESA').val();
    const SN_ATIVO = $('#SN_ATIVO:checked').length;
    const QR_CODE = NOME_MESA; //wtf????? MERMA COISA, TIO
    const IMG = $('#qrcodeMesa>img').attr('src');

    var file = dataURItoBlob(QR_CODE, 'image/png');

    const formData = new FormData();
    formData.append('NOME_MESA', NOME_MESA)
    formData.append('SN_ATIVO', SN_ATIVO)
    formData.append('QR_CODE', QR_CODE)
    formData.append('IMG', file)

    fetch('http://app-84c469d6-9c06-4181-9a74-5d84696798cf.cleverapps.io/addMesa', {
        method: 'POST',
        body: formData

    }).then(response => response.json()).then(data => alert(data.status)).then(clearModal()).then(reload());
}

function dataURItoBlob(dataURI, type) {
    // convert base64 to raw binary data held in a string
    //    var byteString = atob(dataURI.split(',')[1]);
    var byteString = dataURI;

    // separate out the mime component
    //  var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

    // write the bytes of the string to an ArrayBuffer
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    // write the ArrayBuffer to a blob, and you're done
    var bb = new Blob([ab], {
        type: type
    });
    return bb;
}