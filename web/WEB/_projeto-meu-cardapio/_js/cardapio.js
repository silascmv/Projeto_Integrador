function cadastro() {
    const nome_produto = document.getElementsByName('NOME_PRODUTO')[0].value;
    const id_fornecedor = '1';
    const valor = document.getElementsByName('VALOR')[0].value;
    const descricao = document.getElementsByName('DESCRICAO')[0].value;
    const codigo_barra = document.getElementsByName('CODIGO_BARRA')[0].value;
    const tipo = document.getElementsByName('TIPO')[0].value;
    const validade = document.getElementsByName('VALIDADE')[0].value;
    const img = document.getElementById('IMG')

    const formData = new FormData();
    formData.append('NOME_PRODUTO', nome_produto)
    formData.append('ID_FORNECEDOR', id_fornecedor)
    formData.append('VALOR', valor)
    formData.append('DESCRICAO', descricao)
    formData.append('CODIGO_BARRA', codigo_barra)
    formData.append('TIPO', tipo)
    formData.append('VALIDADE', validade)
    formData.append('IMG', img.files[0])

    fetch('http://app-84c469d6-9c06-4181-9a74-5d84696798cf.cleverapps.io/addProduto/', {
        method: 'POST',
        body: formData

    }).then(response => response.json()).then(data => alert(data.status)).then(clearModal()).then(reload());
}