CREATE TABLE IF NOT EXISTS LOGIN (
    ID_LOGIN INT PRIMARY KEY AUTO_INCREMENT,
    CD_LOGIN VARCHAR(30) NOT NULL UNIQUE,
    CD_SENHA VARCHAR(30) NOT NULL,
    SN_ATIVO BOOLEAN NOT NULL,
    TP_LOGIN VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS CLIENTE (
    ID_CLIENTE INT PRIMARY KEY AUTO_INCREMENT,
    ID_LOGIN INT UNIQUE NOT NULL ,
    NOME VARCHAR(70) NOT NULL,
    EMAIL VARCHAR (70) UNIQUE NOT NULL,
    ENDERECO VARCHAR (70) NOT NULL,
    SN_ATIVO BOOLEAN NOT NULL,
    TELEFONE VARCHAR (70) ,
    CELULAR VARCHAR (70) NOT NULL,
	FOREIGN KEY(ID_LOGIN) REFERENCES LOGIN (ID_LOGIN)
);

CREATE TABLE IF NOT EXISTS FUNCIONARIO (
    ID_FUNCIONARIO INT PRIMARY KEY AUTO_INCREMENT,
    ID_LOGIN INT,
    NOME VARCHAR(70),
    CPF VARCHAR(11),
    TELEFONE VARCHAR(20),
    SETOR INT,
    LOGIN VARCHAR(30),
    SENHA VARCHAR(30),
    FOREIGN KEY(ID_LOGIN) REFERENCES LOGIN (ID_LOGIN)
);

CREATE TABLE IF NOT EXISTS MESAS (
    ID_MESA INT PRIMARY KEY AUTO_INCREMENT,
    DESCRICAO VARCHAR(70),
    SN_ATIVO BOOLEAN NOT NULL     
);

CREATE TABLE IF NOT EXISTS FORNECEDOR (
    ID_FORNECEDOR INT PRIMARY KEY AUTO_INCREMENT,
    CNPJ VARCHAR(30) UNIQUE,
    RAZAO_SOCIAL VARCHAR(70),
    TP_FORNECIMENTO VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS TIPO_PAGAMENTO (
    ID_TP_PAGAMENTO INT PRIMARY KEY AUTO_INCREMENT,
    DESCRICAO VARCHAR(70),
    SN_ATIVO BOOLEAN
);

CREATE TABLE IF NOT EXISTS ESTOQUE (
    ID_ESTOQUE INT PRIMARY KEY AUTO_INCREMENT,
    NM_ESTOQUE VARCHAR(70)
);

CREATE TABLE PRODUTO_ESTOQUE (
    ID_PRODUTO_ESTOQUE INT PRIMARY KEY AUTO_INCREMENT,
    ID_ESTOQUE INT,
    ID_PRODUTO_FK INT,
    QUANTIDADE_PRODUTO INT,
    FOREIGN KEY(ID_ESTOQUE) REFERENCES ESTOQUE (ID_ESTOQUE)
);

CREATE TABLE IF NOT EXISTS PRODUTOS (
    ID_PRODUTO INT PRIMARY KEY AUTO_INCREMENT,
    NOME_PRODUTO VARCHAR(30) NOT NULL UNIQUE,
    ID_FORNECEDOR INT,
    VALOR DECIMAL,
    DESCRICAO VARCHAR(60),
    CODIGO_BARRA VARCHAR(30),
    TIPO VARCHAR(30),
    VALIDADE DATE,
    IMAGEM_PATH VARCHAR(200),
    FOREIGN KEY(ID_FORNECEDOR) REFERENCES FORNECEDOR (ID_FORNECEDOR)
);

CREATE TABLE IF NOT EXISTS COMANDA (
    ID_COMANDA INT PRIMARY KEY AUTO_INCREMENT,
    ID_CLIENTE INT,
    ID_FUNCIONARIO INT,
    ID_TP_PAGAMENTO INT,
    ID_MESA INT,
    VALOR_TOTAL DECIMAL,
    PORCENTAGEM_GARCOM BOOLEAN,
    QR_CODE VARCHAR(30),
    SN_PAGO INT,
    OBSERVACAO VARCHAR(70),
    DT_HR_INICIO_COMANDA DATE,
    DT_HR_FIM_COMANDA DATE,    
    FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTE (ID_CLIENTE),
    FOREIGN KEY(ID_FUNCIONARIO) REFERENCES FUNCIONARIO (ID_FUNCIONARIO),
    FOREIGN KEY(ID_TP_PAGAMENTO) REFERENCES TIPO_PAGAMENTO (ID_TP_PAGAMENTO),
    FOREIGN KEY(ID_MESA) REFERENCES MESAS (ID_MESA)
);

CREATE TABLE IF NOT EXISTS COMANDA_PRODUTO (
    ID_COMANDA_FK INT,
    ID_PRODUTO_FK INT,
	FOREIGN KEY(ID_COMANDA_FK) REFERENCES COMANDA (ID_COMANDA),
    FOREIGN KEY(ID_PRODUTO_FK) REFERENCES PRODUTOS (ID_PRODUTO)
);


ALTER TABLE PRODUTO_ESTOQUE ADD FOREIGN KEY(ID_PRODUTO_FK) REFERENCES PRODUTOS(ID_PRODUTO);

