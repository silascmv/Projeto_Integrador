  window.onload = function () {
      if (localStorage.getItem('cardapio') != "true") {
          window.location.href = "../pagina_de_login.html"
      }
  };


  function exitUm() {

      localStorage.setItem('cardapio', false)

  };

  function exitDois() {

      window.location.href = "../pagina_de_login.html";

  };