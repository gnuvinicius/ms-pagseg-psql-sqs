CREATE SEQUENCE endereco_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE tb_endereco (
    id INTEGER PRIMARY KEY DEFAULT nextval('endereco_seq'),
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(255) NULL,
    complemento VARCHAR(255) NULL,
    bairro VARCHAR(255) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    cep VARCHAR(255) NOT NULL
);

CREATE SEQUENCE cliente_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE tb_cliente (
    id INTEGER PRIMARY KEY DEFAULT nextval('cliente_seq'),
    nome VARCHAR(255) NOT NULL,
    endereco_id INTEGER,
    telefone VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES tb_endereco(id)
);

CREATE SEQUENCE produto_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE tb_produto (
    id INTEGER PRIMARY KEY DEFAULT nextval('produto_seq'),
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    preco numeric(38,2) NOT NULL,
    quantidade INTEGER
);

CREATE SEQUENCE venda_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE tb_venda (
    id INTEGER PRIMARY KEY DEFAULT nextval('venda_seq'),
    valor_total numeric(38,2) NOT NULL,
    data_venda TIMESTAMP,
    forma_pagamento varchar(50) NOT NULL,
    cliente_id INTEGER NOT NULL,
    num_transacao VARCHAR(255),
    CONSTRAINT fk_venda_cliente FOREIGN KEY (cliente_id) REFERENCES tb_cliente(id)
);

CREATE SEQUENCE item_venda_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE tb_item_venda (
    id INTEGER PRIMARY KEY DEFAULT nextval('item_venda_seq'),
    produto_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_unitario numeric(38,2) NOT NULL,
    preco_total numeric(38,2) NOT NULL,
    venda_id INTEGER NOT NULL,
    CONSTRAINT fk_item_produto FOREIGN KEY (produto_id) REFERENCES tb_produto(id),
    CONSTRAINT fk_item_venda FOREIGN KEY (venda_id) REFERENCES tb_venda(id)
);