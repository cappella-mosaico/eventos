ALTER TABLE public.eventos
ADD COLUMN created_at timestamp,
ADD COLUMN updated_at timestamp;

UPDATE public.eventos SET created_at = now();

ALTER TABLE public.eventos
ALTER COLUMN created_at SET NOT NULL;

