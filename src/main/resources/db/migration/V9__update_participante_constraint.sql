ALTER TABLE participantes
      DROP CONSTRAINT unique_cpf;
      
DROP INDEX IF EXISTS participantes_cpf;

CREATE UNIQUE INDEX participantes_cpf_evento 
       ON participantes (cpf, evento_id);

ALTER TABLE participantes 
      ADD CONSTRAINT unique_cpf_per_evento
      UNIQUE USING INDEX participantes_cpf_evento;
