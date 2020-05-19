function cadastroMesa() {

    const DESCRICAO = $('#NOME_MESA').val();
    const SN_ATIVO = $('#SN_ATIVO:checked').length;
    const QR_CODE = document.getElementById('qrcodeMesa')

    const formData = new FormData();
    formData.append('NOME_MESA', DESCRICAO)
    formData.append('SN_ATIVO', SN_ATIVO)
    formData.append('qrcodeMesa', dataURItoBlob(QR_CODE.files[0], 'image/png'))

    fetch('http://app-63e8a389-b098-4421-abd4-cc50f39f4df1.cleverapps.io/addMesa', {
        method: 'POST',
        body: formData

    }).then(response => response.json()).then(data => alert(data.status)).then(clearModal()).then(reload());
}

function dataURItoBlob(dataURI, type) {
    // convert base64 to raw binary data held in a string
    var byteString = atob(dataURI.split(',')[1]);

    // separate out the mime component
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

    // write the bytes of the string to an ArrayBuffer
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
        // write the ArrayBuffer to a blob, and you're done
    }

    var bb = new Blob([ab], {
        type: type
    });
    return bb;
}