// function login() {
//   const CD_LOGIN = document.getElementsByName('CD_LOGIN')[0].value;
//   const CD_SENHA = document.getElementsByName('CD_SENHA')[0].value;
//   fetch('http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io/realizarLoginFuncionario', {
//     method: 'POST',
//     headers: {
//         'Accept': 'application/json',
//         'Content-Type': 'application/json'
//     },
//     body: JSON.stringify({

//       CD_LOGIN = CD_LOGIN,
//       CD_SENHA = CD_SENHA

//     }),
//   }).then(response => response.json()).then(data => {
//     if (data.code_status === 1) {
//       localStorage.setItem('cardapio', true), window.location.href = "./index.html";
//     } else {
//       alert(data.status)
//     }
//   });
// }

// function login() {

//   const CD_LOGIN = document.getElementsByName('CD_LOGIN')[0].value;
//   const CD_SENHA = document.getElementsByName('CD_SENHA')[0].value;

//   const formData = new FormData();
//   formData.append('CD_LOGIN', CD_LOGIN);
//   formData.append('CD_SENHA', CD_SENHA);


//   fetch('http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io/realizarLoginFuncionario', {
//       method: 'POST',
//       body: formData,

//   }).then(response => response.json()).then(data => {
//     if (data.code_status === 1) {
//       localStorage.setItem('cardapio', true), window.location.href = "./index.html";
//     } else {
//       alert(data.status)
//     }
//   });
// }

function login() {
  const CD_LOGIN = document.getElementsByName('CD_LOGIN')[0].value;
  const CD_SENHA = document.getElementsByName('CD_SENHA')[0].value;


  fetch('http://root-7a103f0a.localhost.run/realizarLoginFuncionario', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      CD_LOGIN: CD_LOGIN,
      CD_SENHA: CD_SENHA,

    }),
  }).then(response => response.json()).then(data => {
    if (data.code_status === 1) {
      localStorage.setItem('cardapio', true), window.location.href = "./index.html";
    } else {
      alert(data.status)
    }
  });
}