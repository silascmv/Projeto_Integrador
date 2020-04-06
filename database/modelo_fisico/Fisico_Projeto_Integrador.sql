CREATE TABLE IF NOT EXISTS LOGIN (
    ID_LOGIN INT PRIMARY KEY AUTO_INCREMENT,
    CD_LOGIN VARCHAR(30) NOT NULL,
    CD_SENHA VARCHAR(30) NOT NULL,
    SN_ATIVO BOOLEAN NOT NULL,
    TP_LOGIN VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS CLIENTE (
    ID_CLIENTE INT PRIMARY KEY AUTO_INCREMENT,
    ID_LOGIN INT NOT NULL,
    NOME VARCHAR(70) NOT NULL,
    EMAIL VARCHAR (70) UNIQUE NOT NULL,
    ENDERECO VARCHAR (70) NOT NULL,
    SN_ATIVO BOOLEAN NOT NULL,
    TELEFONE VARCHAR (70) ,
    CELULAR VARCHAR (70) NOT NULL,
	FOREIGN KEY(ID_LOGIN) REFERENCES LOGIN (ID_LOGIN)
	
);