﻿-- Folha de respostas
-- Nome: Bruno Vieira Schettini da Silva
-- Data: 11/03/2019
-- Parte 3 – PL/SQL Prático
-- Exercício 3.
-- Crie uma procedure chamada DEFINIR_VALORES para que altere o valor dos itens 
-- da tabela EXAME_ITEMNF, definindo números inteiros aleatórios entre 1 e 100. 
-- Faça com que a linha da tabela EXAME_NF receba o valor da somatória de seus 
-- itens na coluna TOTALGERAL. Salve o fonte do script no arquivo SCRIPT3.SQL.

-- DROP PROCEDURE DEFINIR_VALROES;

CREATE OR REPLACE PROCEDURE DEFINIR_VALORES(xidnf NUMBER)

IS
    TOTAL number;

BEGIN

    FOR ITEMS IN (
        SELECT * FROM EXAME_ITEMNF WHERE IDNF = xidnf
    ) LOOP   
        -- UPDATE EXAME_ITEMNF SET VALOR = (SELECT CAST(round(dbms_random.value(1,100)) AS number) FROM dual) WHERE IDNF = xidnf        
        UPDATE EXAME_ITEMNF SET VALOR = (SELECT CAST(round(dbms_random.value(1,100)) AS number) FROM dual) WHERE IDITEMNF = ITEMS.IDITEMNF;
    END LOOP;    

    SELECT SUM(VALOR) INTO TOTAL FROM EXAME_ITEMNF WHERE IDNF = xidnf;

    UPDATE EXAME_NF SET TOTALGERAL = TOTAL  WHERE IDNF = xidnf;

END;

-- URL da ferramenta de teste: https://livesql.oracle.com
-- EXEC DEFINIR_VALORES(1)
