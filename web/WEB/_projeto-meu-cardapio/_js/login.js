function login() {
  const cd_login = document.getElementsByName('nomeouendereco')[0].value;
  const cd_senha = document.getElementsByName('senha')[0].value;
  fetch('http://app-ee0cc445-4a89-42ba-8fe5-8954b141f3e2.cleverapps.io/realizaLogin/', {
    method: 'POST',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({

      cd_login = cd_login,
      cd_senha = cd_senha

    }),
  }).then(response => response.json()).then(data => {
    if (data.code_status === 1) {
      localStorage.setItem('cardapio', true), window.location.href = "./index.html";
    } else {
      alert(data.status)
    }
  });
}