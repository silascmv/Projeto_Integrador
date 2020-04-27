function cadastro() {
    const nome_produto = document.getElementsByName('NOME_PRODUTO')[0].value;
    const id_fornecedor = document.getElementsByName('ID_FORNECEDOR')[0].value;
    const valor = document.getElementsByName('VALOR')[0].value;
    const descricao = document.getElementsByName('DESCRICAO')[0].value;
    const codigo_barra = document.getElementsByName('CODIGO_BARRA')[0].value;
    const tipo = document.getElementsByName('TIPO')[0].value;
    const validade = document.getElementsByName('VALIDADE')[0].value;
    const img = document.querySelector("IMG"[type='file'])

    const formData = new FormData();
    formData.append('NOME_PRODUTO',nome_produto)
    formData.append('ID_FORNECEDOR',id_fornecedor)
    formData.append('VALOR',valor)
    formData.append('DESCRICAO',descricao)
    formData.append('CODIGO_BARRA',codigo_barra)
    formData.append('TIPO',tipo)
    formData.append('VALIDADE',validade)
    formData.append('IMG',img)

    fetch('http://app-63e8a389-b098-4421-abd4-cc50f39f4df1.cleverapps.io/addProduto/',{
    method: 'POST',
    body: formData,
    
}).then(response => response.json()).then(data => alert(data.status));
}
