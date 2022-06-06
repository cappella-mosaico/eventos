CREATE TABLE public.dependentes
(
    id uuid NOT NULL,
    nome character varying(256) NOT NULL,
    participante_id uuid NOT NULL,
    
    PRIMARY KEY (id),
    CONSTRAINT fk_participante
      FOREIGN KEY (participante_id)
      REFERENCES participantes(id)
)
    WITH ( OIDS = FALSE );

ALTER TABLE public.dependentes
    OWNER to postgres;
