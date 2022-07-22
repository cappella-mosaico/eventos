-- adding the isento column to the dependente
ALTER TABLE public.dependentes
ADD COLUMN isento boolean;

UPDATE public.dependentes SET isento = false;

ALTER TABLE public.dependentes
ALTER COLUMN isento SET NOT NULL;

-- adding the isento column to the participante
ALTER TABLE public.participantes
ADD COLUMN isento boolean;

UPDATE public.participantes SET isento = false;

ALTER TABLE public.participantes
ALTER COLUMN isento SET NOT NULL;

