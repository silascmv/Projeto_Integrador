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

function cadastro() {
  const username = document.getElementsByName('username')[0].value;
  const your_email = document.getElementsByName('your_email')[0].value;
  const password = document.getElementsByName('password')[0].value;
  const name = document.getElementsByName('name')[0].value;
  const address = document.getElementsByName('address')[0].value;
  const telephone = document.getElementsByName('telephone')[0].value;
  const cellphone = document.getElementsByName('cellphone')[0].value;
  fetch('http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io/addCliente/', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      username: username, 
      your_email: your_email,
      password: password,
      name: name,
      address: address,
      telephone: telephone,
      cellphone: cellphone
    }),
  }).then(response => response.json()).then(data => alert(data.status));
}