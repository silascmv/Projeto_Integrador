function cadastroMesa() {
    
    const DESCRICAO = $('#NOME_MESA').val();
    const SN_ATIVO  = $('#SN_ATIVO:checked').length;
    const QR_CODE   = $('#qrcodeMesa>img').attr('src');

    const formData = new FormData();
    formData.append('NOME_MESA', DESCRICAO)
    formData.append('SN_ATIVO', SN_ATIVO)
    formData.append('qrcodeMesa', QR_CODE)

    fetch('http://app-63e8a389-b098-4421-abd4-cc50f39f4df1.cleverapps.io/addMesa', {
        method: 'POST',
        body: formData

    }).then(response => response.json()).then(data => alert(data.status)).then(clearModal()).then(reload());
}