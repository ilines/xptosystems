-- Folha de respostas
-- Nome: Bruno Vieira Schettini da Silva
-- Data: 11/03/2019
-- Parte 3 – PL/SQL Prático
-- Exercício 5.
-- Otimize a última consulta SELECT (Exercício 4), medindo o custo e melhore caso
-- seja possível. Crie índices caso necessário. Salve todas alterações, caso existam,
-- no arquivo SCRIPT5.SQL

CREATE INDEX EXAME_NF_DATACADASTRO_IDX ON EXAME_NF (DATACADASTRO);
CREATE INDEX EXAME_ITEMNF_VALOR_IDX ON EXAME_ITEMNF (VALOR);

