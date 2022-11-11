-- adding the idade column to the dependente
ALTER TABLE public.dependentes
ADD COLUMN idade int;

UPDATE public.dependentes SET idade = 1;

ALTER TABLE public.dependentes
ALTER COLUMN idade SET NOT NULL;

-- adding the idade column to the participante
ALTER TABLE public.participantes
ADD COLUMN idade int;

UPDATE public.participantes SET idade = 1;

ALTER TABLE public.participantes
ALTER COLUMN idade SET NOT NULL;


-- adding the valorPago column to the participante
ALTER TABLE public.participantes
ADD COLUMN valor_pago real;

UPDATE public.participantes SET valor_pago = 0.0;

ALTER TABLE public.participantes
ALTER COLUMN valor_pago SET NOT NULL;

