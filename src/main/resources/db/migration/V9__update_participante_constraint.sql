ALTER TABLE participantes
      DROP CONSTRAINT IF EXISTS unique_cpf;

CREATE UNIQUE INDEX participantes_cpf_evento 
       ON participantes (cpf, evento_id);

ALTER TABLE participantes 
      ADD CONSTRAINT unique_cpf_per_evento
      UNIQUE USING INDEX participantes_cpf_evento;
