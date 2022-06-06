CREATE TABLE public.participantes
(
    id uuid NOT NULL,
    nome character varying(256) NOT NULL,
    telefone character varying(64) NOT NULL,
    email character varying(256) NOT NULL,
    cpf character varying(64) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp,
    evento_id int NOT NULL,
    
    PRIMARY KEY (id),
    CONSTRAINT fk_evento
      FOREIGN KEY (evento_id)
      REFERENCES eventos(id)
)
    WITH ( OIDS = FALSE );

ALTER TABLE public.participantes
    OWNER to postgres;
