CREATE TABLE public.eventos
(
    id serial NOT NULL,
    titulo character varying(256) NOT NULL,
    data_inicial timestamp NOT NULL,
    data_final timestamp NOT NULL,
    imagem character varying(512),
    sobre text NOT NULL,
    valor character varying(256),
    local character varying(256),
    endereco character varying(512),    
    PRIMARY KEY (id)
)
    WITH ( OIDS = FALSE );

ALTER TABLE public.eventos
    OWNER to postgres;
