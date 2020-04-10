function login() {
    const nomeouendereco = document.getElementsByName('nomeouendereco')[0].value;
    const senha = document.getElementsByName('senha')[0].value;
    fetch('http://app-63e8a389-b098-4421-abd4-cc50f39f4df1.cleverapps.io/realizarLogin/'+nomeouendereco+'&'+senha, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(item)
}).then(response => response.json()).then(data => alert(data.Status));
}
