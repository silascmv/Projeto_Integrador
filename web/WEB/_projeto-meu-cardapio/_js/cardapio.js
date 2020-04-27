function cadastro() {
    const nome_produto = document.getElementsByName('nome_produto')[0].value;
    const id_fornecedor = document.getElementsByName('id_fornecedor')[0].value;
    const valor = document.getElementsByName('valor')[0].value;
    const descricao = document.getElementsByName('descricao')[0].value;
    const codigo_barra = document.getElementsByName('codigo_barra')[0].value;
    const tipo = document.getElementsByName('tipo')[0].value;
    const validade = document.getElementsByName('validade')[0].value;
    //const img = document.getElementsByName('img')[0].value;
    fetch('http://app-63e8a389-b098-4421-abd4-cc50f39f4df1.cleverapps.io/addProduto/?nome_produto='+nome_produto+'&id_fornecedor='+id_fornecedor+'&valor='+valor+'&descricao='+descricao+'&codigo_barra='+codigo_barra+'&tipo='+tipo+'&validade='+validade, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },

}).then(response => response.json()).then(data => alert(data.status));
}
