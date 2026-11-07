CREATE DATABASE IF NOT EXISTS empresa_db;
USE empresa_db;

CREATE TABLE IF NOT exists funcionarios (
	matricula int primary key,
    nome varchar(100) not null,
    idade int not null,
    tipo enum('GERENTE', 'OPERADOR') not null,
    departamento varchar(100) null,
	funcao varchar(100) null,
    constraint 	regra_cargos check (
    (tipo = 'GERENTE' and departamento is not null and funcao is null) or
    (tipo = 'operador' and funcao is not null and departamento is null)
    )
);
