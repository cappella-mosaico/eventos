-- dependentes should be unique per participante
CREATE UNIQUE INDEX dependentes_nome_participante 
ON dependentes (nome, participante_id);

ALTER TABLE dependentes 
ADD CONSTRAINT unique_nome_participante 
UNIQUE USING INDEX dependentes_nome_participante;

-- participantes should be unique per cpf
CREATE UNIQUE INDEX participantes_cpf 
ON participantes (cpf);

ALTER TABLE participantes 
ADD CONSTRAINT unique_cpf 
UNIQUE USING INDEX participantes_cpf;
