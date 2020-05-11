function cadastro() {
    const username = document.getElementsByName('username')[0].value;
    const your_email = document.getElementsByName('your_email')[0].value;
    const password = document.getElementsByName('password')[0].value;
    const name = document.getElementsByName('name')[0].value;
    const address = document.getElementsByName('address')[0].value;
    const telephone = document.getElementsByName('telephone')[0].value;
    const cellphone = document.getElementsByName('cellphone')[0].value;
    fetch('http://app-63e8a389-b098-4421-abd4-cc50f39f4df1.cleverapps.io/addCliente/?cd_login='+username+'&cd_senha='+password+'&nome='+name+'&email='+your_email+'&endereco='+address+'&telefone='+telephone+'&celular='+cellphone, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },

}).then(response => response.json()).then(data => alert(data.status));
}
