BEGIN;

-- 1. Уникальность названия категории в проекте
ALTER TABLE category
    ADD CONSTRAINT category_project_id_name_unique
        UNIQUE (project_id, name);

-- 2. Уникальность цвета категории в проекте
ALTER TABLE category
    ADD CONSTRAINT category_project_id_color_unique
        UNIQUE (project_id, color);

COMMENT ON CONSTRAINT category_project_id_name_unique ON category
    IS 'Запрещает дублирование названий категорий в одном проекте';
COMMENT ON CONSTRAINT category_project_id_color_unique ON category
    IS 'Запрещает дублирование цветов категорий в одном проекте';

COMMIT;