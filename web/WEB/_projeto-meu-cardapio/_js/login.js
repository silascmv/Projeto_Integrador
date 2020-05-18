function login() {
  const nomeouendereco = document.getElementsByName('nomeouendereco')[0].value;
  const senha = document.getElementsByName('senha')[0].value;
  fetch('http://app-84c469d6-9c06-4181-9a74-5d84696798cf.cleverapps.io/realizarLogin/' + nomeouendereco + '&' + senha, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
  }).then(response => response.json()).then(data => {
    if (data.code_status === 1) {
      localStorage.setItem('cardapio', true), window.location.href = "./index.html";
    } else {
      alert(data.status)
    }
  });
}