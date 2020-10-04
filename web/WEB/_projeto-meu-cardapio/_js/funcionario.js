function cadastro() {
    const CD_LOGIN = document.getElementsByName('CD_LOGIN')[0].value;
    const CD_SENHA = document.getElementsByName('CD_SENHA')[0].value;
    const TIPO_USUARIO = document.getElementsByName('TIPO_USUARIO')[0].value;
    const NOME_FUNCIONARIO = document.getElementsByName('NOME_FUNCIONARIO')[0].value;
    const CPF = document.getElementsByName('CPF')[0].value;
    const TELEFONE = document.getElementsByName('TELEFONE')[0].value;
    const SETOR = document.getElementsByName('SETOR')[0].value;
    const SN_ATIVO = $('#SN_ATIVO:checked').length;

    fetch('http://root-7a103f0a.localhost.run/addFuncionario/', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            CD_LOGIN: CD_LOGIN,
            CD_SENHA: CD_SENHA,
            TIPO_USUARIO: "Administrador",
            NOME_FUNCIONARIO: NOME_FUNCIONARIO,
            CPF: CPF,
            TELEFONE: TELEFONE,
            SETOR: SETOR,
            SN_ATIVO: SN_ATIVO
        }),
        }).then(response => response.json()).then(data => alert(data.status)).then(clearModal()).then(reload());

}