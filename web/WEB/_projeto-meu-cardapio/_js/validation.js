  
window.onload = function() {
if(localStorage.getItem('cardapio') != "true" ){window.location.href ="http://localhost/faul/web/WEB/_projeto-meu-cardapio/pagina_de_login.html"}
};


function exitUm(){

    localStorage.setItem('cardapio', false)

};

function exitDois(){

    window.location.href ="http://localhost/faul/web/WEB/_projeto-meu-cardapio/pagina_de_login.html"

};
