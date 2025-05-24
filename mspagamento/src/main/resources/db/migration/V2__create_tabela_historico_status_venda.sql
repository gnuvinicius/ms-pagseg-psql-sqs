-- V2__create_tabela_historico_status_venda.sql

CREATE SEQUENCE historico_status_venda_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE tb_historico_status_venda (
    id INTEGER PRIMARY KEY DEFAULT nextval('historico_status_venda_seq'),
    venda_id INTEGER,
    status VARCHAR(50),
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP,
    CONSTRAINT fk_historico_status_venda_venda FOREIGN KEY (venda_id) REFERENCES tb_venda(id)
);