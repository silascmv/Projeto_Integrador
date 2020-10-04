// function cadastro() {
//   const username = document.getElementsByName('username')[0].value;
//   const your_email = document.getElementsByName('your_email')[0].value;
//   const password = document.getElementsByName('password')[0].value;
//   const name = document.getElementsByName('name')[0].value;
//   const address = document.getElementsByName('address')[0].value;
//   const telephone = document.getElementsByName('telephone')[0].value;
//   const cellphone = document.getElementsByName('cellphone')[0].value;
//   fetch('http://app-84c469d6-9c06-4181-9a74-5d84696798cf.cleverapps.io/addCliente/?cd_login=' + username + '&cd_senha=' + password + '&nome=' + name + '&email=' + your_email + '&endereco=' + address + '&telefone=' + telephone + '&celular=' + cellphone, {
//     method: 'POST',
//     headers: {
//       'Accept': 'application/json',
//       'Content-Type': 'application/json'
//     },

//   }).then(response => response.json()).then(data => alert(data.status));
// }

// function cadastro() {
//   const username = document.getElementsByName('username')[0].value;
//   const your_email = document.getElementsByName('your_email')[0].value;
//   const password = document.getElementsByName('password')[0].value;
//   const name = document.getElementsByName('name')[0].value;
//   const address = document.getElementsByName('address')[0].value;
//   const telephone = document.getElementsByName('telephone')[0].value;
//   const cellphone = document.getElementsByName('cellphone')[0].value;
//   fetch('http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io/addCliente/', {
//     method: 'POST',
//     headers: {
//       'Accept': 'application/json',
//       'Content-Type': 'application/json'
//     },
//     body: JSON.stringify({
//       username: username, 
//       your_email: your_email,
//       password: password,
//       name: name,
//       address: address,
//       telephone: telephone,
//       cellphone: cellphone
//     }),
//   }).then(response => response.json()).then(data => alert(data.status));
// }

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
          TIPO_USUARIO: "ADM", //perguntar a silas
          NOME_FUNCIONARIO: NOME_FUNCIONARIO,
          CPF: CPF,
          TELEFONE: TELEFONE,
          SETOR: "ADM", //perguntar a silas
          SN_ATIVO: "1" //permanece setado
      }),
      }).then(response => response.json()).then(data => alert(data.status)).then(clearModal()).then(reload());

}