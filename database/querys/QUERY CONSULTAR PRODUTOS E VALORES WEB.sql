SELECT COMANDA_PRODUTO.ID_COMANDA_FK,COMANDA.ID_COMANDA,PRODUTOS.NOME_PRODUTO,PRODUTOS.NOME_PRODUTO,SUM(PRODUTOS.VALOR),CLIENTE.NOME,COMANDA.SN_PAGO,COMANDA.DT_HR_INICIO_COMANDA AS 'HORÁRIO DE ABERTURA DA MESA',PRODUTOS.ID_PRODUTO FROM COMANDA_PRODUTO
INNER JOIN PRODUTOS ON COMANDA_PRODUTO.ID_PRODUTO_FK = PRODUTOS.ID_PRODUTO
INNER JOIN COMANDA ON COMANDA_PRODUTO.ID_COMANDA_FK = COMANDA.ID_COMANDA
INNER JOIN CLIENTE ON COMANDA.ID_CLIENTE = CLIENTE.ID_CLIENTE
INNER JOIN MESAS ON COMANDA.ID_MESA = MESAS.ID_MESA
WHERE COMANDA.SN_PAGO <> 1
group by PRODUTOS.NOME_PRODUTO